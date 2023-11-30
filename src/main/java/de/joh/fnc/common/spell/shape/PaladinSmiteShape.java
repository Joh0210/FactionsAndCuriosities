package de.joh.fnc.common.spell.shape;

import com.mna.api.faction.IFaction;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.common.capability.PlayerCapabilityProvider;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.item.DivineArmorItem;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.List;

public class PaladinSmiteShape extends Shape {
    public PaladinSmiteShape(ResourceLocation registryName, ResourceLocation guiIcon) {
        super(registryName, guiIcon, new AttributeValuePair(Attribute.DAMAGE, 1.0F, 1.0F, 5.0F, 1F, 20F));
    }

    public List<SpellTarget> Target(SpellSource source, Level world, IModifiedSpellPart<Shape> modificationData, ISpellDefinition recipe) {
        if (source.getPlayer() == null) {
            return Collections.singletonList(SpellTarget.NONE);
        } else {
            source.getPlayer().getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(smiteCapability -> smiteCapability.addSmiteFromShape(recipe));

            float mod = 1.0f;

            if(source.getPlayer().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DivineArmorItem divineArmor && divineArmor.isSetEquipped(source.getPlayer())){
                mod += 0.5f;
                divineArmor.usedByPlayer(source.getPlayer());
            }

            MobEffectInstance instance = source.getPlayer().getEffect(EffectInit.PALADIN_SMITE.get());
            if(instance == null){
                source.getPlayer().addEffect(new MobEffectInstance(EffectInit.PALADIN_SMITE.get(), (int)(CommonConfig.SMITE_DURATION.get() * 20 * mod), 0));
            }
            else {
                //Update the duration of the effect
                instance.update(new MobEffectInstance(EffectInit.PALADIN_SMITE.get(), (int)(CommonConfig.SMITE_DURATION.get() * 20 * mod), 0));
            }

            return List.of(new SpellTarget(source.getCaster()));
        }
    }

    /**
     * No entity will be spawned, but the Effect will not be applied immediately
     */
    public boolean spawnsTargetEntity() {
        return true;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.PALADIN;
    }
    @Override
    public boolean canBeOnRandomStaff() {
        return false;
    }

    public float initialComplexity() {
        return 80.0F;
    }

    public int requiredXPForRote() {
        return 300;
    }
}
