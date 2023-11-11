package de.joh.fnc.compat.dmnr.item;

import de.joh.dragonmagicandrelics.item.items.dragonmagearmor.DragonMageArmor;
import de.joh.fnc.compat.dmnr.gui.NamedMischiefDragonMageArmor;
import de.joh.fnc.utils.RLoc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class MischiefDragonMageArmor extends DragonMageArmor {
    public MischiefDragonMageArmor(EquipmentSlot slot) {
        super(slot, RLoc.create("fnc_mischief_armor_set_bonus"));
    }

    public MenuProvider getProvider(ItemStack itemStack) {
        return this.slot == EquipmentSlot.CHEST ? new NamedMischiefDragonMageArmor(itemStack) : null;
    }

    public ResourceLocation getWingTextureLocation() {
        return RLoc.create("textures/models/armor/mischief_dragon_wing.png");
    }

    public ResourceLocation getTextureLocation() {
        return RLoc.create("textures/models/armor/mischief_dragon_mage_armor_texture.png");
    }
}
