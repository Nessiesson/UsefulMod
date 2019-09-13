package nessiesson.usefulmod.config;

import com.google.gson.annotations.Expose;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "usefulmod")
public class Config implements Exposable {
	@Expose
	public boolean snapAim = false;
	@Expose
	public boolean shulkerBoxDisplay = false;
	@Expose
	public boolean deathLocation = false;
	@Expose
	public boolean miningGhostBlockFix = false;
	@Expose
	public boolean clickBlockMining = false;
	@Expose
	public boolean centeredPlants = false;
	@Expose
	public boolean clearLava = false;
	@Expose
	public boolean smoothItemMovement = false;
	@Expose
	public boolean showBeaconRange = false;
	@Expose
	public boolean showBlockBreakingParticles = true;
	@Expose
	public boolean jumpBoostStepAssist = false;
	@Expose
	public boolean respawnOnDeath = false;
	@Expose
	public boolean instantDoubleRetraction = false;
	@Expose
	public boolean noFall = false;
	@Expose
	public boolean alwaysSingleplayerCheats = false;
	@Expose
	public boolean showScoreboard = true;
	@Expose
	public boolean sortEnchantmentTooltip = false;
	@Expose
	public boolean insaneBlockBreakingParticles = false;
	@Expose
	public boolean showDeathAnimation = true;
	@Expose
	public boolean showServerNames = true;
	@Expose
	public boolean showTeamMenu = true;
	@Expose
	public boolean showSnowDripParticles = false;
	@Expose
	public boolean extendedChat = false;
	@Expose
	public boolean showItemAttributes = true;
	@Expose
	public boolean showOneBossBar = false;
	@Expose
	public boolean noTimeout = false;
	@Expose
	public boolean craftingHax = false;
	@Expose
	public boolean noLight = false;
	@Expose
	public boolean stepAssist = false;
	@Expose
	public boolean flightInertiaCancellation = false;
	@Expose
	public boolean elytraHax = false;
	@Expose
	public boolean alwaysRenderTileEntities = false;
	@Expose
	public boolean alwaysRenderEntities = false;

	public Config() {
		LiteLoader.getInstance().registerExposable(this, null);
	}

	void save() {
		LiteLoader.getInstance().writeConfig(this);
	}
}
