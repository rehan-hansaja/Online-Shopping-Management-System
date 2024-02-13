import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import static org.junit.Assert.*;

public class WestminsterShoppingManagerTest{
    public void setInputForTesting(String... values) {
        String inputString = String.join(System.lineSeparator(), values);
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(inputStream);
    }
    @Test
    public void testAddNewProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        setInputForTesting("TestProduct", "10", "20.0", "1", "TestBrand", "12");
        shoppingManager.addNewProduct();
        assertTrue(shoppingManager.productsList.get(0) instanceof Electronics);
        assertEquals(1, shoppingManager.productsList.size());
    }

    @Test
    public void testDeleteProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Product productToDelete = new Electronics("item001", "TestProduct", 5, 25.0, "TestBrand", 12);
        shoppingManager.productsList.add(productToDelete);
        setInputForTesting("item001");
        shoppingManager.deleteProduct();
        assertEquals(0, shoppingManager.productsList.size());
    }

    @Test
    public void testSaveProductsToFile() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Product product1 = new Electronics("item001", "TestProduct1", 5, 25.0, "TestBrand", 12);
        Product product2 = new Clothing("item002", "TestProduct2", 8, 30.0, "M", "Blue");
        shoppingManager.productsList.add(product1);
        shoppingManager.productsList.add(product2);
        shoppingManager.saveProductsToFile();
        assertEquals(2, shoppingManager.productsList.size());
    }

    @Test
    public void testPrintProducts() {   //error showing due to the whitespace characters of the table format
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Product product1 = new Electronics("item001", "TestProduct1", 5, 125.0, "TestBrand", 12);
        Product product2 = new Clothing("item002", "TestProduct2", 8, 130.0, "M", "Blue");
        shoppingManager.productsList.add(product1);
        shoppingManager.productsList.add(product2);

        Collections.sort(shoppingManager.productsList, (p1, p2) -> p1.getProductID().compareTo(p2.getProductID()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        shoppingManager.printProducts();
        String expectedOutput = "---------------------------------------------------------------------------------------------------------------------------------\n";
        expectedOutput += "| ProductID  | Product Name         | Available Items No  | Price  | Type            | Brand / Size    | Warranty / Colour |\n";
        expectedOutput += "---------------------------------------------------------------------------------------------------------------------------------\n";
        expectedOutput += "| item001    | TestProduct1         | 5                   | 125.00 | Electronic      | TestBrand       | 12                |\n";
        expectedOutput += "| item002    | TestProduct2         | 8                   | 130.00 | Clothing        | M               | Blue              |\n";
        expectedOutput += "---------------------------------------------------------------------------------------------------------------------------------\n";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());

    }
}