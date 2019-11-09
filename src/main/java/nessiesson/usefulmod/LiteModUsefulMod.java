package nessiesson.usefulmod;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.JoinGameListener;
import com.mumfrey.liteloader.PostRenderListener;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import nessiesson.usefulmod.config.Config;
import nessiesson.usefulmod.config.GuiConfig;
import nessiesson.usefulmod.mixins.ISoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LiteModUsefulMod implements Tickable, Configurable, PostRenderListener, JoinGameListener {
	public static Config config = new Config();
	public static KeyBinding highlightEntities = new KeyBinding("key.usefulmod.highlight_entities", Keyboard.KEY_LMENU, "UsefulMod");
	private static KeyBinding reloadAudioEngineKey = new KeyBinding("key.usefulmod.reload_audio", Keyboard.KEY_B, "UsefulMod");
	private StepAssistHelper stepAssistHelper = new StepAssistHelper();
	private String originalTitle;
	private List<KeyBinding> keybinds = new ArrayList<>();

	@Override
	public void init(File configPath) {
		final List<Field> fields = Arrays.asList(Config.class.getFields());
		fields.sort(Comparator.comparing(Field::getName));
		for (Field f : fields) {
			keybinds.add(new KeyBinding(f.getName(), Keyboard.KEY_NONE, "UsefulMod"));
		}

		for (KeyBinding key : this.keybinds) {
			LiteLoader.getInput().registerKeyBinding(key);
		}

		LiteLoader.getInput().registerKeyBinding(highlightEntities);
		LiteLoader.getInput().registerKeyBinding(reloadAudioEngineKey);
		this.originalTitle = Display.getTitle();
		this.updateTitle();
	}

	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
		if (!inGame) {
			return;
		}

		for(KeyBinding key : this.keybinds) {
			if(key.isPressed()) {
				try {
					final Field field = Config.class.getField(key.getKeyDescription());
					field.setBoolean(config, !field.getBoolean(config));
				} catch (NoSuchFieldException | IllegalAccessException ignored) {
					// noop
				}
			}
		}

		if (reloadAudioEngineKey.isKeyDown()) {
			((ISoundHandler) minecraft.getSoundHandler()).getSoundManager().reloadSoundSystem();
			this.debugFeedBack();
		}

		final EntityPlayerSP player = minecraft.player;
		if (config.noFall && player.fallDistance > 2F) {
			player.connection.sendPacket(new CPacketPlayer(true));
		}

		if (LiteModUsefulMod.config.flightInertiaCancellation && player.capabilities.isFlying) {
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

	private void updateTitle() {
		Display.setTitle(this.originalTitle + " - " + Minecraft.getMinecraft().getSession().getUsername());
	}

	@Override
	public void onPostRenderEntities(float partialTicks) {
		AreaSelectionRenderer.render(partialTicks);
	}

	@Override
	public void onJoinGame(INetHandler netHandler, SPacketJoinGame joinGamePacket, ServerData serverData, RealmsServer realmsServer) {
		this.updateTitle();
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

	@Override
	public void onPostRender(float partialTicks) {
		// noop
	}
}
