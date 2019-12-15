package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.ISnooperInfo;
import net.minecraft.util.IThreadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IThreadListener, ISnooperInfo {
	@Redirect(method = "dispatchKeypresses", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/GameSettings;setOptionValue(Lnet/minecraft/client/settings/GameSettings$Options;I)V"))
	private void onSetOptionValue(GameSettings gameSettings, GameSettings.Options settingsOption, int value) {
		// noop
	}

	@ModifyVariable(method = "middleClickMouse", ordinal = 0, index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;sendSlotPacket(Lnet/minecraft/item/ItemStack;I)V"), print = true)
	private ItemStack maxStackSize(ItemStack stack) {
		if (LiteModUsefulMod.config.alwaysPickBlockMaxStack) {
			stack.setCount(stack.getMaxStackSize());
		}

		return stack;
	}
}
