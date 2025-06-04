package jtt.locmelis.dto;

public class OrderItems {
    private int id;
    private int userId;
    private int shoesId;
    private String shoesSize;
    private double price;
    private Integer orderId;
    private Shoes shoe;

    public OrderItems() {}

    public OrderItems(int id, int userId, int shoesId, String shoesSize, double price, Integer orderId) {
        this.id = id;
        this.userId = userId;
        this.shoesId = shoesId;
        this.shoesSize = shoesSize;
        this.price = price;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Shoes getShoe() {
        return shoe;
    }

    public void setShoe(Shoes shoe) {
        this.shoe = shoe;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShoesId() {
        return shoesId;
    }

    public void setShoesId(int shoesId) {
        this.shoesId = shoesId;
    }

    public String getShoesSize() {
        return shoesSize;
    }

    public void setShoesSize(String shoesSize) {
        this.shoesSize = shoesSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
