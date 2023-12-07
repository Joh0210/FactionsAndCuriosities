package de.joh.fnc.compat.dmnr;

import de.joh.dmnr.DragonMagicAndRelics;
import de.joh.fnc.compat.AddonCompatibleMod;
import de.joh.fnc.compat.dmnr.common.event.AddonDmnrDamageEventHandler;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrArmorUpgradeInit;
import de.joh.fnc.compat.dmnr.common.event.AddonDmnrCommonEventHandler;
import de.joh.fnc.compat.dmnr.common.event.AddonDmnrClientModEventHandler;
import de.joh.fnc.compat.dmnr.client.gui.AddonDmnrContainerInit;
import de.joh.fnc.compat.dmnr.client.gui.AddonDmnrGuiInit;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrItemInit;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrWildMagicInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
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
        AddonDmnrItemInit.ITEMS.register(modBus);

        modBus.register(AddonDmnrContainerInit.class);
        modBus.register(AddonDmnrGuiInit.class);
        modBus.register(AddonDmnrArmorUpgradeInit.class);
        modBus.register(AddonDmnrWildMagicInit.class);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modBus.register(AddonDmnrClientModEventHandler.class));

        IEventBus forge_bus = MinecraftForge.EVENT_BUS;
        forge_bus.register(AddonDmnrCommonEventHandler.class);
        forge_bus.register(AddonDmnrDamageEventHandler.class);
    }

}
