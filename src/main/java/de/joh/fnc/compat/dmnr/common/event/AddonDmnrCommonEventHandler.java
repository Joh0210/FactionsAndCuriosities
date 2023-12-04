package de.joh.fnc.compat.dmnr.common.event;

import com.mna.Registries;
import com.mna.api.events.SpellCastEvent;
import com.mna.api.items.IFactionSpecific;
import com.mna.items.armor.ISetItem;
import de.joh.dragonmagicandrelics.DragonMagicAndRelics;
import de.joh.dragonmagicandrelics.armorupgrades.types.ArmorUpgrade;
import de.joh.dragonmagicandrelics.capabilities.dragonmagic.ArmorUpgradeHelper;
import de.joh.dragonmagicandrelics.events.additional.DragonUpgradeEvent;
import de.joh.dragonmagicandrelics.events.additional.HasMaxFactionEvent;
import de.joh.fnc.api.event.AddSmiteEvent;
import de.joh.fnc.common.item.DivineArmorItem;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrArmorUpgradeInit;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrItemInit;
import de.joh.fnc.common.item.MischiefArmorItem;
import de.joh.fnc.api.spelladjustment.SpellAdjustmentHelper;
import de.joh.fnc.api.util.Quality;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
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
            ItemStack chest = event.getPlayer().getItemBySlot(EquipmentSlot.CHEST);
            if ((chest.getItem() instanceof MischiefArmorItem || chest.getItem() instanceof DivineArmorItem)
                    && ((ISetItem)chest.getItem()).isSetEquipped(event.getPlayer()) && chest.getItem() instanceof IFactionSpecific) {
                event.setValues(true, Registries.Factions.get().getValue(((IFactionSpecific)chest.getItem()).getFaction()));
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
            ItemStack chest = event.getPlayer().getItemBySlot(EquipmentSlot.CHEST);
            if (chest.getItem() instanceof MischiefArmorItem && ((ISetItem)chest.getItem()).isSetEquipped(event.getPlayer()) && chest.getItem() instanceof IFactionSpecific) {
                event.setAlternativeArmorValues(
                        new ItemStack(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_HELMET.get()),
                        new ItemStack(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_CHESTPLATE.get()),
                        new ItemStack(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_LEGGING.get()),
                        new ItemStack(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_BOOTS.get()),
                        (HashMap <ArmorUpgrade, Integer>) Map.of(
                                AddonDmnrArmorUpgradeInit.WILD_MAGIC_LUCK, 1,
                                AddonDmnrArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT, 2
                        ));
            }

            if (chest.getItem() instanceof DivineArmorItem && ((ISetItem)chest.getItem()).isSetEquipped(event.getPlayer()) && chest.getItem() instanceof IFactionSpecific) {
                event.setAlternativeArmorValues(
                        new ItemStack(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_HELMET.get()),
                        new ItemStack(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_CHESTPLATE.get()),
                        new ItemStack(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_LEGGING.get()),
                        new ItemStack(AddonDmnrItemInit.DIVINE_DRAGON_MAGE_BOOTS.get()),
                        (HashMap <ArmorUpgrade, Integer>) Map.of(
                                AddonDmnrArmorUpgradeInit.MAGIC_RESISTANCE, 2,
                                AddonDmnrArmorUpgradeInit.SMITE_DURATION, 1
                        ));
            }
        }
    }

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent event){
        Player caster = event.getCaster();
        //todo: outsource as Event!
        Random random = new Random();
        int level = ArmorUpgradeHelper.getUpgradeLevel(caster, AddonDmnrArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT);
        if(level >= 1 && random.nextInt(100) < 25 * level){
            SpellAdjustmentHelper.performRandomSpellAdjustment(event, (rs, c, s) -> rs.getQuality(Objects.requireNonNull(s.getComponent(0)).getPart().getUseTag()).ordinal() >= Quality.NEUTRAL.ordinal());
        }
    }

    @SubscribeEvent
    public static void onAddSmite(AddSmiteEvent event){
        Player player = event.getPlayer();
        int level = ArmorUpgradeHelper.getUpgradeLevel(player, AddonDmnrArmorUpgradeInit.SMITE_DURATION);
        if(level > 0){
            event.addDuration((int)(event.getDuration() * 0.5f * level));
        }
    }
}
