package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class MixinBlock {
	@Inject(method = "getOffset", at = @At("HEAD"), cancellable = true)
	private void onGetOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<Vec3d> cir) {
		if (LiteModUsefulMod.config.centeredPlants) {
			cir.setReturnValue(Vec3d.ZERO);
		}
	}

	// fix for MC-2399 by MrGrim https://github.com/mrgrim/MUP/commit/45ddb6fbb93c6f6f3d89ba05de9a6345e108d307
	@ModifyVariable(method = "registerBlocks", name = "flag4", at = @At(value = "STORE", ordinal = 0))
	private static boolean noTranslucentNeighborBrightness(boolean flag4) {
		return false;
	}

	// fix for MC-2399 by MrGrim https://github.com/mrgrim/MUP/commit/45ddb6fbb93c6f6f3d89ba05de9a6345e108d307
	@ModifyVariable(method = "registerBlocks", name = "flag5", at = @At(value = "STORE", ordinal = 0))
	private static boolean noZeroOpacityNeighborBrightness(boolean flag5) {
		return false;
	}
}
