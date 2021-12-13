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
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * The Add product controller adds a new product to the product table.
 * The user can enter a product name, inventory amount, price, and min/max inventory amount.
 * The user can also add an associated part to the product
 * The program checks to ensure values are valid and saves and closes screen if they are.
 * If invalid then program will send an error to user to fix the issue.
 */

public class AddProductController implements Initializable {

    //Tables
    public TableView<Part> associatedPartTable;
    public TableView<Part> partTable;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    //Text Fields
    public TextField productIDText;
    public TextField productNameText;
    public TextField productInvText;
    public TextField productMaxText;
    public TextField productPriceText;
    public TextField productMinText;
    public TextField searchPartText;

    //Table Columns
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInventoryLvl;
    public TableColumn partCost;
    public TableColumn associatedID;
    public TableColumn associatedName;
    public TableColumn associatedInv;
    public TableColumn associatedCost;

    //Buttons
    public Button cancelProductB;
    public Button removeAssociatedPB;
    public Button saveProductB;
    public Button addAssociatedPB;
    public Button searchPartB;

    Random random = new Random();
    boolean idMatch = false;
    int productID;

    /** Generate unique id for product.
     * @return Returns a generated id for the product.
     */
    public int generateProductID(){
        int genIDProductID;
        genIDProductID = 1 + random.nextInt(999999);

        for(Part part : MainController.getAllParts()){
            if(part.getId() == genIDProductID) {
                idMatch = true;
                generateProductID();
            }
        }
        return genIDProductID;
    }

    /** Saves product and closes to main screen.
     * @param actionEvent The action event that saves a product when save button is clicked.
     */
    public void onSaveProductB(ActionEvent actionEvent) throws NumberFormatException {
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
                MainController.addProduct(newProduct);
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainPane.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 900, 500);
                stage.setTitle("Inventory Main");
                stage.setScene(scene);
                stage.show();
        }
        catch(NumberFormatException | IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid value at: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /** Leaves add product page without saving.
     * @param actionEvent The action event that cancels a product when cancel button is clicked.
     */
    public void onCancelProductB(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel without saving new product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainPane.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 500);
            stage.setTitle("Inventory Main");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Search part function */
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
                //Ignore
            }
        }

        partTable.setItems(searchParts);
        searchPartText.setText("");

    }

    /** Searches by part name when called.
     * @param partialName Any letter or name of a part.
     * @return Returns a part that matches the letter or name that user inputted.
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
     * @return Returns a part with a matching id or none if no matches.*/
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

    /** Add Associated part */
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

    /** Sets the part table and associated part table with parts and generates a unique ID for the product. */
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
    associatedPartTable.setItems(associatedParts);

        productID = generateProductID();
        productIDText.setText(Integer.toString(productID));
    }
}
