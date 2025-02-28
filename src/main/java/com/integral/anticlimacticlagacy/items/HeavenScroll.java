package com.integral.anticlimacticlagacy.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.config.OmniconfigHandler;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ExperienceHelper;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBaseCurio;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.world.item.Item.Properties;

public class HeavenScroll extends ItemBaseCurio {
	public static Omniconfig.DoubleParameter xpCostModifier;

	@SubscribeConfig
	public static void onConfig(OmniconfigWrapper builder) {
		builder.pushPrefix("HeavenScroll");

		xpCostModifier = builder
				.comment("Multiplier for experience consumption by Gift of the Heaven.")
				.getDouble("XPCostModifier", 1.0);

		builder.popPrefix();
	}

	public Map<Player, Integer> flyMap = new WeakHashMap<Player, Integer>();
	public final double baseXpConsumptionProbability = 0.025D/2D;

	public HeavenScroll() {
		super(ItemBaseCurio.getDefaultProperties().rarity(Rarity.EPIC));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "heaven_scroll"));
	}

	public HeavenScroll(Properties properties) {
		super(properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");

		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.heavenTome1");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.heavenTome2");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.heavenTome3");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.heavenTome4");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}
	}

	@Override
	public void curioTick(SlotContext context, ItemStack stack) {
		if (context.entity().level.isClientSide)
			return;

		if (context.entity() instanceof Player player) {
			if (Math.random() <= (this.baseXpConsumptionProbability * xpCostModifier.getValue()) && player.getAbilities().flying) {
				ExperienceHelper.drainPlayerXP(player, 1);
			}

			this.handleFlight(player);
		}

	}

	protected void handleFlight(Player player) {
		try {
			if (ExperienceHelper.getPlayerXP(player) > 0 && SuperpositionHandler.isInBeaconRange(player)) {

				if (!player.getAbilities().mayfly) {
					player.getAbilities().mayfly = true;
				}

				player.onUpdateAbilities();
				this.flyMap.put(player, 100);

			} else if (this.flyMap.get(player) > 1) {
				this.flyMap.put(player, this.flyMap.get(player)-1);
			} else if (this.flyMap.get(player) == 1) {
				if (!player.isCreative()) {
					player.getAbilities().mayfly = false;
					player.getAbilities().flying = false;
					player.onUpdateAbilities();
					player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0, true, false));
				}

				this.flyMap.put(player, 0);
			}

		} catch (NullPointerException ex) {
			ex.printStackTrace();
			this.flyMap.put(player, 0);
		}
	}

	@Override
	public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
		if (context.entity() instanceof Player player) {
			if (!player.isCreative()) {
				player.getAbilities().mayfly = false;
				player.getAbilities().flying = false;
				player.onUpdateAbilities();
			}

			this.flyMap.put(player, 0);
		}
	}

}
