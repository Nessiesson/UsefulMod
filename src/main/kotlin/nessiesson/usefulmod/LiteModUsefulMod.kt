package nessiesson.usefulmod

import com.mumfrey.liteloader.Configurable
import com.mumfrey.liteloader.Tickable
import com.mumfrey.liteloader.core.LiteLoader
import nessiesson.usefulmod.mixins.ISoundHandler
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.GameSettings
import net.minecraft.client.settings.KeyBinding
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraft.util.text.Style
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.Display
import java.io.File

class LiteModUsefulMod : Tickable, Configurable {
	private val stepAssistHelper = StepAssistHelper()
	private val reloadAudioEngineKey = KeyBinding("key.usefulmod.reload_audio", Keyboard.KEY_B, "UsefulMod")

	override fun init(configPath: File) {
		LiteLoader.getInput().registerKeyBinding(highlightEntities)
		LiteLoader.getInput().registerKeyBinding(reloadAudioEngineKey)
		Display.setTitle(Display.getTitle() + " - " + Minecraft.getMinecraft().session.username)
	}

	override fun onTick(minecraft: Minecraft, partialTicks: Float, inGame: Boolean, clock: Boolean) {
		if (!inGame) {
			return
		}

		if (reloadAudioEngineKey.isPressed) {
			(minecraft.soundHandler as ISoundHandler).soundManager.reloadSoundSystem()
			this.debugFeedback()
		}

		val player = minecraft.player
		if (config.noFall && player.fallDistance > 2.0f) {
			player.connection.sendPacket(CPacketPlayer(true))
		}

		if (player.capabilities.isFlying) {
			val settings = minecraft.gameSettings
			if (!(GameSettings.isKeyDown(settings.keyBindForward) || GameSettings.isKeyDown(settings.keyBindBack) || GameSettings.isKeyDown(settings.keyBindLeft) || GameSettings.isKeyDown(settings.keyBindRight))) {
				player.motionX = 0.0
				player.motionZ = 0.0
			}
		}

		stepAssistHelper.update(player)
	}

	private fun debugFeedback() {
		val tag = TextComponentTranslation("debug.prefix")
		val text = TextComponentTranslation("key.usefulmod.reload_audio")

		tag.style = Style().setColor(TextFormatting.YELLOW).setBold(true)
		val message = TextComponentString("").appendSibling(tag).appendText(" ").appendSibling(text)

		Minecraft.getMinecraft().ingameGUI.chatGUI.printChatMessage(message)
	}

	override fun getVersion(): String {
		return "@VERSION@"
	}

	override fun getName(): String {
		return "@NAME@"
	}

	override fun getConfigPanelClass(): Class<ConfigPanel> {
		return ConfigPanel::class.java
	}

	override fun upgradeSettings(version: String, configPath: File, oldConfigPath: File) {
		// noop
	}

	companion object {
		val config = Config()
		val highlightEntities = KeyBinding("key.usefulmod.highlight_entities", Keyboard.KEY_LMENU, "UsefulMod")
	}
}
