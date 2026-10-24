package de.joh.fnc.common.item;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.wildmagic.WildMagic;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.tnl.api.item.ScrollableItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Magic wand that allows the player to switch back and forth between the different Wild Magics to test them.
 * <br> To select a Wild Magic: Scroll while Shifting
 * <br> How to use a Wild Magic:
 * <br> - {@link DebugOrbWildMagicItem#use(Level, Player, InteractionHand) Use} -> Wilde magic on your Self
 * <br> - {@link DebugOrbWildMagicItem#hurtEnemy(ItemStack, LivingEntity, LivingEntity) Hurt} -> Wild magic on the other Person
 * <br> Only available in creative
 * @author Joh0210
 */
public class DebugOrbWildMagicItem extends Item implements ScrollableItem {
    public DebugOrbWildMagicItem() {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    /**
     * Performs the selected Wild Magic on the target.
     * @param world Level in which the procedure takes place (Effect will only be performed on the Server)
     * @param source Source of the Wild Magic. Can be the target of the Wild Magic
     * @param target Of the Selection. Can be the target of the Wild Magic
     * @param stack Debug Rod used
     */
    public void useWildMagic(Level world, Player source, SpellTarget target, ItemStack stack, SpellPartTags spellPartTag, boolean cancelable){
        if (!world.isClientSide()) {
            source.displayClientMessage(Component.literal(Component.translatable("fnc.feedback.selected.wildmagic").getString() + Component.translatable(getSelectedWildMagic(stack, source).toString()).getString()), false);
            WildMagic wildMagic = getSelectedWildMagic(stack, source);
            if(!WildMagicHelper.performWildMagic(wildMagic, source, target, spellPartTag, cancelable)){
                source.displayClientMessage(Component.translatable("fnc.feedback.selected.wildmagic.condition_false"), false);
            }
        }
    }

    /**
     * Number of selectable Wild Magics
     */
    @Override
    public int getIteratorSize(Player player) {
        return WildMagicHelper.getAllWildMagic().length;
    }

    /**
     * Selects a new Wild Magic for the used DebugRod and tells the player which one is selected now.
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
            player.displayClientMessage(Component.literal(Component.translatable("fnc.feedback.selected.wildmagic").getString() + Component.translatable(getSelectedWildMagic(stack, player).toString()).getString()), false);
        }

        return iterator;
    }

    public WildMagic getSelectedWildMagic(ItemStack stack, Player player){
        WildMagic[] knowWildMagic = WildMagicHelper.getAllWildMagic();

        if(knowWildMagic.length == 0){
            return WildMagic.INSTANCE;
        } else {
            return knowWildMagic[adjustWildMagicIterator(getIterator(stack), player)];
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        InteractionResultHolder<ItemStack> ret = super.use(world, user, hand);
        useWildMagic(world, user, new SpellTarget(user), user.getItemInHand(hand), SpellPartTags.NEUTRAL, true);
        user.getCooldowns().addCooldown(this, 20);
        return ret;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (attacker instanceof Player) {
            useWildMagic(attacker.level(), (Player) attacker, new SpellTarget(target), stack, SpellPartTags.HARMFUL, true);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}

