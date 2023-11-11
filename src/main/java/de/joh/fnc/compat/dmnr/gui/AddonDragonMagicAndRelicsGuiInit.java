package de.joh.fnc.compat.dmnr.gui;

import de.joh.dragonmagicandrelics.gui.GuiDragonMageArmor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class AddonDragonMagicAndRelicsGuiInit {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        MenuScreens.register(AddonDragonMagicAndRelicsContainerInit.MISCHIEF_DRAGON_MAGE_CHESTPLATE, GuiDragonMageArmor<ContainerMischiefDragonMageArmor>::new);
        //HUDOverlayRenderer.instance = new HUDOverlayRenderer();
    }
}