/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Departament;
import entities.Seller;
import model.Dao.SellerDao;

/**
 *
 * @author gueel
 */
public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"INSERT INTO seller (Name, Email, BirthDate,BaseSalary,DepartmentId) VALUES (?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartaement().getId());
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
	public void update(Seller obj) {

		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(
					"UPDATE seller SET name =?,Email=?, birthDate =?, baseSalary=?, DepartmentId=? WHERE id =?");

			pst.setString(1, obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartaement().getId());
			pst.setInt(6, obj.getId());

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
			pst = conn.prepareStatement("DELETE FROM seller WHERE id =?");
			pst.setInt(1, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new db.DbExeption(e.getMessage());
		}
	}

	@Override
	public Seller fidById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(
					"SELECT seller.*, department.name AS DName \n" + "FROM seller INNER JOIN Department\n"
							+ "ON seller.DepartmentId = department.Id \n" + "where seller.Id = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				Departament dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new db.DbExeption(e.getMessage());
		} finally {
		}

	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement("SELECT seller.*, department.name AS DName \n"
					+ "FROM seller INNER JOIN Department\n" + "ON seller.DepartmentId = department.Id order by name");
			rs = pst.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Departament> map = new HashMap<>();

			while (rs.next()) {
				Departament dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new db.DbExeption(e.getMessage());
		} finally {
		}
	}

	private Departament instantiateDepartment(ResultSet rs) throws SQLException {
		Departament dep = new Departament();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DName"));
		return dep;
	}

	private Seller instantiateSeller(ResultSet rs, Departament dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBirthDate(new Date(rs.getTimestamp("BirthDate").getTime()));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setDepartaement(dep);
		return obj;
	}

	@Override
	public List<Seller> findByDepartment(Departament departament) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(
					"SELECT seller.*, department.name AS DName \n" + "FROM seller INNER JOIN Department\n"
							+ "ON seller.DepartmentId = department.Id \n" + "where DepartmentId = ? order by name");

			pst.setInt(1, departament.getId());
			rs = pst.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Departament> map = new HashMap<>();

			while (rs.next()) {
				Departament dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new db.DbExeption(e.getMessage());
		} finally {
		}
	}

}
