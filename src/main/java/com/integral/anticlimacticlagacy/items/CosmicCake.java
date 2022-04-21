package com.integral.anticlimacticlagacy.items;

import java.util.List;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.GenericBlockItem;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CosmicCake extends GenericBlockItem {

	public CosmicCake() {
		super(AnticlimacticLagacy.cosmicCake, getDefaultProperties().stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(tooltip, "tooltip.anticlimacticlagacy.cosmicCake1");
			ItemLoreHelper.addLocalizedString(tooltip, "tooltip.anticlimacticlagacy.cosmicCake2");
		} else {
			ItemLoreHelper.addLocalizedString(tooltip, "tooltip.anticlimacticlagacy.holdShift");
		}
	}

}
