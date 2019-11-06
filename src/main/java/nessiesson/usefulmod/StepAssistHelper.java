package nessiesson.usefulmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class StepAssistHelper {
	private boolean wasOnGround = false;

	void update(EntityPlayer player) {
		player.stepHeight = getStepAmount(player);
		this.stepDown(player);
		this.wasOnGround = player.onGround;
	}

	private void stepDown(EntityPlayer player) {
		if (!LiteModUsefulMod.config.unstepAssist && !wasOnGround && player.onGround && player.motionY > 0D) {
			return;
		}

		final AxisAlignedBB range = player.getEntityBoundingBox().expand(0D, -1.5D, 0D).contract(0D, player.height, 0D);
		if (!player.world.collidesWithAnyBlock(range)) {
			return;
		}

		final List<AxisAlignedBB> collisions = player.world.getCollisionBoxes(player, range);
		if (!collisions.isEmpty()) {
			player.setPositionAndUpdate(player.posX, Collections.max(collisions, Comparator.comparingDouble(f -> f.maxY)).maxY, player.posZ);
		}
	}

	private float getStepAmount(EntityPlayer player) {
		if (LiteModUsefulMod.config.stepAssist) {
			return player.isSneaking() ? 0.9F : 1.5F;
		}

		if (!LiteModUsefulMod.config.jumpBoostStepAssist || !player.isPotionActive(MobEffects.JUMP_BOOST)) {
			return 0.6F;
		}

		// getActivePotionEffect() is never null since we checked for that just above with isPotionAction().
		//noinspection ConstantConditions
		return player.isSneaking() ? 0.9F : 1F + 0.25F * player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
	}
}
