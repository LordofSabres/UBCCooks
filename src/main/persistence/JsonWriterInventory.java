package persistence;

import org.json.JSONObject;
import model.*;

import java.io.*;

// All following functions Used from JsonSerializationDemo!! (CITATION)
// Represents a writer that writes the JSON representation of Inventory to file
public class JsonWriterInventory {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: makes writer for cookbook to write to the destination file
    public JsonWriterInventory(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cant be opened
    // for writing
    public void openInventory() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of inventory to file
    public void writeInventory(Inventory i) {
        JSONObject json = i.toJson();
        saveInventoryToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void closeInventory() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveInventoryToFile(String json) {
        writer.print(json);
    }

}
