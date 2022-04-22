package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;
import com.integral.anticlimacticlagacy.items.generic.ItemBasePotion;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AntiforbiddenPotion extends ItemBasePotion {

	public AntiforbiddenPotion() {
		super(getDefaultProperties().stacksTo(1).rarity(Rarity.EPIC));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "antiforbidden_potion"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.antiforbiddenPotion1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.antiforbiddenPotion2");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}
	}

	@Override
	public void onConsumed(Level worldIn, Player player, ItemStack potion) {
		AnticlimacticLagacy.forbiddenFruit.defineConsumedFruit(player, false);
	}

	@Override
	public boolean canDrink(Level world, Player player, ItemStack potion) {
		return AnticlimacticLagacy.forbiddenFruit.haveConsumedFruit(player);
	}



}
