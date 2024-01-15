package ui;

import model.*;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import persistence.JsonReaderCookbook;
import persistence.JsonWriterCookbook;
import persistence.JsonWriterInventory;
import persistence.JsonReaderInventory;
import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;

import java.io.FileNotFoundException;
import java.io.IOException;

// The primary cook application
public class CookApp extends JFrame implements Observer {
    private static final String JSON_STORE_Cb = "./data/cookbook.json";
    private static final String JSON_STORE_Inv = "./data/inventory.json";
    private Scanner userInput;
    private Inventory inventory;
    private Cookbook cookbookCore;
    private Recipe recipeEnter;
    private JsonWriterCookbook jsonWriterCb;
    private JsonWriterInventory jsonWriterInv;
    private JsonReaderCookbook jsonReaderCb;
    private JsonReaderInventory jsonReaderInv;
    private String recipeName;
    private List<String> recipeIngredients;
    private List<String> finalPrint;

    private JButton nextButton;
    private JButton prevButton;
    private JButton addCookbookButton;
    private JButton deleteCookbookButton;
    private JTextArea cookbookTextArea;
    private JTextArea inventoryTextArea;
    private JButton addInventoryButton;
    private JButton deleteInventoryButton;
    private JButton viewCookbookButton;
    private JButton viewInventoryButton;
    private JButton useCookbookButton;
    private JButton saveCookbookButton;
    private JButton loadCookbookButton;
    private JButton saveInventoryButton;
    private JButton loadInventoryButton;
    private JTabbedPane tabbedPane;

    //For adding recipes
    private JTextField recipeNameField;
    private JTextArea ingredientsArea;
    private JPanel dialogPanel;


    private int currentRecipeIndex;


    //EFFECTS: Runs cook app
    public CookApp() {
        //startCook();
        startApp();

    }


    //MODIFIES: this
    //EFFECTS: Takes user input and works through app plus intialization
    //         at the start of the app
    private void startApp() {
        startUp();
        setTitle("UBC Cookbook App");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cookbookTextArea = new JTextArea();
        cookbookTextArea.setEditable(false);

        inventoryTextArea = new JTextArea();
        inventoryTextArea.setEditable(false);

        initializeButtons();
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        setCookbookTab();
        setInventoryTab();

        add(tabbedPane, BorderLayout.CENTER);

        currentRecipeIndex = 0;
        //loadCookbook();
        //loadInventory();
        displayCookbook();
        displayInventory();
        buttonActions();


    }

    // EFFECTS: Make all buttons (saves line on Panel set up)
    private void initializeButtons() {
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        addCookbookButton = new JButton("Add Recipe");
        deleteCookbookButton = new JButton("Delete Recipe");
        addInventoryButton = new JButton("Add to Inventory");
        deleteInventoryButton = new JButton("Delete Inventory Item");
        saveCookbookButton = new JButton("Save Cookbook");
        loadCookbookButton = new JButton("Load/View Cookbook");
        saveInventoryButton = new JButton("Save Inventory");
        loadInventoryButton = new JButton("Load/View Inventory");
        useCookbookButton = new JButton("Generate Meal Options");
    }

    //EFFECTS: Sets up the Cookbook tab
    private void setCookbookTab() {
        JPanel cookbookPanel = new JPanel(new BorderLayout());
        JPanel cookbookButtonPanel = new JPanel();
        cookbookButtonPanel.add(prevButton);
        cookbookButtonPanel.add(nextButton);
        cookbookButtonPanel.add(addCookbookButton);
        cookbookButtonPanel.add(deleteCookbookButton);
        cookbookButtonPanel.add(useCookbookButton);
        cookbookButtonPanel.add(saveCookbookButton);
        cookbookButtonPanel.add(loadCookbookButton);

        cookbookPanel.add(cookbookTextArea, BorderLayout.CENTER);

        cookbookPanel.add(cookbookButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Cookbook", cookbookPanel);
    }

    // EFFECTS: Sets up the Inventory Tab and adds to the main panel
    private void setInventoryTab() {
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        JPanel inventoryButtonPanel = new JPanel();
        inventoryButtonPanel.add(addInventoryButton);
        inventoryButtonPanel.add(deleteInventoryButton);
        inventoryButtonPanel.add(saveInventoryButton);
        inventoryButtonPanel.add(loadInventoryButton);

        inventoryPanel.add(inventoryTextArea, BorderLayout.CENTER);
        inventoryPanel.add(inventoryButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Inventory", inventoryPanel);
    }


    // EFFECT: Displays cookbook (showing recipes with name and ingredients) on the panel
    private void displayCookbook() {
        List<Recipe> displayCookbook = cookbookCore.showRecipes();
        if (displayCookbook.isEmpty()) {
            cookbookTextArea.setText("Welcome to UBC Cook! The cookbook is empty! Add some recipes to it "
                    + "or load from a saved file!");
        } else {
            cookbookTextArea.setText(displayCookbook.get(currentRecipeIndex).toString());
        }

    }

    // EFFECTS: Shows inventory on the panel
    private void displayInventory() {
        List<String> currentIngredients = inventory.showInventory();
        inventoryTextArea.setText(currentIngredients.toString());
    }

    //CITATION: CodeJava.net (How to use Java Lambda expression for action listener
    //          in Swing). Made the design more streamline
    // EFFECTS: Sets up buttons to work (and saves lines for the panel development)
    private void buttonActions() {
        prevButton.addActionListener(e -> showPreviousRecipe());
        nextButton.addActionListener(e -> showNextRecipe());
        addCookbookButton.addActionListener(e -> addNewRecipe());
        deleteCookbookButton.addActionListener(e -> deleteRecipeFromCookbook());
        saveCookbookButton.addActionListener(e -> saveCookbook());
        loadCookbookButton.addActionListener(e -> loadCookbook());
        saveInventoryButton.addActionListener(e -> saveInventory());
        loadInventoryButton.addActionListener(e -> loadInventory());
        addInventoryButton.addActionListener(e -> addToInventory());
        deleteInventoryButton.addActionListener(e -> deleteFromInventory());
        useCookbookButton.addActionListener(e -> useCookbook());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });
    }

    //@Override
    //	public void printLog(EventLog el) {
    //		for (Event next : el) {
    //            logArea.setText(logArea.getText() + next.toString() + "\n\n");
    //        }
    //
    //		repaint();
    //	}

    //if (resultsList.size() != 0) {
    //            for (Recipe recipe : resultsList) {
    //                resultText.append(recipe.toString() + "\n");
    //                System.out.println("You can cook: " + recipe.getRecipeName() + " which is made with: "
    //                        + recipe.getIngredientsNeeded());
    //            }
    //            cookbookTextArea.setText(resultText.toString());

    public void printLog(EventLog el) {
        StringBuilder resultText = new StringBuilder("Event Log: \n\n");
        for (Event next : el) {
            resultText.append(next.toString() + "\n\n");
        }
        System.out.println(resultText);
    }

    // EFFECTS: Sets up pop-up panel that allows for adding the recipe
    private void addNewRecipe() {
        recipeNameField = new JTextField();
        ingredientsArea = new JTextArea();
        dialogPanel = new JPanel(new GridLayout(0, 2));
        dialogPanel.add(new JLabel("Recipe Name:"));
        dialogPanel.add(recipeNameField);
        dialogPanel.add(new JLabel("Ingredients (One per line):"));
        dialogPanel.add(new JScrollPane(ingredientsArea));
        recipeAdder();
    }


    // REQUIRES: # of Ingredients added into the inventory is non-zero
    // MODIFIES: this
    // EFFECTS: Adds recipe into the cookbook when the "Add Recipe" button is pressed
    private void recipeAdder() {
        int result = JOptionPane.showConfirmDialog(null, dialogPanel, "Add Recipe",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String recipeName = recipeNameField.getText().trim().toLowerCase();
            String ingredientsText = ingredientsArea.getText().trim().toLowerCase();
            String[] ingredientLines = ingredientsText.split("\n");

            if (!recipeName.isEmpty() && ingredientLines.length > 0) {
                List<String> recipeIngredients = new ArrayList<>();
                for (String ingredient : ingredientLines) {
                    recipeIngredients.add(ingredient);
                }
                Recipe recipeEnter = new Recipe(recipeName, recipeIngredients);
                cookbookCore.addRecipe(recipeEnter);
                currentRecipeIndex = cookbookCore.getSize() - 1;
                displayCookbook();
            }
        }
    }

    // EFFECTS: Shows recipes you can make based on inventory
    private void useCookbook() {
        List<Recipe> cookbookMain = cookbookCore.showRecipes();
        List<Recipe> resultsList = cookbookCore.findRecipe(cookbookMain, inventory);
        //List<String> missingIngredients = new ArrayList<>();
        JTextArea recommendField = new JTextArea();
        recommendField.setEditable(false);
        JPanel recommendDialogPanel = new JPanel(new GridLayout(0, 1));
        recommendDialogPanel.add(new JLabel("You can make these recipes:"));
        recommendDialogPanel.add(recommendField);
        StringBuilder resultText = new StringBuilder("You can make: \n \n");


        if (resultsList.size() != 0) {
            for (Recipe recipe : resultsList) {
                resultText.append(recipe.toString() + "\n");
                //System.out.println("You can cook: " + recipe.getRecipeName() + " which is made with: "
                //        + recipe.getIngredientsNeeded());
            }
            cookbookTextArea.setText(resultText.toString());
        } else {
            cookbookTextArea.setText("Sorry! Unfortunately, you can't cook anything!");
            //System.out.println("Sorry! Unfortunately, you can't cook anything!");
        }

        //cookbookTextArea.setText("Hello World!");
    }

    // REQUIRES: # of Ingredients added into the inventory is non-zero
    // MODIFIES: this
    // EFFECTS: Adds foods into the Inventory
    private void addToInventory() {
        JTextArea inventoryArea = new JTextArea();
        JPanel inventoryDialogPanel = new JPanel(new GridLayout(0, 2));
        inventoryDialogPanel.add(new JLabel("Ingredient to add (One per line):"));
        inventoryDialogPanel.add(new JScrollPane(inventoryArea));
        int result = JOptionPane.showConfirmDialog(null, inventoryDialogPanel,
                "Add Ingredient to Inventory",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String inventoryIngredientText = inventoryArea.getText().trim().toLowerCase();
            String[] inventoryIngredientLines = inventoryIngredientText.split("\n");

            if (inventoryIngredientText != null && inventoryIngredientLines.length > 0) {
                for (String newIngredient : inventoryIngredientLines) {
                    inventory.addIngredients(newIngredient);
                }
            }
            displayInventory();
        }
    }

    // REQUIRES: # of ingredients to delete is non-zero
    // MODIFIES: this
    // EFFECT: Deletes recipe from the cookbook
    private void deleteRecipeFromCookbook() {
        String recipeToDelete = JOptionPane.showInputDialog(this, "What is the name of the recipe you want to remove");
        recipeToDelete.toLowerCase();
        cookbookCore.removeRecipe(recipeToDelete);
        if (currentRecipeIndex >= cookbookCore.getSize()) {
            currentRecipeIndex = (cookbookCore.getSize() - 1);
        }
        displayCookbook();
    }


    // EFFECTS: Initializes the main menu, inventory, and cookbook
    private void startUp() {
        //userInput = new Scanner(System.in);
        //userInput.useDelimiter("\n");
        cookbookCore = new Cookbook();
        inventory = new Inventory();
        jsonWriterCb = new JsonWriterCookbook(JSON_STORE_Cb);
        jsonReaderCb = new JsonReaderCookbook(JSON_STORE_Cb);
        jsonWriterInv = new JsonWriterInventory(JSON_STORE_Inv);
        jsonReaderInv = new JsonReaderInventory(JSON_STORE_Inv);
    }

    // EFFECTS: Shows previous recipe in the cookbook panel
    private void showPreviousRecipe() {
        if (currentRecipeIndex > 0) {
            currentRecipeIndex--;
            displayCookbook();
        }
    }

    // EFFECTS: Shows next recipe in the cookbook panel
    private void showNextRecipe() {
        List<Recipe> displayed = cookbookCore.showRecipes();
        if (currentRecipeIndex < (displayed.size() - 1)) {
            currentRecipeIndex++;
            displayCookbook();
        }
    }


    // CITATION: FROM WorkRoomApp.java from JsonSerializationDemo
    // EFFECTS: Saves the Cookbook to file
    private void saveCookbook() {
        try {
            jsonWriterCb.openCookbook();
            jsonWriterCb.writeCookbook(cookbookCore);
            jsonWriterCb.closeCookbook();
            cookbookTextArea.setText("Saved Cookbook to " + JSON_STORE_Cb);
        } catch (FileNotFoundException e) {
            cookbookTextArea.setText("Unable to write to file: " + JSON_STORE_Cb);
        }
    }

    // CITATION: FROM WorkRoomApp.java from JsonSerializationDemo
    // EFFECTS: Saves the Inventory to file
    private void saveInventory() {
        try {
            jsonWriterInv.openInventory();
            jsonWriterInv.writeInventory(inventory);
            jsonWriterInv.closeInventory();
            inventoryTextArea.setText("Saved Inventory to " + JSON_STORE_Cb);
            //System.out.println("Saved Cookbook to " + JSON_STORE_Cb);
        } catch (FileNotFoundException e) {
            inventoryTextArea.setText("Unable to write to file: " + JSON_STORE_Cb);
        }
    }

    // CITATION: FROM WorkRoomApp.java from JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads cookbook from file
    private void loadCookbook() {
        try {
            cookbookCore = jsonReaderCb.readCookbook();
            //System.out.println("Loaded Cookbook from " + JSON_STORE_Cb);
            displayCookbook();
        } catch (IOException e) {
            cookbookTextArea.setText("Unable to read from file: " + JSON_STORE_Cb);
        }
    }

    // CITATION: FROM WorkRoomApp.java from JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads inventory from file
    private void loadInventory() {
        try {
            inventory = jsonReaderInv.readInventory();
            displayInventory();
            //System.out.println("Loaded Inventory from " + JSON_STORE_Inv);
        } catch (IOException e) {
            inventoryTextArea.setText("Unable to read from file: " + JSON_STORE_Inv);
        }
    }

    // END OF CODE FOR GUI RELATED APP
    //-----------------------------------------------------------------------------------------------
    //The code below was created for the console app



    //MODIFIES: this
    //EFFECTS: Takes user input and works through app plus initialization
    //         at the start of the app
    private void startCook() {
        boolean appOn = true;
        String command = null;
        startUp();

        while (appOn) {
            mainMenu();
            command = userInput.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                appOn = false;
                System.out.println("\nThank you for using UBCCook! Happy Cooking! :)");
            } else {
                controller(command);
            }
        }
    }



    // Got this code from TellerApp.Java (modified to fit my app)
    // EFFECTS: Initializes the main menu
    private void initialize() {
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
    }

    // Got this code from TellerApp.Java (modified to fit my app)
    //MODIFIES: this
    //EFFECTS: Takes user input and works through app (go back to main menu)
    private void runCook() {
        boolean appOn = true;
        String command = null;
        initialize();

        while (appOn) {
            mainMenu();
            command = userInput.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                appOn = false;
                System.out.println("\nThank you for using UBCCook! Happy Cooking! :)");
            } else {
                controller(command);
            }
        }
    }

    // Got this from the code from TellerApp.Java (modified to fit
    // my app's purpose)
    private void controller(String command) {
        if (command.equals("a")) {
            //System.out.println("Adding recipe into cookbook"); //Stub
            editCookbook(); // add or remove recipe into cookbook
            //addRecipeIntoCookbook();  // Working version (simplified)
        } else if (command.equals("b")) {
            //System.out.println("Adding food into inventory"); //Stub
            editInventory(); //Add food into inventory
            //addFoodIntoInventory(); //Working version (simplified)
        } else if (command.equals("c")) {
            //System.out.println("Viewing cookbook"); //Stub
            viewCookbook();
        } else if (command.equals("d")) {
            //System.out.println("Viewing inventory"); //Stub
            viewInventory(); //Finds recipes based on inventory
        } else if (command.equals("e")) {
            //System.out.println("Finding recipes based on your inventory");
            useCookbook(); //Finds recipes based on inventory
        } else if (command.equals("f")) {
            saveCookbook(); //Save cookbook to file
            saveInventory(); //Save inventory to file
        } else if (command.equals("g")) {
            loadCookbook(); //Load cookbook from file
            loadInventory(); //Load inventory from file
        } else {
            System.out.println("Selection not valid. Please choose from the available options.");
        }
    }

    // Got this code from TellerApp.java from Example given by EdX
    //EFFECTS: Sets main menu of app
    private void mainMenu() {
        System.out.println("\nWelcome to UBCCook. Please select from the following options:");
        System.out.println("\na -> Edit Cookbook (Add or Remove recipes)"); //Add recipe into cookbook U1
        System.out.println("\nb -> Edit inventory (Add or remove foods)"); //add food into inventory U2
        System.out.println("\nc -> View cookbook (all recipes)"); //view the cookbook U3
        System.out.println("\nd -> View inventory"); //View the inventory U3
        System.out.println("\ne -> What am I cooking today?"); //Set comparison U4
        System.out.println("\nf -> Save Cookbook and Inventory to file"); //Saves both cookbook and inventory
        System.out.println("\ng -> Load Cookbook and Inventory from file"); //Loads both cookbook and inventory
        System.out.println("\nq -> Quit app"); //End application
    }

    // MODIFIES: this
    // modifies Cookbook (set up as menu to add or remove recipes)
    private void editCookbook() {
        System.out.println("Press a to add recipes, "
                + "r to remove recipes from cookbook, or "
                + "q to go back to main menu.");
        String choice = userInput.next().toLowerCase();
        if (choice.equals("a")) {
            addRecipeIntoCookbook();
        } else if (choice.equals("r")) {
            removeRecipeFromCookbook();
        } else if (choice.equals("q")) {
            runCook();
        } else {
            System.out.println("Invalid option. Please select again");
        }
    }

    // MODIFIES: this
    // modifies Inventory (set up as menu to add or remove ingredients in inventory)
    private void editInventory() {
        System.out.println("Press a to add food into the inventory, "
                + "r to remove food from the inventory, or "
                + "q to go back to main menu.");
        String choice = userInput.next().toLowerCase();
        if (choice.equals("a")) {
            addFoodIntoInventory();
        } else if (choice.equals("r")) {
            removeFoodFromInventory();
        } else if (choice.equals("q")) {
            runCook();
        } else {
            System.out.println("Invalid option. Please select again");
        }
    }


    // MODIFIES: this
    // EFFECTS: Remove recipe into the Cookbook
    private void removeRecipeFromCookbook() {
        boolean keepRemoving = true;
        while (keepRemoving) {
            System.out.println("What is the name of the recipe you want to remove? Enter q if you are done!");
            String recipeName = userInput.next().toLowerCase();
            if (recipeName.equals("q")) {
                keepRemoving = false;
            } else {
                cookbookCore.removeRecipe(recipeName);
                System.out.println("Removal Successful!");
            }
        }
    }


    // REQUIRES: # of Ingredients added into recipe is non-zero
    // MODIFIES: this
    // EFFECTS: Adds recipe into the Cookbook
    private void addRecipeIntoCookbook() {
        System.out.println("What is the name of the recipe you want to add?");
        String recipeName = userInput.next().toLowerCase();

        List<String> ingredients = new ArrayList<String>();
        boolean keepAdding = true;

        while (keepAdding) {
            System.out.println("What are the ingredients in your recipe? Press q to quit");
            String command2 = userInput.next().toLowerCase();
            if (command2.equals("q")) {
                keepAdding = false;
            } else {
                ingredients.add(command2);
            }
        }
        recipeEnter = new Recipe(recipeName, ingredients);
        cookbookCore.addRecipe(recipeEnter);
        System.out.println("Recipe named: " + recipeEnter.getRecipeName().toUpperCase() + " with ingredients: "
                + recipeEnter.getIngredientsNeeded() + " has been added into the Cookbook!");
    }

    // REQUIRES: # of Ingredients added into the inventory is non-zero
    // MODIFIES: this
    // EFFECTS: Adds foods into the Inventory
    private void addFoodIntoInventory() {
        boolean keepAdding2 = true;
        while (keepAdding2) {
            System.out.println("What do you want to add into the inventory (Pantry, fridge, freezer, etc)? "
                    + "Press q to quit");
            String command3 = userInput.next().toLowerCase();
            if (command3.equals("q")) {
                keepAdding2 = false;
            } else {
                inventory.addIngredients(command3);
                System.out.println(command3 + " has been added into inventory!");
            }
        }

        System.out.print(inventory.showInventory() + " is now in your inventory!");
    }




    // REQUIRES: # of Ingredients removed from the inventory is non-zero
    // MODIFIES: this
    // EFFECTS: Removes foods into the Inventory
    private void removeFoodFromInventory() {
        boolean keepRemoving2 = true;
        while (keepRemoving2) {
            System.out.println("What do you want to REMOVE from the inventory (Pantry, fridge, freezer, etc)? "
                    + "Press q to quit");
            String command4 = userInput.next().toLowerCase();
            if (command4.equals("q")) {
                keepRemoving2 = false;
            } else {
                inventory.removeIngredients(command4);
                System.out.println(command4 + " has been removed from the inventory!");
            }
        }
        System.out.print("Removal was successful! " + inventory.showInventory()
                + " is your current inventory!");
    }

    // REQUIRES: # of Ingredients removed from the inventory is non-zero
    // MODIFIES: this
    // EFFECTS: Removes foods into the Inventory
    private void deleteFromInventory() {
        String toDelete = JOptionPane.showInputDialog(this,
                "What do you want to REMOVE from the inventory (Pantry, fridge, freezer, etc)?").toLowerCase();
        if (toDelete != null && !toDelete.trim().isEmpty()) {
            inventory.removeIngredients(toDelete);
            displayInventory();
        }

    }

    // EFFECTS: Views Cookbook recipes
    private void viewCookbook() {
        List<Recipe> results = cookbookCore.showRecipes();
        List<String> recipeNames = new ArrayList<>();
        System.out.println("Current Cookbook recipes: ");
        for (Recipe recipe : results) {
            //recipeNames.add(recipe.getRecipeName());
            recipeName = recipe.getRecipeName();
            recipeIngredients = recipe.getIngredientsNeeded();
            System.out.println("\nName: " + recipeName + "\nIngredients: " + recipeIngredients);
        }

    }

    // EFFECTS: Views Inventory
    private void viewInventory() {
        List<String> currentIngredients = inventory.showInventory();
        System.out.println("Current ingredients in the inventory: " + currentIngredients);
    }


    @Override
    public void update() {
        //Stub?
    }
}
