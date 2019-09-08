package nessiesson.usefulmod.config

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mumfrey.liteloader.core.LiteLoader
import com.mumfrey.liteloader.modconfig.ConfigStrategy
import com.mumfrey.liteloader.modconfig.Exposable
import com.mumfrey.liteloader.modconfig.ExposableOptions

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "usefulmod")
object Config : Exposable {
	@Expose
	@SerializedName("poop")
	var isShulkerBoxDisplayEnabled = true
	@Expose
	var isNarratorShortcutEnabled = false
	@Expose
	var isDeathLocationEnabled = true
	@Expose
	var isSpectatorToSpectatorEnabled = true
	@Expose
	var isMiningGhostblockFixEnabled = false
	@Expose
	var isTestEnabled = false
	@Expose
	var isCenteredPlantsEnabled = false
	@Expose
	var isTranslucentPackedIceEnabled = false
	@Expose
	var isClearLavaEnabled = false
	@Expose
	var isSmoothItemMovementEnabled = true
	@Expose
	var isNoSlimeSlowdownEnabled = false
	@Expose
	var isShowBeaconRangeEnabled = false
	@Expose
	var showBlockBreakingParticles = true
	@Expose
	var isJumpBoostStepAssistEnabled = false
	@Expose
	var isRespawnOnDeathEnabled = false
	@Expose
	var isOneEightPistonsEnabled = false
	@Expose
	var isNoFallEnabled = false
	@Expose
	var isAlwaysSingleplayerCheatedEnabled = true
	@Expose
	var isScoreboardHidden = false
	@Expose
	var sortEnchantmentTooltip = false
	@Expose
	var insaneBlockBreakingParticles = false
	@Expose
	var hideDeathAnimations = false
	@Expose
	var hideServerNames = false
	@Expose
	var hideTeams = false

	init {
		LiteLoader.getInstance().registerExposable(this, null)
	}

	fun save() {
		LiteLoader.getInstance().writeConfig(this)
	}
}
