package de.joh.fnc.compat.dmnr;

import de.joh.fnc.compat.AddonCompatibleMod;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrArmorUpgradeInit;
import de.joh.fnc.compat.dmnr.common.event.AddonDmnrCommonHandler;
import de.joh.fnc.compat.dmnr.common.event.AddonDmnrClientModEvents;
import de.joh.fnc.compat.dmnr.client.gui.AddonDmnrContainerInit;
import de.joh.fnc.compat.dmnr.client.gui.AddonDmnrGuiInit;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonDmnr extends AddonCompatibleMod {
    public static final String MODID = "dragonmagicandrelics";

    public static ResourceLocation rl(String path)
    {
        return new ResourceLocation(MODID, path);
    }

    @Override
    public String getModID()
    {
        return MODID;
    }

    @Override
    protected void onLoad()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        AddonDmnrItemInit.ITEMS.register(modBus);

        modBus.register(AddonDmnrContainerInit.class);
        modBus.register(AddonDmnrGuiInit.class);
        modBus.register(AddonDmnrArmorUpgradeInit.class);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modBus.register(AddonDmnrClientModEvents.class));

        IEventBus forge_bus = MinecraftForge.EVENT_BUS;
        forge_bus.register(AddonDmnrCommonHandler.class);
    }

}
