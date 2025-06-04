package jtt.locmelis.queries;

public interface OrderQueries {
    String QUERY_INSERT = "INSERT INTO orders (user_id, total_price) VALUES (?, ?)";
    String QUERY_UPDATE = "UPDATE orders SET user_id = ?, total_price = ? WHERE id = ?";
    String QUERY_DELETE = "DELETE FROM orders WHERE id = ?";
    String QUERY_GET_BY_ID = "SELECT * FROM orders WHERE id = ?";
    String QUERY_GET_ALL = "SELECT * FROM orders";
}
