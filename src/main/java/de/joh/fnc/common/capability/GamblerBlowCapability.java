package de.joh.fnc.common.capability;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

/**
 * Capability for GamblersBlowMobEffect to store the precision of the applied spell.
 * @author Joh0210
 */
public class GamblerBlowCapability {
    private int offensivePrecision = 1;
    private int defensivePrecision = 1;
    private int finalDualPrecision = 1;
    private boolean finalDualLost = true;
    private UUID finalDualEnemie = null;

    public void copyFrom(GamblerBlowCapability source) {
        this.offensivePrecision = source.offensivePrecision;
        this.defensivePrecision = source.defensivePrecision;
        this.finalDualPrecision = source.finalDualPrecision;
        this.finalDualLost = source.finalDualLost;
        this.finalDualEnemie = source.finalDualEnemie;
    }

    public void saveNBT(CompoundTag compound) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("offensivePrecision", offensivePrecision);
        nbt.putInt("defensivePrecision", defensivePrecision);
        nbt.putInt("finalDualPrecision", finalDualPrecision);
        nbt.putBoolean("finalDualLost", finalDualLost);
        if (finalDualEnemie != null) {
            nbt.putUUID("finalDualEnemie", finalDualEnemie);
        }
        compound.put("fnc_gambler_blow_data", nbt);
    }

    public void loadNBT(CompoundTag compound) {
        if (compound.contains("fnc_gambler_blow_data")) {
            CompoundTag nbt = compound.getCompound("fnc_gambler_blow_data");
            offensivePrecision = nbt.getInt("offensivePrecision");
            defensivePrecision = nbt.getInt("defensivePrecision");
            finalDualPrecision = nbt.getInt("finalDualPrecision");
            finalDualLost = nbt.getBoolean("finalDualLost");
            if (nbt.hasUUID("finalDualEnemie")) {
                finalDualEnemie = nbt.getUUID("finalDualEnemie");
            } else {
                finalDualEnemie = null;
            }
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

    public int getFinalDualPrecision() {
        return finalDualPrecision;
    }

    public void setFinalDualPrecision(int finalDualPrecision) {
        this.finalDualPrecision = finalDualPrecision;
    }

    public boolean isFinalDualLost() {
        return finalDualLost;
    }

    public void setFinalDualLost(boolean finalDualLost) {
        this.finalDualLost = finalDualLost;
    }

    public UUID getFinalDualEnemie() {
        return finalDualEnemie;
    }

    public void setFinalDualEnemie(UUID finalDualEnemie) {
        this.finalDualEnemie = finalDualEnemie;
    }
}
