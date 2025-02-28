package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;
import com.integral.anticlimacticlagacy.triggers.UseUnholyGrailTrigger;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UnholyGrail extends ItemBase implements Vanishable {

	public UnholyGrail() {
		super(ItemBase.getDefaultProperties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "unholy_grail"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.unholyGrail1");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		if (!(entityLiving instanceof Player))
			return stack;

		Player player = (Player) entityLiving;

		if (!worldIn.isClientSide) {
			boolean isTheWorthyOne = SuperpositionHandler.isTheCursedOne(player) && AnticlimacticLagacy.forbiddenFruit.haveConsumedFruit(player);

			if (!isTheWorthyOne) {
				player.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 2, false, true));
				player.addEffect(new MobEffectInstance(MobEffects.POISON, 160, 1, false, true));
				player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 240, 0, false, true));
				player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1, false, true));
				player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 160, 2, false, true));
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 240, 0, false, true));

				UseUnholyGrailTrigger.INSTANCE.trigger((ServerPlayer) player, false);
			} else {
				player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1000/2, 2, false, true));
				player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1600/2, 1, false, true));
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2400/2, 1, false, true));
				player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2000/2, 1, false, true));
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400/2, 0, false, true));
				//player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0, false, true));

				UseUnholyGrailTrigger.INSTANCE.trigger((ServerPlayer) player, true);
			}
		}

		player.awardStat(Stats.ITEM_USED.get(this));

		return stack;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		playerIn.startUsingItem(handIn);
		return new InteractionResultHolder<>(InteractionResult.CONSUME, playerIn.getItemInHand(handIn));
	}

}
