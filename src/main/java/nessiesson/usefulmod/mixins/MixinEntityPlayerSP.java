package nessiesson.usefulmod.mixins;

import com.mojang.authlib.GameProfile;
import nessiesson.usefulmod.LiteModUsefulMod;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {
	public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
		super(worldIn, playerProfile);
	}

	@Inject(method = "canUseCommand", at = @At("HEAD"), cancellable = true)
	private void overrideCommandPermissions(int permLevel, String commandName, CallbackInfoReturnable<Boolean> cir) {
		if (LiteModUsefulMod.config.alwaysSingleplayerCheats) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "isOpenBlockSpace", at = @At("HEAD"), cancellable = true)
	private void elytraHax(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(LiteModUsefulMod.config.elytraHax && !this.world.getBlockState(pos).isNormalCube());
	}

	@Override
	public void turn(float yaw, float pitch) {
		final float f = this.rotationPitch;
		final float f1 = this.rotationYaw;
		this.rotationYaw += yaw * 0.15F;

		if (LiteModUsefulMod.config.snapAim && !(this.getRidingEntity() instanceof EntityBoat)) {
			final float r = this.rotationYaw - this.rotationYaw % 90F;
			final float r2 = Math.abs(r - rotationYaw);
			if (r2 < 1.5F) {
				this.rotationYaw = (int) r;
			} else if (r2 > 88.5F) {
				this.rotationYaw = (int) (this.rotationYaw > 0 ? r + 90F : r - 90F);
			}
		}

		this.rotationPitch = MathHelper.clamp(this.rotationPitch - pitch * 0.15F, -90F, 90F);
		this.prevRotationPitch += this.rotationPitch - f;
		this.prevRotationYaw += this.rotationYaw - f1;

		if (this.getRidingEntity() != null) {
			this.getRidingEntity().applyOrientationToEntity(this);
		}
	}
}
