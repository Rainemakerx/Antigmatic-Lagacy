package com.integral.anticlimacticlagacy.gui.containers;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;

public class PortableCrafterContainerProvider implements MenuProvider {
	private Component name;

	public PortableCrafterContainerProvider(Component name) {
		this.name = name;
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory playerInv, Player player) {
		return AnticlimacticLagacy.PORTABLE_CRAFTER.create(syncId, playerInv);
	}

	@Override
	public Component getDisplayName() {
		return this.name;
	}

}
