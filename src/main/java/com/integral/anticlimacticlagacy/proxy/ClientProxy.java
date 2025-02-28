package com.integral.anticlimacticlagacy.proxy;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;
import com.integral.anticlimacticlagacy.AnticlimacticLagacy;
import com.integral.anticlimacticlagacy.client.fx.PermanentItemPickupParticle;
import com.integral.anticlimacticlagacy.client.renderers.PermanentItemRenderer;
import com.integral.anticlimacticlagacy.client.renderers.UltimateWitherSkullRenderer;
import com.integral.anticlimacticlagacy.entities.AnticlimacticPotionEntity;
import com.integral.anticlimacticlagacy.entities.PermanentItemEntity;
import com.integral.anticlimacticlagacy.entities.UltimateWitherSkullEntity;
import com.integral.anticlimacticlagacy.handlers.SuperpositionHandler;
import com.integral.anticlimacticlagacy.helpers.ItemNBTHelper;
import com.integral.anticlimacticlagacy.objects.RevelationTomeToast;
import com.integral.anticlimacticlagacy.objects.TransientPlayerData;
import com.integral.etherium.client.ShieldAuraLayer;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientProxy extends CommonProxy {
	private static final Random random = new Random();
	protected final Map<Player, TransientPlayerData> clientTransientPlayerData;
	protected final List<InfinitumCounterEntry> theInfinitumHoldTicks;

	public ClientProxy() {
		super();
		this.clientTransientPlayerData = new WeakHashMap<>();
		this.theInfinitumHoldTicks = new ArrayList<>();

		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(this::onClientSetup);
	}

	@Override
	public void clearTransientData() {
		super.clearTransientData();
		this.clientTransientPlayerData.clear();
		this.theInfinitumHoldTicks.clear();
	}

	@Override
	public Map<Player, TransientPlayerData> getTransientPlayerData(boolean clientOnly) {
		if (clientOnly)
			return this.clientTransientPlayerData;
		else
			return this.commonTransientPlayerData;
	}

	@Override
	public void displayReviveAnimation(int entityID, int reviveType) {
		Player player = this.getClientPlayer();
		Entity entity = player.level.getEntity(entityID);

		if (entity != null) {
			Item item = reviveType == 0 ? AnticlimacticLagacy.cosmicScroll : AnticlimacticLagacy.theCube;
			int i = 40;
			Minecraft.getInstance().particleEngine.createTrackingEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);

			int amount = 50;

			for (int counter = 0; counter <= amount; counter++) {
				player.level.addParticle(ParticleTypes.FLAME, true, entity.getX() + (Math.random() - 0.5), entity.getY() + (entity.getBbHeight()/2) + (Math.random() - 0.5), entity.getZ() + (Math.random() - 0.5), (Math.random() - 0.5D) * 0.2, (Math.random() - 0.5D) * 0.2, (Math.random() - 0.5D) * 0.2);
			}

			player.level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 1.0F, 1.0F, false);

			if (entity == player) {
				ItemStack stack = SuperpositionHandler.getCurioStack(player, item);

				if (stack == null) {
					stack = new ItemStack(item, 1);
				}

				Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
			}
		}
	}

	@Override
	public void handleItemPickup(int pickuper_id, int item_id) {
		try {
			Entity pickuper = Minecraft.getInstance().level.getEntity(pickuper_id);
			Entity entity = Minecraft.getInstance().level.getEntity(item_id);

			// TODO Verify fix... someday

			Minecraft.getInstance().particleEngine.add(new PermanentItemPickupParticle(Minecraft.getInstance().getEntityRenderDispatcher(), Minecraft.getInstance().renderBuffers(), Minecraft.getInstance().level, pickuper, entity));
			Minecraft.getInstance().level.playLocalSound(pickuper.getX(), pickuper.getY(), pickuper.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, (ClientProxy.random.nextFloat() - ClientProxy.random.nextFloat()) * 1.4F + 2.0F, false);
		} catch (Throwable ex) {
			Exception log = new Exception("Unknown error when rendering permanent item pickup", ex);
			AnticlimacticLagacy.logger.catching(log);
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void addLayers(EntityRenderersEvent.AddLayers evt) {
		this.addPlayerLayer(evt, "default");
		this.addPlayerLayer(evt, "slim");
	}

	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	private void addPlayerLayer(EntityRenderersEvent.AddLayers evt, String skin) {
		EntityRenderer<? extends Player> renderer = evt.getSkin(skin);

		if (renderer instanceof LivingEntityRenderer livingRenderer) {
			livingRenderer.addLayer(new ShieldAuraLayer(livingRenderer, Minecraft.getInstance().getEntityModels()));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void onClientSetup(FMLClientSetupEvent event) {
		ItemProperties.register(AnticlimacticLagacy.infernalShield, new ResourceLocation("blocking"),
				(stack, world, entity, seed) -> entity != null && entity.isUsingItem()
				&& entity.getUseItem() == stack ? 1 : 0);

		ItemProperties.register(AnticlimacticLagacy.theInfinitum, new ResourceLocation(AnticlimacticLagacy.MODID, "the_infinitum_open"), (stack, world, entity, seed) -> {
			if (entity instanceof Player player) {
				for (InfinitumCounterEntry entry : this.theInfinitumHoldTicks) {
					if (entry.getPlayer() == player && entry.getStack() == stack)
						return entry.animValue;
				}

				ItemStack mainhand = player.getMainHandItem();
				ItemStack offhand = player.getOffhandItem();

				if (mainhand == stack || offhand == stack)
					if (SuperpositionHandler.isTheWorthyOne(player)) {
						this.theInfinitumHoldTicks.add(new InfinitumCounterEntry(player, stack));
					}
			}

			return 0;
		});
	}

	@Override
	public void updateInfinitumCounters() {
		for (InfinitumCounterEntry entry : new ArrayList<>(this.theInfinitumHoldTicks)) {
			if (entry.getPlayer() == null || entry.getStack() == null) {
				this.theInfinitumHoldTicks.remove(entry);
				continue;
			}

			ItemStack stack = entry.getStack();
			Player player = entry.getPlayer();
			int holdTicks = entry.counter;
			ItemStack mainhand = player.getMainHandItem();
			ItemStack offhand = player.getOffhandItem();

			if (mainhand == stack || offhand == stack)
				if (SuperpositionHandler.isTheWorthyOne(player)) {
					if (entry.counter > 5) {
						entry.animValue = 1F;
					} else if (entry.counter == 5) {
						entry.animValue = 0.5F;
					} else {
						entry.animValue = 0F;
					}

					entry.counter++;
					continue;
				}

			if (entry.counter <= 0) {
				this.theInfinitumHoldTicks.remove(entry);
			} else {
				if (entry.counter > 5) {
					entry.counter = 5;
				}

				if (entry.counter > 1) {
					entry.animValue = 1F;
				} else if (entry.counter == 1) {
					entry.animValue = 0.5F;
				} else {
					entry.animValue = 0F;
				}

				entry.counter--;

			}
		}
	}

	@Override
	public void initEntityRendering() {
		EntityRenderers.register(PermanentItemEntity.TYPE, renderManager -> new PermanentItemRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));
		EntityRenderers.register(AnticlimacticPotionEntity.TYPE, ThrownItemRenderer::new);
		EntityRenderers.register(UltimateWitherSkullEntity.TYPE, UltimateWitherSkullRenderer::new);
	}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {
		this.initAuxiliaryRender();
	}

	@Override
	public boolean isInVanillaDimension(Player player) {
		return player.level.dimension().equals(this.getOverworldKey()) || player.level.dimension().equals(this.getNetherKey()) || player.level.dimension().equals(this.getEndKey());
	}

	@Override
	public boolean isInDimension(Player player, ResourceKey<Level> world) {
		return player.level.dimension().equals(world);
	}

	@Override
	public Level getCentralWorld() {
		return Minecraft.getInstance().level;
	}

	@Override
	public UseAnim getVisualBlockAction() {
		return UseAnim.BLOCK;
	}

	@Override
	public Player getPlayer(UUID playerID) {
		if (Minecraft.getInstance().level != null)
			return Minecraft.getInstance().level.getPlayerByUUID(playerID);
		else
			return null;
	}

	@Override
	public void pushRevelationToast(ItemStack renderedStack, int xp, int knowledge) {
		ToastComponent gui = Minecraft.getInstance().getToasts();
		gui.addToast(new RevelationTomeToast(renderedStack, xp, knowledge));
	}

	@Override
	@SuppressWarnings("deprecation")
	public void spawnBonemealParticles(Level world, BlockPos pos, int data) {
		if (data == 0) {
			data = 15;
		}

		BlockState blockstate = world.getBlockState(pos);
		if (!blockstate.isAir()) {
			double d0 = 0.5D;
			double d1;
			if (blockstate.is(Blocks.WATER)) {
				data *= 3;
				d1 = 1.0D;
				d0 = 3.0D;
			} else if (blockstate.isSolidRender(world, pos)) {
				pos = pos.above();
				data *= 3;
				d0 = 3.0D;
				d1 = 1.0D;
			} else {
				d1 = blockstate.getShape(world, pos).max(Direction.Axis.Y);
			}

			world.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);

			for(int i = 0; i < data; ++i) {
				double d2 = random.nextGaussian() * 0.02D;
				double d3 = random.nextGaussian() * 0.02D;
				double d4 = random.nextGaussian() * 0.02D;
				double d5 = 0.5D - d0;
				double d6 = pos.getX() + d5 + random.nextDouble() * d0 * 2.0D;
				double d7 = pos.getY() + random.nextDouble() * d1;
				double d8 = pos.getZ() + d5 + random.nextDouble() * d0 * 2.0D;

				world.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
			}

		}
	}

	@Override
	public Player getClientPlayer() {
		return Minecraft.getInstance().player;
	}

	@Override
	public int getStats(Player player, ResourceLocation stat) {
		if (player instanceof LocalPlayer playerLP)
			return playerLP.getStats().getValue(Stats.CUSTOM.get(stat));
		else
			return super.getStats(player, stat);
	}

	private static class InfinitumCounterEntry {
		private final WeakReference<ItemStack> stack;
		private final WeakReference<Player> player;
		private int counter = 0;
		private float animValue = 0F;

		public InfinitumCounterEntry(Player player, ItemStack stack) {
			this.player = new WeakReference<>(player);
			this.stack = new WeakReference<>(stack);
		}

		public Player getPlayer() {
			return this.player.get();
		}

		public ItemStack getStack() {
			return this.stack.get();
		}
	}

}
