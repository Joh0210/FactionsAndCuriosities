package de.joh.fnc.datagen;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.compat.AddonCompatibleManager;
import de.joh.fnc.compat.AddonCompatibleMod;
import de.joh.tnl.api.datagen.DataGenValidation;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // The providers run in this order, so the checks run before any file is generated
        generator.addProvider(true, new ValidationProvider());
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));

        ModBlockTagsProvider blockTags = generator.addProvider(event.includeServer(), new ModBlockTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModDamageTypeTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, existingFileHelper));
    }

    static List<DeferredRegister<Item>> itemRegisters() {
        return List.of(ItemInit.ITEMS);
    }

    static Stream<RegistryObject<Item>> itemEntries() {
        return itemRegisters().stream().flatMap(register -> register.getEntries().stream());
    }

    /**
     * Forge fires the GatherDataEvent for every loaded mod, also when another mod's data is generated (e.g. an addon
     * with this mod in its development runs), and the event does not tell whose data is generated. Only the providers
     * of the generated mods run, so the checks are a provider instead of being part of the event handler.
     */
    private static class ValidationProvider implements DataProvider {
        @Override
        public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
            for (AddonCompatibleMod mod : AddonCompatibleManager.MODS) {
                if (!mod.isLoaded()) {
                    throw new IllegalStateException(FactionsAndCuriosities.MOD_ID + ": data generation needs all optional mods with code integration, missing: " + mod.getModID());
                }
            }
            DataGenValidation.validateRegisters(FactionsAndCuriosities.MOD_ID, "DataGenerators.itemRegisters()",
                    ForgeRegistries.ITEMS.getKeys(), itemEntries());
            return CompletableFuture.completedFuture(null);
        }

        @Override
        public @NotNull String getName() {
            return "Validation: " + FactionsAndCuriosities.MOD_ID;
        }
    }
}
