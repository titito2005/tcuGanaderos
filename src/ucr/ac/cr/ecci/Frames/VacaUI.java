/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Frames;

import ucr.ac.cr.ecci.Models.Vaca;
import ucr.ac.cr.ecci.Models.Finca;
import ucr.ac.cr.ecci.Models.EstadoDesarrollo;
import ucr.ac.cr.ecci.Models.Raza;
import ucr.ac.cr.ecci.Models.ModoPreñez;
import ucr.ac.cr.ecci.Models.Bovino;
import java.awt.Component;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.RollbackException;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import ucr.ac.cr.ecci.DBManager;
import static ucr.ac.cr.ecci.Frames.VacaTableUI.centreWindow;

/**
 *
 * @author afg
 */
public class VacaUI extends javax.swing.JFrame {

    Bovino b;
    // Cuando el form se abre por detalles, se enciende el flag.
    boolean detalles = false;
    String vQuery;
    String tQuery;
    javax.persistence.Query vacasQuery;
    javax.persistence.Query torosQuery;
    
    java.util.List vacasList;
    java.util.List torosList;
    
    /**
     * Creates new form VacaUI
     */
    public VacaUI() {
        centreWindow(this);
        initComponents();
        vQuery= "SELECT b FROM Bovino b WHERE b.esToro = 0";
        tQuery= "SELECT b FROM Bovino b WHERE b.esToro = 1";
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vQuery);
        torosQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(tQuery);
        vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();
        torosList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : torosQuery.getResultList();
        
        madreComboBox.addItem("Indefinido");
        for(int i=0; i< vacasList.size(); i++){
            Bovino bo = (Bovino)vacasList.get(i);
            String dato= bo.getId() +" "+ bo.getNombre();
            madreComboBox.addItem(dato);
        }
        
        padreComboBox.addItem("Indefinido");
        for(int i=0; i< torosList.size(); i++){
            Bovino bo = (Bovino)torosList.get(i);
            String dato= bo.getId() +" "+ bo.getNombre();
            padreComboBox.addItem(dato);
        }
        editarCheckBox.setVisible(false);
        edadLabel.setVisible(false);
        edadTextField.setVisible(false);
    }
    
    public VacaUI(Bovino b) {
        centreWindow(this);
        initComponents();
        vQuery= "SELECT b FROM Bovino b WHERE b.esToro = 0";
        tQuery= "SELECT b FROM Bovino b WHERE b.esToro = 1";
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vQuery);
        torosQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(tQuery);
        vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();
        torosList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : torosQuery.getResultList();
        
        madreComboBox.addItem("Indefinido");
        for(int i=0; i< vacasList.size(); i++){
            Bovino bo = (Bovino)vacasList.get(i);
            String dato= bo.getId() +" "+ bo.getNombre();
            madreComboBox.addItem(dato);
        }
        
        padreComboBox.addItem("Indefinido");
        for(int i=0; i< torosList.size(); i++){
            Bovino bo = (Bovino)torosList.get(i);
            String dato= bo.getId() +" "+ bo.getNombre();
            padreComboBox.addItem(dato);
        }
        
        this.b = b;
        idFormattedTextField.setText(Integer.toString(b.getId()));
        nombreTextField.setText(b.getNombre()); 
        if (b.getFechaNacimiento() != null){
            fechaDatePicker.setDate(b.getFechaNacimiento());
            int edad = getEdad(b.getFechaNacimiento());
            edadTextField.setText(Integer.toString(edad));
        }
        if (b.getPeso() != null){
            pesoFormattedTextField.setText(Double.toString(b.getPeso()));
        }
        fincaComboBox.setSelectedItem(b.getIdFinca());
        razaComboBox.setSelectedItem(b.getIdRaza());
        estadoComboBox.setSelectedItem(b.getVaca().getIdEstadoDesarrollo());
        
        if(b.getIdMadre()!=null){
            Bovino madre= b.getIdMadre();
            String datoMadre= madre.getId() +" "+ madre.getNombre();
            madreComboBox.setSelectedItem(datoMadre);
        }else{
            madreComboBox.setSelectedItem("Indefinido");
        }
        
        if(b.getIdPadre()!=null){
            Bovino padre= b.getIdPadre();
            String datoPadre= padre.getId() +" "+ padre.getNombre();
            padreComboBox.setSelectedItem(datoPadre);
        }else{
            padreComboBox.setSelectedItem("Indefinido");
        }

        
        caracteristicasTextArea.setText(b.getCaracteristicas());
        
        setEnabledFalse();
        guardarButton.setVisible(false);
        
        detalles = true;
    }
    
    private void setEnabledFalse() {
        fincaComboBox.setEnabled(false);
        idFormattedTextField.setEditable(false);
        nombreTextField.setEditable(false);
        fechaDatePicker.setEditable(false);
        pesoFormattedTextField.setEditable(false);
        razaComboBox.setEnabled(false);
        estadoComboBox.setEnabled(false);
        madreComboBox.setEnabled(false);
        padreComboBox.setEnabled(false);
        caracteristicasTextArea.setEditable(false);
    }
    
    public int getEdad(Date fecha){
        Calendar present = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.setTime(fecha);

        int years = 0;

        while (past.before(present)) {
            past.add(Calendar.YEAR, 1);
            if (past.before(present)) {
                years++;
            }
        } 
        return years;
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
        estadoDesarrolloQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT e FROM EstadoDesarrollo e");
        estadoDesarrolloList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : estadoDesarrolloQuery.getResultList();
        fincaQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT f FROM Finca f");
        fincaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : fincaQuery.getResultList();
        bovinoQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT b FROM Bovino b WHERE b.esToro = 1");
        bovinoList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bovinoQuery.getResultList();
        bovinoQuery1 = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT b FROM Bovino b WHERE b.esToro = 1");
        bovinoList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bovinoQuery1.getResultList();
        bovinoQuery2 = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT b FROM Bovino b WHERE b.esToro = 0");
        bovinoList2 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bovinoQuery2.getResultList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombreTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        caracteristicasTextArea = new javax.swing.JTextArea();
        editarCheckBox = new javax.swing.JCheckBox();
        volverButton = new javax.swing.JButton();
        guardarButton = new javax.swing.JButton();
        idFormattedTextField = new javax.swing.JFormattedTextField();
        pesoFormattedTextField = new javax.swing.JFormattedTextField();
        razaComboBox = new javax.swing.JComboBox<>();
        estadoComboBox = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        fincaComboBox = new javax.swing.JComboBox<>();
        fechaDatePicker = new org.jdesktop.swingx.JXDatePicker();
        annosLabel = new javax.swing.JLabel();
        edadLabel = new javax.swing.JLabel();
        edadTextField = new javax.swing.JTextField();
        padreComboBox = new javax.swing.JComboBox<>();
        madreComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("# Trazable: ");

        jLabel2.setText("Nombre: ");

        jLabel3.setText("Fecha de nacimiento: ");

        jLabel4.setText("Raza: ");

        jLabel5.setText("Peso: ");

        jLabel7.setText("Estado de desarrollo: ");

        jLabel8.setText("Madre: ");

        jLabel9.setText("kg");

        jLabel10.setText("Padre: ");

        jLabel11.setText("Características");

        caracteristicasTextArea.setColumns(20);
        caracteristicasTextArea.setRows(5);
        jScrollPane1.setViewportView(caracteristicasTextArea);

        editarCheckBox.setText("Editar");
        editarCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                editarCheckBoxStateChanged(evt);
            }
        });
        editarCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarCheckBoxActionPerformed(evt);
            }
        });
        editarCheckBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                editarCheckBoxPropertyChange(evt);
            }
        });

        volverButton.setText("Volver");
        volverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverButtonActionPerformed(evt);
            }
        });

        guardarButton.setText("Guardar");
        guardarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarButtonActionPerformed(evt);
            }
        });

        idFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        idFormattedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idFormattedTextFieldActionPerformed(evt);
            }
        });

        pesoFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###.###"))));
        pesoFormattedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesoFormattedTextFieldActionPerformed(evt);
            }
        });

        razaComboBox.setSelectedItem(razaList);
        razaComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Raza) {
                    Raza r = (Raza)value;
                    setText(r.getRaza());
                }
                return this;
            }
        });

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, razaList, razaComboBox);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, razaComboBox, org.jdesktop.beansbinding.ObjectProperty.create(), razaComboBox, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        razaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                razaComboBoxActionPerformed(evt);
            }
        });

        estadoComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof EstadoDesarrollo) {
                    EstadoDesarrollo ed = (EstadoDesarrollo)value;
                    setText(ed.getEstado());
                }
                return this;
            }
        });

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, estadoDesarrolloList, estadoComboBox);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, estadoDesarrolloList, org.jdesktop.beansbinding.ObjectProperty.create(), estadoComboBox, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        jLabel12.setText("Finca");

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

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, fincaList, fincaComboBox);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, fincaComboBox, org.jdesktop.beansbinding.ObjectProperty.create(), fincaComboBox, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        fechaDatePicker.setFormats(new String[]{"dd/MM/yyyy"});
        fechaDatePicker.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fechaDatePickerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fechaDatePickerFocusLost(evt);
            }
        });
        fechaDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaDatePickerActionPerformed(evt);
            }
        });
        fechaDatePicker.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fechaDatePickerPropertyChange(evt);
            }
        });

        edadLabel.setText("Edad:");

        edadTextField.setEditable(false);

        padreComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Bovino) {
                    Bovino b = (Bovino)value;
                    if(b.getEsToro()==1){
                        String id = Integer.toString(b.getId());
                        setText(id + "  " + b.getNombre());
                    }
                }
                return this;
            }
        });
        padreComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                padreComboBoxActionPerformed(evt);
            }
        });

        madreComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Bovino) {
                    Bovino b = (Bovino)value;
                    if(b.getEsToro()==0){
                        String id = Integer.toString(b.getId());
                        setText(id + "  " + b.getNombre());
                    }
                }
                return this;
            }
        });
        madreComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                madreComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                                .addComponent(edadLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nombreTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(annosLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(madreComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(padreComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editarCheckBox, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(volverButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(guardarButton))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(estadoComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(56, 56, 56))
                                .addComponent(jScrollPane1)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(pesoFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel9)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel4)))
                                    .addGap(4, 4, 4)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fechaDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(razaComboBox, 0, 249, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(fincaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(fincaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editarCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edadLabel)
                    .addComponent(edadTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fechaDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(annosLabel)
                        .addGap(329, 329, 329))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(pesoFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel9))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(razaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(estadoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(padreComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(madreComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(volverButton)
                            .addComponent(guardarButton))
                        .addContainerGap(22, Short.MAX_VALUE))))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editarCheckBoxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_editarCheckBoxPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_editarCheckBoxPropertyChange

    private void editarCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_editarCheckBoxStateChanged
        if (editarCheckBox.isSelected()){
            fincaComboBox.setEnabled(true);
            nombreTextField.setEditable(true);
            fechaDatePicker.setEditable(true);
            pesoFormattedTextField.setEditable(true);
            razaComboBox.setEnabled(true);
            estadoComboBox.setEnabled(true);
            madreComboBox.setEnabled(true);
            padreComboBox.setEnabled(true);
            caracteristicasTextArea.setEditable(true);
            guardarButton.setVisible(true);
            
        } else if (!editarCheckBox.isSelected()){
            setEnabledFalse();
            guardarButton.setVisible(false);
        }
    }//GEN-LAST:event_editarCheckBoxStateChanged

    private void guardarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarButtonActionPerformed
        DBManager dbm = new DBManager();
        Calendar present = Calendar.getInstance();
        if (!idFormattedTextField.getText().equals("") ){
 
            if(fechaDatePicker.getDate()!=null ){
                Calendar cal = Calendar.getInstance();
                Date date= fechaDatePicker.getDate();
                Date date2= cal.getTime();
                if(date.before(date2)){
                    if (detalles) {
                        b.setNombre(nombreTextField.getText());
                        b.setIdFinca((Finca) fincaComboBox.getSelectedItem());
                        b.setFechaNacimiento(fechaDatePicker.getDate());
                        if (!pesoFormattedTextField.getText().equals("")){
                            b.setPeso(Double.parseDouble(pesoFormattedTextField.getText()));
                        }
                        b.setIdRaza((Raza) razaComboBox.getSelectedItem());
                        b.getVaca().setIdEstadoDesarrollo((EstadoDesarrollo) estadoComboBox.getSelectedItem());

                        String madre = (String)madreComboBox.getSelectedItem();
                        String padre = (String)padreComboBox.getSelectedItem();

                        if(madre!="Indefinido"){
                            String[] splitStr = madre.split(" ");
                            int idMadre = Integer.parseInt(splitStr[0]);
                            Bovino vacaMadre = dbm.findBovinoById(idMadre);
                            if(vacaMadre!=null){
                                b.setIdMadre(vacaMadre);
                            }else{
                                vacaMadre=null;
                                b.setIdMadre(vacaMadre);
                            }
                        }else{
                            Bovino vacaMadre=null;
                            b.setIdMadre(vacaMadre);
                        }

                        if(padre!="Indefinido"){
                            String[] splitStr = padre.split(" ");
                            int idPadre = Integer.parseInt(splitStr[0]);
                            Bovino vacaPadre = dbm.findBovinoById(idPadre);
                            if(vacaPadre!=null){
                                b.setIdPadre(vacaPadre);
                            }else{
                                vacaPadre=null;
                                b.setIdPadre(vacaPadre);
                            }
                        }else{
                            Bovino vacaPadre=null;
                            b.setIdPadre(vacaPadre);
                        }

                        b.setCaracteristicas(caracteristicasTextArea.getText());
                        b.setEsToro((short)0);
                        dbm.updateBovino(b);
                        dbm.updateVaca(b.getVaca());
                        dbm.close();  
                        dispose();  
                    } else {
                        int id = Integer.parseInt(idFormattedTextField.getText());
                        Bovino v = new Bovino(id);
                        v.setNombre(nombreTextField.getText());
                        v.setIdFinca((Finca) fincaComboBox.getSelectedItem());
                        v.setFechaNacimiento(fechaDatePicker.getDate());

                            if (!pesoFormattedTextField.getText().equals("")){
                                v.setPeso(Double.parseDouble(pesoFormattedTextField.getText()));
                            }
                            v.setIdRaza((Raza) razaComboBox.getSelectedItem());
                            Vaca vaca = new Vaca(id);
                            vaca.setIdEstadoDesarrollo((EstadoDesarrollo) estadoComboBox.getSelectedItem());
                            v.setVaca(vaca);


                            String madre = (String)madreComboBox.getSelectedItem();
                            String padre = (String)padreComboBox.getSelectedItem();

                            if(madre!="Indefinido"){
                                String[] splitStr = madre.split(" ");
                                int idMadre = Integer.parseInt(splitStr[0]);
                                Bovino vacaMadre = dbm.findBovinoById(idMadre);
                                if(vacaMadre!=null){
                                    v.setIdMadre(vacaMadre);
                                }else{
                                    vacaMadre=null;
                                    v.setIdMadre(vacaMadre);
                                }
                            }else{
                                Bovino vacaMadre=null;
                                v.setIdMadre(vacaMadre);
                            }

                            if(padre!="Indefinido"){
                                String[] splitStr = padre.split(" ");
                                int idPadre = Integer.parseInt(splitStr[0]);
                                Bovino vacaPadre = dbm.findBovinoById(idPadre);
                                if(vacaPadre!=null){
                                    v.setIdPadre(vacaPadre);
                                }else{
                                    vacaPadre=null;
                                    v.setIdPadre(vacaPadre);
                                }
                            }else{
                                Bovino vacaPadre=null;
                                v.setIdPadre(vacaPadre);
                            }
                            v.setCaracteristicas(caracteristicasTextArea.getText());
                            v.setEsToro((short)0);
                        try {
                            dbm.insert(v);
                            dbm.insert(vaca);
                            dbm.close();  
                            setVisible(false);
                            dispose();  
                        } catch (RollbackException ex) {
                            JOptionPane.showMessageDialog(new JFrame(),"El numero trazable ya pertenece a otro bovino.","Error de operación",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(new JFrame(),"La fecha ingresada no es válida, debe ser anterior o igual a la fecha actual.","Error de operación",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(new JFrame(),"Debe incluir la fecha de nacimiento.","Error de operación",JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"Debe incluir al menos el número trazable.","Error de operación",JOptionPane.ERROR_MESSAGE);
        }  
    }//GEN-LAST:event_guardarButtonActionPerformed

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButtonActionPerformed
            setVisible(false);
            dispose();  
    }//GEN-LAST:event_volverButtonActionPerformed

    private void editarCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editarCheckBoxActionPerformed

    private void fechaDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaDatePickerActionPerformed
        
    }//GEN-LAST:event_fechaDatePickerActionPerformed

    private void fechaDatePickerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fechaDatePickerPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaDatePickerPropertyChange

    private void fechaDatePickerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaDatePickerFocusGained
        int edad = getEdad(fechaDatePicker.getDate());
        edadTextField.setText(Integer.toString(edad));
        edadTextField.repaint();
    }//GEN-LAST:event_fechaDatePickerFocusGained

    private void fechaDatePickerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaDatePickerFocusLost
        int edad = getEdad(fechaDatePicker.getDate());
        edadTextField.setText(Integer.toString(edad));
        edadTextField.repaint();
    }//GEN-LAST:event_fechaDatePickerFocusLost

    private void razaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_razaComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_razaComboBoxActionPerformed

    private void pesoFormattedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesoFormattedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pesoFormattedTextFieldActionPerformed

    private void idFormattedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idFormattedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idFormattedTextFieldActionPerformed

    private void padreComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_padreComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_padreComboBoxActionPerformed

    private void madreComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_madreComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_madreComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(VacaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VacaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VacaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VacaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VacaUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager TCUGanaderosPUEntityManager;
    private javax.swing.JLabel annosLabel;
    private java.util.List<ucr.ac.cr.ecci.Models.Bovino> bovinoList;
    private java.util.List<ucr.ac.cr.ecci.Models.Bovino> bovinoList1;
    private java.util.List<ucr.ac.cr.ecci.Models.Bovino> bovinoList2;
    private javax.persistence.Query bovinoQuery;
    private javax.persistence.Query bovinoQuery1;
    private javax.persistence.Query bovinoQuery2;
    private javax.swing.JTextArea caracteristicasTextArea;
    private javax.swing.JLabel edadLabel;
    private javax.swing.JTextField edadTextField;
    private javax.swing.JCheckBox editarCheckBox;
    private javax.swing.JComboBox<String> estadoComboBox;
    private java.util.List<ucr.ac.cr.ecci.Models.EstadoDesarrollo> estadoDesarrolloList;
    private javax.persistence.Query estadoDesarrolloQuery;
    private org.jdesktop.swingx.JXDatePicker fechaDatePicker;
    private javax.swing.JComboBox<String> fincaComboBox;
    private java.util.List<ucr.ac.cr.ecci.Models.Finca> fincaList;
    private javax.persistence.Query fincaQuery;
    private javax.swing.JButton guardarButton;
    private javax.swing.JFormattedTextField idFormattedTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> madreComboBox;
    private java.util.List<ucr.ac.cr.ecci.Models.ModoPreñez> modoPreñezList;
    private javax.persistence.Query modoPreñezQuery;
    private javax.swing.JTextField nombreTextField;
    private javax.swing.JComboBox<String> padreComboBox;
    private javax.swing.JFormattedTextField pesoFormattedTextField;
    private javax.swing.JComboBox<String> razaComboBox;
    private java.util.List<ucr.ac.cr.ecci.Models.Raza> razaList;
    private javax.persistence.Query razaQuery;
    private javax.swing.JButton volverButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
