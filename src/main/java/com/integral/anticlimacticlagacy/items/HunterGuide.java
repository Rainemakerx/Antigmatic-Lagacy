package com.integral.anticlimacticlagacy.items;

import java.util.List;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HunterGuide extends ItemBase implements Vanishable {

	public static Omniconfig.IntParameter effectiveDistance;
	public static Omniconfig.PerhapsParameter synergyDamageReduction;

	@SubscribeConfig
	public static void onConfig(OmniconfigWrapper builder) {
		builder.pushPrefix("HunterGuide");

		effectiveDistance = builder
				.comment("The range in which Guide to Feral Hunt will redirect damage from pet to it's owner.")
				.getInt("EffectiveDistance", 24);

		synergyDamageReduction = builder
				.comment("The percantage subtracted from damage redirected by Guide to Feral Hunt, if Guide to Animal Companionship is also possessed.")
				.max(100)
				.getPerhaps("SynergyDamageReduction", 50);

		builder.popPrefix();
	}

	public HunterGuide() {
		super(getDefaultProperties().stacksTo(1).rarity(Rarity.RARE));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "hunter_guide"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.hunterGuide1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.hunterGuide2", ChatFormatting.GOLD, effectiveDistance);
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.hunterGuide3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.hunterGuide4");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.hunterGuide5", ChatFormatting.GOLD, synergyDamageReduction + "%");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

	}

}
