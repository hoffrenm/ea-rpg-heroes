package equipment;

/**
 * Abstract base class for Weapon and Armor.
 * Item has name, required level and slot to which it is equipped.
 */
abstract public class Item {

    private final String name;
    private final int requiredLevel;
    private final Slot slot;

    /**
     * Creates Item with given name, required level and slot.
     * Can only be called by subclass.
     *
     * @param name          name of the item
     * @param requiredLevel level required to equip item
     * @param slot          slot that the item will be equipped
     */
    protected Item(String name, int requiredLevel, Slot slot) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.slot = slot;
    }

    /**
     * Returns name of the item as String.
     *
     * @return name of item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns level requirement to equip the item as integer.
     *
     * @return level required to equip item
     */
    public int getRequiredLevel() {
        return requiredLevel;
    }

    /**
     * Returns slot that the item will be equipped to.
     *
     * @return slot of item
     * @see Slot
     */
    public Slot getSlot() {
        return slot;
    }
}
