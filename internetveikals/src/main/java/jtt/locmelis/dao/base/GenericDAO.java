package jtt.locmelis.dao.base;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    int insert(T value) throws SQLException;
    int update(T value) throws SQLException;
    int deleteById(int id) throws SQLException;
    T getById(int id) throws SQLException;
    List<T> getAll() throws SQLException;
}
