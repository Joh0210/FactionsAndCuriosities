package de.joh.fnc.api.spelladjustment;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.base.ISpellDefinition;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.api.event.PerformSpellAdjustmentEvent;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.util.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Random;

import static com.mojang.text2speech.Narrator.LOGGER;

/**
 * Helper Functions for using Spell Adjustments
 * @author Joh0210
 */
public class SpellAdjustmentHelper {
    /**
     * DO NOT CALL!
     * <br> Only use getAllSpellAdjustment()!
     */
    private static SpellAdjustment[] allSpellAdjustment = null;

    /**
     * @return an array with all registered Spell Adjustments
     */
    public static SpellAdjustment[] getAllSpellAdjustment(){
        if(allSpellAdjustment == null){
            allSpellAdjustment = Registries.SPELL_ADJUSTMENTS.get().getValues().toArray(new SpellAdjustment[0]);
        }

        return allSpellAdjustment;
    }

    /**
     * IMPORTANT: Use only on Server Level!
     * @param rolls                 how often should be rolled. (Minimum of 1)
     * @param chooseHigherQuality   in case of multiple rolls, should the better Wild Magic be chosen?
     * @param spellCastEvent        Spell Event that should be adjusted?
     * @param spellAdjustmentFilter An additional Condition, the Spell Adjustment must fulfill, in order to be selected & performed.
     * @return a Random Wild Magic
     */
    public static @Nullable SpellAdjustment getRandomSpellAdjustment(int rolls, boolean chooseHigherQuality, @NotNull SpellCastEvent spellCastEvent, @NotNull SpellAdjustmentFilters spellAdjustmentFilter){
        Random random = new Random();
        SpellAdjustment[] spellAdjustments = Arrays.stream(getAllSpellAdjustment()).filter(rs -> rs.canBePerformed(spellCastEvent) && spellAdjustmentFilter.condition(rs, spellCastEvent.getSource().getCaster(), spellCastEvent.getSpell())).toArray(SpellAdjustment[]::new);
        int weightedListLength = Arrays.stream(spellAdjustments).mapToInt(rs -> rs.frequency).sum();

        if(weightedListLength < 1){
            return null;
        }


        SpellAdjustment ret = null;
        for(int i = 0; i < Math.max(rolls, 1); i++){
            if(ret == null) {
                ret = getSpellAdjustmentAt(random.nextInt(weightedListLength) + 1, spellAdjustments);
            } else {
                SpellAdjustment alternative = getSpellAdjustmentAt(random.nextInt(weightedListLength) + 1, spellAdjustments);

                if(alternative.getQuality(spellCastEvent.getSpell().getComponent(0).getPart().getUseTag()).ordinal() > ret.getQuality(spellCastEvent.getSpell().getComponent(0).getPart().getUseTag()).ordinal() == chooseHigherQuality){
                    ret = alternative;
                }
            }
        }

        return ret;
    }

    /**
     * @param count: Starting at 1, position of Spell Adjustment weighted list in which each Spell Adjustment appears as often as its frequency corresponds
     * @param spellAdjustments: List of the filtered Wild Magics to choose from (not weighted)
     * @return Selected Wild Magic
     */
    private static SpellAdjustment getSpellAdjustmentAt(int count, SpellAdjustment[] spellAdjustments){
        //todo: min size = 1!

        if(count < 1){
            LOGGER.error(FactionsAndCuriosities.MOD_ID + ": When attempting to select spell adjustment, a value less than 1 was selected. This value is invalid, so no magic can be selected. If this error occurs again, please contact the modder. ");
            return SpellAdjustment.INSTANCE;
        }

        for(SpellAdjustment spellAdjustment : spellAdjustments){
            count -= spellAdjustment.frequency;
            if(count <= 0){
                return spellAdjustment;
            }
        }

        LOGGER.error(FactionsAndCuriosities.MOD_ID + ": When attempting to select spell adjustment, a value greater than the maximum was selected. This value is invalid, so no magic can be selected. If this error occurs again, please contact the modder. ");
        return SpellAdjustment.INSTANCE;
    }

    /**
     * Performs the given Spell Adjustment, and posts the PerformSpellAdjustmentEvent on the Forge Event-Bus,
     * which could be canceled, so the Wild Magic will not be performed
     * @return false if canceled or if it could not be performed
     */
    public static boolean performSpellAdjustment(@NotNull SpellAdjustment spellAdjustment, @NotNull SpellCastEvent spellCastEvent){
        if(spellAdjustment.canBePerformed(spellCastEvent) && spellCastEvent.getSource().getCaster() instanceof Player){
            PerformSpellAdjustmentEvent event = new PerformSpellAdjustmentEvent(spellAdjustment, spellCastEvent, (Player) spellCastEvent.getSource().getCaster(), spellCastEvent.getSpell().getComponent(0).getPart().getUseTag());
            MinecraftForge.EVENT_BUS.post(event);
            if(event.isCanceled()) {
                return false;
            }
            spellAdjustment.performSpellAdjustment(spellCastEvent);

            return true;
        }
        return false;
    }

    /**
     * @param spellCastEvent        Spell Event that should be adjusted?
     * @param spellAdjustmentFilter An additional Condition, the Spell Adjustment must fulfill, in order to be selected & performed.
     * @return true if the Wild Magic was performed
     */
    public static boolean performRandomSpellAdjustment(@NotNull SpellCastEvent spellCastEvent, @NotNull SpellAdjustmentFilters spellAdjustmentFilter){
        if(!spellCastEvent.getSource().getCaster().level().isClientSide()){
            int wildMagicLuck = WildMagicHelper.getWildMagicLuck(spellCastEvent.getSource().getCaster());
            SpellAdjustment spellAdjustment = getRandomSpellAdjustment(
                    Math.abs(wildMagicLuck) + 1,
                    wildMagicLuck >= 0,
                    spellCastEvent,
                    spellAdjustmentFilter);

            if(spellAdjustment == null){
                //todo: There is no performable wild magic that meets all the conditions. Write to Log?
                return false;
            }

            return performSpellAdjustment(spellAdjustment, spellCastEvent);
        }

        return false;
    }

    /**
     * An additional Condition, the Spell Adjustment must fulfill, in order to be selected & performed by {@link SpellAdjustmentHelper#performRandomSpellAdjustment(SpellCastEvent, SpellAdjustmentFilters) performRandomSpellAdjustment()}
     * @author Joh0210
     */
    public interface SpellAdjustmentFilters {
        /**
         * @return true if the spellAdjustment should potentially be selected & performed by {@link SpellAdjustmentHelper#performRandomSpellAdjustment(SpellCastEvent, SpellAdjustmentFilters) performRandomSpellAdjustment()}
         */
        boolean condition(@NotNull SpellAdjustment spellAdjustment, @NotNull LivingEntity caster, @NotNull ISpellDefinition spell);
    }
}
