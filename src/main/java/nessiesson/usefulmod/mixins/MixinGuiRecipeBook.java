package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.MixinCode;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.crafting.IRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiRecipeBook.class)
abstract class MixinGuiRecipeBook {
	@Inject(method = "handleRecipeClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;markDirty()V", shift = At.Shift.AFTER))
	private void haxCrafting(IRecipe recipe, List<Slot> slots, int id, InventoryCraftResult result, CallbackInfo ci) {
		MixinCode.handleHaxCrafting(id);
	}
}
