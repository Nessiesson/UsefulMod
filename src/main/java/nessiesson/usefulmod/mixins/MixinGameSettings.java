package nessiesson.usefulmod.mixins;

import net.minecraft.client.resources.I18n;
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
	private void overrideGammaValue(GameSettings.Options option, float value, CallbackInfo ci) {
		if (option != GameSettings.Options.GAMMA) {
			return;
		}

		if (value >= 0.95F) {
			value = 1000F;
		} else if (value >= 0.9F) {
			value = 1F;
		} else {
			value = Math.min(1F, value / 0.9F);
		}

		this.gammaSetting = value;
	}

	@Inject(method = "getKeyBinding", at = @At("HEAD"), cancellable = true)
	private void overrideGammaText(GameSettings.Options option, CallbackInfoReturnable<String> cir) {
		if (option != GameSettings.Options.GAMMA) {
			return;
		}

		cir.cancel();
		final float f = this.getOptionFloatValue(option);
		String s = I18n.format(option.getTranslation()) + ": ";
		if (f > 1F) {
			s += I18n.format("usefulmod.options.gamma.fullbright");
		} else if (f > 0.95F) {
			s += I18n.format("options.gamma.max");
		} else if (f > 0F) {
			s += "+" + (int) (f * 100.0F) + "%";
		} else {
			s += I18n.format("options.gamma.min");
		}

		cir.setReturnValue(s);
	}
}
