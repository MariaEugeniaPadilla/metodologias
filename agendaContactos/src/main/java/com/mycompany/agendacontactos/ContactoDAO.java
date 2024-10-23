/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.agendacontactos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactoDAO {

    public void agregarContacto(String nombre, int telefono, String email) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO contactos (nombre, telefono, email) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setInt(2, telefono);
            stmt.setString(3, email);
            stmt.executeUpdate();
            System.out.println("Contacto agregado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contacto> obtenerContactos() {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM contactos";
        List<Contacto> contactos = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contacto contacto = new Contacto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("telefono"),
                    rs.getString("email")
                );
                contactos.add(contacto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactos;
    }
    
    public void editarContacto(int id, String nombre, Int telefono, String email) {
    try {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE contactos SET nombre = ?, telefono = ?, email = ? WHERE id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, nombre);
        pst.setInt(2, telefono);
        pst.setString(3, email);
        pst.setInt(4, id);
        pst.executeUpdate();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void eliminarContacto(int id) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM contactos";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Contacto eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
