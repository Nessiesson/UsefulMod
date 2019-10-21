package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public abstract class MixinParticleManager {
	@Shadow
	protected World world;

	@Inject(method = "addBlockDestroyEffects", at = @At("HEAD"), cancellable = true)
	private void onAddBlockDestroyEffects(BlockPos pos, IBlockState state, CallbackInfo ci) {
		if (!LiteModUsefulMod.config.showBlockBreakingParticles) {
			ci.cancel();
			return;
		}

		if (LiteModUsefulMod.config.showExplosion) {
			if (this.world instanceof WorldClient) {
				((WorldClient) this.world).playSound(pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
				final float f = this.world.rand.nextFloat();
				if (f <= 0.5F) {
					this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX(), pos.getY(), pos.getZ(), 1D, 0D, 0D);
				} else if (f <= 0.85F) {
					this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.getX(), pos.getY(), pos.getZ(), 0D, 0D, 0D);
				} else {
					this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, pos.getX(), pos.getY(), pos.getZ(), 0D, 0D, 0D);
				}
			}
		}
	}
}
