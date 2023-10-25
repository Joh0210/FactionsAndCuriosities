package de.joh.fnc.utils;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.event.handler.MagicEventHandler;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AttributeInit {
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.Keys.ATTRIBUTES, FactionsAndCuriosities.MOD_ID);

    /**
     * Attribute for all Living Entities.
     * <br>When determining the Wild Magic entry for an entity, the dice are rolled as many times as the amount of this attribute + 1.
     * <br>If the value of this Attribute is positive, the better Wild Magic will be chosen. Otherwise, the worse.
     * @see MagicEventHandler
     */
    public static final RegistryObject<Attribute> WILD_MAGIC_LUCK = ATTRIBUTES.register("wild_magic_luck", () -> new RangedAttribute("attribute.description.fnc.wild_magic_luck", 0.0D, -1024.0D, 1024.0D).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
