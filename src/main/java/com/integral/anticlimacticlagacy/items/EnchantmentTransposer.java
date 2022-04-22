package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnchantmentTransposer extends ItemBase {

	public EnchantmentTransposer() {
		super(ItemBase.getDefaultProperties().stacksTo(1).rarity(Rarity.UNCOMMON));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "enchantment_transposer"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.enchantmentTransposer1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.enchantmentTransposer2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.enchantmentTransposer3");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

	}

}
