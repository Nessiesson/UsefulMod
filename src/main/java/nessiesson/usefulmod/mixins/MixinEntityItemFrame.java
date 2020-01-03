package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.entity.item.EntityItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityItemFrame.class)
public abstract class MixinEntityItemFrame {
	@Inject(method = {"getWidthPixels", "getHeightPixels"}, at = @At("HEAD"), cancellable = true)
	private void adjustHitBox(CallbackInfoReturnable<Integer> cir) {
		if (!LiteModUsefulMod.config.showItemFrameFrame) {
			cir.setReturnValue(5);
		}
	}
}
