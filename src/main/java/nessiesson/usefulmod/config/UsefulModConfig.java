package nessiesson.usefulmod.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "usefulmod.json")
public class UsefulModConfig implements Exposable {
	public static UsefulModConfig instance;
	@Expose
	@SerializedName("shulkerbox_display")
	public boolean isShulkerBoxDisplayEnabled = true;
	@Expose
	@SerializedName("narrator_shortcut")
	public boolean isNarratorShortcutEnabled = false;
	@Expose
	@SerializedName("death_location")
	public boolean isDeathLocationEnabled = true;
	@Expose
	@SerializedName("spectator_to_spectator")
	public boolean isSpectatorToSpectatorEnabled = true;
	@Expose
	@SerializedName("mining_ghostblock_fix")
	public boolean isMiningGhostblockFixEnabled = true;
	@Expose
	@SerializedName("test")
	public boolean isTestEnabled = false;
	@Expose
	@SerializedName("centered_plants")
	public boolean isCenteredPlantsEnabled = true;

	public UsefulModConfig() {
		if (instance == null) {
			instance = this;
			LiteLoader.getInstance().registerExposable(instance, null);
		}
	}

	public static void save() {
		LiteLoader.getInstance().writeConfig(instance);
	}
}
