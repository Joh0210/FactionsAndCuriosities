package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.ITieredItem;
import de.joh.fnc.common.event.DamageEventHandler;
import de.joh.fnc.common.init.FactionInit;
import de.joh.tnl.api.item.IDragonMagicItem;
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
 * <br>Dragon Magic: The user regains the whole applied Smite-Damage as HP instead of half of it.
 * @see DamageEventHandler
 * @author Joh0210
 */
public class PaladinSwordItem extends SwordItem implements IFactionSpecific, ITieredItem<PaladinSwordItem>, IDragonMagicItem {
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
    public String dragonMagicID() {
        return "item.fnc.brimstone_sword.dragonmagic";
    }

    /**
     * @param user Wielder of the sword
     * @return Which share of the applied Smite-Damage the user regains as HP: all of it with Dragon Magic, otherwise half of it
     */
    public float getHealFactor(LivingEntity user) {
        return this.hasDragonMagic(user) ? 1.0F : 0.5F;
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


    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        // keeps the glint of enchantments (e.g. Holy Smite)
        return super.isFoil(stack) || (this.isEnabled() && this.hasDragonMagic());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fnc.brimstone_sword.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("  "));
        this.tooltipAddition(tooltip);
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
