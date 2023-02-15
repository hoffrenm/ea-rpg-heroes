package hoffrenm;

abstract public class Item {
    private final String name;
    private int requiredLevel;
    private Slot slot;

    public Item(String name, int requiredLevel, Slot slot) {
        this.name = name;
        this.requiredLevel = requiredLevel;
        this.slot = slot;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public Slot getSlot() {
        return slot;
    }
}
