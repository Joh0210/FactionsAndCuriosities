package de.joh.fnc.compat.dmnr.client.init;


import de.joh.dmnr.client.gui.GuiDragonMageArmor;
import de.joh.fnc.compat.dmnr.client.gui.ContainerDivineDragonMageArmor;
import de.joh.fnc.compat.dmnr.client.gui.ContainerMischiefDragonMageArmor;
import de.joh.fnc.compat.dmnr.client.init.AddonDmnrContainerInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class AddonDmnrGuiInit {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        MenuScreens.register(AddonDmnrContainerInit.MISCHIEF_DRAGON_MAGE_CHESTPLATE.get(), GuiDragonMageArmor<ContainerMischiefDragonMageArmor>::new);
        MenuScreens.register(AddonDmnrContainerInit.DIVINE_DRAGON_MAGE_CHESTPLATE.get(), GuiDragonMageArmor<ContainerDivineDragonMageArmor>::new);
        //HUDOverlayRenderer.instance = new HUDOverlayRenderer();
    }
}