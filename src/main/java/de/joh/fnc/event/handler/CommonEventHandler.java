package de.joh.fnc.event.handler;

import com.mna.api.events.ComponentApplyingEvent;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.effect.neutral.WildMagicCooldown;
import de.joh.fnc.utils.AttributeInit;
import de.joh.fnc.wildmagic.util.WildMagic;
import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler for Forge-Events that take place on the Server and Client
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {
    /**
     * This number defines how many tries should be made, to finde a random wildMagic, which can be performed.
     * <br> In case within the tries, no wild Magic can be found, none will occur.
     */
    private static final int TRIES = 10;

    /**
     * Causes the Wild Magic, when a spell takes place.
     * <br> a small cooldown will be added.
     * <br>Determination of (dis-)advantage the Wild Magic Roll: {@link AttributeInit#WILD_MAGIC_LUCK link}
     * @see WildMagicCooldown
     * @see WildMagic
     */
    @SubscribeEvent
    public static void onComponentApplying(ComponentApplyingEvent event){
        //todo: When the Target is a block, there should be a 50% chance, that it interacts with the target (50& chance to use a prefiltered list)
        //todo: When the Target is an Living Entity, there should be a 50% chance, that it interacts with the target (50& chance to use a prefiltered list)

        LivingEntity source = event.getSource().getPlayer();
        if(source != null && !source.hasEffect(EffectInit.WILD_MAGIC_COOLDOWN.get()) && !source.getLevel().isClientSide()){
            AttributeInstance modifierAttribute = source.getAttribute(AttributeInit.WILD_MAGIC_LUCK.get());

            source.addEffect(new MobEffectInstance(EffectInit.WILD_MAGIC_COOLDOWN.get(), WildMagicCooldown.WILD_MAGIC_COOLDOWN, 0));
            WildMagic wildMagic;
            int tries = 0;
            do{
                wildMagic = WildMagicHelper.getRandomWildMagic(
                        modifierAttribute != null ? Math.abs((int)modifierAttribute.getValue()) + 1 : 1,
                        modifierAttribute == null || modifierAttribute.getValue() >= 0,
                        event.getComponent().getUseTag());
                tries++;
            } while (!wildMagic.canBePerformed(source, event.getTarget()) && tries <= TRIES);

            if(wildMagic.canBePerformed(source, event.getTarget())){
                //todo: cast new Wild-Magic-Event
                wildMagic.performWildMagic(source, event.getTarget());
            }
        }
    }
}
