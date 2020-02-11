package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayerClient {
	@Shadow
	private WorldClient world;

	@Inject(method = "handleCombatEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
	private void sendDeathLocation(SPacketCombatEvent packetIn, CallbackInfo ci) {
		if (LiteModUsefulMod.config.respawnOnDeath) {
			Minecraft.getMinecraft().player.respawnPlayer();
		}

		if (LiteModUsefulMod.config.deathLocation) {
			final Minecraft mc = Minecraft.getMinecraft();
			final BlockPos pos = mc.player.getPosition();
			final String formatted = String.format("You died @ %d %d %d", pos.getX(), pos.getY(), pos.getZ());
			final ITextComponent message = new TextComponentString(formatted);
			message.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, formatted));
			mc.ingameGUI.getChatGUI().printChatMessage(message);
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

	@Redirect(method = "handleChunkData", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", remap = false))
	private boolean replaceTileEntityLoop(Iterator<NBTTagCompound> iterator) {
		while (iterator.hasNext()) {
			final NBTTagCompound compound = iterator.next();
			final BlockPos pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
			final boolean isPiston = compound.getString("id").equals("minecraft:piston");
			if (isPiston) {
				compound.setFloat("progress", Math.min(compound.getFloat("progress") + 0.5F, 1F));
			}

			TileEntity te = this.world.getTileEntity(pos);
			if (te != null) {
				te.readFromNBT(compound);
			} else {
				if (!isPiston) {
					continue;
				}

				final IBlockState state = this.world.getBlockState(pos);
				if (state.getBlock() != Blocks.PISTON_EXTENSION) {
					continue;
				}

				te = new TileEntityPiston();
				te.readFromNBT(compound);
				this.world.setTileEntity(pos, te);
				te.updateContainingBlockInfo();
			}
		}

		return false;
	}
}
