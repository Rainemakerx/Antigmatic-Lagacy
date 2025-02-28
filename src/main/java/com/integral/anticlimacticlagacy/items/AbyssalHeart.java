package com.integral.anticlimacticlagacy.items;

import java.util.List;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.items.IBlessable;
import com.integral.anticlimacticlagacy.api.items.ICursed;
import com.integral.anticlimacticlagacy.api.items.IEldritch;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AbyssalHeart extends ItemBase implements IEldritch {

	public AbyssalHeart() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.EPIC).stacksTo(1));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "abyssal_heart"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.indicateWorthyOnesOnly(list);
		} else {
			//if (SuperpositionHandler.isTheWorthyOne(Minecraft.getInstance().player)) {
			list.add(new TranslatableComponent("tooltip.anticlimacticlagacy.abyssalHeart1"));
			list.add(new TranslatableComponent("tooltip.anticlimacticlagacy.abyssalHeart2"));
			list.add(new TranslatableComponent("tooltip.anticlimacticlagacy.abyssalHeart3"));
			list.add(new TranslatableComponent("tooltip.anticlimacticlagacy.abyssalHeart4"));
			//} else {
			//	list.add(new TranslatableComponent("tooltip.anticlimacticlagacy.abyssalHeart1_obf"));
			//	list.add(new TranslatableComponent("tooltip.anticlimacticlagacy.abyssalHeart2_obf"));
			//	list.add(new TranslatableComponent("tooltip.anticlimacticlagacy.abyssalHeart3_obf"));
			//	list.add(new TranslatableComponent("tooltip.anticlimacticlagacy.abyssalHeart4_obf"));
			//}

			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.indicateCursedOnesOnly(list);
		}
	}

}
