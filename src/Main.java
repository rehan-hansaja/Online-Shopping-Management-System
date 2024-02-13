import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager shopManager = new WestminsterShoppingManager();
        shopManager.loadProductsFile();

        boolean process = true;
        while(process){
            try {
                shopManager.menuList();
                System.out.println("Enter your choice (1-5): ");
                Scanner optionInput = new Scanner(System.in);
                int menuNumber = optionInput.nextInt();
                switch (menuNumber) {
                    case 1:
                        shopManager.addNewProduct();
                        break;
                    case 2:
                        shopManager.deleteProduct();
                        shopManager.saveProductsToFile();
                        break;
                    case 3:
                        shopManager.printProducts();
                        break;
                    case 4:
                        shopManager.saveProductsToFile();
                        break;
                    case 5:
                        shopManager.identifyUser();
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                }
            }catch (Exception e) {
                System.out.println("Invalid input.Enter a valid option!");
            }
        }
    }
}
