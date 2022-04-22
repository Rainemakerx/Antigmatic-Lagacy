package com.integral.anticlimacticlagacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;

import net.minecraftforge.client.event.RenderTooltipEvent;

/**
 * LEAVE MY BLASTED TOOLTIPS ALONE.
 * @author Aizistral
 */

@Pseudo
@Mixin(targets = "vazkii.quark.content.client.tooltip.AttributeTooltips", remap = false)
public class MixinAttributeTooltips {

	@Inject(method = "makeTooltip", at = @At("HEAD"), cancellable = true, require = 1)
	private static void onTooltipEvent(RenderTooltipEvent.GatherComponents event, CallbackInfo info) {
		if (event.getItemStack().getItem().getRegistryName().getNamespace().equals(anticlimacticlagacy.MODID)) {
			info.cancel();
		}
	}

}
