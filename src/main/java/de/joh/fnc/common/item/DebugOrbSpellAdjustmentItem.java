package de.joh.fnc.common.item;

import com.mna.api.events.SpellCastEvent;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.spelladjustment.SpellAdjustmentHelper;
import de.joh.tnl.api.item.ScrollableItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

/**
 * Magic wand that allows the player to switch back and forth between the different Wild Magics to test them.
 * <br> To select a Wild Magic: Scroll while Shifting
 * <br> How to use a Wild Magic:
 * <br> - {@link DebugOrbSpellAdjustmentItem#use(Level, Player, InteractionHand) Use} -> Wilde magic on your Self
 * <br> - {@link DebugOrbSpellAdjustmentItem#hurtEnemy(ItemStack, LivingEntity, LivingEntity) Hurt} -> Wild magic on the other Person
 * <br> Only available in creative
 * @author Joh0210
 */
public class DebugOrbSpellAdjustmentItem extends Item implements ScrollableItem {
    public DebugOrbSpellAdjustmentItem() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    /**
     * Performs the selected Wild Magic on the target.
     * @param world Level in which the procedure takes place (Effect will only be performed on the Server)
     * @param source Source of the Wild Magic. Can be the target of the Wild Magic
     * @param stack Debug Rod used
     */
    public void useSpellAdjustment(Level world, Player source, ItemStack stack, SpellCastEvent spellCastEvent){
        if (!world.isClientSide()) {
            source.displayClientMessage(Component.literal(Component.translatable("fnc.feedback.selected.spelladjustment").getString() + Component.translatable(getSelectedSpellAdjustment(stack, source).toString()).getString()), false);
            SpellAdjustment spellAdjustment = getSelectedSpellAdjustment(stack, source);
            if(!SpellAdjustmentHelper.performSpellAdjustment(spellAdjustment, spellCastEvent)){
                source.displayClientMessage(Component.translatable("fnc.feedback.selected.spelladjustment.condition_false"), false);
            }
        }
    }

    /**
     * Number of selectable Spell Adjustments
     */
    @Override
    public int getIteratorSize(Player player) {
        return SpellAdjustmentHelper.getAllSpellAdjustment().length;
    }

    /**
     * Selects a new Spell Adjustment for the used DebugRod and tells the player which one is selected now.
     * <br>Called by the scroll handling of Tomes And Libs
     * @param stack used DebugRod
     * @param inverted count down instead of up
     * @param player Player who uses the Rod
     * @see ScrollableItem
     */
    @Override
    public int incrementIterator(ItemStack stack, boolean inverted, Player player) {
        int iterator = ScrollableItem.super.incrementIterator(stack, inverted, player);

        if(!player.level().isClientSide()){
            player.displayClientMessage(Component.literal(Component.translatable("fnc.feedback.selected.spelladjustment").getString() + Component.translatable(getSelectedSpellAdjustment(stack, player).toString()).getString()), false);
        }

        return iterator;
    }

    public SpellAdjustment getSelectedSpellAdjustment(ItemStack stack, Player player){
        SpellAdjustment[] knowSpellAdjustment = SpellAdjustmentHelper.getAllSpellAdjustment();

        if(knowSpellAdjustment.length == 0){
            return SpellAdjustment.INSTANCE;
        } else {
            return knowSpellAdjustment[adjustWildMagicIterator(getIterator(stack), player)];
        }
    }
}

