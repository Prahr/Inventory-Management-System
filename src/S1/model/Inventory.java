package S1.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Holds the inventory data of all parts and products in the inventory management system.
 */

public class Inventory {
    /**
     * List of all parts in inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * List of all products in inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to inventory
     * @param newPart Part to add
     */

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Adds product to inventory
     * @param newProduct Product to add
     */

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Looks up part in inventory by part ID
     * @param partID Part ID to search
     * @return Returns matching part if found. Null if not.
     */

    public static Part lookupPart(int partID){
       Part found = null;
       for(Part p : allParts){
           if(p.getId() == partID){
               found = p;
           }
       }
       return found;
    }

    /**
     * Looks up product in inventory by product ID
     * @param productID Product ID to search
     * @return Returns matching product if found. Null if not.
     */

    public static Product lookupProduct(int productID){
        Product found = null;
        for(Product p : allProducts){
            if(p.getId() == productID){
                found = p;
            }
        }
        return found;
    }

    /**
     * Looks up part in inventory by partial or exact part name or ID
     * @param partName Part name to search for
     * @return Returns matching part if found. Null if not.
     */

    public static ObservableList lookupPart(String partName){
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        for(Part p : allParts){
            if(p.getName().contains(partName) || String.valueOf(p.getId()).contains(partName)){
                searchParts.add(p);
            }
        }
        return searchParts;
    }

    /**
     * Looks up product in inventory by partial or exact product name or ID
     * @param productName Product name to search for
     * @return Returns matching product if found. Null if not.
     */

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> searchProducts = FXCollections.observableArrayList();
        for(Product p : allProducts){
            if(p.getName().contains(productName) || String.valueOf(p.getId()).contains(productName)){
                searchProducts.add(p);
            }
        }
        return searchProducts;
    }

    /**
     * Updates part in inventory
     * @param index Position of part in inventory list
     * @param selectedPart Part to update
     */

    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * Updates product in inventory
     * @param index Position of product in inventory list
     * @param selectedProduct Product to update
     */

    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     * Deletes part from inventory if it exists
     * @param selectedPart Part to delete
     * @return Return true if part is successfully deleted. False if not
     */

    public static boolean deletePart(Part selectedPart){
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }

    /**
     * Deletes product from inventory if it exists
     * @param selectedProduct Product to delete
     * @return True if product is successfully deleted. False if not
     */

    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    /**
     * Returns a list of all parts in inventory
     * @return Returns a list of all parts in inventory
     */

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Returns a list of all products in inventory
     * @return Returns a list of all products in inventory
     */

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
