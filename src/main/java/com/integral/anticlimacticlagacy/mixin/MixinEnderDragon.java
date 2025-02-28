package com.integral.anticlimacticlagacy.mixin;

import static com.integral.anticlimacticlagacy.AnticlimacticLagacy.abyssalHeart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.integral.anticlimacticlagacy.api.quack.IAbyssalHeartBearer;
import com.integral.anticlimacticlagacy.entities.PermanentItemEntity;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.objects.Vector3;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(EnderDragon.class)
public abstract class MixinEnderDragon extends Mob implements Enemy, IAbyssalHeartBearer {
	@Shadow
	public int dragonDeathTime;
	private Player abyssalHeartOwner;

	protected MixinEnderDragon() {
		super(null, null);
		throw new IllegalStateException("Can't touch this");
	}

	@Inject(method = "tickDeath", at = @At("RETURN"), require = 1)
	private void onTickDeath(CallbackInfo info) {
		if (this.dragonDeathTime == 200 && this.level instanceof ServerLevel) {
			if (this.abyssalHeartOwner != null) {
				int heartsGained = SuperpositionHandler.getPersistentInteger(this.abyssalHeartOwner, "AbyssalHeartsGained", 0);

				Vector3 center = Vector3.fromEntityCenter(this);
				PermanentItemEntity heart = new PermanentItemEntity(this.level, center.x, center.y, center.z, new ItemStack(abyssalHeart, 1));
				heart.setOwnerId(this.abyssalHeartOwner.getUUID());
				this.level.addFreshEntity(heart);

				SuperpositionHandler.setPersistentInteger(this.abyssalHeartOwner, "AbyssalHeartsGained", heartsGained + 1);
			}
		}
	}

	@Override
	public void dropAbyssalHeart(Player player) {
		this.abyssalHeartOwner = player;
	}

}
