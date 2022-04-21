package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.api.items.ICursed;
import com.integral.anticlimacticlagacy.api.items.IEldritch;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseCurio;
import com.integral.omniconfig.Configuration;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;

public class DesolationRing extends ItemBaseCurio implements IEldritch {

	public DesolationRing() {
		super(getDefaultProperties().rarity(Rarity.EPIC));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "desolation_ring"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.desolationRing1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.desolationRing2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.indicateWorthyOnesOnly(list);
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.eternallyBound1");

			if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCreative()) {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.eternallyBound2_creative");
			} else {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.eternallyBound2");
			}

			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.indicateCursedOnesOnly(list);
		}
	}

	@Override
	public boolean canUnequip(SlotContext context, ItemStack stack) {
		if (context.entity() instanceof Player player && player.isCreative())
			return super.canUnequip(context, stack);
		else
			return false;
	}

	@Override
	public boolean canEquipFromUse(SlotContext context, ItemStack stack) {
		return false;
	}

	@Override
	public DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
		return DropRule.ALWAYS_KEEP;
	}

}
