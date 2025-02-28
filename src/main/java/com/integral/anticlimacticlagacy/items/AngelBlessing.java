package com.integral.anticlimacticlagacy.items;

import java.util.List;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.api.items.ISpellstone;
import com.integral.anticlimacticlagacy.config.OmniconfigHandler;
import com.integral.anticlimacticlagacy.entities.UltimateWitherSkullEntity;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemSpellstoneCurio;
import com.integral.anticlimacticlagacy.objects.Vector3;
import com.integral.anticlimacticlagacy.packets.clients.PacketForceArrowRotations;
import com.integral.anticlimacticlagacy.packets.clients.PacketPlayerMotion;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;

public class AngelBlessing extends ItemSpellstoneCurio  {
	public static Omniconfig.IntParameter spellstoneCooldown;
	public static Omniconfig.DoubleParameter accelerationModifier;
	public static Omniconfig.DoubleParameter accelerationModifierElytra;
	public static Omniconfig.PerhapsParameter deflectChance;

	@SubscribeConfig
	public static void onConfig(OmniconfigWrapper builder) {
		builder.pushPrefix("AngelBlessing");

		spellstoneCooldown = builder
				.comment("Active ability cooldown for Angel's Blessing. Measured in ticks. 20 ticks equal to 1 second.")
				.getInt("Cooldown", 40);

		accelerationModifier = builder
				.comment("Acceleration modifier for active ability of Angel's Blessing. The greater it is, the more momentum you will gain.")
				.max(256)
				.getDouble("AccelerationModifier", 1.0);

		accelerationModifierElytra = builder
				.comment("Separate acceleration modifier for active ability of Angel's Blessing when player is flying with Elytra.")
				.max(256)
				.getDouble("AccelerationModifierElytra", 0.6);

		deflectChance = builder
				.comment("Chance to deflect projectile when having Angel's Blessing equipped. Measured in percents.")
				.max(100)
				.getPerhaps("DeflectChance", 50);

		builder.popPrefix();
	}

	protected double range = 4.0D;

	public AngelBlessing() {
		super(ItemSpellstoneCurio.getDefaultProperties().rarity(Rarity.RARE));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "angel_blessing"));

		this.immunityList.add(DamageSource.FALL.msgId);
		this.immunityList.add(DamageSource.FLY_INTO_WALL.msgId);

		this.resistanceList.put(DamageSource.WITHER.msgId, () -> 2F);
		this.resistanceList.put(DamageSource.OUT_OF_WORLD.msgId, () -> 2F);
	}

	@Override
	public int getCooldown(Player player) {
		if (player != null && SuperpositionHandler.hasFoolsFavor(player))
			return 15;
		else
			return spellstoneCooldown.getValue();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessing1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessing2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessingCooldown", ChatFormatting.GOLD, ((this.getCooldown(Minecraft.getInstance().player))) / 20.0F);
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessing3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessing4");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessing5", ChatFormatting.GOLD, deflectChance.getValue().asPercentage() + "%");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessing6");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessing7");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessing8");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.angelBlessing9");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

		try {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.currentKeybind", ChatFormatting.LIGHT_PURPLE, KeyMapping.createNameSupplier("key.spellstoneAbility").get().getString().toUpperCase());
		} catch (NullPointerException ex) {
			// Just don't do it lol
		}
	}

	@Override
	public void triggerActiveAbility(Level world, ServerPlayer player, ItemStack stack) {
		if (SuperpositionHandler.hasSpellstoneCooldown(player))
			return;

		Vector3 accelerationVec = new Vector3(player.getLookAngle());
		Vector3 motionVec = new Vector3(player.getDeltaMovement());

		if (player.isFallFlying()) {
			accelerationVec = accelerationVec.multiply(accelerationModifierElytra.getValue());
			accelerationVec = accelerationVec.multiply(1 / (Math.max(0.15D, motionVec.mag()) * 2.25D));
		} else {
			accelerationVec = accelerationVec.multiply(accelerationModifier.getValue());
		}

		Vector3 finalMotion = new Vector3(motionVec.x + accelerationVec.x, motionVec.y + accelerationVec.y, motionVec.z + accelerationVec.z);

		AnticlimacticLagacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> player), new PacketPlayerMotion(finalMotion.x, finalMotion.y, finalMotion.z));
		player.setDeltaMovement(finalMotion.x, finalMotion.y, finalMotion.z);

		world.playSound(null, player.blockPosition(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.PLAYERS, 1.0F, (float) (0.6F + (Math.random() * 0.1D)));

		SuperpositionHandler.setSpellstoneCooldown(player, this.getCooldown(player));
	}

	@Override
	public void curioTick(SlotContext context, ItemStack stack) {
		LivingEntity living = context.entity();

		if (living.level.isClientSide)
			return;

		List<AbstractHurtingProjectile> projectileEntities = living.level.getEntitiesOfClass(AbstractHurtingProjectile.class, new AABB(living.getX() - this.range, living.getY() - this.range, living.getZ() - this.range, living.getX() + this.range, living.getY() + this.range, living.getZ() + this.range));
		List<AbstractArrow> arrowEntities = living.level.getEntitiesOfClass(AbstractArrow.class, new AABB(living.getX() - this.range, living.getY() - this.range, living.getZ() - this.range, living.getX() + this.range, living.getY() + this.range, living.getZ() + this.range));
		List<ThrowableItemProjectile> potionEntities = living.level.getEntitiesOfClass(ThrowableItemProjectile.class, new AABB(living.getX() - this.range, living.getY() - this.range, living.getZ() - this.range, living.getX() + this.range, living.getY() + this.range, living.getZ() + this.range));

		for (AbstractHurtingProjectile entity : projectileEntities) {
			this.redirect(living, entity);
		}

		for (AbstractArrow entity : arrowEntities) {
			this.redirect(living, entity);
		}

		for (ThrowableItemProjectile entity : potionEntities) {
			this.redirect(living, entity);
		}
	}

	public void redirect(LivingEntity bearer, Entity redirected) {
		if (redirected instanceof UltimateWitherSkullEntity || redirected instanceof WitherSkull)
			return;

		Vector3 entityPos = Vector3.fromEntityCenter(redirected);
		Vector3 bearerPos = Vector3.fromEntityCenter(bearer);

		Vector3 redirection = entityPos.subtract(bearerPos);
		redirection = redirection.normalize();

		if ((redirected instanceof AbstractArrow arrow && arrow.getOwner() == bearer)
				|| (redirected instanceof ThrowableItemProjectile projectile && projectile.getOwner() == bearer)) {
			if (redirected.getTags().contains("AB_ACCELERATED")) {
				if (redirected.level instanceof ServerLevel level) {
					//ServerChunkCache cache = level.getChunkSource();
					//cache.broadcastAndSend(redirected, new ClientboundSetEntityMotionPacket(redirected));
					//cache.broadcastAndSend(redirected, new ClientboundTeleportEntityPacket(redirected));
					//anticlimacticlagacy.packetInstance.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(bearer.getX(), bearer.getY(), bearer.getZ(), 64.0D, bearer.level.dimension())), new PacketForceArrowRotations(redirected.getId(), redirected.getYRot(), redirected.getXRot(), redirected.getDeltaMovement().x, redirected.getDeltaMovement().y, redirected.getDeltaMovement().z, redirected.getX(), redirected.getY(), redirected.getZ()));
				}

				return;
			}

			if (redirected.getTags().stream().anyMatch(tag -> tag.startsWith("AB_DEFLECTED")))
				return;

			if (redirected instanceof ThrownTrident) {
				ThrownTrident trident = (ThrownTrident) redirected;

				if (trident.clientSideReturnTridentTickCount > 0)
					return;
			}

			if (redirected.addTag("AB_ACCELERATED")) {
				redirected.setDeltaMovement(redirected.getDeltaMovement().x * 1.75D, redirected.getDeltaMovement().y * 1.75D, redirected.getDeltaMovement().z * 1.75D);

				if (redirected.level instanceof ServerLevel level) {
					AnticlimacticLagacy.packetInstance.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(bearer.getX(), bearer.getY(), bearer.getZ(), 64.0D, bearer.level.dimension())), new PacketForceArrowRotations(redirected.getId(), redirected.getYRot(), redirected.getXRot(), redirected.getDeltaMovement().x, redirected.getDeltaMovement().y, redirected.getDeltaMovement().z, redirected.getX(), redirected.getY(), redirected.getZ()));
				}

				//anticlimacticlagacy.packetInstance.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(bearer.getX(), bearer.getY(), bearer.getZ(), 64.0D, bearer.level.dimension())), new PacketForceArrowRotations(redirected.getId(), redirected.getYRot(), redirected.getXRot(), redirected.getDeltaMovement().x, redirected.getDeltaMovement().y, redirected.getDeltaMovement().z, redirected.getX(), redirected.getY(), redirected.getZ()));
			}
		} else {
			// redirected.setDeltaMovement(redirection.x, redirection.y, redirection.z);
		}

		/*
		if (redirected instanceof AbstractHurtingProjectile) {
			AbstractHurtingProjectile redirectedProjectile = (AbstractHurtingProjectile) redirected;
			redirectedProjectile.xPower = (redirection.x / 4.0);
			redirectedProjectile.yPower = (redirection.y / 4.0);
			redirectedProjectile.zPower = (redirection.z / 4.0);
		}
		 */
	}
}
