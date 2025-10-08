package flora.core.init;

import flora.core.ConstantsFLORA;
import flora.core.item.ItemArmorFLORA;
import flora.core.logic.EnumArmorQuality;
import flora.core.logic.EnumArmorType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FLORAItems {

    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(BuiltInRegistries.ITEM, ConstantsFLORA.MOD_ID);

    // Fluid Buckets
    public static final DeferredHolder<Item, BucketItem> COAL_BUCKET = ITEMS.register("coal_bucket",
        () -> new BucketItem(FLORAFluids.COAL_STILL.get(),
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Item, BucketItem> PYROTHEUM_BUCKET = ITEMS.register("pyrotheum_bucket",
        () -> new BucketItem(FLORAFluids.PYROTHEUM_STILL.get(),
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Item, BucketItem> CRYOTHEUM_BUCKET = ITEMS.register("cryotheum_bucket",
        () -> new BucketItem(FLORAFluids.CRYOTHEUM_STILL.get(),
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Item, BucketItem> MANA_BUCKET = ITEMS.register("mana_bucket",
        () -> new BucketItem(FLORAFluids.MANA_STILL.get(),
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Item, BucketItem> ENDER_BUCKET = ITEMS.register("ender_bucket",
        () -> new BucketItem(FLORAFluids.ENDER_STILL.get(),
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Item, BucketItem> REDSTONE_BUCKET = ITEMS.register("redstone_bucket",
        () -> new BucketItem(FLORAFluids.REDSTONE_STILL.get(),
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Item, BucketItem> GLOWSTONE_BUCKET = ITEMS.register("glowstone_bucket",
        () -> new BucketItem(FLORAFluids.GLOWSTONE_STILL.get(),
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    // Infuser Block Item
    public static final DeferredHolder<Item, BlockItem> INFUSER = ITEMS.register("infuser",
        () -> new BlockItem(FLORABlocks.INFUSER.get(), new Item.Properties()));

    // Armor Items - 16 total (4 types x 4 qualities)
    // Leadstone armor (quality 0)
    public static final DeferredHolder<Item, ItemArmorFLORA> LEADSTONE_HELMET = ITEMS.register("leadstone_helmet",
        () -> new ItemArmorFLORA(EnumArmorType.HELMET, EnumArmorQuality.LEADSTONE));
    public static final DeferredHolder<Item, ItemArmorFLORA> LEADSTONE_CHESTPLATE = ITEMS.register("leadstone_chestplate",
        () -> new ItemArmorFLORA(EnumArmorType.CHESTPLATE, EnumArmorQuality.LEADSTONE));
    public static final DeferredHolder<Item, ItemArmorFLORA> LEADSTONE_LEGGINGS = ITEMS.register("leadstone_leggings",
        () -> new ItemArmorFLORA(EnumArmorType.LEGGINGS, EnumArmorQuality.LEADSTONE));
    public static final DeferredHolder<Item, ItemArmorFLORA> LEADSTONE_BOOTS = ITEMS.register("leadstone_boots",
        () -> new ItemArmorFLORA(EnumArmorType.BOOTS, EnumArmorQuality.LEADSTONE));

    // Hardened armor (quality 1)
    public static final DeferredHolder<Item, ItemArmorFLORA> HARDENED_HELMET = ITEMS.register("hardened_helmet",
        () -> new ItemArmorFLORA(EnumArmorType.HELMET, EnumArmorQuality.HARDENED));
    public static final DeferredHolder<Item, ItemArmorFLORA> HARDENED_CHESTPLATE = ITEMS.register("hardened_chestplate",
        () -> new ItemArmorFLORA(EnumArmorType.CHESTPLATE, EnumArmorQuality.HARDENED));
    public static final DeferredHolder<Item, ItemArmorFLORA> HARDENED_LEGGINGS = ITEMS.register("hardened_leggings",
        () -> new ItemArmorFLORA(EnumArmorType.LEGGINGS, EnumArmorQuality.HARDENED));
    public static final DeferredHolder<Item, ItemArmorFLORA> HARDENED_BOOTS = ITEMS.register("hardened_boots",
        () -> new ItemArmorFLORA(EnumArmorType.BOOTS, EnumArmorQuality.HARDENED));

    // Redstone armor (quality 2)
    public static final DeferredHolder<Item, ItemArmorFLORA> REDSTONE_HELMET = ITEMS.register("redstone_helmet",
        () -> new ItemArmorFLORA(EnumArmorType.HELMET, EnumArmorQuality.REDSTONE));
    public static final DeferredHolder<Item, ItemArmorFLORA> REDSTONE_CHESTPLATE = ITEMS.register("redstone_chestplate",
        () -> new ItemArmorFLORA(EnumArmorType.CHESTPLATE, EnumArmorQuality.REDSTONE));
    public static final DeferredHolder<Item, ItemArmorFLORA> REDSTONE_LEGGINGS = ITEMS.register("redstone_leggings",
        () -> new ItemArmorFLORA(EnumArmorType.LEGGINGS, EnumArmorQuality.REDSTONE));
    public static final DeferredHolder<Item, ItemArmorFLORA> REDSTONE_BOOTS = ITEMS.register("redstone_boots",
        () -> new ItemArmorFLORA(EnumArmorType.BOOTS, EnumArmorQuality.REDSTONE));

    // Resonant armor (quality 3)
    public static final DeferredHolder<Item, ItemArmorFLORA> RESONANT_HELMET = ITEMS.register("resonant_helmet",
        () -> new ItemArmorFLORA(EnumArmorType.HELMET, EnumArmorQuality.RESONANT));
    public static final DeferredHolder<Item, ItemArmorFLORA> RESONANT_CHESTPLATE = ITEMS.register("resonant_chestplate",
        () -> new ItemArmorFLORA(EnumArmorType.CHESTPLATE, EnumArmorQuality.RESONANT));
    public static final DeferredHolder<Item, ItemArmorFLORA> RESONANT_LEGGINGS = ITEMS.register("resonant_leggings",
        () -> new ItemArmorFLORA(EnumArmorType.LEGGINGS, EnumArmorQuality.RESONANT));
    public static final DeferredHolder<Item, ItemArmorFLORA> RESONANT_BOOTS = ITEMS.register("resonant_boots",
        () -> new ItemArmorFLORA(EnumArmorType.BOOTS, EnumArmorQuality.RESONANT));
}
