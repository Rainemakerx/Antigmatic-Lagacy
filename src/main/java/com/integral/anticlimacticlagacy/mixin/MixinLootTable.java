package com.integral.anticlimacticlagacy.mixin;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.commons.lang3.tuple.Triple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.common.ForgeHooks;

/**
 * TODO Do we still need this?
 *
 * @author Aizistral
 */

@Mixin(LootTable.class)
@SuppressWarnings("unused")
public class MixinLootTable {

	@Inject(at = @At("HEAD"), method = "addPool", cancellable = true, remap = false)
	public void onPoolAdded(LootPool pool, CallbackInfo info) {
		if (true)
			return;

		Object forgottenObject = this;
		if (pool.getName() == null) {
			info.cancel();
			NullPointerException ex = new NullPointerException("I'm fucking done with this");
			ex.fillInStackTrace();

			AnticlimacticLagacy.exceptionList.add(Triple.of(forgottenObject instanceof LootTable ? (LootTable)forgottenObject : null, pool, ex));
		}

	}
}
