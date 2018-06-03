package nessiesson.usefulmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;

import java.util.List;

// Modified version of the map inventory renderer from Vazkii's Quark Forgemod.
public class MapDisplay {
	private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");

	public static void handleMapDisplayRenderer(ItemStack stack, int x, int y) {
		if (!LiteModUsefulMod.config.isMapDisplayEnabled) {
			return;
		}
		if (stack != null && stack.getItem() instanceof ItemMap && GuiScreen.isShiftKeyDown()) {
			final Minecraft mc = Minecraft.getMinecraft();

			final MapData mapdata = Items.FILLED_MAP.getMapData(stack, mc.world);
			if (mapdata == null) {
				return;
			}
			final int pad = 7;
			final double size = 135D;
			final double scale = 0.5D;

			GlStateManager.pushMatrix();
			GlStateManager.color(1F, 1F, 1F);
			GlStateManager.disableLighting();
			//mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);

			//TODO figure out how to add the background.
			/*
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder vertexbuffer = tessellator.getBuffer();

			vertexbuffer.begin(GL.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			vertexbuffer.pos(-pad, size, 0.0D).tex(0.0D, 1.0D).endVertex();
			vertexbuffer.pos(size, size, 0.0D).tex(1.0D, 1.0D).endVertex();
			vertexbuffer.pos(size, -pad, 0.0D).tex(1.0D, 0.0D).endVertex();
			vertexbuffer.pos(-pad, -pad, 0.0D).tex(0.0D, 0.0D).endVertex();
			tessellator.draw();
			*/

			GlStateManager.translate(x, y - size * scale - 5, 700);
			GlStateManager.scale(scale, scale, scale);
			mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);

			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}

	public static void addMapTooltip(ItemStack stack, List<String> tooltip) {
		if (!LiteModUsefulMod.config.isMapDisplayEnabled) {
			return;
		}

		if (!stack.isEmpty() && stack.getItem() instanceof ItemMap) {
			if (!GuiScreen.isShiftKeyDown())
				tooltip.add(1, "Hold Shift to see contents");
		}
	}
}
