package com.integral.anticlimacticlagacy.items;

import java.util.List;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.items.ICursed;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class EvilEssence extends ItemBase implements ICursed {

	public EvilEssence() {
		super(getDefaultProperties().rarity(Rarity.EPIC).stacksTo(8).fireResistant());
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "evil_essence"));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.evilEssence1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.evilEssence2");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
		ItemLoreHelper.indicateCursedOnesOnly(list);
	}

}
