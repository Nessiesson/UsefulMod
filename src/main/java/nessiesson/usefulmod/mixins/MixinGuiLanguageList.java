package nessiesson.usefulmod.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net/minecraft/client/gui/GuiLanguage$List")
public abstract class MixinGuiLanguageList {
	@Redirect(method = "elementClicked(IZII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;refreshResources()V"))
	private void onlyRefreshLanguage(Minecraft mc) {
		mc.getLanguageManager().onResourceManagerReload(mc.getResourceManager());
	}
}
