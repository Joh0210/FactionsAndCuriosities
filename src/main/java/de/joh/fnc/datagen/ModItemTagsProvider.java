package de.joh.fnc.datagen;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.util.RLoc;
import de.joh.tnl.api.datagen.MnaItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Joh0210
 */
public class ModItemTagsProvider extends MnaItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, FactionsAndCuriosities.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        List<Item> modItems = DataGenerators.itemEntries().map(RegistryObject::get).toList();
        addCurioSlotTags(ItemInit.getCurioSlots(), modItems);
        addRelicTag(modItems);

        // Reagent of the rituals pact and twisted_fealty
        tag(ItemTags.create(RLoc.create("paladin_equipment"))).add(Items.SHIELD, Items.IRON_SWORD);

        tag(ItemTags.create(new ResourceLocation("mna", "battlemage_amulet_valid_swords"))).add(ItemInit.BRIMSTONE_SWORD.get());
        tag(ItemTags.create(new ResourceLocation("mna", "cannon_hundredpct_boost"))).add(ItemInit.BRIMSTONE_SWORD.get());
    }
}
