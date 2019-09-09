package nessiesson.usefulmod;

import com.mumfrey.liteloader.gl.GL;
import nessiesson.usefulmod.mixins.IEntityBeacon;
import nessiesson.usefulmod.mixins.INBTTagList;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Comparator;
import java.util.Random;

public class MixinCode {
	public static void onBlockSnowRandomDisplayTick(World world, BlockPos pos, Random rand) {
		if (!LiteModUsefulMod.config.showSnowDripParticles) {
			return;
		}

		if (rand.nextInt(10) == 0 && world.getBlockState(pos.down()).isTopSolid()) {
			final Material material = world.getBlockState(pos.down(2)).getMaterial();

			if (!material.blocksMovement() && !material.isLiquid()) {
				final double x = pos.getX() + rand.nextDouble();
				final double y = pos.getY() - 1.05;
				final double z = pos.getZ() + rand.nextDouble();
				world.spawnParticle(EnumParticleTypes.END_ROD, x, y, z, 0.0, -0.06, 0.0);
			}
		}
	}

	public static void handleHaxCrafting(int id) {
		if (!LiteModUsefulMod.config.craftingHax) {
			return;
		}

		final Minecraft mc = Minecraft.getMinecraft();
		final PlayerControllerMP controller = mc.playerController;
		final EntityPlayerSP player = mc.player;
		if (GuiScreen.isShiftKeyDown() && GuiScreen.isAltKeyDown()) {
			controller.windowClick(id, 0, 1, ClickType.QUICK_MOVE, player);
		} else if (GuiScreen.isShiftKeyDown() && GuiScreen.isCtrlKeyDown()) {
			controller.windowClick(id, 0, 1, ClickType.THROW, player);
		}
	}

	public static Pair<Boolean, Float> onSetOptionFloatValue(GameSettings.Options option, float oldValue) {
		float value = oldValue;
		if (option != GameSettings.Options.GAMMA) {
			return new ImmutablePair<>(false, value);
		}

		if (value >= 0.95F) {
			value = 1000F;
		} else if (value >= 0.9F) {
			value = 1F;
		} else {
			value = Math.min(1F, value / 0.9F);
		}

		return new ImmutablePair<>(true, value);
	}

	public static Pair<Boolean, String> renderBrightnessText(GameSettings.Options option, float value) {
		if (option != GameSettings.Options.GAMMA) {
			return new ImmutablePair<>(false, "");
		}

		String s = I18n.format(option.getTranslation()) + " ";
		if (value > 1F) {
			s += I18n.format("usefulmod.options.gamma.fullbright");
		} else if (value > 0.95F) {
			s += I18n.format("options.gamma.max");
		} else if (value > 0F) {
			s += "+" + (int) (value * 100.0F) + "%";
		} else {
			s += I18n.format("options.gamma.min");
		}

		return new ImmutablePair<>(true, s);
	}

	public static void renderBeaconRange(TileEntityBeacon te, float partialTicks) {
		if (!LiteModUsefulMod.config.showBeaconRange) {
			return;
		}

		if (((IEntityBeacon) te).getIsComplete() && te.getLevels() > 0) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			double d0 = te.getLevels() * 10 + 10;
			double d1 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
			double d2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
			double d3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;

			BlockPos pos = te.getPos();
			// It's not good, but it seems reasonably random.
			int colour = pos.getX() + 101 * pos.getZ() + 41942 * pos.getY();
			float[] colours = new Color((int) (colour * (16777215.0 / 2611456.0))).getRGBColorComponents(null);
			AxisAlignedBB axisalignedbb = (new AxisAlignedBB(pos)).offset(-d1, -d2, -d3).grow(d0).expand(0.0, te.getWorld().getHeight(), 0.0);

			GlStateManager.depthFunc(GL.GL_LESS);
			GlStateManager.depthMask(false);
			GlStateManager.disableFog();
			GlStateManager.disableLighting();
			GlStateManager.disableTexture2D();

			RenderGlobal.renderFilledBox(axisalignedbb, colours[0], colours[1], colours[2], 0.2F);
			RenderGlobal.drawSelectionBoundingBox(axisalignedbb, colours[0], colours[1], colours[2], 1F);

			GlStateManager.enableTexture2D();
			GlStateManager.enableLighting();
			GlStateManager.enableFog();
			GlStateManager.depthMask(true);
			GlStateManager.depthFunc(GL.GL_LEQUAL);
		}
	}

	public static void sendDeathLocation() {
		if (!LiteModUsefulMod.config.deathLocation) {
			return;
		}

		final Minecraft mc = Minecraft.getMinecraft();
		final BlockPos pos = mc.player.getPosition();
		final ITextComponent message = new TextComponentString(String.format("You died @ %d %d %d", pos.getX(), pos.getY(), pos.getZ()));
		message.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("%d %d %d", pos.getX(), pos.getY(), pos.getZ())));
		mc.ingameGUI.getChatGUI().printChatMessage(message);
	}

	public static NBTTagList sortEnchantments(NBTTagList list) {
		final Comparator comp = Comparator.<NBTTagCompound>comparingInt(t -> t.getShort("lvl"))
				.reversed()
				.thenComparingInt(t -> t.getShort("id"));
		if (LiteModUsefulMod.config.sortEnchantmentTooltip) {
			((INBTTagList) list).getTagList().sort(comp);
		}
		return list;
	}
}
