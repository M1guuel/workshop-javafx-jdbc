package model.services;

import java.util.ArrayList;
import java.util.List;

import entities.Departament;
import model.Dao.DaoFactory;
import model.Dao.DepartamentDao;

public class DepartamentService {
	
	private DepartamentDao dao = DaoFactory.createDapartamentDao();

public List<Departament> findAll(){
	return dao.findAll();
	}
}
