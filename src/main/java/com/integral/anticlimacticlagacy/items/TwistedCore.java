package com.integral.anticlimacticlagacy.items;

import java.util.List;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.items.ICursed;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class TwistedCore extends ItemBase implements ICursed, Vanishable {

	public TwistedCore() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.EPIC).stacksTo(1));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "twisted_core"));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		ItemLoreHelper.indicateCursedOnesOnly(tooltip);
	}

}
