package com.integral.anticlimacticlagacy.items;

import java.util.List;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.items.IBlessable;
import com.integral.anticlimacticlagacy.api.items.ITaintable;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

public class CosmicHeart extends ItemBase implements IBlessable, Vanishable {

	public CosmicHeart() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.EPIC).stacksTo(1));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "cosmic_heart"));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof Player && !entityIn.level.isClientSide) {
			Player player = (Player) entityIn;
			this.handleBlessable(stack, player);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (this.isBlessed(stack)) {
			ItemLoreHelper.addLocalizedString(tooltip, "tooltip.anticlimacticlagacy.blessed1");
			ItemLoreHelper.addLocalizedString(tooltip, "tooltip.anticlimacticlagacy.blessed2");
		}
	}

}
