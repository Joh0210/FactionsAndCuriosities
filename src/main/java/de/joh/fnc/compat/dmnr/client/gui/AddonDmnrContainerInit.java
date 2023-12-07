package de.joh.fnc.compat.dmnr.client.gui;

import de.joh.fnc.FactionsAndCuriosities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.*;

public class AddonDmnrContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS;
    static final String MISCHIEF_DRAGON_MAGE_CHESTPLATE_ID = "fnc:mischief_dragon_mage_chestplate";
    @ObjectHolder(registryName = "menu", value = MISCHIEF_DRAGON_MAGE_CHESTPLATE_ID)
    public static final MenuType<ContainerMischiefDragonMageArmor> MISCHIEF_DRAGON_MAGE_CHESTPLATE;

    static final String DIVINE_DRAGON_MAGE_CHESTPLATE_ID = "fnc:divine_dragon_mage_chestplate";
    @ObjectHolder(registryName = "menu", value = DIVINE_DRAGON_MAGE_CHESTPLATE_ID)
    public static final MenuType<ContainerDivineDragonMageArmor> DIVINE_DRAGON_MAGE_CHESTPLATE;

    public AddonDmnrContainerInit() {
    }

    @SubscribeEvent
    public static void registerContainers(RegisterEvent event) {
        event.register(ForgeRegistries.MENU_TYPES.getRegistryKey(), (helper) -> {
            helper.register(new ResourceLocation(MISCHIEF_DRAGON_MAGE_CHESTPLATE_ID), new MenuType<>(ContainerMischiefDragonMageArmor::new));
            helper.register(new ResourceLocation(DIVINE_DRAGON_MAGE_CHESTPLATE_ID), new MenuType<>(ContainerDivineDragonMageArmor::new));
        });
    }

    static {
        CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, FactionsAndCuriosities.MOD_ID);
        MISCHIEF_DRAGON_MAGE_CHESTPLATE = null;
        DIVINE_DRAGON_MAGE_CHESTPLATE = null;
    }
}
