package de.joh.fnc.common.spell.component;

import com.mna.api.affinity.Affinity;
import com.mna.api.config.GeneralConfigValues;
import com.mna.api.faction.IFaction;
import com.mna.api.sound.SFX;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IDamageComponent;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.util.BloodMagicHelper;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.util.RLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * A blood damage spell that deals damage and replenishes the caster's mana.
 * @author Joh0210
 */
public class BloodSacrificeComponent extends SpellEffect implements IDamageComponent {

    public BloodSacrificeComponent(ResourceLocation icon) {
        super(icon, new AttributeValuePair(Attribute.DAMAGE, 5.0F, 1.0F, 20.0F, 0.5F, 3.0F),
            new AttributeValuePair(Attribute.MAGNITUDE, 1.0F, 0.1F, 2.0F, 0.1F, 3.0F)
        );
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        if (context.getLevel().isClientSide()) {
            return ComponentApplicationResult.SUCCESS;
        }

        if (target.isEntity() && target.getEntity() instanceof LivingEntity entity) {
            float damage = modificationData.getValue(Attribute.DAMAGE) * Math.max((float) GeneralConfigValues.GlobalDamageScale, 0.1f);
            float efficiency = modificationData.getValue(Attribute.MAGNITUDE);

            DamageSource damageSource = new DamageSource(
                    context.getLevel().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE).getHolderOrThrow(
                            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.DAMAGE_TYPE, RLoc.create("blood_sacrifice"))
                    ),
                    source.getCaster()
            );

            entity.hurt(damageSource, damage);

            if (source.getCaster() instanceof Player caster) {
                // TODO: The damage might be reduced by enchantments or other effects. 
                //  Currently, we use the theoretical damage for mana replenishment.
                BloodMagicHelper.regenerateMana(caster, entity, damage * efficiency);
            }

            return ComponentApplicationResult.SUCCESS;
        }

        return ComponentApplicationResult.FAIL;
    }

    @Override
    public Affinity getAffinity() {
        return Affinity.BLOOD;
    }

    @Override
    public float initialComplexity() {
        return 10.0F;
    }

    @Override
    public int requiredXPForRote() {
        return 500;
    }

    @Override
    public SpellPartTags getUseTag() {
        return SpellPartTags.HARMFUL;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.BLOOD_MAGE;
    }

    @Override
    public boolean targetsBlocks() {
        return false;
    }

    @Override
    public SoundEvent SoundEffect() {
        return SFX.Spell.Buff.BLOOD;
    }
}