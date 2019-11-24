package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {"net/minecraft/client/renderer/color/BlockColors$4", "net/minecraft/client/renderer/color/BlockColors$5"})
public abstract class MixinRainbowLeafBlockColors {
	@Inject(method = "colorMultiplier(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;I)I", at = @At("HEAD"), cancellable = true)
	private void rainbowLeaves(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex, CallbackInfoReturnable<Integer> cir) {
		if (!LiteModUsefulMod.config.showRainbowLeaves || pos == null) {
			return;
		}

		int red = pos.getX() * 16 + pos.getY() * 8;
		if ((red & 256) != 0) {
			red = 255 - (red & 255);
		}
		red &= 255;

		int blue = pos.getY() * 16 + pos.getZ() * 8;
		if ((blue & 256) != 0) {
			blue = 255 - (blue & 255);
		}
		blue ^= 255;

		int green = pos.getX() * 8 + pos.getZ() * 16;
		if ((green & 256) != 0) {
			green = 255 - (green & 255);
		}
		green &= 255;

		cir.setReturnValue(red << 16 | blue << 8 | green);
	}
}
