package S1.controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import S1.model.Inventory;
import S1.model.Part;
import S1.model.Product;
import javafx.stage.Stage;

/**
 * Controller for the main page of the inventory management system
 */

public class MainController implements Initializable {

    private static Part modPart;
    private static Product modProduct;
    public Button partAdd;
    public Button partModify;
    public Button partDelete;
    public TableView<Part> partsTable;
    public TableColumn<Object, Object> partID;
    public TableColumn partName;
    public TableColumn partInventoryLevel;
    public TableColumn partPPU;
    public TextField partSearch;
    public Button productAdd;
    public Button productModify;
    public Button productDelete;
    public TableView<Product> productsTable;
    public TableColumn productID;
    public TableColumn productName;
    public TableColumn productInventoryLevel;
    public TableColumn productPPU;
    public TextField productSearch;
    public Button exit;
    private ObservableList results = FXCollections.observableArrayList();
    private ObservableList<Product> resultsProduct = FXCollections.observableArrayList();

    /**
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPPU.setCellValueFactory(new PropertyValueFactory<>("price"));

        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPPU.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Method returns the selected part in the part table.
     * @return Returns the selected part in the part table.
     */

    public static Part selectedPart(){
        return modPart;
    }

    /**
     * Method returns the selected product in the product table.
     * @return Returns the selected product in the product table.
     */

    public static Product selectedProduct(){
        return modProduct;
    }

    /**
     * Loads the add part page
     * @param actionEvent Add part button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void addPart(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method loads the modify part page with the information of the selected part. Also confirms a part was selected.
     * @param actionEvent Modify part button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void modifyPart (ActionEvent actionEvent) throws IOException {
        modPart = partsTable.getSelectionModel().getSelectedItem();
        if(modPart != null) {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyPart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("A part must be selected");
            error.showAndWait();
        }
    }

    /**
     * Method deletes the selected part. Checks to make sure a part was selected and also asks the user to confirm that they want to delete the part.
     * @param actionEvent Delete part button clicked
     */

    public void deletePart(ActionEvent actionEvent) {
        Part deletePart = partsTable.getSelectionModel().getSelectedItem();
        if(deletePart == null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("A part must be selected");
            error.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm");
        confirm.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(deletePart);
        }
    }

    /**
     * Exits the program
     * @param actionEvent Exit button clicked
     */

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Loads the add product page
     * @param actionEvent Add product button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void addProduct(ActionEvent actionEvent) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that loads the modify product page. Checks to make sure a product is selected.
     *
     * RUNTIME ERROR: Before I checked to see if a product was selected,
     * when clicked the modify button without a product selected would cause the application
     * to throw a null pointer exception. The null pointer was from the modify part controller trying to initialize
     * the assigned product ID, but there being no assigned ID with no product selected. The application would then crash.
     * This error was discovered with the modify product page, but would have also occurred when trying to modify a part without
     * selecting one. This runtime error was also corrected in the modifyPart method.
     * The error was easily fixed by making sure that modProduct was not a null value before loading the modify product page.
     *
     * @param actionEvent Modify product button clicked
     * @throws IOException Catches error if resource loaded is null
     */

    public void modifyProduct(ActionEvent actionEvent) throws IOException{
        modProduct = productsTable.getSelectionModel().getSelectedItem();
        if(modProduct != null) {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("A product must be selected");
            error.showAndWait();
        }
    }

    /**
     * Deletes the selected product. First makes sure that a product is selected, if so it checks to see if any parts are
     * associated with the selected part. Outputs an error if so. Finally asks the user to confirm that they really want
     * to delete the selected product.
     * @param actionEvent Delete product button clicked
     */

    public void deleteProduct(ActionEvent actionEvent) {
        Product deleteProduct = productsTable.getSelectionModel().getSelectedItem();
        if(deleteProduct == null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("A product must be selected");
            error.showAndWait();
            return;
        }
        if(deleteProduct.getAllAssociatedParts().size() > 0){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("You cannot delete a product with associated parts");
            error.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm");
        confirm.setContentText("Are you sure you want to delete this product?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(deleteProduct);
        }
    }

    /**
     * When a character is typed into the search text field, this method is called and takes the search string and checks
     * the inventory for all parts whose names or ID contain the search string. Creates a dialog box informing the user
     * if no search results are found.
     * @param keyEvent Character being typed into search text field
     */

    public void searchPartPressed(KeyEvent keyEvent) {
        String search = partSearch.getText();

        results = Inventory.lookupPart(search);
        partsTable.setItems(results);
        if(results.size() < 1){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No results found");
            error.showAndWait();
        }
    }

    /**
     * When a character is typed into the search text field, this method is called and takes the search string and checks
     * the inventory for all products whose names or ID contain the search string. Creates a dialog box informing the user
     * if no search results are found.
     * @param keyEvent Character being typed into search text field
     */

    public void searchProductPressed(KeyEvent keyEvent) {
        String search = productSearch.getText();

        resultsProduct = Inventory.lookupProduct(search);
        productsTable.setItems(resultsProduct);
        if(resultsProduct.size() < 1){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No results found");
            error.showAndWait();
        }
    }
}
