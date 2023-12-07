package de.joh.fnc.common.event;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.common.effect.harmful.HexMobEffect;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.item.WildPickaxeItem;
import de.joh.fnc.api.util.ModTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler for Forge-Events that do not belong to any particular category
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {
    /**
     * @see HexMobEffect
     */
    @SubscribeEvent
    public static void onLivingHeal(final LivingHealEvent event){
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(EffectInit.HEX.get())){
            event.setCanceled(true);
        }
    }

    /**
     * Handles the random block of the WildPickaxe
     * @see WildPickaxeItem
     */
    @SubscribeEvent
    public static void onBreak(final BlockEvent.BreakEvent event){
        if(ModTags.isBlockIn(event.getState().getBlock(), ModTags.Blocks.ORES)
                && !event.isCanceled() && ForgeHooks.isCorrectToolForDrops(event.getState(), event.getPlayer())
                && (event.getPlayer().getMainHandItem().getItem() instanceof WildPickaxeItem || event.getPlayer().getOffhandItem().getItem() instanceof WildPickaxeItem || event.getPlayer().hasEffect(EffectInit.RANDOM_ORE_MINER.get()))){

            if(event.getPlayer().getMainHandItem().getItem() instanceof WildPickaxeItem wp){
                wp.usedByPlayer(event.getPlayer());
            }

            if(event.getPlayer().getOffhandItem().getItem() instanceof WildPickaxeItem wp){
                wp.usedByPlayer(event.getPlayer());
            }

            Block block = ModTags.getRandomBlocks(ModTags.Blocks.ORES);
            if(block != null) {
                event.setCanceled(true);

                //break effect:
                event.getLevel().setBlock(event.getPos(), block.defaultBlockState(), 3);
                event.getLevel().destroyBlock(event.getPos(), false, null);

                //random drop:
                Block.dropResources(block.defaultBlockState(), event.getPlayer().level, event.getPos(), null, event.getPlayer(), event.getPlayer().getMainHandItem());
            }
        }
    }
}
