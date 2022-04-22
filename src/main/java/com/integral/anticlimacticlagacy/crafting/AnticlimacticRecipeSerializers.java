package com.integral.anticlimacticlagacy.crafting;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.objects.EnabledCondition;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AnticlimacticRecipeSerializers {
	public static final RecipeSerializer<MendingMixtureRepairRecipe> CRAFTING_MENDING_MIXTURE_REPAIR = MendingMixtureRepairRecipe.SERIALIZER;
	public static final RecipeSerializer<EnchantmentTransposingRecipe> ENCHANTMENT_TRANSPOSING = EnchantmentTransposingRecipe.SERIALIZER;
	public static final RecipeSerializer<OblivionStoneCombineRecipe> OBLIVION_STONE_COMBINE = OblivionStoneCombineRecipe.SERIALIZER;
	public static final RecipeSerializer<BindToPlayerRecipe> BIND_TO_PLAYER = BindToPlayerRecipe.SERIALIZER;
	public static final RecipeSerializer<ShapelessNoReturnRecipe> SHAPELESS_NO_RETURN = new ShapelessNoReturnRecipe.Serializer();
	public static final RecipeSerializer<CursedShapedRecipe> CURSED_SHAPED = new CursedShapedRecipe.Serializer();
	public static final RecipeSerializer<BlessedShapedRecipe> BLESSED_SHAPED = new BlessedShapedRecipe.Serializer();

	@SubscribeEvent
	public void onRegisterSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
		anticlimacticlagacy.logger.info("Initializing recipe serializers registration...");
		CraftingHelper.register(EnabledCondition.Serializer.INSTANCE);

		event.getRegistry().register(CRAFTING_MENDING_MIXTURE_REPAIR.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, "mending_mixture_repair")));
		event.getRegistry().register(SHAPELESS_NO_RETURN.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, "shapeless_no_return_craft")));
		event.getRegistry().register(OBLIVION_STONE_COMBINE.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, "oblivion_stone_combine")));
		event.getRegistry().register(ENCHANTMENT_TRANSPOSING.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, "enchantment_transposing")));
		event.getRegistry().register(BIND_TO_PLAYER.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, "bind_to_player")));
		event.getRegistry().register(CURSED_SHAPED.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, "crafting_shaped_cursed")));
		event.getRegistry().register(BLESSED_SHAPED.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, "crafting_shaped_blessed")));
		anticlimacticlagacy.logger.info("Recipe serializers registered successfully.");
	}
}
