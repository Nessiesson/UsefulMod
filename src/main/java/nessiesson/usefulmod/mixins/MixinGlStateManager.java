package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.config.Config;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GlStateManager.class)
public class MixinGlStateManager {
	@ModifyVariable(method = "setFogDensity", at = @At("HEAD"), argsOnly = true)
	private static float adjustFogDensity(float fogDensity) {
		// In vanilla code, this method is only called with fogdentity = 2.0F when in lava.
		// We're changing the fog density in here to remain compatible with OptiFine.
		if (fogDensity == 2.0F && Config.INSTANCE.isClearLavaEnabled()) {
			return 0.0F;
		} else {
			return fogDensity;
		}
	}
}
