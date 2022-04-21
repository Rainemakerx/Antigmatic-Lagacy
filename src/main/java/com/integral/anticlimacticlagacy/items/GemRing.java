package com.integral.anticlimacticlagacy.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseCurio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class GemRing extends ItemBaseCurio {

	public GemRing() {
		super(ItemBaseCurio.getDefaultProperties().rarity(Rarity.UNCOMMON));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "gem_ring"));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown()) {
			if (Minecraft.getInstance().player != null && SuperpositionHandler.isTheCursedOne(Minecraft.getInstance().player)) {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.gemRing1Cursed");
			} else {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.gemRing1");
			}
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
		attributes.put(Attributes.LUCK, new AttributeModifier(UUID.fromString("6c913e9a-0d6f-4b3b-81b9-4c82f7778b52"), AnticlimacticLagacy.MODID+":luck_bonus", 1.0, AttributeModifier.Operation.ADDITION));

		return attributes;
	}

	//@Override
	public boolean rendersPiglinsNeutral(String identifier, ItemStack stack, LivingEntity wearer) {
		return true;
	}

	@Override
	public boolean isPiglinCurrency(ItemStack stack) {
		return true;
	}

}
