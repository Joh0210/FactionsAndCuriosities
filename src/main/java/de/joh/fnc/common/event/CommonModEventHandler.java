package de.joh.fnc.common.event;

import com.mna.api.entities.FactionRaidRegistry;
import com.mna.entities.EntityInit;
import com.mna.factions.Factions;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.api.util.AttributeInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.HashMap;

/**
 * Handler for Mod-Events that take place on the Server and Client
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEventHandler {
    /**
     * Sets the Faction Raid Mobs for Paladins and Wild Mages
     */
    @SubscribeEvent
    public static void loadCompleteEventHandler(FMLLoadCompleteEvent event) {
        FactionRaidRegistry.registerSoldier(FactionInit.PALADIN, EntityInit.SPELLBREAKER.get(), new HashMap<>() {
            {
                this.put(30, 0);
                this.put(65, 1);
                this.put(100, 2);
            }
        });
        FactionRaidRegistry.registerSoldier(FactionInit.PALADIN, EntityInit.WITCH_HUNTER.get(), new HashMap<>() {
            {
                this.put(15, 0);
                this.put(45, 1);
                this.put(75, 2);
            }
        });
        FactionRaidRegistry.registerSoldier(FactionInit.PALADIN, EntityInit.LIVING_WARD.get(), new HashMap<>() {
            {
                this.put(25, 0);
                this.put(35, 1);
                this.put(45, 2);
            }
        });
        FactionRaidRegistry.registerSoldier(FactionInit.WILD, EntityInit.BARKLING.get(), new HashMap<>() {
            {
                this.put(10, 0);
                this.put(25, 1);
                this.put(40, 2);
            }
        });
        FactionRaidRegistry.registerSoldier(FactionInit.WILD, EntityInit.MUSHROOM_SOLDIER.get(), new HashMap<>() {
            {
                this.put(10, 0);
                this.put(45, 1);
                this.put(80, 2);
            }
        });
        FactionRaidRegistry.registerSoldier(FactionInit.WILD, EntityInit.PIXIE.get(), new HashMap<>() {
            {
                this.put(10, 0);
                this.put(35, 1);
                this.put(60, 2);
            }
        });
    }


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
