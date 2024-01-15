package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class InventoryTest {
    private Inventory testInventory;

    @BeforeEach
    void runBefore() {
        testInventory = new Inventory();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testInventory.getSize());
    }

    @Test
    void testAddIngredients() {
        testInventory.addIngredients("bread");
        testInventory.addIngredients("butter");
        testInventory.addIngredients("cheese");
        testInventory.addIngredients("jelly");
        List<String> inventoryList = testInventory.showInventory();

        assertEquals(4, inventoryList.size());
        assertEquals("butter", inventoryList.get(1));


    }

    @Test
    void testRemoveIngredients() {
        testInventory.addIngredients("bread");
        testInventory.addIngredients("butter");
        testInventory.addIngredients("cheese");
        testInventory.removeIngredients("bread");
        List<String> inventoryList1 = testInventory.showInventory();
        assertEquals(2, inventoryList1.size());
        assertEquals("butter", inventoryList1.get(0));
    }
}
