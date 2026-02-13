package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Performs a random Extreme-Wild-Magic (either VERY_BAD or VERY_GOOD, nothing in beaten)
 * @author Joh0210
 */
public class DeckOfManyItem extends Item implements IFactionSpecific {
    public DeckOfManyItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        InteractionResultHolder<ItemStack> ret = super.use(world, user, hand);
        this.usedByPlayer(user);
        if (!world.isClientSide()) {
            //todo: Might not be performed
            WildMagicHelper.performRandomWildMagic(user,
                    null,
                    SpellPartTags.FRIENDLY,
                    (wm, s, t, comp) -> wm.getQuality(comp) == Quality.VERY_BAD || wm.getQuality(comp) == Quality.VERY_GOOD,
                    false);
        }
        user.getCooldowns().addCooldown(this, 800);
        return ret;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fnc.deck_of_many.description").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("  "));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.WILD;
    }
}
