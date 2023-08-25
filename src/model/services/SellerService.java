package model.services;

import java.util.ArrayList;
import java.util.List;

import entities.Seller;
import model.Dao.DaoFactory;
import model.Dao.SellerDao;
import model.Dao.impl.SellerDaoJDBC;

public class SellerService {

	private SellerDao dao = DaoFactory.createSellerDao();

	public List<Seller> findAll() {
		return dao.findAll();
	}

	public void insert(Seller obj) {
		dao.insert(obj);
	}

	public void saveOrUpdate(Seller obj) {
		if (obj.getId() == null) {
			dao.insert(obj);

		} else {
			dao.update(obj);
		}
	}
	
	
	public void remove(Seller obj) {
		dao.deleteById(obj.getId());
	}
}
