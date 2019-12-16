package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import nessiesson.usefulmod.MixinCode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayerClient {
	@Inject(method = "handleCombatEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
	private void sendDeathLocation(SPacketCombatEvent packetIn, CallbackInfo ci) {
		MixinCode.sendDeathLocation();
		if (LiteModUsefulMod.config.respawnOnDeath) {
			Minecraft.getMinecraft().player.respawnPlayer();
		}
	}

	@Redirect(method = "handleTimeUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/server/SPacketTimeUpdate;getWorldTime()J"))
	private long alwaysDay(SPacketTimeUpdate packet) {
		final long time = packet.getWorldTime();
		if (LiteModUsefulMod.config.alwaysDay) {
			return time >= 0 ? -(time - time % 24000L + 6000L) : time;
		}

		return time;
	}

	@Redirect(method = "handleSetPassengers", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V", remap = false))
	private void noopWarn(Logger logger, String message) {
		// noop
	}
}
