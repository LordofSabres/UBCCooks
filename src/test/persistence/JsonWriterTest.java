package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// CITATION: Tests based on JsonWriterTest from JsonSerializationDemo
public class JsonWriterTest {

    @Test
    void testWriterInvalidCookbook() {
        try {
            Cookbook c = new Cookbook();
            JsonWriterCookbook writer = new JsonWriterCookbook("./data/my\0illegal:fileName.json");
            writer.openCookbook();
            fail("IOException was expected to be thrown!");
        } catch (IOException e) {
            //Expected
        }
    }

    @Test
    void testWriterEmptyCookbook() {
        try {
            Cookbook cook = new Cookbook();
            JsonWriterCookbook writer = new JsonWriterCookbook("./data/testEmptyCookbook.json");
            writer.openCookbook();
            writer.writeCookbook(cook);
            writer.closeCookbook();

            JsonReaderCookbook reader = new JsonReaderCookbook("./data/testEmptyCookbook.json");
            cook = reader.readCookbook();
            assertEquals(0, cook.getSize());

        } catch (IOException e) {
            fail("IOException was not expected to be thrown!");
        }
    }

    @Test
    void testWriterGeneralCookbook() {
        try {
            Cookbook cook = new Cookbook();
            List<String> ingredients1 = new ArrayList<String>();
            ingredients1.add("bread");
            ingredients1.add("butter");
            ingredients1.add("cheese");

            List<String> ingredients2 = new ArrayList<String>();
            ingredients2.add("peanut butter");
            ingredients2.add("jelly");
            ingredients2.add("bread");

            Recipe r1 = new Recipe("grilled cheese", ingredients1);
            Recipe r2 = new Recipe("pb & j", ingredients2);

            cook.addRecipe(r1);
            cook.addRecipe(r2);
            JsonWriterCookbook writer = new JsonWriterCookbook("./data/testGeneralCookbook.json");
            writer.openCookbook();
            writer.writeCookbook(cook);
            writer.closeCookbook();

            JsonReaderCookbook reader = new JsonReaderCookbook("./data/testGeneralCookbook.json");
            cook = reader.readCookbook();
            assertEquals(2, cook.getSize());
            List<Recipe> recipesList = cook.showRecipes();
            assertEquals("grilled cheese", recipesList.get(0).getRecipeName());
            assertEquals("pb & j", recipesList.get(1).getRecipeName());

        } catch (IOException e) {
            fail("IOException was not expected to be thrown!");
        }
    }

    @Test
    void testWriterInvalidInventory() {
        try {
            Inventory inv = new Inventory();
            JsonWriterInventory writer = new JsonWriterInventory("./data/my\0illegal:fileName.json");
            writer.openInventory();
            fail("IOException was expected to be thrown!");
        } catch (IOException e) {
            //Expected
        }
    }

    @Test
    void testWriterEmptyInventory() {
        try {
            Inventory inv = new Inventory();
            JsonWriterInventory writer = new JsonWriterInventory("./data/testEmptyInventory.json");
            writer.openInventory();
            writer.writeInventory(inv);
            writer.closeInventory();

            JsonReaderInventory reader = new JsonReaderInventory("./data/testEmptyInventory.json");
            inv = reader.readInventory();
            assertEquals(0, inv.getSize());

        } catch (IOException e) {
            fail("IOException was not expected to be thrown!");
        }
    }

    @Test
    void testWriterGeneralInventory() {
        try {
            Inventory inv = new Inventory();
            inv.addIngredients("bread");
            inv.addIngredients("butter");
            inv.addIngredients("cheese");
            inv.addIngredients("jelly");
            List<String> inventoryList = inv.showInventory();


            JsonWriterInventory writer = new JsonWriterInventory("./data/testGeneralInventory.json");
            writer.openInventory();
            writer.writeInventory(inv);
            writer.closeInventory();

            JsonReaderInventory reader = new JsonReaderInventory("./data/testGeneralInventory.json");
            inv = reader.readInventory();
            assertEquals(4, inv.getSize());
            assertEquals("butter", inventoryList.get(1));

        } catch (IOException e) {
            fail("IOException was not expected to be thrown!");
        }
    }


}
