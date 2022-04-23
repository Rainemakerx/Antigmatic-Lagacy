package com.integral.anticlimacticlagacy.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
//import com.integral.anticlimacticlagacy.handlers.DevotedBelieversHandler;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseCurio;
import com.integral.omniconfig.Configuration;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.BindingCurseEnchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;

public class CosmicScroll extends ItemBaseCurio {
	private static final ResourceLocation ADVANCEMENT = new ResourceLocation(AnticlimacticLagacy.MODID, "main/cosmic_scroll");
	public static Omniconfig.PerhapsParameter unchosenDamageBonus;
	public static Omniconfig.PerhapsParameter unchosenKnockbackBonus;
	public static Omniconfig.PerhapsParameter etheriumShieldThreshold;
	public static Omniconfig.IntParameter deathProtectionCooldown;
	public static Omniconfig.BooleanParameter multiequip;

	@SubscribeConfig
	public static void onConfig(OmniconfigWrapper builder) {
		builder.pushPrefix("CosmicScroll");

		multiequip = builder.comment("Whether or not it should be allowed to equip multiple Fool's Writs").getBoolean("Multiequip", true);

		etheriumShieldThreshold = builder
				.comment("Alternative Etherium Shield health requirement for those who bear The Fool's Writ. Defined as percentage.")
				.max(100)
				.getPerhaps("EtheriumShieldThreshold", 80);

		unchosenDamageBonus = builder
				.comment("Attack damage bonus of The Fool's Writ against non-chosen players.")
				.getPerhaps("UnchosenDamageBonus", 100);

		unchosenKnockbackBonus = builder
				.comment("Knockback bonus of The Fool's Writ against non-chosen players.")
				.getPerhaps("UnchosenKnockbackBonus", 100);

		deathProtectionCooldown = builder
				.comment("Cooldown of death protection ability of The Fool's Writ. Measured in seconds.")
				.getInt("DeathProtectionCooldown", 600);

		builder.popPrefix();
	}
	public CosmicScroll() {
		super(getDefaultProperties().rarity(Rarity.EPIC));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "cosmic_scroll"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll4", ChatFormatting.GOLD, etheriumShieldThreshold + "%");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll5");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll6");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll7");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll8");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll9");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll10");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll11", ChatFormatting.GOLD, deathProtectionCooldown);
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll12", ChatFormatting.GOLD, unchosenDamageBonus + "%", unchosenKnockbackBonus + "%");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll13");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScroll14");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScrollLore1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScrollLore2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

		if (this.hasCooldown(stack)) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.cosmicScrollCooldown", ChatFormatting.GOLD, (this.getCooldown(stack)/20));
		}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
		ItemLoreHelper.indicateBlessedOnesOnly(list);
	}

	public boolean hasCooldown(ItemStack scroll) {
		return this.getCooldown(scroll) > 0;
	}

	public int getCooldown(ItemStack scroll) {
		if (scroll.is(this))
			return ItemNBTHelper.getInt(scroll, "ReviveCooldown", 0);
		else
			return 0;
	}

	public void setCooldown(ItemStack scroll, int cooldown) {
		if (scroll.is(this)) {
			ItemNBTHelper.setInt(scroll, "ReviveCooldown", cooldown);
		}
	}

	@Override
	public boolean canEquip(SlotContext context, ItemStack stack) {
		if (multiequip.getValue())
			return true;
		else
			return super.canEquip(context, stack) && context.entity() instanceof Player player && SuperpositionHandler.isTheBlessedOne(player);
	}

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
		tooltips.clear();
		return tooltips;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();

		attributes.put(Attributes.LUCK, new AttributeModifier(
				UUID.fromString("290d5f76-87aa-4f7c-9c1a-9aef2fe25d05"),
				AnticlimacticLagacy.MODID+":luck_bonus", 1, AttributeModifier.Operation.ADDITION));

		// ARCANE SCROLL SLOT BONUS
		CuriosApi.getCuriosHelper().addSlotModifier(attributes, "scroll",
				UUID.fromString("fb4dfa90-1df4-4e26-86a9-481dcdd830c5"), 1, Operation.ADDITION);

		return attributes;
	}

	@Override
	public void curioTick(SlotContext context, ItemStack stack) {
		if (!context.entity().level.isClientSide)
			if (this.hasCooldown(stack)) {
				this.setCooldown(stack, this.getCooldown(stack) - 1);
			}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity pEntity, int pSlotId, boolean pIsSelected) {
		if (!level.isClientSide)
			if (this.hasCooldown(stack)) {
				this.setCooldown(stack, this.getCooldown(stack) - 1);
			}
	}

	@Override
	public void onEquip(SlotContext context, ItemStack prevStack, ItemStack stack) {
		if (context.entity() instanceof ServerPlayer player) {
			if (!SuperpositionHandler.hasAdvancement(player, ADVANCEMENT)) {
				SuperpositionHandler.grantAdvancement(player, ADVANCEMENT);
			}
		}
	}

}
