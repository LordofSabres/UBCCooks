package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Holds a recipe - which includes the name of the recipe and the ingredients to make it
public class Recipe implements Writable {
    private List<String> ingredientsNeeded; //List of ingredients in recipe
    private String name; //name of recipe
    private JSONArray jsArrayIng;

    // REQUIRES: recipeName has a non-zero length
    //           ingredientsList has a non-zero length
    // EFFECTS: name of recipe is set to recipeName. recipeID is a positive integer unique to each recipe
    //          the ingredientsNeeded is the ingredientsList.

    public Recipe(String recipeName, List<String> ingredientsList) {
        this.name = recipeName;
        this.ingredientsNeeded = ingredientsList;
    }

    public String getRecipeName() {
        return this.name;
    }

    public List<String> getIngredientsNeeded() {
        return this.ingredientsNeeded;
    }

    @Override
    public String toString() {
        StringBuilder recipeText = new StringBuilder(this.name + "\nIngredients:\n");
        for (String ing : this.ingredientsNeeded) {
            recipeText.append("- ").append(ing).append("\n");
        }
        return recipeText.toString();
    }

    // CITATION: Taken from Thingy.java in JsonSerializationDemo and jsArrayIng based on trick from StackExchange
    // EFFECT: Takes name of recipe and ingredients of recipe and inputs into JSONs
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        jsArrayIng = new JSONArray(ingredientsNeeded);
        json.put("ingredients", jsArrayIng);
        return json;
    }
}
