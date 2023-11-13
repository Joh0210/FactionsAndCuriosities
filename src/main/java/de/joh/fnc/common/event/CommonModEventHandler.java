package de.joh.fnc.common.event;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.api.util.AttributeInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler for Mod-Events that take place on the Server and Client
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEventHandler {

    /**
     * Registering of the Entity-Attributes
     * @see AttributeInit
     */
    @SubscribeEvent
    public static void onEntityAttributeModification(final EntityAttributeModificationEvent event){
        for(EntityType<? extends LivingEntity> entityType : event.getTypes()){
            event.add(entityType, AttributeInit.WILD_MAGIC_LUCK.get());
        }
    }
}
