import java.io.Serializable;
public class Electronics extends Product implements Serializable {
    private String brand;
    private int warrantyPeriod;
    public Electronics(String productID,String productName,int availableItemsNo,double price,String brand,int warrantyPeriod) {
        super(productID, productName, availableItemsNo, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;

    }
    public String getBrand() {
        return brand;
    }
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public  String toString() {
        return "--------Electronics---------" +"\n"+
                "Product ID = " + productID +"\n"+ "Product Name = " + productName + "\n" +
                "No of Available Items = " + availableItemsNo +"\n" + "Price = " + price +"\n" +
                "Brand = " + brand +"\n" + "Warranty Period = " + warrantyPeriod ;
    }
}
