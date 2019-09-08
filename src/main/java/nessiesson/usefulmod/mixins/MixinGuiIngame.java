package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.config.Config;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.ScoreObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {
	@Inject(method = "renderScoreboard", at = @At("HEAD"), cancellable = true)
	private void toggleScoreboard(ScoreObjective objective, ScaledResolution scaledRes, CallbackInfo ci) {
		if (Config.INSTANCE.isScoreboardHidden()) {
			ci.cancel();
		}
	}
}
