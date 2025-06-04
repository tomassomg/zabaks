package jtt.locmelis.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jtt.locmelis.connection.Database;
import jtt.locmelis.dao.ShoesDAO;
import jtt.locmelis.dto.Shoes;
import jtt.locmelis.queries.ShoesQueries;

public class ShoesDAOImpl implements ShoesDAO, ShoesQueries {

    @Override
    public int insert(Shoes shoe) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, shoe.getName());
        stmt.setString(2, shoe.getBrand());
        stmt.setDouble(3, shoe.getPrice());
        stmt.setString(4, shoe.getGender());
        stmt.setString(5, shoe.getSizes());
        stmt.setString(6, shoe.getColor());
        stmt.setString(7, shoe.getUrl());
        return stmt.executeUpdate();
    }

    @Override
    public int update(Shoes shoe) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, shoe.getName());
        stmt.setString(2, shoe.getBrand());
        stmt.setDouble(3, shoe.getPrice());
        stmt.setString(4, shoe.getGender());
        stmt.setString(5, shoe.getSizes());
        stmt.setString(6, shoe.getColor());
        stmt.setString(7, shoe.getUrl());
        stmt.setInt(8, shoe.getId());
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
    public Shoes getById(int id) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_ID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.first()) {
            String name = rs.getString("name");
            String brand = rs.getString("brand");
            double price = rs.getDouble("price");
            String gender = rs.getString("gender");
            String sizes = rs.getString("sizes");
            String color = rs.getString("color");
            String url = rs.getString("url");
            return new Shoes(id, name, brand, price, gender, sizes, color, url);
        }

        return null;
    }

    @Override
    public List<Shoes> getAll() throws SQLException {
        Connection conn = Database.getConnection();
        List<Shoes> list = new ArrayList<>();
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(QUERY_GET_ALL);

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String brand = rs.getString("brand");
            double price = rs.getDouble("price");
            String gender = rs.getString("gender");
            String sizes = rs.getString("sizes");
            String color = rs.getString("color");
            String url = rs.getString("url");
            list.add(new Shoes(id, name, brand, price, gender, sizes, color, url));
        }

        return list;
    }

    @Override
    public Shoes getByName(String name) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_NAME, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        if (rs.first()) {
            int id = rs.getInt("id");
            String brand = rs.getString("brand");
            double price = rs.getDouble("price");
            String gender = rs.getString("gender");
            String sizes = rs.getString("sizes");
            String color = rs.getString("color");
            String url = rs.getString("url");
            return new Shoes(id, name, brand, price, gender, sizes, color, url);
        }

        return null;
    }

    public List<Shoes> getAllByGender(String gender) throws SQLException {
        Connection conn = Database.getConnection();
        List<Shoes> list = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_BY_GENDER, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, gender);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String brand = rs.getString("brand");
            double price = rs.getDouble("price");
            String sizes = rs.getString("sizes");
            String color = rs.getString("color");
            String url = rs.getString("url");
            list.add(new Shoes(id, name, brand, price, gender, sizes, color, url));
        }

        return list;
    }
    
    @Override
    public List<Shoes> getAllCheaperThan70() throws SQLException {
        Connection conn = Database.getConnection();
        List<Shoes> list = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(QUERY_GET_CHEAPER_THAN_70, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String brand = rs.getString("brand");
            double price = rs.getDouble("price");
            String gender = rs.getString("gender");
            String sizes = rs.getString("sizes");
            String color = rs.getString("color");
            String url = rs.getString("url");
            list.add(new Shoes(id, name, brand, price, gender, sizes, color, url));
        }

        return list;
    }

    public Shoes getByUrl(String url) throws SQLException {
        Connection conn = Database.getConnection();
        String sql = "SELECT * FROM shoes WHERE url = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, url);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Shoes shoe = new Shoes();
            shoe.setId(rs.getInt("id"));
            shoe.setName(rs.getString("name"));
            shoe.setBrand(rs.getString("brand"));
            shoe.setPrice(rs.getDouble("price"));
            shoe.setGender(rs.getString("gender"));
            shoe.setColor(rs.getString("color"));
            shoe.setSizes(rs.getString("sizes"));
            shoe.setUrl(rs.getString("url"));
            return shoe;
        }
        return null;
    }
    
    public List<String> getSizesList(Shoes shoe) {
        List<String> sizesList = new ArrayList<>();
        if (shoe != null && shoe.getSizes() != null) {
            String[] sizesArray = shoe.getSizes().split(",");
            for (String size : sizesArray) {
                sizesList.add(size.trim());
            }
        }
        return sizesList;
    }

    public List<String> getSizesAsList(Shoes shoe) {
        List<String> sizesList = new ArrayList<>();
        if (shoe.getSizes() != null && !shoe.getSizes().isEmpty()) {
            String[] sizesArray = shoe.getSizes().split(",");
            for (String size : sizesArray) {
                sizesList.add(size.trim());
            }
        }
        return sizesList;
    }

}
