package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(TileEntityBeaconRenderer.class)
public abstract class MixinTileEntityBeaconRenderer implements IMixinEntityBeacon {
	@Inject(method = "render", at = @At("RETURN"))
	private void render(TileEntityBeacon te, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci) {
		if (!LiteModUsefulMod.config.isShowBeaconRangeEnabled) {
			return;
		}
		if (((IMixinEntityBeacon) te).getIsComplete() && te.getLevels() > 0) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			double d0 = (double) (te.getLevels() * 10 + 10);
			double d1 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
			double d2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
			double d3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
			float[] colours = new Color(255, 20, 147).getRGBColorComponents(null);

			AxisAlignedBB axisalignedbb = (new AxisAlignedBB(te.getPos())).offset(-d1, -d2, -d3).grow(d0).expand(0.0D, (double) te.getWorld().getHeight(), 0.0D);

			GlStateManager.disableFog();
			GlStateManager.disableLighting();
			GlStateManager.disableTexture2D();

			RenderGlobal.renderFilledBox(axisalignedbb, colours[0], colours[1], colours[2], 0.2F);
			RenderGlobal.drawSelectionBoundingBox(axisalignedbb, colours[0], colours[1], colours[2], 1F);

			GlStateManager.enableTexture2D();
			GlStateManager.enableLighting();
			GlStateManager.enableFog();
		}
	}
}
