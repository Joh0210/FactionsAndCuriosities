package de.joh.fnc.common.item;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.faction.IFaction;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.parts.SpellEffect;
import de.joh.fnc.api.event.AddSmiteEvent;
import de.joh.fnc.common.init.FactionInit;
import de.joh.fnc.common.init.ItemInit;
import de.joh.fnc.common.util.CommonConfig;
import de.joh.tnl.api.item.BaseDragonMagicItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import com.mna.api.items.IFactionSpecific;
import top.theillusivec4.curios.api.CuriosApi;


import java.util.List;

/**
 * Extends the {@link Attribute#DURATION} of the beneficial spell components the wearer casts (also beyond their maximum)
 * and how long the wearer's Smites stay charged.
 * <br>Boost: {@link CommonConfig#TIME_BELT_DURATION_BOOST}
 * <br>Dragon Magic: The boost is doubled.
 * @author Joh0210
 */
public class TimeBeltItem extends BaseDragonMagicItem implements IFactionSpecific {
    public TimeBeltItem() {
        super((new Properties()).setNoRepair().stacksTo(1));
    }

    @Override
    public IFaction getFaction() {
        return FactionInit.PALADIN;
    }

    @Override
    public String dragonMagicID() {
        return "item.fnc.time_belt.dragonmagic";
    }

    /**
     * @return The boost of the Belt of Time for this wearer in percent: {@link CommonConfig#TIME_BELT_DURATION_BOOST}, doubled with Dragon Magic.
     */
    private static int getDurationBoost(LivingEntity wearer) {
        int boost = CommonConfig.TIME_BELT_DURATION_BOOST.get();
        return ((TimeBeltItem) ItemInit.TIME_BELT.get()).hasDragonMagic(wearer) ? boost * 2 : boost;
    }

    private static boolean isBeneficial(SpellPartTags useTag) {
        return useTag != SpellPartTags.HARMFUL;
    }

    private static boolean isEquipped(LivingEntity entity) {
        return !CuriosApi.getCuriosHelper().findCurios(entity, ItemInit.TIME_BELT.get()).isEmpty();
    }

    public static void eventSpellCast(SpellCastEvent event) {
        LivingEntity caster = event.getSource().getCaster();
        if (caster == null || !isEquipped(caster)) return;

        List<IModifiedSpellPart<SpellEffect>> components = event.getSpell().getComponents().stream()
                .filter(modifiedSpellPart -> isBeneficial(modifiedSpellPart.getPart().getUseTag()))
                .filter(modifiedSpellPart -> modifiedSpellPart.getContainedAttributes().contains(Attribute.DURATION))
                .toList();
        if (components.isEmpty()) return;

        // getValue() already contains the multiplier of M&A, so the value without it is scaled
        float factor = 1 + getDurationBoost(caster) * 0.01f;
        components.forEach(modifiedSpellPart -> modifiedSpellPart.setValue(Attribute.DURATION, modifiedSpellPart.getValueWithoutMultipliers(Attribute.DURATION) * factor));
        if (caster instanceof Player player) ((TimeBeltItem) ItemInit.TIME_BELT.get()).usedByPlayer(player);
    }

    public static void eventAddSmite(AddSmiteEvent event) {
        Player player = event.getEntity();
        if (!isEquipped(player)) return;

        event.addDuration((int)(event.getDuration() * getDurationBoost(player) * 0.01f));
        ((TimeBeltItem) ItemInit.TIME_BELT.get()).usedByPlayer(player);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("item.fnc.time_belt.description", CommonConfig.TIME_BELT_DURATION_BOOST.get()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("  "));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
