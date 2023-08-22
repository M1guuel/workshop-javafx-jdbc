/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model.Dao;

import java.util.List;

import entities.Departament;
import entities.Seller;

/**
 *
 * @author gueel
 */
public interface SellerDao {
    void insert(Seller obj);
    void update(Seller obj);
    void deleteById(Integer id);
    Seller fidById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Departament departament);
}
