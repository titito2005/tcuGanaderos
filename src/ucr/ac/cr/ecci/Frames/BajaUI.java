/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Frames;

import ucr.ac.cr.ecci.Models.Bovino;
import ucr.ac.cr.ecci.Models.Baja;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ucr.ac.cr.ecci.DBManager;
import static ucr.ac.cr.ecci.Frames.VacaTableUI.centreWindow;

/**
 *
 * @author Alessandro
 */
public class BajaUI extends javax.swing.JFrame {
    private javax.persistence.EntityManager TCUGanaderosPUEntityManager;
    Bovino bovino;
    Baja baja;
    String vQuery;
    javax.persistence.Query vacasQuery;
    java.util.List vacasList;
    
    /**
     * Creates new form BajaUI
     */
    public BajaUI() {
        centreWindow(this);
        initComponents();
        TCUGanaderosPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("TCUGanaderosPU").createEntityManager();
        vQuery= "SELECT b FROM Bovino b WHERE b.esToro = 0";
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vQuery);
        vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();
    }
    
    public BajaUI(Bovino bovino) {
        centreWindow(this);
        initComponents();
        this.bovino = bovino;
        fillForm();
        if (this.bovino.getBaja() != null){
            bajaButton.setVisible(false);
        }
        
        TCUGanaderosPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("TCUGanaderosPU").createEntityManager();
        vQuery= "SELECT b FROM Bovino b WHERE b.esToro = 0";
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vQuery);
        vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();
    }
    
    // Llena los campos del formulario con los datos del Bovino y la fecha actual.
    private void fillForm() {
        idTextField.setText(Integer.toString(bovino.getId()));
        nombreTextField.setText(bovino.getNombre());
        fechaDatePicker.setDate(java.sql.Date.valueOf(LocalDate.now()));
        idTextField.setEditable(false);
        nombreTextField.setEditable(false);
    }
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();
        nombreTextField = new javax.swing.JTextField();
        fechaDatePicker = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane1 = new javax.swing.JScrollPane();
        causaTextArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        bajaButton = new javax.swing.JButton();
        eliminarButton = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel2.setText("# Trazable");

        jLabel3.setText("Nombre");

        jLabel4.setText("Fecha de baja");

        jLabel5.setText("Causa de baja");

        fechaDatePicker.setFormats(new String[]{"dd/MM/yyyy"});

        causaTextArea.setColumns(20);
        causaTextArea.setRows(5);
        jScrollPane1.setViewportView(causaTextArea);

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        bajaButton.setText("Dar de baja");
        bajaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bajaButtonActionPerformed(evt);
            }
        });

        eliminarButton.setText("Eliminar");
        eliminarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarButtonActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreTextField)
                            .addComponent(fechaDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(eliminarButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bajaButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fechaDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(bajaButton)
                    .addComponent(eliminarButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
        dispose();   
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bajaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bajaButtonActionPerformed
        int des = JOptionPane.showConfirmDialog(null, "Está seguro que quiere dar de baja a este Bovino? La acción no es reversible.","Dar de baja a Bovino", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        boolean guardar=true;
        if (des == 0){
            DBManager dbm = new DBManager();
            baja = new Baja(bovino.getId());
            if (!causaTextArea.getText().equals("")){
                baja.setCausa(causaTextArea.getText());
            }
            
            if (fechaDatePicker.getDate()!=null){//FECHA
                Calendar cal = Calendar.getInstance();
                Date date= fechaDatePicker.getDate();
                Date date2= cal.getTime();
                if(date.before(date2)){
                    baja.setFecha(fechaDatePicker.getDate());
                }else{
                    JOptionPane.showMessageDialog(new JFrame(),"La fecha de baja no es válida, debe agregar una fecha anterior o igual a la actual.","Error de operación",JOptionPane.ERROR_MESSAGE);
                    guardar=false;
                }
            }else{
                JOptionPane.showMessageDialog(new JFrame(),"Debe agregar una fecha de baja.","Error de operación",JOptionPane.ERROR_MESSAGE);
                guardar=false;
            }
            
            if(guardar){
            dbm.insert(baja);
            dbm.close();  
            setVisible(false);
            dispose(); 
            }
        }
    }//GEN-LAST:event_bajaButtonActionPerformed

    private void eliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarButtonActionPerformed
        int des = JOptionPane.showConfirmDialog(null, "Está seguro que quiere eliminar este Bovino? La acción no es reversible, y el Bovino no se tomará en cuenta para reportes.","Eliminar Bovino", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (des == 0){
            DBManager dbm = new DBManager();
            
            Bovino bovinoDB = dbm.findBovinoById(bovino.getId());
            boolean continuar= true;
            for(int i=0; i<vacasList.size() && continuar==true; i++){
                Bovino aux= (Bovino)vacasList.get(i);

                Bovino padre= aux.getIdPadre();
                if(padre!=null && continuar==true){
                    int idPadre= padre.getId();
                    if(idPadre == bovinoDB.getId()){
                        JOptionPane.showMessageDialog(new JFrame(),"Error, este toro está asignado como padre en la lista de vacas, debe modificar el dato en la vaca hija para eliminar el toro.","Error de operación",JOptionPane.ERROR_MESSAGE);
                        continuar=false;
                    }
                }
                
                Bovino madre= aux.getIdMadre();
                if(madre!=null && continuar== true){
                    int idMadre= madre.getId();
                    if(idMadre == bovinoDB.getId()){
                        JOptionPane.showMessageDialog(new JFrame(),"Error, esta vaca está asignada como madre en la lista de vacas, debe modificar el dato en la vaca hija para eliminar la vaca.","Error de operación",JOptionPane.ERROR_MESSAGE);
                        continuar=false;
                    }
                }
            }
            if(continuar==true){
                dbm.delete(bovinoDB);
                dbm.close();
                dispose();           
            }
        }
    }//GEN-LAST:event_eliminarButtonActionPerformed

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
            java.util.logging.Logger.getLogger(BajaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BajaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BajaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BajaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BajaUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bajaButton;
    private javax.swing.JTextArea causaTextArea;
    private javax.swing.JButton eliminarButton;
    private org.jdesktop.swingx.JXDatePicker fechaDatePicker;
    private javax.swing.JTextField idTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nombreTextField;
    // End of variables declaration//GEN-END:variables
}