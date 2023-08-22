/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Dao;

import db.Conexao;
import model.Dao.impl.DepartamentDaoJDBC;
import model.Dao.impl.SellerDaoJDBC;

/**
 *
 * @author gueel
 */
public class DaoFactory {
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(Conexao.getConnection());
    }
    public static DepartamentDao createDapartamentDao(){
        return new DepartamentDaoJDBC(Conexao.getConnection());
    }
}
