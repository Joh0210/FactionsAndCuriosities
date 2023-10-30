package de.joh.fnc.event.handler;

import com.mna.api.events.ComponentApplyingEvent;
import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.parts.Shape;
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
 * Handler for Forge-Events that revolve around magic
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MagicEventHandler {
    /**
     * Handels {@link EffectInit#MAXIMIZED}, {@link EffectInit#MINIMIZED} and {@link EffectInit#EMPOWERED}
     */
    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent event){
        IModifiedSpellPart<Shape> shape = event.getSpell().getShape();

        int maximizedLevel = 0;
        if(event.getCaster().hasEffect(EffectInit.MAXIMIZED.get())){
            maximizedLevel += event.getCaster().getEffect(EffectInit.MAXIMIZED.get()).getAmplifier() + 1;
        }
        if(event.getCaster().hasEffect(EffectInit.MINIMIZED.get())){
            maximizedLevel -= event.getCaster().getEffect(EffectInit.MINIMIZED.get()).getAmplifier() + 1;
        }
        if(maximizedLevel > 0 && shape != null){
//            shape.getContainedAttributes().stream()
//                    .filter(attribute -> attribute != Attribute.DELAY)
//                    .forEach(attribute -> shape.setValue(attribute, shape.getMaximumValue(attribute)));

            event.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                    .filter(attribute -> attribute != Attribute.DELAY)
                    .forEach(attribute -> modifiedSpellPart.setValue(attribute, modifiedSpellPart.getMaximumValue(attribute))));
        } else if(maximizedLevel < 0 && shape != null){
//            shape.getContainedAttributes().stream()
//                    .filter(attribute -> attribute != Attribute.DELAY)
//                    .forEach(attribute -> shape.setValue(attribute, shape.getMinimumValue(attribute)));

            event.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                    .filter(attribute -> attribute != Attribute.DELAY)
                    .forEach(attribute -> modifiedSpellPart.setValue(attribute, modifiedSpellPart.getMinimumValue(attribute))));
        }

        if(event.getCaster().hasEffect(EffectInit.EMPOWERED.get()) && shape != null){
            int level = event.getCaster().getEffect(EffectInit.EMPOWERED.get()).getAmplifier() + 1;

//            shape.getContainedAttributes().stream()
//                    .filter(attribute -> attribute != Attribute.DELAY && attribute != Attribute.PRECISION)
//                    .forEach(attribute -> shape.setValue(attribute, shape.getValue(attribute) + level * shape.getStep(attribute)));

            event.getSpell().getComponents().forEach(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().stream()
                    .filter(attribute -> attribute != Attribute.DELAY && attribute != Attribute.PRECISION)
                    .forEach(attribute -> modifiedSpellPart.setValue(attribute, modifiedSpellPart.getValue(attribute) + level * modifiedSpellPart.getStep(attribute))));
        }
    }

    /**
     * Causes the Wild Magic, when a spell takes place.
     * <br> a small cooldown will be added.
     * <br>Determination of (dis-)advantage the Wild Magic Roll: {@link WildMagicHelper#getWildMagicLuck(LivingEntity) WildMagicHelper.getWildMagicLuck()}
     * @see WildMagicCooldown
     * @see WildMagic
     */
    @SubscribeEvent
    public static void onComponentApplying(ComponentApplyingEvent event){
        LivingEntity source = event.getSource().getPlayer();
        if(source != null && WildMagicHelper.shouldCauseWildMagic(source)){
            source.addEffect(new MobEffectInstance(EffectInit.WILD_MAGIC_COOLDOWN.get(), WildMagicCooldown.WILD_MAGIC_COOLDOWN, 0));
            WildMagicHelper.performRandomWildMagic(source, event.getTarget(), event.getComponent().getUseTag(), (wm, s, t, ct) -> true);
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
