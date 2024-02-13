import java.io.Serializable;
public class Clothing extends Product implements Serializable {
    private String size;
    private String colour;
    public Clothing(String productID,String productName,int availableItemsNo,double price,String size,String colour) {
        super(productID, productName, availableItemsNo, price);
        this.size = size;
        this.colour = colour;
    }
    public String getSize() {
        return size;
    }
    public String getColour() {
        return colour;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public  String toString() {
        return "---------Clothing--------" +"\n" +
                "Product ID = " + productID + "\n" + "Product Name = " + productName + "\n" +
                "No od Available Items = " + availableItemsNo +"\n" + "Price = " + price +"\n" +
                "Size = " + size + "\n" + "Colour = " + colour ;
    }
}
