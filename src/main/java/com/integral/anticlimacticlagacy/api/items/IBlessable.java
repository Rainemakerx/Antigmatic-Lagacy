package com.integral.anticlimacticlagacy.api.items;

import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IBlessable {

	default void handleBlessable(ItemStack stack, Player player) {
		if (SuperpositionHandler.isTheBlessedOne(player)) {
			if (!ItemNBTHelper.getBoolean(stack, "isBelieverBlessed", false)) {
				ItemNBTHelper.setBoolean(stack, "isBelieverBlessed", true);
			}
		} else {
			if (ItemNBTHelper.getBoolean(stack, "isBelieverBlessed", false)) {
				ItemNBTHelper.setBoolean(stack, "isBelieverBlessed", false);
			}
		}
	}

	default boolean isBlessed(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, "isBelieverBlessed", false);
	}

}
