package com.integral.anticlimacticlagacy.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.brewing.ComplexBrewingRecipe;
import com.integral.anticlimacticlagacy.objects.AdvancedPotion;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

/**
 * Methods for interacting with advanced potion system.
 * Registering methods for them are also stored here.
 * @author Integral
 */

public class PotionHelper {

	public static int getColor(ItemStack stack) {

		if (PotionHelper.isAdvancedPotion(stack))
			if (PotionHelper.getEffects(stack) != null && PotionHelper.getEffects(stack).size() > 0)
				return PotionUtils.getColor(PotionHelper.getEffects(stack));

		return PotionUtils.getColor(stack);

	};

	public static boolean isAdvancedPotion(ItemStack stack) {
		if (ItemNBTHelper.verifyExistance(stack, "AnticlimacticPotion"))
			return true;


		return false;
	}

	public static List<MobEffectInstance> getEffects(ItemStack stack) {
		if (PotionHelper.isAdvancedPotion(stack)) {
			AdvancedPotion potion = PotionHelper.getAdvancedPotion(ItemNBTHelper.getString(stack, "AnticlimacticPotion", "nothing"));
			if (potion != null)
				return potion.getEffects();
		}

		return new ArrayList<MobEffectInstance>();
	};

	public static AdvancedPotion getAdvancedPotion(ItemStack stack) {
		if (PotionHelper.isAdvancedPotion(stack))
			return PotionHelper.getAdvancedPotion(ItemNBTHelper.getString(stack, "AnticlimacticPotion", "nothing"));

		return AnticlimacticLagacy.EMPTY;
	}

	public static ItemStack setAdvancedPotion(ItemStack stack, AdvancedPotion potion) {
		ItemNBTHelper.setString(stack, "AnticlimacticPotion", potion.getId());
		return stack;
	}

	public static ItemStack setAdvancedPotion(ItemStack stack, String id) {
		ItemNBTHelper.setString(stack, "AnticlimacticPotion", id);
		return stack;
	}

	public static AdvancedPotion getAdvancedPotion(String identifier) {

		for (AdvancedPotion potion : AnticlimacticLagacy.ultimatePotionTypes) {
			if (potion.getId().equals(identifier))
				return potion;
		}

		for (AdvancedPotion potion : AnticlimacticLagacy.commonPotionTypes) {
			if (potion.getId().equals(identifier))
				return potion;
		}


		return AnticlimacticLagacy.EMPTY;
	}

	public static HashMap<Ingredient, Ingredient> constructIngredientMap(Ingredient... ingredients) {
		HashMap<Ingredient, Ingredient> returnMap = new HashMap<Ingredient, Ingredient>();

		if (ingredients.length % 2 != 0)
			throw new IllegalArgumentException("Uneven number of ingredients passed. This must not be!");

		for (int counter = 0; counter < ingredients.length; counter += 2) {
			returnMap.put(ingredients[counter], ingredients[counter+1]);
		}

		return returnMap;
	}

	public static ItemStack createAdvancedPotion(Item item, AdvancedPotion potion) {
		return PotionHelper.setAdvancedPotion(new ItemStack(item), potion);
	}

	public static ItemStack createVanillaPotion(Item item, Potion potion) {
		return PotionUtils.setPotion(new ItemStack(item), potion);
	}

	public static void registerCommonPotions() {

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.AWKWARD)),
								Ingredient.of(Items.QUARTZ)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.HASTE)),
								Ingredient.of(Items.REDSTONE)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.LONG_HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.HASTE)),
								Ingredient.of(Items.GLOWSTONE_DUST)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.STRONG_HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.AWKWARD)),
								Ingredient.of(Items.QUARTZ),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.HASTE)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.HASTE)),
								Ingredient.of(Items.REDSTONE),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.LONG_HASTE)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.LONG_HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.HASTE)),
								Ingredient.of(Items.GLOWSTONE_DUST),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.STRONG_HASTE)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.STRONG_HASTE)
						));



		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.AWKWARD)),
								Ingredient.of(Items.QUARTZ),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.HASTE)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.HASTE)),
								Ingredient.of(Items.REDSTONE),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.LONG_HASTE)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.LONG_HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.HASTE)),
								Ingredient.of(Items.GLOWSTONE_DUST),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.STRONG_HASTE)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.STRONG_HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.MUNDANE)),
								Ingredient.of(Items.BLAZE_ROD)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.MOLTEN_HEART)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.MOLTEN_HEART)),
								Ingredient.of(Items.REDSTONE)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.LONG_MOLTEN_HEART)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.MUNDANE)),
								Ingredient.of(Items.BLAZE_ROD),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.MOLTEN_HEART)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.MOLTEN_HEART)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.MOLTEN_HEART)),
								Ingredient.of(Items.REDSTONE),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.LONG_MOLTEN_HEART)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.LONG_MOLTEN_HEART)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.MUNDANE)),
								Ingredient.of(Items.BLAZE_ROD),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.MOLTEN_HEART)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.MOLTEN_HEART)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.MOLTEN_HEART)),
								Ingredient.of(Items.REDSTONE),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.LONG_MOLTEN_HEART)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.LONG_MOLTEN_HEART)
						));
	}


	public static void registerBasicUltimatePotions() {
		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_NIGHT_VISION)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_NIGHT_VISION)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_INVISIBILITY)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_INVISIBILITY)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_FIRE_RESISTANCE)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_FIRE_RESISTANCE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.STRONG_HEALING)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_HEALING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.STRONG_HARMING)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_HARMING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_SLOW_FALLING)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_SLOW_FALLING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_WEAKNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_WEAKNESS)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_WATER_BREATHING)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_WATER_BREATHING)
						));


		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_STRENGTH)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.STRONG_STRENGTH)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_STRENGTH)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_LEAPING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.STRONG_LEAPING)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_LEAPING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_SWIFTNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.STRONG_SWIFTNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_SWIFTNESS)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_SLOWNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.STRONG_SLOWNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_SLOWNESS)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_TURTLE_MASTER)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.STRONG_TURTLE_MASTER)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_TURTLE_MASTER)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_POISON)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.STRONG_POISON)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_POISON)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.LONG_REGENERATION)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.POTION, Potions.STRONG_REGENERATION)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_REGENERATION)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.LONG_HASTE)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.STRONG_HASTE)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionBase, AnticlimacticLagacy.LONG_MOLTEN_HEART)),
								Ingredient.of(AnticlimacticLagacy.astralDust)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_MOLTEN_HEART)
						));
	}



	public static void registerSplashUltimatePotions() {
		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_NIGHT_VISION)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_NIGHT_VISION)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_NIGHT_VISION)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_INVISIBILITY)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_INVISIBILITY)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_INVISIBILITY)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_FIRE_RESISTANCE)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_FIRE_RESISTANCE)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_FIRE_RESISTANCE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.STRONG_HEALING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_HEALING)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_HEALING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.STRONG_HARMING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_HARMING)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_HARMING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_SLOW_FALLING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_SLOW_FALLING)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_SLOW_FALLING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_WEAKNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_WEAKNESS)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_WEAKNESS)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_WATER_BREATHING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_WATER_BREATHING)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_WATER_BREATHING)
						));




		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_STRENGTH)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.STRONG_STRENGTH)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_STRENGTH)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_STRENGTH)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_LEAPING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.STRONG_LEAPING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_LEAPING)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_LEAPING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_SWIFTNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.STRONG_SWIFTNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_SWIFTNESS)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_SWIFTNESS)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_SLOWNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.STRONG_SLOWNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_SLOWNESS)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_SLOWNESS)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_TURTLE_MASTER)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.STRONG_TURTLE_MASTER)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_TURTLE_MASTER)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_TURTLE_MASTER)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_POISON)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.STRONG_POISON)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_POISON)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_POISON)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.LONG_REGENERATION)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.SPLASH_POTION, Potions.STRONG_REGENERATION)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_REGENERATION)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_REGENERATION)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.LONG_HASTE)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.STRONG_HASTE)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_HASTE)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionSplash, AnticlimacticLagacy.LONG_MOLTEN_HEART)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionBase, AnticlimacticLagacy.ULTIMATE_MOLTEN_HEART)),
								Ingredient.of(Items.GUNPOWDER)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_MOLTEN_HEART)
						));
	}



	public static void registerLingeringUltimatePotions() {
		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_NIGHT_VISION)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_NIGHT_VISION)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_NIGHT_VISION)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_INVISIBILITY)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_INVISIBILITY)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_INVISIBILITY)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_FIRE_RESISTANCE)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_FIRE_RESISTANCE)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_FIRE_RESISTANCE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.STRONG_HEALING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_HEALING)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_HEALING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.STRONG_HARMING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_HARMING)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_HARMING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_SLOW_FALLING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_SLOW_FALLING)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_SLOW_FALLING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_WEAKNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_WEAKNESS)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_WEAKNESS)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_WATER_BREATHING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_WATER_BREATHING)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_WATER_BREATHING)
						));




		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_STRENGTH)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.STRONG_STRENGTH)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_STRENGTH)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_STRENGTH)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_LEAPING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.STRONG_LEAPING)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_LEAPING)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_LEAPING)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_SWIFTNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.STRONG_SWIFTNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_SWIFTNESS)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_SWIFTNESS)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_SLOWNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.STRONG_SLOWNESS)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_SLOWNESS)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_SLOWNESS)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_TURTLE_MASTER)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.STRONG_TURTLE_MASTER)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_TURTLE_MASTER)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_TURTLE_MASTER)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_POISON)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.STRONG_POISON)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_POISON)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_POISON)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.LONG_REGENERATION)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createVanillaPotion(Items.LINGERING_POTION, Potions.STRONG_REGENERATION)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_REGENERATION)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_REGENERATION)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.LONG_HASTE)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.STRONG_HASTE)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_HASTE)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_HASTE)
						));

		BrewingRecipeRegistry.addRecipe(
				new ComplexBrewingRecipe(
						PotionHelper.constructIngredientMap(
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.commonPotionLingering, AnticlimacticLagacy.LONG_MOLTEN_HEART)),
								Ingredient.of(AnticlimacticLagacy.astralDust),
								Ingredient.of(PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionSplash, AnticlimacticLagacy.ULTIMATE_MOLTEN_HEART)),
								Ingredient.of(Items.DRAGON_BREATH)
								),
						PotionHelper.createAdvancedPotion(AnticlimacticLagacy.ultimatePotionLingering, AnticlimacticLagacy.ULTIMATE_MOLTEN_HEART)
						));
	}

}
