package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Part;
import model.Product;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The modify product controller retrieves a selected product and its values for editing.
 * The user can update any product values except for ID.
 * They can also remove or add associated parts.
 * The program then checks if the values are valid and updates the product with the new values and closes window.
 */
public class ModifyProductController implements Initializable {

    //Tables
    public TableView<Part> associatedPartTable;
    public TableView<Part> partTable;
    private Product product;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    //Text Fields
    public TextField productIDText;
    public TextField productNameText;
    public TextField productInvText;
    public TextField productMaxText;
    public TextField productMinText;
    public TextField productPriceText;
    public TextField searchPartText;

    //Buttons
    public Button addAssociatedPB;
    public Button searchPartB;
    public Button removeAssociatedPB;
    public Button saveProductB;
    public Button cancelProductB;

    //Table Columns
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInventoryLvl;
    public TableColumn partCost;
    public TableColumn associatedID;
    public TableColumn associatedName;
    public TableColumn associatedInv;
    public TableColumn associatedCost;

    /** Retrieves product information.
     * @param selectedProduct The product selected.
     */
    public void retrieveProduct(Product selectedProduct) {
        this.product = selectedProduct;

        productIDText.setText(Integer.toString(product.getId()));
        productNameText.setText(product.getName());
        productInvText.setText(Integer.toString(product.getStock()));
        productPriceText.setText(Double.toString(product.getPrice()));
        productMaxText.setText(Integer.toString(product.getMax()));
        productMinText.setText(Integer.toString(product.getMin()));
        associatedParts = product.getAssociatedParts();
        associatedPartTable.setItems(associatedParts);
    }

    /** Adds an associated part to a product. */
    public void onAddAssociatedPB() {
        System.out.println("Adding associated part.");

        Part SP = partTable.getSelectionModel().getSelectedItem();

        if(SP != null){
            associatedParts.add(SP);
            associatedPartTable.setItems(associatedParts);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }
    }

    /** Remove an associated part */
    public void onRemoveAssociatedPB() {
        Part SP = associatedPartTable.getSelectionModel().getSelectedItem();

        if(SP != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove the associated part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                associatedParts.remove(SP);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }
    }

    /** Searches for a part by its name or id. */
    public void onSearchPartB() {

        String q = searchPartText.getText();

        ObservableList<Part> searchParts = searchByAssociatedPart(q);

        if(searchParts.size() == 0) {
            try {
                int partID = Integer.parseInt(q);
                Part part = getPartID(partID);

                if (part != null)
                    searchParts.add(part);
            }
            catch(NumberFormatException e) {

                //ignore

            }
        }

        partTable.setItems(searchParts);
        searchPartText.setText("");
    }

    /** Searches by part name when called.
     * @param partialName The name or letter typed by the user.
     * @return Returns a part name that matches the name or letters typed by the user.
     */
    private ObservableList<Part> searchByAssociatedPart(String partialName){
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        ObservableList<Part> allParts = MainController.getAllParts();

        for(Part part : allParts){
            if(part.getName().toLowerCase().contains(partialName.toLowerCase())){
                namedPart.add(part);
            }
        }
        return namedPart;

    }

    /** Searches by part id when called.
     * @param partID The part ID.
     * @return  Returns a part that matches the id and if not found returns null.
     */
    private Part getPartID(int partID){
        ObservableList<Part> allParts = MainController.getAllParts();

        for(int i = 0; i < allParts.size(); i++){
            Part part = allParts.get(i);

            if(part.getId() == partID){
                return part;
            }
        }

        return null;
    }

    /** Saves and updates the modified product into the product table. */
    public void onSaveProductB() throws NumberFormatException{
        try {
            int id = Integer.parseInt(productIDText.getText());
            String name = productNameText.getText();
            int stock = Integer.parseInt(productInvText.getText());
            double price = Double.parseDouble(productPriceText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());

            if(name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
            }

            try{
                stock = Integer.parseInt(productInvText.getText());
            }
            catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory must be a number.");
                alert.showAndWait();
            }

            try{
                price = Double.parseDouble(productPriceText.getText());
            }
            catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Price must be a number.");
                alert.showAndWait();
            }

            try{
                min = Integer.parseInt(productMinText.getText());
            }
            catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be a number.");
                alert.showAndWait();
            }

            try{
                max = Integer.parseInt(productMaxText.getText());
            }
            catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Max must be a number.");
                alert.showAndWait();
            }

            if(price <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Price must be greater than $0");
                alert.showAndWait();
            }

            if(stock < min || stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory must be a number between min and max.");
                alert.showAndWait();
                return;
            }

            if(max < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Max must be greater than min.");
                alert.showAndWait();
                return;
            }

            if(min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be less than max.");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(id, name, price, stock, min, max);

            for(Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }

            MainController.updateModProduct(newProduct);
            Stage stage = (Stage) saveProductB.getScene().getWindow();
            stage.close();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid value at: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /** Closes the page without saving. */
    public void onCancelProductB() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel without modifying product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelProductB.getScene().getWindow();
            stage.close();
        }
    }

    /** Sets the part table and associated part table with correct values and displays on the modify product form. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLvl.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTable.setItems(MainController.getAllParts());

        associatedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
