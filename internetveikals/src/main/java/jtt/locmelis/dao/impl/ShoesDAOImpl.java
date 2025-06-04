package jtt.locmelis.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jtt.locmelis.connection.Database;
import jtt.locmelis.dao.ShoesDAO;
import jtt.locmelis.dto.Shoes;
import jtt.locmelis.queries.ShoesQueries;

public class ShoesDAOImpl implements ShoesDAO, ShoesQueries {

    public void addSize(String newSize, Shoes shoe) {
        String sizes = shoe.getSizes();
        if (sizes.isEmpty()) {
            shoe.setSizes(newSize);	
        } else {
            shoe.setSizes(sizes + " " + newSize);
        }
    }

    public void removeSize(String sizeToRemove, Shoes shoe) {
        String sizes = shoe.getSizes();
        List<String> sizeList = new ArrayList<>();
        String[] allSizes = sizes.split(" ");
        for (int i = 0; i < allSizes.length; i++) {
            if (!allSizes[i].equals(sizeToRemove)) {
                sizeList.add(allSizes[i]);
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sizeList.size(); i++) {
            result.append(sizeList.get(i));
            if (i < sizeList.size() - 1) {
                result.append(" ");
            }
        }
        shoe.setSizes(result.toString());
    }
    
    public List<String> getSizesAsList(String sizes) {
        List<String> sizeList = new ArrayList<>();

        if (sizes != null && !sizes.isEmpty()) {
            String[] sizeArray = sizes.trim().split("[,\\s]+");

            for (String sizeStr : sizeArray) {
                if (!sizeStr.isEmpty()) {
                    sizeList.add(sizeStr);
                }
            }
        }

        return sizeList;
    }




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
        stmt.setInt(7, shoe.getId());
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

}
