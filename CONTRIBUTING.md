# Factions And Curiosities

Welcome to the Contribution Guide for Factions And Curiosities! 
<br>Thank you for your interest in improving the project — whether you're fixing bugs, adding features, or helping with documentation. Every contribution helps! 

## Requirements
- [Java Development Kit](https://www.oracle.com/ae/java/technologies/downloads/) with Java 17 or higher.
- Recommended is an IDE to make coding more enjoyable (I use [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/?section=windows)).
- The remaining configs and data are included in the Git.

## Setup
1. Fork the repository on GitHub and clone it to your local machine.
2. Open the project in your preferred IDE.
3. Let Gradle finish importing and setting up dependencies (this may take 10+ minutes).
4. Use the `runClient` task via Gradle to start the modded Minecraft client. <br>At this point, no changes should be necessary — the main branch should always contain a working version of the mod.
5. Make your changes!
6. Commit your work to your forked repository.
7. Open a **Pull Request** to the main Factions And Curiosities repository.
8. Your changes will be reviewed and, if accepted, included in a future update. :D

## Project Structure
### Code
Located in: `src/main/java/de/joh/fnc`
- **`FactionsAndCuriosities.java`**: The main entry point of the mod.
- **Packages**:
  - `client`: Client-only features like GUIs.
  - `common`: Shared code (items, handlers, etc.).
  - `networking`: Handles communication between client and server using packets.
  - `api`: Exposed API allowing other mods to hook into DM&R features.
  - `compat`: Code that may only be loaded if a certain other mod is loaded. This allows for optional mod integrations.
  - `datagen`: Data generators that create the files in `src/generated/resources` (see "Generated data").
  - `mixins`: Injected modifications to base Minecraft or Mana and Artifice code.<br>**Use with caution** – can easily introduce bugs or incompatibilities.

Subpackages in `common` group game areas. Content is initialized in the corresponding `init` classes.  
Events are handled in the `event` package, and reusable helper functions are located in `util`.

### Data
Hand-made data is located in: `src/main/resources`
- **`assets`**: Client-specific content (textures, translations, guidebook)
- **`data`**: Server-synced definitions (rituals, crafting and enchanting recipes, damage types)
- **`fnc.mixins.json`**: Mixin configuration

### Generated data
Located in: `src/generated/resources`

Item models, tags (including the Curios slots) and the manaweaving, Eldrin Altar, spell component and shape recipes are created by the data generators in `src/main/java/de/joh/fnc/datagen`.
- Never edit these files by hand. Change the matching provider instead, run the `runData` Gradle task and commit the regenerated files together with your code.
- A file must never exist in both `src/main/resources` and `src/generated/resources`, the build fails in that case.
- New items get an item model with their texture `textures/item/<item_id>.png` automatically. Curios are registered with `ItemInit.registerCurio(...)`, which also assigns their Curios slot.
- `runData` checks the data while generating it (e.g. missing textures, unknown items in recipes, curios without a slot) and fails with a list of the problems.

### Build configuration
- **`gradle.properties`**: All versions (Minecraft, NeoForge, dependencies) and mod metadata. Change versions only here.
- **`build.gradle`**: Build logic based on [ModDevGradle Legacy](https://github.com/neoforged/ModDevGradle/blob/main/LEGACY.md).
- **`src/main/templates`**: `META-INF/mods.toml` and `pack.mcmeta`. Their placeholders are filled from `gradle.properties` during the build.
- **`libs`**: Local mod libraries that are not available from a Maven repository (Tomes And Libs, Dragon Magic and Relics).