package de.joh.fnc.wildmagic.util;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import de.joh.fnc.utils.Registries;
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
     * @param tries how often should be rolled. (Minimum of 1)
     * @param chooseHigherQuality in case of multiple rolls, schuld the better Wild Magic be chosen?
     * @return a Random Wild Magic
     */
    public static @NotNull WildMagic getRandomWildMagic(int tries, boolean chooseHigherQuality){
        Random random = new Random();
        WildMagic ret = null;
        for(int i = 0; i < Math.max(tries, 1); i++){
            if(ret == null) {
                ret = getWildMagicAt(random.nextInt(getWeightedListLength()) + 1);
            } else {
                WildMagic alternative = getWildMagicAt(random.nextInt(getWeightedListLength()) + 1);

                //todo: Check if this function works
                if(alternative.quality.ordinal() > ret.quality.ordinal() == chooseHigherQuality){
                    ret = alternative;
                }
            }
        }

        return ret;
    }
}
