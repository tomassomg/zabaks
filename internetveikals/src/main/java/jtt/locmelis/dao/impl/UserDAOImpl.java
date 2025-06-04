package jtt.locmelis.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jtt.locmelis.connection.Database;
import jtt.locmelis.dao.UserDAO;
import jtt.locmelis.dto.User;
import jtt.locmelis.queries.UserQueries;

public class UserDAOImpl implements UserDAO, UserQueries {

    @Override
    public int insert(User user) throws SQLException {
        Connection conn = Database.getConnection();
        List<User> users = getAll();

        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return 0;
            }
        }

        PreparedStatement stmt = conn.prepareStatement(QUERY_INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getRole());
        stmt.setDouble(5, user.getBalance());
        return stmt.executeUpdate();
    }

    @Override
    public int update(User user) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getEmail());
        stmt.setDouble(4, user.getBalance());
        stmt.setInt(5, user.getId());
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
    public User getById(int id) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.first();
        return new User(
            id,
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("role"),
            rs.getDouble("balance")
        );
    }

    @Override
    public List<User> getAll() throws SQLException {
        Connection conn = Database.getConnection();
        List<User> users = new ArrayList<>();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(QUERY_GET_ALL);

        while (rs.next()) {
            User user = new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("role"),
                rs.getDouble("balance")
            );
            users.add(user);
        }

        return users;
    }
    
    public User getByCredentials(String username, String password) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_CREDENTIALS);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("role"),
                rs.getDouble("balance")
            );
        }

        return null;
    }
    
    public User getByUsername(String username) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("role"),
                rs.getDouble("balance")
            );
        }
        return null;
    }

}
