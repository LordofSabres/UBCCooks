package persistence;

import model.*;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// CITATION: Tests based on JsonReaderTest from JsonSerializationDemo
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentCookbook() {
        JsonReaderCookbook reader = new JsonReaderCookbook("./data/nonexistentcookbook.json");
        try {
            Cookbook c = reader.readCookbook();
            fail("IOException expected!");
        } catch (IOException e) {
            //Expected
        }
    }

    @Test
    void testReaderEmptyCookbook() {
        JsonReaderCookbook reader = new JsonReaderCookbook("./data/testEmptyCookbook.json");
        try {
            Cookbook c = reader.readCookbook();
            assertEquals(0, c.getSize());
        } catch (IOException e) {
            fail("IOException not expected to be caught!");
        }
    }

    @Test
    void testReaderGeneralCookbook() {
        JsonReaderCookbook reader = new JsonReaderCookbook("./data/testGeneralCookbook.json");
        try {
            Cookbook c = reader.readCookbook();
            assertEquals(2, c.getSize());
            List<Recipe> recipesList = c.showRecipes();
            assertEquals("grilled cheese", recipesList.get(0).getRecipeName());
            assertEquals("pb & j", recipesList.get(1).getRecipeName());
        } catch (IOException e) {
            fail("IOException not expected to be caught!");
        }
    }

    @Test
    void testReaderNonExistentInventory() {
        JsonReaderInventory reader = new JsonReaderInventory("./data/nonexistentinventory.json");
        try {
            Inventory i = reader.readInventory();
            fail("IOException expected!");
        } catch (IOException e) {
            //Expected
        }
    }

    @Test
    void testReaderEmptyInventory() {
        JsonReaderInventory reader = new JsonReaderInventory("./data/testEmptyInventory.json");
        try {
            Inventory i = reader.readInventory();
            assertEquals(0, i.getSize());
        } catch (IOException e) {
            fail("IOException not expected to be caught!");
        }
    }

    @Test
    void testReaderGeneralInventory() {
        JsonReaderInventory reader = new JsonReaderInventory("./data/testGeneralInventory.json");
        try {
            Inventory i = reader.readInventory();
            assertEquals(4, i.getSize());
            List<String> inventoryList = i.showInventory();
            assertEquals("bread", inventoryList.get(0));
            assertEquals("butter", inventoryList.get(1));
            assertEquals("cheese", inventoryList.get(2));
            assertEquals("jelly", inventoryList.get(3));
        } catch (IOException e) {
            fail("IOException not expected to be caught!");
        }
    }


}
