package de.joh.fnc.common.capability;

import com.mna.api.spells.base.ISpellDefinition;
import com.mna.spells.crafting.SpellRecipe;
import de.joh.fnc.api.smite.SmiteMobEffect;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;

/**
 * A Player only capability for managing Smites
 * @see PlayerCapabilityProvider
 * @see SmiteMobEffect
 * @author Joh0210
 */
public class SmitePlayerCapability {
    private ArrayList<SmiteEntry> smites = new ArrayList<>();

    private @Nullable CompoundTag smiteRecipe = null;

    public void copyFrom(SmitePlayerCapability source) {
        this.smites = source.smites;
        this.smiteRecipe = source.smiteRecipe;
    }

    public void saveNBT(CompoundTag compound) {
        CompoundTag nbt = new CompoundTag();

        int smiteSize = 0;
        for(SmiteEntry entry : smites) {
            try {
                nbt.put("smite_entry_" + smiteSize, entry.saveNBT());
                smiteSize++;
            }
            catch (IllegalArgumentException e){
                //todo: log
                nbt.remove("smite_entry_" + smiteSize);
            }
        }
        nbt.putInt("smite_size", smiteSize);

        if(smiteRecipe != null){
            nbt.put("smite_shape", smiteRecipe);
        }

        compound.put("fnc_smite_data", nbt);
    }

    public void loadNBT(CompoundTag compound) {
        if (compound.contains("fnc_smite_data")) {
            CompoundTag nbt = compound.getCompound("fnc_smite_data");

            smites = new ArrayList<>();
            for(int i = 0; i< nbt.getInt("smite_size"); i++){
                try {
                    smites.add(new SmiteEntry(nbt.getCompound("smite_entry_" + i)));
                }
                catch (IllegalArgumentException e){
                    //todo: log
                }
            }

            if(nbt.contains("smite_shape")){
                smiteRecipe = nbt.getCompound("smite_shape");
            } else {
                smiteRecipe = null;
            }
        }
    }

    public void addSmiteFromShape(ISpellDefinition smite){
        CompoundTag smiteShape = new CompoundTag();
        smite.writeToNBT(smiteShape);
        this.smiteRecipe = smiteShape;
    }

    public void removeSmiteFromShape(){
        this.smiteRecipe = null;
    }

    public void addSmite(SmiteMobEffect smiteEffect, int damage, int range, int magnitude, int duration, int precision){
        Optional<SmiteEntry> optionalEntry = smites.stream().filter(s -> s.getSmite() == smiteEffect).findFirst();

        if(optionalEntry.isEmpty()){
            smites.add(new SmiteEntry(smiteEffect, damage, range, magnitude, duration, precision));
        } else {
            optionalEntry.get().overwrite(damage, range, magnitude, duration, precision);
        }
    }

    public void removeSmite(SmiteMobEffect smiteEffect){
        smites.removeIf(entry -> entry.getSmite() == smiteEffect);
    }

    /**
     * @return a deep copy of the SmitesArray, so that changes on it do not affect the original
     */
    public ArrayList<SmiteEntry> getSmites() {
        ArrayList<SmiteEntry> deepCopy = new ArrayList<>();

        for(SmiteEntry entry : this.smites){
            deepCopy.add(entry.deepCopy());
        }

        return deepCopy;
    }

    @Nullable
    public ISpellDefinition getSmiteFromShape(){
        return smiteRecipe == null ? null : SpellRecipe.fromNBT(smiteRecipe);
    }
}
