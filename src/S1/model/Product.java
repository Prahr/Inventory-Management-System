package S1.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model of a product object
 */

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor of a product object
     * @param id Product ID
     * @param name Product name
     * @param price Product price
     * @param stock Product stock level
     * @param min Product minimum stock level
     * @param max Product maximum stock level
     */

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a part to the list of associated parts of the product
     * @param newPart Part to be added
     */

    public void addAssociatedPart(Part newPart) {
        associatedParts.add(newPart);
    }

    /**
     * Makes the selected part no longer associated with the product if the part is associated.
     * @param removePart Selected part to be removed
     * @return True if part is removed successfully. False if not.
     */

    public boolean deleteAssociatedPart(Part removePart){
        if(associatedParts.contains(removePart)){
            associatedParts.remove(removePart);
            return true;
        }
        return false;
    }

    /**
     * Returns a list of all parts associated with the product
     * @return Returns a list of all parts associated with the product
     */

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}