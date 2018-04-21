package nessiesson.usefulmod.config;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;

public class UsefulModConfigPanel extends AbstractConfigPanel {
	@Override
	protected void addOptions(ConfigPanelHost host) {
		final int SPACING = 16;
		int controlId = 0;

		// I feel like there is a cleaner way to do this, lol.
		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Shulkerbox display."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isShulkerBoxDisplayEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isShulkerBoxDisplayEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Print coordinates on death."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isDeathLocationEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isDeathLocationEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable smooth item movement."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isSmoothItemMovementEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isSmoothItemMovementEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Let spectators teleport to spectators."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isSpectatorToSpectatorEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isSpectatorToSpectatorEnabled;


		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable centered plants."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isCenteredPlantsEnabled = control.checked = !control.checked;
				Minecraft.getMinecraft().renderGlobal.loadRenderers();
			}
		}).checked = LiteModUsefulMod.config.isCenteredPlantsEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable client-side ghost block mining fix."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isMiningGhostblockFixEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isMiningGhostblockFixEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable clear lava."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isClearLavaEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isClearLavaEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Show beacon range."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isShowBeaconRangeEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isShowBeaconRangeEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Show block breaking particles."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.showBlockBreakingParticles = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.showBlockBreakingParticles;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable stepassist when jump boost is active."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isJumpBoostStepAssistEnabled = control.checked = !control.checked;
				LiteModUsefulMod.stepAssistHelper.update();
			}
		}).checked = LiteModUsefulMod.config.isJumpBoostStepAssistEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable narrator shortcut."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isNarratorShortcutEnabled = control.checked = false;
			}
		}).checked = LiteModUsefulMod.config.isNarratorShortcutEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Test option please ignore."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isTestEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isTestEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable flight inertia cancellation. [Experimental]"), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isFlightInertiaCancellationEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isFlightInertiaCancellationEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable translucent packed ice. [Experimental]"), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isTranslucentPackedIceEnabled = control.checked = !control.checked;
				Minecraft.getMinecraft().renderGlobal.loadRenderers();
			}
		}).checked = LiteModUsefulMod.config.isTranslucentPackedIceEnabled;

		this.addControl(new GuiCheckbox(controlId, 0, SPACING * controlId++, "Enable no slime movement slowdowns. [Experimental]"), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isNoSlimeSlowdownEnabled = control.checked = !control.checked;
				Blocks.SLIME_BLOCK.slipperiness = LiteModUsefulMod.config.isNoSlimeSlowdownEnabled ? 0.6F : 0.8F;
			}
		}).checked = LiteModUsefulMod.config.isNoSlimeSlowdownEnabled;
	}

	@Override
	public String getPanelTitle() {
		return "UsefulConfiguration";
	}

	@Override
	public void onPanelHidden() {
		UsefulModConfig.save();
	}
}
