package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.items.ICursed;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkMirror extends ItemBase implements ICursed, Vanishable {

	public DarkMirror() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.RARE).stacksTo(1));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "dark_mirror"));
	}

	/*
	@Override
	public int getUseDuration(ItemStack stack) {
		return 80;
	}

	@Override
	public UseAnim getUseAction(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
		Vector3 vec = Vector3.fromEntityCenter(player);

		for (int counter = 0; counter <= 3; counter++) {
			player.world.addParticle(ParticleTypes.PORTAL, true, vec.x, vec.y, vec.z, (random.nextDouble() - 0.5D) * 3D, (random.nextDouble() - 0.5D) * 3D, (random.nextDouble() - 0.5D) * 3D);
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		if (entityLiving instanceof Player) {
			Player player = (Player) entityLiving;

			if (player instanceof ServerPlayer) {
				SuperpositionHandler.backToSpawn((ServerPlayer) player);
				player.getCooldownTracker().setCooldown(this, 200);
			}
		}

		return stack;
	}

	 */


	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.darkMirror1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.darkMirror2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.darkMirror3");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
		ItemLoreHelper.indicateCursedOnesOnly(list);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		if (AnticlimacticLagacy.proxy.isInVanillaDimension(player) && SuperpositionHandler.isTheCursedOne(player) && !player.getCooldowns().isOnCooldown(this)) {
			player.startUsingItem(hand);

			if (player instanceof ServerPlayer) {
				SuperpositionHandler.backToSpawn((ServerPlayer) player);
				player.getCooldowns().addCooldown(this, 200);
			}

			return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
		} else
			return new InteractionResultHolder<>(InteractionResult.PASS, player.getItemInHand(hand));
	}

}
