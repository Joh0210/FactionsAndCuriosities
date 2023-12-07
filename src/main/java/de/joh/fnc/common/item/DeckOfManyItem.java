package de.joh.fnc.common.item;

import com.mna.api.items.IFactionSpecific;
import com.mna.api.spells.SpellPartTags;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.api.util.CreativeModeTabInit;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import net.minecraft.resources.ResourceLocation;
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
 * @author Joh0210
 */
public class DeckOfManyItem extends Item implements IFactionSpecific {
    public DeckOfManyItem() {
        super(new Properties().stacksTo(1).tab(CreativeModeTabInit.FACTIONS_AND_CURIOSITIES).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player user, @NotNull InteractionHand hand) {
        InteractionResultHolder<ItemStack> ret = super.use(world, user, hand);
        this.usedByPlayer(user);
        if (!world.isClientSide()) {
            //todo: Might not be performed
            WildMagicHelper.performRandomWildMagic(user, null, SpellPartTags.FRIENDLY, (wm, s, t, comp) -> wm.getQuality(comp) == Quality.VERY_BAD || wm.getQuality(comp) == Quality.VERY_GOOD, false);
        }
        user.getCooldowns().addCooldown(this, 800);
        return ret;
    }

    public ResourceLocation getFaction() {
        return FactionInit.WILD.getRegistryName();
    }
}
