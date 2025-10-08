package flora.client;

import flora.client.gui.ScreenInfuser;
import flora.core.ConstantsFLORA;
import flora.core.init.FLORAFluids;
import flora.core.init.FLORAMenuTypes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;

@EventBusSubscriber(modid = ConstantsFLORA.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(FLORAMenuTypes.INFUSER.get(), ScreenInfuser::new);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        // Register fluid rendering properties for all fluids
        registerFluidRenderProperties(event, "coal", FLORAFluids.COAL_FLUID_TYPE.get());
        registerFluidRenderProperties(event, "pyrotheum", FLORAFluids.PYROTHEUM_FLUID_TYPE.get());
        registerFluidRenderProperties(event, "cryotheum", FLORAFluids.CRYOTHEUM_FLUID_TYPE.get());
        registerFluidRenderProperties(event, "mana", FLORAFluids.MANA_FLUID_TYPE.get());
        registerFluidRenderProperties(event, "ender", FLORAFluids.ENDER_FLUID_TYPE.get());
        registerFluidRenderProperties(event, "redstone", FLORAFluids.REDSTONE_FLUID_TYPE.get());
        registerFluidRenderProperties(event, "glowstone", FLORAFluids.GLOWSTONE_FLUID_TYPE.get());
    }

    private static void registerFluidRenderProperties(RegisterClientExtensionsEvent event, String name, FluidType fluidType) {
        final ResourceLocation stillTexture = ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "block/" + name + "_still");
        final ResourceLocation flowingTexture = ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "block/" + name + "_flow");

        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }
        }, fluidType);
    }
}
