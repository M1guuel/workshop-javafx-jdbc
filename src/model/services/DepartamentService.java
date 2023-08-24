package model.services;

import java.util.ArrayList;
import java.util.List;

import entities.Departament;
import model.Dao.DaoFactory;
import model.Dao.DepartamentDao;
import model.Dao.impl.DepartamentDaoJDBC;

public class DepartamentService {

	private DepartamentDao dao = DaoFactory.createDapartamentDao();

	public List<Departament> findAll() {
		return dao.findAll();
	}

	public void insert(Departament obj) {
		dao.insert(obj);
	}

	public void saveOrUpdate(Departament obj) {
		if (obj != null) {
			dao.insert(obj);

		} else {
			dao.update(obj);
		}
	}
}
