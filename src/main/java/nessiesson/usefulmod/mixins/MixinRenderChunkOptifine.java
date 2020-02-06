package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.renderer.chunk.RenderChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderChunk.class)
public abstract class MixinRenderChunkOptifine {
	// @Dynamic
	@Inject(method = "isPlayerUpdate", at = @At("HEAD"), cancellable = true, remap = false)
	private void stopDumbBlinking(CallbackInfoReturnable<Boolean> ci) {
		ci.setReturnValue(LiteModUsefulMod.config.smoothPistonOptifineStopBlink || ci.getReturnValue());
	}
}
