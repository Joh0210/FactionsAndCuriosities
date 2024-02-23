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
 * A new Faction with a default-Fighting-Focused and Smiting Theme
 * @author Joh0210
 */
public class PaladinFaction extends BaseFaction {
    public PaladinFaction() {
        super(ResourceIDs.FACTION_PALADIN_ID, ResourceIDs.PALADIN_MANA);
    }

    @Override
    public List<IFaction> getEnemyFactions() {
        return ManaAndArtificeMod.getFactionHelper().getFactionsExcept(this, Factions.COUNCIL);
    }

    @Override
    public ItemStack getFactionGrimoire() {
        //todo getFactionGrimoire
        return new ItemStack(ItemInit.GRIMOIRE_COUNCIL.get());
    }

    @Override
    public Item getTokenItem() {
        //todo getTokenItem
        return ItemInit.MARK_OF_THE_COUNCIL.get();
    }

    @Override
    public SoundEvent getRaidSound() {
        return SFX.Event.Faction.FACTION_RAID_COUNCIL;
    }

    @Nullable
    @Override
    public SoundEvent getHornSound() {
        return SFX.Event.Faction.FACTION_HORN_COUNCIL;
    }

    @Override
    public Component getOcculusTaskPrompt(int i) {
        return Component.translatable("fnc:rituals/pact");
    }

    @Override
    public ResourceLocation getFactionIcon() {
        return ResourceIDs.FACTION_PALADIN_ICON;
    }

    @Override
    public int[] getManaweaveRGB() {
        return new int[]{192, 192, 192};
    }

    @Override
    public ChatFormatting getTornJournalPageFactionColor() {
        return ChatFormatting.GRAY;
    }

    public ResourceLocation getSanctumStructure() {
        return RLoc.create("multiblock/council_circle");
    }
}
