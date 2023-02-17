package character;

import equipment.*;

import java.util.Arrays;
import java.util.List;

/**
 * Represents Rogue class. Subclass of Hero.
 */
public class Rogue extends Hero {

    /**
     * Creates Rogue with given name. Rogue has distinct attribute composition that emphasizes dexterity.
     * Rogue may use weapons of type Dagger, Sword and equip armors of type Leather and Mail.
     *
     * @param name name of rogue
     */
    public Rogue(String name) {
        super(name);

        HeroAttribute baseAttributes = new HeroAttribute(2, 6, 1);
        HeroAttribute levelUpAttributes = new HeroAttribute(1, 4, 1);

        List<WeaponType> validWeapons = Arrays.asList(WeaponType.Dagger, WeaponType.Sword);
        List<ArmorType> validArmors = Arrays.asList(ArmorType.Leather, ArmorType.Mail);

        this.setLevelAttributes(baseAttributes);
        this.setLevelUpAttributes(levelUpAttributes);
        this.setValidWeaponTypes(validWeapons);
        this.setValidArmorTypes(validArmors);
    }

    /**
     * Represents damage that the rogue deals. Damage is modified by equipped weapon and
     * attributes that armor provides. Dexterity attribute is used to calculate damage modifier.
     *
     * @return damage value
     */
    public double damage() {
        Weapon equippedWeapon = (Weapon) this.getEquipment().get(Slot.Weapon);
        int baseDamage = equippedWeapon != null ? equippedWeapon.getWeaponDamage() : 1;

        double modifier = 1 + (this.totalAttributes().getDexterity() / 100d);

        return baseDamage * modifier;
    }
}
