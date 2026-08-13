package de.joh.fnc.common.item;

import de.joh.fnc.api.util.BloodMagicHelper;
import de.joh.fnc.common.util.RLoc;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * An item that allows the player to sacrifice their own health for blood magic mana.
 * @author Joh0210
 */
public class SacrificialDaggerItem extends Item {
    public SacrificialDaggerItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if (!level.isClientSide) {
            float damageAmount = 2.0f;
            float efficiency = 4.0f;

            DamageSource damageSource = new DamageSource(
                    level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(
                            ResourceKey.create(Registries.DAMAGE_TYPE, RLoc.create("blood_sacrifice"))
                    ),
                    player
            );

            player.hurt(damageSource, damageAmount);
            BloodMagicHelper.regenerateMana(player, player, damageAmount * efficiency);
            
            player.getCooldowns().addCooldown(this, 10);
        }
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
