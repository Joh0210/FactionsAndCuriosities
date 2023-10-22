package de.joh.fnc.item.init;

import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.event.handler.ClientEventHandler;
import de.joh.fnc.networking.packet.IncrementSelectedWildMagicC2SPacket;
import de.joh.fnc.wildmagic.util.WildMagic;
import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.InputEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Magic wand that allows the player to switch back and forth between the different Wild Magics to test them.
 * <br> To select a Wild Magic: Scroll while Shifting
 * <br> How to use a Wild Magic:
 * <br> - {@link de.joh.fnc.item.init.DebugRod#use(Level, Player, InteractionHand) Use} -> Wilde magic on your Self
 * <br> - {@link de.joh.fnc.item.init.DebugRod#hurtEnemy(ItemStack, LivingEntity, LivingEntity) Hurt} -> Wild magic on the other Person
 * <br> Only available in creative
 * @author Joh0210
 */
public class DebugRod extends Item {
    public DebugRod() {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).tab(CreativeModeTab.TAB_BREWING));
    }

    /**
     * Performs the selected Wild Magic on the target.
     * @param world Level in which the procedure takes place (Effect will only be performed on the Server)
     * @param source Source of the Wild Magic. Can be the target of the Wild Magic
     * @param target Of the Selection. Can be the target of the Wild Magic
     * @param stack Debug Rod used
     */
    public void useWildMagic(Level world, Player source, SpellTarget target, ItemStack stack){
        if (!world.isClientSide()) {
            source.displayClientMessage(new TextComponent(new TranslatableComponent("fnc.feedback.selected.wildmagic").getString() + new TranslatableComponent(getSelectedWildMagic(stack).toString()).getString()), false);
            WildMagic wildMagic = getSelectedWildMagic(stack);
            if(wildMagic.canBePerformed(source, target)){
                wildMagic.performWildMagic(source, target);
            } else {
                source.displayClientMessage(new TranslatableComponent("fnc.feedback.selected.wildmagic.condition_false"), false);
            }
        }
    }

    /**
     * Selects a new Wild Magic for the used DebugRod
     * <br>Called by the {@link de.joh.fnc.event.handler.ClientEventHandler#onMouseScroll(InputEvent.MouseScrollEvent) ClientEventHandler.onMouseScroll()}
     * @param stack used DebugRod
     * @param inverted count down instead of up
     * @param player Player who uses the Rod
     * @see ClientEventHandler
     * @see IncrementSelectedWildMagicC2SPacket
     */
    public void incrementWildMagicIterator(ItemStack stack, boolean inverted, Player player){
        if(!player.level.isClientSide()){
            AtomicInteger wildMagicIterator = new AtomicInteger(0);
            AtomicBoolean isInverted = new AtomicBoolean(inverted);
            if(stack.getTag() != null && stack.getTag().contains(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator")){
                wildMagicIterator.set(stack.getTag().getInt(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator"));
                stack.getTag().remove(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator");
            }

            wildMagicIterator.set(adjustWildMagicIterator(wildMagicIterator.get() + (isInverted.get() ? -1 : +1)));

            CompoundTag nbtData = new CompoundTag();
            nbtData.putInt(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator", wildMagicIterator.get());
            if(stack.getTag() == null){
                stack.setTag(nbtData);
            } else {
                stack.getTag().merge(nbtData);
            }

            player.displayClientMessage(new TextComponent(new TranslatableComponent("fnc.feedback.selected.wildmagic").getString() + new TranslatableComponent(getSelectedWildMagic(stack).toString()).getString()), false);
        }
    }

    /**
     * Makes sure the used Iterator is valid. If not, it will be adjusted
     * @param wildMagicIterator iterator, which could be over or below the limit
     * @return adjusted version of the iterator, so it reveres to a Wild Magic
     */
    public int adjustWildMagicIterator(int wildMagicIterator){
        WildMagic[] knowWildMagic = WildMagicHelper.getAllWildMagic();

        if(knowWildMagic.length == 0){
            return 0;
        } else {
            while (wildMagicIterator >= knowWildMagic.length) {
                wildMagicIterator -= knowWildMagic.length;
            }
            while (wildMagicIterator < 0) {
                wildMagicIterator += knowWildMagic.length;
            }
            return wildMagicIterator;
        }
    }

    public WildMagic getSelectedWildMagic(ItemStack stack){
        WildMagic[] knowWildMagic = WildMagicHelper.getAllWildMagic();

        if(knowWildMagic.length == 0){
            return WildMagic.INSTANCE;
        } else {
            return knowWildMagic[adjustWildMagicIterator(stack.getTag() != null ? stack.getTag().getInt(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator") : 0)];
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, user, hand);
        useWildMagic(world, user, new SpellTarget(user), user.getItemInHand(hand));
        user.getCooldowns().addCooldown(this, 20);
        return ar;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (attacker instanceof Player) {
            useWildMagic(attacker.getLevel(), (Player) attacker, new SpellTarget(target), stack);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}

