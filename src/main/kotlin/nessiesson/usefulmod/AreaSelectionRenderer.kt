package nessiesson.usefulmod

import nessiesson.usefulmod.mixins.IGuiChat
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiChat
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.command.CommandBase
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos

object AreaSelectionRenderer {
	fun render(partialTicks: Float) {
		val mc = Minecraft.getMinecraft()
		if (mc.currentScreen == null || mc.currentScreen !is GuiChat) {
			return
		}

		val chat = mc.currentScreen
		val msg = (chat as IGuiChat).inputField.text.trim()
		val args = msg.split(' ').toTypedArray()
		if (args.isEmpty()) {
			return
		}

		val player = mc.player
		val d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks
		val d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks
		val d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks

		var p0: BlockPos? = null
		var p1: BlockPos? = null
		var p2: BlockPos? = null

		// @formatter:off
		try { p0 = CommandBase.parseBlockPos(player, args, 1, false) } catch (e: Exception) { /* noop */ }
		try { p1 = CommandBase.parseBlockPos(player, args, 4, false) } catch (e: Exception) { /* noop */ }
		try { p2 = CommandBase.parseBlockPos(player, args, 7, false) } catch (e: Exception) { /* noop */ }
		// @formatter:on

		GlStateManager.depthMask(false)
		GlStateManager.disableTexture2D()
		GlStateManager.disableLighting()
		GlStateManager.disableCull()
		GlStateManager.disableBlend()
		GlStateManager.glLineWidth(3f)

		if (args[0] in listOf("/clone", "/fill", "/setblock")) {
			if (args[0] == "/setblock") {
				p1 = p0
			}

			var origin: AxisAlignedBB? = null
			if (p0 != null && p1 != null) {
				origin = AxisAlignedBB(p0, p1)
				RenderGlobal.drawSelectionBoundingBox(origin.expand(1.0, 1.0, 1.0).offset(-d0, -d1, -d2), 0.9f, 0.9f, 0.9f, 1f)
			}

			if (args[0] == "/clone") {
				if (p2 != null && origin != null) {
					val target = AxisAlignedBB(p2, p2.add(origin.maxX - origin.minX + 1, origin.maxY - origin.minY + 1, origin.maxZ - origin.minZ + 1))
					RenderGlobal.drawSelectionBoundingBox(target.grow(0.005).offset(-d0, -d1, -d2), 0.99f, 0.99f, 0.99f, 1f)
				}
			}
		}

		GlStateManager.glLineWidth(1f)
		GlStateManager.enableTexture2D()
		GlStateManager.enableLighting()
		GlStateManager.enableCull()
		GlStateManager.disableBlend()
		GlStateManager.depthMask(true)
	}
}
