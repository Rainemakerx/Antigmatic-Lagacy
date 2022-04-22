package com.integral.anticlimacticlagacy.items;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseCurio;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

public class UnwitnessedAmulet extends ItemBaseCurio {

	public UnwitnessedAmulet() {
		super(ItemBaseCurio.getDefaultProperties().rarity(Rarity.UNCOMMON).fireResistant());
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "unwitnessed_amulet"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.unwitnessedAmuletShift1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.unwitnessedAmuletShift2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.unwitnessedAmuletShift3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.unwitnessedAmuletShift4");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.unwitnessedAmuletHoldShift");
		}
	}

	@Override
	public boolean canEquip(SlotContext context, ItemStack stack) {
		return false;
	}

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
		return tooltips;
	}

}