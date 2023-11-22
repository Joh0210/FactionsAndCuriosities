package de.joh.fnc.common.event;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.api.smite.SmiteHelper;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.effect.beneficial.ExplosionResistanceMobEffect;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.item.BlackCatBraceletItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler for Forge-Events that revolve around damage and attacks
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DamageEventHandler {
    /**
     * @see BlackCatBraceletItem
     */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player source && source != event.getEntity() && !source.getLevel().isClientSide()) {
            LivingEntity target = event.getEntityLiving();
            if (source.isShiftKeyDown() && source.getMainHandItem().isEmpty() && ((BlackCatBraceletItem) ItemInit.BLACK_CAT_BRACELET.get()).isEquippedAndHasMana(source, 20.0F, true)) {
               WildMagicHelper.performRandomWildMagic(target, new SpellTarget(target), SpellPartTags.FRIENDLY,(wm, s, t, ct) -> wm.getQuality(SpellPartTags.FRIENDLY).ordinal() <= Quality.NEUTRAL.ordinal());
            }
        }
    }

    /**
     * @see ExplosionResistanceMobEffect
     * @see SmiteHelper
     */
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        DamageSource source = event.getSource();
        if (!event.getEntityLiving().level.isClientSide) {
            //protection against explosions
            if (source.isExplosion() && event.getEntityLiving().hasEffect(EffectInit.EXPLOSION_RESISTANCE.get())) {
                event.setCanceled(true);
                return;
            }

            //Smites
            //todo: only with special weapons?
            if(source.getDirectEntity() instanceof Player && source.msgId.equals("player")){
                SmiteHelper.applySmite((Player) source.getDirectEntity(), event.getEntityLiving());
            }
        }
    }
}
