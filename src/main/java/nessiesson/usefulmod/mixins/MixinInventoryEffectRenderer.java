package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryEffectRenderer.class)
public abstract class MixinInventoryEffectRenderer extends GuiContainer {
	public MixinInventoryEffectRenderer(Container container) {
		super(container);
	}

	@Inject(method = "updateActivePotionEffects", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/InventoryEffectRenderer;guiLeft:I", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
	private void noPotionShift(CallbackInfo ci) {
		if (!LiteModUsefulMod.config.showPotionShift) {
			this.guiLeft = (this.width - this.xSize) / 2;
		}
	}
}
