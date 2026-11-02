# CLAUDE.md

Guidance for Claude Code when working in this repository.

## Git rules (mandatory)

- **Claude must not commit or push changes.** No `git commit`, no `git push`, no `git merge`/`rebase` onto remote branches, no creating tags or pull requests – not even when asked as part of another task.
- Claude only edits files in the working tree. Committing and pushing is done by the maintainer (Joh0210) personally.
- Read-only git commands (`git status`, `git diff`, `git log`, `git show`, `git blame`) are allowed.

## Language rule (mandatory)

- **All text created within this repository must be written exclusively in English.** This includes code, identifiers, comments, Javadoc, log messages, config comments, documentation, Markdown files, JSON content and Codex/guide entries.
- **Only exception: translations.** Localized files for other languages (e.g. `assets/fnc/lang/zh_cn.json`, `assets/fnc/guide/base/zh_cn.json`) are of course written in their respective language.
- This rule applies regardless of the language used in the conversation with Claude.

## Project context

**Factions And Curiosities** (mod ID `fnc`, package `de.joh.fnc`) is a Minecraft mod and an add-on for **Mana and Artifice (M&A)**. It adds new factions that change the way spells are used, together with matching gear and curios, spell components and shapes, rituals, effects and an enchantment, and extends the late and after game of M&A. Its main target audience is modpacks.

- Core concept **factions**: every faction (`common/faction`, registered in `FactionInit`) has its own casting resource (`common/faction/castingresource`, IDs in `ResourceIDs`), whose resource bar texture (`assets/fnc/gui/resource_bars.png`) is swapped into the M&A HUD by `CastingResourceMixin`.
  - **Wild Courts** (`WildFaction`): their spells can trigger a random **Wild Magic** (`api/wildmagic`, custom registry `fnc:wildmagic`); the Wild Magic Luck of the caster tips the roll.
  - **Paladins** (`PaladinFaction`, two casting resources: shield and sword): their spell components are **Smites** (`api/smite`, `SmiteComponent`, `SmiteMobEffect`), which apply their effect on the next direct melee attack.
  - **Blood Mages** (`BloodMageFaction`): blood as casting resource, regained by sacrificing themselves or others (`BloodMagicHelper`).
- **Spell Adjustments** (`api/spelladjustment`, custom registry `fnc:spelladjustment`) change a spell while it is cast (e.g. cancel, delay, empower, maximize, minimize).
- In-game documentation is provided via the M&A Codex/guidebook (`assets/fnc/guide/base/*.json`, registered in `FactionsAndCuriosities.onRegisterGuidebooks`); complete content only exists in `en_us`.

### Tech stack
| Component | Version |
|---|---|
| Minecraft | 1.20.1 |
| Loader | NeoForge 47.1.65 (the 1.20.1 NeoForge fork: mod ID `forge`, Java packages `net.minecraftforge`) |
| Build | Gradle 8.14.6 (wrapper), ModDevGradle Legacy `net.neoforged.moddev.legacyforge` |
| Java | 17 (toolchain, provisioned automatically); Gradle itself runs on Java 17–24 |
| Mappings | Official Mojang mappings + Parchment `2023.09.03` (1.20.1) |
| Required dependencies | Mana and Artifice (`mna`), Tomes And Libs (`tnl`), Curios, GeckoLib 4.4.4 |
| Optional | Dragon Magic and Relics (`dmnr`, code integration in `de.joh.fnc.compat.dmnr`), MagiChem (recipes only, generated: distillation and sublimation) |
| Development runs only | Caelus (required by Dragon Magic and Relics), JEI |

### Build configuration

- `gradle.properties` is the **single source of truth** for all versions and mod metadata. `*_version` is the artifact used in development, `*_version_range` the range written into `mods.toml` (`mna_version`, `magichem_version`, `jei_version` are CurseMaven file IDs).
- `src/main/templates/META-INF/mods.toml` and `src/main/templates/pack.mcmeta` are templates. The `generateModMetadata` task in `build.gradle` expands their `${...}` placeholders; every placeholder must be listed in its `replaceProperties` map. Never put `META-INF/mods.toml` or `pack.mcmeta` into `src/main/resources`.
- Mod dependencies are declared with the remapping configurations `modImplementation`, `modCompileOnly`, `modRuntimeOnly` and `modLocalRuntime` (development runs only). `magichemData` only resolves the MagiChem jar as a file for `runData` (see "MagiChem recipes"). They are **not transitive**; every required mod has to be listed explicitly. Every repository has a `content { includeGroup ... }` filter.
- A new required mod needs: version + range in `gradle.properties`, a dependency in `build.gradle`, an entry in the `replaceProperties` map and a `[[dependencies.${mod_id}]]` block in the `mods.toml` template. An optional mod needs the same, with `mandatory=false` in its `mods.toml` block.
- Mixins are set up: the config `src/main/resources/fnc.mixins.json` (package `de.joh.fnc.mixins`, refmap `fnc.refmap.json`), the Mixin annotation processor, the `mixin { }` block in `build.gradle` and the `MixinConfigs` manifest attribute (Forge 1.20.1 loads Mixin configs only from it, not from `mods.toml`). A new Mixin class must be listed in `fnc.mixins.json` (`client` for client-only targets, otherwise `mixins`).
- `update.json` in the repository root is read by the Forge update checker from `master` (`mod_update_json_url`); it lists the promoted version per Minecraft version.
- F&C has no GameTests. The client and server runs set `forge.enableGameTest` to `false`, because the GameTests of the DM&R jar reference the optional Create/AE2 classes that are not on the classpath; `runGameTestServer` crashes as long as no GameTests are registered.

### Tomes And Libs (`libs/tnl-<tnl_version>.jar`)

- The author's own library (mod ID `tnl`, package `de.joh.tnl`), a regular (SRG-obfuscated) mod jar that is included via the `flatDir` repository and `modImplementation "de.joh:tnl:${tnl_version}"`, so ModDevGradle remaps it.
- F&C uses `de.joh.tnl.api.item.CurioSlot` and `CurioRegistry` (used by `ItemInit.registerCurio`), `ScrollableItem` (debug orbs), and the data generation code shared with Dragon Magic and Relics in `de.joh.tnl.api.datagen`: all recipe builders (`ModRecipeBuilder`, `ItemAndPatternRecipeBuilder`, `ItemOutputRecipeBuilder`, `ManaweavingRecipeBuilder`, `EldrinAltarRecipeBuilder`, `SpellPartRecipeBuilder`, `MagiChem*RecipeBuilder`), `ManaweavePatterns`, the provider bases `MnaRecipeProvider` (recipe ID scheme, `save`/`saveIntegration` overloads, `whenModLoaded`, `ALL_AFFINITIES`, `magichemItem`), `MnaItemTagsProvider` (Curios slot tags, `mna:relics`), `DefaultItemModelProvider` (default item models, `handheldItem`) and `DataGenValidation.validateRegisters`. Changes to these classes are made in TnL; the providers in `de.joh.fnc.datagen` extend them.
- TnL also contains the Dragon Magic API (`de.joh.tnl.api.item.IDragonMagicItem`, `BaseDragonMagicItem`, `ChargeableDragonMagicItem`, `DragonMagicRegistry`, `de.joh.tnl.api.event.DragonMagicChangeEvent`) and the Curio Boost capability (`de.joh.tnl.capabilities.CurioBoostProvider`). F&C currently uses neither; Dragon Magic items would be built on these classes, not on DM&R.
- The jar is version-controlled (exception `!libs/tnl-*.jar` in `.gitignore`). To update TnL, replace the jar and adjust `tnl_version`/`tnl_version_range`.

### Dragon Magic and Relics (`libs/dmnr-<dmnr_version>.jar`)

- Optional integration. The jar is included like TnL (`modImplementation "de.joh:dmnr:${dmnr_version}"`, version-controlled via `!libs/dmnr-*.jar`), so the compat code compiles and DM&R is loaded in every development run.
- The integration lives in `de.joh.fnc.compat.dmnr` (`AddonDmnr`; currently the three Wild Magics of `AddonDmnrWildMagicInit`). To update DM&R, replace the jar and adjust `dmnr_version`/`dmnr_version_range`.

### Branches

- `master`: main branch, should always contain a working version (the Forge update checker reads `update.json` from it).
- `blood_magic`: development of the Blood Mage faction (1.3.x); also contains the current build setup (ModDevGradle Legacy, Tomes And Libs, data generation), which is not on `master` yet.
- `1.18`, `1.19`: states for older Minecraft versions.

## Build & run

On Windows use `gradlew.bat`, or `./gradlew` in Git Bash. The initial Gradle setup can take 10+ minutes.

```bash
./gradlew build             # Compiles and produces the reobfuscated release jar in build/libs (unobfuscated jar: build/devlibs)
./gradlew runClient         # Starts a dev client (game directory run/client)
./gradlew runServer         # Dedicated dev server (--nogui)
./gradlew runData           # Datagen into src/generated/resources
```

## Project structure

```
build.gradle, settings.gradle     Build logic (ModDevGradle Legacy)
gradle.properties                  All versions and mod metadata
update.json                        Forge update checker (promoted versions)
libs/                              Tomes And Libs (tnl-<version>.jar), Dragon Magic and Relics (dmnr-<version>.jar)
icon-files/                        Source icons (not part of the mod)
run/                               Gradle run directories (local)
CREDITS.md, CONTRIBUTING.md        Credits for assets/translations, contribution guide
src/main/java/de/joh/fnc/
├── FactionsAndCuriosities.java    Entry point (@Mod), registers DeferredRegisters, config, networking, guidebook
├── api/                           API usable by other mods: event/ (Wild Magic, Smite and Spell Adjustment events),
│                                  wildmagic/, smite/, spelladjustment/, spell/component (SmiteComponent),
│                                  util/ (AttributeInit, BloodMagicHelper, ModTags, Quality)
├── common/
│   ├── init/                      Registrations: ItemInit, EffectInit, EnchantmentInit, CreativeModeTabInit,
│   │                              FactionInit (factions + casting resources), SpellInit (M&A shapes/components),
│   │                              RitualInit (M&A rituals), WildMagicInit, SpellAdjustmentInit
│   ├── faction/                   Factions (Wild Courts, Paladins, Blood Mages), ResourceIDs; castingresource/
│   ├── item/                      All items (curios, armor, weapons, tools, debug orbs); material/ (ArmorMaterials, ToolMaterials)
│   ├── effect/                    MobEffects, grouped into beneficial/harmful/neutral; smite/ (the Smite effects)
│   ├── enchantment/               Holy Smite
│   ├── spell/component, shape/    Custom M&A spell components and shapes
│   ├── ritual/                    Custom M&A ritual effects
│   ├── wildmagic/                 Wild Magic implementations
│   ├── spelladjustment/           Spell Adjustment implementations
│   ├── capability/                Player capabilities (Smites, Gambler's Blow)
│   ├── event/                     Forge bus handlers: CommonEventHandler, DamageEventHandler, MagicEventHandler,
│   │                              CapabilityForgeEventHandler; mod bus: CommonModEventHandler
│   └── util/                      CommonConfig (fnc-common.toml), RLoc (ResourceLocation helper),
│                                  Registries (custom registries), ModItemProperties (item model properties)
├── compat/                        AddonCompatibleManager, AddonCompatibleMod; dmnr/ (Dragon Magic and Relics)
├── datagen/                       Data generators (GatherDataEvent): item models, item/block/damage type tags, recipes
├── mixins/                        CastingResourceMixin (resource bar texture of the F&C factions)
└── networking/                    Messages (SimpleChannel)
src/main/templates/                META-INF/mods.toml, pack.mcmeta (expanded from gradle.properties)
src/main/resources/
├── fnc.mixins.json                Mixin config
├── assets/fnc/                    lang/ (en_us, zh_cn), guide/base/ (Codex entries), gui/ (resource bars), textures/
└── data/fnc/                      recipes/ (rituals/, manaweaving/enchanting/holy_smite.json, sacrificial_dagger.json),
                                   damage_type/
src/generated/resources/           Datagen output (./gradlew runData), committed, never edited by hand:
├── assets/fnc/models/item/        Item models
├── data/fnc/recipes/              Manaweaving, Eldrin Altar, spell part and MagiChem recipes: manaweaving/,
│                                  eldrin_altar/, components/, shapes/ and magichem/ (distillation/, sublimation/)
│                                  (forge:conditional)
└── data/                          All tags: fnc/ (items, blocks, damage_type), curios/ (curio slots),
                                   mna/ (e.g. relics), minecraft/ (damage_type)
```

## Conventions

- **Registration**: new content is registered in the matching `*Init` class via `DeferredRegister`/`RegistryObject` (`ItemInit.ITEMS.register("snake_case_id", ...)`). Curios are registered with `ItemInit.registerCurio("snake_case_id", CurioSlot.<SLOT>, ...)`, which also assigns them to their Curios slot (see "Data generation"). Spells, rituals, factions, Wild Magics and Spell Adjustments are registered in `SpellInit`/`RitualInit`/`FactionInit`/`WildMagicInit`/`SpellAdjustmentInit` via `RegisterEvent` on the mod bus; the registries of Wild Magics and Spell Adjustments are created in `common/util/Registries` (IDs `wildmagic/<name>`, `spelladjustment/<name>`). Casting resources are registered in `FactionInit` (`CastingResourceRegistry`), their GUI providers in `FactionInit.Client`. Always create `ResourceLocation`s via `RLoc.create(...)`.
- **Event handlers**: classes annotated with `@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = ...)` and static `@SubscribeEvent` methods. There is no `client` package; keep client code in classes that are only loaded on the client (subscribers with `value = Dist.CLIENT` like `FactionInit.Client`, `FMLClientSetupEvent`) or guard it with `@OnlyIn(Dist.CLIENT)`/`Dist.CLIENT`.
- **Optional mods with code integration**: one subclass of `AddonCompatibleMod` per mod in `compat/<mod_id>`, added to the list in `AddonCompatibleManager`. `onLoad()` only runs if the mod is loaded and registers the handlers of the integration (e.g. `AddonDmnr` registers `AddonDmnrWildMagicInit` on the mod bus). Classes of the other mod may only be referenced inside its compat package. `runData` fails if a mod of `AddonCompatibleManager.MODS` is not loaded, so every such mod must be part of the development runs.
- **Translations**: every new key belongs at least in `assets/fnc/lang/en_us.json`; tooltips use `item.fnc.<id>.description`. Every effect gets `effect.fnc.<id>.description`, the key read by effect description mods (JEED, EMIffect, Effect Descriptions); every enchantment gets `enchantment.fnc.<id>.desc` (Enchantment Descriptions). New content should get a Codex entry in `assets/fnc/guide/base/en_us.json`.
- **Data generation**: item models, tags (incl. curio slots, block and damage type tags) and the manaweaving, Eldrin Altar, spell component, shape and MagiChem recipes are generated by the providers in `de.joh.fnc.datagen` into `src/generated/resources`. Change the provider and run `./gradlew runData` instead of editing these files, and commit the regenerated output. New items get a flat item model with the texture `item/<item_id>` automatically; special models are added in `ModItemModelProvider` (handheld items, the debug orbs with the M&A texture, the Blessed Bow with its pulling stages read from `ModItemProperties`). Most tag entries are listed explicitly in the tag providers, except two tags derived from the items: the Curios slot tags (`curios:<slot>`) come from the slot passed to `ItemInit.registerCurio`, and `mna:relics` contains every item that implements `IRelic`. `runData` fails for an `ICurioItem` registered without a slot; curios that do not implement `ICurioItem` (effects handled by events) are not checked, so they must not be forgotten. F&C has no blocks, so there are no blockstates, block models or loot tables. The damage types themselves are maintained by hand in `data/fnc/damage_type`, only their tags are generated (`ModDamageTypeTagsProvider`). A file must never exist in both `src/main/resources` and `src/generated/resources`. Providers that handle every item of the mod take them from `DataGenerators.itemRegisters()`; a new `DeferredRegister<Item>` (e.g. of a new integration) must be added there, otherwise `runData` fails.
- **Recipe builders**: all generated recipes come from `ModRecipeProvider`, written with builders based on `ModRecipeBuilder`. It collects every problem of a recipe in `validate` and fails `runData` with the full list in `save(writer, id, existingFileHelper)`, writes the `type` itself (serializers of MagiChem are not registered during `runData`), generates no advancements, and wraps the recipe in `forge:conditional` when it has conditions (`whenModLoaded(modId)`). Shared checks live there too (`checkRange`, `checkSize`, `checkItemOutput`). The M&A builders extend `ItemAndPatternRecipeBuilder`, which takes the item and pattern limits of the crafting station as constructor arguments; `item(ItemLike, count)` adds an item several times. The builders with an item output (manaweaving, Eldrin Altar) share `ItemOutputRecipeBuilder`, which writes `output` and `quantity`. The recipes are grouped by the item they craft, not by crafting station.
- **Manaweaving recipes** (`mna:manaweaving-recipe`) are written with `ManaweavingRecipeBuilder`. IDs: `fnc:manaweaving/<name>`; recipes of an integration are `fnc:<mod_id>/<name>`, wrapped in `forge:conditional` with `forge:mod_loaded` (`saveIntegration`). Never change a recipe ID: the Codex references the recipes by ID. M&A resolves an entry of `items` as an item if one with that ID exists, otherwise as an item tag, so tags are written without `#`; `items` is matched shapeless, `patterns` in order. Pattern IDs are always the full form `mna:manaweave_patterns/<name>` (constants in `ManaweavePatterns`). `quantity` is always written (M&A default: 1). The builder fails `runData` if the tier is outside 1–5, `items`/`patterns` are empty or too long (9/6), the output or an item is not registered, a tag uses an item ID, or a tag/pattern does not exist according to the `ExistingFileHelper`. Not checked: the faction ID, whether a tag contains items, and the tier of the ingredients. The builder supports no `enchant`, so the enchanting recipe `manaweaving/enchanting/holy_smite` is maintained by hand.
- **Eldrin Altar recipes** (`mna:eldrin-altar`) come from `ModRecipeProvider` as well and are written with `EldrinAltarRecipeBuilder` (same base and the same rules for `items`, tier, faction and `quantity`). IDs: `fnc:eldrin_altar/<name>`. The altar has no patterns, so `patterns` is not written (the base class omits it when the pattern limit is 0). Instead `power_requirements` lists the Eldrin power per affinity (`power(amount, Affinity...)`). Eldrin recipes sit next to the manaweaving recipes of the same item group in the provider, so upgrade stages stay together. Additionally to the shared checks, the builder fails `runData` if `power_requirements` is empty, an affinity is not one of `Affinity.CoreSix()` (there are no Eldrin conduits for the others) or is listed twice, or an amount is not positive. `colors` is read by M&A but never used, so the builder does not support it.
- **Spell component and shape recipes** (`mna:component`, `mna:shape`) come from `ModRecipeProvider` as well and are written with one builder for both, `SpellPartRecipeBuilder` (`component(SpellEffect, tier)` / `shape(Shape, tier)`; same base and the same rules for `items`, `patterns`, tier and faction, limits 9/9). `output` is the registry ID of the spell part, `quantity` is not written. The recipe ID is always the registry ID of the spell part (`fnc:components/<name>`, `fnc:shapes/<name>`), also for integrations (made conditional with `whenModLoaded`): M&A takes the tier of a spell part from the recipe with the same ID and the Codex (`"type": "spell_part"`) finds it by that ID, so the builder derives the ID itself and fails for any other ID or an unregistered spell part.
- **MagiChem recipes** (distillation and sublimation) come from `ModRecipeProvider` as well, written with `MagiChemDistillationRecipeBuilder` and `MagiChemSublimationRecipeBuilder` (shared base `MagiChemRecipeBuilder`). The ID is derived from the item: `fnc:magichem/<distillation|sublimation>/<item_id>` (`save` overloads in the provider), so every item has at most one of each; the base adds `whenModLoaded("magichem")` itself, so the recipe is always wrapped in `forge:conditional`. Materia are added with `essentia(name, count)` / `admixture(name, count)` (`magichem:essentia_<name>`, `magichem:admixture_<name>`), in order; a materia listed twice, a non-positive count or more than 5 materia per list fail `runData`. MagiChem is not loaded during `runData`, so the base reads two things from the MagiChem jar: the materia from MagiChem's own definitions (`data/magichem/generator/essentia.json`, `admixtures.json`) and its item IDs from its item models (`assets/magichem/models/item/*.json`). `build.gradle` resolves the jar through the configuration `magichemData` (same `magichem_version`, never on a classpath) and passes its path to the data run as the system property `tnl.magichem_jar`. Items of MagiChem are referenced by ID (`magichemItem(...)` in the provider). Advancement fields (`required_advancement`, `forbidden_advancement`, `granted_advancement`) are not supported.
  - **Distillation** (`magichem:distillation_fabrication`, `distillation(item)`): the materia go into `components`, at most 5. Defaults: `wisdom` 6 (distill only, no fabrication; 0–5 would allow it), `categories` `CRAFTABLE` (bitmask enum `Category`, `categories(...)` replaces it), `batch_size` 1, `output_rate` 1.0. Also fails for wisdom outside 0–6, batch size outside 1–127, output rate outside 0 (exclusive)–1 or empty categories.
  - **Sublimation** (`magichem:sublimation`, `sublimation(item, tier)`, Alchemical Nexus): `stage(experience)` starts a stage; the following `items(...)` / `item(id)` (single items, repeated for several) and `essentia`/`admixture` calls belong to it. MagiChem reads a `tag` in `components` but ignores it, so tags are not supported. `object.count` (`quantity(...)`, default 1) is always written. Defaults: `wisdom` 0 (wisdom the Nexus needs to offer the recipe). Also fails for tier outside 1–5, wisdom outside 0–5, quantity outside 1–max stack size, no or more than 4 stages, negative experience, no or more than 5 components per stage, or a component that is neither registered nor a MagiChem item. The limits are those of MagiChem's own recipes.
- **Recipes/data**: all other JSON files (rituals in `data/fnc/recipes/rituals`, the enchanting recipe `manaweaving/enchanting/holy_smite`, the crafting recipe `sacrificial_dagger`; damage types, Codex, lang) are maintained by hand. M&A-specific recipe types live in the respective subfolders of `data/fnc/recipes`.
- **Configuration**: balancing values should be `ForgeConfigSpec` entries in `CommonConfig` instead of hardcoded values whenever they should be adjustable for modpacks.
- **Code style**: English identifiers and comments, Javadoc on classes with `@author Joh0210`, `@NotNull`/`@Nullable` from `org.jetbrains.annotations`. Follow the style of the surrounding class.
- **Mixins** only when there is no other way via events/APIs – they can easily cause incompatibilities (see "Build configuration" for the setup).
