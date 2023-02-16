package character;

import equipment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {

    private Hero warrior;

    @BeforeEach
    void setUp() {
        warrior = new Warrior("Agnar");
    }

    @Test
    void createWarrior_validName_shouldReturnSameName() {
        warrior = new Warrior("Cadet");

        String name = warrior.getName();

        assertEquals("Cadet", name);
    }

    @Test
    void createWarrior_atBeginning_shouldStartAtLevelOne() {
        int startingLevel = warrior.getLevel();

        assertEquals(1, startingLevel);
    }

    @Test
    void createWarrior_atBeginning_shouldHaveCorrectBaseAttributes() {
        HeroAttribute attributes = warrior.getLevelAttributes();
        HeroAttribute baseAttributes = new HeroAttribute(5, 2, 1);

        assertEquals(baseAttributes, attributes);
    }

    @Test
    void levelUp_byOneLevel_shouldIncreaseLevelByOne() {
        warrior.levelUp();

        int level = warrior.getLevel();

        assertEquals(2, level);
    }

    @Test
    void levelUp_byFourLevelUps_shouldIncreaseLevelByFour() {
        IntStream.range(0, 4).forEach((i) -> warrior.levelUp());

        int level = warrior.getLevel();

        assertEquals(5, level);
    }

    @Test
    void levelUp_byOneLevelUp_shouldIncreaseLevelAttributesByOne() {
        HeroAttribute expected = new HeroAttribute(8, 4, 2);

        warrior.levelUp();
        HeroAttribute attributes = warrior.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void levelUp_byFourLevelUps_shouldIncreaseLevelAttributesFourTimes() {
        HeroAttribute expected = new HeroAttribute(17, 10, 5);

        IntStream.range(0, 4).forEach((i) -> warrior.levelUp());
        HeroAttribute attributes = warrior.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void equipment_atStart_shouldHaveNothingEquipped() {
        Map<Slot, Item> items = warrior.getEquipment();

        boolean hasNoItems = items.values().stream().allMatch(Objects::isNull);

        assertTrue(hasNoItems);
    }

    @Test
    void equipWeapon_validType_shouldBeAbleToEquip() throws InvalidWeaponException {
        Weapon weapon = new Weapon("Regular hammer", 1, WeaponType.Hammer, 1);

        warrior.equip(weapon);
        Item equippedItem = warrior.getEquipment().get(Slot.Weapon);

        assertEquals(weapon, equippedItem);
    }

    @Test
    void equipWeapon_validSword_shouldBeAbleToEquip() throws InvalidWeaponException {
        Weapon dagger = new Weapon("Regular sword", 1, WeaponType.Sword, 2);
        warrior.equip(dagger);

        Item equippedItem = warrior.getEquipment().get(Slot.Weapon);

        assertEquals(dagger, equippedItem);
    }

    @Test
    void equipWeapon_validTypeInvalidLevel_shouldThrowInvalidWeaponException() {
        Weapon invalidWeapon = new Weapon("Regular sword", 9, WeaponType.Sword, 3);
        String errorMessage = "Your level is too low to wield that weapon.";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> warrior.equip(invalidWeapon));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipment_afterLevelingUp_shouldBeAbleToEquipHigherLevelItem() throws InvalidWeaponException {
        Weapon validWeapon = new Weapon("Regular dagger", 7, WeaponType.Sword, 6);
        IntStream.range(0, 10).forEach((i) -> warrior.levelUp());

        warrior.equip(validWeapon);
        Item equippedItem = warrior.getEquipment().get(Slot.Weapon);

        assertEquals(validWeapon, equippedItem);
    }

    @Test
    void equipWeapon_invalidType_shouldThrowInvalidWeaponException() {
        Weapon staff = new Weapon("Magic staff", 1, WeaponType.Staff, 2);
        String errorMessage = "Warrior cannot equip weapon of type " + staff.getType() + ".";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> warrior.equip(staff));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_validTypePlate_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 5, 0);
        Armor legs = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Plate, armorAttributes);

        warrior.equip(legs);
        Item equippedItem = warrior.getEquipment().get(Slot.Legs);

        assertEquals(legs, equippedItem);
    }

    @Test
    void equipArmor_validTypeMail_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(3, 3, 1);
        Armor body = new Armor("Mail vest", 1, Slot.Body, ArmorType.Mail, armorAttributes);

        warrior.equip(body);
        Item equippedItem = warrior.getEquipment().get(Slot.Body);

        assertEquals(body, equippedItem);
    }

    @Test
    void equipArmor_invalidLevelValidType_shouldThrowInvalidArmorException() {
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 1);
        Armor pants = new Armor("Chain-link pants", 5, Slot.Legs, ArmorType.Mail, armorAttributes);
        String errorMessage = "Your level is too low to equip that armor.";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> warrior.equip(pants));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_invalidTypeValidLevel_shouldThrowInvalidArmorException() {
        HeroAttribute armorAttributes = new HeroAttribute(10, 2, 0);
        Armor hat = new Armor("Hat", 1, Slot.Head, ArmorType.Cloth, armorAttributes);
        String errorMessage = "Warrior cannot equip armor type of " + hat.getType() + ".";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> warrior.equip(hat));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_toSameSlot_shouldReplaceOldArmor() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 1);
        Armor old = new Armor("Mail pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);
        Armor replacement = new Armor("Plate legs", 1, Slot.Legs, ArmorType.Plate, armorAttributes);

        warrior.equip(old);
        warrior.equip(replacement);
        Item equippedItem = warrior.getEquipment().get(Slot.Legs);

        assertEquals(replacement, equippedItem);
    }

    @Test
    void totalAttributes_atBeginning_shouldEqualBaseAttributes() {
        HeroAttribute expectedAttributes = new HeroAttribute(5, 2, 1);

        HeroAttribute attributes = warrior.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_afterLevelingUpFiveTimes_shouldEqualTheirSum() {
        HeroAttribute expectedAttributes = new HeroAttribute(20, 12, 6);

        IntStream.range(0, 5).forEach((i) -> warrior.levelUp());
        HeroAttribute attributes = warrior.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withOneArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(15, 12, 2);
        HeroAttribute armorAttributes = new HeroAttribute(10, 10, 1);
        Armor pants = new Armor("Mail pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);

        warrior.equip(pants);
        HeroAttribute attributes = warrior.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withTwoArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(25, 22, 21);
        HeroAttribute armorAttributes = new HeroAttribute(10, 10, 10);
        Armor pants = new Armor("Mail pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);
        Armor vest = new Armor("Breastplate", 1, Slot.Body, ArmorType.Plate, armorAttributes);

        warrior.equip(pants);
        warrior.equip(vest);
        HeroAttribute attributes = warrior.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withAllSlotsEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(35, 32, 31);
        HeroAttribute armorAttributes = new HeroAttribute(10, 10, 10);
        Armor hat = new Armor("Leather hat", 1, Slot.Head, ArmorType.Mail, armorAttributes);
        Armor vest = new Armor("Leather vest", 1, Slot.Body, ArmorType.Mail, armorAttributes);
        Armor pants = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);

        warrior.equip(hat);
        warrior.equip(vest);
        warrior.equip(pants);
        HeroAttribute attributes = warrior.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withReplacedArmor_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(25, 7, 1);
        HeroAttribute armorAttributes = new HeroAttribute(3, 3, 3);
        HeroAttribute newArmorAttributes = new HeroAttribute(20, 5, 0);
        Armor vest = new Armor("Chain-link armor", 1, Slot.Body, ArmorType.Mail, armorAttributes);
        Armor newVest = new Armor("Breastplate", 1, Slot.Body, ArmorType.Plate, newArmorAttributes);

        warrior.equip(vest);
        warrior.equip(newVest);
        HeroAttribute attributes = warrior.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void damage_withoutWeaponOrArmor_shouldEqualBaseDamageModifiedByBaseAttributes() {
        double damage = warrior.damage();

        assertEquals(1.05, damage, 1e-3);
    }

    @Test
    void damage_withWeapon_shouldEqualWeaponDamageModifiedByBaseAttributes() throws InvalidWeaponException {
        Weapon weapon = new Weapon("Woodcutting axe", 1, WeaponType.Axe, 15);
        warrior.equip(weapon);

        double damage = warrior.damage();

        assertEquals(15.75, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(7, 22, 0);
        Armor pants = new Armor("Chain-link pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);
        Weapon weapon = new Weapon("Sharp sword", 1, WeaponType.Sword, 27);

        warrior.equip(pants);
        warrior.equip(weapon);
        double damage = warrior.damage();

        assertEquals(30.24, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndAllArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(10, 10, 1);
        Armor head = new Armor("Plate helmet", 1, Slot.Head, ArmorType.Plate, armorAttributes);
        Armor body = new Armor("Breastplate", 1, Slot.Body, ArmorType.Plate, armorAttributes);
        Armor legs = new Armor("Armored boots", 1, Slot.Legs, ArmorType.Plate, armorAttributes);
        Weapon weapon = new Weapon("Battle hammer", 1, WeaponType.Hammer, 82);

        warrior.equip(head);
        warrior.equip(body);
        warrior.equip(legs);
        warrior.equip(weapon);
        double damage = warrior.damage();

        assertEquals(110.7, damage, 1e-3);
    }

    @Test
    void damage_withReplacedWeapon_shouldBeCalculatedWithNewWeapon() throws InvalidWeaponException {
        Weapon oldWeapon = new Weapon("Poor sword", 1, WeaponType.Sword, 1);
        Weapon newWeapon = new Weapon("Magnificent sword", 1, WeaponType.Sword, 173);

        warrior.equip(oldWeapon);
        warrior.equip(newWeapon);
        double damage = warrior.damage();

        assertEquals(181.65, damage, 1e-3);
    }

    @Test
    void display_atBeginning_shouldDisplayName() {
        String display = warrior.display();
        String name = warrior.getName();

        boolean hasDisplayName = display.contains("Name: " + name);

        assertTrue(hasDisplayName);
    }

    @Test
    void display_atBeginning_shouldDisplayClass() {
        String display = warrior.display();

        boolean hasClassName = display.contains("Class: Warrior");

        assertTrue(hasClassName);
    }

    @Test
    void display_atBeginning_warriorShouldNotDisplayOtherClasses() {
        String display = warrior.display();

        boolean isMage = display.contains("Mage");
        boolean isRogue = display.contains("Rogue");
        boolean isRanger = display.contains("Ranger");

        assertFalse(isMage || isRogue || isRanger);
    }

    @Test
    void display_atBeginning_shouldDisplayLevelOne() {
        String display = warrior.display();

        boolean hasLevel = display.contains("Level: 1");

        assertTrue(hasLevel);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseLevelAttributes() {
        String display = warrior.display();
        HeroAttribute baseAttributes = warrior.getLevelAttributes();

        boolean hasStrength = display.contains("Strength: " + baseAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + baseAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + baseAttributes.getIntelligence());

        assertTrue(hasStrength && hasDexterity && hasIntelligence);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseDamage() {
        String display = warrior.display();
        double damage = warrior.damage();

        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasDamage);
    }

    @Test
    void display_whenLevelingUp_shouldDisplayUpdatedLevel() {
        int levels = 11;
        IntStream.range(0, levels).forEach((i) -> warrior.levelUp());
        String display = warrior.display();

        boolean hasLevel = display.contains("Level: " + (levels + 1));

        assertTrue(hasLevel);
    }

    @Test
    void display_withLevelUpsAndWeaponAndArmor_shouldDisplayUpdatedInformation() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(17, 10, 1);
        warrior.equip(new Armor("Leather hat", 1, Slot.Head, ArmorType.Plate, armorAttributes));
        warrior.equip(new Armor("Leather vest", 1, Slot.Body, ArmorType.Plate, armorAttributes));
        warrior.equip(new Armor("Leather pants", 1, Slot.Legs, ArmorType.Plate, armorAttributes));
        warrior.equip(new Weapon("Sword", 1, WeaponType.Sword, 61));
        IntStream.range(0, 10).forEach((i) -> warrior.levelUp());
        HeroAttribute totalAttributes = warrior.totalAttributes();
        double damage = warrior.damage();

        String display = warrior.display();
        boolean hasStrength = display.contains("Strength: " + totalAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + totalAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + totalAttributes.getIntelligence());
        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasStrength && hasDexterity && hasIntelligence && hasDamage);
    }
}
