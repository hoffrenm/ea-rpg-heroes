package character;

import equipment.ArmorType;
import equipment.Slot;
import equipment.Weapon;
import equipment.WeaponType;

import java.util.Arrays;
import java.util.List;

/**
 * Represents Warrior class. Subclass of Hero.
 */
public class Warrior extends Hero {

    /**
     * Creates Warrior with given name. Warrior has distinct attribute composition that emphasizes strength.
     * Warrior may use weapons of type Axe, Hammer, Sword and equip armors of type Mail and Plate.
     *
     * @param name name of warrior
     */
    public Warrior(String name) {
        super(name);

        HeroAttribute baseAttributes = new HeroAttribute(5, 2, 1);
        HeroAttribute levelUpAttributes = new HeroAttribute(3, 2, 1);

        List<WeaponType> validWeapons = Arrays.asList(WeaponType.Axe, WeaponType.Hammer, WeaponType.Sword);
        List<ArmorType> validArmors = Arrays.asList(ArmorType.Mail, ArmorType.Plate);

        this.setLevelAttributes(baseAttributes);
        this.setLevelUpAttributes(levelUpAttributes);
        this.setValidWeaponTypes(validWeapons);
        this.setValidArmorTypes(validArmors);
    }

    /**
     * Represents damage that the warrior deals. Damage is modified by equipped weapon and
     * attributes that armor provides. Strength attribute is used to calculate damage modifier.
     *
     * @return damage value
     */
    public double damage() {
        Weapon equippedWeapon = (Weapon) this.getEquipment().get(Slot.Weapon);
        int baseDamage = equippedWeapon != null ? equippedWeapon.getWeaponDamage() : 1;

        double modifier = 1 + (this.totalAttributes().getStrength() / 100d);

        return baseDamage * modifier;
    }
}
