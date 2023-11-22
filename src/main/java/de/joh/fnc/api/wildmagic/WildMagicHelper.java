package de.joh.fnc.api.wildmagic;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.AttributeInit;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.api.event.PerformWildMagicEvent;
import de.joh.fnc.common.util.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * @return an array with all registered Wild Magics
     */
    public static WildMagic[] getAllWildMagic(){
        if(allWildMagic == null){
            allWildMagic = Registries.WILD_MAGIC.get().getValues().toArray(new WildMagic[0]);
        }

        return allWildMagic;
    }

    /**
     * IMPORTANT: Use only on Server Level!
     * @param rolls                 how often should be rolled. (Minimum of 1)
     * @param chooseHigherQuality   in case of multiple rolls, should the better Wild Magic be chosen?
     * @param source                Source of the Wild Magic/Caster of the Spell
     * @param target                Target of the Spell
     * @param componentTag          What type of spell/event triggers the Wild Magic?
     * @param wildMagicFilters      An additional Condition, the Wild Magic must fulfill, in order to be selected & performed.
     * @return a Random Wild Magic
     */
    public static @Nullable WildMagic getRandomWildMagic(int rolls, boolean chooseHigherQuality, SpellPartTags componentTag, @NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull WildMagicHelper.WildMagicFilters wildMagicFilters){
        Random random = new Random();
        WildMagic[] wildMagics = Arrays.stream(getAllWildMagic()).filter(wm -> wm.canBePerformed(source, target) && wildMagicFilters.condition(wm, source, target, componentTag)).toArray(WildMagic[]::new);
        int weightedListLength = Arrays.stream(wildMagics).mapToInt(wm -> wm.frequency).sum();

        if(weightedListLength < 1){
            return null;
        }


        WildMagic ret = null;
        for(int i = 0; i < Math.max(rolls, 1); i++){
            if(ret == null) {
                ret = getWildMagicAt(random.nextInt(weightedListLength) + 1, wildMagics);
            } else {
                WildMagic alternative = getWildMagicAt(random.nextInt(weightedListLength) + 1, wildMagics);

                if(alternative.getQuality(componentTag).ordinal() > ret.getQuality(componentTag).ordinal() == chooseHigherQuality){
                    ret = alternative;
                }
            }
        }

        return ret;
    }

    /**
     * @param count: Starting at 1, position of Wild Magic weighted list in which each Wild Magic appears as often as its frequency corresponds
     * @param wildMagics: List of the filtered Wild Magics to choose from (not weighted)
     * @throws IllegalIcuArgumentException when count lower than 1 or higher than the number of entries
     * @return Selected Wild Magic
     */
    private static WildMagic getWildMagicAt(int count, WildMagic[] wildMagics){
        //todo: wildMagics min size = 1!

        if(count < 1){
            throw new IllegalIcuArgumentException("input count was lower than 1");
        }

        for(WildMagic wildMagic : wildMagics){
            count -= wildMagic.frequency;
            if(count <= 0){
                return wildMagic;
            }
        }

        throw new IllegalIcuArgumentException("input count was higher than the number of entries");
        //return WildMagic.INSTANCE;
    }

    /**
     * determent by {@link AttributeInit#WILD_MAGIC_LUCK WildMagicluckAttribute},
     * {@link EffectInit#BAD_WILD_MAGIC BadWildMagicEffect} and
     * {@link EffectInit#GOOD_WILD_MAGIC GodWildMagicEffect}
     * @return Positive value = good luck; Negative = bad luck; Amount = strength of luck
     */
    public static int getWildMagicLuck(LivingEntity entity){
        //todo: Use Event instead
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

    /**
     * Performs the given Wild Magic, and posts the PerformWildMagicEvent on the Forge Event-Bus,
     * which could be canceled, so the Wild Magic will not be performed
     * @return false if canceled or if it could not be performed
     */
    public static boolean performWildMagic(@NotNull WildMagic wildMagic, @NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags componentTag, boolean cancelable){
        if(wildMagic.canBePerformed(source, target)){
            PerformWildMagicEvent event = new PerformWildMagicEvent(source, target, wildMagic, componentTag, cancelable);
            MinecraftForge.EVENT_BUS.post(event);
            if(event.isCanceled() && cancelable) {
                return false;
            }
            wildMagic.performWildMagic(source, target, componentTag);
            return true;
        }
        return false;
    }

    /**
     * @param source            Source of the Wild Magic/Caster of the Spell
     * @param target            Target of the Spell
     * @param componentTag      What type of spell/event triggers the Wild Magic?
     * @param wildMagicFilters  An additional Condition, the Wild Magic must fulfill, in order to be selected & performed.
     * @return true if the Wild Magic was performed
     */
    public static boolean performRandomWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags componentTag, @NotNull WildMagicFilters wildMagicFilters, boolean cancelable){
        if(!source.getLevel().isClientSide()){
            int wildMagicLuck = getWildMagicLuck(source);
            WildMagic wildMagic = getRandomWildMagic(
                        Math.abs(wildMagicLuck) + 1,
                        wildMagicLuck >= 0,
                        componentTag,
                        source,
                        target,
                    wildMagicFilters);

            if(wildMagic == null){
                //todo: There is no performable wild magic that meets all the conditions. Write to Log?
                return false;
            }

            return performWildMagic(wildMagic, source, target, componentTag, cancelable);
        }

        return false;
    }

    public static boolean performRandomWildMagic(@NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags componentTag, @NotNull WildMagicFilters wildMagicFilters){
        return performRandomWildMagic(source, target, componentTag, wildMagicFilters, true);
    }

    /**
     * An additional Condition, the Wild Magic must fulfill, in order to be selected & performed by {@link WildMagicHelper#performRandomWildMagic(LivingEntity, SpellTarget, SpellPartTags, WildMagicFilters) performRandomWildMagic()}
     * @author Joh0210
     */
    public interface WildMagicFilters {
        /**
         * @return true if the wildMagic should potentially be selected & performed by {@link WildMagicHelper#performRandomWildMagic(LivingEntity, SpellTarget, SpellPartTags, WildMagicFilters) performRandomWildMagic()}
         */
        boolean condition(@NotNull WildMagic wildMagic, @NotNull LivingEntity source, @Nullable SpellTarget target, @NotNull SpellPartTags componentTag);
    }
}
