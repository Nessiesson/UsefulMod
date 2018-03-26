package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.ItemNBTHelper;
import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;

//Stolen from Vazkii's Quark Forge mod.
@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
	private static ResourceLocation WIDGET_RESOURCE = new ResourceLocation("usefulmod", "textures/shulker_widget.png");

	@Inject(method = "renderToolTip", at = @At(value = "RETURN"))
	private void postRenderToolTip(ItemStack stack, int x, int y, CallbackInfo ci) {
		if(!LiteModUsefulMod.config.isShulkerBoxDisplayEnabled) {
			return;
		}

		if(stack != null && stack.getItem() instanceof ItemShulkerBox && stack.hasTagCompound() && GuiScreen.isShiftKeyDown()) {
			NBTTagCompound cmp = ItemNBTHelper.getCompound(stack, "BlockEntityTag", true);
			if(cmp != null && cmp.hasKey("Items", 9)) {
				int currentX = x - 5;
				int currentY = y - 70;

				int texWidth = 172;
				int texHeight = 64;


				if(currentY < 0) {
					//TODO: Properly adjust y-value.
					currentY = y + ((GuiScreen)(Object)this).getItemToolTip(stack).size() * 10 + 5;
				}
				currentY -= 18;

				ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
				int right = x + texWidth;
				if(right > res.getScaledWidth()) {
					currentX -= right - res.getScaledWidth();
				}

				GlStateManager.pushMatrix();
				RenderHelper.enableStandardItemLighting();
				GlStateManager.enableRescaleNormal();
				GlStateManager.color(1F, 1F, 1F);
				GlStateManager.translate(0, 0, 700);

				Minecraft mc = Minecraft.getMinecraft();
				mc.getTextureManager().bindTexture(WIDGET_RESOURCE);

				RenderHelper.disableStandardItemLighting();

				if(true) {
					EnumDyeColor dye = ((BlockShulkerBox) ((ItemBlock) stack.getItem()).getBlock()).getColor();
					int color = ItemDye.DYE_COLORS[dye.getDyeDamage()];
					Color colorObj = new Color(color);
					GlStateManager.color((float)colorObj.getRed() / 255F, (float)colorObj.getGreen() / 255F, (float)colorObj.getBlue() / 255F);
				}
				Gui.drawModalRectWithCustomSizedTexture(currentX, currentY, 0, 0, texWidth, texHeight, 256, 256);

				GlStateManager.color(1F, 1F, 1F);

				NonNullList<ItemStack> itemList = NonNullList.withSize(27, ItemStack.EMPTY);
				ItemStackHelper.loadAllItems(cmp, itemList);

				RenderItem render = mc.getRenderItem();

				RenderHelper.enableGUIStandardItemLighting();
				GlStateManager.enableDepth();
				int i = 0;
				for(ItemStack itemstack : itemList) {
					int xp = currentX + 6 + (i % 9) * 18;
					int yp = currentY + 6 + (i / 9) * 18;

					if(!itemstack.isEmpty()) {
						render.renderItemAndEffectIntoGUI(itemstack, xp, yp);
						render.renderItemOverlays(mc.fontRenderer, itemstack, xp, yp);
					}

					 i++;
				}

				GlStateManager.disableDepth();
				GlStateManager.disableRescaleNormal();
				GlStateManager.popMatrix();
			}
		}
	}
}
