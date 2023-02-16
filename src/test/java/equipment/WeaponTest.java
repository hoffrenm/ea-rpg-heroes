package equipment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {

    @Test
    void createWeapon_validName_shouldReturnSameName() {
        Weapon weapon = new Weapon("Great axe", 1, WeaponType.Axe, 4);

        String name = weapon.getName();

        assertEquals("Great axe", name);
    }

    @Test
    void createWeapon_requiredLevel_shouldReturnSameLevel() {
        Weapon weapon = new Weapon("Greater axe", 3, WeaponType.Axe, 8);

        int requiredLevel = weapon.getRequiredLevel();

        assertEquals(3, requiredLevel);
    }

    @Test
    void createWeapon_weaponDamage_shouldReturnSameDamage() {
        Weapon weapon = new Weapon("Greatest axe", 1, WeaponType.Axe, 16);

        int weaponDamage = weapon.getWeaponDamage();

        assertEquals(16, weaponDamage);
    }

    @Test
    void createWeapon_typeBow_shouldReturnBowType() {
        Weapon weapon = new Weapon("Bow", 1, WeaponType.Bow, 10);

        WeaponType type = weapon.getType();

        assertEquals(WeaponType.Bow, type);
    }

    @Test
    void createWeapon_typeHammer_shouldReturnHammerType() {
        Weapon weapon = new Weapon("Hammer", 1, WeaponType.Hammer, 10);

        WeaponType type = weapon.getType();

        assertEquals(WeaponType.Hammer, type);
    }
}
