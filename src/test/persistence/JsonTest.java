package persistence;

import model.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// CITATION: Tests based on JsonTest from JsonSerializationDemo
public class JsonTest {
    protected void checkCookbook(int size, Cookbook cookbook) {
        assertEquals(size, cookbook.getSize());
    }

    protected void checkRecipe(String name, int size, Recipe recipe) {
        assertEquals(size, recipe.getIngredientsNeeded().size());
        assertEquals(name, recipe.getRecipeName());
    }

    protected void checkInventory(int size, Inventory inventory) {
        assertEquals(size, inventory.getSize());
    }

}
