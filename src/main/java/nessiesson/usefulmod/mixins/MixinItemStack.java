package nessiesson.usefulmod.mixins;

import com.google.common.collect.Multimap;
import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Comparator;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
	private static final Comparator comp = Comparator.<NBTTagCompound>comparingInt(t -> t.getShort("lvl"))
			.reversed()
			.thenComparingInt(t -> t.getShort("id"));

	@Shadow
	public abstract NBTTagList getEnchantmentTagList();

	@Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Multimap;isEmpty()Z", remap = false))
	private boolean noAttributes(Multimap multimap) {
		return !LiteModUsefulMod.config.showItemAttributes || multimap.isEmpty() || !Minecraft.getMinecraft().gameSettings.advancedItemTooltips;
	}

	@Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getEnchantmentTagList()Lnet/minecraft/nbt/NBTTagList;"))
	private NBTTagList sortEnchantments(ItemStack item) {
		final NBTTagList enchantments = this.getEnchantmentTagList();
		if (LiteModUsefulMod.config.sortEnchantmentTooltip) {
			((INBTTagList) enchantments).getTagList().sort(comp);
		}
		return enchantments;
	}
}
