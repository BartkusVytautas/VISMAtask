package lt.vytautasb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product implements Comparable<Product> {

    private String name;
    private String code;
    private Integer quantity;
    private Date expirationDate;


    Product(String name, String code, Integer quantity, Date expirationDate) {
        this.name = name;
        this.code = code;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "[name: " + this.name + ", code: " + this.code + ", quantity: " + this.quantity + ", Expiration date: " +
                new SimpleDateFormat("yyy-MM-dd").format(this.expirationDate) + "]";
    }

    @Override
    public int compareTo(Product o) {
        if (this.getName().compareTo(o.getName()) != 0) {
            return this.getName().compareTo(o.getName());
        }
        if (this.getCode().compareTo(o.getCode()) != 0) {
            return this.getCode().compareTo(o.getCode());
        }
        return this.getExpirationDate().compareTo(o.getExpirationDate());
    }
}
