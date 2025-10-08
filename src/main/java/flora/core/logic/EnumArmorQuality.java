package flora.core.logic;

public enum EnumArmorQuality {
    LEADSTONE("Leadstone", 2, 4000),
    HARDENED("Hardened", 3, 8000),
    REDSTONE("Redstone", 4, 16000),
    RESONANT("Resonant", 5, 32000);

    public final String name;
    public final int protection;
    public final int storage;

    EnumArmorQuality(String name, int protection, int storage) {
        this.name = name;
        this.protection = protection;
        this.storage = storage;
    }
}
