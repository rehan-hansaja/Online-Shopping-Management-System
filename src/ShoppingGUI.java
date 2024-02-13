import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
public class ShoppingGUI extends WestminsterShoppingManager {
    private JTable table1;
    private JTable table2;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JScrollPane scrollPane1;
    private JLabel productLabel;
    private JComboBox productCategory;
    private ShoppingGUITable guiTable;
    private FrameShoppingCart frameShoppingCart;
    private User user;

    public  ShoppingGUI(User user) {   // Constructor for the main shopping GUI
        this.user = user;
        FrameGUI frame1 = new FrameGUI();

        JLabel text = new JLabel("Select Product Category");
        text.setBounds(500, 25, 200, 50);
        panel1.add(text);

        String[] categoryList = {"All", "Electronics", "Clothing"};
        productCategory = new JComboBox<>(categoryList);
        productCategory.setSelectedIndex(0);
        productCategory.setBounds(700, 25, 200, 50);
        panel1.add(productCategory);

        productCategory.addActionListener(productActionListener);
        productCategory.addItemListener(productItemListener);
        panel2.setBackground(Color.WHITE);

        // Button for adding selected product to the shopping cart
        JButton button = new JButton("Add to Shopping Cart");
        button.setSize(30, 60);
        button.setBounds(800, 100, 200, 100);
        button.addActionListener(cartActionListener);
        panel2.add(button);

        frame1.add(panel1, BorderLayout.CENTER);
        frame1.add(panel2, BorderLayout.SOUTH);
        frame1.setVisible(true);
        updateGUITable(productCategory.getSelectedItem().toString());
    }

    class FrameGUI extends JFrame {     // FrameGUI class representing the main frame of the shopping GUI
        public FrameGUI() {
            setTitle("Westminster Shopping center");
            setLayout(new BorderLayout());
            setSize(1500, 1200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JButton shoppingCartButton = new JButton("Shopping Cart");
            shoppingCartButton.setBounds(1250, 20, 120, 45);
            panel1 = new JPanel();
            panel1.setLayout(null);
            panel2 = new JPanel();

            // Action listener for the shopping cart button
            shoppingCartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameShoppingCart = new FrameShoppingCart();
                    panel3 = new JPanel();
                    panel4 = new JPanel(new GridLayout(8, 8));
                    panel4.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
                    frameShoppingCart.setLocationRelativeTo(null);
                    frameShoppingCart.add(panel3, BorderLayout.CENTER);
                    frameShoppingCart.add(panel4, BorderLayout.SOUTH);
                    if(userShoppingCart != null){
                        if (table2 != null) {
                            panel3.remove(table2);
                        }
                        updateCartTable();
                        finalTotal(user);
                        frameShoppingCart.setVisible(true);}
                }
            });
            panel1.add(shoppingCartButton);
        }
    }
    class FrameShoppingCart extends JFrame {   // FrameShoppingCart class representing the shopping cart frame
        public FrameShoppingCart() {
            setSize(1000, 750);
            setLayout(new BorderLayout());
            setTitle("Shopping cart");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    private void updateGUITable(String selectCategory) {    // Method to update the GUI table
        productsList = loadProductsFile();
        ArrayList<Product> selectedList = new ArrayList<>();
        if (selectCategory.equals("All")) {
            selectedList.addAll(productsList);
            System.out.println(selectedList.size());
            System.out.println("all selected");
        } else {
            for (Product product : productsList) {
                if (product.getClass().getSimpleName().equalsIgnoreCase(selectCategory)) {
                    selectedList.add(product);
                    System.out.println(selectedList.size());
                    System.out.println("product added");
                }
            }
        }
        if (scrollPane1 != null) {
            panel1.remove(scrollPane1);
        }
        // Create a new ShoppingGUITable instance with the selected list of products
        guiTable = new ShoppingGUITable(selectedList);
        table1 = new JTable(guiTable);
        scrollPane1 = new JScrollPane(table1);
        scrollPane1.setBounds(250, 130, 850, 200);
        scrollPane1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        table1.setGridColor(Color.BLACK);
        int i;
        int j;
        for (i = 0; i < 6; i++) {
            for (j = 0; j < selectedList.size(); j++) {
                guiTable.getValueAt(j, i);
            }
        }
        table1.setDefaultRenderer(Object.class, new TableChangeColour());   // Set a custom renderer to change cell colors
        table1.getSelectionModel().addListSelectionListener(rowListener);
        table1.getTableHeader().setBackground(Color.LIGHT_GRAY);
        scrollPane1.setVisible(true);
        panel1.add(scrollPane1);
        panel1.revalidate();
        panel1.repaint();
    }

    class TableChangeColour extends DefaultTableCellRenderer {
        private final Color redColor = new Color(255, 220, 220);  // Adjust the RGB values for the shade of Red
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            ShoppingGUITable gui = (ShoppingGUITable) table.getModel();
            Product product = gui.tableProductsList.get(row);

            // Set the background color for the entire row
            if (product.getAvailableItemsNo() < 3) {
                setBackground(redColor);
            } else {
                setBackground(Color.WHITE);
            }
            return this;
        }   //reference: https://stackoverflow.com/questions/46390425/add-behavior-to-a-default-jtable-cell-renderer
    }

    ListSelectionListener rowListener = new ListSelectionListener() {   // Handle row selection in the JTable
        public void valueChanged(ListSelectionEvent event) {
            if (table1 != null) {
                if (!event.getValueIsAdjusting()) {
                    int viewRow = table1.getSelectedRow();
                    if (viewRow != -1) {
                        int modelRow = table1.convertRowIndexToModel(viewRow);
                        Product product = guiTable.tableProductsList.get(modelRow);
                        updateProductLabel(product);
                        System.out.println("selected");
                    } else {
                        clearProductLabel();
                    }
                } else System.out.println("table doesn't exist");
            }
        }

        private void updateProductLabel(Product product) {     // Update Product Label
            if (productLabel != null) {
                panel2.remove(productLabel);
            }
            if (product instanceof Electronics) {    // Create a new JLabel with HTML-formatted text based on the product type
                productLabel = new JLabel("<html>------------------------<br/><br/>Selected Product - Details "
                        + "<br/><br/> Product ID : " + product.getProductID()
                        + "<br/><br/>Product name: " + product.getProductName()
                        + " <br/><br/>Product Category: " + product.getClass().getSimpleName()
                        + " <br/><br/>Brand name: " + ((Electronics) product).getBrand()
                        + " <br/><br/>Warranty period (Months): " + ((Electronics) product).getWarrantyPeriod()
                        + " <br/><br/>No of available items: " + product.getAvailableItemsNo()
                        + "<br/><br/> -------------------------</html>", SwingConstants.CENTER);
                System.out.println("electronics row");

            } else if (product instanceof Clothing) {
                productLabel = new JLabel("<html>------------------------<br/><br/>Selected Product - Details "
                        + "<br/><br/> Product ID : " + product.getProductID()
                        + "<br/><br/>Product name: " + product.getProductName()
                        + " <br/><br/>Product Category: " + product.getClass().getSimpleName()
                        + " <br/><br/>Size: " + ((Clothing) product).getSize()
                        + " <br/><br/>Colour : " + ((Clothing) product).getColour()
                        + " <br/><br/>No of available items: " + product.getAvailableItemsNo()
                        + "<br/><br/> -------------------------</html>", SwingConstants.CENTER);
                System.out.println("clothing row");
            }
            assert productLabel != null;
            productLabel.setBounds(400, 100, 400, 200);
            productLabel.setVisible(true);
            panel2.add(productLabel);
            panel2.revalidate();
        }
        private void clearProductLabel() {    // Clear the product label
            if (productLabel != null) {
                panel2.remove(productLabel);
                productLabel = null;
                panel2.revalidate();
            }
        }
    };

    // ActionListener for the "Add to Shopping Cart" button
    ActionListener cartActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table1 != null) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    int row = table1.convertRowIndexToModel(selectedRow);
                    Product selectedProduct = guiTable.tableProductsList.get(row);
                    userShoppingCart = user.getShoppingCart();
                    userShoppingCart.addToCart(selectedProduct);
                    System.out.println("button clicked" + userShoppingCart.getShoppingCart().size());
                    saveProductsToFile();
                    updateGUITable(productCategory.getSelectedItem().toString());
                }
            } else
                System.out.println("Table Not Found");
        }
    };

    ActionListener productActionListener = new ActionListener() {  // ActionListener for the product category selection
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedType = (String) productCategory.getSelectedItem();
            updateGUITable(selectedType);
            System.out.println("ActionEvent");
        }
    };
    ItemListener productItemListener = new ItemListener() {   // ItemListener for the product category selection
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedType = (String) productCategory.getSelectedItem();
                updateGUITable(selectedType);
            }
        }
    };
    private void updateCartTable() {        // Update cart table
        ShoppingCartTable userTable = new ShoppingCartTable(userShoppingCart);
        table2 = new JTable(userTable);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setBounds(150, 50, 1000, 300);
        scrollPane2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        table2.setGridColor(Color.BLACK);
        System.out.println("Shopping Cart Size"+ userShoppingCart.getShoppingCart().size());
        int i;
        int j;
        for (i = 0; i < 3; i++) {
            for (j = 0; j <userShoppingCart.getShoppingCart().size() ; j++) {
                userTable.getValueAt(j, i);
            }
        }
        table2.getTableHeader().setBackground(Color.LIGHT_GRAY);
        scrollPane2.setVisible(true);
        panel3.add(scrollPane2,BorderLayout.CENTER);
        panel3.revalidate();
        panel3.repaint();
    }

    private void finalTotal(User user) {    // Calculate the Final Total
        double discount10 = 0;
        double discount20 = 0;
        JLabel label1 = new JLabel("          Total : "+ userShoppingCart.totalPrice());
        panel4.add(label1);
        if (user.getNewUser()) {
            discount10 = userShoppingCart.totalPrice() * 0.1;
            JLabel label2 = new JLabel("          First Purchase Discount (10%) : -" + discount10);
            panel4.add(label2);
        }
        for (ShoppingCartAdd product : user.getShoppingCart().getShoppingCart()) {
            int productCount = product.getCount();
            if (productCount >= 3) {
                discount20 = userShoppingCart.totalPrice() * 0.2;
                JLabel label3 = new JLabel("          Three Items in Same Category Discount (20%) : -" + discount20);
                panel4.add(label3);
                break;
            }
        }
        JLabel label4 = new JLabel("          Final Total : " + (userShoppingCart.totalPrice() - discount10 -discount20));
        label4.setBounds(250,500,100,50);
        panel4.add(label4);
    }
}
