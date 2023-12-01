package de.joh.fnc.compat.dmnr.client.gui;

import de.joh.fnc.FactionsAndCuriosities;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class AddonDmnrContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS;
    static final String MISCHIEF_DRAGON_MAGE_CHESTPLATE_ID = "fnc:mischief_dragon_mage_chestplate";
    @ObjectHolder(MISCHIEF_DRAGON_MAGE_CHESTPLATE_ID)
    public static final MenuType<ContainerMischiefDragonMageArmor> MISCHIEF_DRAGON_MAGE_CHESTPLATE;

    static final String DIVINE_DRAGON_MAGE_CHESTPLATE_ID = "fnc:divine_dragon_mage_chestplate";
    @ObjectHolder(DIVINE_DRAGON_MAGE_CHESTPLATE_ID)
    public static final MenuType<ContainerDivineDragonMageArmor> DIVINE_DRAGON_MAGE_CHESTPLATE;

    public AddonDmnrContainerInit() {
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<MenuType<?>> event) {
        IForgeRegistry<MenuType<?>> r = event.getRegistry();
        r.register((new MenuType<>(ContainerMischiefDragonMageArmor::new)).setRegistryName(MISCHIEF_DRAGON_MAGE_CHESTPLATE_ID));
        r.register((new MenuType<>(ContainerDivineDragonMageArmor::new)).setRegistryName(DIVINE_DRAGON_MAGE_CHESTPLATE_ID));
    }

    static {
        CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, FactionsAndCuriosities.MOD_ID);
        MISCHIEF_DRAGON_MAGE_CHESTPLATE = null;
        DIVINE_DRAGON_MAGE_CHESTPLATE = null;
    }
}
