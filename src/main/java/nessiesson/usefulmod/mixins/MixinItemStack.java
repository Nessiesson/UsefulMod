package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.ItemNBTHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

//Stolen from Vazkii's Quark Forge mod.
@Mixin(ItemStack.class)
public abstract class MixinItemStack {
	@Inject(method = "getTooltip", at = @At(value = "RETURN"))
	private void postGetTooltip(@Nullable EntityPlayer playerIn, ITooltipFlag advanced, CallbackInfoReturnable<List<String>> cir) {
		ItemStack stack = (ItemStack)(Object)this;
		if(!stack.isEmpty() && stack.getItem() instanceof ItemShulkerBox && stack.hasTagCompound()) {
			NBTTagCompound cmp = ItemNBTHelper.getCompound(stack, "BlockEntityTag", true);
			if(cmp != null && cmp.hasKey("Items", 9)) {
				List<String> tooltip = cir.getReturnValue();
				List<String> tooltipCopy = new ArrayList(tooltip);

				for(int i = 1; i < tooltipCopy.size(); i++) {
					String s = tooltipCopy.get(i);
					if(!s.startsWith("\u00a7") || s.startsWith("\u00a7o")) {
						tooltip.remove(tooltip.indexOf(s));
					}
				}

				if(!GuiScreen.isShiftKeyDown()) {
					tooltip.add(1, "Hold Shift to see contents");
				}
			}
		}
	}
}
