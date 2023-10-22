package de.joh.fnc.rituals.effects;

import com.mna.api.capabilities.IPlayerProgression;
import com.mna.api.rituals.IRitualContext;
import com.mna.api.rituals.RitualEffect;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import de.joh.fnc.factions.FactionInit;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Ritual for joining/leveling the wild courts.
 * @author Joh0210
 */
public class WildEnergy extends RitualEffect {
    public WildEnergy(ResourceLocation ritualName) {
        super(ritualName);
    }

    @Override
    public Component canRitualStart(IRitualContext context) {
        IPlayerProgression progression = context.getCaster().getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
        if (progression == null) {
            return new TextComponent("Progression could not be found...this is a problem.");
        } else {
            if (progression.getTierProgress(context.getWorld()) < 1.0F) {
                return new TranslatableComponent("ritual.mna.progression.not_ready");
            }

            if (progression != null && progression.hasAlliedFaction() && progression.getAlliedFaction() != FactionInit.WILD) {
                return new TranslatableComponent("event.mna.faction_ritual_failed");
            }

            return null;
        }
    }

    @Override
    protected boolean applyRitualEffect(IRitualContext context) {
        if (context.getCaster() != null) {
            IPlayerProgression progression = context.getCaster().getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
            if (progression != null && progression.getTier() < 5) {
                if (progression.getAlliedFaction() == null) {
                    progression.setAlliedFaction(FactionInit.WILD, context.getCaster());
                    context.getCaster().sendMessage(new TranslatableComponent("event.fnc.faction_ally_wild"), Util.NIL_UUID);
                }

                if (progression.getAlliedFaction() == FactionInit.WILD) {
                    progression.setTier(progression.getTier() + 1, context.getCaster());
                    context.getCaster().sendMessage(new TranslatableComponent("mna:progresscondition.advanced", progression.getTier()), Util.NIL_UUID);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected int getApplicationTicks(IRitualContext iRitualContext) {
        return 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean spawnRitualParticles(IRitualContext context) {
        for(int i = 0; i < 360; i += 3) {
            if(i % 10 == 0){
                context.getWorld().addParticle(ParticleTypes.ENCHANT,
                        context.getCenter().getX() + 0.5,
                        context.getCenter().getY() + 2.1,
                        context.getCenter().getZ() + 0.5,
                        Math.cos(i) * 3d,
                        0.15d,
                        Math.sin(i) * 3d);
            }
        }

        return true;
    }
}
