package S1.controller;

import S1.model.InHouse;
import S1.model.Inventory;
import S1.model.Outsourced;
import S1.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the modify part page.
 */

public class ModifyPartController implements Initializable {
    public RadioButton inhouseRadio;
    public RadioButton outsourcedRadio;
    public Label idLabel;
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField machineIdField;
    public TextField minField;
    public Button saveButton;
    public Button cancelButton;
    public Part part = MainController.selectedPart();
    private int machineID;
    private String companyName;

    /**
     * Initializes the modify part page. Checks to see if the selected part to modify is in-house or outsourced and
     * changes the page to display appropriately. Sets the values of the text boxes to the values assigned to the part
     * when it was created.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(part instanceof InHouse){
            idLabel.setText("Machine ID");
            machineIdField.setText(String.valueOf(((InHouse) part).getMachineId()));
            idField.setText(String.valueOf(part.getId()));
            nameField.setText(part.getName());
            invField.setText(String.valueOf(part.getStock()));
            priceField.setText(String.valueOf(part.getPrice()));
            maxField.setText(String.valueOf(part.getMax()));
            minField.setText(String.valueOf(part.getMin()));
            inhouseRadio.setSelected(true);
        }
        if(part instanceof  Outsourced){
            idLabel.setText("Company Name");
            machineIdField.setText(((Outsourced) part).getCompanyName());
            idField.setText(String.valueOf(part.getId()));
            nameField.setText(part.getName());
            invField.setText(String.valueOf(part.getStock()));
            priceField.setText(String.valueOf(part.getPrice()));
            maxField.setText(String.valueOf(part.getMax()));
            minField.setText(String.valueOf(part.getMin()));
            outsourcedRadio.setSelected(true);
        }
    }

    /**
     * Method that sets the text of a label to Machine ID when called. Needed when part in made in house.
     * @param actionEvent Inhouse radio button clicked
     */

    public void inhouseSelected(ActionEvent actionEvent) {
        idLabel.setText("Machine ID");
    }

    /**
     * Method that sets the text of a label to Company Name when called. Needed when part is outsourced.
     * @param actionEvent Outsourced radio button clicked
     */

    public void outsourcedSelected(ActionEvent actionEvent) {
        idLabel.setText("Company Name");
    }

    /**
     * Method is called when the save button is pressed.
     * Saves the data user inputs into the modify part page. Creates a new part object and saves it to inventory while keeping the same unique ID.
     * Deletes the old part from inventory. Checks for valid input into text fields and returns user to the main screen when save is successful.
     * @param actionEvent Save button clicked
     * @throws IOException Catches invalid data entry errors
     */

    public void saveClicked(ActionEvent actionEvent) throws IOException{
        try {
            String name = nameField.getText();
            Double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            int id = part.getId();
            boolean checkAdd = false;

            if(checkMinMax(min, max) == true && checkStock(stock, min, max) == true) {
                if (inhouseRadio.isSelected()) {
                    try {
                        machineID = Integer.parseInt(machineIdField.getText());
                        InHouse newPart = new InHouse(id, name, price, stock, min, max, machineID);
                        Inventory.addPart(newPart);
                        checkAdd = true;
                    } catch (Exception e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText("Invalid input, check Machine ID is only integers");
                        error.showAndWait();
                    }
                }

                if (outsourcedRadio.isSelected()) {
                    companyName = machineIdField.getText();
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newPart);
                    checkAdd = true;
                }

                }
                if(checkAdd == true){
                    Inventory.deletePart(part);
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/IMS.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    System.out.println(machineID);
                }
        }catch(Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Invalid input");
            error.showAndWait();
        }
    }

    /**
     * Method that checks to make sure the stock level is between the min and max values provided.
     * @param stock Stock level
     * @param min Minimum stock level
     * @param max Maximum stock level
     * @return True if stock level is valid. False if not.
     */

    private boolean checkStock(int stock, int min, int max){
        boolean check = true;

        if(stock < min || stock > max){
            check = false;
        }
        return check;
    }

    /**
     * Method that checks that min is less than max and also not a negative number.
     * @param min Minimum stock level
     * @param max Maximum stock level
     * @return True if minimum and maximum stock levels are valid. False if not.
     */

    private boolean checkMinMax(int min, int max){
        boolean check = true;
        if(min >= max || min <= 0){
            check = false;
        }
        return check;
    }

    /**
     * Returns the user to the main screen when the cancel button is clicked.
     * @param actionEvent Cancel button clicked
     * @throws IOException Catches error if loaded resource is null
     */

    public void cancelClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/IMS.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
