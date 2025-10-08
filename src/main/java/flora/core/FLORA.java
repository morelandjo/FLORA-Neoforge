package flora.core;

import com.mojang.logging.LogUtils;
import flora.core.init.FLORAArmorMaterials;
import flora.core.init.FLORABlocks;
import flora.core.init.FLORABlockEntities;
import flora.core.init.FLORACreativeTabs;
import flora.core.init.FLORAEntityTypes;
import flora.core.init.FLORAFluids;
import flora.core.init.FLORAItems;
import flora.core.init.FLORAMenuTypes;
import flora.core.logic.ArmorEffectsManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(ConstantsFLORA.MOD_ID)
public class FLORA {

    public static final Logger LOGGER = LogUtils.getLogger();

    public FLORA(IEventBus modEventBus) {
        LOGGER.info("Initializing FLORA mod...");

        // Register deferred registers
        FLORAFluids.FLUIDS.register(modEventBus);
        FLORAFluids.FLUID_TYPES.register(modEventBus);
        FLORABlocks.BLOCKS.register(modEventBus);
        FLORAArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        FLORAItems.ITEMS.register(modEventBus);
        FLORABlockEntities.BLOCK_ENTITIES.register(modEventBus);
        FLORAEntityTypes.ENTITY_TYPES.register(modEventBus);
        FLORAMenuTypes.MENU_TYPES.register(modEventBus);
        FLORACreativeTabs.CREATIVE_TABS.register(modEventBus);

        // Setup event listeners
        modEventBus.addListener(this::commonSetup);

        // Register armor effects manager to game event bus
        NeoForge.EVENT_BUS.register(new ArmorEffectsManager());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("FLORA common setup complete");
    }
}
