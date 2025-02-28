package com.integral.anticlimacticlagacy.brewing;

import javax.annotation.Nonnull;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.resources.ResourceLocation;

/**
 * Variation of brewing recipe sensitive to NBT of input stack.
 * @author Integral
 */

public class SpecialBrewingRecipe extends AbstractBrewingRecipe {
	@Nonnull private final Ingredient input;
	@Nonnull private final Ingredient ingredient;
	@Nonnull private final ItemStack output;

	public SpecialBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
		this(input, ingredient, output, new ResourceLocation(AnticlimacticLagacy.MODID, ItemNBTHelper.getString(output, "AnticlimacticPotion", "unknown")));
	}

	public SpecialBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output, ResourceLocation name) {
		super(name);
		this.input = input;
		this.ingredient = ingredient;
		this.output = output;
	}

	@Override
	public boolean isInput(@Nonnull ItemStack stack) {

		if (stack != null) {
			for (ItemStack testStack : this.getInput().getItems()) {
				if (testStack.getItem() == stack.getItem() && PotionUtils.getPotion(testStack) == PotionUtils.getPotion(stack))
					return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		return this.isInput(input) && this.isIngredient(ingredient) ? this.getOutput().copy() : ItemStack.EMPTY;
	}

	public Ingredient getInput() {
		return this.input;
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	public ItemStack getOutput() {
		return this.output;
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		if (ingredient != null)
			return this.ingredient.test(ingredient);
		else
			return false;
	}

}
