package nessiesson.usefulmod.config;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import nessiesson.usefulmod.LiteModUsefulMod;

public class UsefulModConfigPanel extends AbstractConfigPanel {
	@Override
	protected void addOptions(ConfigPanelHost host) {
		final LiteModUsefulMod mod = host.<LiteModUsefulMod>getMod();

		// I feel like there is a cleaner way to do this, lol.
		this.addControl(new GuiCheckbox(0, 0, 0, "Shulkerbox display."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isShulkerBoxDisplayEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isShulkerBoxDisplayEnabled;

		this.addControl(new GuiCheckbox(1, 0, 16, "Enable narrator shortcut."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isNarratorShortcutEnabled = control.checked = false;
			}
		}).checked = LiteModUsefulMod.config.isNarratorShortcutEnabled;

		this.addControl(new GuiCheckbox(2, 0, 32, "Print coordinates on death."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isDeathLocationEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isDeathLocationEnabled;

		this.addControl(new GuiCheckbox(3, 0, 48, "Let spectators teleport to spectators."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isSpectatorToSpectatorEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isSpectatorToSpectatorEnabled;

		this.addControl(new GuiCheckbox(4, 0, 64, "Enable client-side ghost block mining fix."), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isMiningGhostblockFixEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isMiningGhostblockFixEnabled;

		this.addControl(new GuiCheckbox(5, 0, 80, "Test option please ignore"), new ConfigOptionListener<GuiCheckbox>() {
			@Override
			public void actionPerformed(GuiCheckbox control) {
				LiteModUsefulMod.config.isTestEnabled = control.checked = !control.checked;
			}
		}).checked = LiteModUsefulMod.config.isTestEnabled;
	}

	@Override
	public String getPanelTitle() {
		return "UsefulConfiguration";
	}

	@Override
	public void onPanelHidden() {
		LiteModUsefulMod.config.save();
	}
}
