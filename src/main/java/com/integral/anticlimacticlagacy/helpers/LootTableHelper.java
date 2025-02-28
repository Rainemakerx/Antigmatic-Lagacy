package com.integral.anticlimacticlagacy.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

// TODO This is fucked up, we need better way for doing it
public class LootTableHelper {
	public static Field isFrozenTable = null;
	public static Field isFrozenPool = null;
	public static Field lootPoolsTable = null;

	static {
		try {
			isFrozenTable = LootTable.class.getDeclaredField("isFrozen");
			isFrozenPool = LootPool.class.getDeclaredField("isFrozen");

			try {
				lootPoolsTable = LootTable.class.getDeclaredField("pools");
			} catch (NoSuchFieldException ex) {
				lootPoolsTable = LootTable.class.getDeclaredField("f_79109_");
			}

			isFrozenTable.setAccessible(true);
			isFrozenPool.setAccessible(true);
			lootPoolsTable.setAccessible(true);
		} catch (Throwable ex) {
			AnticlimacticLagacy.logger.fatal("FAILED TO REFLECT LOOTTABLE FIELDS");
			AnticlimacticLagacy.logger.catching(ex);
			throw new RuntimeException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static void unfreezePlease(LootTable table) {
		try {
			isFrozenTable.set(table, false);
			List<LootPool> poolList = (List<LootPool>)lootPoolsTable.get(table);

			for (LootPool pool : poolList) {
				unfreezePlease(pool);
			}
		} catch (Throwable ex) {
			AnticlimacticLagacy.logger.fatal("FAILED TO UNFREEZE LOOT TABLE");
			throw new RuntimeException(ex);
		}
	}

	public static void unfreezePlease(LootPool pool) {
		try {
			isFrozenPool.set(pool, false);
		} catch (Throwable ex) {
			AnticlimacticLagacy.logger.fatal("FAILED TO UNFREEZE LOOT POOL");
			throw new RuntimeException(ex);
		}
	}

}
