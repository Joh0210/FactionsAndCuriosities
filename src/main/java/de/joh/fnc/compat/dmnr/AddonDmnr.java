package de.joh.fnc.compat.dmnr;

import de.joh.dmnr.DragonMagicAndRelics;
import de.joh.fnc.compat.AddonCompatibleMod;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrWildMagicInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonDmnr extends AddonCompatibleMod {
    public static final String MOD_ID = DragonMagicAndRelics.MOD_ID;

    public static ResourceLocation rl(String path)
    {
        return new ResourceLocation(MOD_ID, path);
    }

    @Override
    public String getModID()
    {
        return MOD_ID;
    }

    @Override
    protected void onLoad()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.register(AddonDmnrWildMagicInit.class);
    }
}
