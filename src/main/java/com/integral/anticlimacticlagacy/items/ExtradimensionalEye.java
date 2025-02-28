package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExtradimensionalEye extends ItemBase implements Vanishable {

	public float range = 3.0F;

	public ExtradimensionalEye() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.UNCOMMON).stacksTo(1));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "extradimensional_eye"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEye1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEye2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEye3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEye4");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEye5");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEye6");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEye7");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}
		if (ItemNBTHelper.verifyExistance(stack, "BoundDimension")) {

			String boundDimensionName = null;
			String dimensionID = ItemNBTHelper.getString(stack, "BoundDimension", "minecraft:overworld");

			if (dimensionID.equals("minecraft:overworld")) {
				boundDimensionName = "tooltip.anticlimacticlagacy.overworld";
			} else if (dimensionID.equals("minecraft:the_nether")) {
				boundDimensionName = "tooltip.anticlimacticlagacy.nether";
			} else if (dimensionID.equals("minecraft:the_end")) {
				boundDimensionName = "tooltip.anticlimacticlagacy.end";
			}

			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEyeLocation");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEyeX", ChatFormatting.GOLD, ItemNBTHelper.getInt(stack, "BoundX", 0));
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEyeY", ChatFormatting.GOLD, ItemNBTHelper.getInt(stack, "BoundY", 0));
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEyeZ", ChatFormatting.GOLD, ItemNBTHelper.getInt(stack, "BoundZ", 0));
			if (boundDimensionName != null) {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEyeDimension", null, new TranslatableComponent(boundDimensionName).getString());
			} else {
				ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.extradimensionalEyeDimension", ChatFormatting.GOLD, dimensionID);
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);

		if (playerIn.isCrouching() && ItemNBTHelper.getString(itemstack, "BoundDimension", null) == null) {
			ItemNBTHelper.setDouble(itemstack, "BoundX", playerIn.getX());
			ItemNBTHelper.setDouble(itemstack, "BoundY", playerIn.getY());
			ItemNBTHelper.setDouble(itemstack, "BoundZ", playerIn.getZ());

			ItemNBTHelper.setString(itemstack, "BoundDimension", playerIn.level.dimension().location().toString());
			playerIn.swing(handIn);
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
		}

		return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
	}

}
