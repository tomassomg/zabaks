package jtt.locmelis.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jtt.locmelis.connection.Database;
import jtt.locmelis.dao.OrderDAO;
import jtt.locmelis.dto.Order;
import jtt.locmelis.dto.OrderItems;
import jtt.locmelis.dto.Shoes;
import jtt.locmelis.queries.OrderQueries;

public class OrderDAOImpl implements OrderDAO, OrderQueries {

    @Override
    public int insert(Order order) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, order.getUserId());
        stmt.setDouble(2, order.getTotalPrice());
        return stmt.executeUpdate();
    }

    @Override
    public int update(Order order) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, order.getUserId());
        stmt.setDouble(2, order.getTotalPrice());
        stmt.setInt(3, order.getId());
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
    public Order getById(int id) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.first()) {
            int userId = rs.getInt("user_id");
            double totalPrice = rs.getDouble("total_price");
            return new Order(id, userId, totalPrice);
        }

        return null;
    }

    @Override
    public List<Order> getAll() throws SQLException {
        Connection conn = Database.getConnection();
        List<Order> list = new ArrayList<>();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(QUERY_GET_ALL);

        while (rs.next()) {
            int id = rs.getInt("id");
            int userId = rs.getInt("user_id");
            double totalPrice = rs.getDouble("total_price");
            list.add(new Order(id, userId, totalPrice));
        }

        return list;
    }
    
    public List<OrderItems> getByUserId(int userId) throws SQLException {
        List<OrderItems> list = new ArrayList<>();
        Connection conn = Database.getConnection();

        String sql = "SELECT oi.*, s.name, s.color, s.price, s.url FROM order_items oi " +
                     "JOIN shoes s ON oi.shoe_id = s.id " +
                     "WHERE oi.user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Shoes shoe = new Shoes();
            shoe.setName(rs.getString("name"));
            shoe.setColor(rs.getString("color"));
            shoe.setPrice(rs.getDouble("price"));
            shoe.setUrl(rs.getString("url"));

            OrderItems item = new OrderItems();
            item.setId(rs.getInt("id"));
            item.setUserId(rs.getInt("user_id"));
            item.setShoesId(rs.getInt("shoe_id"));
            item.setShoesSize(rs.getString("size"));
  

            list.add(item);
        }
        rs.close();
        ps.close();
        conn.close();

        return list;
    }

}
