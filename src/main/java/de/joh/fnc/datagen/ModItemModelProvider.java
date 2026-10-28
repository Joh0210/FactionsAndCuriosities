package de.joh.fnc.datagen;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.init.ItemInit;
import de.joh.tnl.api.datagen.DefaultItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author Joh0210
 */
public class ModItemModelProvider extends DefaultItemModelProvider {
    // Minimum "pull" (ModItemProperties) of each pulling model of the bow, 0 needs only "pulling"
    private static final float[] BOW_PULL_STAGES = {0.0F, 0.65F, 0.9F};

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FactionsAndCuriosities.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheldItem(ItemInit.BRIMSTONE_SWORD);
        handheldItem(ItemInit.DECK_OF_MANY);
        handheldItem(ItemInit.SACRIFICIAL_DAGGER);
        handheldItem(ItemInit.WILD_PICKAXE);
        debugOrb(ItemInit.DEBUG_ROD);
        debugOrb(ItemInit.DEBUG_ROD_SPELL_ADJUSTMENT);
        debugOrb(ItemInit.DEBUG_ROD_SMITE);
        blessedBow();

        defaultModels(DataGenerators.itemEntries());
    }

    private void debugOrb(RegistryObject<Item> item) {
        getBuilder(item.getId().getPath())
                .parent(GENERATED)
                .texture("layer0", new ResourceLocation("mna", "item/debugorb"));
    }

    // Same display and pull stages as the vanilla bow
    private void blessedBow() {
        String name = ItemInit.BLESSED_BOW.getId().getPath();
        ItemModelBuilder bow = getBuilder(name)
                .parent(GENERATED)
                .texture("layer0", modLoc(ITEM_FOLDER + "/" + name))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(-80, 260, -40).translation(-1, -2, 2.5F).scale(0.9F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(-80, 280, 40).translation(-1, -2, 2.5F).scale(0.9F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(1.13F, 3.2F, 1.13F).scale(0.68F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 90, -25).translation(1.13F, 3.2F, 1.13F).scale(0.68F).end()
                .end();

        for (int stage = 0; stage < BOW_PULL_STAGES.length; stage++) {
            String pulling = name + "_pulling_" + stage;
            ItemModelBuilder pullingModel = getBuilder(pulling)
                    .parent(bow)
                    .texture("layer0", modLoc(ITEM_FOLDER + "/" + pulling));

            ItemModelBuilder.OverrideBuilder override = bow.override().predicate(mcLoc("pulling"), 1);
            if (BOW_PULL_STAGES[stage] > 0) {
                override.predicate(mcLoc("pull"), BOW_PULL_STAGES[stage]);
            }
            override.model(pullingModel);
        }
    }
}
