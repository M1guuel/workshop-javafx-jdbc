
package model.Dao;

import java.util.List;

import entities.Departament;

/**
 *
 * @author gueel
 */
public interface DepartamentDao {
    void insert(Departament obj);
    void update(Departament obj);
    void deleteById(Integer id);
    Departament fidById(Integer id);
    List<Departament> findAll();
}
