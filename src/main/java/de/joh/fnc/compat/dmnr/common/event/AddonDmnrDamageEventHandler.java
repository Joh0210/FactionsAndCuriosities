package de.joh.fnc.compat.dmnr.common.event;

import de.joh.dmnr.capabilities.dragonmagic.ArmorUpgradeHelper;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.compat.dmnr.common.armorupgrades.MagicResistanceArmorUpgrade;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrArmorUpgradeInit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Handler for Forge-Events that revolve around damage and attacks
 * @author Joh0210
 */
public class AddonDmnrDamageEventHandler {
    /**
     * Protection from Magic
     * <br>see {@link AddonDmnrArmorUpgradeInit#MAGIC_RESISTANCE MagicResistanceUpgrade}
     */
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        LivingEntity targetEntity = event.getEntity();
        if(targetEntity instanceof Player) {
            int level = ArmorUpgradeHelper.getUpgradeLevel((Player) targetEntity, AddonDmnrArmorUpgradeInit.MAGIC_RESISTANCE);
            if (DamageEventHandler.isMagic(event.getSource()) && level > 0) {
                if(!(0 < event.getAmount() * (1.0f - 0.25f * level))){
                    event.setCanceled(true);
                }
            }
        }
    }

    /**
     * @see MagicResistanceArmorUpgrade
     */
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity targetEntity = event.getEntity();
        if(targetEntity instanceof Player) {
            int level = ArmorUpgradeHelper.getUpgradeLevel((Player) targetEntity, AddonDmnrArmorUpgradeInit.MAGIC_RESISTANCE);
            if (DamageEventHandler.isMagic(event.getSource()) && level > 0) {
                if(0 < event.getAmount() * (1.0f - 0.25f * level)){
                    event.setAmount(event.getAmount() * (1.0f - 0.25f * level));
                } else {
                    event.setCanceled(true);
                }
            }
        }
    }
}
