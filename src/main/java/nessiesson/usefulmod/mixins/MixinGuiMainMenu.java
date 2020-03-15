package nessiesson.usefulmod.mixins;

import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu {
	@Inject(method = "switchToRealms", at = @At("HEAD"), cancellable = true)
	private void onClickRealmsButton(CallbackInfo ci) {
		ci.cancel();
	}
}
