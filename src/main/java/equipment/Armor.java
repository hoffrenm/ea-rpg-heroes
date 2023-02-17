package equipment;

import character.HeroAttribute;

/**
 * Represent Armor object that Hero can wield and
 * gain additional attributes provided by armor pieces.
 */
public class Armor extends Item {

    private ArmorType type;
    private HeroAttribute armorAttributes;

    /**
     * Creates Armor object with given name, required level, slot, type and attributes
     *
     * @param name            name of the armor
     * @param requiredLevel   minimum level required to equip armor
     * @param slot            slot of the armor that it will use
     * @param type            type of the armor
     * @param armorAttributes attributes that the armor will grant to the wearer
     */
    public Armor(String name, int requiredLevel, Slot slot, ArmorType type, HeroAttribute armorAttributes) {
        super(name, requiredLevel, slot);
        this.type = type;
        this.armorAttributes = armorAttributes;
    }

    /**
     * Returns type of the armor as ArmorType enum.
     *
     * @return type of armor
     * @see ArmorType
     */
    public ArmorType getType() {
        return type;
    }

    /**
     * Returns attributes that the armor has as HeroAttribute object.
     *
     * @return attributes attached to armor
     * @see HeroAttribute
     */
    public HeroAttribute getArmorAttributes() {
        return armorAttributes;
    }
}
