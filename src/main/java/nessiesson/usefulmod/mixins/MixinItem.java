package nessiesson.usefulmod.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(Item.class)
public abstract class MixinItem {
	private static final IItemPropertyGetter PERFECT_SILK_GETTER = (stack, worldIn, entityIn) -> {
		final Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
		return map.containsKey(Enchantments.EFFICIENCY) && map.get(Enchantments.EFFICIENCY) == 5
				&& map.containsKey(Enchantments.UNBREAKING) && map.get(Enchantments.UNBREAKING) == 3
				&& map.containsKey(Enchantments.SILK_TOUCH)
				&& map.containsKey(Enchantments.MENDING) ? 1F : 0F;
	};
	private static final IItemPropertyGetter PERFECT_FORTUNE_GETTER = (stack, worldIn, entityIn) -> {
		final Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
		return map.containsKey(Enchantments.EFFICIENCY) && map.get(Enchantments.EFFICIENCY) == 5
				&& map.containsKey(Enchantments.FORTUNE) && map.get(Enchantments.FORTUNE) == 3
				&& map.containsKey(Enchantments.UNBREAKING) && map.get(Enchantments.UNBREAKING) == 3
				&& map.containsKey(Enchantments.MENDING) ? 1F : 0F;
	};

	@Shadow
	public abstract void addPropertyOverride(ResourceLocation key, IItemPropertyGetter getter);

	@Inject(method = "<init>", at = @At("RETURN"))
	private void addCustomOverrrides(CallbackInfo ci) {
		this.addPropertyOverride(new ResourceLocation("perfect_silk"), PERFECT_SILK_GETTER);
		this.addPropertyOverride(new ResourceLocation("perfect_fortune"), PERFECT_FORTUNE_GETTER);
	}
}
