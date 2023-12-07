package de.joh.fnc.common.ritual;

import com.mna.api.capabilities.IPlayerProgression;
import com.mna.api.rituals.IRitualContext;
import com.mna.api.rituals.RitualEffect;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

/**
 * Ritual for joining/leveling the wild courts.
 * @author Joh0210
 */
public class WildEnergyRitual extends RitualEffect {
    public WildEnergyRitual(ResourceLocation ritualName) {
        super(ritualName);
    }

    @Override
    public Component canRitualStart(IRitualContext context) {
        IPlayerProgression progression = context.getCaster().getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
        if (progression == null) {
            return Component.literal("Progression could not be found...this is a problem.");
        } else {
            if (progression.getTierProgress(context.getWorld()) < 1.0F) {
                return Component.translatable("ritual.mna.progression.not_ready");
            }

            if (progression != null && progression.hasAlliedFaction() && progression.getAlliedFaction() != FactionInit.WILD) {
                return Component.translatable("event.mna.faction_ritual_failed");
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
                    context.getCaster().displayClientMessage(Component.translatable("event.fnc.faction_ally_wild"), false);
                }

                if (progression.getAlliedFaction() == FactionInit.WILD) {
                    progression.setTier(progression.getTier() + 1, context.getCaster());
                    context.getCaster().displayClientMessage(Component.translatable("mna:progresscondition.advanced", progression.getTier()), false);
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
        Random random = new Random();

        for(int i = 0; i < 360; i += 3) {
            if(random.nextDouble() < 0.05f){
                context.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK,
                        context.getCenter().getX() + Math.cos(i) * 2 + 0.5,
                        context.getCenter().getY() + 2.1,
                        context.getCenter().getZ() + Math.sin(i) * 2 + 0.5,
                        Math.cos(i) * 3d,
                        0.15d,
                        Math.sin(i) * 3d);
            }
        }

        return true;
    }
}
