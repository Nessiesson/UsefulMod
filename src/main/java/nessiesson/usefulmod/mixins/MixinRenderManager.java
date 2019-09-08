package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager {
	@Inject(method = "renderEntityStatic", at = @At("HEAD"), cancellable = true)
	private void hideDeathAnimation(Entity entity, float f, boolean z, CallbackInfo ci) {
		if (LiteModUsefulMod.Companion.getConfig().showDeathAnimation || !(entity instanceof EntityLivingBase)) {
			return;
		}
		final EntityLivingBase mob = (EntityLivingBase) entity;
		if (mob.deathTime > 0) {
			ci.cancel();
		}
	}
}
