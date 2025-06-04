package jtt.locmelis.queries;

public interface OrderItemQueries {
    final String TABLE = "order_items";
    
    final String QUERY_INSERT = "INSERT INTO " + TABLE + "(order_id, product_id, price_at_purchase) VALUES (?, ?, ?)";
    
    final String QUERY_UPDATE = "UPDATE " + TABLE + " SET order_id = ?, product_id = ?, price_at_purchase = ? WHERE id = ?";
    
    final String QUERY_DELETE = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    final String QUERY_GET_BY_ORDER_ID = "SELECT * FROM " + TABLE + " WHERE order_id = ?";
    final String QUERY_GET_BY_PRODUCT_ID = "SELECT * FROM " + TABLE + " WHERE product_id = ?";
    final String QUERY_GET_ALL = "SELECT * FROM " + TABLE;
}
