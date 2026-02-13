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
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Ritual for joining/leveling the wild courts.
 * @author Joh0210
 */
public class PactRitual extends RitualEffect {
    public PactRitual(ResourceLocation ritualName) {
        super(ritualName);
    }

    @Override
    public Component canRitualStart(IRitualContext context) {
        IPlayerProgression progression = context.getCaster().getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
        if (progression == null) {
            return Component.literal("Progression could not be found...this is a problem.");
        } else {
            if (progression.getTierProgress(context.getLevel()) < 1.0F) {
                return Component.translatable("ritual.mna.progression.not_ready");
            }

            if (progression != null && progression.hasAlliedFaction() && progression.getAlliedFaction() != FactionInit.PALADIN) {
                return Component.translatable("event.mna.faction_ritual_failed");
            }

            return null;
        }
    }

    @Override
    protected boolean applyRitualEffect(IRitualContext context) {
        if (context.getCaster() != null) {
            IPlayerProgression progression = context.getCaster().getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
            IPlayerMagic magic = context.getCaster().getCapability(PlayerMagicProvider.MAGIC).orElse(null);
            if (progression != null && magic != null && progression.getTier() < 5) {
                if (progression.getAlliedFaction() == null) {
                    progression.setAlliedFaction(FactionInit.PALADIN, context.getCaster());
                    context.getCaster().displayClientMessage(Component.translatable("event.fnc.faction_ally_paladin"), false);
                }

                if (progression.getAlliedFaction() == FactionInit.PALADIN) {
                    progression.setTier(progression.getTier() + 1, context.getCaster());
                    context.getCaster().displayClientMessage(Component.translatable("mna:progresscondition.advanced", progression.getTier()), false);

                    boolean sword = !context.getCollectedReagents((i) -> i.getItem() == Items.IRON_SWORD).isEmpty();
                    if (!sword) {
                        context.getCaster().getPersistentData().putInt("faction_casting_resource_idx", 0);
                    } else {
                        context.getCaster().getPersistentData().putInt("faction_casting_resource_idx", 1);
                    }

                    magic.setCastingResourceType(FactionInit.PALADIN.getCastingResource(context.getCaster()));
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
                context.getLevel().addParticle(ParticleTypes.ENCHANT,
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
