package nessiesson.usefulmod

import nessiesson.usefulmod.config.Config
import net.minecraft.block.BlockShulkerBox
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.resources.I18n
import net.minecraft.inventory.ItemStackHelper
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemShulkerBox
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation

// Modified version of the shulkerbox display from Vazkii's Quark Forgemod.
object ShulkerBoxDisplay {
	private val WIDGET_RESOURCE = ResourceLocation("usefulmod", "textures/shulker_widget.png")

	fun handleShulkerBoxDisplayRenderer(stack: ItemStack?, x: Int, y: Int, gui: Gui) {
		if (!Config.isShulkerBoxDisplayEnabled) {
			return
		}

		if (stack != null && stack.item is ItemShulkerBox && stack.hasTagCompound() && GuiScreen.isShiftKeyDown()) {
			val cmp = getCompoundOrNull(stack)
			if (cmp != null && cmp.hasKey("Items", 9)) {
				val texWidth = 172
				val texHeight = 64
				val currentY = y - texHeight - 18

				GlStateManager.pushMatrix()
				RenderHelper.enableGUIStandardItemLighting()
				GlStateManager.enableRescaleNormal()
				GlStateManager.translate(0f, 0f, 700f)

				val mc = Minecraft.getMinecraft()
				mc.textureManager.bindTexture(WIDGET_RESOURCE)

				val dye = ((stack.item as ItemBlock).block as BlockShulkerBox).color
				val colours = dye.colorComponentValues
				GlStateManager.color(colours[0], colours[1], colours[2])

				gui.drawTexturedModalRect(x, currentY, 0, 0, texWidth, texHeight)

				val itemList = NonNullList.withSize(27, ItemStack.EMPTY)
				ItemStackHelper.loadAllItems(cmp, itemList)

				val render = mc.renderItem
				GlStateManager.enableDepth()
				for ((i, item) in itemList.withIndex()) {
					if (!item.isEmpty) {
						val xp = x + 6 + i % 9 * 18
						val yp = currentY + 6 + i / 9 * 18
						render.renderItemAndEffectIntoGUI(item, xp, yp)
						render.renderItemOverlays(mc.fontRenderer, item, xp, yp)
					}
				}
				GlStateManager.disableDepth()
				GlStateManager.popMatrix()
			}
		}
	}

	fun addShulkerBoxTooltop(stack: ItemStack, tooltip: MutableList<String>) {
		if (!Config.isShulkerBoxDisplayEnabled) {
			return
		}

		if (!stack.isEmpty && stack.item is ItemShulkerBox && stack.hasTagCompound()) {
			val cmp = getCompoundOrNull(stack)
			if (cmp != null && cmp.hasKey("Items", 9)) {

				if (!GuiScreen.isShiftKeyDown()) {
					tooltip.add(1, I18n.format("usefulmod.ui.mention_shift"))
				}
			}
		}
	}

	private fun getCompoundOrNull(stack: ItemStack): NBTTagCompound? {
		val compound = stack.tagCompound
		return if (compound != null && compound.hasKey("BlockEntityTag")) {
			compound.getCompoundTag("BlockEntityTag")
		} else {
			null
		}
	}
}
