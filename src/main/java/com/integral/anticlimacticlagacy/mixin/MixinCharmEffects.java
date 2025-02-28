package com.integral.anticlimacticlagacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Pseudo
@Mixin(targets = "net.darkhax.darkutils.features.charms.CharmEffects", remap = false)
public class MixinCharmEffects {

	@Inject(method = "applySleepCharmEffect", at = @At("HEAD"), cancellable = true, require = 0)
	private static void onApplySleepCharmEffect(Entity user, ItemStack item, CallbackInfo info) {
		if (user instanceof Player && SuperpositionHandler.hasCurio((Player) user, AnticlimacticLagacy.cursedRing)) {
			info.cancel();
		}
	}

}
