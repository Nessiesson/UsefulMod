package nessiesson.usefulmod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

public class StepAssistHelper {
	public void update() {
		if (!isFeatureEnabled()) {
			return;
		}
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.player == null) {
			return;
		}
		mc.player.stepHeight = getStepAmount(mc.player);
	}

	private boolean isFeatureEnabled() {
		return LiteModUsefulMod.config.isJumpBoostStepAssistEnabled;
	}

	private float getStepAmount(EntityPlayer player) {
		if (!player.isPotionActive(MobEffects.JUMP_BOOST)) {
			return 0.6F;
		}
		return 1.0F + 0.25F * player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
	}
}
