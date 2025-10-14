package flora.core.logic;

import flora.core.ConstantsFLORA;
import flora.core.item.ItemArmorFLORA;
import flora.core.pulse.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmorEffectsManager {

    // Tag keys for fluid types
    private static final TagKey<Fluid> COAL_TAG = TagKey.create(BuiltInRegistries.FLUID.key(),
        ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "coal"));
    private static final TagKey<Fluid> PYROTHEUM_TAG = TagKey.create(BuiltInRegistries.FLUID.key(),
        ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "pyrotheum"));
    private static final TagKey<Fluid> CRYOTHEUM_TAG = TagKey.create(BuiltInRegistries.FLUID.key(),
        ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "cryotheum"));
    private static final TagKey<Fluid> MANA_TAG = TagKey.create(BuiltInRegistries.FLUID.key(),
        ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "mana"));
    private static final TagKey<Fluid> ENDER_TAG = TagKey.create(BuiltInRegistries.FLUID.key(),
        ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "ender"));
    private static final TagKey<Fluid> REDSTONE_TAG = TagKey.create(BuiltInRegistries.FLUID.key(),
        ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "redstone"));
    private static final TagKey<Fluid> GLOWSTONE_TAG = TagKey.create(BuiltInRegistries.FLUID.key(),
        ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "glowstone"));

    /**
     * Returns the fluid type index (0-6) for a given fluid, or -1 if not recognized.
     * Uses tags to support fluids from other mods.
     */
    private static int getFluidTypeIndex(Fluid fluid) {
        if (fluid.is(COAL_TAG)) return 0;
        if (fluid.is(PYROTHEUM_TAG)) return 1;
        if (fluid.is(CRYOTHEUM_TAG)) return 2;
        if (fluid.is(MANA_TAG)) return 3;
        if (fluid.is(ENDER_TAG)) return 4;
        if (fluid.is(REDSTONE_TAG)) return 5;
        if (fluid.is(GLOWSTONE_TAG)) return 6;
        return -1;
    }

    /**
     * Checks if a fluid is supported by the armor effects system.
     */
    public static boolean isSupportedFluid(Fluid fluid) {
        return getFluidTypeIndex(fluid) >= 0;
    }

    public static float[][] getEffectMatrix(ItemStack[] armor) {
        float[] totalFluidCount = new float[7];

        for (ItemStack stack : armor) {
            if (stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemArmorFLORA armorItem) {
                for (ItemArmorFLORA.FluidTankData tank : armorItem.getFluidTanks(stack)) {
                    if (!tank.fluid.isEmpty()) {
                        int fluidIndex = getFluidTypeIndex(tank.fluid.getFluid());
                        if (fluidIndex >= 0) {
                            totalFluidCount[fluidIndex] += (tank.fluid.getAmount() / 1000F);
                        }
                    }
                }
            }
        }

        float[][] fluidInteractionMatrix = new float[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                fluidInteractionMatrix[i][j] = 10F * (float) Math.sqrt(totalFluidCount[i] * totalFluidCount[j]);
            }
        }
        return fluidInteractionMatrix;
    }

    public static float[][] getEffectMatrix(Player player) {
        ItemStack[] armor = new ItemStack[4];
        List<ItemStack> armorList = player.getInventory().armor;
        for (int i = 0; i < 4 && i < armorList.size(); i++) {
            armor[i] = armorList.get(i);
        }
        return getEffectMatrix(armor);
    }

    private final HashMap<String, Float> modifiedMaxHealthPlayers = new HashMap<>();
    private HashMap<String, Long> fireResistenceCooldown = new HashMap<>();

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        float[][] fluidInteractionMatrix = getEffectMatrix(player);
        float intensity;
        RandomSource rand = player.getRandom();

        // Glowstone-Glowstone: Night Vision
        if (fluidInteractionMatrix[6][6] > 0) {
            intensity = fluidInteractionMatrix[6][6];
            if (rand.nextInt(100) < intensity) {
                // Use 240 ticks (12 seconds) to prevent flickering transition effect
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0, false, false));
            }
        }

        // Redstone-Redstone: Fluctuating max health
        if (fluidInteractionMatrix[5][5] > 0) {
            intensity = fluidInteractionMatrix[5][5];
            if (player.level().getGameTime() % 600 == 0) {
                String playerName = player.getGameProfile().getName();
                if (!modifiedMaxHealthPlayers.containsKey(playerName)) {
                    modifiedMaxHealthPlayers.put(playerName, (float) player.getAttributeValue(Attributes.MAX_HEALTH));
                }
                double newHealth = Math.max(4, (intensity * 0.005 * rand.nextGaussian()) + 20);
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(newHealth);
            }
        } else {
            String playerName = player.getGameProfile().getName();
            if (modifiedMaxHealthPlayers.containsKey(playerName)) {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(modifiedMaxHealthPlayers.get(playerName));
                modifiedMaxHealthPlayers.remove(playerName);
            }
        }

        // Mana-Mana: Cure effects
        if (fluidInteractionMatrix[3][3] > 0) {
            intensity = fluidInteractionMatrix[3][3];
            if (rand.nextInt(2500) < intensity) {
                player.removeAllEffects();
            }
        }

        // Cryotheum-Cryotheum: Restore air
        if (fluidInteractionMatrix[2][2] > 0) {
            intensity = fluidInteractionMatrix[2][2];
            if (rand.nextInt(10000) < intensity) {
                player.setAirSupply(200);
            }
        }

        // Redstone-Glowstone: Explosion (dangerous!)
        if (fluidInteractionMatrix[5][6] > 0) {
            intensity = fluidInteractionMatrix[5][6];
            if (rand.nextInt(720000) < intensity) {
                player.level().explode(player, player.getX(), player.getY(), player.getZ(), 20F, true, ServerLevel.ExplosionInteraction.TNT);
            }
        }

        // Glowstone-Mana: Restore hunger
        if (fluidInteractionMatrix[3][6] > 0) {
            intensity = fluidInteractionMatrix[3][6];
            if (rand.nextInt(200000) < intensity) {
                FoodData stats = player.getFoodData();
                stats.eat(4, 0.3F);
            }
        }

        // Glowstone-Cryotheum: Damage in cold
        if (fluidInteractionMatrix[2][6] > 0) {
            intensity = fluidInteractionMatrix[2][6];
            if (player.level().getBiome(player.blockPosition()).value().getBaseTemperature() <= 0.2F) {
                if (rand.nextInt(1000) < intensity) {
                    player.hurt(player.damageSources().starve(), 1F);
                }
            }
        }

        // Pulse effects (Redstone-Mana, Coal-Redstone, Pyrotheum-Redstone, Cryotheum-Redstone, Ender-Redstone)
        spawnPulses(player, fluidInteractionMatrix, rand);

        // Cryotheum-Mana: Hunger loss
        if (fluidInteractionMatrix[3][2] > 0) {
            intensity = fluidInteractionMatrix[3][2];
            if (rand.nextInt(50000) < intensity) {
                FoodData stats = player.getFoodData();
                stats.setFoodLevel(Math.max(0, stats.getFoodLevel() - 1));
            }
        }

        // Pyrotheum-Mana: Speed
        if (fluidInteractionMatrix[3][1] > 0) {
            intensity = fluidInteractionMatrix[3][1];
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, (int) Math.log10(intensity), false, false));
        }

        // Pyrotheum-Cryotheum: Slowness
        if (fluidInteractionMatrix[2][1] > 0) {
            intensity = fluidInteractionMatrix[2][1];
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, (int) Math.log10(intensity), false, false));
        }

        // Coal-Cryotheum: Mining fatigue
        if (fluidInteractionMatrix[2][0] > 0) {
            intensity = fluidInteractionMatrix[2][0];
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1, (int) Math.log10(intensity), false, false));
        }

        // Ender-Mana: Jump boost
        if (fluidInteractionMatrix[3][4] > 0) {
            intensity = fluidInteractionMatrix[3][4];
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 1, (int) Math.log10(intensity), false, false));
        }

        // Coal-Mana: Confuse mobs
        if (fluidInteractionMatrix[0][3] > 0) {
            intensity = fluidInteractionMatrix[0][3];
            if (rand.nextInt(500) < intensity) {
                AABB area = player.getBoundingBox().inflate(20, 3, 20);
                List<LivingEntity> nearbyEntities = player.level().getEntitiesOfClass(LivingEntity.class, area);
                if (nearbyEntities.size() > 1) {
                    LivingEntity target = nearbyEntities.get(rand.nextInt(nearbyEntities.size()));
                    LivingEntity newTarget = nearbyEntities.get(rand.nextInt(nearbyEntities.size()));
                    target.setLastHurtByMob(newTarget);
                }
            }
        }

        // Update fire resistance cooldown
        HashMap<String, Long> fireResistenceCooldownNext = new HashMap<>();
        for (Map.Entry<String, Long> entry : fireResistenceCooldown.entrySet()) {
            if (entry.getValue() > 0) {
                fireResistenceCooldownNext.put(entry.getKey(), entry.getValue() - 1);
            }
        }
        fireResistenceCooldown = fireResistenceCooldownNext;
    }

    private void spawnPulses(Player player, float[][] matrix, RandomSource rand) {
        // Redstone-Mana: Mana pulse
        spawnPulseIfTriggered(player, matrix[3][5], rand, () -> new EntityPulseMana(player.level(), player));

        // Coal-Redstone: Coal pulse
        spawnPulseIfTriggered(player, matrix[0][5], rand, () -> new EntityPulseCoal(player.level(), player));

        // Pyrotheum-Redstone: Pyrotheum pulse
        spawnPulseIfTriggered(player, matrix[1][5], rand, () -> new EntityPulsePyrotheum(player.level(), player));

        // Cryotheum-Redstone: Slow pulse
        spawnPulseIfTriggered(player, matrix[2][5], rand, () -> new EntityPulseSlow(player.level(), player));

        // Ender-Redstone: Ender pulse
        spawnPulseIfTriggered(player, matrix[4][5], rand, () -> new EntityPulseEnder(player.level(), player));
    }

    private void spawnPulseIfTriggered(Player player, float intensity, RandomSource rand, java.util.function.Supplier<EntityPulse> pulseSupplier) {
        if (intensity > 0 && rand.nextInt(5000) < intensity) {
            AABB area = player.getBoundingBox().inflate(20, 3, 20);
            List<LivingEntity> nearbyEntities = player.level().getEntitiesOfClass(LivingEntity.class, area);
            if (nearbyEntities.size() > 1) {
                LivingEntity target = nearbyEntities.get(rand.nextInt(nearbyEntities.size()));
                if (target != player) {
                    EntityPulse pulse = pulseSupplier.get();
                    pulse.setPos(player.getX(), player.getY() + 1, player.getZ());
                    pulse.shoot(target.getX() - player.getX(), target.getY() - player.getY(), target.getZ() - player.getZ(), 1.0F, 1.0F);
                    player.level().addFreshEntity(pulse);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerHurt(LivingDamageEvent.Pre event) {
        Entity attacker = event.getSource().getEntity();

        // Effects when player attacks
        if (attacker instanceof Player attackerPlayer) {
            if (attackerPlayer.level().isClientSide) return;

            float[][] fluidInteractionMatrix = getEffectMatrix(attackerPlayer);
            float intensity;

            // Glowstone-Coal: Set fire
            if (fluidInteractionMatrix[0][6] > 0) {
                intensity = fluidInteractionMatrix[0][6];
                event.getEntity().igniteForSeconds((int) intensity);
            }

            // Coal-Pyrotheum: Explode burning enemies
            if (fluidInteractionMatrix[1][0] > 0) {
                intensity = fluidInteractionMatrix[1][0];
                if (event.getEntity().isOnFire()) {
                    event.getEntity().level().explode(event.getEntity(),
                        event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                        (float) Math.sqrt(intensity), true, ServerLevel.ExplosionInteraction.MOB);
                }
            }
        }

        // Effects when player is hurt
        if (event.getEntity() instanceof Player player) {
            if (player.level().isClientSide) return;

            float[][] fluidInteractionMatrix = getEffectMatrix(player);
            float intensity;
            RandomSource rand = player.getRandom();

            // Cryotheum-Ender: Teleport when low health
            if (fluidInteractionMatrix[2][4] > 0) {
                intensity = fluidInteractionMatrix[2][4];
                if (player.getHealth() - event.getNewDamage() <= 4) {
                    event.setNewDamage(0);
                    teleportPlayerRandomly(player, intensity, rand);
                }
            }

            // Coal-Ender: Teleport attacker
            if (fluidInteractionMatrix[0][4] > 0 && attacker instanceof Mob mob) {
                event.setNewDamage(0);
                intensity = fluidInteractionMatrix[0][4];
                teleportEntityRandomly(mob, intensity, rand);
            }

            // Pyrotheum-Glowstone: Fire resistance
            if (fluidInteractionMatrix[1][6] > 0) {
                intensity = fluidInteractionMatrix[1][6];
                if (event.getSource().is(net.minecraft.tags.DamageTypeTags.IS_FIRE)) {
                    String playerName = player.getGameProfile().getName();
                    if (!fireResistenceCooldown.containsKey(playerName) ||
                        fireResistenceCooldown.get(playerName) < intensity * 10) {
                        long currentCooldown = fireResistenceCooldown.getOrDefault(playerName, 0L);
                        fireResistenceCooldown.put(playerName, Math.max(40, currentCooldown + 40));
                        event.setNewDamage(0);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        float[][] fluidInteractionMatrix = getEffectMatrix(player);
        float intensity;

        // Pyrotheum-Pyrotheum: Increased fall damage
        if (fluidInteractionMatrix[1][1] > 0) {
            intensity = fluidInteractionMatrix[1][1];
            if (event.getDistance() > 2) {
                event.setDistance((float) (event.getDistance() + Math.sqrt(intensity)));
            }
        }

        // Ender-Pyrotheum: Reduced fall damage
        if (fluidInteractionMatrix[1][4] > 0) {
            intensity = fluidInteractionMatrix[1][4];
            event.setDistance(event.getDistance() / intensity);
        }
    }

    private void teleportPlayerRandomly(Player player, float intensity, RandomSource rand) {
        int x = (int) (player.getX() + rand.nextInt((int) (6 * intensity)) - (3 * intensity));
        int z = (int) (player.getZ() + rand.nextInt((int) (6 * intensity)) - (3 * intensity));
        int y = 255;
        BlockPos pos = new BlockPos(x, y, z);
        while (player.level().isEmptyBlock(pos.below()) && y > player.level().getMinBuildHeight()) {
            y--;
            pos = new BlockPos(x, y, z);
        }
        player.teleportTo(x + 0.5, y, z + 0.5);
    }

    private void teleportEntityRandomly(Mob entity, float intensity, RandomSource rand) {
        int x = (int) (entity.getX() + rand.nextInt((int) (2 * intensity)) - intensity);
        int z = (int) (entity.getZ() + rand.nextInt((int) (2 * intensity)) - intensity);
        int y = 255;
        BlockPos pos = new BlockPos(x, y, z);
        while (entity.level().isEmptyBlock(pos.below()) && y > entity.level().getMinBuildHeight()) {
            y--;
            pos = new BlockPos(x, y, z);
        }
        entity.teleportTo(x + 0.5, y, z + 0.5);
    }
}
