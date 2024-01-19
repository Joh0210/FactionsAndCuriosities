package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.ITieredItem;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.item.material.ToolMaterials;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;

/**
 * If the Player Uses this Sword to Smite, he regains half the applied Smite-Damage as HP.
 * <br>This Effect only works on the actual HP (Absorption or other hit pools will be ignored)
 * @see DamageEventHandler
 * @author Joh0210
 */
public class BrimstoneSwordItem extends SwordItem implements IFactionSpecific, ITieredItem<BrimstoneSwordItem> {
    private int tier = -1;

    public BrimstoneSwordItem() {
        super(ToolMaterials.LIVE_RIP, 2, -2.4F, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if(attacker instanceof Player){
            this.usedByPlayer((Player) attacker);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.PALADIN;
    }

    @Override
    public void setCachedTier(int tier) {
        this.tier = tier;
    }

    @Override
    public int getCachedtier() {
        return this.tier;
    }
}
