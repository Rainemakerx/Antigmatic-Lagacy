package com.integral.etherium.core;

import java.util.Optional;

import javax.annotation.Nullable;

import com.integral.anticlimacticlagacy.objects.Perhaps;
import com.integral.etherium.items.EtheriumAxe;
import com.integral.etherium.items.EtheriumPickaxe;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.ModList;

public interface IEtheriumConfig {

	public Ingredient getRepairMaterial();

	public CreativeModeTab getCreativeTab();

	public String getOwnerMod();

	public ArmorMaterial getArmorMaterial();

	public Tier getToolMaterial();

	public Perhaps getShieldThreshold(@Nullable Player player);

	public Perhaps getShieldReduction();

	public boolean disableAOEShiftInhibition();

	public SoundEvent getAOESoundOn();

	public SoundEvent getAOESoundOff();

	public SoundEvent getShieldTriggerSound();

	public int getAxeMiningVolume();

	public int getScytheMiningVolume();

	public int getPickaxeMiningRadius();

	public int getPickaxeMiningDepth();

	public int getShovelMiningRadius();

	public int getShovelMiningDepth();

	public int getSwordCooldown();

	public int getAOEBoost(@Nullable Player player);

	public void knockBack(LivingEntity entityIn, float strength, double xRatio, double zRatio);

	public boolean isStandalone();

	public default Optional<Material> getSorceryMaterial(String name) {
		if (ModList.get().isLoaded("astralsorcery")) {
			try {
				Class<?> sorceryBlockMaterials = Class.forName("hellfirepvp.astralsorcery.common.lib.MaterialsAS");
				Material material = (Material) sorceryBlockMaterials.getField(name).get(null);
				return Optional.ofNullable(material);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return Optional.empty();
	}

}
