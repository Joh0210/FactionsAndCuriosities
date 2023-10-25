package de.joh.fnc.event.handler;

import com.mna.api.events.ComponentApplyingEvent;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.effect.neutral.WildMagicCooldown;
import de.joh.fnc.event.additional.PerformWildMagicEvent;
import de.joh.fnc.item.init.MischiefArmor;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagic;
import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler for Forge-Events that take place on the Server and Client
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MagicEventHandler {
    /**
     * Causes the Wild Magic, when a spell takes place.
     * <br> a small cooldown will be added.
     * <br>Determination of (dis-)advantage the Wild Magic Roll: {@link WildMagicHelper#getWildMagicLuck(LivingEntity) WildMagicHelper.getWildMagicLuck()}
     * @see WildMagicCooldown
     * @see WildMagic
     */
    @SubscribeEvent
    public static void onComponentApplying(ComponentApplyingEvent event){
        //todo: When the Target is a block, there should be a 50% chance, that it interacts with the target (50& chance to use a prefiltered list)
        //todo: When the Target is an Living Entity, there should be a 50% chance, that it interacts with the target (50& chance to use a prefiltered list)

        LivingEntity source = event.getSource().getPlayer();
        if(source != null && WildMagicHelper.shouldCauseWildMagic(source)){
            source.addEffect(new MobEffectInstance(EffectInit.WILD_MAGIC_COOLDOWN.get(), WildMagicCooldown.WILD_MAGIC_COOLDOWN, 0));
            WildMagicHelper.performRandomWildMagic(source, event.getTarget(), event.getComponent().getUseTag());
        }
    }

    @SubscribeEvent
    public static void onPerformWildMagic(PerformWildMagicEvent event){
        LivingEntity source = event.getSource();
        if(source.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof MischiefArmor mischiefArmor
                && mischiefArmor.isSetEquipped(source)
                && (event.getQuality() == Quality.VERY_BAD /*|| event.getQuality() == Quality.BAD*/)
        ){
            event.setCanceled(true);
            if(source instanceof Player){
                ((Player) source).displayClientMessage(new TranslatableComponent("fnc.feedback.wildmagic.accident_protection"), true);
            }
            source.level.playSound(null, source.getX(), source.getY(), source.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 0.9F + (float)Math.random() * 0.2F);
        }
    }
}
