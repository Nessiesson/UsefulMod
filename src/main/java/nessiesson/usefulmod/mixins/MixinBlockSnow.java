package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.MixinCode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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
		MixinCode.onBlockSnowRandomDisplayTick(worldIn, pos, rand);
	}
}