package com.integral.anticlimacticlagacy.packets.clients;

import java.util.Random;
import java.util.function.Supplier;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.AnticlimacticEventHandler;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.items.AstralBreaker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class PacketCosmicRevive {
	private int entityID;
	private int reviveType;

	public PacketCosmicRevive(int entityID, int reviveType) {
		this.entityID = entityID;
		this.reviveType = reviveType;
	}

	public static void encode(PacketCosmicRevive msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.entityID);
		buf.writeInt(msg.reviveType);
	}

	public static PacketCosmicRevive decode(FriendlyByteBuf buf) {
		return new PacketCosmicRevive(buf.readInt(), buf.readInt());
	}

	public static void handle(PacketCosmicRevive msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (msg.reviveType == 0) {
				AnticlimacticLagacy.proxy.displayReviveAnimation(msg.entityID, msg.reviveType);
			} else {
				Player player = AnticlimacticLagacy.proxy.getClientPlayer();
				Entity entity = player.level.getEntity(msg.entityID);

				if (entity == player) {
					AnticlimacticEventHandler.scheduledCubeRevive = 5;
				} else {
					AnticlimacticLagacy.proxy.displayReviveAnimation(msg.entityID, msg.reviveType);
				}
			}
		});

		ctx.get().setPacketHandled(true);
	}

}
