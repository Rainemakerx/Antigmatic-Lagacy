package com.integral.anticlimacticlagacy.items;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;

public class PlaceholderItem extends ItemBase {

	public PlaceholderItem(String name, Rarity rarity) {
		this(name, rarity, 1);
	}

	public PlaceholderItem(String name, Rarity rarity, int maxStackSize) {
		super(ItemBase.getDefaultProperties().rarity(rarity).stacksTo(maxStackSize));
		this.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, name));
	}

}
