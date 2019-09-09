package nessiesson.usefulmod;

import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import nessiesson.usefulmod.config.Config;
import nessiesson.usefulmod.config.GuiConfig;
import nessiesson.usefulmod.mixins.ISoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.File;

public class LiteModUsefulMod implements Tickable, Configurable {
	public static Config config = new Config();
	public static KeyBinding highlightEntities = new KeyBinding("key.usefulmod.highlight_entities", Keyboard.KEY_LMENU, "UsefulMod");
	private static KeyBinding reloadAudioEngineKey = new KeyBinding("key.usefulmod.reload_audio", Keyboard.KEY_B, "UsefulMod");
	private StepAssistHelper stepAssistHelper = new StepAssistHelper();

	@Override
	public void init(File configPath) {
		LiteLoader.getInput().registerKeyBinding(highlightEntities);
		LiteLoader.getInput().registerKeyBinding(reloadAudioEngineKey);
		Display.setTitle(Display.getTitle() + " - " + Minecraft.getMinecraft().getSession().getUsername());
	}

	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
		if (!inGame) {
			return;
		}

		if (reloadAudioEngineKey.isKeyDown()) {
			((ISoundHandler) minecraft).getSoundManager().reloadSoundSystem();
			this.debugFeedBack();
		}

		final EntityPlayerSP player = minecraft.player;
		if (config.noFall && player.fallDistance > 2F) {
			player.connection.sendPacket(new CPacketPlayer(true));
		}

		if (LiteModUsefulMod.config.flightInertiaCancellation  && player.capabilities.isFlying) {
			final GameSettings settings = minecraft.gameSettings;
			if (!(GameSettings.isKeyDown(settings.keyBindForward) || GameSettings.isKeyDown(settings.keyBindBack) || GameSettings.isKeyDown(settings.keyBindLeft) || GameSettings.isKeyDown(settings.keyBindRight))) {
				player.motionX = player.motionZ = 0;
			}
		}

		stepAssistHelper.update(player);
	}

	private void debugFeedBack() {
		final ITextComponent tag = new TextComponentTranslation("debug.prefix");
		final ITextComponent text = new TextComponentTranslation("key.usefulmod.reload_audio");
		tag.setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true));
		final ITextComponent message = new TextComponentString("").appendSibling(tag).appendText(" ").appendSibling(text);
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(message);
	}

	@Override
	public String getName() {
		return "@NAME@";
	}

	@Override
	public String getVersion() {
		return "@VERSION@";
	}

	@Override
	public Class<? extends ConfigPanel> getConfigPanelClass() {
		return GuiConfig.class;
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {
		// noop
	}
}
