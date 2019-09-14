package nessiesson.usefulmod.mixins;

import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ClientBrandRetriever.class, priority = 1001, remap = false)
public abstract class MixinClientBrandRetriever {
	@Inject(method = "getClientModName", at = @At("HEAD"), cancellable = true)
	private static void pureVanilla(CallbackInfoReturnable<String> cir) {
		cir.setReturnValue("vanilla++");
	}
}
