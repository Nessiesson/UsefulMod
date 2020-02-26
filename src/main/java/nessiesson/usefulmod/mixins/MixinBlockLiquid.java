package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid extends Block {
	protected MixinBlockLiquid(Material material) {
		super(material);
	}

	@Override
	public boolean isTranslucent(IBlockState state) {
		return LiteModUsefulMod.config.smoothWaterLighting || super.isTranslucent(state);
	}
}
