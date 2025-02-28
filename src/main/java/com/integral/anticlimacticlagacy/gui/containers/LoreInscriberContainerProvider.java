package com.integral.anticlimacticlagacy.gui.containers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;

public class LoreInscriberContainerProvider implements MenuProvider {
	private Component name;

	public LoreInscriberContainerProvider(Component name) {
		this.name = name;
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory playerInv, Player player) {
		return new LoreInscriberContainer(syncId, playerInv);
	}

	@Override
	public Component getDisplayName() {
		return this.name;
	}

}
