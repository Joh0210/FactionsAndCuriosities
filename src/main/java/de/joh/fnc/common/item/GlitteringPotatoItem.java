package de.joh.fnc.common.item;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.ITieredItem;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.util.CommonConfig;
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
public class GlitteringPotatoItem extends Item implements ITieredItem<GlitteringPotatoItem>, IFactionSpecific {
    private int _tier = -1;

    //todo: not traceable by broker
    private static final boolean BREAKS_BLOCKS = false;

    public GlitteringPotatoItem() {
        super(new Item.Properties().rarity(Rarity.COMMON)
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

        if(!entity.level().isClientSide){
            for(int j = 0; j < (Math.abs(WildMagicHelper.getWildMagicLuck(entity)) + 1); j++) {
                heal = (random.nextInt(100) + 1) > CommonConfig.GLITTERING_POTATO_HURT_CHANCE.get();

                if(chooseHigher == heal){
                    break;
                }
            }

            if(heal){
                entity.heal(CommonConfig.GLITTERING_POTATO_HEAL_AMOUNT.get());
            } else {
                Vec3 coordinates = entity.position();
                entity.level().explode(null, null, null, coordinates.x, coordinates.y, coordinates.z, 2, false, (((ServerLevel)entity.level()).getServer().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && BREAKS_BLOCKS) ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE);
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

    @Override
    public IFaction getFaction() {
        return FactionInit.WILD;
    }
}
