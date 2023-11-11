package de.joh.fnc.compat.dmnr;

import de.joh.fnc.compat.AddonCompatibleMod;
import de.joh.fnc.compat.dmnr.armorupgrades.AddonDragonMagicAndRelicsArmorUpgradeInit;
import de.joh.fnc.compat.dmnr.event.AddonDragonMagicAndRelicsCommonHandler;
import de.joh.fnc.compat.dmnr.event.AddonDragonMagicAndRelicsClientModEvents;
import de.joh.fnc.compat.dmnr.gui.AddonDragonMagicAndRelicsContainerInit;
import de.joh.fnc.compat.dmnr.gui.AddonDragonMagicAndRelicsGuiInit;
import de.joh.fnc.compat.dmnr.item.AddonDragonMagicAndRelicsItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonDragonMagicAndRelics extends AddonCompatibleMod {
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
        AddonDragonMagicAndRelicsItems.ITEMS.register(modBus);

        modBus.register(AddonDragonMagicAndRelicsContainerInit.class);
        modBus.register(AddonDragonMagicAndRelicsGuiInit.class);
        modBus.register(AddonDragonMagicAndRelicsArmorUpgradeInit.class);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modBus.register(AddonDragonMagicAndRelicsClientModEvents.class));

        IEventBus forge_bus = MinecraftForge.EVENT_BUS;
        forge_bus.register(AddonDragonMagicAndRelicsCommonHandler.class);
    }

}
