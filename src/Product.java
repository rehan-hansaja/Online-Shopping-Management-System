import java.io.Serializable;
public abstract class Product implements Serializable{
    protected String productID;
    protected String productName;
    protected int availableItemsNo;
    protected double price;

    public Product(String productID,String productName,int availableItemsNo,double price) {
        this.productID = productID;
        this.productName = productName;
        this.availableItemsNo = availableItemsNo;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }
    public String getProductName() {
        return productName;
    }
    public int getAvailableItemsNo() {
        return availableItemsNo;
    }
    public double getPrice() {
        return price;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setAvailableItemsNo(int availableItemsNo) {
        this.availableItemsNo = availableItemsNo;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return "Product ID: " + productID + "\n Product Name: " + productName +
                "\n No of Available Items: " + availableItemsNo + "\n Price: " + price;
    }
}
