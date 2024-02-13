import java.io.Serializable;
public class User implements Serializable{
    private Boolean newUser;
    private String userName;
    private String password;
    private ShoppingCart shoppingCart;

    public User(String userName, String password, Boolean newUser) {
        this.userName = userName;
        this.password = password;
        this.newUser = newUser;
    }
    public Boolean getNewUser() {
        return newUser;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }

    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public  void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "User{" + "Username ='" + userName + '\'' + ", Password ='" + password + '\'' + ", New User = " + newUser + '}';
    }
}