package com.integral.anticlimacticlagacy.items.generic;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import net.minecraft.world.item.Item.Properties;

public class GenericBlockItem extends BlockItem {

	public GenericBlockItem(Block blockIn) {
		super(blockIn, GenericBlockItem.getDefaultProperties());
		this.setRegistryName(blockIn.getRegistryName());
	}

	public GenericBlockItem(Block blockIn, Properties props) {
		super(blockIn, props);
		this.setRegistryName(blockIn.getRegistryName());
	}

	public static Properties getDefaultProperties() {
		Properties props = new Item.Properties();

		props.tab(AnticlimacticLagacy.anticlimacticTab);
		props.stacksTo(64);
		props.rarity(Rarity.COMMON);

		return props;
	}

}
