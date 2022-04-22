package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.api.items.ICursed;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseFood;
import com.integral.anticlimacticlagacy.objects.TransientPlayerData;
import com.integral.anticlimacticlagacy.triggers.ForbiddenFruitTrigger;
import com.integral.omniconfig.Configuration;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AstralFruit extends ItemBaseFood implements ICursed, Vanishable {

	public AstralFruit() {
		super(getDefaultProperties().rarity(Rarity.EPIC).fireResistant(),
				new FoodProperties.Builder().nutrition(5).saturationMod(20).alwaysEat().build());
		this.setRegistryName(new ResourceLocation(anticlimacticlagacy.MODID, "astral_fruit"));
	}


	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.astralFruit1");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
		ItemLoreHelper.indicateCursedOnesOnly(list);
	}

	@Override
	public boolean isFoil(ItemStack pStack) {
		return true;
	}

	@Override
	public void onConsumed(Level worldIn, Player player, ItemStack food) {
		if (player instanceof ServerPlayer playerMP) {
			SuperpositionHandler.setPersistentBoolean(playerMP, "ConsumedAstralFruit", true);
			SuperpositionHandler.unlockSpecialSlot("ring", playerMP);
			playerMP.level.playSound(null, playerMP.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1F);

			double multiplier = 1;

			player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,            (int) (3000 * multiplier), 3, false, true));
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,         (int) (3000 * multiplier), 2, false, true));
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,          (int) (4000 * multiplier), 3, false, true));
			player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, (int) (5000 * multiplier), 0, false, true));
		}
	}

	@Override
	public boolean canEat(Level world, Player player, ItemStack food) {
		return SuperpositionHandler.isTheCursedOne(player);
	}

}
