package de.joh.fnc.compat.dmnr.event;

import de.joh.dragonmagicandrelics.item.client.armor.DragonMageArmorRenderer;
import de.joh.fnc.compat.dmnr.item.MischiefDragonMageArmor;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AddonDragonMagicAndRelicsClientModEvents {
    @SubscribeEvent
    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(MischiefDragonMageArmor.class, DragonMageArmorRenderer::new);
    }
}
