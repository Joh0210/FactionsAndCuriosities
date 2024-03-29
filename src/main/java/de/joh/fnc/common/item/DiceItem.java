package de.joh.fnc.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * An Item that can roll random numbers.
 * @author Joh0210
 */
public class DiceItem extends Item {
    /**
     * @param size Size of the Dice (e.g. 6 -> random numbers between 1-6)
     */
    public DiceItem(int size) {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON));
        this.size = size;
    }

    private final int size;

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if(!level.isClientSide) {
            Random random = new Random();
            Component component = Component.translatable("fnc.dice.roll.output.text");
            player.getCooldowns().addCooldown(this, 20);
            player.displayClientMessage(Component.literal(component.getString() + (random.nextInt(size) + 1)), false);
        }
        return super.use(level, player, usedHand);
    }
}
