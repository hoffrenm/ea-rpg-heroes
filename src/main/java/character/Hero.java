package character;

import equipment.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class that represents Hero. Contains shared fields for subclasses as well as shared methods.
 * Hero has name, level, attributes, equipment and lists of valid weapons and armors provided by subclasses.
 */
abstract public class Hero {

    private final String name;
    private int level;
    private HeroAttribute levelAttributes;
    private HeroAttribute levelUpAttributes;
    private Map<Slot, Item> equipment;
    private List<WeaponType> validWeaponTypes;
    private List<ArmorType> validArmorTypes;

    /**
     * Creates Hero with given name to level 1. Initializes equipment slots with null values.
     * Can only be called by subclass.
     *
     * @param name name of hero
     */
    protected Hero(String name) {
        this.name = name;
        this.level = 1;
        this.equipment = new HashMap<>();

        for (Slot slot : Slot.values()) {
            this.equipment.put(slot, null);
        }
    }

    /**
     * Returns name of the Hero.
     *
     * @return name of hero
     */
    public String getName() {
        return name;
    }

    /**
     * Returns level of the hero as integer.
     *
     * @return level of hero
     */
    public int getLevel() {
        return level;
    }

    /**
     * Used to set hero's level attributes from subclass.
     *
     * @param levelAttributes attributes hero has at beginning
     */
    protected void setLevelAttributes(HeroAttribute levelAttributes) {
        this.levelAttributes = levelAttributes;
    }

    /**
     * Returns hero's current level attributes that may have increased by leveling.
     *
     * @return level attributes of hero
     */
    public HeroAttribute getLevelAttributes() {
        return levelAttributes;
    }

    /**
     * Used to set hero's attributes for leveling from subclass.
     * Leveling a hero causes level attributes to be increased by these.
     *
     * @param levelUpAttributes attributes hero gains at level up
     */
    protected void setLevelUpAttributes(HeroAttribute levelUpAttributes) {
        this.levelUpAttributes = levelUpAttributes;
    }

    /**
     * Returns equipment that hero currently has. Represented as Map with Slot as a key.
     *
     * @return hero's equipment
     */
    public Map<Slot, Item> getEquipment() {
        return equipment;
    }

    /**
     * Sets valid weapon types allowed for hero.
     *
     * @param validWeaponTypes list of allowed weapon types
     */
    protected void setValidWeaponTypes(List<WeaponType> validWeaponTypes) {
        this.validWeaponTypes = validWeaponTypes;
    }

    /**
     * Sets valid armor types allowed for hero.
     *
     * @param validArmorTypes list of allowed armor types
     */
    protected void setValidArmorTypes(List<ArmorType> validArmorTypes) {
        this.validArmorTypes = validArmorTypes;
    }

    /**
     * Levels up a hero. Causes level to increase by 1 and level attributes increase by amount of
     * declared by levelUpAttributes.
     */
    public void levelUp() {
        this.level += 1;
        this.levelAttributes.addAttributes(this.levelUpAttributes);
    }

    /**
     * Equips weapon for a hero. Throws exception if weapon is wrong type or too high level to be equipped.
     * Previously equipped weapon will be discarded.
     *
     * @param weapon weapon that hero attempts to equip
     * @throws InvalidWeaponException invalid weapon type or required level too high
     */
    public void equip(Weapon weapon) throws InvalidWeaponException {
        if (!this.validWeaponTypes.contains(weapon.getType())) {
            throw new InvalidWeaponException(this.getClass().getSimpleName() + " cannot equip weapon of type " + weapon.getType() + ".");
        }

        if (weapon.getRequiredLevel() > this.level) {
            throw new InvalidWeaponException("Your level is too low to wield that weapon.");
        }

        this.equipment.put(Slot.Weapon, weapon);
    }

    /**
     * Equips armor for a hero. Throws exception if armor is wrong type or too high level to be equipped.
     * Previously equipped armor on same slot will be discarded.
     *
     * @param armor armor that hero attempts to equip
     * @throws InvalidArmorException invalid armor type or required level too high
     */
    public void equip(Armor armor) throws InvalidArmorException {
        if (!this.validArmorTypes.contains(armor.getType())) {
            throw new InvalidArmorException(this.getClass().getSimpleName() + " cannot equip armor type of " + armor.getType() + ".");
        }

        if (armor.getRequiredLevel() > this.level) {
            throw new InvalidArmorException("Your level is too low to equip that armor.");
        }

        this.equipment.put(armor.getSlot(), armor);
    }

    /**
     * Calculates sum of total attributes from hero's level attributes and those provided by armor pieces.
     * Returns total as new HeroAttribute.
     *
     * @return sum of attributes from hero and armors
     */
    public HeroAttribute totalAttributes() {
        HeroAttribute totalAttributes = new HeroAttribute(0, 0, 0);
        totalAttributes.addAttributes(this.levelAttributes);

        for (Item item : this.equipment.values()) {
            if (item instanceof Armor) {
                totalAttributes.addAttributes(((Armor) item).getArmorAttributes());
            }
        }

        return totalAttributes;
    }

    /**
     * Abstract damage method that subclasses have to implement.
     *
     * @return damage hero does
     */
    abstract public double damage();

    /**
     * String presentation of hero. Displays hero's name, class, level, attributes and damage hero currently does.
     *
     * @return hero information
     */
    public String display() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(this.getName()).append("\n")
                .append("Class: ").append(this.getClass().getSimpleName()).append("\n")
                .append("Level: ").append(this.getLevel()).append("\n")
                .append("Strength: ").append(this.totalAttributes().getStrength()).append("\n")
                .append("Dexterity: ").append(this.totalAttributes().getDexterity()).append("\n")
                .append("Intelligence: ").append(this.totalAttributes().getIntelligence()).append("\n")
                .append("Damage: ").append(this.damage());

        return sb.toString();
    }
}
