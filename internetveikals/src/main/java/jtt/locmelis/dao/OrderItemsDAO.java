package jtt.locmelis.dao;

import jtt.locmelis.dao.base.GenericDAO;
import jtt.locmelis.dto.OrderItems;

public interface OrderItemsDAO extends GenericDAO<OrderItems> {
    OrderItems getByUserAndProduct(int userId, int productId) throws Exception;
}
