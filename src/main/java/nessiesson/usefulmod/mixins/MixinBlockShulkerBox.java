package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.ShulkerBoxDisplay;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BlockShulkerBox.class)
public abstract class MixinBlockShulkerBox {
	@Inject(method = "addInformation", at = @At(value = "HEAD"))
	private void postGetTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn, CallbackInfo ci) {
		ShulkerBoxDisplay.addShulkerBoxTooltip(stack, tooltip);
	}
}
