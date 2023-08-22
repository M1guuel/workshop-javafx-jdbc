package model.services;

import java.util.ArrayList;
import java.util.List;

import entities.Departament;

public class DepartamentService {
	
	public List<Departament> findAll() {
		List<Departament> list = new ArrayList<Departament>();
		list.add(new Departament(1, "Books"));
		list.add(new Departament(2, "Computers"));
		list.add(new Departament(3, "Electronics"));
		return list;

	}
}
