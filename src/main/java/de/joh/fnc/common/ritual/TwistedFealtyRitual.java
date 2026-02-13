package de.joh.fnc.common.ritual;

import com.mna.api.capabilities.IPlayerMagic;
import com.mna.api.capabilities.IPlayerProgression;
import com.mna.api.rituals.IRitualContext;
import com.mna.api.rituals.RitualEffect;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

/**
 * changes the Pact of a Paladin
 * @author Joh0210
 */
public class TwistedFealtyRitual extends RitualEffect {
    public TwistedFealtyRitual(ResourceLocation ritualName) {
        super(ritualName);
    }

    public Component canRitualStart(IRitualContext context) {
        IPlayerProgression progression = (IPlayerProgression)context.getCaster().getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
        if (progression == null) {
            return Component.literal("Progression could not be found...this is a problem.");
        } else {
            return progression.getTier() > 2 && progression.getAlliedFaction() != FactionInit.PALADIN ? Component.translatable("ritual.fnc.twisted_fealty.wrong_faction") : null;
        }
    }

    protected boolean applyRitualEffect(IRitualContext context) {
        if (context.getCaster() != null && context.getCaster().getUUID() != null) {
            IPlayerProgression progression = context.getCaster().getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
            IPlayerMagic magic = context.getCaster().getCapability(PlayerMagicProvider.MAGIC).orElse(null);
            if (progression != null && magic != null) {
                if (progression != null && progression.hasAlliedFaction() && progression.getAlliedFaction() != FactionInit.PALADIN) {
                    context.getCaster().sendSystemMessage(Component.translatable("ritual.fnc.twisted_fealty.wrong_faction"));
                    return false;
                } else {
                    boolean sword = !context.getCollectedReagents((i) -> i.getItem() == Items.IRON_SWORD).isEmpty();
                    if (!sword) {
                        context.getCaster().getPersistentData().putInt("faction_casting_resource_idx", 0);
                    } else {
                        context.getCaster().getPersistentData().putInt("faction_casting_resource_idx", 1);
                    }

                    magic.setCastingResourceType(FactionInit.PALADIN.getCastingResource(context.getCaster()));
                    return true;
                }
            } else {
                context.getCaster().sendSystemMessage(Component.literal("Progression or Magic capabilities are missing.  Aborting ritual to prevent a crash.  You should save and quit, something is wrong."));
                return false;
            }
        } else {
            return false;
        }
    }

    protected int getApplicationTicks(IRitualContext context) {
        return 60;
    }
}

