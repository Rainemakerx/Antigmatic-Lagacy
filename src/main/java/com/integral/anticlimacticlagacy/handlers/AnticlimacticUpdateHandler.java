package com.integral.anticlimacticlagacy.handlers;

import java.net.URL;
import java.util.Scanner;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.packets.clients.PacketUpdateNotification;
import com.integral.omniconfig.Configuration;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

/**
 * Some code is still borrowed from Tainted Magic update handler.
 * @author Integral
 */

public class AnticlimacticUpdateHandler {
	public static Omniconfig.BooleanParameter notificationsEnabled;

	@SubscribeConfig(receiveClient = true)
	public static void onConfig(OmniconfigWrapper builder) {

		if (builder.config.getSidedType() != Configuration.SidedConfigType.CLIENT) {
			// NO-OP
		} else {

			notificationsEnabled = builder
					.comment("Whether or not Anticlimactic Lagacy should show notification in chat when new mod update is available.")
					.getBoolean("UpdateHandlerEnabled", true);

		}
	}

	private static String currentVersion = AnticlimacticLagacy.VERSION + " " + AnticlimacticLagacy.RELEASE_TYPE;
	private static String newestVersion;
	public static TranslatableComponent updateStatus = null;
	public static boolean show = false;
	static boolean worked = false;

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

		if (event.getPlayer() instanceof ServerPlayer) {
			AnticlimacticLagacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> ((ServerPlayer)event.getPlayer())), new PacketUpdateNotification());
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void handleShowup(LocalPlayer player) {
		if (!AnticlimacticUpdateHandler.show)
			return;

		if (notificationsEnabled.getValue()) {
			player.sendMessage(AnticlimacticUpdateHandler.updateStatus, player.getUUID());
		}

		AnticlimacticUpdateHandler.show = false;

	}

	public static void init() {

		AnticlimacticUpdateHandler.getNewestVersion();

		if (AnticlimacticUpdateHandler.newestVersion != null)
		{
			if (AnticlimacticUpdateHandler.newestVersion.equalsIgnoreCase(AnticlimacticUpdateHandler.currentVersion))
			{
				AnticlimacticUpdateHandler.show = false;
			}
			else if (!AnticlimacticUpdateHandler.newestVersion.equalsIgnoreCase(AnticlimacticUpdateHandler.currentVersion))
			{
				AnticlimacticUpdateHandler.show = true;

				TextComponent newVerArg = new TextComponent(AnticlimacticUpdateHandler.newestVersion);
				newVerArg.withStyle(ChatFormatting.GOLD);

				AnticlimacticUpdateHandler.updateStatus = new TranslatableComponent("status.anticlimacticlagacy.outdated", newVerArg);
				AnticlimacticUpdateHandler.updateStatus.withStyle(ChatFormatting.DARK_PURPLE);
			}
		}
		else
		{
			AnticlimacticUpdateHandler.show = true;
			AnticlimacticUpdateHandler.updateStatus = new TranslatableComponent("status.anticlimacticlagacy.noconnection");
			AnticlimacticUpdateHandler.updateStatus.withStyle(ChatFormatting.RED);
		}
	}

	private static void getNewestVersion() {
		try
		{
			URL url = new URL("https://raw.githubusercontent.com/Extegral/Anticlimactic-Lagacy/1.18.X/version.txt");
			Scanner s = new Scanner(url.openStream());
			AnticlimacticUpdateHandler.newestVersion = s.nextLine();
			s.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}