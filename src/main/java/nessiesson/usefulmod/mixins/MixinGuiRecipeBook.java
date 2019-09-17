package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.MixinCode;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.inventory.InventoryCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiRecipeBook.class)
public abstract class MixinGuiRecipeBook {
	@Shadow
	private InventoryCrafting craftingSlots;

	@Inject(method = "updateStackedContents", at = @At("RETURN"))
	private void craftingHax(CallbackInfo ci) {
		if (!this.craftingSlots.isEmpty()) {
			MixinCode.handleHaxCrafting();
		}
	}
}
