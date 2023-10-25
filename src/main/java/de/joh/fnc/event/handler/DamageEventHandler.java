package de.joh.fnc.event.handler;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.effect.EffectInit;
import de.joh.fnc.item.ItemInit;
import de.joh.fnc.item.init.BlackCatBracelet;
import de.joh.fnc.wildmagic.util.Quality;
import de.joh.fnc.wildmagic.util.WildMagicHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DamageEventHandler {
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player source && source != event.getEntity() && !source.getLevel().isClientSide()) {
            LivingEntity target = event.getEntityLiving();
            if (source.isShiftKeyDown() && source.getMainHandItem().isEmpty() && ((BlackCatBracelet) ItemInit.MISCHIEF_BRACELET.get()).isEquippedAndHasMana(source, 20.0F, true)) {
               WildMagicHelper.performRandomWildMagic(target, new SpellTarget(target), SpellPartTags.FRIENDLY,(wm, s, t, ct) -> wm.getQuality(SpellPartTags.FRIENDLY).ordinal() <= Quality.NEUTRAL.ordinal());
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        DamageSource source = event.getSource();
        if (!event.getEntityLiving().level.isClientSide) {
            //protection against explosions
            if (source.isExplosion() && event.getEntityLiving().hasEffect(EffectInit.EXPLOSION_RESISTANCE.get())) {
                event.setCanceled(true);
            }
        }
    }
}
