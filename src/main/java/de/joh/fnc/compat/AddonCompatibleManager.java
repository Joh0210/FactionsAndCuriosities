package de.joh.fnc.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.joh.fnc.compat.dmnr.AddonDmnr;

/**
 * Manges the Compatibility with other Mods
 * @author Joh0210
 */
public class AddonCompatibleManager
{
    public static final List<AddonCompatibleMod> MODS;

    /**
     * <a href="https://github.com/Joh0210/DragonMagicAndRelics">Dragon Magic and Relics</a>
     */
    public static final AddonDmnr DMNR;

    static
    {
        List<AddonCompatibleMod> mods = new ArrayList<>();
        mods.add(DMNR = new AddonDmnr());

        for (AddonCompatibleMod mod : mods)
        {
            mod.tryLoad();
        }

        MODS = Collections.unmodifiableList(mods);
    }

    public static void visit()
    {

    }

}
