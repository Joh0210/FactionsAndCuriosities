package de.joh.fnc.common.faction;

import com.mna.api.ManaAndArtificeMod;
import com.mna.api.capabilities.IPlayerMagic;
import com.mna.api.capabilities.IPlayerProgression;
import com.mna.api.faction.BaseFaction;
import com.mna.api.faction.IFaction;
import com.mna.api.sound.SFX;
import com.mna.api.tools.RLoc;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import com.mna.factions.Factions;
import com.mna.items.ItemInit;
import de.joh.fnc.common.faction.castingresource.PaladinShieldCastingResource;
import de.joh.fnc.common.faction.castingresource.PaladinSwordCastingResource;
import de.joh.fnc.common.init.FactionInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A new Faction with a default-Fighting-Focused and Smiting Theme
 * @author Joh0210
 */
public class PaladinFaction extends BaseFaction {
    public PaladinFaction() {
        super(ResourceIDs.PALADIN_MANA_SHIELD, ResourceIDs.PALADIN_MANA_SWORD);
    }

    public static boolean eventHandlePaladinDamageAdjustment(LivingHurtEvent event){
        if(event.isCanceled()) return true;

        float amount = event.getAmount();
        boolean adjusted = false;

        if(event.getEntity() instanceof Player defender){
            IPlayerProgression progression = defender.getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
            IPlayerMagic magic = defender.getCapability(PlayerMagicProvider.MAGIC).orElse(null);

            if(progression != null && magic != null && progression.getAlliedFaction() == FactionInit.PALADIN && magic.getCastingResource() instanceof PaladinShieldCastingResource){
                amount = amount*0.85f;
                adjusted = true;
            }
        }

        if(event.getSource().getEntity() instanceof Player attacker){
            IPlayerProgression progression = attacker.getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
            IPlayerMagic magic = attacker.getCapability(PlayerMagicProvider.MAGIC).orElse(null);
            if(progression != null && magic != null && progression.getAlliedFaction() == FactionInit.PALADIN && magic.getCastingResource() instanceof PaladinSwordCastingResource){
                amount = amount*1.15f;
                adjusted = true;
            }
        }

        if (adjusted && amount < 0.25f){
            event.setCanceled(true);
            return true;
        }

        event.setAmount(amount);
        return false;
    }

    @Override
    public List<IFaction> getEnemyFactions() {
        return ManaAndArtificeMod.getFactionHelper().getFactionsExcept(this, Factions.COUNCIL);
    }

    @Override
    public ItemStack getFactionGrimoire() {
        return new ItemStack(ItemInit.GRIMOIRE_COUNCIL.get());
    }

    @Override
    public Item getTokenItem() {
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
