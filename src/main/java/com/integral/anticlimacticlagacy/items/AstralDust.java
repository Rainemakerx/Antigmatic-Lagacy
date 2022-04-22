package com.integral.anticlimacticlagacy.items;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;

public class AstralDust extends ItemBase {

	public AstralDust() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.EPIC));
		this.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, "astral_dust"));
	}

}
