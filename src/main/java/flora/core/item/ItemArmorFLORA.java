package flora.core.item;

import flora.core.ConstantsFLORA;
import flora.core.init.FLORAArmorMaterials;
import flora.core.logic.ArmorEffectsManager;
import flora.core.logic.EnumArmorQuality;
import flora.core.logic.EnumArmorType;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemArmorFLORA extends ArmorItem {

    public final EnumArmorType floraType;
    public final EnumArmorQuality quality;

    public ItemArmorFLORA(EnumArmorType type, EnumArmorQuality quality) {
        super(getArmorMaterial(quality), type.armorType,
            new Item.Properties()
                .stacksTo(1));
        this.floraType = type;
        this.quality = quality;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        // Return a very high value to effectively make armor indestructible
        // Original mod used Integer.MAX_VALUE through ISpecialArmor
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        // Never consider the armor damaged
        return false;
    }

    private static Holder<ArmorMaterial> getArmorMaterial(EnumArmorQuality quality) {
        return switch (quality) {
            case LEADSTONE -> FLORAArmorMaterials.LEADSTONE;
            case HARDENED -> FLORAArmorMaterials.HARDENED;
            case REDSTONE -> FLORAArmorMaterials.REDSTONE_ARMOR;
            case RESONANT -> FLORAArmorMaterials.RESONANT;
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        if (flag.hasShiftDown()) {
            List<FluidTankData> tanks = getFluidTanks(stack);
            for (FluidTankData tank : tanks) {
                if (!tank.fluid.isEmpty()) {
                    tooltip.add(Component.literal(tank.fluid.getHoverName().getString() + ": " + tank.fluid.getAmount() + "mB"));
                }
            }
            tooltip.add(Component.literal("Capacity: " + quality.storage + "mB"));

            // Show active effects
            float[][] effectMatrix = ArmorEffectsManager.getEffectMatrix(new ItemStack[]{stack});
            tooltip.add(Component.literal("Active Effects:").withColor(0xAA00AA));

            String[][] descriptions = getDescriptions();
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j <= i; j++) {
                    if (effectMatrix[i][j] > 0) {
                        tooltip.add(Component.literal(descriptions[i][j]).withColor(0x00AA00));
                    }
                }
            }
        } else {
            tooltip.add(Component.literal("Hold Shift for information"));
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(quality.name + " " + floraType.name);
    }

    public static class FluidTankData {
        public FluidStack fluid;
        public int capacity;

        public FluidTankData(FluidStack fluid, int capacity) {
            this.fluid = fluid;
            this.capacity = capacity;
        }
    }

    public List<FluidTankData> getFluidTanks(ItemStack stack) {
        List<FluidTankData> tanks = new ArrayList<>();
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        if (customData.isEmpty()) {
            return tanks;
        }

        var nbt = customData.copyTag();
        if (nbt.contains("FloraTanks")) {
            var tanksList = nbt.getList("FloraTanks", 10); // 10 = TAG_COMPOUND
            for (int i = 0; i < tanksList.size(); i++) {
                var tankNBT = tanksList.getCompound(i);
                // Use built-in registries for fluid lookup
                var lookupProvider = createBuiltInLookupProvider();
                FluidStack fluid = FluidStack.parse(lookupProvider, tankNBT).orElse(FluidStack.EMPTY);
                if (!fluid.isEmpty()) {
                    tanks.add(new FluidTankData(fluid, quality.storage));
                }
            }
        }

        return tanks;
    }

    public void setFluidTanks(ItemStack stack, List<FluidTankData> tanks) {
        var nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        var tanksList = new net.minecraft.nbt.ListTag();
        for (FluidTankData tank : tanks) {
            if (!tank.fluid.isEmpty()) {
                var lookupProvider = createBuiltInLookupProvider();
                var tankNBT = tank.fluid.save(lookupProvider);
                tanksList.add(tankNBT);
            }
        }

        nbt.put("FloraTanks", tanksList);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }

    public int getTotalFluidAmount(ItemStack stack) {
        int total = 0;
        for (FluidTankData tank : getFluidTanks(stack)) {
            total += tank.fluid.getAmount();
        }
        return total;
    }

    public int getFluidCapacity() {
        return quality.storage;
    }

    private String[][] getDescriptions() {
        return new String[][]{
            {"(No Effect)", "Explode burning enemies", "Mining Fatigue", "Confuse Mobs", "Teleport attacking enemies", "Coal Pulse", "Ignite enemies"},
            {"Explode burning enemies", "Increased Fall Damage", "Slowness", "(No Effect)", "Reduced fall damage", "Fire Pulse", "Fire Resistance"},
            {"Mining Fatigue", "Slowness", "Restore Air", "Hunger Loss", "Teleport when hurt", "Slow Pulse", "Cold Damage"},
            {"Confuse Mobs", "(No Effect)", "Hunger Loss", "Cure Effects", "Jump Boost", "Mana Pulse", "Hunger Restore"},
            {"Teleport attacking enemies", "Reduced fall damage", "Teleport when hurt", "Jump Boost", "(No Effect)", "Ender Pulse", "(No Effect)"},
            {"Coal Pulse", "Fire Pulse", "Slow Pulse", "Mana Pulse", "Ender Pulse", "Fluctuating Health", "Explosion Risk"},
            {"Ignite enemies", "Fire Resistance", "Cold Damage", "Hunger Restore", "(No Effect)", "Explosion Risk", "Night Vision"}
        };
    }

    /**
     * Creates a HolderLookup.Provider from the built-in registries.
     * This is used for FluidStack serialization since fluids are in built-in registries.
     */
    private static HolderLookup.Provider createBuiltInLookupProvider() {
        return RegistryAccess.fromRegistryOfRegistries(net.minecraft.core.registries.BuiltInRegistries.REGISTRY);
    }
}
