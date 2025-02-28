package com.integral.anticlimacticlagacy.brewing;

import java.util.HashMap;

import javax.annotation.Nonnull;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;
import com.integral.anticlimacticlagacy.helpers.PotionHelper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.resources.ResourceLocation;

/**
 * A recipe with one output and any amount of input-ingredient pairs.
 * @author Integral
 */

public class ComplexBrewingRecipe extends AbstractBrewingRecipe {
    @Nonnull private final HashMap<Ingredient, Ingredient> processingMappings;
    @Nonnull private final ItemStack output;

	public ComplexBrewingRecipe(HashMap<Ingredient, Ingredient> ingredientCompliances, ItemStack output) {
    	this(ingredientCompliances, output, new ResourceLocation(AnticlimacticLagacy.MODID, ItemNBTHelper.getString(output, "AnticlimacticPotion", "unknown")));
    }

    public ComplexBrewingRecipe(HashMap<Ingredient, Ingredient> ingredientCompliances, ItemStack output, ResourceLocation name) {
        super(name);
    	this.processingMappings = ingredientCompliances;
        this.output = output;
    }

    @Override
    public boolean isInput(@Nonnull ItemStack stack) {
        for (Ingredient ing : this.processingMappings.keySet()) {
        	if (this.isInput(stack, ing))
        		return true;
        }

        return false;
    }

    public boolean isInput(@Nonnull ItemStack stack, Ingredient ingredient) {
    	for (ItemStack testStack : ingredient.getItems()) {
    		if (testStack.getItem().equals(stack.getItem()) && PotionUtils.getPotion(testStack).equals(PotionUtils.getPotion(stack)) && PotionHelper.getAdvancedPotion(testStack).equals(PotionHelper.getAdvancedPotion(stack)))
    			return true;
    	}

    	return false;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
    	for (Ingredient ing : this.processingMappings.keySet()) {

    		if (this.isInput(input, ing)) {
    			if (this.processingMappings.get(ing).test(ingredient))
    				return this.output.copy();
    		}

    	}

    	return ItemStack.EMPTY;
    }

    @Override
    public boolean isIngredient(ItemStack stack) {
    	if (stack != null)
    	for (Ingredient ing : this.processingMappings.values()) {
    		if (ing.test(stack))
    			return true;
    	}

        return false;
    }

    public HashMap<Ingredient, Ingredient> getProcessingMappings() {
		return this.processingMappings;
	}

	public ItemStack getOutput() {
		return this.output;
	}
}