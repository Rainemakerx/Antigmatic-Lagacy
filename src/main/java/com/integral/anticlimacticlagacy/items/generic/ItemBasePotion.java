package com.integral.anticlimacticlagacy.items.generic;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.item.Item.Properties;

public abstract class ItemBasePotion extends ItemBase {

	public ItemBasePotion() {
		this(getDefaultProperties().stacksTo(1));
	}

	public ItemBasePotion(Properties props) {
		super(props);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity living) {
		if (living instanceof Player) {
			Player player =  (Player) living;

			this.onConsumed(worldIn, player, stack);

			if (player instanceof ServerPlayer) {
				CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
			}

			if (!player.getAbilities().instabuild) {
				stack.shrink(1);

				if (stack.isEmpty())
					return new ItemStack(Items.GLASS_BOTTLE);

				player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
			}

		}

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
		if (this.canDrink(worldIn, playerIn, playerIn.getItemInHand(handIn))) {
			playerIn.startUsingItem(handIn);
			return super.use(worldIn, playerIn, handIn);
		} else
			return new InteractionResultHolder<>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	public boolean canDrink(Level world, Player player, ItemStack potion) {
		return true;
	}

	public void onConsumed(Level worldIn, Player player, ItemStack potion) {
		// NO-OP
	}

}
