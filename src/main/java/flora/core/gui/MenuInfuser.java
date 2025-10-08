package flora.core.gui;

import flora.core.block.BlockEntityInfuser;
import flora.core.init.FLORAMenuTypes;
import flora.core.item.ItemArmorFLORA;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MenuInfuser extends AbstractContainerMenu {

    private final Container container;

    public MenuInfuser(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(5));
    }

    public MenuInfuser(int containerId, Inventory playerInventory, Container container) {
        super(FLORAMenuTypes.INFUSER.get(), containerId);
        this.container = container;
        container.startOpen(playerInventory.player);

        // Armor slots (0-3)
        this.addSlot(new SlotFloraArmor(container, 0, 64, 61, 0));
        this.addSlot(new SlotFloraArmor(container, 1, 84, 61, 1));
        this.addSlot(new SlotFloraArmor(container, 2, 104, 61, 2));
        this.addSlot(new SlotFloraArmor(container, 3, 124, 61, 3));

        // Bucket slot (4)
        this.addSlot(new SlotBucket(container, 4, 11, 26));

        // Player inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Player hotbar
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.copy();

            if (index < 5) {
                // Move from container to player inventory
                if (!this.moveItemStackTo(slotStack, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Move from player inventory to container
                if (BlockEntityInfuser.getFluidFromItem(slotStack) != null) {
                    // Try to move bucket to bucket slot
                    if (!this.moveItemStackTo(slotStack, 4, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotStack.getItem() instanceof ItemArmorFLORA armorItem) {
                    // Try to move armor to correct slot
                    int armorSlot = armorItem.floraType.ordinal();
                    if (!this.moveItemStackTo(slotStack, armorSlot, armorSlot + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public Container getContainer() {
        return container;
    }
}
