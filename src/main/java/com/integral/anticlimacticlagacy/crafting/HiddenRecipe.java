package com.integral.anticlimacticlagacy.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.google.gson.JsonObject;
import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.items.ITaintable;
import com.integral.anticlimacticlagacy.config.OmniconfigHandler;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;
import com.integral.omniconfig.wrappers.Omniconfig;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class HiddenRecipe extends CustomRecipe {
	static final SimpleRecipeSerializer<HiddenRecipe> SERIALIZER = new SimpleRecipeSerializer<>(HiddenRecipe::new);
	static final Map<ItemStack[][], ItemStack> RECIPES = new HashMap<>();

	public HiddenRecipe(ResourceLocation id) {
		super(id);
	}

	@Override
	public ItemStack assemble(CraftingContainer inv) {
		ItemStack output = ItemStack.EMPTY;

		Optional<CompoundTag> amuletNBT = Optional.empty();

		recipes: for (Map.Entry<ItemStack[][], ItemStack> entry : RECIPES.entrySet()) {
			for (int r = 0; r < 3; r++) {
				for (int i = 0; i < 3; i++) {
					ItemStack slotStack = inv.getItem(3 * r + i);

					if (slotStack.getItem() != entry.getKey()[r][i].getItem()) {
						continue recipes;
					} else {
						if (slotStack.is(AnticlimacticLagacy.anticlimacticAmulet) || slotStack.is(AnticlimacticLagacy.ascensionAmulet)) {
							amuletNBT = Optional.of(slotStack.getTag().copy());
						}
					}
				}
			}

			output = entry.getValue().copy();
			amuletNBT.ifPresent(output::setTag);
			break;
		}

		if (!OmniconfigHandler.isItemEnabled(output.getItem())) {
			output = ItemStack.EMPTY;
		}

		return output;
	}

	@Override
	public boolean matches(CraftingContainer inv, Level world) {
		recipes: for (ItemStack[][] array : RECIPES.keySet()) {
			for (int r = 0; r < 3; r++) {
				for (int i = 0; i < 3; i++) {
					ItemStack slotStack = inv.getItem(3 * r + i);

					if (slotStack.getItem() != array[r][i].getItem()) {
						continue recipes;
					}
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 9;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	public static void addRecipe(ItemStack output, ItemStack... inputs) {
		ItemStack[][] array = new ItemStack[][] {
			{ inputs[0], inputs[1], inputs[2] },
			{ inputs[3], inputs[4], inputs[5] },
			{ inputs[6], inputs[7], inputs[8] }
		};
		RECIPES.put(array, output);
	}

	public static Entry<ItemStack[][], ItemStack> getRecipe(ResourceLocation output) {
		return RECIPES.entrySet().stream().filter(entry -> entry.getValue().getItem().getRegistryName()
				.equals(output)).findFirst().get();
	}

}
