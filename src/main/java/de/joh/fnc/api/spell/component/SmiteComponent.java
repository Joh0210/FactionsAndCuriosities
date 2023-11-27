package de.joh.fnc.api.spell.component;

import com.mna.api.affinity.Affinity;
import com.mna.api.faction.IFaction;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import de.joh.fnc.api.smite.SmiteHelper;
import de.joh.fnc.api.smite.SmiteMobEffect;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Gives the Target (if it is a Player) a Smite Mob Effect.
 * <br>The Duration of the SmiteMobEffect is defined by the {@link CommonConfig}; (Base 30s)
 * @see SmiteMobEffect
 * @see SmiteComponent
 * @author Joh0210
 */
public class SmiteComponent extends SpellEffect {
    private final int initialComplexity;
    private final Affinity affinity;
    private final RegistryObject<SmiteMobEffect> smite;

    public SmiteComponent(ResourceLocation registryName, ResourceLocation guiIcon, int initialComplexity, Affinity affinity, RegistryObject<SmiteMobEffect> smite, AttributeValuePair... attributeValuePairs) {
        super(registryName, guiIcon, adjustAttributes(attributeValuePairs));
        this.initialComplexity = initialComplexity;
        this.affinity = affinity;
        this.smite = smite;
    }

    public static AttributeValuePair[] adjustAttributes(AttributeValuePair... attributeValuePairs){
        ArrayList<AttributeValuePair> list = new ArrayList<>(Arrays.asList(attributeValuePairs));
        if (list.stream().noneMatch((m) -> m.getAttribute() == Attribute.DELAY)) {
            list.add(new AttributeValuePair(Attribute.DAMAGE, 1.0F, 1.0F, 5.0F, 1F, 20F));
        }
        return list.toArray(new AttributeValuePair[0]);
    }

    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        if(target.getLivingEntity() instanceof Player player){
            SmiteHelper.addSmite(
                    player,
                    smite.get(),
                    Math.round(modificationData.getValue(Attribute.DAMAGE)),
                    Math.round(modificationData.getValue(Attribute.RANGE)),
                    Math.round(modificationData.getValue(Attribute.MAGNITUDE)),
                    Math.round(modificationData.getValue(Attribute.DURATION)),
                    Math.round(modificationData.getValue(Attribute.PRECISION)));

            return ComponentApplicationResult.SUCCESS;
        } else {
            if(source.getPlayer() != null && !source.getPlayer().getLevel().isClientSide){
                source.getPlayer().sendMessage(new TranslatableComponent("fnc.component.smite.no_player.error"), Util.NIL_UUID);
            }
            return ComponentApplicationResult.FAIL;
        }
    }

    @Override
    public boolean targetsBlocks() {
        return false;
    }

    @Override
    public IFaction getFactionRequirement() {
        return FactionInit.PALADIN;
    }

    @Override
    public Affinity getAffinity() {
        return affinity;
    }

    @Override
    public float initialComplexity() {
        return initialComplexity;
    }

    @Override
    public int requiredXPForRote() {
        return 250;
    }

    @Override
    public SpellPartTags getUseTag() {
        return SpellPartTags.UTILITY;
    }

    @Override
    public List<Affinity> getValidTinkerAffinities() {
        return List.of(Affinity.ARCANE);
    }

    @Override
    public boolean canBeOnRandomStaff() {
        return false;
    }
}
