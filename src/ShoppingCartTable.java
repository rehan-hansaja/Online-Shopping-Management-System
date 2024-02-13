import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
public class ShoppingCartTable extends AbstractTableModel {
    private String columns[] = {"Product", "Quantity", "Price"};
    private ShoppingCart userCart;
    private int quantity;
    private ArrayList<ShoppingCartAdd> userProductsList;    // List of ShoppingCartAdd objects representing products in the cart

    public ShoppingCartTable(ShoppingCart userCart) {
        this.userCart = userCart;
        userProductsList = userCart.getShoppingCart();
    }
    @Override
    public int getRowCount() {
        return userProductsList.size();
    }
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    @Override
    public Class getColumnClass(int column) {
        if (column == 0) {
            return String.class;
        } else if (column == 1) {
            return int.class;
        } else {
            return double.class;
        }
    }
    // Method to get the value at a specific cell in the table
    @Override
    public Object getValueAt(int row, int col) {
        ShoppingCartAdd product = userProductsList.get(row);
        if (col == 0) {
            if (product.getProduct() instanceof Electronics) {
                return product.getProduct().getProductID() +
                        ", " + product.getProduct().getProductName();
            } else if (product.getProduct() instanceof Clothing) {
                return product.getProduct().getProductID() +
                        ", " + product.getProduct().getProductName();
            }
        } else if (col == 1) {
            if (userCart != null) {
                quantity = product.getCount();
                System.out.println("Quantity" + quantity);
                return quantity;
            }
        } else if (col == 2) {
            return product.getProduct().getPrice() * quantity;
        }
        return null;
    }
}
    // reference: https://stackoverflow.com/questions/4192973/getting-column-names-from-an-abstracttablemodel