package com.integral.anticlimacticlagacy.api.items;

import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ITaintable {

	default void handleTaintable(ItemStack stack, Player player) {
		if (SuperpositionHandler.isTheCursedOne(player)) {
			if (!ItemNBTHelper.getBoolean(stack, "isTainted", false)) {
				ItemNBTHelper.setBoolean(stack, "isTainted", true);
			}
		} else {
			if (ItemNBTHelper.getBoolean(stack, "isTainted", false)) {
				ItemNBTHelper.setBoolean(stack, "isTainted", false);
			}
		}
	}

	default boolean isTainted(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, "isTainted", false);
	}

}
