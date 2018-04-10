package nessiesson.usefulmod;

import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import nessiesson.usefulmod.config.UsefulModConfig;
import nessiesson.usefulmod.config.UsefulModConfigPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;

import java.io.File;

public class LiteModUsefulMod implements Tickable, Configurable {
	public static UsefulModConfig config = new UsefulModConfig();

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
}
