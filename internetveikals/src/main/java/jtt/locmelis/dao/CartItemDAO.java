package jtt.locmelis.dao;

import jtt.locmelis.dao.base.GenericDAO;
import jtt.locmelis.dto.CartItem;

public interface CartItemDAO extends GenericDAO<CartItem> {
    CartItem getByUserAndProduct(int userId, int productId) throws Exception;
}
