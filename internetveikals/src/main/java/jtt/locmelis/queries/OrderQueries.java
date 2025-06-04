package jtt.locmelis.queries;

public interface OrderQueries {
    final String TABLE = "orders";
    
    final String QUERY_INSERT = "INSERT INTO " + TABLE + "(user_id, total_amount, status) VALUES (?, ?, ?)";
    
    final String QUERY_UPDATE = "UPDATE " + TABLE + " SET user_id = ?, total_amount = ?, status = ? WHERE id = ?";
    
    final String QUERY_DELETE = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    final String QUERY_GET_BY_ID = "SELECT * FROM " + TABLE + " WHERE id = ?";
    final String QUERY_GET_ALL = "SELECT * FROM " + TABLE;
    final String QUERY_GET_BY_USER_ID = "SELECT * FROM " + TABLE + " WHERE user_id = ?";
}
