package de.joh.fnc.event.handler;

import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.item.init.WildPickaxe;
import de.joh.fnc.utils.ModTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler for Forge-Events that do not belong to any particular category
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {

    /**
     * Handles the random block of the WildPickaxe
     * @see WildPickaxe
     */
    @SubscribeEvent
    public static void onBreak(final BlockEvent.BreakEvent event){
        if(ModTags.isBlockIn(event.getState().getBlock(), ModTags.Blocks.ORES)
                && !event.isCanceled() && ForgeHooks.isCorrectToolForDrops(event.getState(), event.getPlayer())
                && (event.getPlayer().getMainHandItem().getItem() instanceof WildPickaxe || event.getPlayer().hasEffect(EffectInit.RANDOM_ORE_MINER.get()))){
            Block block = ModTags.getRandomBlocks(ModTags.Blocks.ORES);
            if(block != null) {
                event.setCanceled(true);

                //break effect:
                event.getWorld().setBlock(event.getPos(), block.defaultBlockState(), 3);
                event.getWorld().destroyBlock(event.getPos(), false, null);

                //random drop:
                Block.dropResources(block.defaultBlockState(), event.getPlayer().level, event.getPos(), null, event.getPlayer(), event.getPlayer().getMainHandItem());
            }
        }
    }
}
