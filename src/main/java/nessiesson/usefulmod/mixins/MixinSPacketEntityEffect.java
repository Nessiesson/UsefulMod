package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketEntityEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SPacketEntityEffect.class)
public abstract class MixinSPacketEntityEffect {
	@Inject(method = "processPacket", at = @At("RETURN"))
	private void postProcessPacket(INetHandlerPlayClient handler, CallbackInfo ci) {
		LiteModUsefulMod.stepAssistHelper.update();
	}
}
