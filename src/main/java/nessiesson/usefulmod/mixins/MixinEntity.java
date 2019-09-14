package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class MixinEntity {
	@Shadow
	public abstract boolean isInRangeToRenderDist(double distance);

	@Redirect(method = "isInRangeToRender3d", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isInRangeToRenderDist(D)Z"))
	private boolean alwaysRenderTileEntities(Entity entity, double distance) {
		return LiteModUsefulMod.config.alwaysRenderEntities || this.isInRangeToRenderDist(distance);
	}
}
