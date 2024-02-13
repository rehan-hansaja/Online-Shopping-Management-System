import java.util.ArrayList;
public class ShoppingCart  extends WestminsterShoppingManager{
    private ArrayList<ShoppingCartAdd> shoppingCart;
    public ShoppingCart() {
        this.shoppingCart = new ArrayList<>();
    }
    public ArrayList<ShoppingCartAdd> getShoppingCart() {
        return shoppingCart;
    }

    public void addToCart(Product product) {
        if (product.getAvailableItemsNo() <= 0) {
            System.out.println("The product is out of stock.");
            return;
        }
        // Check if the product is already in the cart
        ShoppingCartAdd existingProduct = findItemInCart(product);
        if (existingProduct != null) {
            existingProduct.addCount();
        } else {
            // If not found, add a new item to the cart
            shoppingCart.add(new ShoppingCartAdd(product));
        }
        // Decrease availability
        product.availableItemsNo--;
        product.setAvailableItemsNo(product.availableItemsNo);
    }

    // Helper method to find an item in the cart based on the product ID
    private ShoppingCartAdd findItemInCart(Product product) {
        for (ShoppingCartAdd newProduct : shoppingCart) {
            if (newProduct.getProduct().getProductID().equals(product.getProductID())) {
                return newProduct;
            }
        }
        return null;
    }

    public double totalPrice() {
        double totalPrice = 0.0;
        for (ShoppingCartAdd cartAdd : shoppingCart) {
            totalPrice += cartAdd.getProduct().getPrice() * cartAdd.getCount();
        }
        return totalPrice;
    }
}

