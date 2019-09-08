package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "net/minecraft/network/NetworkManager$5")
public abstract class MixinNetworkManager$5 {
	@ModifyConstant(method = "initChannel", constant = @Constant(intValue = 30), remap = false)
	private static int noTimeout(int timeoutSeconds) {
		return 0;
	}
}
