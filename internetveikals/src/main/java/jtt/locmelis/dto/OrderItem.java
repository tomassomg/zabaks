package jtt.locmelis.dto;

public class OrderItem {
    private int id;
    private int orderId;
    private int productId;;
    private double priceAtPurchase;

    public OrderItem() {}

    public OrderItem(int id, int orderId, int productId, double priceAtPurchase) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.priceAtPurchase = priceAtPurchase;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public double getPriceAtPurchase() { return priceAtPurchase; }
    public void setPriceAtPurchase(double priceAtPurchase) { this.priceAtPurchase = priceAtPurchase; }
}
