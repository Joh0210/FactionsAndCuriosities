package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.ITieredItem;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.item.material.ToolMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * If the Player Uses this Sword to Smite, he regains half the applied Smite-Damage as HP.
 * <br>This Effect only works on the actual HP (Absorption or other hit pools will be ignored)
 * @see DamageEventHandler
 * @author Joh0210
 */
public class PaladinSwordItem extends SwordItem implements IFactionSpecific, ITieredItem<PaladinSwordItem> {
    private int tier = -1;

    public PaladinSwordItem(Tier tier, int p1, float p2, Item.Properties properties) {
        super(tier, p1, p2, properties);
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
    public int getCachedTier() {
        return this.tier;
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fnc.brimstone_sword.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("  "));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
