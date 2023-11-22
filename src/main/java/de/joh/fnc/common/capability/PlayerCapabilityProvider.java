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
 * Provides all Player Capabilities of this mod
 * @author Joh0210
 */
public class PlayerCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    /**
     * Capability for managing Smites
     */
    public static Capability<SmitePlayerCapability> PLAYER_SMITE = CapabilityManager.get(new CapabilityToken<>() {});

    private SmitePlayerCapability smitePlayerCapability = null;
    private final LazyOptional<SmitePlayerCapability> optional = LazyOptional.of(this::createSmitePlayerCapability);

    private SmitePlayerCapability createSmitePlayerCapability() {
        if(this.smitePlayerCapability == null){
            this.smitePlayerCapability = new SmitePlayerCapability();
        }

        return this.smitePlayerCapability;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_SMITE){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createSmitePlayerCapability().saveNBT(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createSmitePlayerCapability().loadNBT(nbt);
    }
}
