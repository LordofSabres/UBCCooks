package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Holds the inventory of food (the food in the pantry, fridge, etc). Use for comparison
public class Inventory extends Observable implements Writable {
    private List<String> inventory; // the inventory/pantry of materials
    private JSONArray jsArrayInv;

    //EFFECTS: Inventory is created as an empty list of strings
    public Inventory() {
        inventory = new ArrayList<String>();
    }

    //MODIFIES: this
    //EFFECTS: Add ingredients into the Inventory
    public void addIngredients(String ing) {

        inventory.add(ing);
        notifyObservers();
        EventLog.getInstance().logEvent(new Event("Ingredients added into Inventory."));
    }

    //MODIFIES: this
    //EFFECTS: Remove ingredients from the Inventory
    public void removeIngredients(String ing) {
        inventory.remove(ing);
        notifyObservers();
        EventLog.getInstance().logEvent(new Event("Ingredients removed from Inventory."));
    }

    //EFFECTS: Shows the inventory
    public List<String> showInventory() {
        return inventory;
    }

    public int getSize() {
        return inventory.size();
    }

    // CITATION: Design from WorkRoom.java from JsonSerializationDemo
    // CITATION: jsArrayInv based on trick learned from StackExchange
    // EFFECT: Takes inventory and places into JSON
    @Override
    public JSONObject toJson() {
        JSONObject jsonInventory = new JSONObject();
        jsArrayInv = new JSONArray(inventory);
        jsonInventory.put("inventory", jsArrayInv);
        return jsonInventory;
    }

    @Override
    public void notifyObservers() {
        for (Observer next : observers) {
            next.update();
        }
    }

}
