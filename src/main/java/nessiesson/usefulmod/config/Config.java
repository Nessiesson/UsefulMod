package nessiesson.usefulmod.config;

import com.google.gson.annotations.Expose;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "usefulmod")
public class Config implements Exposable {
	@Expose
	public boolean shulkerBoxDisplay = true;
	@Expose
	public boolean deathLocation = true;
	@Expose
	public boolean miningGhostBlockFix = true;
	@Expose
	public boolean clickBlockMining = false;
	@Expose
	public boolean centeredPlants = true;
	@Expose
	public boolean clearLava = true;
	@Expose
	public boolean smoothItemMovement = true;
	@Expose
	public boolean showBeaconRange = false;
	@Expose
	public boolean showBlockBreakingParticles = true;
	@Expose
	public boolean jumpBoostStepAssist = true;
	@Expose
	public boolean respawnOnDeath = false;
	@Expose
	public boolean instantDoubleRetraction = false;
	@Expose
	public boolean noFall = false;
	@Expose
	public boolean alwaysSingleplayerCheats = true;
	@Expose
	public boolean showScoreboard = true;
	@Expose
	public boolean sortEnchantmentTooltip = true;
	@Expose
	public boolean insaneBlockBreakingParticles = true;
	@Expose
	public boolean showDeathAnimation = false;
	@Expose
	public boolean showServerNames = false;
	@Expose
	public boolean showTeamMenu = false;
	@Expose
	public boolean showSnowDripParticles = true;
	@Expose
	public boolean extendedChat = true;
	@Expose
	public boolean showItemAttributes = false;
	@Expose
	public boolean showOneBossBar = true;
	@Expose
	public boolean noTimeout = false;
	@Expose
	public boolean craftingHax = true;

	public Config() {
		LiteLoader.getInstance().registerExposable(this, null);
	}

	void save() {
		LiteLoader.getInstance().writeConfig(this);
	}
}
