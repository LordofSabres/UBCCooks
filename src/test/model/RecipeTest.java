package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class RecipeTest {
    private Recipe testRecipe;
    private Recipe testRecipe2;
    private List<String> ingredients;
    private List<String> ingredients2;

    @BeforeEach
    void runBefore() {
        ingredients = new ArrayList<String>();
        ingredients.add("Bread");
        ingredients.add("Butter");
        ingredients.add("Cheese");
        testRecipe = new Recipe("Grilled Cheese", ingredients);

        ingredients2 = new ArrayList<String>();
        ingredients2.add("Bread");
        ingredients2.add("Peanut Butter");
        ingredients2.add("Jelly");
        testRecipe2 = new Recipe("PBJ", ingredients2);

    }

    //testing constructor of one recipe
    @Test
    void testConstructor() {
        List<String> testIngredients = testRecipe.getIngredientsNeeded();

        assertEquals("Grilled Cheese", testRecipe.getRecipeName());
        assertEquals(3, testIngredients.size());
        assertEquals("Bread", testIngredients.get(0));
        assertEquals("Butter", testIngredients.get(1));
        assertEquals("Cheese", testIngredients.get(2));
    }

    @Test
    void testConstructor2() {
        List<String> testIngredients2 = testRecipe2.getIngredientsNeeded();

        assertEquals("PBJ", testRecipe2.getRecipeName());
        assertEquals(3, testIngredients2.size());
        assertEquals("Peanut Butter", testIngredients2.get(1));
        assertEquals("Jelly", testIngredients2.get(2));

    }

    @Test
    void testToString() {
        assertEquals("Grilled Cheese" + "\nIngredients:\n" + "- Bread\n" + "- Butter\n"
        + "- Cheese\n", testRecipe.toString());
    }
}
