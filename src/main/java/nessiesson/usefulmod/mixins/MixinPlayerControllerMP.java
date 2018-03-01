package nessiesson.usefulmod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP {
	// Client-side fix for instant mining ghost blocks.
	@Inject(method = "clickBlock", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
			target = "Lnet/minecraft/block/Block;onBlockClicked(Lnet/minecraft/world/World;"
					+ "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/EntityPlayer;)V"))
	private void onInstantMine(BlockPos loc, EnumFacing face, CallbackInfoReturnable<Boolean> cir) {
		NetHandlerPlayClient connection = Minecraft.getMinecraft().getConnection();
		connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, loc, face));
	}
}
