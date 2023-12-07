package de.joh.fnc.common.capability;

import de.joh.fnc.api.smite.SmiteMobEffect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * List Entry for managing the active Smites of a Player
 * @see SmitePlayerCapability
 * @see SmiteMobEffect
 * @author Joh0210
 */
public class SmiteEntry {
    private final SmiteMobEffect smite;
    private int damage;
    private int range;
    private int magnitude;
    private int duration;
    private int precision;

    public SmiteEntry(SmiteMobEffect smite, int damage, int range, int magnitude, int duration, int precision){
        this.smite = smite;
        this.damage = damage;
        this.range = range;
        this.magnitude = magnitude;
        this.duration = duration;
        this.precision = precision;
    }

    public SmiteEntry(CompoundTag nbt) throws IllegalArgumentException {
        if (nbt.contains("smite") && nbt.contains("damage") && nbt.contains("range") && nbt.contains("magnitude") && nbt.contains("duration") && nbt.contains("precision") && ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(nbt.getString("smite"))) instanceof SmiteMobEffect smiteEffect) {
            this.smite = smiteEffect;
            this.damage = nbt.getInt("damage");
            this.range = nbt.getInt("range");
            this.magnitude = nbt.getInt("magnitude");
            this.duration = nbt.getInt("duration");
            this.precision = nbt.getInt("precision");
        } else {
            throw new IllegalArgumentException("The NBT data received does not match that of a SmiteEntry");
        }
    }

    public CompoundTag saveNBT() throws IllegalArgumentException {
        CompoundTag nbt = new CompoundTag();

        ResourceLocation rLoc = ForgeRegistries.MOB_EFFECTS.getKey(smite);
        if(rLoc == null){
            throw new IllegalArgumentException("The SmiteEffect does not have a registryName");
        }

        nbt.putString("smite", rLoc.toString());
        nbt.putInt("damage", damage);
        nbt.putInt("range", range);
        nbt.putInt("magnitude", magnitude);
        nbt.putInt("duration", duration);
        nbt.putInt("precision", precision);

        return nbt;
    }

    public void overwrite(int damage, int range, int magnitude, int duration, int precision){
        this.damage = damage;
        this.range = range;
        this.magnitude = magnitude;
        this.duration = duration;
        this.precision = precision;
    }

    /**
     * @return a deep copy of this instance, so that changes on it do not further this instance
     */
    public SmiteEntry deepCopy(){
        return new SmiteEntry(smite, damage, range, magnitude, duration, precision);
    }

    public int getDamage() {
        return damage;
    }

    public SmiteMobEffect getSmite() {
        return smite;
    }

    public int getDuration() {
        return duration;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public int getPrecision() {
        return precision;
    }

    public int getRange() {
        return range;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setMagnitude(int magnitude) {
        this.magnitude = magnitude;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
