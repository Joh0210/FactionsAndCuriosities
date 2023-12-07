package de.joh.fnc.common.spell.shape;

import com.mna.api.faction.IFaction;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.event.AddSmiteEvent;
import de.joh.fnc.common.capability.PlayerCapabilityProvider;
import de.joh.fnc.common.init.EffectInit;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

import java.util.Collections;
import java.util.List;

/**
 * This shape behaves like the other Smite Spell cast with the Self-Shape. However, the effect that this Smite triggers is determined by the Player
 * @author Joh0210
 */
public class PaladinSmiteShape extends Shape {
    public PaladinSmiteShape(ResourceLocation registryName, ResourceLocation guiIcon) {
        super(registryName, guiIcon, new AttributeValuePair(Attribute.DAMAGE, 1.0F, 1.0F, 5.0F, 1F, 20F));
    }

    public List<SpellTarget> Target(SpellSource source, Level world, IModifiedSpellPart<Shape> modificationData, ISpellDefinition recipe) {
        if (source.getPlayer() != null) {
            AddSmiteEvent event = new AddSmiteEvent(source.getPlayer());
            MinecraftForge.EVENT_BUS.post(event);
            if (!event.isCanceled()) {
                source.getPlayer().addEffect(new MobEffectInstance(EffectInit.PALADIN_SMITE.get(), event.getDuration(), 0));
                source.getPlayer().getCapability(PlayerCapabilityProvider.PLAYER_SMITE).ifPresent(smiteCapability -> smiteCapability.addSmiteFromShape(recipe));

                return List.of(new SpellTarget(source.getCaster()));
            }

        }
        return Collections.singletonList(SpellTarget.NONE);
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
