package jtt.locmelis.dao;

import java.sql.SQLException;
import jtt.locmelis.dto.Order;
import java.util.List;

import jtt.locmelis.dao.base.GenericDAO;

public interface OrderDAO extends GenericDAO<Order> {
    List<Order> getByUserId(int userId) throws SQLException;
}
