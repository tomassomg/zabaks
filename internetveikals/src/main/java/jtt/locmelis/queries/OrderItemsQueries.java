package jtt.locmelis.queries;

public interface OrderItemsQueries {
    String QUERY_INSERT = "INSERT INTO order_items (user_id, shoes_id, shoes_size, price, order_id) VALUES (?, ?, ?, ?, ?)";
    String QUERY_UPDATE = "UPDATE order_items SET user_id = ?, shoes_id = ?, shoes_size = ?, price = ?, order_id = ? WHERE id = ?";
    String QUERY_DELETE = "DELETE FROM order_items WHERE id = ?";
    String QUERY_GET_BY_ID = "SELECT * FROM order_items WHERE id = ?";
    String QUERY_GET_ALL = "SELECT * FROM order_items";
    String QUERY_GET_BY_USER_ID = "SELECT * FROM order_items WHERE user_id = ?";
}
