package com.integral.anticlimacticlagacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.config.JsonConfigHandler;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseCurio;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(Villager.class)
public class MixinVillagerEntity {

	@Inject(at = @At("RETURN"), method = "updateSpecialPrices")
	private void onSpecialPrices(Player player, CallbackInfo info) {
		Object forgottenObject = this;

		if (forgottenObject instanceof Villager) {
			Villager villager = (Villager) forgottenObject;

			if (SuperpositionHandler.hasCurio(player, anticlimacticlagacy.avariceScroll)) {
				for (MerchantOffer trade : villager.getOffers()) {
					double discountValue = 0.35;
					int discount = (int) Math.floor(discountValue * trade.getBaseCostA().getCount());
					trade.addToSpecialPriceDiff(-Math.max(discount, 1));
				}
			}
		}
	}

}
