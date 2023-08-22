package db;

import javax.swing.JOptionPane;

/**
 *
 * @author gueel
 */
public class DBIntergrityExeception extends RuntimeException{
    public DBIntergrityExeception(String msg){
       JOptionPane.showMessageDialog(null, msg);
    }
}
