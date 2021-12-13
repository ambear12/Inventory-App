package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The MainController deals with the inventory main page.
 * The user can add, modify, or delete a part/product and will be redirected if needed.
 * The user can also search for a part/product either by partial name or exact ID.
 */

public class MainController implements Initializable {

    //Table Data
    public TableView partsTable;
    public TableColumn partName;
    public TableColumn partID;
    public TableColumn partInventoryLvl;
    public TableColumn partPrice;
    public TableView productsTable;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInventoryLvl;
    public TableColumn productPrice;

    //Buttons
    public Button addProductB;
    public Button modProductB;
    public Button deleteProductB;
    public Button exitB;

    //Labels
    public Label partLabel;
    public Label productLabel;

    //Text Fields
    public TextField searchPartMain;
    public TextField searchProductMain;

    /** An observable list that stores all parts in the part table. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** An observable list that stores all products in the product table. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds a new part to the part table.
     * @param newPart The new part to be added.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Adds a new product to the product table.
     * @param newProduct The new product to be added.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Looks up a parts ID and compares to the partID and if it matches will display in part table.
     * @param partID The parts ID.
     * @return Returns a part that matches the part ID or null if not found.
     */
    private static Part lookupPart(int partID){
        ObservableList<Part> allParts = getAllParts();

        for(int i = 0; i < allParts.size(); i++){
            Part part = allParts.get(i);
            if(part.getId() == partID){
                return part;
            }
        }
        return null;
    }

    /** Looks up a products ID and compares to the productID and if it matches will display in product table.
     * @param productID  The products ID.
     * @return  Returns a product with the matching ID or null if not found.
     */
    private static Product lookupProduct(int productID){
        ObservableList<Product> allProducts = getAllProducts();

        for(int i = 0; i < allProducts.size(); i++){
            Product product = allProducts.get(i);
            if(product.getId() == productID){
                return product;
            }
        }
        return null;
    }

    /** Checks to see if the characters in a query match any parts and then displays results in part table.
     * @param partName The parts name.
     * @return Returns the part that matches the name or characters typed by the user.*/
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> part = FXCollections.observableArrayList();
        for(Part p : allParts){
            if(p.getName().toLowerCase().contains(partName.toLowerCase())){
                part.add(p);
            }
        }
        return part;
    }

    /** Checks to see if the characters in a query match any products and then displays results in product table.
     * @param productName The products name.
     * @return  Returns the product that matches the name or characters typed by the user.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> product = FXCollections.observableArrayList();
        for(Product prod : allProducts){
            if(prod.getName().toLowerCase().contains(productName.toLowerCase())){
                product.add(prod);
            }
        }
        return product;
    }

    /** Updates the part table with the modified part by comparing their ID's and then replacing the old part.
     * @param selectedPart The selected part to be modified.
     */
    public static void updateModPart(Part selectedPart){
        for(int i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == selectedPart.getId()){
                allParts.set(i, selectedPart);
            }
        }
    }

    /** Updates the part table with the modified part by comparing their ID's and then replacing the old product.
     * @param newProduct The product to be modified.
     */
    public static void updateModProduct(Product newProduct){
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == newProduct.getId()){
                allProducts.set(i, newProduct);
            }
        }
    }

    /** Deletes a selected part.
     * @param selectedPart The part that is selected.
     * @return Removes a selected part if true.
     */
    public static boolean deletePart (Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }

    /** Deletes a selected product.
     * @param selectedProduct The product that is selected.
     * @return Removes a selected product if true.
     */
    public static boolean deleteProduct (Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }

    /** Gets the parts in the part table.
     * @return Returns all parts from part table.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Gets the products in the product table.
     * @return Returns all products from the product table.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


     //Below are the buttons and text fields on the inventory screen.


    /** Opens the add part form.
     * @param actionEvent Opens the add part form.
     */
    public void onAddPartB (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene (root, 900, 500);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /** Opens the add product form.
     * @param actionEvent Opens the add product form.
     */
    public void onAddProductB(ActionEvent actionEvent) throws IOException {
        System.out.println("Adding product.");

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene (root, 900, 500);
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();

    }

    /** Opens the modify part form. */
    public void onModPartB() {
        System.out.println("Modifying part.");

        if (partsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyPartForm.fxml"));
                Parent mainScreen = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Modify Part Form");
                stage.setScene(new Scene(mainScreen, 900, 500));
                stage.show();
                ModifyPartController controller = loader.getController();
                Part part = (Part) partsTable.getSelectionModel().getSelectedItem();
                controller.getPart(part);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
        }
    }

    /** Opens the modify product form. */
    public void onModProductB() {
        System.out.println("Modifying product.");

        if (productsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyProductForm.fxml"));
                Parent mainScreen = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Modify Product Form");
                stage.setScene(new Scene(mainScreen, 900, 500));
                stage.show();
                ModifyProductController controller = loader.getController();
                Product product = (Product) productsTable.getSelectionModel().getSelectedItem();
                controller.retrieveProduct(product);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
        }
    }

    /** When the delete button is clicked this will confirm deletion. If no part is selected returns error. */
    public void onDeletePartB() {
        Part SP = (Part) partsTable.getSelectionModel().getSelectedItem();

        if(SP != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm part deletion?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                MainController.deletePart(SP);
                System.out.println("Deleting part.");
            }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to delete.");
            alert.showAndWait();
        }
    }

    /** When the delete button is clicked this will confirm deletion. If no product is selected returns error. */
    public void onDeleteProductB() {
        Product SP = (Product) productsTable.getSelectionModel().getSelectedItem();

        if(SP != null){
            if(!SP.getAssociatedParts().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please remove all associated parts to delete this product.");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm product deletion?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    MainController.deleteProduct(SP);
                    System.out.println("Deleting product.");
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }
    }

    /** Query search field for part table and displays the searched options in the table. */
    public void getPartResult() {
        String q = searchPartMain.getText();

        ObservableList<Part> parts = lookupPart(q);

        if(parts.size() == 0){
            int partID = Integer.parseInt(q);
            Part part = lookupPart(partID);

            if(part != null)
                parts.add(part);
        }

        partsTable.setItems(parts);
        searchPartMain.setText("");
    }

    /** Query search field for the product table and displays the searched options in the table. */
    public void getProductResult() {
        String q = searchProductMain.getText();

        ObservableList<Product> products = lookupProduct(q);

        if(products.size() == 0){
            int productID = Integer.parseInt(q);
            Product product = lookupProduct(productID);

            if(product != null)
                products.add(product);
        }

        productsTable.setItems(products);
        searchProductMain.setText("");

    }

    /** Displays the test data in table only once. */
    private static boolean firstTime = true;
    private void testData(){
        if (!firstTime){
            return;
        }
        firstTime = false;

        //Adding products into the product table
        allProducts.add(new Product(1000, "Giant Bike",299.99, 5, 1, 20));
        allProducts.add(new Product(1001, "Tricycle",99.99, 3, 1, 20));
        //Adding parts into the part table.
        allParts.add(new InHouse(1, "Brakes", 15.00, 10, 1, 20, 111));
        allParts.add(new InHouse(2,"Wheel", 11.00, 16, 1, 20, 123));
        allParts.add(new InHouse(3, "Seat", 15.00, 10, 1, 20,231));
    }

    /** Confirms and exits the application when button is pressed. */
    public void onExitB() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the application?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Exiting.");
            System.exit(0);
        }
    }

    /** Initialization sets the tables with the products and parts. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("I am initialized.");
        testData();

        partsTable.setItems(allParts);
        productsTable.setItems(allProducts);

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Product> products = getAllProducts();
        productsTable.setItems(products);

        ObservableList<Part> parts = getAllParts();
        partsTable.setItems(parts);
    }
}
