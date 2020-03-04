package nessiesson.usefulmod;

import nessiesson.usefulmod.mixins.IEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ResourceLocation;

public class Blur {
	private static final ResourceLocation BLUR_SHADER = new ResourceLocation("usefulmod", "shaders/post/fade_in_blur.json");
	private long start;

	public void onScreenChange(GuiScreen screen) {
		final Minecraft mc = Minecraft.getMinecraft();
		if (mc.world != null) {
			final EntityRenderer renderer = mc.entityRenderer;
			final boolean excluded = screen == null || screen.getClass() == GuiChat.class;
			if (!renderer.isShaderActive() && !excluded) {
				((IEntityRenderer) renderer).callLoadShader(BLUR_SHADER);
				start = System.currentTimeMillis();
			} else if (renderer.isShaderActive() && excluded) {
				renderer.stopUseShader();
			}
		}
	}

	private float getProgress() {
		return Math.min((System.currentTimeMillis() - start) / 200F, 1);
	}

	public int getBackgroundColor() {
		final int color = 75000000;
		int a = color >>> 24;
		int r = (color >> 16) & 0xFF;
		int b = (color >> 8) & 0xFF;
		int g = color & 0xFF;
		float prog = this.getProgress();
		a *= prog;
		r *= prog;
		g *= prog;
		b *= prog;
		return a << 24 | r << 16 | b << 8 | g;
	}
}
