package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.api.items.ICursed;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseCurio;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class CursedScroll extends ItemBaseCurio implements ICursed {
	public static Omniconfig.PerhapsParameter damageBoost;
	public static Omniconfig.PerhapsParameter miningBoost;
	public static Omniconfig.PerhapsParameter regenBoost;

	@SubscribeConfig
	public static void onConfig(OmniconfigWrapper builder) {
		builder.pushPrefix("CursedScroll");

		damageBoost = builder
				.comment("Damage increase provided by Scroll of a Thousand Curses for each curse, as percentage.")
				.getPerhaps("DamageBoost", 4);

		miningBoost = builder
				.comment("Mining speed increase provided by Scroll of a Thousand Curses for each curse, as percentage.")
				.getPerhaps("MiningBoost", 7);

		regenBoost = builder
				.comment("Health regeneration increase provided by Scroll of a Thousand Curses for each curse, as percentage.")
				.getPerhaps("RegenBoost", 4);

		builder.popPrefix();
	}

	public CursedScroll() {
		super(ItemBaseCurio.getDefaultProperties().rarity(Rarity.EPIC));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "cursed_scroll"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cursed_scroll1", ChatFormatting.GOLD, damageBoost + "%");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cursed_scroll2", ChatFormatting.GOLD, miningBoost + "%");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cursed_scroll3", ChatFormatting.GOLD, regenBoost + "%");

			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cursed_scroll4");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cursed_scroll5");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

		if (Minecraft.getInstance().player != null)
			if (SuperpositionHandler.getCurioStack(Minecraft.getInstance().player, this) == stack) {
				int curses = SuperpositionHandler.getCurseAmount(Minecraft.getInstance().player);

				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cursed_scroll6");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cursed_scroll7", ChatFormatting.GOLD, (damageBoost.getValue().asPercentage()*curses) + "%");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cursed_scroll8", ChatFormatting.GOLD, (miningBoost.getValue().asPercentage()*curses) + "%");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cursed_scroll9", ChatFormatting.GOLD, (regenBoost.getValue().asPercentage()*curses) + "%");

			}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
		ItemLoreHelper.indicateCursedOnesOnly(list);
	}

	@Override
	public boolean canEquip(SlotContext context, ItemStack stack) {
		return super.canEquip(context, stack) && context.entity() instanceof Player player && SuperpositionHandler.isTheCursedOne(player);
	}

}