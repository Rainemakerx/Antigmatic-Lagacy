package com.integral.anticlimacticlagacy.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class BerserkEmblem extends ItemBaseCurio implements ICursed {
	public static Omniconfig.DoubleParameter attackDamage;
	public static Omniconfig.DoubleParameter attackSpeed;
	public static Omniconfig.DoubleParameter movementSpeed;
	public static Omniconfig.DoubleParameter damageResistance;

	@SubscribeConfig
	public static void onConfig(OmniconfigWrapper builder) {
		builder.pushPrefix("BerserkEmblem");

		attackDamage = builder
				.comment("Damage increase provided by Emblem of Bloodstained Valor for each missing percent of health. Measured as percentage.")
				.getDouble("DamageBoost", 1);

		attackSpeed = builder
				.comment("Attack speed increase provided by Emblem of Bloodstained Valor for each missing percent of health. Measured as percentage.")
				.getDouble("AttackSpeedBoost", 1);

		movementSpeed = builder
				.comment("Movement speed increase provided by Emblem of Bloodstained Valor for each missing percent of health. Measured as percentage.")
				.getDouble("SpeedBoost", 0.5);

		damageResistance = builder
				.comment("Damage resistance provided by Emblem of Bloodstained Valor for each missing percent of health. Measured as percentage.")
				.getDouble("ResistanceBoost", 0.5);

		builder.popPrefix();
	}


	public BerserkEmblem() {
		super(ItemBaseCurio.getDefaultProperties().rarity(Rarity.EPIC));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "berserk_emblem"));
	}

	private Multimap<Attribute, AttributeModifier> createAttributeMap(Player player) {
		Multimap<Attribute, AttributeModifier> attributesDefault = HashMultimap.create();

		float missingHealthPool = SuperpositionHandler.getMissingHealthPool(player);

		attributesDefault.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("ec62548c-5b26-401e-83fd-693e4aafa532"), "anticlimacticlagacy:attack_speed_modifier", missingHealthPool*attackSpeed.getValue(), AttributeModifier.Operation.MULTIPLY_BASE));
		attributesDefault.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("f4ece564-d2c0-40d2-a96a-dc68b493137c"), "anticlimacticlagacy:speed_modifier", missingHealthPool*movementSpeed.getValue(), AttributeModifier.Operation.MULTIPLY_BASE));

		return attributesDefault;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem1", ChatFormatting.GOLD, minimizeNumber(attackDamage.getValue()) + "%");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem2", ChatFormatting.GOLD, minimizeNumber(attackSpeed.getValue()) + "%");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem3", ChatFormatting.GOLD, minimizeNumber(movementSpeed.getValue()) + "%");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem4", ChatFormatting.GOLD, minimizeNumber(damageResistance.getValue()) + "%");

			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem5");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem6");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

		if (Minecraft.getInstance().player != null)
			if (SuperpositionHandler.getCurioStack(Minecraft.getInstance().player, this) == stack) {
				float missingPool = SuperpositionHandler.getMissingHealthPool(Minecraft.getInstance().player);
				int percentage = (int)(missingPool * 100F);

				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem7");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem8", ChatFormatting.GOLD, minimizeNumber(attackDamage.getValue()*percentage) + "%");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem9", ChatFormatting.GOLD, minimizeNumber(attackSpeed.getValue()*percentage) + "%");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem10", ChatFormatting.GOLD, minimizeNumber(movementSpeed.getValue()*percentage) + "%");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.berserk_emblem11", ChatFormatting.GOLD, minimizeNumber(damageResistance.getValue()*percentage) + "%");

			}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
		ItemLoreHelper.indicateCursedOnesOnly(list);

	}

	@Override
	public void curioTick(SlotContext context, ItemStack stack) {
		if (context.entity() instanceof Player player) {
			player.getAttributes().addTransientAttributeModifiers(this.createAttributeMap(player));
		}
	}

	@Override
	public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
		if (context.entity() instanceof Player player) {
			player.getAttributes().removeAttributeModifiers(this.createAttributeMap(player));
		}
	}

	@Override
	public boolean canEquip(SlotContext context, ItemStack stack) {
		return super.canEquip(context, stack) && context.entity() instanceof Player player && SuperpositionHandler.isTheCursedOne(player);
	}

}