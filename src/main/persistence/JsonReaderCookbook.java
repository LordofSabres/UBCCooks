package persistence;

import model.Recipe;
import model.Cookbook;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// Represents the reader that reads the CookBook and Inventory from JSON data stored in file
public class JsonReaderCookbook {
    private String source;

    // EFFECTS: reads Cookbook from file and returns it;
    public JsonReaderCookbook(String entry) {
        this.source = entry;
    }

    // EFFECTS: reads Cookbook from file and returns it;
    // throws IOException if an error occurs reading the data from the file
    public Cookbook readCookbook() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCookbook(jsonObject);
    }

    // EFFECTS: reads the source file as a string and returns as string
    private String readFile(String source) throws IOException {
        StringBuilder creator = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> creator.append(s));
        }

        return creator.toString();
    }

    // EFFECTS: Parses Cookbook from JSON Object and returns the cookbook
    private Cookbook parseCookbook(JSONObject jsonObject) {
        //String name = jsonObject.getString("name");
        Cookbook c = new Cookbook();
        addRecipes(c, jsonObject);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses the recipes from the JSON object and adds
    private void addRecipes(Cookbook c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("recipes");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addRecipeJson(c, nextRecipe);
        }
    }


    // USED GEEKSFORGEEKS.ORG FOR THE FOR LOOP
    // MODIFIES: c
    // EFFECTS: parses each recipe from JSON Object and adds it into the Cookbook
    private void addRecipeJson(Cookbook c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray jsonArray2 = jsonObject.getJSONArray("ingredients");
        List<String> recipeIngredients = new ArrayList<>();

        for (int i = 0; i < jsonArray2.length(); i++) {
            recipeIngredients.add(jsonArray2.getString(i));
        }
        Recipe recipe = new Recipe(name, recipeIngredients);
        c.addRecipe(recipe);
    }



}
