package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.BlockPistonBase.EXTENDED;

@Mixin(BlockPistonBase.class)
public abstract class MixinBlockPistonBase {

	@Inject(method = "checkForMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addBlockEvent(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;II)V", ordinal = 1, shift = At.Shift.BEFORE))
	private void onPistonDepower(World worldIn, BlockPos pos, IBlockState state, CallbackInfo ci) {
		if (LiteModUsefulMod.config.isOneEightPistonsEnabled) {
			worldIn.setBlockState(pos, state.withProperty(EXTENDED, Boolean.valueOf(false)), 2); // add this line
		}
	}
}
