package nessiesson.usefulmod.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {
	@Unique
	private double usefulmodD0;
	@Unique
	private double usefulmodD1;
	@Unique
	private double usefulmodD2;

	public MixinEntityLivingBase(World world) {
		super(world);
	}

	@Inject(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setPosition(DDD)V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void keepCopies(CallbackInfo ci, double d0, double d1, double d2, double d3) {
		this.usefulmodD0 = d0;
		this.usefulmodD1 = d1;
		this.usefulmodD2 = d2;
	}

	@Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setPosition(DDD)V"))
	private void fixSquidAndWitherMovement(EntityLivingBase entity, double x, double y, double z) {
		if (entity instanceof EntitySquid || entity instanceof EntityWither) {
			entity.move(MoverType.SELF, this.usefulmodD0 - this.posX, this.usefulmodD1 - this.posY, this.usefulmodD2 - this.posZ);
		} else {
			entity.setPosition(x, y, z);
		}
	}
}
