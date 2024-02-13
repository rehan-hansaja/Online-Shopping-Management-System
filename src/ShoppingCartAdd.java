public class ShoppingCartAdd {
    private Product product;
    private int count;
    public ShoppingCartAdd(Product product) {
        this.product = product;
        this.count = 1;      // Initialized to 1 when first product is added to the cart
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        count++;
    }
}
