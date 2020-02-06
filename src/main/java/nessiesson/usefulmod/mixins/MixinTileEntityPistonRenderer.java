package nessiesson.usefulmod.mixins;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityPistonRenderer;
import net.minecraft.tileentity.TileEntityPiston;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityPistonRenderer.class)
public abstract class MixinTileEntityPistonRenderer {
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableCull()V"))
	private void cullMovingBlocks() {
		GlStateManager.enableCull();
	}

	@Inject(method = "render", at = @At("RETURN"))
	private void uncullMovingBlocks(TileEntityPiston te, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci) {
		GlStateManager.disableCull();
	}

	@ModifyConstant(method = "render", constant = @Constant(floatValue = 0.25F))
	private float fixShortArm(float value) {
		return 0.5F;
	}

	@ModifyConstant(method = "render", constant = @Constant(floatValue = 1F))
	private float fixPistonBlink(float value) {
		return Float.MAX_VALUE;
	}
}
