package nessiesson.usefulmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

class StepAssistHelper {
	void update(EntityPlayer player) {
		player.stepHeight = getStepAmount(player);
	}

	private float getStepAmount(EntityPlayer player) {
		if (!LiteModUsefulMod.config.isJumpBoostStepAssistEnabled || !player.isPotionActive(MobEffects.JUMP_BOOST)) {
			return 0.6F;
		}

		if (player.isSneaking()) {
			return 0.9F;
		}

		return 1.0F + 0.25F * player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
	}
}
