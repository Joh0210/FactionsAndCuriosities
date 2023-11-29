package de.joh.fnc.api.event;

import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.base.ISpellDefinition;
import de.joh.fnc.api.smite.SmiteHelper;
import de.joh.fnc.api.smite.SmiteMobEffect;
import de.joh.fnc.common.capability.SmiteEntry;
import de.joh.fnc.common.spell.shape.PaladinSmiteShape;
import de.joh.fnc.common.util.CommonConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This event is fired when a Smite will occur.
 * <br>If the event is canceled, the Smite will not come into effect.
 * <br><br>This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 * @see SmiteHelper
 * @author Joh0210
 */
@Cancelable
public class PerformSmiteEvent extends PlayerEvent {
    /**
     * List of all Smites that will be applied
     */
    private final ArrayList<SmiteEntry> smites;

    public final LivingEntity target;

    /**
     * Damage Boosts that can stack beyond the Max Smite Damage
     */
    private int damageMod = 0;

    /**
     * Increases the maximum Damage Smites can deal by this mod
     * <br>Can be negative.
     */
    private int maxDamageMod = 0;

    /**
     * A Smite, which effects is defined by the attached Spell Effects
     * @see PaladinSmiteShape
     */
    private @Nullable ISpellDefinition smiteFromShape;

    public PerformSmiteEvent(@NotNull Player source, @NotNull LivingEntity target, @NotNull ArrayList<SmiteEntry> smites, @Nullable ISpellDefinition smiteFromShape) {
        super(source);
        this.target = target;
        this.smites = smites;
        this.smiteFromShape = smiteFromShape;
    }

    public void removeSmiteFromShape(){
        this.smiteFromShape = null;
    }

    public @Nullable ISpellDefinition getSmiteFromShape() {
        return smiteFromShape;
    }

    public Player getSource() {
        return super.getPlayer();
    }

    public int getDamageMod() {
        return damageMod;
    }

    public void setDamageMod(int damageMod) {
        this.damageMod = damageMod;
    }

    public void addDamageMod(int mod){
        damageMod += mod;
    }

    public void setMaxDamageMod(int maxDamageMod) {
        this.maxDamageMod = maxDamageMod;
    }

    public int getMaxDamageMod() {
        return maxDamageMod;
    }

    public void addMaxDamageMod(int maxDamageMod) {
        this.maxDamageMod += maxDamageMod;
    }

    public void addSmite(SmiteMobEffect smiteEffect, int damage, int range, int magnitude, int duration, int precision){
        Optional<SmiteEntry> optionalEntry = smites.stream().filter(s -> s.getSmite() == smiteEffect).findFirst();

        if(optionalEntry.isEmpty()){
            smites.add(new SmiteEntry(smiteEffect, damage, range, magnitude, duration, precision));
        } else {
            optionalEntry.get().overwrite(damage, range, magnitude, duration, precision);
        }
    }

    public void removeSmite(SmiteMobEffect smiteEffect){
        smites.removeIf(entry -> entry.getSmite() == smiteEffect);
    }

    @Nullable
    public SmiteEntry getSmite(SmiteMobEffect smiteEffect) {
        return smites.stream().filter(s -> s.getSmite() == smiteEffect).findFirst().orElse(null);
    }

    /**
     * The base value cannot exceed the amount defined by the {@link CommonConfig}. The {@link PerformSmiteEvent#damageMod damageMod} is not effected by this rule
     * @return Total amount of damage caused by the Smites.
     */
    public int getDamage(boolean ignoreCap){
        int dmgFromShape = 0;
        if(smiteFromShape != null && smiteFromShape.getShape() != null){
            dmgFromShape = Math.round(smiteFromShape.getShape().getValue(Attribute.DAMAGE));
        }

        int sum = smites.stream().mapToInt(SmiteEntry::getDamage).sum() + dmgFromShape;

        return Math.max(0, (ignoreCap ? sum : Math.min(sum, CommonConfig.MAX_SMITE_DAMAGE.get() + maxDamageMod)) + damageMod);
    }

    public ArrayList<SmiteEntry> getSmites() {
        return smites;
    }
}
