/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * <p>
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * <p>
 * File Created @ [8 Sep 2013, 19:36:25 (GMT)]
 */
package nessiesson.usefulmod;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

//Stolen from Vazkii's Quark Forge mod.
public final class ItemNBTHelper {

	/**
	 * Checks if an ItemStack has a Tag Compound
	 **/
	public static boolean detectNBT(ItemStack stack) {
		return stack.hasTagCompound();
	}

	/**
	 * Tries to initialize an NBT Tag Compound in an ItemStack,
	 * this will not do anything if the stack already has a tag
	 * compound
	 **/
	public static void initNBT(ItemStack stack) {
		if (!detectNBT(stack))
			injectNBT(stack, new NBTTagCompound());
	}

	/**
	 * Injects an NBT Tag Compound to an ItemStack, no checks
	 * are made previously
	 **/
	public static void injectNBT(ItemStack stack, NBTTagCompound nbt) {
		stack.setTagCompound(nbt);
	}

	/**
	 * Gets the NBTTagCompound in an ItemStack. Tries to init it
	 * previously in case there isn't one present
	 **/
	public static NBTTagCompound getNBT(ItemStack stack) {
		initNBT(stack);
		return stack.getTagCompound();
	}

	public static boolean verifyExistance(ItemStack stack, String tag) {
		return !stack.isEmpty() && getNBT(stack).hasKey(tag);
	}

	/**
	 * If nullifyOnFail is true it'll return null if it doesn't find any
	 * compounds, otherwise it'll return a new one.
	 **/
	public static NBTTagCompound getCompound(ItemStack stack, String tag, boolean nullifyOnFail) {
		return verifyExistance(stack, tag) ? getNBT(stack).getCompoundTag(tag) : nullifyOnFail ? null : new NBTTagCompound();
	}
}