package nessiesson.usefulmod;

import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.List;

// Modified version of the shulkerbox display from Vazkii's  Quark Forgemod.
public class ShulkerBoxDisplay {
	private final static ResourceLocation WIDGET_RESOURCE = new ResourceLocation("usefulmod", "textures/shulker_widget.png");

	public static void handleShulkerBoxDisplayRenderer(ItemStack stack, int x, int y, Gui gui) {
		if (!LiteModUsefulMod.config.isShulkerBoxDisplayEnabled) {
			return;
		}

		if (stack != null && stack.getItem() instanceof ItemShulkerBox && stack.hasTagCompound() && GuiScreen.isShiftKeyDown()) {
			NBTTagCompound cmp = ItemNBTHelper.getCompoundOrNull(stack, "BlockEntityTag");
			if (cmp != null && cmp.hasKey("Items", 9)) {
				final int texWidth = 172;
				final int texHeight = 64;

				int currentX = x;
				int currentY = y - texHeight - 18;

				GlStateManager.pushMatrix();
				RenderHelper.enableGUIStandardItemLighting();
				GlStateManager.enableRescaleNormal();
				GlStateManager.translate(0, 0, 700);

				Minecraft mc = Minecraft.getMinecraft();
				mc.getTextureManager().bindTexture(WIDGET_RESOURCE);

				EnumDyeColor dye = ((BlockShulkerBox) ((ItemBlock) stack.getItem()).getBlock()).getColor();
				Color color = new Color(ItemDye.DYE_COLORS[dye.getDyeDamage()]);
				GlStateManager.color((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F);

				gui.drawTexturedModalRect(currentX, currentY, 0, 0, texWidth, texHeight);

				NonNullList<ItemStack> itemList = NonNullList.withSize(27, ItemStack.EMPTY);
				ItemStackHelper.loadAllItems(cmp, itemList);

				RenderItem render = mc.getRenderItem();

				GlStateManager.enableDepth();
				int i = 0;
				for (ItemStack itemstack : itemList) {
					if (!itemstack.isEmpty()) {
						int xp = currentX + 6 + (i % 9) * 18;
						int yp = currentY + 6 + (i / 9) * 18;

						render.renderItemAndEffectIntoGUI(itemstack, xp, yp);
						render.renderItemOverlays(mc.fontRenderer, itemstack, xp, yp);
					}

					i++;
				}

				GlStateManager.disableDepth();
				GlStateManager.popMatrix();
			}
		}
	}

	public static void whatever(ItemStack stack, List<String> tooltip) {
		if (!LiteModUsefulMod.config.isShulkerBoxDisplayEnabled) {
			return;
		}

		if (!stack.isEmpty() && stack.getItem() instanceof ItemShulkerBox && stack.hasTagCompound()) {
			NBTTagCompound cmp = ItemNBTHelper.getCompoundOrNull(stack, "BlockEntityTag");
			if (cmp != null && cmp.hasKey("Items", 9)) {

				if (!GuiScreen.isShiftKeyDown()) {
					tooltip.add(1, "Hold Shift to see contents");
				}
			}
		}
	}
}
