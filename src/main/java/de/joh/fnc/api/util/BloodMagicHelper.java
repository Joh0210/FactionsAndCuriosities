package de.joh.fnc.api.util;

import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * A helper class for blood magic calculations.
 * Used to determine how much blood a blood mage regenerates when sacrificing themselves or others.
 * @author Joh0210
 */
public class BloodMagicHelper {
    /**
     * Calculates the amount of blood regenerated based on the sacrifice.
     * @param bloodMage The blood mage performing the sacrifice.
     * @param sacrifice The entity being sacrificed (can be the blood mage themselves).
     * @param amountPaid The amount of health that was paid/lost for the sacrifice.
     */
    public static void regenerateMana(Player bloodMage, LivingEntity sacrifice, float amountPaid) {
        int regenerationAmount = (int) amountPaid;

        bloodMage.getCapability(PlayerMagicProvider.MAGIC).ifPresent(magic -> {
            if (magic.isMagicUnlocked()) {
                magic.getCastingResource().restore(regenerationAmount);
            }
        });
    }
}
