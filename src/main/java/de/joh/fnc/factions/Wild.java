package de.joh.fnc.factions;

import com.mna.api.ManaAndArtificeMod;
import com.mna.api.faction.BaseFaction;
import com.mna.api.faction.FactionIDs;
import com.mna.api.faction.IFaction;
import com.mna.api.sound.SFX;
import com.mna.items.ItemInit;
import de.joh.fnc.factions.castingresource.ResourceIDs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wild extends BaseFaction {
    public Wild() {
        super(ResourceIDs.FACTION_WILD_ID, ResourceIDs.WILD_MANA);
    }

    @Override
    public List<IFaction> getEnemyFactions() {
        return ManaAndArtificeMod.getFactionHelper().getFactionsExcept(this.getRegistryName(), FactionIDs.FEY);
    }

    @Override
    public ItemStack getFactionGrimoire() {
        //todo getFactionGrimoire
        return new ItemStack(ItemInit.GRIMOIRE_FEY.get());
    }

    @Override
    public Item getTokenItem() {
        //todo getTokenItem
        return ItemInit.MARK_OF_THE_FEY.get();
    }

    @Override
    public SoundEvent getRaidSound() {
        return SFX.Event.Faction.FACTION_RAID_FEY;
    }

    @Nullable
    @Override
    public SoundEvent getHornSound() {
        return SFX.Event.Faction.FACTION_HORN_FEY;
    }

    @Override
    public Component getOcculusTaskPrompt(int i) {
        return new TranslatableComponent("fnc:rituals/wild_energy");
    }

    @Override
    public ResourceLocation getFactionIcon() {
        return ResourceIDs.FACTION_WILD_ICON;
    }

    @Override
    public int[] getManaweaveRGB() {
        return new int[]{218, 165, 32};
    }

    @Override
    public ChatFormatting getTornJournalPageFactionColor() {
        return ChatFormatting.GOLD;
    }
}
