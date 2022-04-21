package com.integral.anticlimacticlagacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.integral.anticlimacticlagacy.handlers.AnticlimacticEventHandler;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

@Mixin(MobEffect.class)
public class MixinMobEffect {

	@Redirect(method = "applyEffectTick", at = @At(value = "INVOKE", target =
			"Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
			ordinal = 0))
	private boolean onHurt(LivingEntity entity, DamageSource source, float amount) {
		AnticlimacticEventHandler.isPoisonHurt = true;
		boolean result = entity.hurt(source, amount);
		AnticlimacticEventHandler.isPoisonHurt = false;
		return result;
	}

}
