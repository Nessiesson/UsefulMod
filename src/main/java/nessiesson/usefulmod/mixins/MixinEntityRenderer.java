package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
	@Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
	private void hideHand(float partialTicks, int pass, CallbackInfo ci) {
		if (!LiteModUsefulMod.config.showHand) {
			ci.cancel();
		}
	}
}
