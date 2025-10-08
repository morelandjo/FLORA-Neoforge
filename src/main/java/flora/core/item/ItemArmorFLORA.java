package flora.core.item;

import flora.core.ConstantsFLORA;
import flora.core.init.FLORAArmorMaterials;
import flora.core.logic.ArmorEffectsManager;
import flora.core.logic.EnumArmorQuality;
import flora.core.logic.EnumArmorType;
import net.minecraft.core.Holder;
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
                .durability(0)
                .stacksTo(1));
        this.floraType = type;
        this.quality = quality;
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
                FluidStack fluid = FluidStack.parse(net.minecraft.core.HolderLookup.Provider.create(java.util.stream.Stream.empty()), tankNBT).orElse(FluidStack.EMPTY);
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
                var tankNBT = new net.minecraft.nbt.CompoundTag();
                tank.fluid.save(net.minecraft.core.HolderLookup.Provider.create(java.util.stream.Stream.empty()), tankNBT);
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
            {"Shoot Fireballs", "Explode burning enemies", "Mining Speed", "Pacify Mobs", "Teleport attacking enemies", "Damaging Pulse", "Ignite enemies"},
            {"Explode burning enemies", "Increase Fall Damage", "Slowness", "Underwater Breath", "Reduced fall damage", "Igniting pulse", "Protection from lava"},
            {"Mining Speed", "Slowness", "Underwater breath", "Hunger Loss", "Teleport away when injured", "Slowness pulse", "Damage in cold environments"},
            {"Pacify Mobs", "Underwater Breath", "Hunger Loss", "Antidote", "Long Jump", "Antidote pulse", "Hunger gain"},
            {"Teleport attacking enemies", "Reduced fall damage", "Teleport away when injured", "Long Jump", "Blink", "Teleportation pulse", "Fall through ground when sneaking"},
            {"Damaging Pulse", "Igniting pulse", "Slowness pulse", "Antidote pulse", "Teleportation pulse", "Fluctuating max health", "Self-Combustion"},
            {"Ignite enemies", "Protection from lava", "Damage in cold environments", "Hunger gain", "Fall through ground when sneaking", "Self-Combustion", "Night Vision"}
        };
    }
}
