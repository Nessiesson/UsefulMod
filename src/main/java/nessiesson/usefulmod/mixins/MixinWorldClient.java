package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;

// Priority changed to override Tweakeroo.
@Mixin(value = WorldClient.class, priority = 1001)
public abstract class MixinWorldClient extends World {
	protected MixinWorldClient(ISaveHandler ish, WorldInfo wi, WorldProvider wp, Profiler p, boolean c) {
		super(ish, wi, wp, p, c);
	}

	@Override
	public void updateEntity(Entity entity) {
		if (LiteModUsefulMod.config.clientEntityUpdates || entity instanceof EntityPlayer || entity instanceof EntityFireworkRocket) {
			super.updateEntity(entity);
		}
	}

	@Override
	public float getRainStrength(float delta) {
		return LiteModUsefulMod.config.showRain ? this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * delta : 0F;
	}
}
