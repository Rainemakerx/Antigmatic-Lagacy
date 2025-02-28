package com.integral.anticlimacticlagacy.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.hoglin.HoglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;

@Mixin(HoglinAi.class)
public class MixinHoglinTasks {

	@Inject(at = @At("RETURN"), method = "findNearestValidAttackTarget", cancellable = true)
	private static void onHoglinShallAttack(Hoglin hoglin, CallbackInfoReturnable<Optional<? extends LivingEntity>> info) {
		Optional<? extends LivingEntity> returnedTarget = info.getReturnValue();

		if (returnedTarget.isPresent() && returnedTarget.orElse(null) instanceof Player) {
			Player returnedPlayer = (Player) returnedTarget.orElse(null);

			if (SuperpositionHandler.hasItem(returnedPlayer, AnticlimacticLagacy.animalGuide)) {
				info.setReturnValue(Optional.empty());
			}
		}
	}
}
