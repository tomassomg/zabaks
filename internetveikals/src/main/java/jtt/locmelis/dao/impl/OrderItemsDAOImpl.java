package jtt.locmelis.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jtt.locmelis.connection.Database;
import jtt.locmelis.dao.base.GenericDAO;
import jtt.locmelis.dto.OrderItems;
import jtt.locmelis.dto.Shoes;
import jtt.locmelis.queries.OrderItemsQueries;

public class OrderItemsDAOImpl implements GenericDAO<OrderItems>, OrderItemsQueries {

    @Override
    public int insert(OrderItems item) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, item.getUserId());
        stmt.setInt(2, item.getShoesId());
        stmt.setString(3, item.getShoesSize());
        stmt.setDouble(4, item.getPrice());

        if (item.getOrderId() != null) {
            stmt.setInt(5, item.getOrderId());
        } else {
            stmt.setNull(5, Types.INTEGER);
        }

        return stmt.executeUpdate();
    }

    @Override
    public int update(OrderItems item) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, item.getUserId());
        stmt.setInt(2, item.getShoesId());
        stmt.setString(3, item.getShoesSize());
        stmt.setDouble(4, item.getPrice());

        if (item.getOrderId() != null) {
            stmt.setInt(5, item.getOrderId());
        } else {
            stmt.setNull(5, Types.INTEGER);
        }

        stmt.setInt(6, item.getId());
        return stmt.executeUpdate();
    }

    @Override
    public int deleteById(int id) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, id);
        return stmt.executeUpdate();
    }

    @Override
    public OrderItems getById(int id) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.first()) {
            int userId = rs.getInt("user_id");
            int shoesId = rs.getInt("shoes_id");
            String shoesSize = rs.getString("shoes_size");
            double price = rs.getDouble("price");
            int orderId = rs.getInt("order_id");
            Integer orderIdValue = rs.wasNull() ? null : orderId;

            return new OrderItems(id, userId, shoesId, shoesSize, price, orderIdValue);
        }

        return null;
    }

    @Override
    public List<OrderItems> getAll() throws SQLException {
        Connection conn = Database.getConnection();
        List<OrderItems> list = new ArrayList<>();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(QUERY_GET_ALL);

        while (rs.next()) {
            int id = rs.getInt("id");
            int userId = rs.getInt("user_id");
            int shoesId = rs.getInt("shoes_id");
            String shoesSize = rs.getString("shoes_size");
            double price = rs.getDouble("price");
            int orderId = rs.getInt("order_id");
            Integer orderIdValue = rs.wasNull() ? null : orderId;

            list.add(new OrderItems(id, userId, shoesId, shoesSize, price, orderIdValue));
        }

        return list;
    }
    
    public List<OrderItems> getWithShoesByUserId(int userId) throws SQLException {
        List<OrderItems> items = new ArrayList<>();

        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT oi.id, oi.user_id, oi.shoes_id, oi.shoes_size, oi.price, oi.order_id, " +
            "s.name AS shoe_name, s.color AS shoe_color, s.url AS shoe_url " +
            "FROM order_items oi " +
            "JOIN shoes s ON oi.shoes_id = s.id " +
            "WHERE oi.user_id = ?",
            ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_READ_ONLY
        );
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Shoes shoe = new Shoes();
            shoe.setId(rs.getInt("shoes_id"));
            shoe.setName(rs.getString("shoe_name"));
            shoe.setColor(rs.getString("shoe_color"));
            shoe.setUrl(rs.getString("shoe_url"));

            OrderItems item = new OrderItems();
            item.setId(rs.getInt("id"));
            item.setUserId(rs.getInt("user_id"));
            item.setShoesId(rs.getInt("shoes_id"));
            item.setShoesSize(rs.getString("shoes_size"));
            item.setPrice(rs.getDouble("price"));
            int orderId = rs.getInt("order_id");
            item.setOrderId(rs.wasNull() ? null : orderId);
            item.setShoe(shoe);

            items.add(item);
        }

        rs.close();
        stmt.close();
        conn.close();

        return items;
    }

    public List<OrderItems> getByUserId(int userId) throws SQLException {
        Connection conn = Database.getConnection();
        List<OrderItems> list = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_USER_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int shoesId = rs.getInt("shoes_id");
            String shoesSize = rs.getString("shoes_size");
            double price = rs.getDouble("price");
            int orderId = rs.getInt("order_id");
            Integer orderIdValue = rs.wasNull() ? null : orderId;

            list.add(new OrderItems(id, userId, shoesId, shoesSize, price, orderIdValue));
        }

        return list;
    }
}
