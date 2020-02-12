package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem {
	private boolean isPerfectBasicToolBase(Map<Enchantment, Integer> map) {
		return map.containsKey(Enchantments.UNBREAKING) && map.get(Enchantments.UNBREAKING) == 3 && map.containsKey(Enchantments.MENDING);
	}

	private boolean isPerfectToolBase(Map<Enchantment, Integer> map) {
		return map.containsKey(Enchantments.EFFICIENCY) && map.get(Enchantments.EFFICIENCY) == 5 && this.isPerfectBasicToolBase(map);
	}

	private boolean isPerfectSilk(Map<Enchantment, Integer> map) {
		return this.isPerfectToolBase(map) && map.containsKey(Enchantments.SILK_TOUCH);
	}

	private boolean isPerfectFortune(Map<Enchantment, Integer> map) {
		return this.isPerfectToolBase(map) && map.containsKey(Enchantments.FORTUNE);
	}

	private boolean isPerfectSilkAxe(Map<Enchantment, Integer> map) {
		return this.isPerfectSilk(map) && map.containsKey(Enchantments.SHARPNESS) && map.get(Enchantments.SHARPNESS) == 5;
	}

	private boolean isPerfectFortuneAxe(Map<Enchantment, Integer> map) {
		return this.isPerfectFortune(map) && map.containsKey(Enchantments.SHARPNESS) && map.get(Enchantments.SHARPNESS) == 5;
	}

	private boolean isPerfectHoe(Map<Enchantment, Integer> map) {
		return map.containsKey(Enchantments.UNBREAKING) && map.get(Enchantments.UNBREAKING) == 3 && map.containsKey(Enchantments.MENDING);
	}

	private boolean isPerfectSword(Map<Enchantment, Integer> map) {
		return this.isPerfectBasicToolBase(map)
				&& map.containsKey(Enchantments.SHARPNESS) && map.get(Enchantments.SHARPNESS) == 5
				&& map.containsKey(Enchantments.LOOTING) && map.get(Enchantments.LOOTING) == 3
				&& map.containsKey(Enchantments.FIRE_ASPECT) && map.get(Enchantments.FIRE_ASPECT) == 2;
	}

	@Inject(method = "renderItemOverlayIntoGUI", at = @At("HEAD"))
	private void postRenderItemCount(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text, CallbackInfo ci) {
		if (LiteModUsefulMod.config.showIdealToolMarker || stack.isEmpty()) {
			return;
		}

		final Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
		final Item item = stack.getItem();
		String marker = "";
		if (item instanceof ItemAxe) {
			if (this.isPerfectSilkAxe(map)) {
				marker = "S";
			} else if (this.isPerfectFortuneAxe(map)) {
				marker = "F";
			}
		} else if (item instanceof ItemElytra && this.isPerfectBasicToolBase(map)) {
			marker = "P";
		} else if (item instanceof ItemFirework && stack.hasTagCompound()) {
			final NBTTagCompound itemData = stack.getTagCompound();
			if (itemData != null) {
				final NBTTagCompound fireworks = itemData.getCompoundTag("Fireworks");
				marker = String.valueOf(fireworks.getByte("Flight"));
			}
		} else if (item instanceof ItemFlintAndSteel && this.isPerfectBasicToolBase(map)) {
			marker = "P";
		} else if (item instanceof ItemHoe && this.isPerfectHoe(map)) {
			marker = "P";
		} else if (item instanceof ItemPickaxe) {
			if (this.isPerfectSilk(map)) {
				marker = "S";
			} else if (this.isPerfectFortune(map)) {
				marker = "F";
			}
		} else if (item instanceof ItemShears && this.isPerfectToolBase(map)) {
			marker = "P";
		} else if (item instanceof ItemSpade) {
			if (this.isPerfectSilk(map)) {
				marker = "S";
			} else if (this.isPerfectFortune(map)) {
				marker = "F";
			}
		} else if (item instanceof ItemSword && this.isPerfectSword(map)) {
			marker = "P";
		}

		if (!marker.equals("")) {
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			GlStateManager.disableBlend();
			GlStateManager.pushMatrix();
			final float f = 0.5F;
			GlStateManager.scale(f, f, f);
			fr.drawStringWithShadow(marker, (xPosition) / f, (yPosition + 12) / f, 0xFFFFFF);
			GlStateManager.popMatrix();
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			GlStateManager.enableBlend();
		}
	}
}
