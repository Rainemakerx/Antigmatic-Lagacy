package com.integral.anticlimacticlagacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;

import net.minecraft.world.item.ItemStack;

@Pseudo
@Mixin(targets = "squeek.appleskin.client.TooltipOverlayHandler", remap = false)
public class MixinTooltipOverlayHandler {

	@Inject(method = "shouldShowTooltip", at = @At("HEAD"), cancellable = true)
	private static void onItemCheck(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
		if (stack != null && stack.getItem().getRegistryName().getNamespace().equals(anticlimacticlagacy.MODID)) {
			info.setReturnValue(false);
		}
	}

}
