package de.joh.fnc.common.item;

import com.mna.api.events.SpellCastEvent;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.api.spelladjustment.SpellAdjustment;
import de.joh.fnc.api.spelladjustment.SpellAdjustmentHelper;
import de.joh.fnc.client.event.ClientEventHandler;
import de.joh.fnc.networking.packet.IncrementSelectedSpellAdjustmentC2SPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.InputEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Magic wand that allows the player to switch back and forth between the different Wild Magics to test them.
 * <br> To select a Wild Magic: Scroll while Shifting
 * <br> How to use a Wild Magic:
 * <br> - {@link DebugOrbSpellAdjustmentItem#use(Level, Player, InteractionHand) Use} -> Wilde magic on your Self
 * <br> - {@link DebugOrbSpellAdjustmentItem#hurtEnemy(ItemStack, LivingEntity, LivingEntity) Hurt} -> Wild magic on the other Person
 * <br> Only available in creative
 * @author Joh0210
 */
public class DebugOrbSpellAdjustmentItem extends Item {
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
            source.displayClientMessage(Component.literal(Component.translatable("fnc.feedback.selected.spelladjustment").getString() + Component.translatable(getSelectedSpellAdjustment(stack).toString()).getString()), false);
            SpellAdjustment spellAdjustment = getSelectedSpellAdjustment(stack);
            if(!SpellAdjustmentHelper.performSpellAdjustment(spellAdjustment, spellCastEvent)){
                source.displayClientMessage(Component.translatable("fnc.feedback.selected.spelladjustment.condition_false"), false);
            }
        }
    }

    /**
     * Selects a new Wild Magic for the used DebugRod
     * <br>Called by the {@link ClientEventHandler#onMouseScroll(InputEvent.MouseScrollingEvent) ClientEventHandler.onMouseScroll()}
     * @param stack used DebugRod
     * @param inverted count down instead of up
     * @param player Player who uses the Rod
     * @see ClientEventHandler
     * @see IncrementSelectedSpellAdjustmentC2SPacket
     */
    public void incrementSpellAdjustmentIterator(ItemStack stack, boolean inverted, Player player){
        if(!player.level().isClientSide()){
            AtomicInteger spellAdjustmentIterator = new AtomicInteger(0);
            AtomicBoolean isInverted = new AtomicBoolean(inverted);
            if(stack.getTag() != null && stack.getTag().contains(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator")){
                spellAdjustmentIterator.set(stack.getTag().getInt(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator"));
                stack.getTag().remove(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator");
            }

            spellAdjustmentIterator.set(adjustSpellAdjustmentIterator(spellAdjustmentIterator.get() + (isInverted.get() ? -1 : +1)));

            CompoundTag nbtData = new CompoundTag();
            nbtData.putInt(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator", spellAdjustmentIterator.get());
            if(stack.getTag() == null){
                stack.setTag(nbtData);
            } else {
                stack.getTag().merge(nbtData);
            }

            player.displayClientMessage(Component.literal(Component.translatable("fnc.feedback.selected.spelladjustment").getString() + Component.translatable(getSelectedSpellAdjustment(stack).toString()).getString()), false);
        }
    }

    /**
     * Makes sure the used Iterator is valid. If not, it will be adjusted
     * @param spellAdjustmentIterator iterator, which could be over or below the limit
     * @return adjusted version of the iterator, so it reveres to a Wild Magic
     */
    public int adjustSpellAdjustmentIterator(int spellAdjustmentIterator){
        SpellAdjustment[] knowSpellAdjustment = SpellAdjustmentHelper.getAllSpellAdjustment();

        if(knowSpellAdjustment.length == 0){
            return 0;
        } else {
            while (spellAdjustmentIterator >= knowSpellAdjustment.length) {
                spellAdjustmentIterator -= knowSpellAdjustment.length;
            }
            while (spellAdjustmentIterator < 0) {
                spellAdjustmentIterator += knowSpellAdjustment.length;
            }
            return spellAdjustmentIterator;
        }
    }

    public SpellAdjustment getSelectedSpellAdjustment(ItemStack stack){
        SpellAdjustment[] knowSpellAdjustment = SpellAdjustmentHelper.getAllSpellAdjustment();

        if(knowSpellAdjustment.length == 0){
            return SpellAdjustment.INSTANCE;
        } else {
            return knowSpellAdjustment[adjustSpellAdjustmentIterator(stack.getTag() != null ? stack.getTag().getInt(FactionsAndCuriosities.MOD_ID + "_wild_magic_iterator") : 0)];
        }
    }
}

