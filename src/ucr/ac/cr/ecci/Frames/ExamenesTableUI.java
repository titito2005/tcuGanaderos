/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Frames;

import static ucr.ac.cr.ecci.Frames.VacaTableUI.centreWindow;
import ucr.ac.cr.ecci.Models.Bovino;
import ucr.ac.cr.ecci.Models.ExamenAndrologico;

/**
 *
 * @author afg
 */
public class ExamenesTableUI extends javax.swing.JFrame {
    
    Bovino bovino;
    String bovino_id;

    /**
     * Creates new form ExamenesTableUI
     */
    public ExamenesTableUI() {
        centreWindow(this);
        initComponents();
    }
    
    public ExamenesTableUI(Bovino bovino) {
        centreWindow(this);
        this.bovino = bovino;
        this.bovino_id = Integer.toString(bovino.getId());
        initComponents();
        toroIdTextField.setText(bovino_id);
        toroNombreTextField.setText(bovino.getNombre());
        setEnabledFalse();
    }
    
    private void setEnabledFalse() {
        toroIdTextField.setEditable(false);
        toroNombreTextField.setEditable(false);
    }
    
    private void redrawExamenesTable() {
        examenAndrologicoQuery = TCUGanaderosPUEntityManager.createQuery("SELECT e FROM ExamenAndrologico e WHERE e.idBovino.id = " + bovino_id);
        examenAndrologicoList = examenAndrologicoQuery.getResultList();
        examenesTable.setColumnSelectionAllowed(false);
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, examenAndrologicoList, examenesTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fecha}"));
        columnBinding.setColumnName("Fecha");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${observaciones}"));
        columnBinding.setColumnName("Observaciones");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(examenesTable);
        if (examenesTable.getColumnModel().getColumnCount() > 0) {
            examenesTable.getColumnModel().getColumn(0).setResizable(false);
            examenesTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            examenesTable.getColumnModel().getColumn(1).setResizable(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        TCUGanaderosPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("TCUGanaderosPU").createEntityManager();
        examenAndrologicoQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT e FROM ExamenAndrologico e WHERE e.idBovino.id = " + bovino_id);
        examenAndrologicoList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : examenAndrologicoQuery.getResultList();
        jLabel1 = new javax.swing.JLabel();
        toroIdTextField = new javax.swing.JTextField();
        toroNombreTextField = new javax.swing.JTextField();
        volverButton = new javax.swing.JButton();
        agregarButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        examenesTable = new javax.swing.JTable();
        detallesButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jLabel1.setText("Toro:");

        volverButton.setText("Volver");
        volverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverButtonActionPerformed(evt);
            }
        });

        agregarButton.setText("Agregar");
        agregarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarButtonActionPerformed(evt);
            }
        });

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, examenAndrologicoList, examenesTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fecha}"));
        columnBinding.setColumnName("Fecha");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${observaciones}"));
        columnBinding.setColumnName("Observaciones");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(examenesTable);
        if (examenesTable.getColumnModel().getColumnCount() > 0) {
            examenesTable.getColumnModel().getColumn(0).setResizable(false);
            examenesTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            examenesTable.getColumnModel().getColumn(1).setResizable(false);
        }

        detallesButton.setText("Detalles");
        detallesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detallesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(volverButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(detallesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(agregarButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(toroIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(toroNombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(toroIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toroNombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(volverButton)
                    .addComponent(agregarButton)
                    .addComponent(detallesButton))
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButtonActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_volverButtonActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        redrawExamenesTable();
    }//GEN-LAST:event_formWindowGainedFocus

    private void agregarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarButtonActionPerformed
        new ExamenesUI(bovino).setVisible(true);
    }//GEN-LAST:event_agregarButtonActionPerformed

    private void detallesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detallesButtonActionPerformed
        if (examenesTable.getSelectedRow() != -1) {
            new ExamenesUI(bovino, (ExamenAndrologico) examenAndrologicoList.get(examenesTable.getSelectedRow())).setVisible(true);
        }
    }//GEN-LAST:event_detallesButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ExamenesTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExamenesTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExamenesTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExamenesTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExamenesTableUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager TCUGanaderosPUEntityManager;
    private javax.swing.JButton agregarButton;
    private javax.swing.JButton detallesButton;
    private java.util.List<ucr.ac.cr.ecci.Models.ExamenAndrologico> examenAndrologicoList;
    private javax.persistence.Query examenAndrologicoQuery;
    private javax.swing.JTable examenesTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField toroIdTextField;
    private javax.swing.JTextField toroNombreTextField;
    private javax.swing.JButton volverButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
