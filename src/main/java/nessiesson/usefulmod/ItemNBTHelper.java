package nessiesson.usefulmod;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

final class ItemNBTHelper {
	static NBTTagCompound getCompoundOrNull(ItemStack stack, String tag) {
		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null && compound.hasKey(tag)) {
			return compound.getCompoundTag(tag);
		} else {
			return null;
		}
	}
}