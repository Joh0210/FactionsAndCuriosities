package de.joh.fnc.wildmagic.util;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.utils.AttributeInit;
import de.joh.fnc.utils.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

/**
 * Helper Functions for using Wild Magic
 * @author Joh0210
 */
public class WildMagicHelper {
    /**
     * DO NOT CALL!
     * <br> Only use getAllWildMagic()!
     */
    private static WildMagic[] allWildMagic = null;

    /**
     * @return a list with all registered Wild Magics
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
    public static int getWeightedListLength(){
        return Arrays.stream(getAllWildMagic())
                .mapToInt(wm -> wm.frequency)
                .sum();
    }

    /**
     * @param count: Starting at 1, position of Wild Magic weighted list in which each Wild Magic appears as often as its frequency corresponds
     * @throws IllegalIcuArgumentException when count lower than 1 or higher than the number of entries
     * @return Selected Wild Magic
     */
    public static WildMagic getWildMagicAt(int count){
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
        return !entity.hasEffect(EffectInit.WILD_MAGIC_COOLDOWN.get()) && (entity.hasEffect(EffectInit.WILD_MAGIC.get()) || entity.hasEffect(EffectInit.BAD_WILD_MAGIC.get()) || entity.hasEffect(EffectInit.GOOD_WILD_MAGIC.get()));
    }
}
