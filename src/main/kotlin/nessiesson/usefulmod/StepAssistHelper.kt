package nessiesson.usefulmod

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects

internal class StepAssistHelper {
	fun update(player: EntityPlayer) {
		player.stepHeight = getStepAmount(player)
	}

	private fun getStepAmount(player: EntityPlayer): Float {
		if (!LiteModUsefulMod.config.jumpBoostStepAssist || !player.isPotionActive(MobEffects.JUMP_BOOST)) {
			return 0.6f
		}

		return if (player.isSneaking) {
			0.9f
		} else {
			1.0f + 0.25f * player.getActivePotionEffect(MobEffects.JUMP_BOOST)!!.amplifier
		}
	}
}
