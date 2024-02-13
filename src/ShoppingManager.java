import java.awt.event.ActionEvent;
public interface ShoppingManager {
    void addNewProduct()  ;
    void deleteProduct();
    void printProducts() ;
    void saveProductsToFile() ;
    void identifyUser();
    ActionEvent UI (User identifiedUser);     // Method to handle UI actions with the identified user
}