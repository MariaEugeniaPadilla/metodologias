/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.agendacontactos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author stiven
 */
public class FormularioAgenda extends javax.swing.JFrame {
    private JTextField nombreField, telefonoField, emailField;
    private JTable tablaContactos;
    private DefaultTableModel modeloTabla;
    private ContactoDAO contactoDAO;
    
    public FormularioAgenda() {
       contactoDAO = new ContactoDAO();

        setTitle("Agenda de Contactos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); 

        JPanel panel = new JPanel();
        panel.setBounds(20, 20, 550, 330);
        panel.setLayout(null);
        add(panel);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(10, 10, 80, 25);
        panel.add(nombreLabel);

        nombreField = new JTextField();
        nombreField.setBounds(100, 10, 160, 25);
        panel.add(nombreField);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(10, 40, 80, 25);
        panel.add(telefonoLabel);

        telefonoField = new JTextField();
        telefonoField.setBounds(100, 40, 160, 25);
        panel.add(telefonoField);
        telefonoField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            // Permitir solo números y el carácter de retroceso
            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                e.consume(); // Descartar el evento si no es un número
            }
        }
    });

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 70, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 70, 160, 25);
        panel.add(emailField);
        
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String email = emailField.getText();
                if (!email.contains("@")) {
                    JOptionPane.showMessageDialog(panel, "El correo electrónico debe contener al menos un '@'", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    emailField.requestFocus(); // Volver a enfocar el campo
                }
            }
        });
        JButton agregarButton = new JButton("Agregar Contacto");
        agregarButton.setBounds(100, 100, 160, 25);
        panel.add(agregarButton);
        
        

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Email");

        tablaContactos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaContactos);
        scrollPane.setBounds(10, 140, 520, 150);
        panel.add(scrollPane);

        cargarContactos();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarContacto();
            }
        });

        JButton eliminarButton = new JButton("Eliminar Contacto");
        eliminarButton.setBounds(100, 300, 160, 25);
        panel.add(eliminarButton);

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarContacto();
            }
        });

        setVisible(true);
    }

    private void cargarContactos() {
        modeloTabla.setRowCount(0);
        List<Contacto> contactos = contactoDAO.obtenerContactos();

        for (Contacto contacto : contactos) {
            modeloTabla.addRow(new Object[]{
                contacto.getId(),
                contacto.getNombre(),
                contacto.getTelefono(),
                contacto.getEmail()
            });
        }
    }

    private void agregarContacto() {
        String nombre = nombreField.getText();
        String telefono = telefonoField.getText();
        String email = emailField.getText();

        if (!nombre.isEmpty() && !telefono.isEmpty() && !email.isEmpty()) {
            contactoDAO.agregarContacto(nombre, telefono, email);
            cargarContactos();
            nombreField.setText("");
            telefonoField.setText("");
            emailField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarContacto() {
    int filaSeleccionada = tablaContactos.getSelectedRow();
    if (filaSeleccionada != -1) {
        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        // Mostrar diálogo de confirmación
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de que desea eliminar el contacto: " + nombre + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Si el usuario confirma, eliminar el contacto
            contactoDAO.eliminarContacto(id);
            cargarContactos();  // Recargar los contactos
        }
    } else {
        JOptionPane.showMessageDialog(null, "Seleccione un contacto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormularioAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioAgenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioAgenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
