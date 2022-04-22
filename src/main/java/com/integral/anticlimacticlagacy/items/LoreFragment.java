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

public class LoreFragment extends ItemBase {

	public LoreFragment() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.UNCOMMON).stacksTo(16));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "lore_fragment"));

		// TODO Lore Fragment copying recipe
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {

		if (!stack.getOrCreateTagElement("display").getString("Name").equals("") || stack.getOrCreateTagElement("display").getList("Lore", 8).size() != 0)
			return;

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreFragment1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreFragment2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreFragment3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreFragment4");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

	}

}
