package de.joh.fnc.compat.dmnr.client.gui;

import com.mna.inventory.ItemInventoryBase;
import de.joh.dmnr.client.gui.ContainerDragonMageArmor;
import de.joh.fnc.compat.dmnr.client.init.AddonDmnrContainerInit;
import de.joh.fnc.compat.dmnr.common.init.AddonDmnrItemInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ContainerMischiefDragonMageArmor extends ContainerDragonMageArmor {
    public ContainerMischiefDragonMageArmor(int i, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(i, playerInventory, new ItemInventoryBase(new ItemStack(AddonDmnrItemInit.MISCHIEF_DRAGON_MAGE_CHESTPLATE.get(), 2), 2));
    }

    public ContainerMischiefDragonMageArmor(int i, Inventory playerInv, ItemInventoryBase basebag) {
        super(AddonDmnrContainerInit.MISCHIEF_DRAGON_MAGE_CHESTPLATE.get(), i, playerInv, basebag);
    }
}
