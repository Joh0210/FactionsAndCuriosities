package de.joh.fnc.compat.dmnr.gui;

import com.mna.inventory.ItemInventoryBase;
import de.joh.dragonmagicandrelics.gui.ContainerDragonMageArmor;
import de.joh.fnc.compat.dmnr.item.AddonDragonMagicAndRelicsItems;
import de.joh.fnc.item.ItemInit;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ContainerMischiefDragonMageArmor extends ContainerDragonMageArmor {
    public ContainerMischiefDragonMageArmor(int i, Inventory playerInventory) {
        this(i, playerInventory, new ItemInventoryBase(new ItemStack(AddonDragonMagicAndRelicsItems.MISCHIEF_DRAGON_MAGE_CHESTPLATE.get(), 2), 2));
    }

    public ContainerMischiefDragonMageArmor(int i, Inventory playerInv, ItemInventoryBase basebag) {
        super(AddonDragonMagicAndRelicsContainerInit.MISCHIEF_DRAGON_MAGE_CHESTPLATE, i, playerInv, basebag);
    }
}
