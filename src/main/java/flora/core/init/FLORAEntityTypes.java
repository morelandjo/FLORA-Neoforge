package flora.core.init;

import flora.core.ConstantsFLORA;
import flora.core.pulse.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLORAEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ConstantsFLORA.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntityPulseMana>> MANA_PULSE =
        ENTITY_TYPES.register("mana_pulse",
            () -> EntityType.Builder.<EntityPulseMana>of(EntityPulseMana::new, MobCategory.MISC)
                .sized(0.3125F, 0.3125F)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build("mana_pulse"));

    public static final DeferredHolder<EntityType<?>, EntityType<EntityPulseCoal>> COAL_PULSE =
        ENTITY_TYPES.register("coal_pulse",
            () -> EntityType.Builder.<EntityPulseCoal>of(EntityPulseCoal::new, MobCategory.MISC)
                .sized(0.3125F, 0.3125F)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build("coal_pulse"));

    public static final DeferredHolder<EntityType<?>, EntityType<EntityPulsePyrotheum>> PYROTHEUM_PULSE =
        ENTITY_TYPES.register("pyrotheum_pulse",
            () -> EntityType.Builder.<EntityPulsePyrotheum>of(EntityPulsePyrotheum::new, MobCategory.MISC)
                .sized(0.3125F, 0.3125F)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build("pyrotheum_pulse"));

    public static final DeferredHolder<EntityType<?>, EntityType<EntityPulseSlow>> SLOW_PULSE =
        ENTITY_TYPES.register("slow_pulse",
            () -> EntityType.Builder.<EntityPulseSlow>of(EntityPulseSlow::new, MobCategory.MISC)
                .sized(0.3125F, 0.3125F)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build("slow_pulse"));

    public static final DeferredHolder<EntityType<?>, EntityType<EntityPulseEnder>> ENDER_PULSE =
        ENTITY_TYPES.register("ender_pulse",
            () -> EntityType.Builder.<EntityPulseEnder>of(EntityPulseEnder::new, MobCategory.MISC)
                .sized(0.3125F, 0.3125F)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build("ender_pulse"));
}
