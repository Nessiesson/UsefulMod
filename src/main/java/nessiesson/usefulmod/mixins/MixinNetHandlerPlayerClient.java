package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayerClient {
	@Inject(method = "handleCombatEvent", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"),
			locals = LocalCapture.CAPTURE_FAILHARD)
	private void onPlayerDeath(SPacketCombatEvent packetIn, CallbackInfo ci, Entity entity) {
		if (!Config.INSTANCE.isDeathLocationEnabled()) {
			return;
		}

		BlockPos pos = entity.getPosition();
		ITextComponent message = new TextComponentString("You died @ " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
		message.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, pos.getX() + " " + pos.getY() + " " + pos.getZ()));
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(message);
	}

	@Inject(method = "handleCombatEvent", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V",
			shift = At.Shift.BEFORE))
	private void onPlayerDeath(SPacketCombatEvent packetIn, CallbackInfo ci) {
		if (Config.INSTANCE.isRespawnOnDeathEnabled()) {
			Minecraft.getMinecraft().player.respawnPlayer();
		}
	}
}
