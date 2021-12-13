package controller;

import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import java.util.Optional;

/**
 * The modify part controller retrieves a selected part from MainController to be updated.
 * The program will automatically retrieve and populate the form fields and the user can update the part as needed.
 * The program will then check to ensure the fields are valid or invalid.
 * If valid, then the old part is overwritten with the new part and saved to the part table.
 */
public class ModifyPartController {

    //Labels
    public Label partSourceLabel;

    //Text Fields
    public TextField partIDText;
    public TextField partNameText;
    public TextField partInvText;
    public TextField partPriceText;
    public TextField partMaxText;
    public TextField partSourceText;
    public TextField partMinText;

    //Buttons
    public RadioButton inHousePartB;
    public RadioButton outsourcedPartB;
    public Button partCancelB;
    public Button savePartB;

    private Part part;

    /** Retrieves part to be modified.
     * @param part The part to be retrieved.
     */
    public void getPart(Part part) {
        this.part = part;

        if(part instanceof InHouse) {
            InHouse inhouse = (InHouse) part;
            partIDText.setText(Integer.toString(inhouse.getId()));
            partNameText.setText(inhouse.getName());
            partPriceText.setText(Double.toString(inhouse.getPrice()));
            partInvText.setText(Integer.toString(inhouse.getStock()));
            partMaxText.setText(Integer.toString(inhouse.getMax()));
            partMinText.setText(Integer.toString(inhouse.getMin()));
            partSourceText.setText(Integer.toString(inhouse.getMachineId()));
            inHousePartB.setSelected(true);
        }

        else if(part instanceof Outsourced){
            Outsourced outsourced = (Outsourced) part;
            partIDText.setText(Integer.toString(outsourced.getId()));
            partNameText.setText(outsourced.getName());
            partPriceText.setText(Double.toString(outsourced.getPrice()));
            partInvText.setText(Integer.toString(outsourced.getStock()));
            partMaxText.setText(Integer.toString(outsourced.getMax()));
            partMinText.setText(Integer.toString(outsourced.getMin()));
            partSourceText.setText(outsourced.getCompanyName());
            partSourceLabel.setText("Company Name: ");
            outsourcedPartB.setSelected(true);
            inHousePartB.setSelected(false);
        }
    }

    /** Changes the label to say Machine ID when in house button is clicked. */
    public void onInHousePartB() {
        partSourceLabel.setText("Machine ID:");
    }

    /** Changes the label to say Company name when outsourced button is clicked. */
    public void onOutsourcedPartB() {
        partSourceLabel.setText("Company Name:");
    }

    /** Saves the modified changes to the part. */
    public void onSavePartB() throws NumberFormatException, NullPointerException {
        System.out.println("Saving.");

        try {
            int id = Integer.parseInt(partIDText.getText());
            String name = partNameText.getText();
            int stock = Integer.parseInt(partInvText.getText());
            double price = Double.parseDouble(partPriceText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());

            if(name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Name field can't be empty.");
                alert.showAndWait();
                return;
            }

            try {
                stock = Integer.parseInt(partInvText.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory field must be a number.");
                alert.showAndWait();
            }

            if(stock < min || stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory must be a number between min and max.");
                alert.showAndWait();
                return;
            }

            try {
                price = Double.parseDouble(partPriceText.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Price must be a number.");
                alert.showAndWait();
            }

            if(price <= 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Price must be greater than $0.");
                alert.showAndWait();
                return;
            }

            try {
                min = Integer.parseInt(partMinText.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must contain a number.");
                alert.showAndWait();
            }

            if(min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be less than max.");
                alert.showAndWait();
                return;
            }

            try {
                max = Integer.parseInt(partMaxText.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Max must contain a number.");
                alert.showAndWait();
            }

            if(max < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Max must be greater than min.");
                alert.showAndWait();
                return;
            }

            if (inHousePartB.isSelected()) {

                int machineID = Integer.parseInt(partSourceText.getText());

                try {
                    machineID = Integer.parseInt(partSourceText.getText());
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Machine ID must be a number.");
                    alert.showAndWait();
                }

                if(machineID < 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Machine ID must be greater than 0.");
                    alert.showAndWait();
                    return;
                }

                Part newInHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                MainController.updateModPart(newInHousePart);
                Stage stage = (Stage) savePartB.getScene().getWindow();
                stage.close();
            }

            else if (outsourcedPartB.isSelected()) {

                String companyName = partSourceText.getText();

                if(companyName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Company Name can't be empty.");
                    alert.showAndWait();
                    return;
                }

                Part newOutSourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                MainController.updateModPart(newOutSourcedPart);
                Stage stage = (Stage) savePartB.getScene().getWindow();
                stage.close();

            }

        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid value at: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /** Returns to the main screen when cancel button is clicked. */
    public void onPartCancelB() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel without modifying part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) partCancelB.getScene().getWindow();
            stage.close();
        }
    }
}
