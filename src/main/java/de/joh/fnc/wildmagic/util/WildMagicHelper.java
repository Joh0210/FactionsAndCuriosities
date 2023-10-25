package de.joh.fnc.wildmagic.util;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.mna.api.ManaAndArtificeMod;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.effect.neutral.WildMagicCooldown;
import de.joh.fnc.event.additional.PerformWildMagicEvent;
import de.joh.fnc.factions.FactionInit;
import de.joh.fnc.utils.AttributeInit;
import de.joh.fnc.utils.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Helper Functions for using Wild Magic
 * @author Joh0210
 */
public class WildMagicHelper {
    /**
     * This number defines how many tries should be made, to finde a random wildMagic, which can be performed.
     * <br> In case within the tries, no wild Magic can be found, none will occur.
     */
    private static final int TRIES = 10;

    /**
     * DO NOT CALL!
     * <br> Only use getAllWildMagic()!
     */
    private static WildMagic[] allWildMagic = null;

    /**
     * @return an array with all registered Wild Magics
     */
    public static WildMagic[] getAllWildMagic(){
        if(allWildMagic == null){
            allWildMagic = Registries.WILD_MAGIC.get().getValues().toArray(new WildMagic[0]);
        }

        return allWildMagic;
    }

    /**
     * @return Length of the weighted list in which each Wild Magic appears as often as its frequency corresponds
     */
    private static int getWeightedListLength(){
        return Arrays.stream(getAllWildMagic())
                .mapToInt(wm -> wm.frequency)
                .sum();
    }

    /**
     * @param count: Starting at 1, position of Wild Magic weighted list in which each Wild Magic appears as often as its frequency corresponds
     * @throws IllegalIcuArgumentException when count lower than 1 or higher than the number of entries
     * @return Selected Wild Magic
     */
    private static WildMagic getWildMagicAt(int count){
        if(count < 1){
            throw new IllegalIcuArgumentException("input count was lower than 1");
        }

        for(WildMagic wildMagic : getAllWildMagic()){
            count -= wildMagic.frequency;
            if(count <= 0){
                return wildMagic;
            }
        }

        throw new IllegalIcuArgumentException("input count was higher than the number of entries");
        //return WildMagic.INSTANCE;
    }

    /**
     * IMPORTANT: Use only on Server Level!
     * @param rolls how often should be rolled. (Minimum of 1)
     * @param chooseHigherQuality in case of multiple rolls, schuld the better Wild Magic be chosen?
     * @return a Random Wild Magic
     */
    public static @NotNull WildMagic getRandomWildMagic(int rolls, boolean chooseHigherQuality, SpellPartTags componentTag){
        Random random = new Random();
        WildMagic ret = null;
        for(int i = 0; i < Math.max(rolls, 1); i++){
            if(ret == null) {
                ret = getWildMagicAt(random.nextInt(getWeightedListLength()) + 1);
            } else {
                WildMagic alternative = getWildMagicAt(random.nextInt(getWeightedListLength()) + 1);

                //todo: Check if this function works
                if(alternative.getQuality(componentTag).ordinal() > ret.getQuality(componentTag).ordinal() == chooseHigherQuality){
                    ret = alternative;
                }
            }
        }

        return ret;
    }

    /**
     * determent by {@link AttributeInit#WILD_MAGIC_LUCK WildMagicluckAttribute},
     * {@link EffectInit#BAD_WILD_MAGIC BadWildMagicEffect} and
     * {@link EffectInit#GOOD_WILD_MAGIC GodWildMagicEffect}
     * @return Positive value = good luck; Negative = bad luck; Amount = strength of luck
     */
    public static int getWildMagicLuck(LivingEntity entity){
        //todo: Use Event instead
        //todo: build Uses more performant
        int wildMagicLuck = 0;

        AttributeInstance modifierAttribute = entity.getAttribute(AttributeInit.WILD_MAGIC_LUCK.get());
        if(modifierAttribute != null){
            wildMagicLuck += (int)modifierAttribute.getValue();
        }

        if(entity.hasEffect(EffectInit.BAD_WILD_MAGIC.get())){
            wildMagicLuck -= entity.getEffect(EffectInit.BAD_WILD_MAGIC.get()).getAmplifier() + 1;
        }

        if(entity.hasEffect(EffectInit.GOOD_WILD_MAGIC.get())){
            wildMagicLuck += entity.getEffect(EffectInit.GOOD_WILD_MAGIC.get()).getAmplifier() + 1;
        }

        return wildMagicLuck;
    }

    public static boolean shouldCauseWildMagic(LivingEntity entity){
        //todo: Use Event instead

        if(entity.hasEffect(EffectInit.WILD_MAGIC_COOLDOWN.get())){
            return false;
        }

        if(entity.hasEffect(EffectInit.WILD_MAGIC.get()) || entity.hasEffect(EffectInit.BAD_WILD_MAGIC.get()) || entity.hasEffect(EffectInit.GOOD_WILD_MAGIC.get())){
            return true;
        }


        AtomicBoolean isWildMage = new AtomicBoolean(false);
        entity.getCapability(ManaAndArtificeMod.getProgressionCapability()).ifPresent((p)-> isWildMage.set(p.getAlliedFaction() == FactionInit.WILD));
        return isWildMage.get();
    }

    public static boolean performWildMagic(@NotNull WildMagic wildMagic, @NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags componentTag){
        if(wildMagic.canBePerformed(source, target)){
            PerformWildMagicEvent event = new PerformWildMagicEvent(source, target, wildMagic, componentTag);
            MinecraftForge.EVENT_BUS.post(event);
            if(event.isCanceled()) {
                return false;
            }
            wildMagic.performWildMagic(source, target);
            return true;
        }
        return false;
    }


    public static void performRandomWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags componentTag, @NotNull ExtendedCondition extendedCondition){
        if(!source.getLevel().isClientSide()){
            source.addEffect(new MobEffectInstance(EffectInit.WILD_MAGIC_COOLDOWN.get(), WildMagicCooldown.WILD_MAGIC_COOLDOWN, 0));
            WildMagic wildMagic;
            int tries = 0;
            int wildMagicLuck = getWildMagicLuck(source);
            do{
                wildMagic = getRandomWildMagic(
                        Math.abs(wildMagicLuck) + 1,
                        wildMagicLuck >= 0,
                        componentTag);
                tries++;
            } while (!(wildMagic.canBePerformed(source, target) && extendedCondition.condition(wildMagic, source, target, componentTag)) && tries <= TRIES);

            performWildMagic(wildMagic, source, target, componentTag);
        }
    }

    public static void performRandomWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags componentTag){
        performRandomWildMagic(source, target, componentTag, (wm, s, t, ct) -> true);
    }

    public interface ExtendedCondition {
        boolean condition(@NotNull WildMagic wildMagic, @NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags componentTag);
    }
}
