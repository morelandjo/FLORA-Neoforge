package flora.core.init;

import flora.core.ConstantsFLORA;
import flora.core.block.BlockEntityInfuser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLORABlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ConstantsFLORA.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityInfuser>> INFUSER =
        BLOCK_ENTITIES.register("infuser",
            () -> BlockEntityType.Builder.of(BlockEntityInfuser::new, FLORABlocks.INFUSER.get()).build(null));
}
