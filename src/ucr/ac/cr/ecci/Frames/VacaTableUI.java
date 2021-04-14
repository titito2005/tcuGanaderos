/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import ucr.ac.cr.ecci.Models.Bovino;
import ucr.ac.cr.ecci.Models.Finca;

/**
 *
 * @author afg
 */
public final class VacaTableUI extends javax.swing.JFrame {
    
    Finca finca;
    String cond_finca = "";
    boolean showingBajas;

    /**
     * Creates new form VacaTableUI
     */
    public VacaTableUI() {
        centreWindow(this);
        
        initComponents();
        
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel();
        comboModel.addElement("Todas");
        for (int i = 0; i < fincaList.size(); i++) {
            comboModel.addElement(fincaList.get(i).getNombre());
        }
        fincaComboBox.setModel(comboModel);
        
        vacaTable.setColumnSelectionAllowed(false);
        rowSorter = new TableRowSorter<>(vacaTable.getModel());
        vacaTable.setRowSorter(rowSorter);
        


    }
    
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 4);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 4);
        frame.setLocation(x, y);
    }
    
    public void redrawVacasTable(String query) {
        vacasQuery = TCUGanaderosPUEntityManager.createQuery(query);
        vacasQueryList = vacasQuery.getResultList();
        vacaTable.setColumnSelectionAllowed(false);
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();
        org.jdesktop.swingbinding.JTableBinding TableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vacasQueryList, vacaTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("# Trazable");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechaNacimiento}"));
        columnBinding.setColumnName("Fecha Nacimiento");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idRaza.raza}"));
        columnBinding.setColumnName("Raza");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${peso}"));
        columnBinding.setColumnName("Peso");
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vaca.idEstadoDesarrollo.estado}"));
        columnBinding.setColumnName("Estado desarrollo");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${caracteristicas}"));
        columnBinding.setColumnName("Caracteristicas");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vaca.idModoPreñez.modo}"));
        columnBinding.setColumnName("Modo preñez");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idPadre.id}"));
        columnBinding.setColumnName("# Padre");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idMadre.id}"));
        columnBinding.setColumnName("# Madre");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = TableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${esBaja}"));
        columnBinding.setColumnName("Baja");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(TableBinding);
        TableBinding.bind();
        vacaTable.addPropertyChangeListener(this::vacaTablePropertyChange);
        jScrollPane1.setViewportView(vacaTable);
        vacaTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (vacaTable.getColumnModel().getColumnCount() > 0) {
            vacaTable.getColumnModel().getColumn(0).setResizable(false);
            vacaTable.getColumnModel().getColumn(1).setResizable(false);
            vacaTable.getColumnModel().getColumn(2).setResizable(false);
            vacaTable.getColumnModel().getColumn(3).setResizable(false);
            vacaTable.getColumnModel().getColumn(4).setResizable(false);
            vacaTable.getColumnModel().getColumn(5).setResizable(false);
            vacaTable.getColumnModel().getColumn(6).setResizable(false);
            vacaTable.getColumnModel().getColumn(7).setResizable(false);
            vacaTable.getColumnModel().getColumn(8).setResizable(false);
            vacaTable.getColumnModel().getColumn(9).setResizable(false);
            vacaTable.getColumnModel().getColumn(10).setResizable(false);
        }
        jScrollPane1.repaint();
        showingBajas = false;
        bajarButton.setVisible(true);
        rowSorter = new TableRowSorter<>(vacaTable.getModel());
        vacaTable.setRowSorter(rowSorter);
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
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL AND b.esToro = 0 " + cond_finca);
        vacasQueryList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();
        vacasBajasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NOT NULL AND b.esToro = 0");
        vacasBajasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasBajasQuery.getResultList();
        fincaQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT f FROM Finca f");
        fincaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : fincaQuery.getResultList();
        jScrollPane1 = new javax.swing.JScrollPane();
        vacaTable = new javax.swing.JTable();
        volverButton = new javax.swing.JButton();
        detallesButton = new javax.swing.JButton();
        agregarButton = new javax.swing.JButton();
        buscarTextField = new javax.swing.JTextField();
        buscarButton = new javax.swing.JButton();
        bajasCheckBox = new javax.swing.JCheckBox();
        bajarButton = new javax.swing.JButton();
        partosButton = new javax.swing.JButton();
        celosButton = new javax.swing.JButton();
        destetesButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fincaComboBox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        menuMenuBar = new javax.swing.JMenuBar();
        ayudaMenu = new javax.swing.JMenu();
        acercaMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        vacaTable.setCellSelectionEnabled(true);
        vacaTable.setOpaque(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, vacasQueryList, vacaTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("# Trazable");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechaNacimiento}"));
        columnBinding.setColumnName("Fecha Nacimiento");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idRaza.raza}"));
        columnBinding.setColumnName("Raza");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${peso}"));
        columnBinding.setColumnName("Peso");
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vaca.idEstadoDesarrollo.estado}"));
        columnBinding.setColumnName("Estado desarrollo");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${caracteristicas}"));
        columnBinding.setColumnName("Caracteristicas");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vaca.idModoPreñez.modo}"));
        columnBinding.setColumnName("Modo preñez");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idPadre.id}"));
        columnBinding.setColumnName("# Padre");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idMadre.id}"));
        columnBinding.setColumnName("# Madre");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${esBaja}"));
        columnBinding.setColumnName("Baja");
        columnBinding.setColumnClass(Boolean.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        vacaTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                vacaTablePropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(vacaTable);
        vacaTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (vacaTable.getColumnModel().getColumnCount() > 0) {
            vacaTable.getColumnModel().getColumn(0).setResizable(false);
            vacaTable.getColumnModel().getColumn(1).setResizable(false);
            vacaTable.getColumnModel().getColumn(2).setResizable(false);
            vacaTable.getColumnModel().getColumn(3).setResizable(false);
            vacaTable.getColumnModel().getColumn(4).setResizable(false);
            vacaTable.getColumnModel().getColumn(5).setResizable(false);
            vacaTable.getColumnModel().getColumn(6).setResizable(false);
            vacaTable.getColumnModel().getColumn(7).setResizable(false);
            vacaTable.getColumnModel().getColumn(8).setResizable(false);
            vacaTable.getColumnModel().getColumn(9).setResizable(false);
            vacaTable.getColumnModel().getColumn(10).setResizable(false);
        }

        volverButton.setText("Volver");
        volverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverButtonActionPerformed(evt);
            }
        });

        detallesButton.setText("Detalles");
        detallesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detallesButtonActionPerformed(evt);
            }
        });

        agregarButton.setText("Agregar");
        agregarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarButtonActionPerformed(evt);
            }
        });

        buscarTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarTextFieldActionPerformed(evt);
            }
        });

        buscarButton.setText("Buscar");
        buscarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarButtonActionPerformed(evt);
            }
        });

        bajasCheckBox.setText("Ver bajas");
        bajasCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bajasCheckBoxActionPerformed(evt);
            }
        });

        bajarButton.setText("Dar de baja");
        bajarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bajarButtonActionPerformed(evt);
            }
        });

        partosButton.setText("Partos");
        partosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partosButtonActionPerformed(evt);
            }
        });

        celosButton.setText("Celos");
        celosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celosButtonActionPerformed(evt);
            }
        });

        destetesButton.setText("Destetes");
        destetesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destetesButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Finca:");

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

        jButton1.setText("Servicios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ayudaMenu.setText("Ayuda");

        acercaMenuItem.setText("Acerca de la aplicación");
        ayudaMenu.add(acercaMenuItem);

        menuMenuBar.add(ayudaMenu);

        setJMenuBar(menuMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buscarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buscarButton)
                        .addGap(14, 14, 14)
                        .addComponent(bajasCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fincaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(volverButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(detallesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(celosButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(destetesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(partosButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(171, 171, 171)
                        .addComponent(bajarButton)
                        .addGap(18, 18, 18)
                        .addComponent(agregarButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buscarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buscarButton)
                        .addComponent(bajasCheckBox))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(fincaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(volverButton)
                    .addComponent(detallesButton)
                    .addComponent(agregarButton)
                    .addComponent(bajarButton)
                    .addComponent(partosButton)
                    .addComponent(celosButton)
                    .addComponent(destetesButton)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButtonActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_volverButtonActionPerformed

    private void detallesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detallesButtonActionPerformed
        if (vacaTable.getSelectedRow() != -1) {
            VacaUI vaca= new VacaUI((Bovino) vacasQueryList.get(vacaTable.getSelectedRow()));
            vaca.setLocationRelativeTo(null);
            vaca.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"Debe seleccionar una vaca.","Error de operación",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_detallesButtonActionPerformed

    private void agregarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarButtonActionPerformed
        VacaUI vaca = new VacaUI();
        vaca.setLocationRelativeTo(null);
        vaca.setVisible(true);
    }//GEN-LAST:event_agregarButtonActionPerformed

    private void vacaTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_vacaTablePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_vacaTablePropertyChange

    private void buscarTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarTextFieldActionPerformed

    private void bajarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bajarButtonActionPerformed
        if (vacaTable.getSelectedRow() != -1) {
            BajaUI baja = new BajaUI((Bovino) vacasQueryList.get(vacaTable.getSelectedRow()));
            baja.setLocationRelativeTo(null);
            baja.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"Debe seleccionar una vaca.","Error de operación",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bajarButtonActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained

    }//GEN-LAST:event_formFocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if (bajasCheckBox.isSelected()){
            redrawVacasTable("SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NOT NULL AND b.esToro = 0 " + cond_finca);
        }
        else {
            redrawVacasTable("SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL AND b.esToro = 0 " + cond_finca);
        }
    }//GEN-LAST:event_formWindowGainedFocus

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void bajasCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bajasCheckBoxActionPerformed
        if (bajasCheckBox.isSelected()){
            redrawVacasTable("SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NOT NULL AND b.esToro = 0 " + cond_finca);
        }
        else {
            redrawVacasTable("SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL AND b.esToro = 0 " + cond_finca);
        }
    }//GEN-LAST:event_bajasCheckBoxActionPerformed

    private void partosButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partosButtonActionPerformed
        if (vacaTable.getSelectedRow() != -1) {
            new PartosTableUI((Bovino) vacasQueryList.get(vacaTable.getSelectedRow())).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"Debe seleccionar una vaca","Error de operación",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_partosButtonActionPerformed

    private void buscarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarButtonActionPerformed
        String text = buscarTextField.getText();
        if (text.trim().length() == 0) {
           rowSorter.setRowFilter(null);
        } else {
           rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }//GEN-LAST:event_buscarButtonActionPerformed

    private void celosButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celosButtonActionPerformed
        if (vacaTable.getSelectedRow() != -1) {
            new CelosUI((Bovino) vacasQueryList.get(vacaTable.getSelectedRow())).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"Debe seleccionar una vaca","Error de operación",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_celosButtonActionPerformed

    private void destetesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destetesButtonActionPerformed
        if (vacaTable.getSelectedRow() != -1) {
            new DestetesUI((Bovino) vacasQueryList.get(vacaTable.getSelectedRow())).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"Debe seleccionar una vaca","Error de operación",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_destetesButtonActionPerformed

    private void fincaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fincaComboBoxActionPerformed
        if (!fincaComboBox.getSelectedItem().toString().equals("Todas")) {
            this.finca = fincaList.get(fincaComboBox.getSelectedIndex()-1);
            cond_finca = "AND b.idFinca.id = " + Integer.toString(finca.getId()) ;
        } else {
            cond_finca = "";
        }
        if (bajasCheckBox.isSelected()){
            redrawVacasTable("SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NOT NULL AND b.esToro = 0 " + cond_finca);
        }
        else {
            redrawVacasTable("SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL AND b.esToro = 0 " + cond_finca);
        }
    }//GEN-LAST:event_fincaComboBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (vacaTable.getSelectedRow() != -1) {
           new ServiciosTableUI((Bovino) vacasQueryList.get(vacaTable.getSelectedRow())).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"Debe seleccionar una vaca","Error de operación",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(VacaTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VacaTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VacaTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VacaTableUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VacaTableUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager TCUGanaderosPUEntityManager;
    private javax.swing.JMenuItem acercaMenuItem;
    private javax.swing.JButton agregarButton;
    private javax.swing.JMenu ayudaMenu;
    private javax.swing.JButton bajarButton;
    private javax.swing.JCheckBox bajasCheckBox;
    private javax.swing.JButton buscarButton;
    private javax.swing.JTextField buscarTextField;
    private javax.swing.JButton celosButton;
    private javax.swing.JButton destetesButton;
    private javax.swing.JButton detallesButton;
    private javax.swing.JComboBox<String> fincaComboBox;
    private java.util.List<ucr.ac.cr.ecci.Models.Finca> fincaList;
    private javax.persistence.Query fincaQuery;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menuMenuBar;
    private javax.swing.JButton partosButton;
    private javax.swing.JTable vacaTable;
    private java.util.List vacasBajasList;
    private javax.persistence.Query vacasBajasQuery;
    private javax.persistence.Query vacasQuery;
    private java.util.List vacasQueryList;
    private javax.swing.JButton volverButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private TableRowSorter<TableModel> rowSorter;
//    private final TableRowSorter<TableModel> rowSorterBajas;
}
