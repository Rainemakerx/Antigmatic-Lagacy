package com.integral.anticlimacticlagacy.items;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemLoreHelper;
import com.integral.anticlimacticlagacy.items.generic.ItemBase;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RelicOfTesting extends ItemBase {

	public Random lootRandomizer = new Random();

	public RelicOfTesting() {
		super(ItemBase.getDefaultProperties().rarity(Rarity.EPIC).stacksTo(1).tab(null));
		this.setRegistryName(new ResourceLocation(AnticlimacticLagacy.MODID, "relic_of_testing"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
		if (Screen.hasShiftDown()) {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.relicOfTesting1");
		} else {
			ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.holdShift");
		}

		ItemLoreHelper.addLocalizedString(list, "tooltip.anticlimacticlagacy.void");
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

		ItemStack itemstack = playerIn.getItemInHand(handIn);
		playerIn.startUsingItem(handIn);

		SuperpositionHandler.setSpellstoneCooldown(playerIn, 0);

		SuperpositionHandler.setPersistentInteger(playerIn, AnticlimacticLagacy.overworldRevelationTome.persistantPointsTag, 0);

		ItemStack checkTag = playerIn.getInventory().offhand.get(0);

		if (checkTag != null) {
			playerIn.sendMessage(new TextComponent(checkTag.getOrCreateTag().getAsString()), playerIn.getUUID());
		}

		if (!worldIn.isClientSide) {

			//playerIn.sendMessage(new TextComponent("INTEGER: " + UltimaTestConfig.integerTest.getValue()), playerIn.getUniqueID());
			//playerIn.sendMessage(new TextComponent("FLOAT: " + UltimaTestConfig.floatTest.getValue()), playerIn.getUniqueID());
			//playerIn.sendMessage(new TextComponent("BOOLEAN: " + UltimaTestConfig.booleanTest.getValue()), playerIn.getUniqueID());

		}

		/*
		if(!worldIn.isRemote) {
		    Component name = null;

		    if(itemstack.hasDisplayName()) {
		        name = itemstack.getDisplayName();
		    } else {
		        name = new TranslatableComponent(this.getTranslationKey());
		    }

		    playerIn.openContainer(new PortableCrafterContainerProvider(name));
		}
		 */

		playerIn.swing(handIn);

		return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);

	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		List<Creeper> list = world.getEntitiesOfClass(Creeper.class, SuperpositionHandler.getBoundingBoxAroundEntity(entity, 24D));

		for (Creeper creeper : list) {
			creeper.goalSelector.addGoal(1, new AvoidEntityGoal<>(creeper, Player.class, (arg) -> {
				return arg instanceof Player ? SuperpositionHandler.hasCurio(arg, AnticlimacticLagacy.anticlimacticAmulet) : false;
			}, 6.0F, 1.0D, 1.2D, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));

			if (creeper.getTarget() == entity) {
				creeper.setTarget(null);
			}
		}

		if (entity instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer) entity;
			if (entity.tickCount % 20 == 0) {
				//System.out.println("Time since rest: " + player.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)));
			}
		}

		/*

		if (world.isRemote) {
			if (UltimaTestConfig.fovOverride.getValue() != Minecraft.getInstance().gameSettings.fov) {
				Minecraft.getInstance().gameSettings.fov = UltimaTestConfig.fovOverride.getValue();
			}
		}

		 */
	}

	/*
	 * public InteractionResult onItemUse(UseOnContext context) { Player
	 * player = context.getPlayer(); Level world = context.getWorld(); //ItemStack
	 * stack = context.getItem();
	 *
	 * if (world.getBlockState(context.getPos()).hasTileEntity()) { if
	 * (world.getTileEntity(context.getPos()) instanceof ChestTileEntity) {
	 * ChestTileEntity chest = (ChestTileEntity)
	 * world.getTileEntity(context.getPos());
	 *
	 *
	 * if (context.getFace() != Direction.UP) { chest.clear(); } else {
	 * chest.setLootTable(BuiltInLootTables.CHESTS_SIMPLE_DUNGEON,
	 * lootRandomizer.nextLong()); chest.fillWithLoot(player); }
	 *
	 * return InteractionResult.SUCCESS; } }
	 *
	 * return InteractionResult.PASS; }
	 */

}
