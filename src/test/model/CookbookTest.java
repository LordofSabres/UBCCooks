package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class CookbookTest {
    private Recipe r1; //for grilled cheese in this example
    private Recipe r2; //for PBJ in this example

    private List<String> ingredients; //ingredients of r1
    private List<String> ingredients2; //ingredients of r2

    private Cookbook testCookbook;
    private Inventory testInventory;

    @BeforeEach
    void runBefore() {
        ingredients = new ArrayList<String>();
        ingredients2 = new ArrayList<String>();
        testInventory = new Inventory();
        testCookbook = new Cookbook();
        ingredients.add("bread");
        ingredients.add("butter");
        ingredients.add("cheese");

        ingredients2.add("bread");
        ingredients2.add("peanut butter");
        ingredients2.add("jelly");


        r1 = new Recipe("Grilled Cheese", ingredients);
        r2 = new Recipe("PBJ", ingredients2);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testCookbook.getSize());
    }

    @Test
    void testAddRecipe() {
        List<Recipe> recipesList = testCookbook.showRecipes();
        testCookbook.addRecipe(r1);
        testCookbook.addRecipe(r2);
        assertEquals(2, testCookbook.getSize());
        assertEquals("Grilled Cheese", recipesList.get(0).getRecipeName());
        assertEquals("PBJ", recipesList.get(1).getRecipeName());
    }

    @Test
    void testFindRecipe() {
        testCookbook.addRecipe(r1);
        testCookbook.addRecipe(r2);
        testInventory.addIngredients("bread");
        testInventory.addIngredients("cheese");
        testInventory.addIngredients("butter");
        testInventory.addIngredients("jelly");

        List<Recipe> recipesList1 = testCookbook.showRecipes();
        List<Recipe> testResult = testCookbook.findRecipe(recipesList1, testInventory);

        assertEquals(1, testResult.size());
        assertEquals("Grilled Cheese", testResult.get(0).getRecipeName());
    }

    @Test
    void testRemoveRecipe() {
        testCookbook.addRecipe(r1);
        testCookbook.addRecipe(r2);
        testCookbook.removeRecipe("PBJ");
        testCookbook.removeRecipe(null);

        List<Recipe> recipesList2 = testCookbook.showRecipes();

        assertEquals(1, recipesList2.size());
        assertEquals("Grilled Cheese", recipesList2.get(0).getRecipeName());

    }


}