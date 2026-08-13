package de.joh.fnc.common.ritual;

import com.mna.api.capabilities.IPlayerMagic;
import com.mna.api.capabilities.IPlayerProgression;
import com.mna.api.rituals.IRitualContext;
import com.mna.api.rituals.RitualEffect;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

/**
 * Ritual for joining/leveling the blood mage faction.
 * @author Joh0210
 */
public class CrimsonOfferingRitual extends RitualEffect {
    public CrimsonOfferingRitual(ResourceLocation ritualName) {
        super(ritualName);
    }

    @Override
    public Component canRitualStart(IRitualContext context) {
        Player caster = context.getCaster();
        if (caster == null) {
            return Component.literal("Caster could not be found.");
        }

        IPlayerProgression progression = caster.getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
        if (progression == null) {
            return Component.literal("Progression could not be found...this is a problem.");
        } else {
            if (progression.getTierProgress(context.getLevel()) < 1.0F) {
                return Component.translatable("ritual.mna.progression.not_ready");
            }

            if (progression.hasAlliedFaction() && progression.getAlliedFaction() != FactionInit.BLOOD_MAGE) {
                return Component.translatable("event.mna.faction_ritual_failed");
            }

            return null;
        }
    }

    @Override
    protected boolean applyRitualEffect(IRitualContext context) {
        Player caster = context.getCaster();
        if (caster != null) {
            IPlayerProgression progression = caster.getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
            IPlayerMagic magic = caster.getCapability(PlayerMagicProvider.MAGIC).orElse(null);
            if (progression != null && magic != null && progression.getTier() < 5) {
                if (progression.getAlliedFaction() == null) {
                    progression.setAlliedFaction(FactionInit.BLOOD_MAGE, caster);
                    caster.displayClientMessage(Component.translatable("event.fnc.faction_ally_blood_mage"), false);
                }

                if (progression.getAlliedFaction() == FactionInit.BLOOD_MAGE) {
                    progression.setTier(progression.getTier() + 1, caster);
                    caster.displayClientMessage(Component.translatable("mna:progresscondition.advanced", progression.getTier()), false);

                    magic.setCastingResourceType(FactionInit.BLOOD_MAGE.getCastingResource(caster));
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

        for(int i = 0; i < 360; i += 2) {
            double angle = Math.toRadians(i);
            if(random.nextDouble() < 0.1f){
                double x = context.getCenter().getX() + 0.5 + Math.cos(angle) * 2.5;
                double y = context.getCenter().getY() + 2.1;
                double z = context.getCenter().getZ() + 0.5 + Math.sin(angle) * 2.5;

                context.getLevel().addParticle(ParticleTypes.DAMAGE_INDICATOR,
                        x, y, z,
                        -Math.cos(angle) * 0.05,
                        0.02d,
                        -Math.sin(angle) * 0.05);
            }

            if(i % 30 == 0 && random.nextDouble() < 0.5f) {
                double x = context.getCenter().getX() + 0.5 + Math.cos(angle) * 1.5;
                double y = context.getCenter().getY() + 2.1;
                double z = context.getCenter().getZ() + 0.5 + Math.sin(angle) * 1.5;
                context.getLevel().addParticle(ParticleTypes.SOUL, x, y, z, 0, 0.05, 0);
            }
        }

        if (random.nextDouble() < 0.3f) {
            context.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME,
                    context.getCenter().getX() + 0.5 + (random.nextDouble() - 0.5) * 0.8,
                    context.getCenter().getY() + 2.1,
                    context.getCenter().getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.8,
                    0, 0.08, 0);
        }

        return true;
    }
}
