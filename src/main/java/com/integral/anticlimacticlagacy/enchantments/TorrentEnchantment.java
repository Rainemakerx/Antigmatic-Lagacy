package com.integral.anticlimacticlagacy.enchantments;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.config.OmniconfigHandler;
import com.integral.anticlimacticlagacy.objects.RegisteredMeleeAttack;

import static com.integral.anticlimacticlagacy.objects.RegisteredMeleeAttack.*;

import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.TridentImpalerEnchantment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class TorrentEnchantment extends Enchantment {
	public TorrentEnchantment(EquipmentSlot... slots) {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.TRIDENT, slots);

		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "torrent"));
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 1 + (enchantmentLevel - 1) * 8;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 20;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return this.canEnchant(stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean checkCompatibility(Enchantment ench) {
		return !(ench instanceof DamageEnchantment) && !(ench instanceof TridentImpalerEnchantment) && !(ench instanceof WrathEnchantment) && super.checkCompatibility(ench);
	}

	@Override
	public boolean isAllowedOnBooks() {
		return OmniconfigHandler.isItemEnabled(this);
	}

	@Override
	public boolean isDiscoverable() {
		return OmniconfigHandler.isItemEnabled(this);
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		return OmniconfigHandler.isItemEnabled(this) && stack.canApplyAtEnchantingTable(this);
	}

	public float bonusDamageByCreature(LivingEntity attacker, LivingEntity living, int level) {
		float calculated = (living.fireImmune() || living.isSensitiveToWater() || living instanceof EnderDragon) ? level * 2.5F : 0F;
		calculated*= getRegisteredAttackStregth(attacker);

		return calculated;
	}
}

