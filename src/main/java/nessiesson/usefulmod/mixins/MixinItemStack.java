package nessiesson.usefulmod.mixins;

import com.google.common.collect.Multimap;
import nessiesson.usefulmod.LiteModUsefulMod;
import nessiesson.usefulmod.MixinCode;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
	@Shadow
	public abstract NBTTagList getEnchantmentTagList();

	@Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;isEmpty()Z", remap = false))
	private boolean noAttributes(Multimap multimap) {
		return !LiteModUsefulMod.config.showItemAttributes || multimap.isEmpty() || !Minecraft.getMinecraft().gameSettings.advancedItemTooltips;
	}

	@Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getEnchantmentTagList()Lnet/minecraft/nbt/NBTTagList;"))
	private NBTTagList sortEnchantments(ItemStack item) {
		return MixinCode.sortEnchantments(this.getEnchantmentTagList());
	}
}
