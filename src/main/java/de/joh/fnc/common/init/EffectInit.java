package de.joh.fnc.common.init;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.api.smite.SmiteMobEffect;
import de.joh.fnc.common.effect.beneficial.*;
import de.joh.fnc.common.effect.harmful.*;
import de.joh.fnc.common.effect.neutral.*;
import de.joh.fnc.common.effect.smite.*;
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

    // beneficial
    public static final RegistryObject<MobEffect> MAXIMIZED = EFFECTS.register("maximized", MaximizedMobEffect::new);
    public static final RegistryObject<MobEffect> GOOD_WILD_MAGIC = EFFECTS.register("good_wild_magic", GoodWildMagicMobEffect::new);
    public static final RegistryObject<MobEffect> EXPLOSION_RESISTANCE = EFFECTS.register("explosion_resistance", ExplosionResistanceMobEffect::new);
    public static final RegistryObject<MobEffect> EMPOWERED = EFFECTS.register("empowered", EmpoweredMobEffect::new);

    // neutral
    public static final RegistryObject<MobEffect> WILD_MAGIC_COOLDOWN = EFFECTS.register("wild_magic_cooldown", WildMagicCooldownMobEffect::new);
    public static final RegistryObject<MobEffect> WILD_MAGIC = EFFECTS.register("wild_magic", WildMagicMobEffect::new);
    public static final RegistryObject<MobEffect> RANDOM_SPELL_ADJUSTMENT = EFFECTS.register("random_spell_adjustment", RandomSpellAdjustmentMobEffect::new);
    public static final RegistryObject<MobEffect> RANDOM_ORE_MINER = EFFECTS.register("random_ore_miner", RandomOreMinerMobEffect::new);

    // harmful
    public static final RegistryObject<MobEffect> LIVING_BOMB = EFFECTS.register("living_bomb", LivingBombMobEffect::new);
    public static final RegistryObject<MobEffect> MINIMIZED = EFFECTS.register("minimized", MinimizedMobEffect::new);
    public static final RegistryObject<MobEffect> BAD_WILD_MAGIC = EFFECTS.register("bad_wild_magic", BadWildMagicMobEffect::new);

    // smite
    public static final RegistryObject<SmiteMobEffect> BURNING_SMITE = EFFECTS.register("burning_smite", BurningSmite::new);
    public static final RegistryObject<SmiteMobEffect> CRIPPLING_SMITE = EFFECTS.register("crippling_smite", CripplingSmite::new);
    public static final RegistryObject<SmiteMobEffect> DISPELLING_SMITE = EFFECTS.register("dispelling_smite", DispellingSmite::new);


    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
    }
}
