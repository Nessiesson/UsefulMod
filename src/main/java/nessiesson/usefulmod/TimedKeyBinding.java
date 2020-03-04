package nessiesson.usefulmod;

import net.minecraft.client.settings.KeyBinding;

public class TimedKeyBinding extends KeyBinding {
	public int tickDelay = 10;
	public int currentTick = 0;

	public TimedKeyBinding(String description, int keyCode, String category) {
		super(description, keyCode, category);
	}

	public void tick() {
		if (this.currentTick++ >= this.tickDelay) {
			this.currentTick = 0;
			if (this.tickDelay == 0) {
				KeyBinding.setKeyBindState(this.getKeyCode(), true);
			}

			KeyBinding.onTick(this.getKeyCode());
		}
	}
}
