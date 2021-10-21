package S1.controller;

import S1.model.Inventory;
import S1.model.Part;
import S1.model.Product;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the modify product page
 */

public class ModifyProductController implements Initializable {
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TextField searchField;
    public Button addButton;
    public Button removeAssociatedPart;
    public Button saveButton;
    public Button cancelButton;
    public TableColumn partID;
    public TableColumn partName;
    public TableColumn partInventoryLevel;
    public TableColumn partPPU;
    public TableColumn associatedPartID;
    public TableColumn associatedPartName;
    public TableColumn associatedPartInventoryLevel;
    public TableColumn associatedPartPPU;
    public TableView allPartsTable;
    public TableView associatedPartsTable;
    private Product modProduct = MainController.selectedProduct();;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> results = FXCollections.observableArrayList();

    /**
     * Initializes the modify product page. Populates and formats the table that displays all parts currently in
     * inventory as well as the table that displays all parts associated with the selected product.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setText(String.valueOf(modProduct.getId()));
        nameField.setText(modProduct.getName());
        invField.setText(String.valueOf(modProduct.getStock()));
        priceField.setText(String.valueOf(modProduct.getPrice()));
        maxField.setText(String.valueOf(modProduct.getMax()));
        minField.setText(String.valueOf(modProduct.getMin()));

        allPartsTable.setItems(Inventory.getAllParts());
        associatedPartsTable.setItems(modProduct.getAllAssociatedParts());

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPPU.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPPU.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * When a character is typed into the search text field, this method is called and takes the search string and checks
     * the inventory for all parts whose names or ID contain the search string. Creates a dialog box informing the user
     * if no search results are found.
     * @param keyEvent Character entered into search field
     */

    public void searchEntered(KeyEvent keyEvent) {
        String search = searchField.getText();

        results = Inventory.lookupPart(search);
        allPartsTable.setItems(results);
        if(results.size() < 1){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No results found");
            error.showAndWait();
        }
    }

    /**
     * Method that is called when the add button is clicked. Takes the selected part from the first table and adds it to
     * the associated parts list for the product and to the table displaying associated parts.
     * @param actionEvent Add button clicked
     */

    public void addClicked(ActionEvent actionEvent) {
        Part addPart = (Part) allPartsTable.getSelectionModel().getSelectedItem();
        associatedParts.add(addPart);
        associatedPartsTable.setItems(associatedParts);
    }

    /**
     * This method is called when the remove associated part button is pressed. User is asked to confirm that they want
     * to delete the part. If the user confirms, the part is removed from the associated part list of the product and
     * also from the table of associated parts.
     * @param actionEvent Remove associated parts button clicked
     */

    public void removePartClicked(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm");
        confirm.setContentText("Are you sure you want to remove this part?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part removePart = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
            modProduct.deleteAssociatedPart(removePart);
        }
    }

    /**
     * Method is called when the save button is pressed.
     * Saves the data user inputs into the modify product page. Creates a new product object and saves it to inventory while
     * keeping the same unique ID. Adds all selected associated parts to be associated with the product in inventory.
     * Deletes the old product from inventory.
     * Checks for valid input into text fields and returns user to the main screen when save is successful.
     * @param actionEvent Save button clicked
     */

    public void saveClicked(ActionEvent actionEvent) {
        try {
            String name = nameField.getText();
            Double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            int id = modProduct.getId();
            boolean checkAdd = false;

            if(checkMinMax(min, max) == true && checkStock(stock, min, max) == true){
                Product newProduct = new Product(id, name, price, stock, min, max);
                Inventory.addProduct(newProduct);
                for(Part p : associatedParts){
                    newProduct.addAssociatedPart(p);
                }
                checkAdd = true;
            }
            if(checkAdd == true){
                Inventory.deleteProduct(modProduct);
                Parent parent = FXMLLoader.load(getClass().getResource("../view/IMS.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
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
     * @throws IOException Catches error if resource loaded is null
     */

    public void cancelClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/IMS.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
