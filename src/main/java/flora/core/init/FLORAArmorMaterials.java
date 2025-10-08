package flora.core.init;

import flora.core.ConstantsFLORA;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class FLORAArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
        DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, ConstantsFLORA.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> LEADSTONE = ARMOR_MATERIALS.register("leadstone",
        () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.BOOTS, 2);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(
                ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "leadstone"),
                "",
                false
            )),
            0.0F,
            0.0F
        ));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HARDENED = ARMOR_MATERIALS.register("hardened",
        () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.BOOTS, 2);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(
                ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "hardened"),
                "",
                false
            )),
            0.0F,
            0.0F
        ));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> REDSTONE_ARMOR = ARMOR_MATERIALS.register("redstone",
        () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.BOOTS, 2);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.REDSTONE),
            List.of(new ArmorMaterial.Layer(
                ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "redstone"),
                "",
                false
            )),
            0.0F,
            0.0F
        ));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> RESONANT = ARMOR_MATERIALS.register("resonant",
        () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.CHESTPLATE, 8);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.BOOTS, 3);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.of(Items.DIAMOND),
            List.of(new ArmorMaterial.Layer(
                ResourceLocation.fromNamespaceAndPath(ConstantsFLORA.MOD_ID, "resonant"),
                "",
                false
            )),
            1.0F,
            0.0F
        ));
}
