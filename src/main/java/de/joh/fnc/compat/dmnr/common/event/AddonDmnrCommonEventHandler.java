package de.joh.fnc.compat.dmnr.common.event;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.items.IFactionSpecific;
import com.mna.items.armor.ISetItem;
import de.joh.dmnr.DragonMagicAndRelics;
import de.joh.dmnr.api.armorupgrade.ArmorUpgrade;
import de.joh.dmnr.api.event.DragonUpgradeEvent;
import de.joh.dmnr.api.event.HasMaxFactionEvent;
import de.joh.dmnr.capabilities.dragonmagic.ArmorUpgradeHelper;
import de.joh.fnc.api.event.AddSmiteEvent;
import de.joh.fnc.api.spelladjustment.SpellAdjustmentHelper;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.common.item.DivineArmorItem;
import de.joh.fnc.common.item.MischiefArmorItem;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrArmorUpgradeInit;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrItemInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * Handler for Forge-Events of the {@link DragonMagicAndRelics Dragon Magic And Relics} mod
 * @author Joh0210
 */
public class AddonDmnrCommonEventHandler {

    /**
     * Does the Player wear a full Max Faction Armor of F&C
     * @see MischiefArmorItem
     */
    @SubscribeEvent
    public static void hasMaxFaction(HasMaxFactionEvent event){
        if(!event.hasMaxFactionArmor()){
            ItemStack chest = event.getEntity().getItemBySlot(EquipmentSlot.CHEST);
            if ((chest.getItem() instanceof MischiefArmorItem || chest.getItem() instanceof DivineArmorItem)
                    && ((ISetItem)chest.getItem()).isSetEquipped(event.getEntity()) && chest.getItem() instanceof IFactionSpecific) {
                event.setValues(true, ((IFactionSpecific)chest.getItem()).getFaction());
            }
        }
    }

    /**
     * Upgrades the Players Max Faction Armor to a Dragon Magic Armor
     * @see MischiefArmorItem
     */
    @SubscribeEvent
    public static void onDragonUpgrade(DragonUpgradeEvent event){
        if(!event.canBeUpgraded()){
            ItemStack chest = event.getEntity().getItemBySlot(EquipmentSlot.CHEST);
            if (chest.getItem() instanceof MischiefArmorItem && ((ISetItem)chest.getItem()).isSetEquipped(event.getEntity()) && chest.getItem() instanceof IFactionSpecific) {
                HashMap<ArmorUpgrade, Integer> upgrades = new HashMap<>();
                upgrades.put(AddonDmnrArmorUpgradeInit.WILD_MAGIC_LUCK, 1);
                upgrades.put(AddonDmnrArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT, 2);
                event.setAlternativeArmorValues(
                        new ItemStack(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_HELMET.get()),
                        new ItemStack(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_CHESTPLATE.get()),
                        new ItemStack(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_LEGGING.get()),
                        new ItemStack(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_BOOTS.get()),
                        upgrades);
            }

            if (chest.getItem() instanceof DivineArmorItem && ((ISetItem)chest.getItem()).isSetEquipped(event.getEntity()) && chest.getItem() instanceof IFactionSpecific) {
                HashMap<ArmorUpgrade, Integer> upgrades = new HashMap<>();
                upgrades.put(AddonDmnrArmorUpgradeInit.MAGIC_RESISTANCE, 2);
                upgrades.put(AddonDmnrArmorUpgradeInit.SMITE_DURATION, 1);
                event.setAlternativeArmorValues(
                        new ItemStack(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_HELMET.get()),
                        new ItemStack(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_CHESTPLATE.get()),
                        new ItemStack(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_LEGGING.get()),
                        new ItemStack(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_BOOTS.get()),
                        upgrades);
            }
        }
    }

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent event){
        LivingEntity caster = event.getSource().getCaster();
        if (caster instanceof Player) {
            //todo: outsource as Event!
            Random random = new Random();
            int level = ArmorUpgradeHelper.getUpgradeLevel((Player) caster, AddonDmnrArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT);
            if(level >= 1 && random.nextInt(100) < 25 * level){
                SpellAdjustmentHelper.performRandomSpellAdjustment(event, (rs, c, s) -> rs.getQuality(Objects.requireNonNull(s.getComponent(0)).getPart().getUseTag()).ordinal() >= Quality.NEUTRAL.ordinal());
            }
        }

    }

    @SubscribeEvent
    public static void onAddSmite(AddSmiteEvent event){
        Player player = event.getEntity();
        int level = ArmorUpgradeHelper.getUpgradeLevel(player, AddonDmnrArmorUpgradeInit.SMITE_DURATION);
        if(level > 0){
            event.addDuration((int)(event.getDuration() * 0.5f * level));
        }
    }
}
