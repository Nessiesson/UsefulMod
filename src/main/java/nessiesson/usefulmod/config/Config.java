package nessiesson.usefulmod.config;

import com.google.gson.annotations.Expose;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "usefulmod")
public class Config implements Exposable {
	@Expose
	public boolean alwaysDay = false;
	@Expose
	public boolean alwaysPickBlockMaxStack = false;
	@Expose
	public boolean alwaysRenderTileEntities = false;
	@Expose
	public boolean alwaysSingleplayerCheats = false;
	@Expose
	public boolean clickBlockMining = false;
	@Expose
	public boolean clientEntityUpdates = true;
	@Expose
	public boolean craftingHax = false;
	@Expose
	public boolean deathLocation = false;
	@Expose
	public boolean extendedChat = false;
	@Expose
	public boolean fixBlock36Particles = false;
	@Expose
	public boolean flightInertiaCancellation = false;
	@Expose
	public boolean instantDoubleRetraction = false;
	@Expose
	public boolean jumpBoostStepAssist = false;
	@Expose
	public boolean miningGhostBlockFix = false;
	@Expose
	public boolean noFall = false;
	@Expose
	public boolean respawnOnDeath = false;
	@Expose
	public boolean showBeaconRange = false;
	@Expose
	public boolean showBlockBreakingParticles = true;
	@Expose
	public boolean showCenteredPlants = false;
	@Expose
	public boolean showClearLava = false;
	@Expose
	public boolean showDeathAnimation = true;
	@Expose
	public boolean showExplosion = false;
	@Expose
	public boolean showGuiBackGround = true;
	@Expose
	public boolean showHand = true;
	@Expose
	public boolean showIdealToolMarker = false;
	@Expose
	public boolean showInsaneBlockBreakingParticles = false;
	@Expose
	public boolean showItemAttributes = true;
	@Expose
	public boolean showItemFrameFrame = true;
	@Expose
	public boolean showLessBlinkyPistonCanGhostBlock = false;
	@Expose
	public boolean showOneBossBar = false;
	@Expose
	public boolean showPotionShift = true;
	@Expose
	public boolean showRain = true;
	@Expose
	public boolean showRainbowLeaves = false;
	@Expose
	public boolean showServerNames = true;
	@Expose
	public boolean showSmoothWaterLighting = false;
	@Expose
	public boolean showSneakEyeHeight = false;
	@Expose
	public boolean showSnowDripParticles = false;
	@Expose
	public boolean showTeamMenu = true;
	@Expose
	public boolean shulkerBoxDisplay = false;
	@Expose
	public boolean smoothItemMovement = false;
	@Expose
	public boolean sortEnchantmentTooltip = false;
	@Expose
	public boolean stepAssist = false;
	@Expose
	public boolean unstepAssist = false;
	@Expose
	public boolean zzTempBlackSky = false;
	@Expose
	public boolean zzTempMasaFoVFix = false;

	public Config() {
		LiteLoader.getInstance().registerExposable(this, null);
	}

	void save() {
		LiteLoader.getInstance().writeConfig(this);
	}
}
