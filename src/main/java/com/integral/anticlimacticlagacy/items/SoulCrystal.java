package com.integral.anticlimacticlagacy.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Multimap;
import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.items.IPermanentCrystal;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;
import com.integral.anticlimacticlagacy.objects.Vector3;
import com.integral.anticlimacticlagacy.packets.clients.PacketRecallParticles;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

public class SoulCrystal extends ItemBase implements IPermanentCrystal, Vanishable {
	public Map<Player, Multimap<Attribute, AttributeModifier>> attributeDispatcher = new WeakHashMap<>();

	public SoulCrystal() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.EPIC).stacksTo(1).fireResistant().tab(AnticlimacticLagacy.anticlimacticTab));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "soul_crystal"));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.soulCrystal1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.soulCrystal2");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}
	}

	public ItemStack createCrystalFrom(Player player) {
		int lostFragments = this.getLostCrystals(player);
		this.setLostCrystals(player, lostFragments + 1);

		return new ItemStack(this);
	}

	public boolean retrieveSoulFromCrystal(Player player, ItemStack stack) {
		int lostFragments = this.getLostCrystals(player);

		if (lostFragments > 0) {
			this.setLostCrystals(player, lostFragments - 1);

			if (!player.level.isClientSide) {
				player.level.playSound(null, new BlockPos(player.position()), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0f, 1.0f);
			}

			return true;
		} else
			return false;
	}

	public void setLostCrystals(Player player, int lost) {
		SuperpositionHandler.setPersistentInteger(player, "anticlimacticlagacy.lostsoulfragments", lost);
		this.updatePlayerSoulMap(player);
	}

	public int getLostCrystals(Player player) {
		return SuperpositionHandler.getPersistentInteger(player, "anticlimacticlagacy.lostsoulfragments", 0);
	}

	public Multimap<Attribute, AttributeModifier> getOrCreateSoulMap(Player player) {
		if (this.attributeDispatcher.containsKey(player))
			return this.attributeDispatcher.get(player);
		else {
			Multimap<Attribute, AttributeModifier> playerAttributes = HashMultimap.create();
			this.attributeDispatcher.put(player, playerAttributes);
			return playerAttributes;
		}
	}

	public void applyPlayerSoulMap(Player player) {
		Multimap<Attribute, AttributeModifier> soulMap = this.getOrCreateSoulMap(player);
		AttributeMap attributeManager = player.getAttributes();
		attributeManager.addTransientAttributeModifiers(soulMap);
	}

	public void updatePlayerSoulMap(Player player) {
		Multimap<Attribute, AttributeModifier> soulMap = this.getOrCreateSoulMap(player);
		AttributeMap attributeManager = player.getAttributes();

		// Removes former attributes
		attributeManager.removeAttributeModifiers(soulMap);

		soulMap.clear();

		int lostFragments = SuperpositionHandler.getPersistentInteger(player, "anticlimacticlagacy.lostsoulfragments", 0);

		if (lostFragments > 0) {
			soulMap.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("66a2aa2d-7e3c-4af4-882f-bd2b2ded8e7b"), "Lost Soul Health Modifier", -0.1F * lostFragments, AttributeModifier.Operation.MULTIPLY_TOTAL));
		}

		// Applies new attributes
		attributeManager.addTransientAttributeModifiers(soulMap);

		this.attributeDispatcher.put(player, soulMap);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		player.startUsingItem(hand);

		if (this.retrieveSoulFromCrystal(player, stack)) {
			Vector3 playerCenter = Vector3.fromEntityCenter(player);
			if (!player.level.isClientSide) {
				AnticlimacticLagacy.packetInstance.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(playerCenter.x, playerCenter.y, playerCenter.z, 64, player.level.dimension())), new PacketRecallParticles(playerCenter.x, playerCenter.y, playerCenter.z, 48, false));
			}

			player.swing(hand);
			stack.setCount(0);
			return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
		} else
			return new InteractionResultHolder<>(InteractionResult.PASS, stack);
	}

}
