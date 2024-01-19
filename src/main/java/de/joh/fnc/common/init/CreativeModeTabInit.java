package de.joh.fnc.common.init;

import de.joh.fnc.FactionsAndCuriosities;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FactionsAndCuriosities.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FNC_TAB = CREATIVE_MODE_TABS.register("fnc",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.BRIMSTONE_SWORD.get()))
                    .title(Component.translatable("itemGroup.fnc"))
                    .noScrollBar()
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ItemInit.DEBUG_ROD.get());
                        pOutput.accept(ItemInit.DEBUG_ROD_SPELL_ADJUSTMENT.get());
                        pOutput.accept(ItemInit.DEBUG_ROD_SMITE.get());

                        pOutput.accept(ItemInit.DICE.get());
                        pOutput.accept(ItemInit.DICE_20.get());

                        pOutput.accept(ItemInit.MISCHIEF_HELMET.get());
                        pOutput.accept(ItemInit.MISCHIEF_CHESTPLATE.get());
                        pOutput.accept(ItemInit.MISCHIEF_LEGGING.get());
                        pOutput.accept(ItemInit.MISCHIEF_BOOTS.get());
                        pOutput.accept(ItemInit.WILD_PICKAXE.get());
                        pOutput.accept(ItemInit.DECK_OF_MANY.get());
                        pOutput.accept(ItemInit.GLITTERING_POTATO.get());
                        pOutput.accept(ItemInit.BLACK_CAT_BRACELET.get());
                        pOutput.accept(ItemInit.FOUR_LEAF_CLOVER_RING.get());

                        pOutput.accept(ItemInit.DIVINE_HELMET.get());
                        pOutput.accept(ItemInit.DIVINE_CHESTPLATE.get());
                        pOutput.accept(ItemInit.DIVINE_LEGGING.get());
                        pOutput.accept(ItemInit.DIVINE_BOOTS.get());
                        pOutput.accept(ItemInit.BRIMSTONE_SWORD.get());
                        pOutput.accept(ItemInit.BLESSED_BOW.get());
                        pOutput.accept(ItemInit.SMITING_RING.get());
                        pOutput.accept(ItemInit.BLOOD_LUST_BRACELET.get());
                    })
                    .build());

    public static void register(IEventBus pOutputBus) {
        CREATIVE_MODE_TABS.register(pOutputBus);
    }
}
