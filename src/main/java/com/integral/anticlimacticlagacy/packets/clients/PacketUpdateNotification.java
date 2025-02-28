package com.integral.anticlimacticlagacy.packets.clients;

import java.util.function.Supplier;

import com.integral.anticlimacticlagacy.handlers.AnticlimacticUpdateHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

/**
 * Packet for handling update notification.
 * @author Integral
 */

public class PacketUpdateNotification {

	  public PacketUpdateNotification() {
	  }

	  public static void encode(PacketUpdateNotification msg, FriendlyByteBuf buf) {
	  }

	  public static PacketUpdateNotification decode(FriendlyByteBuf buf) {
	    return new PacketUpdateNotification();
	 }

	  public static void handle(PacketUpdateNotification msg, Supplier<NetworkEvent.Context> ctx) {

		    ctx.get().enqueueWork(() -> {
		      LocalPlayer player = Minecraft.getInstance().player;
		      AnticlimacticUpdateHandler.handleShowup(player);
		      
		    });
		    ctx.get().setPacketHandled(true);
	  }

}


