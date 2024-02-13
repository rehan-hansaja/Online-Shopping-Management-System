import java.util.*;
import java.io.*;
import java.util.Collections;
import java.io.Serializable;
import java.awt.event.ActionEvent;
public  class WestminsterShoppingManager implements ShoppingManager, Serializable {
    static Scanner input = new Scanner(System.in);
    static int productsCount;
    ArrayList<Product> productsList = new ArrayList<>();
    ArrayList<User> usersList = new ArrayList<>();
    ShoppingCart userShoppingCart;

    public void menuList() {
        System.out.println("\n--- Welcome to Westminster Shopping Manager ---");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print the list of Products");
        System.out.println("4. Save Products");
        System.out.println("5. View User Interface");
        System.out.println("0. Exit");
        System.out.println("------------------------------------------------");
    }
    @Override
    public void addNewProduct() {
        String productID = createID(productsList,"item");
        productsCount = productsList.size();
        if (productsCount < 50) {       // check the maximum amount of 50
            System.out.print("Enter the product name: ");
            String productName = input.next();
            int availableItemsNo = integerInputValidation(input, "Enter the no of available items: ");
            boolean inputValidation1 = false;
            double price = 0.0;
            while (!inputValidation1) {
                System.out.print("Enter the price:  ");
                if (input.hasNextDouble()) {
                    price = input.nextDouble();
                    inputValidation1 = true;
                } else {
                    System.out.println("Error. Please enter a valid value");
                    input.next();
                }
            }
            boolean inputValidation2 = false;
            System.out.println("Choose the type of product to add:");
            System.out.println("1. Electronics");
            System.out.println("2. Clothing");
            Product product = null;
            while (!inputValidation2) {
                int option = integerInputValidation(input, "Enter the product type: ");
                if (option == 1) {
                    System.out.print("Enter the brand: ");
                    String brand = input.next();
                    int warrantyPeriod = integerInputValidation(input, "Enter the warrant period(In Months): ");
                    product = new Electronics(productID, productName, availableItemsNo, price, brand, warrantyPeriod);
                    inputValidation2 = true;
                } else if (option == 2) {
                    boolean inputValidation3 = false;
                    int i;
                    String size = " ";
                    String[] sizeList = {"XS","S", "M", "L", "XL", "XXL"};
                    while (!inputValidation3) {
                        System.out.print("\nEnter the dress size(XS,S,M,L,XL,XXL): ");
                        size = input.next().toUpperCase();
                        for (i = 0; i < 6; i++) {
                            if (sizeList[i].equals(size)) {
                                inputValidation3 = true;
                                break;
                            }
                        }
                        if (!inputValidation3) {
                            System.out.print("Error: Please enter a  valid value.");
                        }
                    }
                    System.out.print("Enter the colour:  ");
                    String colour = input.next();
                    product = new Clothing(productID, productName, availableItemsNo, price, size, colour);
                    inputValidation2 = true;
                } else {
                    System.out.println("Error. Please enter a valid value");
                }
            }
            productsList.add(product);
            productsCount++;
            System.out.println("Product is added successfully.");
        } else {
            System.out.println("The limit has been reached. Only 50 products can be added");
        }
    }

    public static int integerInputValidation(Scanner input, String message) {    // Validation for Integer Inputs
        int userInput = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print(message);
                userInput = input.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error. Please Enter a valid integer.");
                input.next();
            }
        }
        return userInput;
    }

    public static String createID(List<Product> currentIDs, String prefix) { // Create unique product IDs on alphabetical order
        int maximumID = 0;
        for (Product currentID : currentIDs) {
            if (currentID.getProductID().startsWith(prefix)) {
                try {
                    int id = Integer.parseInt(currentID.getProductID().substring(prefix.length()));
                    if (id > maximumID) {
                        maximumID = id;
                    }
                } catch (NumberFormatException ignored) {
                    System.out.println(ignored);
                }
            }
        }
        maximumID++;
        return String.format("%s%03d", prefix, maximumID);
    }

    @Override
    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the product ID to delete: ");
        String productIDToDelete = scanner.next();
        boolean productFound = false;
        Iterator<Product> iterator = productsList.iterator();   // Iterate through the productList to find & delete product
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductID().equals(productIDToDelete)) {   // Display information about the deleted product
                System.out.println("Deleted Product Information:");
                System.out.println("---------------------------------");
                System.out.println("Product ID: " + product.getProductID());
                System.out.println("Product Name: " + product.getProductName());
                System.out.println("Product Type: " + (product instanceof Electronics ? "Electronics" : "Clothing"));
                System.out.println("---------------------------------");
                iterator.remove();    // Remove the product from the list
                productFound = true;
                break;
            }
        }
        if (productFound) {
            System.out.println("Product deleted successfully.");
            System.out.println("Total number of products left in the system: " + productsList.size());
        } else {
            System.out.println("Product with ID " + productIDToDelete + " not found.");
        }
    }
      @Override
    public void printProducts() {
        List<Product> sortProducts = new ArrayList<>(productsList);
        Collections.sort(sortProducts, Comparator.comparing(Product::getProductID));

        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-20s | %-6s | %-15s | %-15s | %-8s |\n",
                "ProductID", "Product Name", "Available Items No", "Price", "Type","Brand / Size","Warranty / Colour");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        for (Product pro : sortProducts) {    // Iterate through the sorted productsList & print product details
            if (pro instanceof Electronics electronic) {
                System.out.printf("| %-10s | %-20s | %-20d | %.2f | %-15s | %-15s | %-17s |\n",
                        electronic.getProductID(), electronic.getProductName(), electronic.getAvailableItemsNo(),
                        electronic.getPrice(), "Electronic",electronic.getBrand(),electronic.getWarrantyPeriod());
            } else if (pro instanceof Clothing clothing) {
                System.out.printf("| %-10s | %-20s | %-20d | %.2f | %-15s | %-15s | %-17s |\n",
                        clothing.getProductID(), clothing.getProductName(), clothing.getAvailableItemsNo(),
                        clothing.getPrice(), "Clothing",clothing.getSize(),clothing.getColour());
            }
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
    }  // reference: https://stackoverflow.com/questions/33466526/java-printf-formatting-to-print-items-in-a-table-or-columns

    @Override
    public void saveProductsToFile() {
        try {
            FileOutputStream newFile = new FileOutputStream("Products.ser");
            ObjectOutputStream file = new ObjectOutputStream(newFile);
            file.writeObject(productsList); // change arraylist name
            newFile.close();
            System.out.println("Products saved successfully");
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> loadProductsFile() {
        File newFile = new File("Products.ser");
        if (newFile.exists()) {
            try {
                FileInputStream fileInput = new FileInputStream("Products.ser");
                ObjectInputStream FileObj = new ObjectInputStream(fileInput);
                productsList = (ArrayList<Product>) FileObj.readObject();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else
            System.out.println("File not exist");
        return productsList;
    }
    @Override
    public void identifyUser() {   // Identify the user as a new or existing customer
        boolean inputValidation4 = false;
        while (!inputValidation4) {
            System.out.println("Are you a new customer (Y/N): ");
            String value = input.next().toUpperCase();
            if (value.equals("Y")) {
                System.out.println("Create a Username & Password.");
                System.out.println("Create a username:  ");
                String username = input.next();
                System.out.println("Create a password: ");
                String password = input.next();
                User user = new User(username, password, true);  // Create a new User object for new user
                usersList.add(user);
                System.out.println(usersList.get(0));
                userShoppingCart = new ShoppingCart();
                user.setShoppingCart(userShoppingCart);
                saveUsersToFile();
                UI(user);
                inputValidation4 = true;
                break;

            } else if (value.equals("N")) {
                usersList = loadUserFile();
                System.out.println(usersList.get(0));
                System.out.println("Enter your username: ");
                String username = input.next();
                System.out.println("Enter your password: ");
                String password = input.next();
                boolean userInput = false;
                for (User user : usersList) {   // Check if the entered username and password match an existing user
                    if (user.getUserName().equals(username)) {
                        if ((user.getPassword().equals(password))) {
                            user.setNewUser(false);
                            userShoppingCart = new ShoppingCart();
                            user.setShoppingCart(userShoppingCart);
                            saveUsersToFile();
                            System.out.println(user.getNewUser());
                            userInput = true;
                            UI(user);
                            inputValidation4 = true;
                            break;
                        }
                    }
                }
                if (!userInput) {
                    System.out.println("Wrong Username or Password");
                    inputValidation4 = true;
                    break;
                }
            } else {
                System.out.println("Enter the correct value");
            }
        }
    }

    public ActionEvent UI(User identifiedUser) {   // Launch user interface with the identified user
        new ShoppingGUI(identifiedUser);
        return null;
    }

    public void saveUsersToFile() {
        try {
            FileOutputStream newFile = new FileOutputStream("Users.ser");
            ObjectOutputStream user = new ObjectOutputStream(newFile);
            user.writeObject(usersList);
            System.out.println("User details are saved successfully");
            user.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<User> loadUserFile() {
        File userFile = new File("Users.ser");
        if (userFile.exists()) {
            try {
                FileInputStream userInput = new FileInputStream("Users.ser");
                ObjectInputStream userObj = new ObjectInputStream(userInput);
                usersList = (ArrayList<User>) userObj.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not exist");
        }
        return usersList;
    }
}

