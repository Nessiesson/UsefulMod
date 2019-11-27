package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(targets = {"net/minecraft/client/renderer/color/BlockColors$4", "net/minecraft/client/renderer/color/BlockColors$5"})
public abstract class MixinRainbowLeafBlockColors {
	@Inject(method = "colorMultiplier(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;I)I", at = @At("HEAD"), cancellable = true)
	private void rainbowLeaves(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex, CallbackInfoReturnable<Integer> cir) {
		if (!LiteModUsefulMod.config.showRainbowLeaves || pos == null) {
			return;
		}

		final int sc = 1024;
		final float hue = this.dist(pos.getX(), 32 * pos.getY(), pos.getX() + pos.getZ()) % sc / sc;
		cir.setReturnValue(Color.HSBtoRGB(hue, 0.7F, 1F));
	}

	private float dist(int x, int y, int z) {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
}
