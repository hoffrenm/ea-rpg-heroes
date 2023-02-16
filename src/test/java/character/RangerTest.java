package character;

import equipment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class RangerTest {

    private Hero ranger;

    @BeforeEach
    void setUp() {
        ranger = new Ranger("Delmar");
    }

    @Test
    void createRanger_validName_shouldReturnSameName() {
        ranger = new Ranger("Fennel");

        String name = ranger.getName();

        assertEquals("Fennel", name);
    }

    @Test
    void createRanger_atBeginning_shouldStartAtLevelOne() {
        int startingLevel = ranger.getLevel();

        assertEquals(1, startingLevel);
    }

    @Test
    void createRanger_atBeginning_shouldHaveCorrectBaseAttributes() {
        HeroAttribute attributes = ranger.getLevelAttributes();
        HeroAttribute baseAttributes = new HeroAttribute(1, 7, 1);

        assertEquals(baseAttributes, attributes);
    }

    @Test
    void levelUp_byOneLevel_shouldIncreaseLevelByOne() {
        ranger.levelUp();

        int level = ranger.getLevel();

        assertEquals(2, level);
    }

    @Test
    void levelUp_byFourLevelUps_shouldIncreaseLevelByFour() {
        IntStream.range(0, 4).forEach((i) -> ranger.levelUp());

        int level = ranger.getLevel();

        assertEquals(5, level);
    }

    @Test
    void levelUp_byOneLevelUp_shouldIncreaseLevelAttributesByOne() {
        HeroAttribute expected = new HeroAttribute(2, 12, 2);

        ranger.levelUp();
        HeroAttribute attributes = ranger.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void levelUp_byFourLevelUps_shouldIncreaseLevelAttributesFourTimes() {
        HeroAttribute expected = new HeroAttribute(5, 27, 5);

        IntStream.range(0, 4).forEach((i) -> ranger.levelUp());
        HeroAttribute attributes = ranger.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void equipment_atStart_shouldHaveNothingEquipped() {
        Map<Slot, Item> items = ranger.getEquipment();

        boolean hasNoItems = items.values().stream().allMatch(Objects::isNull);

        assertTrue(hasNoItems);
    }

    @Test
    void equipWeapon_validBow_shouldBeAbleToEquip() throws InvalidWeaponException {
        Weapon bow = new Weapon("Bow", 1, WeaponType.Bow, 1);
        ranger.equip(bow);

        Item equippedItem = ranger.getEquipment().get(Slot.Weapon);

        assertEquals(bow, equippedItem);
    }

    @Test
    void equipWeapon_validTypeInvalidLevel_shouldThrowInvalidWeaponException() {
        Weapon highLvlBow = new Weapon("Regular bow", 9, WeaponType.Bow, 3);
        String errorMessage = "Your level is too low to wield that weapon.";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> ranger.equip(highLvlBow));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipment_afterLevelingUp_shouldBeAbleToEquipHigherLevelItem() throws InvalidWeaponException {
        Weapon highLvlBow = new Weapon("Regular bow", 7, WeaponType.Bow, 6);
        IntStream.range(0, 10).forEach((i) -> ranger.levelUp());

        ranger.equip(highLvlBow);
        Item equippedItem = ranger.getEquipment().get(Slot.Weapon);

        assertEquals(highLvlBow, equippedItem);
    }

    @Test
    void equipWeapon_invalidType_shouldThrowInvalidWeaponException() {
        Weapon staff = new Weapon("Magic staff", 1, WeaponType.Staff, 2);
        String errorMessage = "Ranger cannot equip weapon of type " + staff.getType() + ".";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> ranger.equip(staff));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_validLeatherLegs_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 5, 0);
        Armor pants = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);

        ranger.equip(pants);
        Item equippedItem = ranger.getEquipment().get(Slot.Legs);

        assertEquals(pants, equippedItem);
    }

    @Test
    void equipArmor_validMailBody_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(3, 3, 1);
        Armor vest = new Armor("Mail vest", 1, Slot.Body, ArmorType.Mail, armorAttributes);

        ranger.equip(vest);
        Item equippedItem = ranger.getEquipment().get(Slot.Body);

        assertEquals(vest, equippedItem);
    }

    @Test
    void equipArmor_validLeatherHead_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(3, 3, 1);
        Armor hat = new Armor("Fancy leather hat", 1, Slot.Head, ArmorType.Leather, armorAttributes);

        ranger.equip(hat);
        Item equippedItem = ranger.getEquipment().get(Slot.Head);

        assertEquals(hat, equippedItem);
    }

    @Test
    void equipArmor_invalidLevelValidType_shouldThrowInvalidArmorException() {
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 1);
        Armor pants = new Armor("Leather pants", 5, Slot.Legs, ArmorType.Mail, armorAttributes);
        String errorMessage = "Your level is too low to equip that armor.";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> ranger.equip(pants));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_invalidTypeValidLevel_shouldThrowInvalidArmorException() {
        HeroAttribute armorAttributes = new HeroAttribute(10, 2, 0);
        Armor helmet = new Armor("Plate helmet", 1, Slot.Head, ArmorType.Plate, armorAttributes);
        String errorMessage = "Ranger cannot equip armor type of " + helmet.getType() + ".";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> ranger.equip(helmet));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_toSameSlot_shouldReplaceOldArmor() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 1);
        Armor old = new Armor("Mail pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);
        Armor replacement = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);

        ranger.equip(old);
        ranger.equip(replacement);
        Item equippedItem = ranger.getEquipment().get(Slot.Legs);

        assertEquals(replacement, equippedItem);
    }

    @Test
    void totalAttributes_atBeginning_shouldEqualBaseAttributes() {
        HeroAttribute expectedAttributes = new HeroAttribute(1, 7, 1);

        HeroAttribute attributes = ranger.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_afterLevelingUpSixTimes_shouldEqualTheirSum() {
        HeroAttribute expectedAttributes = new HeroAttribute(7, 37, 7);

        IntStream.range(0, 6).forEach((i) -> ranger.levelUp());
        HeroAttribute attributes = ranger.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withOneArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(11, 9, 4);
        HeroAttribute armorAttributes = new HeroAttribute(10, 2, 3);
        Armor pants = new Armor("Mail pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);

        ranger.equip(pants);
        HeroAttribute attributes = ranger.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withTwoArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(21, 11, 7);
        HeroAttribute armorAttributes = new HeroAttribute(10, 2, 3);
        Armor pants = new Armor("Mail pants", 1, Slot.Legs, ArmorType.Mail, armorAttributes);
        Armor vest = new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes);

        ranger.equip(pants);
        ranger.equip(vest);
        HeroAttribute attributes = ranger.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withAllSlotsEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(10, 10, 22);
        HeroAttribute armorAttributes = new HeroAttribute(3, 1, 7);
        Armor hat = new Armor("Leather hat", 1, Slot.Head, ArmorType.Leather, armorAttributes);
        Armor vest = new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes);
        Armor pants = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);

        ranger.equip(hat);
        ranger.equip(vest);
        ranger.equip(pants);
        HeroAttribute attributes = ranger.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withReplacedArmor_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(21, 15, 1);
        HeroAttribute armorAttributes = new HeroAttribute(3, 1, 7);
        HeroAttribute newArmorAttributes = new HeroAttribute(20, 8, 0);

        Armor vest = new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes);
        Armor newVest = new Armor("Much fancier vest", 1, Slot.Body, ArmorType.Leather, newArmorAttributes);

        ranger.equip(vest);
        ranger.equip(newVest);
        HeroAttribute attributes = ranger.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void damage_withoutWeaponOrArmor_shouldEqualBaseDamageModifiedByBaseAttributes() {
        double damage = ranger.damage();

        assertEquals(1.07, damage, 1e-3);
    }

    @Test
    void damage_withWeapon_shouldEqualWeaponDamageModifiedByBaseAttributes() throws InvalidWeaponException {
        Weapon weapon = new Weapon("Pointy thing", 1, WeaponType.Bow, 15);
        ranger.equip(weapon);

        double damage = ranger.damage();

        assertEquals(16.05, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(7, 22, 0);
        Armor pants = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);
        Weapon weapon = new Weapon("Pointier thing", 1, WeaponType.Bow, 27);

        ranger.equip(pants);
        ranger.equip(weapon);
        double damage = ranger.damage();

        assertEquals(34.83, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndAllArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(7, 11, 1);
        Armor hat = new Armor("Leather hat", 1, Slot.Head, ArmorType.Leather, armorAttributes);
        Armor vest = new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes);
        Armor pants = new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes);
        Weapon weapon = new Weapon("Crossbow", 1, WeaponType.Bow, 77);

        ranger.equip(hat);
        ranger.equip(vest);
        ranger.equip(pants);
        ranger.equip(weapon);
        double damage = ranger.damage();

        assertEquals(107.8, damage, 1e-3);
    }

    @Test
    void damage_withReplacedWeapon_shouldBeCalculatedWithNewWeapon() throws InvalidWeaponException {
        Weapon oldWeapon = new Weapon("Poor sword", 1, WeaponType.Bow, 1);
        Weapon newWeapon = new Weapon("Magnificent dagger", 1, WeaponType.Bow, 100);

        ranger.equip(oldWeapon);
        ranger.equip(newWeapon);
        double damage = ranger.damage();

        assertEquals(107.0, damage, 1e-3);
    }

    @Test
    void display_atBeginning_shouldDisplayName() {
        String display = ranger.display();
        String name = ranger.getName();

        boolean hasDisplayName = display.contains("Name: " + name);

        assertTrue(hasDisplayName);
    }

    @Test
    void display_atBeginning_shouldDisplayClass() {
        String display = ranger.display();

        boolean hasClassName = display.contains("Class: Ranger");

        assertTrue(hasClassName);
    }

    @Test
    void display_atBeginning_RangerShouldNotDisplayOtherClasses() {
        String display = ranger.display();

        boolean isMage = display.contains("Mage");
        boolean isWarrior = display.contains("Warrior");
        boolean isRogue = display.contains("Rogue");

        assertFalse((isMage || isWarrior || isRogue));
    }

    @Test
    void display_atBeginning_shouldDisplayLevelOne() {
        String display = ranger.display();

        boolean hasLevel = display.contains("Level: 1");

        assertTrue(hasLevel);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseLevelAttributes() {
        String display = ranger.display();
        HeroAttribute baseAttributes = ranger.getLevelAttributes();

        boolean hasStrength = display.contains("Strength: " + baseAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + baseAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + baseAttributes.getIntelligence());

        assertTrue(hasStrength && hasDexterity && hasIntelligence);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseDamage() {
        String display = ranger.display();
        double damage = ranger.damage();

        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasDamage);
    }

    @Test
    void display_whenLevelingUp_shouldDisplayUpdatedLevel() {
        int levels = 11;
        IntStream.range(0, levels).forEach((i) -> ranger.levelUp());
        String display = ranger.display();

        boolean hasLevel = display.contains("Level: " + (levels + 1));

        assertTrue(hasLevel);
    }

    @Test
    void display_withLevelUpsAndWeaponAndArmor_shouldDisplayUpdatedInformation() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(7, 16, 1);
        ranger.equip(new Armor("Leather hat", 1, Slot.Head, ArmorType.Leather, armorAttributes));
        ranger.equip(new Armor("Leather vest", 1, Slot.Body, ArmorType.Leather, armorAttributes));
        ranger.equip(new Armor("Leather pants", 1, Slot.Legs, ArmorType.Leather, armorAttributes));
        ranger.equip(new Weapon("Bow", 1, WeaponType.Bow, 61));
        IntStream.range(0, 10).forEach((i) -> ranger.levelUp());
        HeroAttribute totalAttributes = ranger.totalAttributes();
        double damage = ranger.damage();

        String display = ranger.display();
        boolean hasStrength = display.contains("Strength: " + totalAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + totalAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + totalAttributes.getIntelligence());
        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasStrength && hasDexterity && hasIntelligence && hasDamage);
    }
}