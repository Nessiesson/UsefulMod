package nessiesson.usefulmod.mixins;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mixin(World.class)
public abstract class MixinWorld {
	@Shadow
	@Final
	private List<TileEntity> tileEntitiesToBeRemoved;
	@Shadow
	@Final
	public List<TileEntity> tickableTileEntities;
	@Shadow
	@Final
	public List<TileEntity> loadedTileEntityList;

	@Inject(method = "updateEntities", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;tileEntitiesToBeRemoved:Ljava/util/List;", ordinal = 0))
	private void tileEntityRemovalFix(CallbackInfo ci) {
		if (!this.tileEntitiesToBeRemoved.isEmpty()) {
			Set<TileEntity> remove = Collections.newSetFromMap(new java.util.IdentityHashMap<>());
			remove.addAll(this.tileEntitiesToBeRemoved);
			this.tickableTileEntities.removeAll(remove);
			this.loadedTileEntityList.removeAll(remove);
			this.tileEntitiesToBeRemoved.clear();
		}
	}
}
