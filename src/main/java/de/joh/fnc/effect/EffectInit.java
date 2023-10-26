package de.joh.fnc.effect;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.effect.beneficial.Empowered;
import de.joh.fnc.effect.beneficial.ExplosionResistance;
import de.joh.fnc.effect.beneficial.GoodWildMagic;
import de.joh.fnc.effect.beneficial.Maximized;
import de.joh.fnc.effect.harmful.BadWildMagic;
import de.joh.fnc.effect.harmful.LivingBomb;
import de.joh.fnc.effect.harmful.Minimized;
import de.joh.fnc.effect.neutral.WildMagic;
import de.joh.fnc.effect.neutral.WildMagicCooldown;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Inits of all mod effects.
 * @author Joh0210
 */
public class EffectInit {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FactionsAndCuriosities.MOD_ID);


    //neutral
    public static final RegistryObject<MobEffect> WILD_MAGIC_COOLDOWN = EFFECTS.register("wild_magic_cooldown", WildMagicCooldown::new);
    public static final RegistryObject<MobEffect> WILD_MAGIC = EFFECTS.register("wild_magic", WildMagic::new);
    public static final RegistryObject<MobEffect> BAD_WILD_MAGIC = EFFECTS.register("bad_wild_magic", BadWildMagic::new);
    public static final RegistryObject<MobEffect> GOOD_WILD_MAGIC = EFFECTS.register("good_wild_magic", GoodWildMagic::new);
    public static final RegistryObject<MobEffect> EXPLOSION_RESISTANCE = EFFECTS.register("explosion_resistance", ExplosionResistance::new);
    public static final RegistryObject<MobEffect> LIVING_BOMB = EFFECTS.register("living_bomb", LivingBomb::new);
    public static final RegistryObject<MobEffect> MAXIMIZED = EFFECTS.register("maximized", Maximized::new);
    public static final RegistryObject<MobEffect> EMPOWERED = EFFECTS.register("empowered", Empowered::new);
    public static final RegistryObject<MobEffect> MINIMIZED = EFFECTS.register("minimized", Minimized::new);

    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}
