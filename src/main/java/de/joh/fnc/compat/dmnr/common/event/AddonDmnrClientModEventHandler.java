package de.joh.fnc.compat.dmnr.common.event;

import de.joh.dmnr.client.item.armor.DragonMageArmorRenderer;
import de.joh.fnc.compat.dmnr.common.item.DivineDragonMageArmorItem;
import de.joh.fnc.compat.dmnr.common.item.MischiefDragonMageArmorItem;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AddonDmnrClientModEventHandler {
    @SubscribeEvent
    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(MischiefDragonMageArmorItem.class, DragonMageArmorRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(DivineDragonMageArmorItem.class, DragonMageArmorRenderer::new);
    }
}
