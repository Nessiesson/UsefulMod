package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.MixinCode;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {GuiCrafting.class, GuiInventory.class})
public abstract class MixinReauthCraftingHaxFix {
	@Redirect(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/recipebook/GuiRecipeBook;mouseClicked(III)Z"))
	private boolean fixPls(GuiRecipeBook gui, int x, int y, int window) {
		final boolean value = gui.mouseClicked(x, y, window);
		if (value) {
			MixinCode.handleHaxCrafting(window);
		}

		return value;
	}
}
