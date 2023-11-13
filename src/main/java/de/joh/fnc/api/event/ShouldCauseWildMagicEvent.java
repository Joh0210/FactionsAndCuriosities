package de.joh.fnc.api.event;

import com.mna.api.ManaAndArtificeMod;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.utils.CommonConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShouldCauseWildMagicEvent extends LivingEvent {
    private boolean autoFail;
    private int chance;

    public ShouldCauseWildMagicEvent(LivingEntity entity) {
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

    public void automaticSuccess(){
        autoFail = false;
        chance = 100;
    }

    public void setChance(int chance) {
        autoFail = false;
        this.chance = chance;
    }

    public void addChance(int increase) {
        autoFail = false;
        this.chance += chance;
    }

    public void setAutoFail(boolean autoFail) {
        this.autoFail = autoFail;
    }

    public boolean shouldCauseWildMagic(){
        if(autoFail){
            return false;
        }

        Random random = new Random();
        return random.nextInt(100) < chance;
    }
}
