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
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
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
	public static final Config config = new Config();
	public static final List<TimedKeyBinding> tapeMouseable = new ArrayList<>();
	public static final KeyBinding highlightEntities = new KeyBinding("key.usefulmod.highlight_entities", Keyboard.KEY_LMENU, "UsefulMod");
	private static final KeyBinding reloadAudioEngineKey = new KeyBinding("key.usefulmod.reload_audio", Keyboard.KEY_B, "UsefulMod");
	private static final StepAssistHelper stepAssistHelper = new StepAssistHelper();
	private static final List<KeyBinding> keybinds = new ArrayList<>();
	private String originalTitle;

	@Override
	public void init(File configPath) {
		final List<Field> fields = Arrays.asList(Config.class.getFields());
		fields.sort(Comparator.comparing(Field::getName));
		for (Field f : fields) {
			keybinds.add(new KeyBinding(f.getName(), Keyboard.KEY_NONE, "UsefulMod"));
		}

		for (KeyBinding key : keybinds) {
			LiteLoader.getInput().registerKeyBinding(key);
		}

		LiteLoader.getInput().registerKeyBinding(highlightEntities);
		LiteLoader.getInput().registerKeyBinding(reloadAudioEngineKey);
		this.originalTitle = Display.getTitle();
		this.updateTitle();
		//ClientCommandHandler.instance.registerCommand(new CommandTapeMouse());
	}

	@Override
	public void onTick(Minecraft mc, float partialTicks, boolean inGame, boolean clock) {
		if (!inGame) {
			return;
		}

		for (KeyBinding key : keybinds) {
			if (key.isPressed()) {
				try {
					final Field field = Config.class.getField(key.getKeyDescription());
					final boolean state = !field.getBoolean(config);
					field.setBoolean(config, state);
					mc.ingameGUI.setOverlayMessage(String.format("%s %s.", field.getName(), state ? "enabled" : "disabled"), false);
				} catch (NoSuchFieldException | IllegalAccessException ignored) {
					// noop
				}
			}
		}

		if (reloadAudioEngineKey.isPressed()) {
			((ISoundHandler) mc.getSoundHandler()).getSoundManager().reloadSoundSystem();
			this.debugFeedBack();
		}

		final EntityPlayerSP player = mc.player;
		if (config.noFall && player.fallDistance > 2F) {
			player.connection.sendPacket(new CPacketPlayer(true));
		}

		if (LiteModUsefulMod.config.flightInertiaCancellation && player.capabilities.isFlying) {
			final GameSettings settings = mc.gameSettings;
			if (!(GameSettings.isKeyDown(settings.keyBindForward) || GameSettings.isKeyDown(settings.keyBindBack) || GameSettings.isKeyDown(settings.keyBindLeft) || GameSettings.isKeyDown(settings.keyBindRight))) {
				player.motionX = player.motionZ = 0;
			}
		}

		stepAssistHelper.update(player);
		if (!tapeMouseable.isEmpty()) {
			if (mc.currentScreen instanceof GuiMainMenu) {
				this.drawStringInCorner("TM paused");
			} else {
				for (TimedKeyBinding key : tapeMouseable) {
					this.drawStringInCorner(String.format("%s %d / %d\n", key.getKeyDescription().replaceFirst("^key\\.", ""), key.currentTick, key.tickDelay));
				}
			}
		}
	}

	private void debugFeedBack() {
		final ITextComponent tag = new TextComponentTranslation("debug.prefix");
		final ITextComponent text = new TextComponentTranslation("key.usefulmod.reload_audio");
		tag.setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true));
		final ITextComponent message = new TextComponentString("").appendSibling(tag).appendText(" ").appendSibling(text);
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(message);
	}

	private void drawStringInCorner(String msg) {
		final FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.5, 0.5, 0.5);
		font.drawString(msg, 5, 5, 0xFFFFFF, true);
		GlStateManager.popMatrix();
	}

	private void updateTitle() {
		Display.setTitle(this.originalTitle + " - " + Minecraft.getMinecraft().getSession().getUsername());
	}

	@Override
	public void onPostRenderEntities(float partialTicks) {
		AreaSelectionRenderer.render(partialTicks);
	}

	@Override
	public void onJoinGame(INetHandler handler, SPacketJoinGame packet, ServerData data, RealmsServer realms) {
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
