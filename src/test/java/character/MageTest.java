package character;

import equipment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class MageTest {

    private Hero mage;

    @BeforeEach
    void setUp() {
        mage = new Mage("Minerva");
    }

    @Test
    void createMage_validName_shouldReturnSameName() {
        mage = new Mage("Alice");

        String name = mage.getName();

        assertEquals("Alice", name);
    }

    @Test
    void createMage_atBeginning_shouldStartAtLevelOne() {
        int startingLevel = mage.getLevel();

        assertEquals(1, startingLevel);
    }

    @Test
    void createMage_atBeginning_shouldHaveCorrectBaseAttributes() {
        HeroAttribute attributes = mage.getLevelAttributes();
        HeroAttribute baseAttributes = new HeroAttribute(1, 1, 8);

        assertEquals(baseAttributes, attributes);
    }

    @Test
    void levelUp_byOneLevel_shouldIncreaseLevelByOne() {
        mage.levelUp();

        int level = mage.getLevel();

        assertEquals(2, level);
    }

    @Test
    void levelUp_byFourLevelUps_shouldIncreaseLevelByFour() {
        IntStream.range(0, 4).forEach((i) -> mage.levelUp());

        int level = mage.getLevel();

        assertEquals(5, level);
    }

    @Test
    void levelUp_byOneLevelUp_shouldIncreaseLevelAttributesByOne() {
        HeroAttribute expected = new HeroAttribute(2, 2, 13);

        mage.levelUp();
        HeroAttribute attributes = mage.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void levelUp_byFiveLevelUps_shouldIncreaseLevelAttributesFiveTimes() {
        HeroAttribute expected = new HeroAttribute(6, 6, 33);

        IntStream.range(0, 5).forEach((i) -> mage.levelUp());
        HeroAttribute attributes = mage.getLevelAttributes();

        assertEquals(expected, attributes);
    }

    @Test
    void equipment_atStart_shouldHaveNothingEquipped() {
        Map<Slot, Item> items = mage.getEquipment();

        boolean hasNoItems = items.values().stream().allMatch(Objects::isNull);

        assertTrue(hasNoItems);
    }

    @Test
    void equipWeapon_validStaff_shouldBeAbleToEquip() throws InvalidWeaponException {
        Weapon staff = new Weapon("Great staff", 1, WeaponType.Staff, 5);
        mage.equip(staff);

        Item equippedItem = mage.getEquipment().get(Slot.Weapon);

        assertEquals(staff, equippedItem);
    }

    @Test
    void equipWeapon_validWand_shouldBeAbleToEquip() throws InvalidWeaponException {
        Weapon wand = new Weapon("Basically a stick", 1, WeaponType.Wand, 0);
        mage.equip(wand);

        Item equippedItem = mage.getEquipment().get(Slot.Weapon);

        assertEquals(wand, equippedItem);
    }

    @Test
    void equipWeapon_validTypeInvalidLevel_shouldThrowInvalidWeaponException() {
        Weapon highLvlWand = new Weapon("Nice wand", 4, WeaponType.Wand, 10);
        String errorMessage = "Your level is too low to wield that weapon.";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> mage.equip(highLvlWand));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipment_afterLevelingUp_shouldBeAbleToEquipHigherLevelItem() throws InvalidWeaponException {
        Weapon highLvlStaff = new Weapon("Regular dagger", 7, WeaponType.Staff, 12);
        IntStream.range(0, 10).forEach((i) -> mage.levelUp());

        mage.equip(highLvlStaff);
        Item equippedItem = mage.getEquipment().get(Slot.Weapon);

        assertEquals(highLvlStaff, equippedItem);
    }

    @Test
    void equipWeapon_invalidType_shouldThrowInvalidWeaponException() {
        Weapon axe = new Weapon("Common axe", 1, WeaponType.Axe, 3);
        String errorMessage = "Mage cannot equip weapon of type " + axe.getType() + ".";

        InvalidWeaponException exception = assertThrows(InvalidWeaponException.class, () -> mage.equip(axe));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_validClothLegs_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 2, 10);
        Armor shoes = new Armor("Cloth shoes", 1, Slot.Legs, ArmorType.Cloth, armorAttributes);

        mage.equip(shoes);
        Item equippedItem = mage.getEquipment().get(Slot.Legs);

        assertEquals(shoes, equippedItem);
    }

    @Test
    void equipArmor_validClothBody_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(3, 3, 15);
        Armor robe = new Armor("Cloth robe", 1, Slot.Body, ArmorType.Cloth, armorAttributes);

        mage.equip(robe);
        Item equippedItem = mage.getEquipment().get(Slot.Body);

        assertEquals(robe, equippedItem);
    }

    @Test
    void equipArmor_validClothHead_shouldBeAbleToEquip() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(3, 3, 1);
        Armor hat = new Armor("Fancy magic hat", 1, Slot.Head, ArmorType.Cloth, armorAttributes);

        mage.equip(hat);
        Item equippedItem = mage.getEquipment().get(Slot.Head);

        assertEquals(hat, equippedItem);
    }

    @Test
    void equipArmor_invalidLevelValidType_shouldThrowInvalidArmorException() {
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 1);
        Armor pants = new Armor("Cloth pants", 5, Slot.Legs, ArmorType.Cloth, armorAttributes);
        String errorMessage = "Your level is too low to equip that armor.";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> mage.equip(pants));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_invalidTypeValidLevel_shouldThrowInvalidArmorException() {
        HeroAttribute armorAttributes = new HeroAttribute(10, 2, 0);
        Armor helmet = new Armor("Plate helmet", 1, Slot.Head, ArmorType.Plate, armorAttributes);
        String errorMessage = "Mage cannot equip armor type of " + helmet.getType() + ".";

        InvalidArmorException exception = assertThrows(InvalidArmorException.class, () -> mage.equip(helmet));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void equipArmor_toSameSlot_shouldReplaceOldArmor() throws InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 1);
        Armor old = new Armor("Old pants", 1, Slot.Legs, ArmorType.Cloth, armorAttributes);
        Armor replacement = new Armor("New pants", 1, Slot.Legs, ArmorType.Cloth, armorAttributes);

        mage.equip(old);
        mage.equip(replacement);
        Item equippedItem = mage.getEquipment().get(Slot.Legs);

        assertEquals(replacement, equippedItem);
    }

    @Test
    void totalAttributes_atBeginning_shouldEqualBaseAttributes() {
        HeroAttribute expectedAttributes = new HeroAttribute(1, 1, 8);

        HeroAttribute attributes = mage.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_afterLevelingUpFiveTimes_shouldEqualTheirSum() {
        HeroAttribute expectedAttributes = new HeroAttribute(6, 6, 33);

        IntStream.range(0, 5).forEach((i) -> mage.levelUp());
        HeroAttribute attributes = mage.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withOneArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(3, 5, 23);
        HeroAttribute armorAttributes = new HeroAttribute(2, 4, 15);
        Armor pants = new Armor("Cloth pants", 1, Slot.Legs, ArmorType.Cloth, armorAttributes);

        mage.equip(pants);
        HeroAttribute attributes = mage.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withTwoArmorEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(3, 3, 28);
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 10);
        Armor pants = new Armor("Cloth pants", 1, Slot.Legs, ArmorType.Cloth, armorAttributes);
        Armor robe = new Armor("Robe", 1, Slot.Body, ArmorType.Cloth, armorAttributes);

        mage.equip(pants);
        mage.equip(robe);
        HeroAttribute attributes = mage.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withAllSlotsEquipped_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(4, 4, 23);
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 5);
        Armor hat = new Armor("Wizard hat", 1, Slot.Head, ArmorType.Cloth, armorAttributes);
        Armor shirt = new Armor("Fancy shirt", 1, Slot.Body, ArmorType.Cloth, armorAttributes);
        Armor pants = new Armor("Cloth pants", 1, Slot.Legs, ArmorType.Cloth, armorAttributes);

        mage.equip(hat);
        mage.equip(shirt);
        mage.equip(pants);
        HeroAttribute attributes = mage.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void totalAttributes_withReplacedArmor_shouldEqualSumOfTheirAttributes() throws InvalidArmorException {
        HeroAttribute expectedAttributes = new HeroAttribute(4, 5, 28);
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 1);
        HeroAttribute newArmorAttributes = new HeroAttribute(3, 4, 20);

        Armor hat = new Armor("Dirty hat", 1, Slot.Head, ArmorType.Cloth, armorAttributes);
        Armor newHat = new Armor("Mysterious hat", 1, Slot.Head, ArmorType.Cloth, newArmorAttributes);

        mage.equip(hat);
        mage.equip(newHat);
        HeroAttribute attributes = mage.totalAttributes();

        assertEquals(expectedAttributes, attributes);
    }

    @Test
    void damage_withoutWeaponOrArmor_shouldEqualBaseDamageModifiedByBaseAttributes() {
        double damage = mage.damage();

        assertEquals(1.08, damage, 1e-3);
    }

    @Test
    void damage_withWeapon_shouldEqualWeaponDamageModifiedByBaseAttributes() throws InvalidWeaponException {
        Weapon weapon = new Weapon("Pointy thing", 1, WeaponType.Wand, 15);
        mage.equip(weapon);

        double damage = mage.damage();

        assertEquals(16.2, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 2, 15);
        Armor pants = new Armor("Cloth pants", 1, Slot.Legs, ArmorType.Cloth, armorAttributes);
        Weapon weapon = new Weapon("Mighty staff", 1, WeaponType.Staff, 30);

        mage.equip(pants);
        mage.equip(weapon);
        double damage = mage.damage();

        assertEquals(36.9, damage, 1e-3);
    }

    @Test
    void damage_withWeaponAndAllArmor_shouldEqualWeaponDamageModifiedByTheirAttributes() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(1, 1, 55);
        Armor hat = new Armor("Hat", 1, Slot.Head, ArmorType.Cloth, armorAttributes);
        Armor shirt = new Armor("Shirt", 1, Slot.Body, ArmorType.Cloth, armorAttributes);
        Armor pants = new Armor("Pants", 1, Slot.Legs, ArmorType.Cloth, armorAttributes);
        Weapon weapon = new Weapon("Normal wand", 1, WeaponType.Wand, 15);

        mage.equip(hat);
        mage.equip(shirt);
        mage.equip(pants);
        mage.equip(weapon);
        double damage = mage.damage();

        assertEquals(40.95, damage, 1e-3);
    }

    @Test
    void damage_withReplacedWeapon_shouldBeCalculatedWithNewWeapon() throws InvalidWeaponException {
        Weapon oldWeapon = new Weapon("A stick", 1, WeaponType.Wand, 1);
        Weapon newWeapon = new Weapon("Powerful staff", 1, WeaponType.Staff, 134);

        mage.equip(oldWeapon);
        mage.equip(newWeapon);
        double damage = mage.damage();

        assertEquals(144.72, damage, 1e-3);
    }

    @Test
    void display_atBeginning_shouldDisplayName() {
        String display = mage.display();
        String name = mage.getName();

        boolean hasDisplayName = display.contains("Name: " + name);

        assertTrue(hasDisplayName);
    }

    @Test
    void display_atBeginning_shouldDisplayClass() {
        String display = mage.display();

        boolean hasClassName = display.contains("Class: Mage");

        assertTrue(hasClassName);
    }

    @Test
    void display_atBeginning_mageShouldNotDisplayOtherClasses() {
        String display = mage.display();

        boolean isRogue = display.contains("Rogue");
        boolean isWarrior = display.contains("Warrior");
        boolean isRanger = display.contains("Ranger");

        assertFalse(isRogue || isWarrior || isRanger);
    }

    @Test
    void display_atBeginning_shouldDisplayLevelOne() {
        String display = mage.display();

        boolean hasLevel = display.contains("Level: 1");

        assertTrue(hasLevel);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseLevelAttributes() {
        String display = mage.display();
        HeroAttribute baseAttributes = mage.getLevelAttributes();

        boolean hasStrength = display.contains("Strength: " + baseAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + baseAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + baseAttributes.getIntelligence());

        assertTrue(hasStrength && hasDexterity && hasIntelligence);
    }

    @Test
    void display_atBeginning_shouldDisplayBaseDamage() {
        String display = mage.display();
        double damage = mage.damage();

        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasDamage);
    }

    @Test
    void display_whenLevelingUp_shouldDisplayUpdatedLevel() {
        int levels = 11;
        IntStream.range(0, levels).forEach((i) -> mage.levelUp());
        String display = mage.display();

        boolean hasLevel = display.contains("Level: " + (levels + 1));

        assertTrue(hasLevel);
    }

    @Test
    void display_withLevelUpsAndWeaponAndArmor_shouldDisplayUpdatedInformation() throws InvalidWeaponException, InvalidArmorException {
        HeroAttribute armorAttributes = new HeroAttribute(4, 5, 10);
        mage.equip(new Armor("Hat", 1, Slot.Head, ArmorType.Cloth, armorAttributes));
        mage.equip(new Armor("Vest", 1, Slot.Body, ArmorType.Cloth, armorAttributes));
        mage.equip(new Armor("Pants", 1, Slot.Legs, ArmorType.Cloth, armorAttributes));
        mage.equip(new Weapon("Staff", 1, WeaponType.Staff, 99));
        IntStream.range(0, 10).forEach((i) -> mage.levelUp());
        HeroAttribute totalAttributes = mage.totalAttributes();
        double damage = mage.damage();

        String display = mage.display();
        boolean hasStrength = display.contains("Strength: " + totalAttributes.getStrength());
        boolean hasDexterity = display.contains("Dexterity: " + totalAttributes.getDexterity());
        boolean hasIntelligence = display.contains("Intelligence: " + totalAttributes.getIntelligence());
        boolean hasDamage = display.contains("Damage: " + damage);

        assertTrue(hasStrength && hasDexterity && hasIntelligence && hasDamage);
    }
}
