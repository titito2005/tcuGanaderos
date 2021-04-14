/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Frames;

import java.util.Calendar;
import static java.util.concurrent.TimeUnit.DAYS;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ucr.ac.cr.ecci.DBManager;
import static ucr.ac.cr.ecci.Frames.VacaTableUI.centreWindow;
import ucr.ac.cr.ecci.Models.Bovino;
import ucr.ac.cr.ecci.Models.Parto;

/**
 *
 * @author afg
 */
public class PartosTableUI extends javax.swing.JFrame {
    
    Bovino bovino;
    String bovino_id;

    /**
     * Creates new form PartosTableUI
     */
    public PartosTableUI() {
        centreWindow(this);
        initComponents();
    }
    
    public PartosTableUI(Bovino bovino) {
        centreWindow(this);
        this.bovino = bovino;
        this.bovino_id = Integer.toString(bovino.getId());
        initComponents();
        numTextField.setText(bovino_id);
        nombreTextField.setText(bovino.getNombre());
        setEnabledFalse();
        if (partoList.size() > 1){
            iepTextField.setText(Integer.toString(getIEP(partoList.get(partoList.size()-2),partoList.get(partoList.size()-1))));
            promedioTextField.setText(Integer.toString(getPromedioIEP()));
        } else {
            iepTextField.setText(Integer.toString(0));
            promedioTextField.setText(Integer.toString(0));
        }
            
        
    }
    
    private void setEnabledFalse() {
        numTextField.setEditable(false);
        nombreTextField.setEditable(false);
    }
    
    private void redrawPartosTable() {
        partoQuery = TCUGanaderosPUEntityManager.createQuery("SELECT p FROM Parto p WHERE p.idMadre.id = " + bovino_id);
        partoList = partoQuery.getResultList();
        partosTable.setColumnSelectionAllowed(false);
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, partoList, partosTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idPadre.id}"));
        columnBinding.setColumnName("# Padre");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sexo}"));
        columnBinding.setColumnName("Sexo");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fecha}"));
        columnBinding.setColumnName("Fecha");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${muerteprematura}"));
        columnBinding.setColumnName("Muerte Prematura");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(partosTable);
        if (partosTable.getColumnModel().getColumnCount() > 0) {
            partosTable.getColumnModel().getColumn(0).setResizable(false);
            partosTable.getColumnModel().getColumn(0).setPreferredWidth(45);
            partosTable.getColumnModel().getColumn(1).setResizable(false);
            partosTable.getColumnModel().getColumn(2).setResizable(false);
            partosTable.getColumnModel().getColumn(3).setResizable(false);
        }
        if (partoList.size() > 1){
            iepTextField.setText(Integer.toString(getIEP(partoList.get(partoList.size()-2),partoList.get(partoList.size()-1))));
            promedioTextField.setText(Integer.toString(getPromedioIEP()));
        } else {
            iepTextField.setText(Integer.toString(0));
            promedioTextField.setText(Integer.toString(0));
        }
    }
    
    int getIEP(Parto p1, Parto p2){
        int iep;
        
        Calendar p1fecha = Calendar.getInstance();
        Calendar p2fecha = Calendar.getInstance();
        p1fecha.setTime(p1.getFecha());
        p2fecha.setTime(p2.getFecha());

        for (iep = 0; p1fecha.before(p2fecha); iep++) {
            p1fecha.add(Calendar.DAY_OF_MONTH, 1);
        } 
        
        return iep;
    }
    
    int getPromedioIEP(){
        int iep = 0;
        int i = 0;
        
        for (i = 1; i < partoList.size(); i++){
            iep += getIEP(partoList.get(partoList.size()-(i+1)),partoList.get(partoList.size()-i));
        }
        
        return iep/(i-1);
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
        partoQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT p FROM Parto p WHERE p.idMadre.id = " + bovino_id);
        partoList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : partoQuery.getResultList();
        jScrollPane1 = new javax.swing.JScrollPane();
        partosTable = new javax.swing.JTable();
        volverButton = new javax.swing.JButton();
        agregarButton = new javax.swing.JButton();
        eliminarButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        numTextField = new javax.swing.JTextField();
        nombreTextField = new javax.swing.JTextField();
        iepLabel = new javax.swing.JLabel();
        iepTextField = new javax.swing.JTextField();
        promedioLabel = new javax.swing.JLabel();
        promedioTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, partoList, partosTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idPadre.id}"));
        columnBinding.setColumnName("# Padre");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sexo}"));
        columnBinding.setColumnName("Sexo");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fecha}"));
        columnBinding.setColumnName("Fecha");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${esMuertePrematura}"));
        columnBinding.setColumnName("Muerte Prematura");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(partosTable);
        if (partosTable.getColumnModel().getColumnCount() > 0) {
            partosTable.getColumnModel().getColumn(0).setResizable(false);
            partosTable.getColumnModel().getColumn(0).setPreferredWidth(45);
            partosTable.getColumnModel().getColumn(1).setResizable(false);
            partosTable.getColumnModel().getColumn(2).setResizable(false);
            partosTable.getColumnModel().getColumn(3).setResizable(false);
        }

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

        eliminarButton.setText("Eliminar");
        eliminarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Vaca:");

        nombreTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreTextFieldActionPerformed(evt);
            }
        });

        iepLabel.setText("Último IEP (días)");

        iepTextField.setEditable(false);

        promedioLabel.setText("IEP Promedio (días)");

        promedioTextField.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(172, 172, 172))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(volverButton)
                                .addGap(157, 157, 157)
                                .addComponent(eliminarButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(iepLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(iepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(promedioLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(promedioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(agregarButton))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(numTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iepLabel)
                    .addComponent(iepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(promedioLabel)
                    .addComponent(promedioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(volverButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(eliminarButton)
                    .addComponent(agregarButton))
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButtonActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_volverButtonActionPerformed

    private void agregarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarButtonActionPerformed
        new PartosUI(bovino).setVisible(true);
    }//GEN-LAST:event_agregarButtonActionPerformed

    private void eliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarButtonActionPerformed
        if (partosTable.getSelectedRow() != -1) {
            int des = JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere eliminar este parto? La acción no es reversible.","Eliminar Servicio", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(des==0){
                DBManager dbm = new DBManager();
                Parto partoLocal = partoList.get(partosTable.getSelectedRow());
                Parto partoDB = dbm.findPartoById(partoLocal.getId());
                dbm.delete(partoDB);
                dbm.close();
                redrawPartosTable();
            }
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"Debe seleccionar un parto para eliminar.","Error de operación",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_eliminarButtonActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        redrawPartosTable();
    }//GEN-LAST:event_formWindowGainedFocus

    private void nombreTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreTextFieldActionPerformed

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
            java.util.logging.Logger.getLogger(PartosTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PartosTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PartosTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PartosTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PartosTableUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager TCUGanaderosPUEntityManager;
    private javax.swing.JButton agregarButton;
    private javax.swing.JButton eliminarButton;
    private javax.swing.JLabel iepLabel;
    private javax.swing.JTextField iepTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nombreTextField;
    private javax.swing.JTextField numTextField;
    private java.util.List<ucr.ac.cr.ecci.Models.Parto> partoList;
    private javax.persistence.Query partoQuery;
    private javax.swing.JTable partosTable;
    private javax.swing.JLabel promedioLabel;
    private javax.swing.JTextField promedioTextField;
    private javax.swing.JButton volverButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
