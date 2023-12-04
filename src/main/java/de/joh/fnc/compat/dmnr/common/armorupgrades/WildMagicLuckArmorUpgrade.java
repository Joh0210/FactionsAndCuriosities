package de.joh.fnc.compat.dmnr.common.armorupgrades;

import de.joh.dragonmagicandrelics.armorupgrades.types.ArmorUpgradeOnEquipped;
import de.joh.fnc.api.util.AttributeInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Increases the wild magic luck of the user
 * @author Joh0210
 */
public class WildMagicLuckArmorUpgrade extends ArmorUpgradeOnEquipped {
    private static final AttributeModifier luck1 = new AttributeModifier("dmnr_luck_boost_1", 1, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier luck2 = new AttributeModifier("dmnr_luck_boost_2", 1, AttributeModifier.Operation.ADDITION);

    public WildMagicLuckArmorUpgrade(@NotNull ResourceLocation registryName, int upgradeCost) {
        super(registryName, 1, false, true, upgradeCost);
    }

    @Override
    public void onEquip(Player player, int level) {
        AttributeInstance luck = player.getAttribute(AttributeInit.WILD_MAGIC_LUCK.get());

        if (luck != null && !luck.hasModifier(luck1) && level >= 1) {
            luck.addTransientModifier(luck1);

            if (!luck.hasModifier(luck2) && level >= 2) {
                luck.addTransientModifier(luck2);
            }
        }
    }

    @Override
    public void onRemove(Player player) {
        AttributeInstance luck = player.getAttribute(AttributeInit.WILD_MAGIC_LUCK.get());

        if(luck != null) {
            luck.removeModifier(luck1);
            luck.removeModifier(luck2);
        }
    }
}
