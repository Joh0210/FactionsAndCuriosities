package de.joh.fnc.mixins;


import com.mna.api.capabilities.IPlayerMagic;

import com.mna.api.capabilities.IPlayerProgression;
import com.mna.api.capabilities.resource.ICastingResourceGuiProvider;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import com.mna.capabilities.playerdata.magic.resources.CastingResourceGuiRegistry;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import com.mna.gui.HUDOverlayRenderer;

import de.joh.fnc.common.faction.ResourceIDs;
import de.joh.fnc.common.util.RLoc;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HUDOverlayRenderer.class)
public class CastingResourceMixin {


    @ModifyArg(
            method = "renderManaBar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
                    ordinal = 0,
                    remap = true
            ),
            index = 0
    )
    private ResourceLocation hemomancy$useProviderTexture(ResourceLocation originalTexture) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            IPlayerMagic magic = player.getCapability(PlayerMagicProvider.MAGIC).orElse(null);
            IPlayerProgression progression = player.getCapability(PlayerProgressionProvider.PROGRESSION).orElse(null);
            if (magic.isMagicUnlocked()) {
                ICastingResourceGuiProvider provider = CastingResourceGuiRegistry.Instance
                        .getGuiProvider(magic.getCastingResource().getRegistryName());
                if (provider != null) {
                    if(progression.getAlliedFaction() != null) {
                        if(progression.getAlliedFaction().is(ResourceIDs.FACTION_PALADIN_ID) || progression.getAlliedFaction().is(ResourceIDs.FACTION_WILD_ID) || progression.getAlliedFaction().is(ResourceIDs.FACTION_BLOOD_MAGE_ID)) {
                            return RLoc.create("gui/resource_bars.png");
                        }
                    }
                }
            }
        }
        return originalTexture;
    }

}


