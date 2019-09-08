package nessiesson.usefulmod

import com.mumfrey.liteloader.gl.GL
import nessiesson.usefulmod.mixins.IEntityBeacon
import nessiesson.usefulmod.mixins.INBTTagList
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.client.resources.I18n
import net.minecraft.client.settings.GameSettings
import net.minecraft.inventory.ClickType
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.tileentity.TileEntityBeacon
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.event.ClickEvent
import net.minecraft.world.World
import java.awt.Color
import java.util.*
import kotlin.math.min

object MixinCode {
	fun onBlockSnowRandomDisplayTick(worldIn: World, pos: BlockPos, rand: Random) {
		if (!LiteModUsefulMod.config.showSnowDripParticles) {
			return
		}

		if (rand.nextInt(10) == 0 && worldIn.getBlockState(pos.down()).isTopSolid) {
			val material = worldIn.getBlockState(pos.down(2)).material

			if (!material.blocksMovement() && !material.isLiquid) {
				val x = pos.x + rand.nextDouble()
				val y = pos.y - 1.05
				val z = pos.z + rand.nextDouble()
				worldIn.spawnParticle(EnumParticleTypes.END_ROD, x, y, z, 0.0, -0.06, 0.0)
			}
		}
	}

	fun handleHaxCrafting(id: Int) {
		val mc = Minecraft.getMinecraft()
		val controller = mc.playerController
		val player = mc.player
		when {
			GuiScreen.isShiftKeyDown() && GuiScreen.isAltKeyDown() -> controller.windowClick(id, 0, 1, ClickType.QUICK_MOVE, player)
			GuiScreen.isShiftKeyDown() && GuiScreen.isCtrlKeyDown() -> controller.windowClick(id, 0, 1, ClickType.THROW, player)
		}
	}

	fun onSetOptionFloatValue(settingsOption: GameSettings.Options, oldValue: Float): Pair<Boolean, Float> {
		var value = oldValue
		if (settingsOption != GameSettings.Options.GAMMA) {
			return Pair(false, value)
		}

		value = when {
			value >= 0.95f -> 1000.0f
			value >= 0.9f -> 1.0f
			else -> min(1.0f, value / 0.9f)
		}

		return Pair(true, value)
	}

	fun renderBrightnessText(option: GameSettings.Options, value: Float): Pair<Boolean, String> {
		if (option != GameSettings.Options.GAMMA) {
			return Pair(false, "")
		}

		var s = "${I18n.format(option.translation)}: "
		s += when {
			value > 1.0f -> I18n.format("usefulmod.options.gamma.fullbright")
			value > 0.95f -> I18n.format("options.gamma.max")
			value > 0.0f -> "+${(value * 100.0f).toInt()}%"
			else -> I18n.format("options.gamma.min")
		}

		return Pair(true, s)
	}

	fun renderBeaconRange(te: TileEntityBeacon, partialTicks: Float) {
		if (!LiteModUsefulMod.config.showBeaconRange) {
			return
		}

		if ((te as IEntityBeacon).isComplete && te.levels > 0) {
			val player = Minecraft.getMinecraft().player
			val d0 = (te.levels * 10 + 10).toDouble()
			val d1 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks.toDouble()
			val d2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks.toDouble()
			val d3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks.toDouble()

			val pos = te.pos
			val colour = pos.x + 101 * pos.z + 41942 * pos.y
			val colours = Color((colour * (16777215.0 / 2611456.0)).toInt()).getRGBColorComponents(null)
			val bb = AxisAlignedBB(pos).offset(-d1, -d2, -d3).grow(d0).expand(0.0, te.world.height.toDouble(), 0.0)

			GlStateManager.depthFunc(GL.GL_LESS)
			GlStateManager.depthMask(false)
			GlStateManager.disableFog()
			GlStateManager.disableLighting()
			GlStateManager.disableTexture2D()

			RenderGlobal.renderFilledBox(bb, colours[0], colours[1], colours[2], 0.2f)
			RenderGlobal.drawSelectionBoundingBox(bb, colours[0], colours[1], colours[2], 1f)

			GlStateManager.enableTexture2D()
			GlStateManager.enableLighting()
			GlStateManager.enableFog()
			GlStateManager.depthMask(true)
			GlStateManager.depthFunc(GL.GL_EQUAL)
		}
	}

	fun sendDeathLocation() {
		if (!LiteModUsefulMod.config.deathLocation) {
			return
		}

		val mc = Minecraft.getMinecraft()
		val pos = mc.player.position
		val message = TextComponentString("You died @ ${pos.x} ${pos.y} ${pos.z}")
		message.style.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "${pos.x} ${pos.y} ${pos.z}")
		mc.ingameGUI.chatGUI.printChatMessage(message)
	}

	fun sortEnchantments(list: NBTTagList): NBTTagList {
		if (LiteModUsefulMod.config.sortEnchantmentTooltip) {
			(list as INBTTagList).tagList.sortWith(
					compareByDescending<NBTBase> { (it as NBTTagCompound).getShort("lvl") }.thenBy { (it as NBTTagCompound).getShort("id") }
			)
		}
		return list
	}
}
