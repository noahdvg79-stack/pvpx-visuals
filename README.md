# PvPX Visuals

A lightweight client-side PvP visuals mod for Minecraft Java 1.21.11 on Fabric.

## Features
- Right Shift opens the visual settings menu.
- Custom crosshair with size, gap, thickness, and color controls.
- Damage flash / hurt vignette.
- Fullbright toggle.
- Camera bobbing toggle.
- FOV effects toggle.
- Particle visibility toggle.
- Minimal PvP watermark.
- Settings are saved locally in `config/pvpx-visuals.properties`.

## Dependencies
Required:
- Minecraft Java 1.21.11
- Fabric Loader 0.18.1+
- Fabric API 0.141.3+1.21.11

No Cloth Config, Mod Menu, Sodium, or other client mod is required.

Java:
- JDK 21

Build:
- Windows: `gradlew.bat build`
- macOS/Linux: `./gradlew build`

The finished jar will be in `build/libs/`.
