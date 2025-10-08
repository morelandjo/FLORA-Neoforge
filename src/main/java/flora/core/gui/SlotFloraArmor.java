package flora.core.gui;

import flora.core.item.ItemArmorFLORA;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotFloraArmor extends Slot {

    private final int armorType;

    public SlotFloraArmor(Container container, int slot, int x, int y, int armorType) {
        super(container, slot, x, y);
        this.armorType = armorType;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.getItem() instanceof ItemArmorFLORA armorItem) {
            return armorItem.floraType.ordinal() == this.armorType;
        }
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
