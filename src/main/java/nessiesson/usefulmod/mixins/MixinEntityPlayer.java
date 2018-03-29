package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase {

	public MixinEntityPlayer(World worldIn) {
		super(worldIn);
	}

	@Inject(method = "travel", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/EntityLivingBase;travel(FFF)V", shift = At.Shift.AFTER))
	private void onPlayerFlightTravel(float strafe, float vertical, float forward, CallbackInfo ci) {
		if (LiteModUsefulMod.config.isFlightInertiaCancellationEnabled && forward == 0.0F && strafe == 0.0F) {
			this.motionX = 0.0D;
			this.motionZ = 0.0D;
		}
	}
}
