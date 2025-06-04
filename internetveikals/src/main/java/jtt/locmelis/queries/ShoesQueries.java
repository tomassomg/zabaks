package jtt.locmelis.queries;

public interface ShoesQueries {
    String TABLE = "shoes";

    String QUERY_INSERT = "INSERT INTO " + TABLE + " (name, brand, price, gender, sizes, color, url) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    String QUERY_UPDATE = "UPDATE " + TABLE + " SET name = ?, brand = ?, price = ?, gender = ?, sizes = ?, color = ?, url = ?" + "WHERE id = ?";

    String QUERY_DELETE = "DELETE FROM " + TABLE + " WHERE id = ?";

    String QUERY_GET_BY_ID = "SELECT * FROM " + TABLE + " WHERE id = ?";

    String QUERY_GET_BY_NAME = "SELECT * FROM " + TABLE + " WHERE name = ?";

    String QUERY_GET_ALL = "SELECT * FROM " + TABLE;
    
    String QUERY_GET_BY_GENDER = "SELECT * FROM shoes WHERE gender = ?";
    
    String QUERY_GET_CHEAPER_THAN_70 = "SELECT * FROM shoes WHERE price < 70";
}
