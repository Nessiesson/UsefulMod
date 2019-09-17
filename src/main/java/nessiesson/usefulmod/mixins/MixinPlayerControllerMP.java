package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP {
	@Inject(method = "clickBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;onPlayerDestroyBlock(Lnet/minecraft/util/math/BlockPos;)Z", shift = At.Shift.AFTER))
	private void onInstantMine(BlockPos loc, EnumFacing face, CallbackInfoReturnable<Boolean> cir) {
		if (LiteModUsefulMod.config.miningGhostBlockFix) {
			final Minecraft mc = Minecraft.getMinecraft();
			final IBlockState block = mc.world.getBlockState(loc);
			if (block.getBlockHardness(mc.world, loc) > 0.0f) {
				final NetHandlerPlayClient connection = mc.getConnection();
				connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(loc, face, EnumHand.MAIN_HAND, 0f, 0f, 0f));
			}
		}
	}

	@ModifyConstant(method = "onPlayerDamageBlock", constant = @Constant(intValue = 5, ordinal = 1))
	private int postBlockMine(int blockHitDelay) {
		return LiteModUsefulMod.config.clickBlockMining ? 0 : 5;
	}

	@Inject(method = "getBlockReachDistance", at = @At("HEAD"), cancellable = true)
	private void onGetBlockReachDistance(CallbackInfoReturnable<Float> cir) {
		if (LiteModUsefulMod.config.extendedExtendedReachHax) {
			cir.setReturnValue(8F);
		} else if (LiteModUsefulMod.config.extendedReachHax) {
			cir.setReturnValue(5F);
		}
	}
}
