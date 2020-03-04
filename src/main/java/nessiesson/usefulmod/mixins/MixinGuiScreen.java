package nessiesson.usefulmod.mixins;

import com.google.common.base.Splitter;
import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("RedundantCast")
@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
	@Shadow
	public abstract void sendChatMessage(String msg, boolean addToChat);

	@Inject(method = "handleInput", at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleKeyboardInput()V"), @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;handleMouseInput()V")}, cancellable = true)
	private void mc31222(CallbackInfo ci) {
		if ((GuiScreen) (Object) this != Minecraft.getMinecraft().currentScreen) {
			ci.cancel();
		}
	}

	/**
	 * @author nessie
	 * @reason Simplest way to inject
	 */
	@Overwrite
	public void sendChatMessage(String msg) {
		for (String message : Splitter.fixedLength(256).split(msg)) {
			this.sendChatMessage(message, true);
		}
	}

	@ModifyConstant(method = "drawWorldBackground", constant = {@Constant(intValue = -1072689136), @Constant(intValue = -804253680)})
	private int getBackGroundColour(int colour) {
		return LiteModUsefulMod.blur.getBackgroundColor();
	}
}
