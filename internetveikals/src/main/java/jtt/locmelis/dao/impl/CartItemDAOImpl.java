package jtt.locmelis.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jtt.locmelis.connection.Database;
import jtt.locmelis.dao.CartItemDAO;
import jtt.locmelis.dto.CartItem;
import jtt.locmelis.queries.CartItemQueries;

public class CartItemDAOImpl implements CartItemDAO, CartItemQueries {

    @Override
    public int insert(CartItem item) throws SQLException {
        Connection conn = Database.getConnection();
        List<CartItem> existing = getAll();

        for (CartItem i : existing) {
            if (i.getUserId() == item.getUserId() && i.getProductId() == item.getProductId()) {
                return 0;
            }
        }

        PreparedStatement stmt = conn.prepareStatement(QUERY_INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, item.getUserId());
        stmt.setInt(2, item.getProductId());

        int result = stmt.executeUpdate();
        return result;
    }

    @Override
    public int update(CartItem item) throws SQLException {
        Connection conn = Database.getConnection();

        PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, item.getUserId());
        stmt.setInt(2, item.getProductId());
        stmt.setInt(4, item.getId());

        int result = stmt.executeUpdate();
        return result;
    }

    @Override
    public int deleteById(int id) throws SQLException {
        Connection conn = Database.getConnection();

        PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, id);

        int result = stmt.executeUpdate();
        return result;
    }

    @Override
    public CartItem getById(int id) throws SQLException {
        Connection conn = Database.getConnection();

        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        rs.first();

        return new CartItem(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getInt("product_id")
        );
    }

    @Override
    public List<CartItem> getAll() throws SQLException {
        Connection conn = Database.getConnection();
        List<CartItem> items = new ArrayList<>();

        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(QUERY_GET_ALL);

        while (rs.next()) {
            CartItem item = new CartItem(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("product_id")
            );
            items.add(item);
        }

        return items;
    }

    @Override
    public CartItem getByUserAndProduct(int userId, int productId) throws SQLException {
        Connection conn = Database.getConnection();

        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_USER_AND_PRODUCT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, userId);
        stmt.setInt(2, productId);

        ResultSet rs = stmt.executeQuery();
        rs.first();

        return new CartItem(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getInt("product_id")
        );
    }
}
