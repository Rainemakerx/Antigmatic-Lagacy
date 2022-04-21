package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.gui.containers.LoreInscriberContainerProvider;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LoreInscriber extends ItemBase implements Vanishable {

	public LoreInscriber() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.EPIC).stacksTo(1));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "lore_inscriber"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber4");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber5");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber6");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber7");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber8");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber9");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber10");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.loreInscriber11");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		playerIn.startUsingItem(handIn);

		if (!worldIn.isClientSide) {
			Component name = new TranslatableComponent("gui.anticlimacticlagacy.lore_inscriber");
			playerIn.openMenu(new LoreInscriberContainerProvider(name));
		}

		return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
	}

}
