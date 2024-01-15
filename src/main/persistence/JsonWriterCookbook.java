package persistence;

import org.json.JSONObject;
import model.*;

import java.io.*;

// All following functions Used from JsonSerializationDemo!! (CITATION)
// Represents a writer that writes the JSON representation of Cookbook to file
public class JsonWriterCookbook {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: makes writer for cookbook to write to the destination file
    public JsonWriterCookbook(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cant be opened
    // for writing
    public void openCookbook() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of cookbook to file
    public void writeCookbook(Cookbook c) {
        JSONObject json = c.toJson();
        saveCookbookToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void closeCookbook() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveCookbookToFile(String json) {
        writer.print(json);
    }



}
