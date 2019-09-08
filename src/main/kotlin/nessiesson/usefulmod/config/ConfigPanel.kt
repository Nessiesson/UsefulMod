package nessiesson.usefulmod.config

import com.mumfrey.liteloader.client.gui.GuiCheckbox
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel
import com.mumfrey.liteloader.modconfig.ConfigPanelHost

class ConfigPanel : AbstractConfigPanel() {
	override fun addOptions(host: ConfigPanelHost) {
		val SPACING = 16
		var controlId = 0

		// I feel like there is a cleaner way to do this, lol.
		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Shulkerbox display."), { control ->
			run {
				control.checked = !control.checked
				Config.isShulkerBoxDisplayEnabled = control.checked
			}
		}).checked = Config.isShulkerBoxDisplayEnabled

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Print coordinates on death."), { control -> Config.isDeathLocationEnabled = !control.checked }).checked = Config.isDeathLocationEnabled
		/*
		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable smooth item movement."), { control -> Config.setIsSmoothItemMovementEnabled(control.checked = !control.checked) }).checked = Config.getIsSmoothItemMovementEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Let spectators teleport to spectators."), { control -> Config.setIsSpectatorToSpectatorEnabled(control.checked = !control.checked) }).checked = Config.getIsSpectatorToSpectatorEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable centered plants."), { control ->
			Config.setIsCenteredPlantsEnabled(control.checked = !control.checked)
			Minecraft.getMinecraft().renderGlobal.loadRenderers()
		}).checked = Config.getIsCenteredPlantsEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable client-side ghost block mining fix."), { control -> Config.setIsMiningGhostblockFixEnabled(control.checked = !control.checked) }).checked = Config.getIsMiningGhostblockFixEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable clear lava."), { control -> Config.setIsClearLavaEnabled(control.checked = !control.checked) }).checked = Config.getIsClearLavaEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Show beacon range."), { control -> Config.setIsShowBeaconRangeEnabled(control.checked = !control.checked) }).checked = Config.getIsShowBeaconRangeEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Show block breaking particles."), { control ->
			control.checked = !control.checked
			Config.showBlockBreakingParticles = control.checked
		}).checked = Config.showBlockBreakingParticles

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable stepassist when jump boost is active."), { control -> Config.setIsJumpBoostStepAssistEnabled(control.checked = !control.checked) }).checked = Config.getIsJumpBoostStepAssistEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable narrator shortcut."), { control -> Config.setIsNarratorShortcutEnabled(control.checked = false) }).checked = Config.getIsNarratorShortcutEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Test option please ignore."), { control -> Config.setIsTestEnabled(control.checked = !control.checked) }).checked = Config.getIsTestEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Immediately respawn on death."), { control -> Config.setIsRespawnOnDeathEnabled(control.checked = !control.checked) }).checked = Config.getIsRespawnOnDeathEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Always have cheats enabled in singleplayer."), { control -> Config.setIsAlwaysSingleplayerCheatedEnabled(control.checked = !control.checked) }).checked = Config.getIsAlwaysSingleplayerCheatedEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable translucent packed ice. [Experimental]"), { control ->
			Config.setIsTranslucentPackedIceEnabled(control.checked = !control.checked)
			Minecraft.getMinecraft().renderGlobal.loadRenderers()
		}).checked = Config.getIsTranslucentPackedIceEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable no slime movement slowdowns. [Experimental]"), { control ->
			Config.setIsNoSlimeSlowdownEnabled(control.checked = !control.checked)
			Blocks.SLIME_BLOCK.slipperiness = if (Config.getIsNoSlimeSlowdownEnabled()) 0.6f else 0.8f
		}).checked = Config.getIsNoSlimeSlowdownEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, TextFormatting.BOLD.toString() + "[SUPER EXPERIMENTAL] " + TextFormatting.RESET + "Enable 1.8(?) pistons [Experimental]"), { control -> Config.setIsOneEightPistonsEnabled(control.checked = !control.checked) }).checked = Config.getIsOneEightPistonsEnabled()

		this.addControl(GuiCheckbox(controlId, 0, SPACING * controlId++, "Disable fall damage. (Will still take damage when hitting wall with elytra.) [Experimental]"), { control -> Config.setIsNoFallEnabled(control.checked = !control.checked) }).checked = Config.getIsNoFallEnabled()
		 */
	}

	override fun getPanelTitle(): String {
		return "UsefulConfiguration"
	}

	override fun onPanelHidden() {
		Config.save()
	}
}
