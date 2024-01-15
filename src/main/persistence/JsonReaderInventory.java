package persistence;


import model.Inventory;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.stream.Stream;

import org.json.*;

public class JsonReaderInventory {
    private String source;

    public JsonReaderInventory(String entry) {
        this.source = entry;
    }

    // EFFECTS: reads Inventory from file and returns it;
    // throws IOException if an error occurs reading the data from the file
    public Inventory readInventory() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads the source file as a string and returns as string
    private String readFile(String source) throws IOException {
        StringBuilder creator = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> creator.append(s));
        }

        return creator.toString();
    }

    // USED GEEKSFORGEEKS.ORG FOR THE FOR LOOP
    // EFFECTS: Parses Cookbook from JSON Object and returns the cookbook
    private Inventory parseInventory(JSONObject jsonObject) {
        Inventory inv = new Inventory();

        JSONArray jsonArray3 = jsonObject.getJSONArray("inventory");
        for (int i = 0; i < jsonArray3.length(); i++) {
            inv.addIngredients(jsonArray3.getString(i));
        }
        return inv;
    }
}
