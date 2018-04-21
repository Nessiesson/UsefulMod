package nessiesson.usefulmod;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.JoinGameListener;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import nessiesson.usefulmod.config.UsefulModConfig;
import nessiesson.usefulmod.config.UsefulModConfigPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.init.Blocks;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.SPacketJoinGame;

import java.io.File;

public class LiteModUsefulMod implements Tickable, Configurable, JoinGameListener {
	public static UsefulModConfig config = new UsefulModConfig();
	public static StepAssistHelper stepAssistHelper = new StepAssistHelper();

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "@VERSION@";
	}

	@Override
	public void init(File configPath) {
		// TODO Auto-generated method stub
		Blocks.SLIME_BLOCK.slipperiness = LiteModUsefulMod.config.isNoSlimeSlowdownEnabled ? 0.6F : 0.8F;
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "UsefulMod";
	}

	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<? extends ConfigPanel> getConfigPanelClass() {
		return UsefulModConfigPanel.class;
	}

	@Override
	public void onJoinGame(INetHandler netHandler, SPacketJoinGame joinGamePacket, ServerData serverData, RealmsServer realmsServer) {
		stepAssistHelper.update();
	}
}
