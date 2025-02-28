package com.integral.anticlimacticlagacy.triggers;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;
import com.integral.anticlimacticlagacy.AnticlimacticLagacy;

import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;

public class ForbiddenFruitTrigger extends SimpleCriterionTrigger<ForbiddenFruitTrigger.Instance> {
	public static final ResourceLocation ID = new ResourceLocation(AnticlimacticLagacy.MODID, "consume_forbidden_fruit");
	public static final ForbiddenFruitTrigger INSTANCE = new ForbiddenFruitTrigger();

	private ForbiddenFruitTrigger() {}

	@Nonnull
	@Override
	public ResourceLocation getId() {
		return ForbiddenFruitTrigger.ID;
	}

	@Nonnull
	@Override
	public ForbiddenFruitTrigger.Instance createInstance(@Nonnull JsonObject json, @Nonnull EntityPredicate.Composite playerPred, DeserializationContext conditions) {
		return new ForbiddenFruitTrigger.Instance(playerPred);
	}

	public void trigger(ServerPlayer player) {
		this.trigger(player, instance -> instance.test());
	}

	static class Instance extends AbstractCriterionTriggerInstance {
		Instance(EntityPredicate.Composite playerPred) {
			super(ForbiddenFruitTrigger.ID, playerPred);
		}

		@Nonnull
		@Override
		public ResourceLocation getCriterion() {
			return ForbiddenFruitTrigger.ID;
		}

		boolean test() {
			return true;
		}
	}

}
