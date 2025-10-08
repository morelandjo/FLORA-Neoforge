package flora.core.init;

import flora.core.ConstantsFLORA;
import flora.core.block.BlockInfuser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLORABlocks {

    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(BuiltInRegistries.BLOCK, ConstantsFLORA.MOD_ID);

    // Fluid Blocks
    public static final DeferredHolder<Block, LiquidBlock> COAL_FLUID_BLOCK = BLOCKS.register("coal",
        () -> new LiquidBlock(FLORAFluids.COAL_STILL.get(), BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()));

    public static final DeferredHolder<Block, LiquidBlock> PYROTHEUM_FLUID_BLOCK = BLOCKS.register("pyrotheum",
        () -> new LiquidBlock(FLORAFluids.PYROTHEUM_STILL.get(), BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_ORANGE)
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()
            .lightLevel((state) -> 15)));

    public static final DeferredHolder<Block, LiquidBlock> CRYOTHEUM_FLUID_BLOCK = BLOCKS.register("cryotheum",
        () -> new LiquidBlock(FLORAFluids.CRYOTHEUM_STILL.get(), BlockBehaviour.Properties.of()
            .mapColor(MapColor.ICE)
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()));

    public static final DeferredHolder<Block, LiquidBlock> MANA_FLUID_BLOCK = BLOCKS.register("mana",
        () -> new LiquidBlock(FLORAFluids.MANA_STILL.get(), BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_PURPLE)
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()));

    public static final DeferredHolder<Block, LiquidBlock> ENDER_FLUID_BLOCK = BLOCKS.register("ender",
        () -> new LiquidBlock(FLORAFluids.ENDER_STILL.get(), BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()));

    public static final DeferredHolder<Block, LiquidBlock> REDSTONE_FLUID_BLOCK = BLOCKS.register("redstone",
        () -> new LiquidBlock(FLORAFluids.REDSTONE_STILL.get(), BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_RED)
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()
            .lightLevel((state) -> 7)));

    public static final DeferredHolder<Block, LiquidBlock> GLOWSTONE_FLUID_BLOCK = BLOCKS.register("glowstone",
        () -> new LiquidBlock(FLORAFluids.GLOWSTONE_STILL.get(), BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()
            .lightLevel((state) -> 15)));

    // Infuser Block
    public static final DeferredHolder<Block, BlockInfuser> INFUSER = BLOCKS.register("infuser",
        () -> new BlockInfuser());
}
