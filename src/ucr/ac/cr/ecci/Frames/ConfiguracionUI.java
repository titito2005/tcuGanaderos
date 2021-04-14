/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Frames;

import java.awt.Component;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import ucr.ac.cr.ecci.DBManager;
import static ucr.ac.cr.ecci.Frames.VacaTableUI.centreWindow;
import ucr.ac.cr.ecci.Models.Configuracion;
import ucr.ac.cr.ecci.Models.Finca;
import ucr.ac.cr.ecci.Models.ModoPreñez;
import ucr.ac.cr.ecci.Models.Raza;


/**
 *
 * @author afg
 */
public class ConfiguracionUI extends javax.swing.JFrame {
    
    Configuracion config;
    Finca finca;

    /**
     * Creates new form ConfiguracionesUI
     */
    public ConfiguracionUI() {
        centreWindow(this);
        initComponents();
        
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel();
        for (int i = 0; i < fincaList.size(); i++) {
            comboModel.addElement(fincaList.get(i).getNombre());
        }
        fincaComboBox.setModel(comboModel);
        
        if (!fincaList.isEmpty()){
            finca = fincaList.get(fincaComboBox.getSelectedIndex());
            config = (Configuracion) configuracionList.get(fincaComboBox.getSelectedIndex());
            alertaIEPSpinner.setValue(config.getMaxIep());
            alertaPalpacionSpinner.setValue(config.getAlertaPalpacion());
            alertaPalpacionSpinner1.setValue(config.getMaxAlertaPalpacion());
            alertaPartoSpinner.setValue(config.getAlertaParto());
            alertaDesteteSpinner.setValue(config.getAlertaDestete());
            alertaDesteteSpinner1.setValue(config.getMaxAlertaDestete());
            PASpinner.setValue(config.getLimitePa());
            SCSpinner.setValue(config.getLimiteSc());
        }       
    }
    
    private void redrawRazasTable() {
        TCUGanaderosPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("TCUGanaderosPU").createEntityManager();
        razaQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT r FROM Raza r");
        razaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : razaQuery.getResultList();
        
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, razaList, razasTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${raza}"));
        columnBinding.setColumnName("Razas");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(razasTable);
    }

    private void redrawModosTable() {
        TCUGanaderosPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("TCUGanaderosPU").createEntityManager();
        modoPreñezQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT m FROM ModoPreñez m");
        modoPreñezList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : modoPreñezQuery.getResultList();
        
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, modoPreñezList, modosTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${modo}"));
        columnBinding.setColumnName("Modos de Preñez");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(modosTable);
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
        razaQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT r FROM Raza r");
        razaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : razaQuery.getResultList();
        modoPreñezQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT m FROM ModoPreñez m");
        modoPreñezList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : modoPreñezQuery.getResultList();
        fincaQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT f FROM Finca f");
        fincaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : fincaQuery.getResultList();
        configuracionQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT c FROM Configuracion c");
        configuracionList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : configuracionQuery.getResultList();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        alertaIEPSpinner = new javax.swing.JSpinner();
        alertaPalpacionSpinner = new javax.swing.JSpinner();
        alertaPartoSpinner = new javax.swing.JSpinner();
        guardarGeneralButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        alertaDesteteSpinner = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        fincaComboBox = new javax.swing.JComboBox<>();
        volverButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        alertaPalpacionSpinner1 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        alertaDesteteSpinner1 = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        PASpinner = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        SCSpinner = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        razasTable = new javax.swing.JTable();
        agregarRazaTextField = new javax.swing.JTextField();
        agregarRazaButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        modosTable = new javax.swing.JTable();
        agregarModoTextField = new javax.swing.JTextField();
        agregarModoButton = new javax.swing.JButton();
        eliminarRazaButton = new javax.swing.JButton();
        eliminarModoButton = new javax.swing.JButton();
        volverButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Valor máximo aceptable del IEP para mostrar alertas: ");

        jLabel2.setText("Cantidad mínima de días luego del salto para mostrar alerta de palpación:");

        jLabel3.setText("Cantidad de días antes del parto para mostrar alertas:");

        guardarGeneralButton.setText("Guardar");
        guardarGeneralButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarGeneralButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Cantidad mínima de días luego del parto para mostrar alerta de destete:");

        jLabel4.setText("Finca:");

        fincaComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Finca) {
                    Finca f = (Finca)value;
                    setText(f.getNombre());
                }
                return this;
            }
        });
        fincaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fincaComboBoxActionPerformed(evt);
            }
        });

        volverButton.setText("Volver");
        volverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Cantidad máxima de días luego del salto para mostrar alerta de palpación:");

        jLabel7.setText("Cantidad máxima de días luego del parto para mostrar alerta de destete:");

        jLabel8.setText("Límite de días de periodo abierto");

        jLabel10.setText("Límite de servicios por concepción");

        SCSpinner.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 0.1d));
        SCSpinner.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                SCSpinnerPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(10, 10, 10)
                                .addComponent(fincaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(alertaPalpacionSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(alertaIEPSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(alertaPartoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(alertaDesteteSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(alertaPalpacionSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(alertaDesteteSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PASpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(volverButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(guardarGeneralButton)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4))
                    .addComponent(fincaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(alertaIEPSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(alertaPalpacionSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(alertaPalpacionSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(alertaPartoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(alertaDesteteSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(alertaDesteteSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PASpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(SCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardarGeneralButton)
                    .addComponent(volverButton))
                .addContainerGap())
        );

        jTabbedPane2.addTab("General", jPanel1);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, razaList, razasTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${raza}"));
        columnBinding.setColumnName("Razas");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(razasTable);

        agregarRazaButton.setText("Agregar");
        agregarRazaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarRazaButtonActionPerformed(evt);
            }
        });

        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, modoPreñezList, modosTable);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${modo}"));
        columnBinding.setColumnName("Modos de Preñez");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(modosTable);

        agregarModoButton.setText("Agregar");
        agregarModoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarModoButtonActionPerformed(evt);
            }
        });

        eliminarRazaButton.setText("Eliminar");
        eliminarRazaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarRazaButtonActionPerformed(evt);
            }
        });

        eliminarModoButton.setText("Eliminar");
        eliminarModoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarModoButtonActionPerformed(evt);
            }
        });

        volverButton1.setText("Volver");
        volverButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(eliminarRazaButton)
                .addGap(167, 167, 167)
                .addComponent(eliminarModoButton))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(agregarRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(agregarRazaButton)
                .addGap(50, 50, 50)
                .addComponent(agregarModoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(agregarModoButton))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(volverButton1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eliminarRazaButton)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(eliminarModoButton)))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(agregarRazaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(agregarRazaButton)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(agregarModoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(agregarModoButton)))
                .addGap(18, 18, 18)
                .addComponent(volverButton1)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Administración", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guardarGeneralButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarGeneralButtonActionPerformed
        //try {
            DBManager dbm = new DBManager();
            config.setAlertaPalpacion((int) alertaPalpacionSpinner.getValue());
            config.setMaxAlertaPalpacion((int) alertaPalpacionSpinner1.getValue());
            config.setAlertaParto((int) alertaPartoSpinner.getValue());
            config.setMaxIep((int) alertaIEPSpinner.getValue());
            config.setAlertaDestete((int) alertaDesteteSpinner.getValue());
            config.setMaxAlertaDestete((int) alertaDesteteSpinner1.getValue());
            
            config.setLimitePa((int) PASpinner.getValue());
            config.setLimiteSc ((double) SCSpinner.getValue());
            dbm.updateConfiguracion(config);
            dbm.close();
            setVisible(false);
            dispose();
        //} catch (Exception e) {
            //System.out.println("Hola");
        //}
    }//GEN-LAST:event_guardarGeneralButtonActionPerformed


    private void agregarRazaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarRazaButtonActionPerformed
        if (!agregarRazaTextField.getText().equals("")){
            DBManager dbm = new DBManager();
            Raza raza = new Raza();
            raza.setRaza(agregarRazaTextField.getText());
            dbm.insert(raza);
            dbm.close();  
            agregarRazaTextField.setText("");
            redrawRazasTable();  
        }  
    }//GEN-LAST:event_agregarRazaButtonActionPerformed

    private void agregarModoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarModoButtonActionPerformed
        if (!agregarModoTextField.getText().equals("")){
            DBManager dbm = new DBManager();
            ModoPreñez modo = new ModoPreñez();
            modo.setModo(agregarModoTextField.getText());
            dbm.insert(modo);
            dbm.close();  
            agregarModoTextField.setText("");
            redrawModosTable();  
        }  
    }//GEN-LAST:event_agregarModoButtonActionPerformed

    private void eliminarRazaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarRazaButtonActionPerformed
        if (razasTable.getSelectedRow() != -1) {
            try {
                DBManager dbm = new DBManager();
                Raza razaLocal = razaList.get(razasTable.getSelectedRow());
                Raza razaDB = dbm.findRazaById(razaLocal.getId());
                dbm.delete(razaDB);
                dbm.close();
                redrawRazasTable();     
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(new JFrame(),"Esta Raza está asociada a uno o más Bovinos.","Error de operación",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_eliminarRazaButtonActionPerformed

    private void eliminarModoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarModoButtonActionPerformed
        if (modosTable.getSelectedRow() != -1) {  
            try {
                DBManager dbm = new DBManager();
                ModoPreñez modoLocal = modoPreñezList.get(modosTable.getSelectedRow());
                ModoPreñez modoDB = dbm.findModoPreñezById(modoLocal.getId());
                dbm.delete(modoDB);
                dbm.close();
                redrawModosTable();
            }
            catch (Exception e) {
                    JOptionPane.showMessageDialog(new JFrame(),"Este Modo de Preñez está asociado a una o más Vacas.","Error de operación",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_eliminarModoButtonActionPerformed

    private void fincaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fincaComboBoxActionPerformed
        this.finca = fincaList.get(fincaComboBox.getSelectedIndex());
        config = (Configuracion) configuracionList.get(fincaComboBox.getSelectedIndex());
        alertaIEPSpinner.setValue(config.getMaxIep());
        alertaPalpacionSpinner.setValue(config.getAlertaPalpacion());
        alertaPalpacionSpinner1.setValue(config.getMaxAlertaPalpacion());
        alertaPartoSpinner.setValue(config.getAlertaParto());
        alertaDesteteSpinner.setValue(config.getAlertaDestete());
        PASpinner.setValue(config.getLimitePa());
        SCSpinner.setValue(config.getLimiteSc());
    }//GEN-LAST:event_fincaComboBoxActionPerformed

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButtonActionPerformed
        dispose();
    }//GEN-LAST:event_volverButtonActionPerformed

    private void volverButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_volverButton1ActionPerformed

    private void SCSpinnerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_SCSpinnerPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_SCSpinnerPropertyChange

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
            java.util.logging.Logger.getLogger(ConfiguracionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfiguracionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfiguracionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfiguracionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfiguracionUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner PASpinner;
    private javax.swing.JSpinner SCSpinner;
    private javax.persistence.EntityManager TCUGanaderosPUEntityManager;
    private javax.swing.JButton agregarModoButton;
    private javax.swing.JTextField agregarModoTextField;
    private javax.swing.JButton agregarRazaButton;
    private javax.swing.JTextField agregarRazaTextField;
    private javax.swing.JSpinner alertaDesteteSpinner;
    private javax.swing.JSpinner alertaDesteteSpinner1;
    private javax.swing.JSpinner alertaIEPSpinner;
    private javax.swing.JSpinner alertaPalpacionSpinner;
    private javax.swing.JSpinner alertaPalpacionSpinner1;
    private javax.swing.JSpinner alertaPartoSpinner;
    private java.util.List configuracionList;
    private javax.persistence.Query configuracionQuery;
    private javax.swing.JButton eliminarModoButton;
    private javax.swing.JButton eliminarRazaButton;
    private javax.swing.JComboBox<String> fincaComboBox;
    private java.util.List<ucr.ac.cr.ecci.Models.Finca> fincaList;
    private javax.persistence.Query fincaQuery;
    private javax.swing.JButton guardarGeneralButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private java.util.List<ucr.ac.cr.ecci.Models.ModoPreñez> modoPreñezList;
    private javax.persistence.Query modoPreñezQuery;
    private javax.swing.JTable modosTable;
    private java.util.List<ucr.ac.cr.ecci.Models.Raza> razaList;
    private javax.persistence.Query razaQuery;
    private javax.swing.JTable razasTable;
    private javax.swing.JButton volverButton;
    private javax.swing.JButton volverButton1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
