package de.joh.fnc.common.event;

import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.FactionsAndCuriosities;
import de.joh.fnc.api.smite.SmiteHelper;
import de.joh.fnc.api.util.Quality;
import de.joh.fnc.api.wildmagic.WildMagicHelper;
import de.joh.fnc.common.effect.beneficial.ExplosionResistanceMobEffect;
import de.joh.fnc.common.effect.harmful.HexMobEffect;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.EnchantmentInit;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.item.BlackCatBraceletItem;
import de.joh.fnc.common.item.DivineArmorItem;
import de.joh.fnc.common.spell.shape.CounterAttackShape;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler for Forge-Events that revolve around damage and attacks
 * @author Joh0210
 */
@Mod.EventBusSubscriber(modid = FactionsAndCuriosities.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DamageEventHandler {
    /**
     * @see HexMobEffect
     * @see CounterAttackShape
     */
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity targetEntity = event.getEntity();
        MobEffectInstance instance = targetEntity.getEffect(EffectInit.HEX.get());
        if (instance != null && event.getAmount() >= 1){
            event.setAmount(Math.max(event.getAmount() * (1 + 0.25f * (instance.getAmplifier() + 1)), 1));
        }

        if(isMagic(event.getSource()) && targetEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DivineArmorItem divineArmor && divineArmor.isSetEquipped(targetEntity)){
            event.setAmount(event.getAmount() * (1.0f - 0.5f));
            if(targetEntity instanceof Player){
                divineArmor.usedByPlayer((Player) targetEntity);
            }
        }


        Entity attacker = event.getSource().getEntity();
        if(targetEntity instanceof Player && attacker instanceof LivingEntity){
            CounterAttackShape.applyCounterAttack((Player) targetEntity, (LivingEntity) attacker);
        }
    }

    /**
     * @see BlackCatBraceletItem
     */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if(event.getSource().getEntity() instanceof LivingEntity source
                && !source.level().isClientSide()
                && (event.getSource().is(SmiteHelper.getSmiteDamage()) || event.getSource().is(SmiteHelper.getMagicSmiteDamage()))
                && source.getMainHandItem().getItem() == ItemInit.BRIMSTONE_SWORD.get())
        {
            source.heal(event.getAmount()/2);
        }

        if (event.getSource().getEntity() instanceof Player source && source != event.getEntity() && !source.level().isClientSide()) {
            LivingEntity target = event.getEntity();
            if (source.getMainHandItem().isEmpty() && ((BlackCatBraceletItem) ItemInit.BLACK_CAT_BRACELET.get()).isEquippedAndHasMana(source, 20.0F, true)) {
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
        if (!event.getEntity().level().isClientSide) {
            //protection against explosions
            if (source.is(DamageTypeTags.IS_EXPLOSION) && event.getEntity().hasEffect(EffectInit.EXPLOSION_RESISTANCE.get())) {
                event.setCanceled(true);
                return;
            }

            //Smites
            if(source.getDirectEntity() instanceof Player && source.is(DamageTypes.PLAYER_ATTACK)){
                SmiteHelper.applySmite((Player) source.getDirectEntity(), event.getEntity(), ((Player) source.getDirectEntity()).getItemBySlot(EquipmentSlot.MAINHAND).getEnchantmentLevel(EnchantmentInit.HOLY_SMITE.get()) >= 1);
            }
            else if (source.getDirectEntity() instanceof AbstractArrow
                    && source.getDirectEntity().getPersistentData().getBoolean("fnc_smite_arrow")
                    && source.getEntity() instanceof Player
            ) {
                SmiteHelper.applySmite((Player) source.getEntity(), event.getEntity(), false);
            }
        }
    }

    //TODO: use normal damageSource.isMagic() when it's re added
    public static boolean isMagic(DamageSource damageSource){
        return damageSource.is(DamageTypeTags.BYPASSES_ARMOR);
    }
}
