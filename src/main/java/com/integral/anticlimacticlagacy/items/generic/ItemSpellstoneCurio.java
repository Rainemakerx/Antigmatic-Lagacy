package com.integral.anticlimacticlagacy.items.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.apache.http.MethodNotSupportedException;

import com.integral.anticlimacticlagacy.api.generic.SubscribeConfig;
import com.integral.anticlimacticlagacy.api.items.ISpellstone;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.omniconfig.wrappers.Omniconfig;
import com.integral.omniconfig.wrappers.OmniconfigWrapper;

import net.minecraft.world.item.Vanishable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.world.item.Item.Properties;

public abstract class ItemSpellstoneCurio extends ItemBaseCurio implements ISpellstone {
	public static Omniconfig.BooleanParameter multiequip;

	@SubscribeConfig
	public static void onConfig(OmniconfigWrapper builder) {
		builder.pushPrefix("Spellstones");

		multiequip = builder.comment("Whether or not it should be allowed to equip multiple spellstones if they are different items," + " granted player somehow gets more than one spellstone slot.").getBoolean("Multiequip", false);

		builder.popPrefix();
	}

	public List<String> immunityList = new ArrayList<String>();
	public HashMap<String, Supplier<Float>> resistanceList = new HashMap<String, Supplier<Float>>();

	public ItemSpellstoneCurio() {
		this(ItemSpellstoneCurio.getDefaultProperties());
	}

	public ItemSpellstoneCurio(Properties props) {
		super(props);
	}

	public boolean isResistantTo(String damageType) {
		return this.resistanceList.containsKey(damageType);
	}

	public boolean isImmuneTo(String damageType) {
		return this.immunityList.contains(damageType);
	}

	public Supplier<Float> getResistanceModifier(String damageType) {
		return this.resistanceList.get(damageType);
	}

	public static Properties getDefaultProperties() {
		return ItemBaseCurio.getDefaultProperties();
	}

	@Override
	public boolean canEquip(SlotContext context, ItemStack stack) {
		if (multiequip.getValue())
			return super.canEquip(context, stack);
		else
			return super.canEquip(context, stack) && SuperpositionHandler.getSpellstone(context.entity()) == null;
	}

	public int getCooldown(@Nullable Player player) {
		throw new UnsupportedOperationException("This spellstone does not implement getCooldown method.");
	}

}
