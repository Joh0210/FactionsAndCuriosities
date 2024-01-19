package de.joh.fnc.compat.dmnr;

import de.joh.dmnr.DragonMagicAndRelics;
import de.joh.fnc.common.init.CreativeModeTabInit;
import de.joh.fnc.compat.AddonCompatibleMod;
import de.joh.fnc.compat.dmnr.common.event.AddonDmnrDamageEventHandler;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrArmorUpgradeInit;
import de.joh.fnc.compat.dmnr.common.event.AddonDmnrCommonEventHandler;
import de.joh.fnc.compat.dmnr.client.init.AddonDmnrContainerInit;
import de.joh.fnc.compat.dmnr.client.init.AddonDmnrGuiInit;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrItemInit;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrWildMagicInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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
        AddonDmnrItemInit.ITEMS.register(modBus);
        AddonDmnrContainerInit.CONTAINERS.register(modBus);

        modBus.register(AddonDmnrContainerInit.class);
        modBus.register(AddonDmnrGuiInit.class);
        modBus.register(AddonDmnrArmorUpgradeInit.class);
        modBus.register(AddonDmnrWildMagicInit.class);
        modBus.addListener(this::addCreative);

        IEventBus forge_bus = MinecraftForge.EVENT_BUS;
        forge_bus.register(AddonDmnrCommonEventHandler.class);
        forge_bus.register(AddonDmnrDamageEventHandler.class);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabInit.FNC_TAB.getKey()) {
            event.accept(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_HELMET);
            event.accept(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_CHESTPLATE);
            event.accept(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_LEGGING);
            event.accept(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_BOOTS);

            event.accept(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_HELMET);
            event.accept(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_CHESTPLATE);
            event.accept(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_LEGGING);
            event.accept(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_BOOTS);
        }

        if(event.getTabKey() == de.joh.dmnr.common.init.CreativeModeTabInit.ARMOR_UPGRADE_TAB.getKey()) {
            event.accept(AddonDmnrItemInit.UPGRADE_SEAL_WILD_MAGIC_LUCK);
            event.accept(AddonDmnrItemInit.UPGRADE_SEAL_RANDOM_SPELL_ADJUSTMENT);
            event.accept(AddonDmnrItemInit.UPGRADE_SEAL_MAGIC_RESISTANCE);
            event.accept(AddonDmnrItemInit.UPGRADE_SEAL_SMITE_DURATION);
        }
    }

}
