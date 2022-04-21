package com.integral.anticlimacticlagacy.packets.clients;

import java.util.function.Supplier;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.helpers.ExperienceHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketUpdateExperience {
	private int experience;

	public PacketUpdateExperience(int xp) {
		this.experience = xp;
	}

	public static void encode(PacketUpdateExperience msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.experience);
	}

	public static PacketUpdateExperience decode(FriendlyByteBuf buf) {
		return new PacketUpdateExperience(buf.readInt());
	}


	public static void handle(PacketUpdateExperience msg, Supplier<NetworkEvent.Context> ctx) {

		ctx.get().enqueueWork(() -> {
			Player player = AnticlimacticLagacy.proxy.getClientPlayer();

			int diff = msg.experience - ExperienceHelper.getPlayerXP(player);
			ExperienceHelper.addPlayerXP(player, diff);

		});

		ctx.get().setPacketHandled(true);
	}

}

