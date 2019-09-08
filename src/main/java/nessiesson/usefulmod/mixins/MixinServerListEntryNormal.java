package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.config.Config;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ServerListEntryNormal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerListEntryNormal.class)
public abstract class MixinServerListEntryNormal {
	@Redirect(method = "drawEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", ordinal = 0))
	private int hideServerNames(FontRenderer fontRenderer, String text, int x, int y, int color) {
		if (Config.INSTANCE.getHideServerNames()) {
			return 0;
		} else {
			return fontRenderer.drawString(text, x, y, color);
		}
	}
}
