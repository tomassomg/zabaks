package jtt.locmelis.queries;

public interface UserQueries {
    final String TABLE = "users";
    
    final String QUERY_INSERT = "INSERT INTO " + TABLE + "(username, password, email, role, balance) VALUES (?, ?, ?, ?, ?)";
    
    final String QUERY_UPDATE = "UPDATE " + TABLE + " SET username = ?, password = ?, email = ?, role = ?, balance = ? WHERE id = ?";
    
    final String QUERY_DELETE = "DELETE FROM " + TABLE + " WHERE id = ?";
    
    final String QUERY_GET_BY_CREDENTIALS = "SELECT * FROM " + TABLE + " WHERE username = ? AND password = ?";
    final String QUERY_GET_BY_ID = "SELECT * FROM " + TABLE + " WHERE id = ?";
    final String QUERY_GET_ALL = "SELECT * FROM " + TABLE;
}
