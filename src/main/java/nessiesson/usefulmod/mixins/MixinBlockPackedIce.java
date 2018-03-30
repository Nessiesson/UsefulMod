package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockPackedIce.class)
public abstract class MixinBlockPackedIce extends Block {
	protected MixinBlockPackedIce(Material materialIn) {
		super(materialIn);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		if(LiteModUsefulMod.config.isTranslucentPackedIceEnabled) {
			return BlockRenderLayer.TRANSLUCENT;
		} else {
			return super.getBlockLayer();
		}
	}
}
