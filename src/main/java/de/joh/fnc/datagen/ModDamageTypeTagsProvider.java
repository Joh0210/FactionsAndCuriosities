package de.joh.fnc.datagen;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.util.RLoc;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * The damage types themselves are maintained by hand in {@code data/fnc/damage_type}.
 * @author Joh0210
 */
public class ModDamageTypeTagsProvider extends TagsProvider<DamageType> {
    private static final ResourceKey<DamageType> BLOOD_SACRIFICE = damageType("blood_sacrifice");
    private static final ResourceKey<DamageType> FINAL_DUAL_BYPASS_ARMOR = damageType("final_dual_bypass_armor");
    private static final ResourceKey<DamageType> FINAL_DUAL_BYPASS_ALL = damageType("final_dual_bypass_all");
    private static final ResourceKey<DamageType> POSTPONEMENT_DAMAGE = damageType("postponement_damage");

    public ModDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, FactionsAndCuriosities.MOD_ID, existingFileHelper);
    }

    private static ResourceKey<DamageType> damageType(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, RLoc.create(name));
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(DamageTypeTags.BYPASSES_ARMOR).add(BLOOD_SACRIFICE, FINAL_DUAL_BYPASS_ARMOR, FINAL_DUAL_BYPASS_ALL,
                POSTPONEMENT_DAMAGE);
        tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(FINAL_DUAL_BYPASS_ALL, POSTPONEMENT_DAMAGE);
        tag(TagKey.create(Registries.DAMAGE_TYPE, RLoc.create("is_postponement"))).add(POSTPONEMENT_DAMAGE);
    }
}
