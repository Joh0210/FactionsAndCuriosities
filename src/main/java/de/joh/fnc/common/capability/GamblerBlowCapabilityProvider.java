package de.joh.fnc.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides the GamblerBlow Capability
 * @author Joh0210
 */
public class GamblerBlowCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<GamblerBlowCapability> GAMBLER_BLOW = CapabilityManager.get(new CapabilityToken<>() {});

    private GamblerBlowCapability gamblerBlowCapability = null;
    private final LazyOptional<GamblerBlowCapability> optional = LazyOptional.of(this::createGamblerBlowCapability);

    private GamblerBlowCapability createGamblerBlowCapability() {
        if(this.gamblerBlowCapability == null){
            this.gamblerBlowCapability = new GamblerBlowCapability();
        }

        return this.gamblerBlowCapability;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == GAMBLER_BLOW){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createGamblerBlowCapability().saveNBT(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createGamblerBlowCapability().loadNBT(nbt);
    }
}
