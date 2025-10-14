# FLORA - Fluids and Liquids on Ridiculous Armor

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
- **Leadstone** - 250 mB capacity
- **Hardened** - 2,500 mB capacity
- **Redstone** - 25,000 mB capacity
- **Resonant** - 250,000 mB capacity

Each armor piece can hold fluids in internal tanks. The amount and combination of fluids determines the effects you receive.

### Infuser Block
Use the Infuser to fill your armor pieces with fluids. Place armor and fluid buckets in the Infuser to transfer fluids.

## Fluid Effects

The effects you receive are based on the **combination** and **amount** of fluids across all your armor pieces. Effects scale with the amount of fluid - more fluid = stronger effects!

### Single Fluid Effects (Same fluid across multiple armor pieces)

- **Glowstone**: Night Vision
- **Redstone**: Fluctuating max health (randomizes every 30 seconds)
- **Mana**: Chance to cure all potion effects
- **Cryotheum**: Chance to restore air supply when drowning
- **Pyrotheum**: Increased fall damage

### Combination Effects (Mixing different fluids)

#### Beneficial Effects
- **Pyrotheum + Mana**: Movement Speed boost
- **Ender + Mana**: Jump Boost
- **Glowstone + Mana**: Chance to restore hunger

#### Attack Effects (When you attack enemies)
- **Coal + Glowstone**: Set enemies on fire
- **Coal + Pyrotheum**: Explode burning enemies

#### Pulse Projectiles (Automatically fire at nearby enemies)
- **Redstone + Mana**: Mana pulse (regeneration + minor damage)
- **Redstone + Coal**: Coal pulse (sets enemies on fire)
- **Redstone + Pyrotheum**: Pyrotheum pulse (damage)
- **Redstone + Cryotheum**: Slow pulse (slows enemies)
- **Redstone + Ender**: Ender pulse (teleportation effect)

#### Defensive Effects (When you take damage)
- **Cryotheum + Ender**: Teleport to safety when health drops below 4 hearts (cancels damage!)
- **Coal + Ender**: Teleport mob attacker away
- **Pyrotheum + Glowstone**: Damage reduction from fire sources (with cooldown)
- **Ender + Pyrotheum**: Reduced fall damage

#### Utility Effects
- **Coal + Mana**: Confuse nearby mobs (makes them attack each other)

#### Negative Effects
- **Redstone + Glowstone**: Rare chance to cause massive explosion
- **Pyrotheum + Cryotheum**: Movement Slowness
- **Coal + Cryotheum**: Mining Fatigue (Dig Slowdown)
- **Cryotheum + Mana**: Hunger loss over time
- **Cryotheum + Glowstone**: Take damage in cold biomes


## Unimplemented Effects (Future TODO)

These effects were listed in the original mod's tooltips but were never actually implemented by the original author. They remain as potential features for future development:


- **Coal + Coal**: "Shoot Fireballs"
- **Coal + Cryotheum**: "Mining Speed" - Tooltip claimed mining speed boost, but actual effect is Mining Fatigue (slowness)
- **Pyrotheum + Cryotheum**: "Underwater Breath" - Was listed but actual effect is slowness
- **Mana + Pyrotheum**: "Underwater Breath" - Was listed but actual effect is speed boost
- **Ender + Ender**: "Blink" 
- **Ender + Glowstone**: "Fall through ground when sneaking"


---

**Note:** This mod depended on two mods originally, Thermal Expansion and Redstone Arsenal, which have not been ported to 1.21.1 Neoforge yet. This means playing with this mod in survival is possible, but you will need to make a datapack (or Kube JS) with your own custom recipes or acquisition methods for the fluids. There are no ways to get any of the things in this mod in a normal survival mode without doing that.



### For Players
FLORA uses **fluid tags** to detect fluids in armor. If another mod adds fluids with similar names (coal, pyrotheum, cryotheum, mana, ender, redstone, or glowstone), they will automatically work with FLORA's armor system if the mod developer adds them to the appropriate tags.

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

