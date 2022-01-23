# Bedrock Features [![Latest release](https://img.shields.io/github/v/release/Micalobia/Bedrock-Features?style=flat-square)](https://github.com/Micalobia/Bedrock-Features/releases/latest) [![License](https://img.shields.io/github/license/Micalobia/Bedrock-Features?style=flat-square)](https://github.com/Micalobia/Bedrock-Features/blob/latest/LICENSE) [![Curseforge](https://cf.way2muchnoise.eu/full_568238_downloads.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/bedrock-features) <br/>

Adds some parity between Bedrock Edition and Java Edition

## Currently implemented

- [Dye Cauldrons](https://minecraft.fandom.com/wiki/Cauldron#Applying_dye_to_cauldron_water)
- [Potion Cauldrons](https://minecraft.fandom.com/wiki/Cauldron#Filling_cauldrons_with_potions)
- [Stackable Cakes](https://minecraft.fandom.com/wiki/Cake)
- [Buffed Pufferfish](https://minecraft.fandom.com/wiki/Pufferfish_(item)#Food)
- [Buffed Sharpness](https://minecraft.fandom.com/wiki/Sharpness#Usage)
- [Dyeable Shulkers](https://minecraft.fandom.com/wiki/Shulker#Dyeing)
- [Magic Bypasses Protection](https://minecraft.fandom.com/wiki/Protection#cite_ref-1)
- [Sugarcane is Bonemealable](https://minecraft.fandom.com/wiki/Sugar_Cane#Farming)
- [Anvils are Pushable](https://minecraft.fandom.com/wiki/Anvil#Falling_anvils)
- [Jukeboxes emit Redstone while playing](https://minecraft.fandom.com/wiki/Jukebox#Redstone_component)
- [Jukeboxes can be used with a hopper](https://minecraft.fandom.com/wiki/Jukebox#Usage)
- [Charged Creepers can drop more than 1 mob head](https://minecraft.fandom.com/wiki/Creeper#Charged_creeper)
- [Dye and Potion cauldrons can be filled with dripstone and rain](https://bugs.mojang.com/browse/MCPE-134433)

## Config

Nearly everything is configurable, and the config can be found in `/config/bedrock_features.toml`.

- Magic Bypasses Protection is off by default because of [this](https://bugs.mojang.com/browse/MCPE-40651)
- Dyeable Shulkers are only dyeable in creative by default
- Jukeboxes interfacing with hoppers is not configurable
- Charged Creepers drop infinite heads by default, but you can change the number. I went with Bedrock default because
  of [this](https://bugs.mojang.com/browse/MC-63534) and [this](https://bugs.mojang.com/browse/MCPE-18599)
- Potion and Dye cauldrons filling with dripstone and rain is off by default because
  of [this](https://bugs.mojang.com/browse/MCPE-134433)

## The future

If you would like to see a feature added or you find a bug, please report
it [here](https://github.com/Micalobia/Bedrock-Features/issues) <br/>
Feel free to contribute, I am happy to let other people implement things as I don't always have time

Consider supporting me [here](https://www.buymeacoffee.com/Micalobia), I develop Minecraft mods and Discord bots