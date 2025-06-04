package jtt.locmelis.dao;

import jtt.locmelis.dto.Shoes;

import java.sql.SQLException;
import java.util.List;

import jtt.locmelis.dao.base.GenericDAO;

public interface ShoesDAO extends GenericDAO<Shoes> {
	Shoes getById(int id) throws SQLException;
    Shoes getByName(String name) throws SQLException;
	List<Shoes> getAllCheaperThan70() throws SQLException;
}
