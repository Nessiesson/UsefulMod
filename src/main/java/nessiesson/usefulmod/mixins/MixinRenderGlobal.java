package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal {
	@Inject(method = "isOutlineActive", at = @At("HEAD"), cancellable = true)
	private void highlightAllEntitites(Entity entityIn, Entity viewer, ICamera camera, CallbackInfoReturnable<Boolean> cir) {
		final EntityPlayerSP player = Minecraft.getMinecraft().player;
		if (player.isSpectator() && LiteModUsefulMod.highlightEntities.isKeyDown()) {
			cir.setReturnValue((entityIn instanceof EntityLivingBase || entityIn instanceof EntityMinecart) && (entityIn.ignoreFrustumCheck || camera.isBoundingBoxInFrustum(entityIn.getEntityBoundingBox()) || entityIn.isRidingOrBeingRiddenBy(player)));
		}
	}

	@Redirect(method = "renderEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderManager;shouldRender(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z"))
	private boolean alwaysRenderTileEntities(RenderManager manager, Entity entity, ICamera camera, double camX, double camY, double camZ) {
		return LiteModUsefulMod.config.alwaysRenderEntities || manager.shouldRender(entity, camera, camX, camY, camZ);
	}
}
