package nessiesson.usefulmod.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockDragonEgg.class)
public abstract class MixinBlockDragonEgg extends Block {
	protected MixinBlockDragonEgg(Material material) {
		super(material);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void onInit(CallbackInfo ci) {
		this.setCreativeTab(CreativeTabs.REDSTONE);
	}
}
