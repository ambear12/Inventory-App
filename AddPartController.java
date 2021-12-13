package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * The Add part controller adds either a new in house or outsourced part.
 * The user inputs a name, inventory amount, price, minimum inventory amount, and a maximum inventory amount.
 * If the part is in house then user inputs a machine ID, if it is outsourced then input is a company name.
 * The program then checks to make sure valid values are entered and if they are it will save and close, if not it tells
 * the user what the error is stays on the page.
 */
public class AddPartController implements Initializable {

    //Labels
    public Label machineIDLabel;

    //Text Fields
    public TextField partNameText;
    public TextField partInvText;
    public TextField partPriceText;
    public TextField partMaxText;
    public TextField partMinText;
    public TextField partIDText;
    public TextField partSourceText;

    //Buttons
    public RadioButton inHousePartB;
    public RadioButton outsourcedPartB;
    public ToggleGroup togglePart;
    public Button savePartB;

    Random random = new Random();
    boolean idMatch = false;
    int partID;

    /** Creates a random unique ID for the new part.
     * @return returns a generated ID for a part.*/
    public int generatePartID() {
        int genIDPartD;
        genIDPartD = 1 + random.nextInt(999999);

        for (Part part : MainController.getAllParts()) {
            if (part.getId() == genIDPartD) {
                idMatch = true;
                generatePartID();
            }
        }
        return genIDPartD;
    }

    /**
     * The save feature on the parts form saves a new part to the parts table.
     * @param actionEvent The action event that saves a part when clicked.

     **<p><b>
     * A run-time error that I ran into was figuring out how to populate the part table. The Part class is abstract, which
     * means in cannot be instantiated, like the Product class can. At first I did not realize that I needed to use the
     * outsourced and In house classes to populate the table. To solve this problem I asked a course mentor for direction.
     * The mentor sent me a video that covered abstract classes and how they worked. After watching the video I realized
     * I needed to populate the part table through the outsourced and in house classes.
     * </p> <p>
     * If there were any feature I would like to add to this application in the future it would be the ability to alert the
     * user if the inventory of a product or part is below a certain amount. This feature would allow the company to know
     * what parts or products they need to get more of right away without having to manually look through each part or
     * product. It would be a feature that overalls improve functionality and efficiency within the inventory system.
     *  </b></p>
     */
    public void onSavePartB(ActionEvent actionEvent) throws NumberFormatException, NullPointerException {
        try {
            System.out.println("Saving.");

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
                    MainController.addPart(newInHousePart);
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainPane.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 900, 500);
                    stage.setTitle("Inventory Main");
                    stage.setScene(scene);
                    stage.show();
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
                        MainController.addPart(newOutSourcedPart);
                        Parent root = FXMLLoader.load(getClass().getResource("/view/MainPane.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 900, 500);
                        stage.setTitle("Inventory Main");
                        stage.setScene(scene);
                        stage.show();


                    }
        }
        catch (NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid value at: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**The cancel feature on the parts form returns to main screen after confirming with user.
     * @param actionEvent The action event that cancels adding a part.
     */
    public void onPartCancelB(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel without saving part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainPane.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 500);
            stage.setTitle("Inventory Main");
            stage.setScene(scene);
            stage.show();

            System.out.println("Cancelling");
        }
    }

    /**Sets the part source label to say Machine ID when the in house radio button is selected.*/
    public void onInHousePartB() {
        machineIDLabel.setText("Machine ID:");
    }

    /**Sets the part source label to say company name when the outsourced radio button is selected.*/
    public void onOutsourcedPartB() {
        machineIDLabel.setText("Company Name:");
    }

    /**Sets the partID to a randomly generated number that cannot be changed.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partID = generatePartID();
        partIDText.setText(Integer.toString(partID));

    }
}

