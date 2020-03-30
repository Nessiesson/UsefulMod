package nessiesson.usefulmod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.debug.DebugRendererChunkBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = DebugRendererChunkBorder.class, priority = 999)
public abstract class MixinDebugRendererChunkBorder {
	@Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
	private EntityPlayerSP alwaysUseCameraEntity() {
		return (EntityPlayerSP) Minecraft.getMinecraft().getRenderViewEntity();
	}
}
