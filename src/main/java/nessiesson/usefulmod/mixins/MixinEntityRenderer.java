package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
	@Unique
	private float usefulmodEyeHeight;
	@Unique
	private float usefulmodLastEyeHeight;

	@Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
	private void hideHand(float partialTicks, int pass, CallbackInfo ci) {
		if (!LiteModUsefulMod.config.showHand) {
			ci.cancel();
		}
	}

	@SuppressWarnings("ConstantConditions")
	@Inject(method = "updateRenderer", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/multiplayer/WorldClient;getLightBrightness(Lnet/minecraft/util/math/BlockPos;)F"))
	private void updateEyeHeights(CallbackInfo ci) {
		this.usefulmodLastEyeHeight = this.usefulmodEyeHeight;
		this.usefulmodEyeHeight += (Minecraft.getMinecraft().getRenderViewEntity().getEyeHeight() - this.usefulmodEyeHeight) * 0.5F;
	}

	@SuppressWarnings("ConstantConditions")
	@Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 4))
	private void eyeHeightTranslate(float x, float y, float z, float partialTicks) {
		if (LiteModUsefulMod.config.showSneakEyeHeight) {
			y += Minecraft.getMinecraft().getRenderViewEntity().getEyeHeight() - this.usefulmodLastEyeHeight - (this.usefulmodEyeHeight - this.usefulmodLastEyeHeight) * partialTicks;
		}

		GlStateManager.translate(x, y, z);
	}
}
