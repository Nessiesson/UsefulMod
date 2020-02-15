package nessiesson.usefulmod.mixins;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TileEntityPiston.class)
public abstract class MixinTileEntityPiston extends TileEntity {
	@Unique
	private static final float USEFULMOD_MATH_NEXT_DOWN_OF_ONE = Math.nextDown(1F);

	@Shadow
	private float progress;

	/**
	 * @author nessie
	 * @reason lazy
	 */
	@Overwrite
	public float getProgress(float partialTicks) {
		if (this.tileEntityInvalid && Math.abs(this.progress - 1F) < 1E-5F) {
			return USEFULMOD_MATH_NEXT_DOWN_OF_ONE;
		}

		return Math.min(1F, (2F * this.progress + partialTicks) / 3F);
	}
}
