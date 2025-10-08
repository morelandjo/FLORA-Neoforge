# Texture and Model Guide for FLORA

The mod is **fully functional** but missing visual assets. Here's what you need to add:

## ✅ What's Working
- Mod loads successfully
- All items registered
- All fluids registered
- Infuser block works
- Effects system active
- No crashes!

## ❌ What's Missing (Textures/Models)

### 1. Fluid Blockstates (Required for fluid blocks)
Create these files in `src/main/resources/assets/flora/blockstates/`:

**Pattern for each fluid** (coal.json, pyrotheum.json, cryotheum.json, mana.json, ender.json, redstone.json, glowstone.json):
```json
{
  "variants": {
    "level=0": { "model": "minecraft:block/water" },
    "level=1": { "model": "minecraft:block/water" },
    "level=2": { "model": "minecraft:block/water" },
    "level=3": { "model": "minecraft:block/water" },
    "level=4": { "model": "minecraft:block/water" },
    "level=5": { "model": "minecraft:block/water" },
    "level=6": { "model": "minecraft:block/water" },
    "level=7": { "model": "minecraft:block/water" },
    "level=8": { "model": "minecraft:block/water" },
    "level=9": { "model": "minecraft:block/water" },
    "level=10": { "model": "minecraft:block/water" },
    "level=11": { "model": "minecraft:block/water" },
    "level=12": { "model": "minecraft:block/water" },
    "level=13": { "model": "minecraft:block/water" },
    "level=14": { "model": "minecraft:block/water" },
    "level=15": { "model": "minecraft:block/water" }
  }
}
```

### 2. Infuser Blockstate
`src/main/resources/assets/flora/blockstates/infuser.json`:
```json
{
  "variants": {
    "": { "model": "flora:block/infuser" }
  }
}
```

### 3. Item Models
Create these in `src/main/resources/assets/flora/models/item/`:

**For buckets** (coal_bucket.json, pyrotheum_bucket.json, etc.):
```json
{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "minecraft:item/bucket"
  }
}
```

**For armor** (leadstone_helmet.json, etc.):
```json
{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "minecraft:item/iron_helmet"
  }
}
```

**For infuser** (infuser.json):
```json
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "minecraft:block/piston_side"
  }
}
```

### 4. Block Model
`src/main/resources/assets/flora/models/block/infuser.json`:
```json
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "minecraft:block/piston_side"
  }
}
```

## Quick Fix Script

I'll create a Python script to generate all the placeholder files:

```python
import os
import json

# Create directories
os.makedirs("src/main/resources/assets/flora/blockstates", exist_ok=True)
os.makedirs("src/main/resources/assets/flora/models/item", exist_ok=True)
os.makedirs("src/main/resources/assets/flora/models/block", exist_ok=True)

# Fluid blockstates
fluids = ["coal", "pyrotheum", "cryotheum", "mana", "ender", "redstone", "glowstone"]
for fluid in fluids:
    blockstate = {
        "variants": {
            f"level={i}": {"model": "minecraft:block/water"} for i in range(16)
        }
    }
    with open(f"src/main/resources/assets/flora/blockstates/{fluid}.json", "w") as f:
        json.dump(blockstate, f, indent=2)

# Infuser blockstate
with open("src/main/resources/assets/flora/blockstates/infuser.json", "w") as f:
    json.dump({"variants": {"": {"model": "flora:block/infuser"}}}, f, indent=2)

# Bucket item models
for fluid in fluids:
    model = {
        "parent": "minecraft:item/generated",
        "textures": {"layer0": "minecraft:item/bucket"}
    }
    with open(f"src/main/resources/assets/flora/models/item/{fluid}_bucket.json", "w") as f:
        json.dump(model, f, indent=2)

# Armor item models
qualities = ["leadstone", "hardened", "redstone", "resonant"]
types = ["helmet", "chestplate", "leggings", "boots"]
armor_icons = ["iron_helmet", "iron_chestplate", "iron_leggings", "iron_boots"]

for quality in qualities:
    for i, armor_type in enumerate(types):
        model = {
            "parent": "minecraft:item/generated",
            "textures": {"layer0": f"minecraft:item/{armor_icons[i]}"}
        }
        with open(f"src/main/resources/assets/flora/models/item/{quality}_{armor_type}.json", "w") as f:
            json.dump(model, f, indent=2)

# Infuser models
infuser_model = {
    "parent": "minecraft:block/cube_all",
    "textures": {"all": "minecraft:block/piston_side"}
}
with open("src/main/resources/assets/flora/models/block/infuser.json", "w") as f:
    json.dump(infuser_model, f, indent=2)

with open("src/main/resources/assets/flora/models/item/infuser.json", "w") as f:
    json.dump({"parent": "flora:block/infuser"}, f, indent=2)

print("All placeholder models and blockstates created!")
```

Save this as `generate_placeholders.py` in your mod root and run: `python generate_placeholders.py`

## Next Steps

1. Run the placeholder generator script
2. Rebuild: `./gradlew build`
3. Test in game - everything should render (using vanilla textures)
4. Replace placeholder textures with proper ones when available

## Custom Textures (Future)

When you're ready to add proper textures:
- Bucket textures: 16x16 PNG with fluid in bucket
- Armor textures: Follow Minecraft armor texture format
- Fluid textures: Animated 16x16 or 16x512 (for animation frames)
- Infuser: 16x16 block texture
- GUI: 176x256 for the infuser interface
