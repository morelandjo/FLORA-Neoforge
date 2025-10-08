# FLORA NeoForge 1.21.1 Conversion Notes

## Conversion Summary

Successfully converted FLORA mod from Forge 1.10.2 to NeoForge 1.21.1!

### What Was Done

#### 1. **Core Mod Structure** ✅
- Created modern `@Mod` annotation-based main class
- Set up DeferredRegister system for all registries
- Updated gradle.properties with mod information

#### 2. **Replaced External Dependencies** ✅
Instead of relying on closed-source Thermal Foundation/Redstone Arsenal, we created our own:

**7 Custom Fluids** (replacing ThermalFoundation):
- Liquefacted Coal
- Blazing Pyrotheum
- Gelid Cryotheum
- Destabilized Mana
- Resonant Ender
- Destabilized Redstone
- Energized Glowstone

Each fluid has:
- Fluid type with properties (density, viscosity, temperature)
- Still and flowing variants
- Fluid blocks
- Bucket items

#### 3. **Armor System** ✅
- Converted ItemArmorFLORA to modern armor system
- Implemented fluid storage using modern DataComponents
- 4 armor qualities (Leadstone, Hardened, Redstone, Resonant)
- 4 armor types (Helmet, Chestplate, Leggings, Boots)
- Total: 16 armor pieces

#### 4. **Effects System** ✅
- Updated ArmorEffectsManager for modern NeoForge events
- Fluid interaction matrix calculates effects from fluid combinations
- All 49 possible fluid interactions implemented
- Effects include: night vision, health changes, teleportation, explosions, status effects, and more

#### 5. **Infuser Block** ✅
- Converted BlockInfuser to modern block system
- Converted TileInfuser to modern BlockEntity
- Allows filling armor with fluids from buckets
- Supports all 7 custom fluids

#### 6. **GUI System** ✅
- Created modern MenuInfuser (server-side)
- Created ScreenInfuser (client-side) with fluid rendering
- Custom slots for armor pieces and fluid buckets
- Displays fluid tanks and amounts

#### 7. **Entity Pulses** ✅
- Converted all 5 pulse entities to modern system:
  - Mana Pulse (regeneration)
  - Coal Pulse (damage)
  - Pyrotheum Pulse (fire damage)
  - Slow Pulse (slowness effect)
  - Ender Pulse (teleportation)

#### 8. **Resources** ✅
- mods.toml configuration
- Language file (en_us.json)
- pack.mcmeta
- Creative tab setup

### What Still Needs to Be Done

#### 1. **Textures** ⚠️
You need to add textures for:

**GUI:**
- `assets/flora/textures/gui/infuser.png` (176x256 PNG)
  - Main GUI: 176x166
  - Tank overlay at y=180: 112x10

**Fluids:**
- `assets/flora/textures/fluid/coal_still.png`
- `assets/flora/textures/fluid/coal_flowing.png`
- `assets/flora/textures/fluid/pyrotheum_still.png`
- `assets/flora/textures/fluid/pyrotheum_flowing.png`
- `assets/flora/textures/fluid/cryotheum_still.png`
- `assets/flora/textures/fluid/cryotheum_flowing.png`
- `assets/flora/textures/fluid/mana_still.png`
- `assets/flora/textures/fluid/mana_flowing.png`
- `assets/flora/textures/fluid/ender_still.png`
- `assets/flora/textures/fluid/ender_flowing.png`
- `assets/flora/textures/fluid/redstone_still.png`
- `assets/flora/textures/fluid/redstone_flowing.png`
- `assets/flora/textures/fluid/glowstone_still.png`
- `assets/flora/textures/fluid/glowstone_flowing.png`

**Armor Textures:**
- `assets/flora/textures/armor/leadstone.png` (layer 1)
- `assets/flora/textures/armor/leadstone_1.png` (layer 2 - leggings)
- `assets/flora/textures/armor/hardened.png`
- `assets/flora/textures/armor/hardened_1.png`
- `assets/flora/textures/armor/redstone.png`
- `assets/flora/textures/armor/redstone_1.png`
- `assets/flora/textures/armor/resonant.png`
- `assets/flora/textures/armor/resonant_1.png`

**Items/Blocks:**
- Item models for all buckets, armor pieces, and infuser
- Block model and blockstate for infuser

**Entity Rendering:**
- Textures for pulse entities (optional, can use default rendering)

#### 2. **Data Generation** ⚠️
Recommended to add DataGen for:
- Block states and models
- Item models
- Recipes (if you want craftable armor)
- Loot tables
- Tags

#### 3. **Crafting Recipes** 💭
The original mod had recipes using Redstone Arsenal and Thermal Expansion items (commented out).
You'll need to decide:
- Make armor craftable with vanilla/modded items
- Make it creative-only
- Add your own progression system

#### 4. **Optional Enhancements** 💭
- Add JEI/EMI integration for infuser
- Add Patchouli documentation
- Create custom particle effects for pulses
- Add sound effects
- Add fluid interactions with world (like Pyrotheum setting things on fire)

### Testing Checklist

- [ ] Build the mod successfully
- [ ] Load into game without crashes
- [ ] Armor pieces appear in creative tab
- [ ] Fluid buckets work correctly
- [ ] Infuser block can be placed
- [ ] Infuser GUI opens
- [ ] Armor can be placed in infuser
- [ ] Fluids can be added to armor
- [ ] Armor effects activate when wearing filled armor
- [ ] Pulse entities spawn and work correctly
- [ ] All 7 fluids are obtainable

### Key Differences from Original

1. **No Thermal Foundation dependency** - We created our own fluids
2. **No Redstone Arsenal dependency** - Recipes removed (need replacement)
3. **No crafting recipes** - Need to be recreated
4. **Modern NeoForge APIs** - Using DataComponents instead of NBT directly
5. **Simplified proxy system** - Using event bus subscriptions

### How to Build

```bash
cd "D:\MinecraftMods\Returns Mods\FLORA-Neoforge"
gradlew build
```

The mod jar will be in `build/libs/`

### Credits

- **Original Author:** Pixlepix
- **Textures:** Blorph
- **NeoForge Conversion:** Returns
- **Original Mod:** FLORA (Fluids + Liquids On Ridiculous Armor)
- **Version:** 2.0.0 for NeoForge 1.21.1
