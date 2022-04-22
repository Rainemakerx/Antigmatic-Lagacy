package com.integral.anticlimacticlagacy.items;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;

public class DarkestScroll extends ItemBase {

	public DarkestScroll() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.UNCOMMON).stacksTo(1));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "darkest_scroll"));
	}

}
