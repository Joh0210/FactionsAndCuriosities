package de.joh.fnc.api.event;

import com.mna.api.ManaAndArtificeMod;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This event is fired when Wild Magic could occur.
 * <br>By default, the entity in question must either have one of the Wild Magic mob effects
 * ({@link EffectInit#WILD_MAGIC Wild Magic}, {@link EffectInit#BAD_WILD_MAGIC Bad Wild Magic}, {@link EffectInit#GOOD_WILD_MAGIC Good Wild Magic})
 * or be part of the {@link FactionInit#WILD Wild Magic Faction} so that Wild Magic could occur.
 * <br>If the entity has the {@link EffectInit#WILD_MAGIC_COOLDOWN wild magic cooldown} mob effect, no wild magic effect occurs by default.
 * <br>However, all chaces can be overwritten.
 * <br><br>This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 * @see WildMagicHelper
 * @see PerformWildMagicEvent
 * @author Joh0210
 */
public class ShouldCauseWildMagicEvent extends LivingEvent {
    /**
     * If true, wild magic cannot be caused.
     */
    private boolean autoFail;

    /**
     * Chance (0-100) if Wild Magic occurs:
     * <br> - Wild Magic mob effects ({@link EffectInit#WILD_MAGIC Wild Magic}, {@link EffectInit#BAD_WILD_MAGIC Bad Wild Magic}, {@link EffectInit#GOOD_WILD_MAGIC Good Wild Magic}): 50%
     * <br> - {@link FactionInit#WILD Wild Magic Faction}: 10%
     * <br> - None: 0%
     */
    private int chance;

    public ShouldCauseWildMagicEvent(@NotNull LivingEntity entity) {
        super(entity);

        if(entity.hasEffect(EffectInit.WILD_MAGIC_COOLDOWN.get())){
            this.autoFail = true;
            this.chance = 0;
            return;
        }

        if(entity.hasEffect(EffectInit.WILD_MAGIC.get()) || entity.hasEffect(EffectInit.BAD_WILD_MAGIC.get()) || entity.hasEffect(EffectInit.GOOD_WILD_MAGIC.get())){
            this.autoFail = false;
            this.chance = CommonConfig.FORCED_WILD_MAGIC_CHANCE.get();
            return;
        }

        AtomicBoolean isWildMage = new AtomicBoolean(false);
        entity.getCapability(ManaAndArtificeMod.getProgressionCapability()).ifPresent((p)-> isWildMage.set(p.getAlliedFaction() == FactionInit.WILD));
        if(isWildMage.get()){
            this.autoFail = false;
            this.chance = CommonConfig.WILD_MAGIC_CHANCE.get();
            return;
        }

        autoFail = true;
        this.chance = 0;
    }

    public void setChance(int chance) {
        autoFail = false;
        this.chance = chance;
    }

    public void addChance(int increase) {
        autoFail = false;
        this.chance += chance;
    }

    public void automaticSuccess(){
        autoFail = false;
        chance = 100;
    }

    public void setAutoFail(boolean autoFail) {
        this.autoFail = autoFail;
    }

    /**
     * The chance that true occurs corresponds to the {@link ShouldCauseWildMagicEvent#chance this.chane}
     * <br>If {@link ShouldCauseWildMagicEvent#autoFail this.autoFail} == ture, the result will always be false
     * @return if true Wild Magic is triggered
     */
    public boolean shouldCauseWildMagic(){
        if(autoFail){
            return false;
        }

        Random random = new Random();
        return random.nextInt(100) < chance;
    }
}
