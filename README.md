# FLORA - Fluids and Liquids on Ridiculous Armor

**Version:** 2.0.0 for NeoForge 1.21.1
**Original Author:** Pixlepix
**Textures:** Blorph
**NeoForge Conversion:** Vodmordia

## Overview

FLORA is a unique armor mod that allows players to infuse armor with various fluids, granting special abilities based on fluid combinations. This version has been completely rebuilt for modern NeoForge, replacing the original Thermal Foundation dependency with custom fluids.

## Features

### Custom Fluids
FLORA adds 7 fluids:
- **Coal**
- **Pyrotheum**
- **Cryotheum**
- **Mana**
- **Ender**
- **Redstone**
- **Glowstone**

### Armor System
Four tiers of armor (each with helmet, chestplate, leggings, boots):
- **Leadstone** 
- **Hardened** 
- **Redstone**
- **Resonant**

Each armor piece can hold fluids in internal tanks. The amount and combination of fluids determines the effects you receive.

### Infuser Block
Use the Infuser to fill your armor pieces with fluids. Place armor and fluid buckets in the Infuser to transfer fluids.

## Fluid Effects

### Single Fluid Effects (Same fluid in multiple pieces)

- **Glowstone + Glowstone**: Night Vision
- **Redstone + Redstone**: Fluctuating max health (changes every 30 seconds)
- **Mana + Mana**: Chance to cure all potion effects
- **Cryotheum + Cryotheum**: Chance to restore air supply

### Combination Effects (Different fluids mixed)

#### Beneficial Effects
- **Glowstone + Mana**: Chance to restore hunger
- **Pyrotheum + Mana**: Movement speed boost
- **Ender + Mana**: Jump boost
- **Cryotheum + Ender**: Teleport to safety when health is low

#### Attack Effects (When wearing armor)
- **Glowstone + Coal**: Set enemies on fire when attacking
- **Coal + Pyrotheum**: Explode burning enemies when attacking
- **Coal + Mana**: Confuse nearby mobs (they attack each other)

#### Pulse Projectiles (Fire projectiles at nearby enemies)
- **Redstone + Mana**: Mana pulse
- **Redstone + Coal**: Coal pulse (sets fire)
- **Redstone + Pyrotheum**: Pyrotheum pulse (damage)
- **Redstone + Cryotheum**: Slow pulse (slows enemies)
- **Redstone + Ender**: Ender pulse

#### Defensive Effects
- **Pyrotheum + Pyrotheum**: Fire resistance
- **Coal + Coal**: Chance to drop coal when hurt
- **Ender + Ender**: Chance to teleport attacker away
- **Pyrotheum + Glowstone**: Damage reduction from fire
- **Pyrotheum + Ender**: Chance to extinguish self when on fire
- **Coal + Ender**: No fall damage

#### Negative Effects
- **Redstone + Glowstone**: Chance to explode
- **Pyrotheum + Cryotheum**: Movement slowness
- **Coal + Cryotheum**: Mining fatigue
- **Cryotheum + Mana**: Hunger loss
- **Glowstone + Cryotheum**: Take damage in cold biomes

## Getting Started

**Note:** Currently all items are available in Creative mode only. Survival crafting recipes are not yet implemented.

1. Get armor pieces from the FLORA creative tab
2. Get fluid buckets from the creative tab
3. Place an Infuser block
4. Put armor and fluid buckets in the Infuser
5. Wait for the fluid to transfer

## Tips

- More fluid = stronger effects (effects scale with total fluid amount)
- Mix different fluids across armor pieces for combination effects
## Mod Compatibility

FLORA uses **fluid tags** to detect fluids in armor, which means it can work with fluids from other mods!

### For Players
If another mod adds fluids with similar names (coal, pyrotheum, cryotheum, mana, ender, redstone, or glowstone), they will automatically work with FLORA's armor system if the mod developer adds them to the appropriate tags.

### For Mod Developers
To make your fluids compatible with FLORA, add them to the appropriate fluid tags in your mod's data files:

```json
{
  "replace": false,
  "values": [
    "yourmod:your_fluid",
    "yourmod:flowing_your_fluid"
  ]
}
```

Available FLORA fluid tags:
- `flora:coal` - Dark energy fluids
- `flora:pyrotheum` - Fire/blazing fluids
- `flora:cryotheum` - Ice/freezing fluids
- `flora:mana` - Magical essence fluids
- `flora:ender` - Teleportation fluids
- `flora:redstone` - Energy/flux fluids
- `flora:glowstone` - Light/illumination fluids

Example: If your mod adds a "liquid fire" fluid, you could add it to `flora:pyrotheum` tag, and it would grant fire-related effects when used in FLORA armor.

## Credits

- **Original Mod:** FLORA by Pixlepix
- **Textures:** Blorph (from original mod)
- **Conversion:** Vodmordia

## License

MIT
