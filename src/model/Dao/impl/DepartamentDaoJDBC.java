package model.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entities.Departament;
import entities.Seller;
import model.Dao.DepartamentDao;

public class DepartamentDaoJDBC implements DepartamentDao {
	private Connection conn;

	public DepartamentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Departament obj) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("INSERT INTO DEPARTMENT (ID,NAME) VALUES (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, obj.getId());
			pst.setString(2, obj.getNome());
			int row = pst.executeUpdate();
			if (row > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}

			} else {
				throw new db.DbExeption("Erro Inesperada, nenhuma linha afertada");
			}
		} catch (SQLException e) {
			throw new db.DbExeption(e.getMessage());
		} finally {
		}
	}

	@Override
	public void update(Departament obj) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("UPDATE department set id=?, name=?");
			pst.setInt(1, obj.getId());
			pst.setString(2, obj.getNome());
			int row = pst.executeUpdate();

		} catch (SQLException e) {
			throw new db.DbExeption(e.getMessage());
		} finally {
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement("DELETE FROM departament WHERE id =?");
			pst.setInt(1, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new db.DbExeption(e.getMessage());
		}
	}

	@Override
	public Departament fidById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Departament dep = new Departament();
		try {
			pst = conn.prepareStatement("Select * from department where id=?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				dep.setId(rs.getInt("Id"));
				dep.setNome(rs.getString("Name"));
			}
			return dep;

		} catch (SQLException e) {
			throw new db.DbExeption(e.getMessage());
		}
	}

	@Override
	public List<Departament> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Departament dep;
		List<Departament> list = new ArrayList<>();
		try {
			pst = conn.prepareStatement("SELECT * FROM department ");
			rs = pst.executeQuery();

			while (rs.next()) {
				dep = new Departament(rs.getInt("id"), rs.getString("name"));
				list.add(dep);
			}
			return list;

		} catch (SQLException e) {
			throw new db.DbExeption(e.getMessage());
		} finally {
		}
	}

}
