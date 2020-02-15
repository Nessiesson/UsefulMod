package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP {
	@Unique
	final private static String onInstantMine = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;onPlayerDestroyBlock(Lnet/minecraft/util/math/BlockPos;)Z";
	@Unique
	float usefulModBlockHardness;

	@Inject(method = "clickBlock", at = @At(value = "INVOKE", target = onInstantMine, shift = At.Shift.BEFORE))
	private void preInstantMine(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> cir) {
		final World world = Minecraft.getMinecraft().world;
		this.usefulModBlockHardness = world.getBlockState(pos).getBlockHardness(world, pos);
	}

	@Inject(method = "clickBlock", at = @At(value = "INVOKE", target = onInstantMine, shift = At.Shift.AFTER))
	private void postInstantMine(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> cir) {
		if (LiteModUsefulMod.config.miningGhostBlockFix && this.usefulModBlockHardness > 0F) {
			final NetHandlerPlayClient connection = Minecraft.getMinecraft().getConnection();
			if (connection != null) {
				connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, face, EnumHand.MAIN_HAND, 0F, 0F, 0F));
			}
		}
	}

	@ModifyConstant(method = "onPlayerDamageBlock", constant = @Constant(intValue = 5, ordinal = 1))
	private int postBlockMine(int blockHitDelay) {
		return LiteModUsefulMod.config.clickBlockMining ? 0 : 5;
	}
}
