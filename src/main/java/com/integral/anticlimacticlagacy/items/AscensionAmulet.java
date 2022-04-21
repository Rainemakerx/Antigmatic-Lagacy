package com.integral.anticlimacticlagacy.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.items.ICursed;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;
import com.integral.anticlimacticlagacy.items.AnticlimacticAmulet.AmuletColor;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseCurio;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import static com.integral.anticlimacticlagacy.items.AnticlimacticAmulet.*;

public class AscensionAmulet extends AnticlimacticAmulet {

	public AscensionAmulet() {
		this(getDefaultProperties().rarity(Rarity.EPIC).fireResistant(), "ascension_amulet");
	}

	protected AscensionAmulet(Properties properties, String name) {
		super(properties, name);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		String name = ItemNBTHelper.getString(stack, "Inscription", null);

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown() && this.isVesselEnabled()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.anticlimacticAmuletShift1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.anticlimacticAmuletShift2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.anticlimacticAmuletShift3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.anticlimacticAmuletShift4");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.anticlimacticAmuletShift5");
		} else {
			if (this.isVesselEnabled()) {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
			}

			if (name != null) {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.anticlimacticAmuletInscription", ChatFormatting.DARK_RED, name);
			}
		}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
		this.addAttributes(list, stack);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
			items.add(new ItemStack(this));
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	protected void addAttributes(List<Component> list, ItemStack stack) {
		ItemLoreHelper.addLocalizedFormattedString(list, "curios.modifiers.charm", ChatFormatting.GOLD);

		for (AmuletColor color : AmuletColor.values())
			if (color != AmuletColor.RED) {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.anticlimacticAmuletModifier" + color);
			} else {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.anticlimacticAmuletModifierRED", ChatFormatting.GOLD, minimizeNumber(damageBonus.getValue()));
			}
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		return super.getAllModifiers(slotContext.entity() instanceof Player player ? player : null);
	}

	@Override
	public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
		tooltips.clear();
		return tooltips;
	}

	@Override
	public boolean canEquip(SlotContext context, ItemStack stack) {
		return !SuperpositionHandler.hasCurio(context.entity(), AnticlimacticLagacy.anticlimacticAmulet)
				&& !SuperpositionHandler.hasCurio(context.entity(), AnticlimacticLagacy.ascensionAmulet);
	}

}
