package nessiesson.usefulmod;

import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import nessiesson.usefulmod.config.UsefulModConfig;
import nessiesson.usefulmod.config.UsefulModConfigPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;

import java.io.File;

public class LiteModUsefulMod implements Tickable, Configurable {
	public static UsefulModConfig config = new UsefulModConfig();
	private StepAssistHelper stepAssistHelper = new StepAssistHelper();

	@Override
	public String getVersion() {
		return "@VERSION@";
	}

	@Override
	public void init(File configPath) {
		Blocks.SLIME_BLOCK.slipperiness = LiteModUsefulMod.config.isNoSlimeSlowdownEnabled ? 0.6F : 0.8F;
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {
	}

	@Override
	public String getName() {
		return "UsefulMod";
	}

	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
		if (!inGame) {
			return;
		}
		final EntityPlayerSP player = minecraft.player;

		//TODO: If possible, make it so vertical collisions with elytra don't take damage.
		if (LiteModUsefulMod.config.isNoFallEnabled && player.fallDistance > 2.0F) {
			player.connection.sendPacket(new CPacketPlayer(true));
		}

		stepAssistHelper.update(player);
	}

	@Override
	public Class<? extends ConfigPanel> getConfigPanelClass() {
		return UsefulModConfigPanel.class;
	}
}
