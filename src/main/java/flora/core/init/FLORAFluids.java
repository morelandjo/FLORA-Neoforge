package flora.core.init;

import flora.core.ConstantsFLORA;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FLORAFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
        DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ConstantsFLORA.MOD_ID);

    public static final DeferredRegister<Fluid> FLUIDS =
        DeferredRegister.create(BuiltInRegistries.FLUID, ConstantsFLORA.MOD_ID);

    // Fluid Types
    public static final DeferredHolder<FluidType, FluidType> COAL_FLUID_TYPE = FLUID_TYPES.register("coal",
        () -> new FluidType(FluidType.Properties.create()
            .density(1000)
            .viscosity(1000)));

    public static final DeferredHolder<FluidType, FluidType> PYROTHEUM_FLUID_TYPE = FLUID_TYPES.register("pyrotheum",
        () -> new FluidType(FluidType.Properties.create()
            .density(2000)
            .viscosity(1200)
            .temperature(4000)
            .canConvertToSource(false)));

    public static final DeferredHolder<FluidType, FluidType> CRYOTHEUM_FLUID_TYPE = FLUID_TYPES.register("cryotheum",
        () -> new FluidType(FluidType.Properties.create()
            .density(2000)
            .viscosity(1200)
            .temperature(50)
            .canConvertToSource(false)));

    public static final DeferredHolder<FluidType, FluidType> MANA_FLUID_TYPE = FLUID_TYPES.register("mana",
        () -> new FluidType(FluidType.Properties.create()
            .density(800)
            .viscosity(800)));

    public static final DeferredHolder<FluidType, FluidType> ENDER_FLUID_TYPE = FLUID_TYPES.register("ender",
        () -> new FluidType(FluidType.Properties.create()
            .density(1500)
            .viscosity(1500)));

    public static final DeferredHolder<FluidType, FluidType> REDSTONE_FLUID_TYPE = FLUID_TYPES.register("redstone",
        () -> new FluidType(FluidType.Properties.create()
            .density(1200)
            .viscosity(1000)));

    public static final DeferredHolder<FluidType, FluidType> GLOWSTONE_FLUID_TYPE = FLUID_TYPES.register("glowstone",
        () -> new FluidType(FluidType.Properties.create()
            .density(900)
            .viscosity(800)
            .lightLevel(15)));

    // Fluids - Coal
    public static final DeferredHolder<Fluid, FlowingFluid> COAL_FLOWING = FLUIDS.register("coal_flowing",
        () -> new BaseFlowingFluid.Flowing(FLORAFluids.COAL_PROPERTIES));

    public static final DeferredHolder<Fluid, FlowingFluid> COAL_STILL = FLUIDS.register("coal",
        () -> new BaseFlowingFluid.Source(FLORAFluids.COAL_PROPERTIES));

    // Fluids - Pyrotheum
    public static final DeferredHolder<Fluid, FlowingFluid> PYROTHEUM_FLOWING = FLUIDS.register("pyrotheum_flowing",
        () -> new BaseFlowingFluid.Flowing(FLORAFluids.PYROTHEUM_PROPERTIES));

    public static final DeferredHolder<Fluid, FlowingFluid> PYROTHEUM_STILL = FLUIDS.register("pyrotheum",
        () -> new BaseFlowingFluid.Source(FLORAFluids.PYROTHEUM_PROPERTIES));

    // Fluids - Cryotheum
    public static final DeferredHolder<Fluid, FlowingFluid> CRYOTHEUM_FLOWING = FLUIDS.register("cryotheum_flowing",
        () -> new BaseFlowingFluid.Flowing(FLORAFluids.CRYOTHEUM_PROPERTIES));

    public static final DeferredHolder<Fluid, FlowingFluid> CRYOTHEUM_STILL = FLUIDS.register("cryotheum",
        () -> new BaseFlowingFluid.Source(FLORAFluids.CRYOTHEUM_PROPERTIES));

    // Fluids - Mana
    public static final DeferredHolder<Fluid, FlowingFluid> MANA_FLOWING = FLUIDS.register("mana_flowing",
        () -> new BaseFlowingFluid.Flowing(FLORAFluids.MANA_PROPERTIES));

    public static final DeferredHolder<Fluid, FlowingFluid> MANA_STILL = FLUIDS.register("mana",
        () -> new BaseFlowingFluid.Source(FLORAFluids.MANA_PROPERTIES));

    // Fluids - Ender
    public static final DeferredHolder<Fluid, FlowingFluid> ENDER_FLOWING = FLUIDS.register("ender_flowing",
        () -> new BaseFlowingFluid.Flowing(FLORAFluids.ENDER_PROPERTIES));

    public static final DeferredHolder<Fluid, FlowingFluid> ENDER_STILL = FLUIDS.register("ender",
        () -> new BaseFlowingFluid.Source(FLORAFluids.ENDER_PROPERTIES));

    // Fluids - Redstone
    public static final DeferredHolder<Fluid, FlowingFluid> REDSTONE_FLOWING = FLUIDS.register("redstone_flowing",
        () -> new BaseFlowingFluid.Flowing(FLORAFluids.REDSTONE_PROPERTIES));

    public static final DeferredHolder<Fluid, FlowingFluid> REDSTONE_STILL = FLUIDS.register("redstone",
        () -> new BaseFlowingFluid.Source(FLORAFluids.REDSTONE_PROPERTIES));

    // Fluids - Glowstone
    public static final DeferredHolder<Fluid, FlowingFluid> GLOWSTONE_FLOWING = FLUIDS.register("glowstone_flowing",
        () -> new BaseFlowingFluid.Flowing(FLORAFluids.GLOWSTONE_PROPERTIES));

    public static final DeferredHolder<Fluid, FlowingFluid> GLOWSTONE_STILL = FLUIDS.register("glowstone",
        () -> new BaseFlowingFluid.Source(FLORAFluids.GLOWSTONE_PROPERTIES));

    // Fluid Properties
    public static final BaseFlowingFluid.Properties COAL_PROPERTIES = new BaseFlowingFluid.Properties(
        COAL_FLUID_TYPE, COAL_STILL, COAL_FLOWING)
        .bucket(() -> FLORAItems.COAL_BUCKET.get())
        .block(() -> FLORABlocks.COAL_FLUID_BLOCK.get());

    public static final BaseFlowingFluid.Properties PYROTHEUM_PROPERTIES = new BaseFlowingFluid.Properties(
        PYROTHEUM_FLUID_TYPE, PYROTHEUM_STILL, PYROTHEUM_FLOWING)
        .bucket(() -> FLORAItems.PYROTHEUM_BUCKET.get())
        .block(() -> FLORABlocks.PYROTHEUM_FLUID_BLOCK.get());

    public static final BaseFlowingFluid.Properties CRYOTHEUM_PROPERTIES = new BaseFlowingFluid.Properties(
        CRYOTHEUM_FLUID_TYPE, CRYOTHEUM_STILL, CRYOTHEUM_FLOWING)
        .bucket(() -> FLORAItems.CRYOTHEUM_BUCKET.get())
        .block(() -> FLORABlocks.CRYOTHEUM_FLUID_BLOCK.get());

    public static final BaseFlowingFluid.Properties MANA_PROPERTIES = new BaseFlowingFluid.Properties(
        MANA_FLUID_TYPE, MANA_STILL, MANA_FLOWING)
        .bucket(() -> FLORAItems.MANA_BUCKET.get())
        .block(() -> FLORABlocks.MANA_FLUID_BLOCK.get());

    public static final BaseFlowingFluid.Properties ENDER_PROPERTIES = new BaseFlowingFluid.Properties(
        ENDER_FLUID_TYPE, ENDER_STILL, ENDER_FLOWING)
        .bucket(() -> FLORAItems.ENDER_BUCKET.get())
        .block(() -> FLORABlocks.ENDER_FLUID_BLOCK.get());

    public static final BaseFlowingFluid.Properties REDSTONE_PROPERTIES = new BaseFlowingFluid.Properties(
        REDSTONE_FLUID_TYPE, REDSTONE_STILL, REDSTONE_FLOWING)
        .bucket(() -> FLORAItems.REDSTONE_BUCKET.get())
        .block(() -> FLORABlocks.REDSTONE_FLUID_BLOCK.get());

    public static final BaseFlowingFluid.Properties GLOWSTONE_PROPERTIES = new BaseFlowingFluid.Properties(
        GLOWSTONE_FLUID_TYPE, GLOWSTONE_STILL, GLOWSTONE_FLOWING)
        .bucket(() -> FLORAItems.GLOWSTONE_BUCKET.get())
        .block(() -> FLORABlocks.GLOWSTONE_FLUID_BLOCK.get());
}
