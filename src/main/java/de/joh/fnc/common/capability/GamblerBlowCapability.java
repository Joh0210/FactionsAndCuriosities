package de.joh.fnc.common.capability;

import net.minecraft.nbt.CompoundTag;

/**
 * Capability for GamblersBlowMobEffect to store the precision of the applied spell.
 * @author Joh0210
 */
public class GamblerBlowCapability {
    private int offensivePrecision = 1;
    private int defensivePrecision = 1;

    public void copyFrom(GamblerBlowCapability source) {
        this.offensivePrecision = source.offensivePrecision;
        this.defensivePrecision = source.defensivePrecision;
    }

    public void saveNBT(CompoundTag compound) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("offensivePrecision", offensivePrecision);
        nbt.putInt("defensivePrecision", defensivePrecision);
        compound.put("fnc_gambler_blow_data", nbt);
    }

    public void loadNBT(CompoundTag compound) {
        if (compound.contains("fnc_gambler_blow_data")) {
            CompoundTag nbt = compound.getCompound("fnc_gambler_blow_data");
            offensivePrecision = nbt.getInt("offensivePrecision");
            defensivePrecision = nbt.getInt("defensivePrecision");
        }
    }

    public int getOffensivePrecision() {
        return offensivePrecision;
    }

    public void setOffensivePrecision(int offensivePrecision) {
        this.offensivePrecision = offensivePrecision;
    }

    public int getDefensivePrecision() {
        return defensivePrecision;
    }

    public void setDefensivePrecision(int defensivePrecision) {
        this.defensivePrecision = defensivePrecision;
    }
}
