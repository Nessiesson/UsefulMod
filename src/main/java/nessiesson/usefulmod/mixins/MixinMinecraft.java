package nessiesson.usefulmod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.ISnooperInfo;
import net.minecraft.util.IThreadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IThreadListener, ISnooperInfo {
	// Disable the goddamn narrator shortcut.
	// If you for some reason need narrator, enable it in Options -> Chat Settings -> Narrator.
	@Redirect(method = "dispatchKeypresses", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/settings/GameSettings;setOptionValue(Lnet/minecraft/client/settings/GameSettings$Options;I)V"))
	private void onSetOptionValue(GameSettings gameSettings, GameSettings.Options settingsOption, int value) {}
}
