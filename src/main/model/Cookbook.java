package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


public class Cookbook extends Observable implements Writable {
    private List<Recipe> cookBook; // The list of recipes to be compared and displayed

    //EFFECTS: cookBook is created as an empty list of recipes
    public Cookbook() {
        cookBook = new ArrayList<Recipe>();
    }

    // MODIFIES: this
    // EFFECTS: Adds recipe into the Cookbook
    public void addRecipe(Recipe r) {
        cookBook.add(r);
        notifyObservers();
        EventLog.getInstance().logEvent(new Event("Recipe Added!"));

    }


    // EFFECTS: Shows the recipes from the cookbook
    public List<Recipe> showRecipes() {
        return cookBook;
    }


    // EFFECTS: Gets the size of the cookbook
    public int getSize() {
        return cookBook.size();
    }

    // EFFECTS: Shows recipes you can make based on inventory
    public List<Recipe> findRecipe(List<Recipe> a, Inventory b) {
        List<Recipe> currentCookbook = a;
        List<String> ingredientsInInventory = b.showInventory();
        List<String> ingredientsInEachRecipe = new ArrayList<>();
        List<Recipe> recipesYouCanMake = new ArrayList<>();

        for (Recipe recipe : currentCookbook) {
            ingredientsInEachRecipe = recipe.getIngredientsNeeded();
            if (ingredientsInInventory.containsAll(ingredientsInEachRecipe)) {
                recipesYouCanMake.add(recipe);
            }
        }
        notifyObservers();
        EventLog.getInstance().logEvent(new Event("Recipes Searched For."));

        return recipesYouCanMake;
    }

    // MODIFIES: this
    // EFFECTS: Removes a recipe from the cookbook
    public void removeRecipe(String s) {
        Recipe toRemove = null;
        for (Recipe r : cookBook) {
            if (r.getRecipeName().equals(s)) {
                toRemove = r;
            }
        }

        if (toRemove != null) {
            cookBook.remove(toRemove);
            notifyObservers();
            EventLog.getInstance().logEvent(new Event("Recipe Removed!"));
        }

    }

    // CITATION: Design from WorkRoom.java from JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject jsonCookbook = new JSONObject();
        jsonCookbook.put("recipes", recipesToJson());
        return jsonCookbook;
    }

    // CITATION: Design from WorkRoom.java from JsonSerializationDemo
    // EFFECTS: Returns recipes in the cookbook as an array for the json
    private JSONArray recipesToJson() {
        JSONArray arr = new JSONArray();

        for (Recipe r : cookBook) {
            arr.put(r.toJson());
        }

        return arr;
    }

    @Override
    public void notifyObservers() {
        for (Observer next : observers) {
            next.update();
        }
    }


}
