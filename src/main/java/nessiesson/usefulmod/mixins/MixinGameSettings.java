package nessiesson.usefulmod.mixins;

import kotlin.Pair;
import nessiesson.usefulmod.MixinCode;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameSettings.class)
public abstract class MixinGameSettings {
	@Shadow
	public float gammaSetting;

	@Shadow
	public abstract float getOptionFloatValue(GameSettings.Options settingOption);

	@Inject(method = "setOptionFloatValue", at = @At("HEAD"), cancellable = true)
	private void overrideGammaValue(GameSettings.Options settingsOption, float value, CallbackInfo ci) {
		Pair<Boolean, Float> pair = MixinCode.INSTANCE.onSetOptionFloatValue(settingsOption, value);
		if (pair.getFirst()) {
			ci.cancel();
		}

		this.gammaSetting = pair.getSecond();
	}

	@Inject(method = "getKeyBinding", at = @At("HEAD"), cancellable = true)
	private void overrideGammaText(GameSettings.Options option, CallbackInfoReturnable<String> cir) {
		Pair<Boolean, String> pair = MixinCode.INSTANCE.renderBrightnessText(option, this.getOptionFloatValue(option));
		if (pair.getFirst()) {
			cir.setReturnValue(pair.getSecond());
		}
	}
}
