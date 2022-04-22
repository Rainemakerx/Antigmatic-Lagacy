package com.integral.anticlimacticlagacy.helpers;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;

import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;

public class AnticlimacticEnchantmentHelper {

	public static boolean hasCustomCrossbowEnchantments(ItemStack crossbowStack) {
		return (EnchantmentHelper.getItemEnchantmentLevel(anticlimacticlagacy.ceaselessEnchantment, crossbowStack) > 0 || EnchantmentHelper.getItemEnchantmentLevel(anticlimacticlagacy.sharpshooterEnchantment, crossbowStack) > 0);
	}

	public static boolean hasSharpshooterEnchantment(ItemStack stack) {
		return AnticlimacticEnchantmentHelper.getSharpshooterLevel(stack) > 0;
	}

	public static boolean hasCeaselessEnchantment(ItemStack stack) {
		return AnticlimacticEnchantmentHelper.getCeaselessLevel(stack) > 0;
	}

	public static boolean hasNemesisCurseEnchantment(ItemStack stack) {
		return AnticlimacticEnchantmentHelper.getNemesisCurseLevel(stack) > 0;
	}

	public static int getSharpshooterLevel(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(anticlimacticlagacy.sharpshooterEnchantment, stack);
	}

	public static int getCeaselessLevel(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(anticlimacticlagacy.ceaselessEnchantment, stack);
	}

	public static int getNemesisCurseLevel(ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(anticlimacticlagacy.nemesisCurse, stack);
	}
}
