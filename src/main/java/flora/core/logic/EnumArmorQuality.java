package flora.core.logic;

public enum EnumArmorQuality {
    LEADSTONE("Leadstone", 2, 250),
    HARDENED("Hardened", 3, 2500),
    REDSTONE("Redstone", 4, 25000),
    RESONANT("Resonant", 5, 250000);

    public final String name;
    public final int protection;
    public final int storage;

    EnumArmorQuality(String name, int protection, int storage) {
        this.name = name;
        this.protection = protection;
        this.storage = storage;
    }
}
