package de.joh.fnc.event.handler;

import com.mna.api.events.ComponentApplyingEvent;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.effect.neutral.WildMagicCooldown;
import de.joh.fnc.wildmagic.util.WildMagic;
import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler for Events that take place on the Server and Client
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
     * @see WildMagicCooldown
     * @see WildMagic
     */
    @SubscribeEvent
    public static void onComponentApplying(ComponentApplyingEvent event){
        LivingEntity source = event.getSource().getPlayer();
        if(source != null && !source.hasEffect(EffectInit.WILD_MAGIC_COOLDOWN.get()) && !source.getLevel().isClientSide()){
            source.addEffect(new MobEffectInstance(EffectInit.WILD_MAGIC_COOLDOWN.get(), WildMagicCooldown.WILD_MAGIC_COOLDOWN, 0));
            WildMagic wildMagic;
            int tries = 0;
            do{
                wildMagic = WildMagicHelper.getRandomWildMagic(1, true, event.getComponent().getUseTag());
                tries++;
            } while (!wildMagic.canBePerformed(source, event.getTarget()) && tries <= TRIES);

            if(wildMagic.canBePerformed(source, event.getTarget())){
                //todo: cast new Wild-Magic-Event
                wildMagic.performWildMagic(source, event.getTarget());
            }
        }
    }
}
