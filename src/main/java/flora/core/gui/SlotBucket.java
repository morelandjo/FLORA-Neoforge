package flora.core.gui;

import flora.core.block.BlockEntityInfuser;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotBucket extends Slot {

    public SlotBucket(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return BlockEntityInfuser.getFluidFromItem(stack) != null;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
