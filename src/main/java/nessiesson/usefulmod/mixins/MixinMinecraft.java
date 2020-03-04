package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import nessiesson.usefulmod.TimedKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.ISnooperInfo;
import net.minecraft.util.IThreadListener;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IThreadListener, ISnooperInfo {
	@Shadow
	@Nullable
	public GuiScreen currentScreen;

	@Shadow
	public WorldClient world;

	@Inject(method = "dispatchKeypresses", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/GameSettings;setOptionValue(Lnet/minecraft/client/settings/GameSettings$Options;I)V"), cancellable = true)
	private void onNarratorKeypress(CallbackInfo ci) {
		ci.cancel();
	}

	@ModifyVariable(method = "middleClickMouse", ordinal = 0, index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;sendSlotPacket(Lnet/minecraft/item/ItemStack;I)V"))
	private ItemStack maxStackSize(ItemStack stack) {
		if (LiteModUsefulMod.config.alwaysPickBlockMaxStack) {
			stack.setCount(stack.getMaxStackSize());
		}

		return stack;
	}

	@Inject(method = "runTick", at = @At("RETURN"))
	private void onPreClientTick(CallbackInfo ci) {
		if (LiteModUsefulMod.tapeMouseable.isEmpty() || this.currentScreen instanceof GuiIngameMenu) {
			return;
		}

		for (TimedKeyBinding key : LiteModUsefulMod.tapeMouseable) {
			key.tick();
		}
	}

	@Inject(method = "displayGuiScreen", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;", opcode = Opcodes.PUTFIELD))
	private void onScreenChange(GuiScreen screen, CallbackInfo ci) {
		LiteModUsefulMod.blur.onScreenChange(screen);
	}
}
