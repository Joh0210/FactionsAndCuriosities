package de.joh.fnc.compat.dmnr.event;

import com.mna.Registries;
import com.mna.api.events.SpellCastEvent;
import com.mna.api.items.IFactionSpecific;
import com.mna.items.armor.ISetItem;
import de.joh.dragonmagicandrelics.DragonMagicAndRelics;
import de.joh.dragonmagicandrelics.armorupgrades.types.ArmorUpgrade;
import de.joh.dragonmagicandrelics.capabilities.dragonmagic.ArmorUpgradeHelper;
import de.joh.dragonmagicandrelics.events.additional.DragonUpgradeEvent;
import de.joh.dragonmagicandrelics.events.additional.HasMaxFactionEvent;
import de.joh.fnc.compat.dmnr.armorupgrades.AddonDragonMagicAndRelicsArmorUpgradeInit;
import de.joh.fnc.compat.dmnr.item.AddonDragonMagicAndRelicsItems;
import de.joh.fnc.item.init.MischiefArmor;
import de.joh.fnc.spelladjustment.util.SpellAdjustmentHelper;
import de.joh.fnc.wildmagic.util.Quality;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Handler for Forge-Events of the {@link DragonMagicAndRelics Dragon Magic And Relics} mod
 * @author Joh0210
 */
public class AddonDragonMagicAndRelicsCommonHandler {

    /**
     * Does the Player wear a full Max Faction Armor of F&C
     * @see MischiefArmor
     */
    @SubscribeEvent
    public static void hasMaxFaction(HasMaxFactionEvent event){
        if(!event.hasMaxFactionArmor()){
            ItemStack chest = event.getPlayer().getItemBySlot(EquipmentSlot.CHEST);
            if (chest.getItem() instanceof MischiefArmor && ((ISetItem)chest.getItem()).isSetEquipped(event.getPlayer()) && chest.getItem() instanceof IFactionSpecific) {
                event.setValues(true, Registries.Factions.get().getValue(((IFactionSpecific)chest.getItem()).getFaction()));
            }
        }
    }

    /**
     * Upgrades the Players Max Faction Armor to a Dragon Magic Armor
     * @see MischiefArmor
     */
    @SubscribeEvent
    public static void onDragonUpgrade(DragonUpgradeEvent event){
        if(!event.canBeUpgraded()){
            ItemStack chest = event.getPlayer().getItemBySlot(EquipmentSlot.CHEST);
            if (chest.getItem() instanceof MischiefArmor && ((ISetItem)chest.getItem()).isSetEquipped(event.getPlayer()) && chest.getItem() instanceof IFactionSpecific) {
                event.setAlternativeArmorValues(
                        new ItemStack(AddonDragonMagicAndRelicsItems.MISCHIEF_DRAGON_MAGE_HELMET.get()),
                        new ItemStack(AddonDragonMagicAndRelicsItems.MISCHIEF_DRAGON_MAGE_CHESTPLATE.get()),
                        new ItemStack(AddonDragonMagicAndRelicsItems.MISCHIEF_DRAGON_MAGE_LEGGING.get()),
                        new ItemStack(AddonDragonMagicAndRelicsItems.MISCHIEF_DRAGON_MAGE_BOOTS.get()),
                        (HashMap <ArmorUpgrade, Integer>) Map.of(
                                AddonDragonMagicAndRelicsArmorUpgradeInit.WILD_MAGIC_LUCK, 1,
                                AddonDragonMagicAndRelicsArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT, 2
                        ));
            }
        }
    }

    @SubscribeEvent
    public static void onSpellCast(SpellCastEvent event){
        Player caster = event.getCaster();
        //todo: outsource as Event!
        if(ArmorUpgradeHelper.getUpgradeLevel(caster, AddonDragonMagicAndRelicsArmorUpgradeInit.RANDOM_SPELL_ADJUSTMENT) >= 1 && new Random().nextBoolean()){
            SpellAdjustmentHelper.performRandomSpellAdjustment(event, (rs, c, s) -> rs.getQuality(s.getComponent(0).getPart().getUseTag()).ordinal() >= Quality.NEUTRAL.ordinal());
        }
    }
}
