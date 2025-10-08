package flora.core.logic;

import net.minecraft.world.item.ArmorItem;

public enum EnumArmorType {
    HELMET("Helmet", ArmorItem.Type.HELMET),
    CHESTPLATE("Chestplate", ArmorItem.Type.CHESTPLATE),
    LEGGINGS("Leggings", ArmorItem.Type.LEGGINGS),
    BOOTS("Boots", ArmorItem.Type.BOOTS);

    public final String name;
    public final ArmorItem.Type armorType;

    EnumArmorType(String name, ArmorItem.Type armorType) {
        this.name = name;
        this.armorType = armorType;
    }
}
