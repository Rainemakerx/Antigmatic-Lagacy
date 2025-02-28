package com.integral.anticlimacticlagacy.items.generic;

import java.util.Map;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import net.minecraft.world.item.Item.Properties;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class ItemBaseCurio extends ItemBase implements ICurioItem, Vanishable, Wearable {

	public ItemBaseCurio() {
		this(getDefaultProperties());
	}

	public ItemBaseCurio(Properties props) {
		super(props);
	}

	@Override
	public void onEquip(SlotContext context, ItemStack prevStack, ItemStack stack) {
		// Insert existential void here
	}

	@Override
	public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
		// Insert existential void here
	}

	@Override
	public void curioTick(SlotContext context, ItemStack stack) {
		// Insert existential void here
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
		// Insert existential void here
	}

	@Override
	public boolean canEquipFromUse(SlotContext context, ItemStack stack) {
		return true;
	}

	@Override
	public boolean canEquip(SlotContext context, ItemStack stack) {
		return !SuperpositionHandler.hasCurio(context.entity(), this);
	}

	@Override
	public boolean canUnequip(SlotContext context, ItemStack stack) {
		return true;
	}

	@Override
	public DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
		return DropRule.DEFAULT;
	}

	public static Properties getDefaultProperties() {
		Properties props = new Item.Properties();

		props.tab(AnticlimacticLagacy.anticlimacticTab);
		props.stacksTo(1);
		props.rarity(Rarity.COMMON);

		return props;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		Map<Enchantment, Integer> list = EnchantmentHelper.getEnchantments(book);

		if (list.size() == 1 && list.containsKey(Enchantments.BINDING_CURSE))
			return true;
		else
			return super.isBookEnchantable(stack, book);
	}

}
