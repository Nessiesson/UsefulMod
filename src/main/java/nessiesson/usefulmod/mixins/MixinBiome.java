package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class MixinBiome {
	@Inject(method = "getSkyColorByTemp", at = @At("HEAD"), cancellable = true)
	private void blackSky(float currentTemperature, CallbackInfoReturnable<Integer> cir) {
		if (LiteModUsefulMod.config.zzTempBlackSky) {
			cir.setReturnValue(0);
		}
	}
}
