package nessiesson.usefulmod.mixins;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {
	@Shadow
	protected abstract void dispatchPacket(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>>[] future);

	@Redirect(method = "flushOutboundQueue", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkManager;dispatchPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V"))
	private void mc132381(NetworkManager manager, Packet<?> packet, GenericFutureListener<? extends Future<? super Void>>[] future) {
		if (packet != null) {
			this.dispatchPacket(packet, future);
		}
	}
}
