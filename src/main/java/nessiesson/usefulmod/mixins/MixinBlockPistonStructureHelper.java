package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.block.state.BlockPistonStructureHelper;

@Mixin(BlockPistonStructureHelper.class)
public class MixinBlockPistonStructureHelper {
	@ModifyConstant(method = "addBlockLine", constant = @Constant(intValue = 12), expect = 3)
	private int pushLimit(int value) {
		return 12;
	}
}
