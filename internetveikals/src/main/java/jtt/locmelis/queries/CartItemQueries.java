package jtt.locmelis.queries;

public interface CartItemQueries {
    final String TABLE = "cart_items";
    
    final String QUERY_INSERT = "INSERT INTO " + TABLE + "(user_id, product_id) VALUES (?, ?)";
    
    final String QUERY_UPDATE = "UPDATE " + TABLE + " SET user_id = ?, product_id = ? WHERE id = ?";
    
    final String QUERY_DELETE = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    final String QUERY_GET_BY_ID = "SELECT * FROM " + TABLE + " WHERE id = ?";
    final String QUERY_GET_BY_USER_AND_PRODUCT = "SELECT * FROM " + TABLE + " WHERE user_id = ? AND product_id = ?";
    final String QUERY_GET_ALL = "SELECT * FROM " + TABLE;
}
