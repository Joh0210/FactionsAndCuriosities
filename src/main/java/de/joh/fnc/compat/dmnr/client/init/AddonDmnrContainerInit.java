package de.joh.fnc.compat.dmnr.client.init;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.compat.dmnr.client.gui.ContainerDivineDragonMageArmor;
import de.joh.fnc.compat.dmnr.client.gui.ContainerMischiefDragonMageArmor;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddonDmnrContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS;
    static final String MISCHIEF_DRAGON_MAGE_CHESTPLATE_ID = "mischief_dragon_mage_chestplate";
    public static final RegistryObject<MenuType<ContainerMischiefDragonMageArmor>>MISCHIEF_DRAGON_MAGE_CHESTPLATE;

    static final String DIVINE_DRAGON_MAGE_CHESTPLATE_ID = "divine_dragon_mage_chestplate";
    public static final RegistryObject<MenuType<ContainerDivineDragonMageArmor>> DIVINE_DRAGON_MAGE_CHESTPLATE;

    public AddonDmnrContainerInit() {
    }

    static <T extends Block> String of(RegistryObject<T> block) {
        return block.getId().getPath();
    }

    static {
        CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, FactionsAndCuriosities.MOD_ID);

        MISCHIEF_DRAGON_MAGE_CHESTPLATE = CONTAINERS.register(MISCHIEF_DRAGON_MAGE_CHESTPLATE_ID,
                () -> IForgeMenuType.create(ContainerMischiefDragonMageArmor::new));

        DIVINE_DRAGON_MAGE_CHESTPLATE = CONTAINERS.register(DIVINE_DRAGON_MAGE_CHESTPLATE_ID,
                () -> IForgeMenuType.create(ContainerDivineDragonMageArmor::new));
    }

    public static void register(IEventBus eventBus){
        CONTAINERS.register(eventBus);
    }
}
