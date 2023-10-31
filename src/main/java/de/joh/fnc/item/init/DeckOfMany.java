package de.joh.fnc.item.init;

import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.utils.CreativeModeTabInit;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Performs a random Extreme-Wild-Magic (either VERY_BAD or VERY_GOOD, nothing in beaten)
 * todo: Artefact?
 * @author Joh0210
 */
public class DeckOfMany extends Item {
    public DeckOfMany() {
        super(new Properties().stacksTo(1).tab(CreativeModeTabInit.FACTIONS_AND_CURIOSITIES).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, user, hand);
        if (!world.isClientSide()) {
            //todo: Might not be performed
            WildMagicHelper.performRandomWildMagic(user, null, SpellPartTags.FRIENDLY, (wm, s, t, comp) -> wm.getQuality(comp) == Quality.VERY_BAD || wm.getQuality(comp) == Quality.VERY_GOOD, false);
        }
        user.getCooldowns().addCooldown(this, 1200);
        return ar;
    }
}
