package equipment;

import character.HeroAttribute;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArmorTest {

    private HeroAttribute armorAttributes = new HeroAttribute(2, 7, 3);

    @Test
    void createArmor_validName_shouldReturnSameName() {
        Armor armor = new Armor("Old boot", 1, Slot.Legs, ArmorType.Cloth, armorAttributes);

        String name = armor.getName();

        assertEquals("Old boot", name);
    }

    @Test
    void createArmor_requiredLevel_shouldReturnRequiredLevel() {
        Armor armor = new Armor("Hard hat", 7, Slot.Head, ArmorType.Plate, armorAttributes);

        int requiredLevel = armor.getRequiredLevel();

        assertEquals(7, requiredLevel);
    }

    @Test
    void createArmor_clothHead_shouldReturnClothHeadSlot() {
        Armor armor = new Armor("Fancy hat", 2, Slot.Head, ArmorType.Cloth, armorAttributes);

        Slot slot = armor.getSlot();
        ArmorType type = armor.getType();

        assertEquals(Slot.Head, slot);
        assertEquals(ArmorType.Cloth, type);
    }

    @Test
    void createArmor_leatherBody_shouldReturnLeatherBodySlot() {
        Armor armor = new Armor("Regular vest", 3, Slot.Body, ArmorType.Leather, armorAttributes);

        Slot slot = armor.getSlot();
        ArmorType type = armor.getType();

        assertEquals(Slot.Body, slot);
        assertEquals(ArmorType.Leather, type);
    }

    @Test
    void createArmor_mailLegs_shouldReturnMailLegsSlot() {
        Armor armor = new Armor("Clumsy armor", 4, Slot.Legs, ArmorType.Mail, armorAttributes);

        Slot slot = armor.getSlot();
        ArmorType type = armor.getType();

        assertEquals(Slot.Legs, slot);
        assertEquals(ArmorType.Mail, type);
    }

    @Test
    void createArmor_withArmorAttributes_shouldReturnSameAttributes() {
        HeroAttribute expectedAttributes = new HeroAttribute(17, 11, 8);
        Armor armor = new Armor("Work boots", 7, Slot.Legs, ArmorType.Plate, expectedAttributes);

        HeroAttribute attributes = armor.getArmorAttributes();

        assertEquals(expectedAttributes, attributes);
    }
}
