package com.integral.anticlimacticlagacy.enchantments;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.config.OmniconfigHandler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class EternalBindingCurse extends Enchantment {
	private final List<String> incompatibleKeywords = new ArrayList<>();

	public EternalBindingCurse(EquipmentSlot... slots) {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.WEARABLE, slots);
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "eternal_binding_curse"));

		this.incompatibleKeywords.add("soulbound");
		this.incompatibleKeywords.add("soulbinding");
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 25;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return 50;
	}

	@Override
	public int getMinLevel() {
		return 1;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public boolean canEnchant(ItemStack stack) {
		return OmniconfigHandler.isItemEnabled(this) && !stack.is(AnticlimacticLagacy.cursedRing)
				&& !stack.is(AnticlimacticLagacy.escapeScroll) && !stack.is(AnticlimacticLagacy.anticlimacticAmulet)
				&& !stack.is(AnticlimacticLagacy.desolationRing) && super.canEnchant(stack);
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isCurse() {
		return true;
	}

	@Override
	public boolean isAllowedOnBooks() {
		return OmniconfigHandler.isItemEnabled(this);
	}

	@Override
	public boolean isDiscoverable() {
		return false;
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) {
		if (this.incompatibleKeywords.stream().anyMatch(keyword ->
		StringUtils.containsIgnoreCase(keyword, ench.getRegistryName().getPath())))
			return false;

		return ench != Enchantments.BINDING_CURSE && ench != Enchantments.VANISHING_CURSE ? super.checkCompatibility(ench) : false;
	}

}
