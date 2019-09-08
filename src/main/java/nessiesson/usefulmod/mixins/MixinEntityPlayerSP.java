package nessiesson.usefulmod.mixins;

import com.mojang.authlib.GameProfile;
import nessiesson.usefulmod.config.Config;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;
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
		if (Config.INSTANCE.isAlwaysSingleplayerCheatedEnabled()) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "isOpenBlockSpace", at = @At("HEAD"), cancellable = true)
	private void adjustIsOpenBlockSpace(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(!this.world.getBlockState(pos).isNormalCube());
	}
}
