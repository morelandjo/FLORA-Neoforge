package flora.core.init;

import flora.core.ConstantsFLORA;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLORACreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ConstantsFLORA.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FLORA_TAB = CREATIVE_TABS.register("flora_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + ConstantsFLORA.MOD_ID))
            .icon(() -> new ItemStack(FLORAItems.RESONANT_CHESTPLATE.get()))
            .displayItems((parameters, output) -> {
                // Buckets
                output.accept(FLORAItems.COAL_BUCKET.get());
                output.accept(FLORAItems.PYROTHEUM_BUCKET.get());
                output.accept(FLORAItems.CRYOTHEUM_BUCKET.get());
                output.accept(FLORAItems.MANA_BUCKET.get());
                output.accept(FLORAItems.ENDER_BUCKET.get());
                output.accept(FLORAItems.REDSTONE_BUCKET.get());
                output.accept(FLORAItems.GLOWSTONE_BUCKET.get());

                // Infuser
                output.accept(FLORAItems.INFUSER.get());

                // Leadstone Armor
                output.accept(FLORAItems.LEADSTONE_HELMET.get());
                output.accept(FLORAItems.LEADSTONE_CHESTPLATE.get());
                output.accept(FLORAItems.LEADSTONE_LEGGINGS.get());
                output.accept(FLORAItems.LEADSTONE_BOOTS.get());

                // Hardened Armor
                output.accept(FLORAItems.HARDENED_HELMET.get());
                output.accept(FLORAItems.HARDENED_CHESTPLATE.get());
                output.accept(FLORAItems.HARDENED_LEGGINGS.get());
                output.accept(FLORAItems.HARDENED_BOOTS.get());

                // Redstone Armor
                output.accept(FLORAItems.REDSTONE_HELMET.get());
                output.accept(FLORAItems.REDSTONE_CHESTPLATE.get());
                output.accept(FLORAItems.REDSTONE_LEGGINGS.get());
                output.accept(FLORAItems.REDSTONE_BOOTS.get());

                // Resonant Armor
                output.accept(FLORAItems.RESONANT_HELMET.get());
                output.accept(FLORAItems.RESONANT_CHESTPLATE.get());
                output.accept(FLORAItems.RESONANT_LEGGINGS.get());
                output.accept(FLORAItems.RESONANT_BOOTS.get());
            })
            .build());
}
