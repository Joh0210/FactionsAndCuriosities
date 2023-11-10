package de.joh.fnc.item.init;

import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.ITieredItem;
import de.joh.fnc.factions.FactionInit;
import de.joh.fnc.utils.CreativeModeTabInit;
import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * A Potato that could heal the target by 4 HP (80% Chance) or to cause a tiny explosion (20% Chance)
 * @author Joh0210
 */
public class GlitteringPotato extends Item implements ITieredItem<GlitteringPotato>, IFactionSpecific {
    private int _tier = -1;

    //todo: not traceable by broker

    //todo: adjustable via Config
    private static final int AMOUNT = 4;
    private static final int HURT_CHANCE = 20;
    private static final boolean BREAKS_BLOCKS = false;

    public GlitteringPotato() {
        super(new Item.Properties().tab(CreativeModeTabInit.FACTIONS_AND_CURIOSITIES).rarity(Rarity.COMMON)
                .food((new FoodProperties.Builder()).nutrition(5).saturationMod(0.6F).alwaysEat().build()));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
        boolean chooseHigher = WildMagicHelper.getWildMagicLuck(entity) >= 0;
        boolean heal = true;
        Random random = new Random();

        if(entity instanceof Player){
            this.usedByPlayer((Player) entity);
        }

        if(!entity.getLevel().isClientSide){
            for(int j = 0; j < (Math.abs(WildMagicHelper.getWildMagicLuck(entity)) + 1); j++) {
                heal = (random.nextInt(100) + 1) > HURT_CHANCE;

                if(chooseHigher == heal){
                    break;
                }
            }

            if(heal){
                entity.heal(AMOUNT);
            } else {
                Vec3 coordinates = entity.position();
                entity.getLevel().explode(null, null, null, coordinates.x, coordinates.y, coordinates.z, 2, false, (((ServerLevel)entity.getLevel()).getServer().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && BREAKS_BLOCKS) ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
            }
        }

        return super.finishUsingItem(itemstack, world, entity);
    }

    @Override
    public void setCachedTier(int tier) {
        this._tier = tier;
    }

    @Override
    public int getCachedtier() {
        return this._tier;
    }

    public ResourceLocation getFaction() {
        return FactionInit.WILD.getRegistryName();
    }
}
