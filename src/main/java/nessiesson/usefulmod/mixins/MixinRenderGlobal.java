package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
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

	@Redirect(method = "playEvent", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;playSound(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V"), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSoundType()Lnet/minecraft/block/SoundType;")))
	private void noBreakSound(WorldClient worldClient, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay) {
		if (!LiteModUsefulMod.config.showExplosion) {
			worldClient.playSound(pos, soundIn, category, volume, pitch, distanceDelay);
		}
	}
}
