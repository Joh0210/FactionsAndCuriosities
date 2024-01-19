package de.joh.fnc.compat.dmnr.common.item;

import de.joh.dmnr.api.item.DragonMageArmorItem;
import de.joh.fnc.common.util.RLoc;
import de.joh.fnc.compat.dmnr.client.gui.NamedDivineDragonMageArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class DivineDragonMageArmorItem extends DragonMageArmorItem {
    public DivineDragonMageArmorItem(ArmorItem.Type type) {
        super(type, RLoc.create("fnc_divine_armor_set_bonus"));
    }

    public MenuProvider getProvider(ItemStack itemStack) {
        return this.getType() == Type.CHESTPLATE ? new NamedDivineDragonMageArmor(itemStack) : null;
    }

    public ResourceLocation getWingTextureLocation() {
        return RLoc.create("textures/models/armor/divine_dragon_wing.png");
    }

    public ResourceLocation getTextureLocation() {
        return RLoc.create("textures/models/armor/divine_dragon_mage_armor_texture.png");
    }
}
