package com.integral.anticlimacticlagacy.handlers;

import org.lwjgl.glfw.GLFW;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.config.OmniconfigHandler;
import com.integral.anticlimacticlagacy.packets.server.PacketEnderRingKey;
import com.integral.anticlimacticlagacy.packets.server.PacketSpellstoneKey;
import com.integral.anticlimacticlagacy.packets.server.PacketXPScrollKey;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

/**
 * Class for creating and handling keybinds on the client.
 * @author Integral
 */

public class AnticlimacticKeybindHandler {

	@OnlyIn(Dist.CLIENT)
	public static boolean checkVariable;

	public KeyMapping enderRingKey;
	public KeyMapping spellstoneAbilityKey;
	public KeyMapping xpScrollKey;

	private boolean spaceDown = false;

	@OnlyIn(Dist.CLIENT)
	public void registerKeybinds() {
		this.enderRingKey = new KeyMapping("key.enderRing", GLFW.GLFW_KEY_I, "key.categories.anticlimacticlagacy");
		this.spellstoneAbilityKey = new KeyMapping("key.spellstoneAbility", GLFW.GLFW_KEY_K, "key.categories.anticlimacticlagacy");
		this.xpScrollKey = new KeyMapping("key.xpScroll", GLFW.GLFW_KEY_J, "key.categories.anticlimacticlagacy");

		ClientRegistry.registerKeyBinding(this.enderRingKey);
		ClientRegistry.registerKeyBinding(this.spellstoneAbilityKey);
		ClientRegistry.registerKeyBinding(this.xpScrollKey);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onKeyInput(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.START || !Minecraft.getInstance().isWindowActive() || Minecraft.getInstance().player == null)
			return;

		boolean spaceDown = Minecraft.getInstance().options.keyJump.isDown();
		boolean jumpClicked = false;

		if (this.spaceDown != spaceDown) {
			this.spaceDown = spaceDown;
			if (spaceDown) {
				jumpClicked = true;
			}
		}

		if (Minecraft.getInstance().player.isFallFlying()) {
			jumpClicked = Minecraft.getInstance().options.keyJump.isDown();
		}

		if (!OmniconfigHandler.angelBlessingDoubleJump.getValue()) {
			jumpClicked = false;
		}

		if (this.enderRingKey.consumeClick()) {
			if (Minecraft.getInstance().isWindowActive()) {
				AnticlimacticLagacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketEnderRingKey(true));
			}
		}

		if (this.xpScrollKey.consumeClick()) {
			AnticlimacticLagacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketXPScrollKey(true));
		}

		if (this.spellstoneAbilityKey.isDown() && SuperpositionHandler.hasCurio(Minecraft.getInstance().player, AnticlimacticLagacy.anticlimacticItem)) {
			AnticlimacticLagacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketSpellstoneKey(true));
		} else if (this.spellstoneAbilityKey.consumeClick() && SuperpositionHandler.hasSpellstone(Minecraft.getInstance().player)) {
			AnticlimacticLagacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketSpellstoneKey(true));
		} else if (jumpClicked) {
			LocalPlayer player = Minecraft.getInstance().player;

			if (!player.isInWater() && !player.isOnGround() && !player.isCreative() && !player.isSpectator()
					&& SuperpositionHandler.hasCurio(player, AnticlimacticLagacy.angelBlessing)) {
				AnticlimacticLagacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketSpellstoneKey(true));
			}
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void onLivingJump(LivingJumpEvent event) {
		if (event.getEntityLiving() instanceof LocalPlayer player) {
			//this.suppressNextJumpClick = true;
		}
	}

}
