package com.integral.anticlimacticlagacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.integral.anticlimacticlagacy.handlers.AnticlimacticEventHandler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Isn't this a pure artwork, my friends?
 * @author Aizistral
 */

@Pseudo
@Mixin(targets = "com.brandon3055.csg.ModEventHandler", remap = false)
public class MixinStarterGear {

	@Inject(method = "playerLogin", at = @At("RETURN"), cancellable = false, require = 1)
	public void onCSGHandling(PlayerEvent.PlayerLoggedInEvent event, CallbackInfo info) {
		if (event.getPlayer() instanceof ServerPlayer player) {
			AnticlimacticEventHandler.grantStarterGear(player);
		}
	}

}
