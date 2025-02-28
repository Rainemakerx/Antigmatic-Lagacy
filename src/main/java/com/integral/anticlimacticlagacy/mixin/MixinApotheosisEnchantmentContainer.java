package com.integral.anticlimacticlagacy.mixin;

import java.lang.reflect.Method;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;

/**
 * No, if I said player gets more enchantments - they will get their enchantments.
 * @author Integral
 */

@Pseudo
@Mixin(targets = "shadows.apotheosis.ench.table.ApothEnchantContainer")
public class MixinApotheosisEnchantmentContainer extends EnchantmentMenu {

	public MixinApotheosisEnchantmentContainer(int id, Inventory Inventory) {
		super(id, Inventory);
	}

	public MixinApotheosisEnchantmentContainer(int id, Inventory Inventory, ContainerLevelAccess worldPosCallable) {
		super(id, Inventory, worldPosCallable);
	}

	@Inject(at = @At("HEAD"), method = "clickMenuButton(Lnet/minecraft/world/entity/player/Player;I)Z", cancellable = true)
	private void onEnchantedItem(Player player, int id, CallbackInfoReturnable<Boolean> info) {
		if (AnticlimacticLagacy.enchanterPearl.isPresent(player)) {
			int level = this.costs[id];
			ItemStack toEnchant = this.enchantSlots.getItem(0);
			int i = id + 1;

			if (this.costs[id] <= 0 || toEnchant.isEmpty() || (player.experienceLevel < i || player.experienceLevel < this.costs[id]) && !player.getAbilities().instabuild) {
				info.setReturnValue(false);
				return;// false;
			}

			this.access.execute((world, pos) -> {
				ItemStack enchanted = toEnchant;
				List<EnchantmentInstance> list = this.getEnchantmentList(toEnchant, id, this.costs[id]);
				if (!list.isEmpty()) {
					ItemStack firstRoll = this.enchantStack(player, enchanted, id, true);
					ItemStack secondRoll = this.enchantStack(player, enchanted, id, false);

					enchanted = SuperpositionHandler.mergeEnchantments(firstRoll, secondRoll, true, false);
					enchanted = SuperpositionHandler.maybeApplyEternalBinding(enchanted);
					this.enchantSlots.setItem(0, enchanted);

					player.awardStat(Stats.ENCHANT_ITEM);
					if (player instanceof ServerPlayer) {

						// TODO Gotta finish this someday

						/*
							try {
								Class<?> triggerClass = Class.forName("shadows.apotheosis.advancements.EnchantedTrigger");
								Method triggerMethod = triggerClass.getDeclaredMethod("trigger", ServerPlayer.class, ItemStack.class, Integer.class, Float.class, Float.class, Float.class);
							} catch (Exception ex) {
							}
						 */
						//EnchantedItemTrigger
						CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, enchanted, level);
					}

					this.enchantSlots.setChanged();
					this.slotsChanged(this.enchantSlots);
					world.playSound((Player) null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
				}

			});


			info.setReturnValue(true);
			return;// true;
		}
	}

	private ItemStack enchantStack(Player player, ItemStack stack, int id, boolean shadowsRollTwice) {
		ItemStack toEnchant = stack.copy();

		int i = id + 1;
		List<EnchantmentInstance> list = this.getEnchantmentList(toEnchant, id, this.costs[id]);
		if (!list.isEmpty()) {
			ItemStack doubleRoll = EnchantmentHelper.enchantItem(player.getRandom(), toEnchant.copy(), (int) Math.min(this.costs[id]/1.5, 40), true);
			player.onEnchantmentPerformed(toEnchant, i);

			boolean tomeConfirmed = false;

			try {
				Class<?> tomeClass = Class.forName("shadows.apotheosis.ench.objects.TomeItem");
				if (tomeClass.isInstance(toEnchant.getItem())) {
					tomeConfirmed = true;
				}
			} catch (Exception ex) {
				// NO-OP
			}

			boolean flag = toEnchant.getItem() == Items.BOOK || tomeConfirmed;
			if (flag) {
				toEnchant = new ItemStack(Items.ENCHANTED_BOOK);
			}

			for (EnchantmentInstance enchantmentdata : list) {
				if (flag) {
					EnchantedBookItem.addEnchantment(toEnchant, enchantmentdata);
				} else {
					toEnchant.enchant(enchantmentdata.enchantment, enchantmentdata.level);
				}
			}

			if (shadowsRollTwice) {
				toEnchant = SuperpositionHandler.mergeEnchantments(toEnchant, doubleRoll, false, true);
			}
			this.enchantmentSeed.set(player.getEnchantmentSeed());
		}
		return toEnchant;
	}

}
