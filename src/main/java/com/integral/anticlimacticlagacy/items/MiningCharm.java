package com.integral.anticlimacticlagacy.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.config.OmniconfigHandler;
import com.integral.anticlimacticlagacy.handlers.AnticlimacticEventHandler;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseCurio;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class MiningCharm extends ItemBaseCurio {
	public static Omniconfig.PerhapsParameter breakSpeedBonus;
	public static Omniconfig.DoubleParameter reachDistanceBonus;
	public static Omniconfig.BooleanParameter enableNightVision;
	//public static EnigmaConfig.BooleanParameter bonusFortuneEnabled;

	@SubscribeConfig
	public static void onConfig(OmniconfigWrapper builder) {
		builder.pushPrefix("MiningCharm");

		breakSpeedBonus = builder
				.comment("Mining speed boost granted by Charm of Treasure Hunter. Defined as percentage.")
				.max(1000)
				.getPerhaps("BreakSpeed", 30);

		reachDistanceBonus = builder
				.comment("Additional block reach granted by Charm of Treasure Hunter.")
				.max(16)
				.getDouble("ReachDistance", 2.15);

		enableNightVision = builder
				.comment("Whether Night Vision ability of Charm of Treasure Hunter should be enabled.")
				.getBoolean("EnableNightVision", true);

		builder.popPrefix();
	}

	public final int nightVisionDuration = 310;

	public MiningCharm() {
		super(ItemBaseCurio.getDefaultProperties().rarity(Rarity.RARE));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "mining_charm"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		TranslatableComponent mode = new TranslatableComponent("tooltip.anticlimacticlagacy.enabled");

		if (ItemNBTHelper.verifyExistance(stack, "nightVisionEnabled"))
			if (!ItemNBTHelper.getBoolean(stack, "nightVisionEnabled", true)) {
				mode = new TranslatableComponent("tooltip.anticlimacticlagacy.disabled");
			}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.miningCharm1", ChatFormatting.GOLD, breakSpeedBonus.getValue().asPercentage() + "%");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.miningCharm2", ChatFormatting.GOLD, 1);
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.miningCharm3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.miningCharm4");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.miningCharm5");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.miningCharmNightVision", null, mode.getString());
	}

	public void removeNightVisionEffect(Player player, int duration) {
		if (player.getEffect(MobEffects.NIGHT_VISION) != null) {
			MobEffectInstance effect = player.getEffect(MobEffects.NIGHT_VISION);

			if (effect.getDuration() <= (duration - 1)) {
				player.removeEffect(MobEffects.NIGHT_VISION);
			}
		}
	}

	@Override
	public void curioTick(SlotContext context, ItemStack stack) {
		if (context.entity() instanceof Player player && !context.entity().level.isClientSide)
			if (SuperpositionHandler.hasCurio(player, AnticlimacticLagacy.miningCharm)) {
				if (ItemNBTHelper.getBoolean(stack, "nightVisionEnabled", true)) {
					if (enableNightVision.getValue()) {
						if (player.getY() < 50 && !player.level.dimension().equals(Level.NETHER)
								&& !player.level.dimension().equals(Level.END)
								&& !player.isEyeInFluid(FluidTags.WATER)
								&& !player.level.canSeeSkyFromBelowWater(player.blockPosition())
								/*&& player.level.getMaxLocalRawBrightness(player.blockPosition(), 0) <= 8*/) {

							AnticlimacticEventHandler.isApplyingNightVision = true;
							player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, this.nightVisionDuration, 0, true, false));
							AnticlimacticEventHandler.isApplyingNightVision = false;
						} else {
							//this.removeNightVisionEffect(player, this.nightVisionDuration);
						}
					} else {
						ItemNBTHelper.setBoolean(stack, "nightVisionEnabled", false);
					}
				}
			}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handIn) {
		ItemStack stack = player.getItemInHand(handIn);

		if (ItemNBTHelper.getBoolean(stack, "nightVisionEnabled", true)) {
			ItemNBTHelper.setBoolean(stack, "nightVisionEnabled", false);
			world.playSound(null, player.blockPosition(), AnticlimacticLagacy.HHOFF, SoundSource.PLAYERS, (float) (0.8F + (Math.random() * 0.2F)), (float) (0.8F + (Math.random() * 0.2F)));
		} else {
			if (enableNightVision.getValue()) {
				ItemNBTHelper.setBoolean(stack, "nightVisionEnabled", true);
				world.playSound(null, player.blockPosition(), AnticlimacticLagacy.HHON, SoundSource.PLAYERS, (float) (0.8F + (Math.random() * 0.2F)), (float) (0.8F + (Math.random() * 0.2F)));
			}
		}

		player.swing(handIn);

		return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
	}

	@Override
	public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
		if (context.entity() instanceof Player player) {
			this.removeNightVisionEffect(player, this.nightVisionDuration);
		}
	}

	@Override
	public boolean canEquipFromUse(SlotContext context, ItemStack stack) {
		return false;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		Multimap<Attribute, AttributeModifier> atts = HashMultimap.create();

		atts.put(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(UUID.fromString("08c3c83d-7137-4b42-880f-b146bcb64d2e"), "Reach bonus", reachDistanceBonus.getValue(), AttributeModifier.Operation.ADDITION));

		return atts;
	}

	@Override
	public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack curio) {
		return super.getFortuneLevel(slotContext, lootContext, curio) + 1;
	}

}
