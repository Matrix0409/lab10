/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author deshm
 */
package Database;

public interface CRUD {
    boolean create();
    Object read(int id);
    boolean update();
    boolean delete(int id);
}