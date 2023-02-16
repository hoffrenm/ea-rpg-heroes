package character;

import equipment.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class Hero {

    private final String name;
    private int level;
    private HeroAttribute levelAttributes;
    private HeroAttribute levelUpAttributes;
    private Map<Slot, Item> equipment;
    private List<WeaponType> validWeaponTypes;
    private List<ArmorType> validArmorTypes;

    public Hero(String name) {
        this.name = name;
        this.level = 1;
        this.equipment = new HashMap<>();

        for (Slot slot : Slot.values()) {
            this.equipment.put(slot, null);
        }
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    protected void setLevelAttributes(HeroAttribute levelAttributes) {
        this.levelAttributes = levelAttributes;
    }

    public HeroAttribute getLevelAttributes() {
        return levelAttributes;
    }

    protected void setLevelUpAttributes(HeroAttribute levelUpAttributes) {
        this.levelUpAttributes = levelUpAttributes;
    }

    public Map<Slot, Item> getEquipment() {
        return equipment;
    }

    protected void setValidWeaponTypes(List<WeaponType> validWeaponTypes) {
        this.validWeaponTypes = validWeaponTypes;
    }

    protected void setValidArmorTypes(List<ArmorType> validArmorTypes) {
        this.validArmorTypes = validArmorTypes;
    }

    public void levelUp() {
        this.level += 1;
        this.levelAttributes.addAttributes(this.levelUpAttributes);
    }

    public void equip(Weapon weapon) throws InvalidWeaponException {
        if (weapon.getRequiredLevel() > this.level || !this.validWeaponTypes.contains(weapon.getType())) {
            throw new InvalidWeaponException("Hero cannot equip that type of weapon");
        } else {
            this.equipment.put(Slot.Weapon, weapon);
        }
    }

    public void equip(Armor armor) throws InvalidArmorException {
        if (armor.getRequiredLevel() > this.level || !this.validArmorTypes.contains(armor.getType())) {
            throw new InvalidArmorException("Hero cannot equip that type of armor");
        } else {
            this.equipment.put(armor.getSlot(), armor);
        }
    }

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

    abstract public double damage();

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
