package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItemFrame;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItemFrame.class)
public abstract class MixinRenderItemFrame {
	@Shadow
	protected abstract void renderItem(EntityItemFrame entity);

	@Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
	private void onDoRender(EntityItemFrame entity, double x, double y, double z, float yaw, float partialTicks, CallbackInfo ci) {
		if (!LiteModUsefulMod.config.showItemFrameFrame) {
			ci.cancel();
			final BlockPos blockpos = entity.getHangingPosition();
			final double dX = (double) blockpos.getX() - entity.posX + x + 0.5D;
			final double dY = (double) blockpos.getY() - entity.posY + y + 0.5D;
			final double dZ = (double) blockpos.getZ() - entity.posZ + z + 0.5D;
			GlStateManager.pushMatrix();
			GlStateManager.translate(dX, dY, dZ);
			GlStateManager.rotate(180F - entity.rotationYaw, 0F, 1F, 0F);
			GlStateManager.translate(0F, 0F, 0.4375F);
			this.renderItem(entity);
			GlStateManager.popMatrix();
		}
	}
}
