package character;

import equipment.ArmorType;
import equipment.Slot;
import equipment.Weapon;
import equipment.WeaponType;

import java.util.Arrays;
import java.util.List;

/**
 * Represents Mage class. Subclass of Hero.
 */
public class Mage extends Hero {

    /**
     * Creates Mage with given name. Mage has distinct attribute composition that emphasizes intelligence.
     * Mage may use weapons of type Staff, Wand and equip armor of type Cloth.
     *
     * @param name name of mage
     */
    public Mage(String name) {
        super(name);

        HeroAttribute baseAttributes = new HeroAttribute(1, 1, 8);
        HeroAttribute levelUpAttributes = new HeroAttribute(1, 1, 5);

        List<WeaponType> validWeapons = Arrays.asList(WeaponType.Staff, WeaponType.Wand);
        List<ArmorType> validArmors = Arrays.asList(ArmorType.Cloth);

        this.setLevelAttributes(baseAttributes);
        this.setLevelUpAttributes(levelUpAttributes);
        this.setValidWeaponTypes(validWeapons);
        this.setValidArmorTypes(validArmors);
    }

    /**
     * Represents damage that the mage will deal. Damage is modified by equipped weapon and
     * attributes that armor provides. Intelligence attribute is used to calculate damage modifier.
     *
     * @return damage value
     */
    public double damage() {
        Weapon equippedWeapon = (Weapon) this.getEquipment().get(Slot.Weapon);
        int baseDamage = equippedWeapon != null ? equippedWeapon.getWeaponDamage() : 1;

        double modifier = 1 + (this.totalAttributes().getIntelligence() / 100d);

        return baseDamage * modifier;
    }
}
