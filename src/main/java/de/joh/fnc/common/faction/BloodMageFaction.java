package de.joh.fnc.common.faction;

import com.mna.api.ManaAndArtificeMod;
import com.mna.api.faction.BaseFaction;
import com.mna.api.faction.IFaction;
import com.mna.api.sound.SFX;
import com.mna.api.tools.RLoc;
import com.mna.factions.Factions;
import com.mna.items.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A new Faction with a Blood Mage theme
 * @author Joh0210
 */
public class BloodMageFaction extends BaseFaction {
    public BloodMageFaction() {
        super(ResourceIDs.BLOOD_MAGIC);
    }

    @Override
    public List<IFaction> getEnemyFactions() {
        return ManaAndArtificeMod.getFactionHelper().getFactionsExcept(this, Factions.UNDEAD);
    }

    @Override
    public ItemStack getFactionGrimoire() {
        //todo getFactionGrimoire
        return new ItemStack(ItemInit.GRIMOIRE_UNDEAD.get());
    }

    @Override
    public Item getTokenItem() {
        //todo getTokenItem
        return ItemInit.MARK_OF_THE_UNDEAD.get();
    }

    @Override
    public SoundEvent getRaidSound() {
        return SFX.Event.Faction.FACTION_RAID_DEMONS;
    }

    @Nullable
    @Override
    public SoundEvent getHornSound() {
        return SFX.Event.Faction.FACTION_HORN_UNDEAD;
    }

    @Override
    public Component getOcculusTaskPrompt(int i) {
        return Component.translatable("fnc:rituals/crimson_offering");
    }

    @Override
    public ResourceLocation getFactionIcon() {
        return ResourceIDs.FACTION_BLOOD_MAGE_ICON;
    }

    @Override
    public int[] getManaweaveRGB() {
        return new int[]{136, 0, 0};
    }

    @Override
    public ChatFormatting getTornJournalPageFactionColor() {
        return ChatFormatting.DARK_RED;
    }

    public ResourceLocation getSanctumStructure() {
        return RLoc.create("multiblock/undead_circle");
    }
}
