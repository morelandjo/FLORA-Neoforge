package flora.core.block;

import flora.core.gui.MenuInfuser;
import flora.core.init.FLORABlockEntities;
import flora.core.init.FLORAFluids;
import flora.core.item.ItemArmorFLORA;
import flora.core.logic.ArmorEffectsManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityInfuser extends BaseContainerBlockEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);

    public BlockEntityInfuser(BlockPos pos, BlockState blockState) {
        super(FLORABlockEntities.INFUSER.get(), pos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("Infuser");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new MenuInfuser(containerId, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BlockEntityInfuser blockEntity) {
        ItemStack bucketStack = blockEntity.getItem(4);
        if (!bucketStack.isEmpty()) {
            FluidStack fluidStack = getFluidFromItem(bucketStack);
            if (fluidStack != null && !fluidStack.isEmpty()) {
                if (blockEntity.fillArmorWithFluid(fluidStack, true)) {
                    if (bucketStack.getItem() instanceof BucketItem) {
                        blockEntity.setItem(4, new ItemStack(Items.BUCKET));
                    }
                    blockEntity.setChanged();
                }
            }
        }
    }

    public static FluidStack getFluidFromItem(ItemStack stack) {
        if (stack.isEmpty()) return null;

        if (stack.getItem() instanceof BucketItem bucketItem) {
            Fluid fluid = bucketItem.content;
            if (fluid != null && ArmorEffectsManager.getFluidMap().containsKey(fluid)) {
                return new FluidStack(fluid, 1000);
            }
        }

        return null;
    }

    public boolean fillArmorWithFluid(FluidStack fluid, boolean doFill) {
        if (fluid == null || fluid.isEmpty()) return false;

        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = getItem(i);
            if (!armorStack.isEmpty() && armorStack.getItem() instanceof ItemArmorFLORA armorItem) {
                int space = armorItem.getFluidCapacity() - armorItem.getTotalFluidAmount(armorStack);
                if (space > 0) {
                    List<ItemArmorFLORA.FluidTankData> tanks = armorItem.getFluidTanks(armorStack);

                    // Try to fill existing tank with same fluid
                    for (ItemArmorFLORA.FluidTankData tank : tanks) {
                        if (tank.fluid.getFluid() == fluid.getFluid()) {
                            int drain = Math.min(space, fluid.getAmount());
                            tank.fluid.grow(drain);
                            fluid.shrink(drain);
                            space -= drain;

                            if (doFill) {
                                armorItem.setFluidTanks(armorStack, tanks);
                            }

                            if (fluid.isEmpty()) {
                                return true;
                            }
                        }
                    }

                    // Create new tank if there's still space
                    if (space > 0) {
                        int amount = Math.min(space, fluid.getAmount());
                        tanks.add(new ItemArmorFLORA.FluidTankData(
                            new FluidStack(fluid.getFluid(), amount),
                            armorItem.getFluidCapacity()
                        ));
                        fluid.shrink(amount);

                        if (doFill) {
                            armorItem.setFluidTanks(armorStack, tanks);
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    public List<FluidStack> getTotalFluidTanks() {
        List<FluidStack> fluids = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = getItem(i);
            if (!armorStack.isEmpty() && armorStack.getItem() instanceof ItemArmorFLORA armorItem) {
                for (ItemArmorFLORA.FluidTankData tank : armorItem.getFluidTanks(armorStack)) {
                    if (!tank.fluid.isEmpty()) {
                        fluids.add(tank.fluid);
                    }
                }
            }
        }
        return fluids;
    }

    public int getTotalFluidAmount() {
        int total = 0;
        for (int i = 0; i < 4; i++) {
            ItemStack armorStack = getItem(i);
            if (!armorStack.isEmpty() && armorStack.getItem() instanceof ItemArmorFLORA armorItem) {
                total += armorItem.getTotalFluidAmount(armorStack);
            }
        }
        return total;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
    }
}
