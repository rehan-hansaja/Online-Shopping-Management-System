import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
public class ShoppingGUITable extends AbstractTableModel {
    private String columns[] = {"Product ID", "Name", "Category", "Price", "No of available items", "Info"};
    protected ArrayList<Product> tableProductsList;

    public ShoppingGUITable(ArrayList<Product> list) {
        this.tableProductsList = list;
    }
    @Override
    public int getRowCount() {
        return tableProductsList.size();
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
        } else if (column == 3) {
            return double.class;
        } else if (column == 4) {
            return int.class;
        } else {
            return String.class;
        }
    }
    // Method to get the value at a specific cell in the table
    @Override
    public Object getValueAt(int row, int column) {
        Object detail = null;
        if (column == 0) {
            detail = tableProductsList.get(row).getProductID();
        } else if (column == 1) {
            detail = tableProductsList.get(row).getProductName();
        } else if (column == 2) {
            detail = tableProductsList.get(row).getClass().getSimpleName();
        } else if (column == 3) {
            detail = tableProductsList.get(row).getPrice();
        } else if (column == 4){
            detail = tableProductsList.get(row).getAvailableItemsNo();
        } else if (column == 5) {
            Product product = tableProductsList.get(row);
            if (product instanceof Electronics) {
                detail = ((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarrantyPeriod()+" Months Warranty";
            } else if (product instanceof Clothing) {
                detail = ((Clothing) product).getColour() + ", " + ((Clothing) product).getSize();
            }
        }
        if (detail == null) {
            detail = "-";
        }
        return detail;
    }
}
    // reference: https://stackoverflow.com/questions/9845800/abstracttablemodel-tutorial

