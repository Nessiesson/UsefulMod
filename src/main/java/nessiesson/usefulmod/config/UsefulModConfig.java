package nessiesson.usefulmod.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "usefulmod.json")
public class UsefulModConfig implements Exposable {
	private static UsefulModConfig instance;
	@Expose
	@SerializedName("shulkerbox_display")
	public boolean isShulkerBoxDisplayEnabled = false;
	@Expose
	@SerializedName("narrator_shortcut")
	public boolean isNarratorShortcutEnabled = false;
	@Expose
	@SerializedName("death_location")
	public boolean isDeathLocationEnabled = false;
	@Expose
	@SerializedName("spectator_to_spectator")
	public boolean isSpectatorToSpectatorEnabled = false;
	@Expose
	@SerializedName("mining_ghostblock_fix")
	public boolean isMiningGhostblockFixEnabled = false;
	@Expose
	@SerializedName("test")
	public boolean isTestEnabled = false;
	@Expose
	@SerializedName("centered_plants")
	public boolean isCenteredPlantsEnabled = false;
	@Expose
	@SerializedName("flight_inertia cancellation")
	public boolean isFlightInertiaCancellationEnabled = false;
	@Expose
	@SerializedName("packed_ice_translucent")
	public boolean isTranslucentPackedIceEnabled = false;
	@Expose
	@SerializedName("clear_lava")
	public boolean isClearLavaEnabled = false;
	@Expose
	@SerializedName("smooth_item_movement")
	public boolean isSmoothItemMovementEnabled = false;

	public UsefulModConfig() {
		if (instance == null) {
			instance = this;
			LiteLoader.getInstance().registerExposable(instance, null);
		}
	}

	static void save() {
		LiteLoader.getInstance().writeConfig(instance);
	}
}
