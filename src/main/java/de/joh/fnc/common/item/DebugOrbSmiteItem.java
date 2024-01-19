package de.joh.fnc.common.item;

import de.joh.fnc.api.smite.SmiteHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Allows the user to Smite itself
 * @author Joh0210
 */
public class DebugOrbSmiteItem extends Item {
    public DebugOrbSmiteItem() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        InteractionResultHolder<ItemStack> ret = super.use(world, user, hand);

        SmiteHelper.applySmite(user, user);

        user.getCooldowns().addCooldown(this, 20);
        return ret;
    }
}

