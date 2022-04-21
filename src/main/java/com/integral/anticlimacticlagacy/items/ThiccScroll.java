package com.integral.anticlimacticlagacy.items;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.resources.ResourceLocation;

public class ThiccScroll extends ItemBase {

	public ThiccScroll() {
		super(ItemBase.getDefaultProperties().stacksTo(16));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "thicc_scroll"));
	}

}
