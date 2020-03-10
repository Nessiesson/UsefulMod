package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleDigging.class)
public abstract class MixinParticleDigging extends Particle {
	protected MixinParticleDigging(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void removeRandomParticleMotion(CallbackInfo ci) {
		if (LiteModUsefulMod.config.showInsaneBlockBreakingParticles) {
			final double multiplier = this.rand.nextFloat() * 5;
			this.motionX *= multiplier;
			this.motionY *= multiplier;
			this.motionZ *= multiplier;
			this.particleMaxAge *= multiplier;
			this.particleGravity = 0F;
		}
	}
}
