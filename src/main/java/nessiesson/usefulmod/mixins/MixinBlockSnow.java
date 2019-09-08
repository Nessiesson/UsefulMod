package nessiesson.usefulmod.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(BlockSnow.class)
public abstract class MixinBlockSnow extends Block {
	protected MixinBlockSnow(Material materialIn) {
		super(materialIn);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(10) == 0 && worldIn.getBlockState(pos.down()).isTopSolid()) {
			Material material = worldIn.getBlockState(pos.down(2)).getMaterial();

			if (!material.blocksMovement() && !material.isLiquid()) {
				final double x = pos.getX() + rand.nextDouble();
				final double y = pos.getY() - 1.05D;
				final double z = pos.getZ() + rand.nextDouble();
				worldIn.spawnParticle(EnumParticleTypes.END_ROD, x, y, z, 0D, -0.06D, 0D);
			}
		}
	}
}