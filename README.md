<div align="center">

<h1>SpawnerTexture</h1>
<hr>

**Client-side Fabric mod that shows the mob texture on spawner icons in inventories**

![Minecraft](https://img.shields.io/badge/MINECRAFT-1.21.4-62B47A?style=flat-square&logo=minecraft&logoColor=white)
![Fabric](https://img.shields.io/badge/FABRIC_LOADER-0.16.10-DBD0B4?style=flat-square)
![Java](https://img.shields.io/badge/JAVA-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Release](https://img.shields.io/github/v/release/hhhhhffd/spawnertexutre?style=flat-square)
![License](https://img.shields.io/github/license/hhhhhffd/spawnertexutre?style=flat-square)

[Download](https://github.com/hhhhhffd/spawnertexutre/releases/tag/Release) · [Build](#build) · [License](#license)

</div>

> [!NOTE]
> Archived — no longer maintained.

## What it is

SpawnerTexture replaces the inventory icon of a spawner with a 16x16 texture of the mob it is configured for, so spawners can be told apart at a glance. A Mixin intercepts slot rendering in `HandledScreen.drawSlot`; if the item is a spawner with a known mob, the mob's PNG is drawn instead of the default icon.

## Textures

Mob textures are PNG files at:

```text
src/main/resources/assets/spawnertexture/textures/mob/<mob_id>.png
```

For example `zombie.png`, `skeleton.png`, `creeper.png`. The supported mob IDs are listed in `KNOWN_MOBS` in `SpawnerTextureManager.java`. Textures can also be supplied by a separate resource pack and are picked up on load and on pack change. The source tree does not contain mob PNGs.

## Stack

| Part | Version |
| --- | --- |
| Minecraft | 1.21.4 |
| Fabric Loader | >= 0.16.0 (built with 0.16.10) |
| Fabric API | 0.119.2+1.21.4 |
| Java | 21 |
| Mod version | 1.0.0 |

## Install

Download `spawnertexture-1.0.0.jar` from the [release](https://github.com/hhhhhffd/spawnertexutre/releases/tag/Release) and put it into `mods/` together with Fabric API.

## Build

```bash
./gradlew build
```

The JAR is written to `build/libs/`.

## License

MIT, see [LICENSE](LICENSE).
