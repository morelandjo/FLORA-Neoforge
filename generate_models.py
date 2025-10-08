import json
import os

base_path = "src/main/resources/assets/flora"

# Create fluid blockstates (they just reference water for now since fluids render dynamically)
fluids = ["coal", "pyrotheum", "cryotheum", "mana", "ender", "redstone", "glowstone"]

for fluid in fluids:
    blockstate = {"variants": {}}
    for i in range(16):
        blockstate["variants"][f"level={i}"] = {"model": "minecraft:block/water"}

    with open(f"{base_path}/blockstates/{fluid}.json", "w") as f:
        json.dump(blockstate, f, indent=2)

# Infuser blockstate
infuser_blockstate = {
    "variants": {
        "": {"model": "flora:block/infuser"}
    }
}
with open(f"{base_path}/blockstates/infuser.json", "w") as f:
    json.dump(infuser_blockstate, f, indent=2)

# Infuser block model
infuser_block = {
    "parent": "minecraft:block/cube",
    "textures": {
        "down": "flora:block/machine_bottom",
        "up": "flora:block/machine_top",
        "north": "flora:block/machine_face",
        "south": "flora:block/machine_side",
        "east": "flora:block/machine_side",
        "west": "flora:block/machine_side",
        "particle": "flora:block/machine_side"
    }
}
with open(f"{base_path}/models/block/infuser.json", "w") as f:
    json.dump(infuser_block, f, indent=2)

# Infuser item model
infuser_item = {
    "parent": "flora:block/infuser"
}
with open(f"{base_path}/models/item/infuser.json", "w") as f:
    json.dump(infuser_item, f, indent=2)

# Bucket item models - use NeoForge fluid container loader
for fluid in fluids:
    bucket_model = {
        "parent": "neoforge:item/bucket_drip",
        "loader": "neoforge:fluid_container",
        "fluid": f"flora:{fluid}"
    }
    with open(f"{base_path}/models/item/{fluid}_bucket.json", "w") as f:
        json.dump(bucket_model, f, indent=2)

# Armor item models
qualities = ["leadstone", "hardened", "redstone", "resonant"]
types = [
    ("helmet", "helm"),
    ("chestplate", "chestplate"),
    ("leggings", "leggings"),
    ("boots", "boots")
]

for quality in qualities:
    for type_name, texture_name in types:
        armor_model = {
            "parent": "minecraft:item/generated",
            "textures": {
                "layer0": f"flora:item/armor{quality}{texture_name}"
            }
        }
        with open(f"{base_path}/models/item/{quality}_{type_name}.json", "w") as f:
            json.dump(armor_model, f, indent=2)

print(f"Created {len(fluids)} fluid blockstates")
print(f"Created 1 infuser blockstate")
print(f"Created 1 infuser block model")
print(f"Created {len(fluids)} bucket models")
print(f"Created {len(qualities) * len(types)} armor models")
print("All models generated successfully!")
