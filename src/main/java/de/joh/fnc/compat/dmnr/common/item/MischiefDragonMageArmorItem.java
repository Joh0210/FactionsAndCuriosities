package de.joh.fnc.compat.dmnr.common.item;

import de.joh.dmnr.api.item.DragonMageArmorItem;
import de.joh.fnc.common.util.RLoc;
import de.joh.fnc.compat.dmnr.client.gui.NamedMischiefDragonMageArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class MischiefDragonMageArmorItem extends DragonMageArmorItem {
    public MischiefDragonMageArmorItem(ArmorItem.Type type) {
        super(type, RLoc.create("fnc_mischief_armor_set_bonus"));
    }

    public MenuProvider getProvider(ItemStack itemStack) {
        return this.getType() == Type.CHESTPLATE ? new NamedMischiefDragonMageArmor(itemStack) : null;
    }

    public ResourceLocation getWingTextureLocation() {
        return RLoc.create("textures/models/armor/mischief_dragon_wing.png");
    }

    public ResourceLocation getTextureLocation() {
        return RLoc.create("textures/models/armor/mischief_dragon_mage_armor_texture.png");
    }
}
