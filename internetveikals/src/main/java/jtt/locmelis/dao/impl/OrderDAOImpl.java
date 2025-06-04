package jtt.locmelis.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jtt.locmelis.connection.Database;
import jtt.locmelis.dao.OrderDAO;
import jtt.locmelis.dto.Order;
import jtt.locmelis.queries.OrderQueries;

public class OrderDAOImpl implements OrderDAO, OrderQueries {

    @Override
    public int insert(Order order) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, order.getUserId());
        stmt.setDouble(2, order.getTotalAmount());
        stmt.setString(3, order.getStatus());
        return stmt.executeUpdate();
    }

    @Override
    public int update(Order order) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, order.getUserId());
        stmt.setDouble(2, order.getTotalAmount());
        stmt.setString(3, order.getStatus());
        stmt.setInt(4, order.getId());
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
        rs.first();
        int userId = rs.getInt("user_id");
        Timestamp orderDate = rs.getTimestamp("order_date");
        String status = rs.getString("status");
        double totalAmount = rs.getDouble("total_amount");
        return new Order(id, userId, orderDate, totalAmount, status);
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
            Timestamp orderDate = rs.getTimestamp("order_date");
            double totalAmount = rs.getDouble("total_amount");
            String status = rs.getString("status");
            list.add(new Order(id, userId, orderDate, totalAmount, status));
        }

        return list;
    }

    @Override
    public List<Order> getByUserId(int userId) throws SQLException {
        Connection conn = Database.getConnection();
        List<Order> list = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_USER_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            Timestamp orderDate = rs.getTimestamp("order_date");
            double totalAmount = rs.getDouble("total_amount");
            String status = rs.getString("status");
            list.add(new Order(id, userId, orderDate, totalAmount, status));
        }

        return list;
    }
}
