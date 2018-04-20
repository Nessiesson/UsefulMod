package nessiesson.usefulmod.mixins;

import net.minecraft.tileentity.TileEntityBeacon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TileEntityBeacon.class)
public interface IMixinEntityBeacon {
	@Accessor
	boolean getIsComplete();
}
