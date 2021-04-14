/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Frames;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import ucr.ac.cr.ecci.DBManager;
import static ucr.ac.cr.ecci.Frames.VacaTableUI.centreWindow;
import ucr.ac.cr.ecci.Models.Bovino;
import ucr.ac.cr.ecci.Models.Celo;
import ucr.ac.cr.ecci.Models.Configuracion;
import ucr.ac.cr.ecci.Models.Destete;
import ucr.ac.cr.ecci.Models.Finca;
import ucr.ac.cr.ecci.Models.Palpacion;
import ucr.ac.cr.ecci.Models.Parto;
import ucr.ac.cr.ecci.Models.Servicio;
import ucr.ac.cr.ecci.Models.Vaca;

/**
 *
 * @author afg
 */
public class AlertasUI extends javax.swing.JFrame {
    
    final int tiempoGestacion = 280;
    int alertaIep;
    int alertaPartos;
    int alertaPalpaciones;
    int maxAlertaPalpaciones;
    int alertaDestetes;
    int maxAlertaDestetes;
    int PA;
    double SC;
    Configuracion config;
    Finca finca;
    
    
    String vacas_queryS;
    javax.persistence.Query vacasQuery;
    java.util.List vacasList;

    ArrayList iepList = new ArrayList();
    ArrayList partosList = new ArrayList();
    ArrayList palpacionList = new ArrayList();
    ArrayList destetesList = new ArrayList();
    
    ArrayList PAList = new ArrayList();
    ArrayList SCList = new ArrayList();
         
    /**
     * Creates new form AlertasUI
     */
    public AlertasUI() {
        centreWindow(this);
        initComponents();
        
        //TCUGanaderosPUEntityManager.getEntityManagerFactory().getCache().evictAll();

        
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel();
        for (int i = 0; i < fincaList.size(); i++) {
            comboModel.addElement(fincaList.get(i).getNombre());
        }
        fincaComboBox.setModel(comboModel);
        
        
        if (!fincaList.isEmpty()){
            this.finca = fincaList.get(fincaComboBox.getSelectedIndex());
            config = (Configuracion) configuracionList.get(fincaComboBox.getSelectedIndex());
            alertaIep = config.getMaxIep();
            alertaPartos = config.getAlertaParto();
            alertaPalpaciones = config.getAlertaPalpacion();
            maxAlertaPalpaciones = config.getMaxAlertaPalpacion();
            alertaDestetes = config.getAlertaDestete();
            maxAlertaDestetes = config.getMaxAlertaDestete();
            PA=config.getLimitePa();
            SC=config.getLimiteSc();
            
            
            iepTextField.setText(Integer.toString(alertaIep));
            partosTextField.setText(Integer.toString(alertaPartos));
            palpacionesTextField.setText(Integer.toString(alertaPalpaciones));
            palpacionesTextField1.setText(Integer.toString(maxAlertaPalpaciones));
            destetesTextField.setText(Integer.toString(alertaDestetes));
            destetesTextField1.setText(Integer.toString(maxAlertaDestetes));
            PATextField.setText(Integer.toString(PA));
            SCTextField.setText(Double.toString(SC));
            
            bindingGroup = new org.jdesktop.beansbinding.BindingGroup();
        
            // Create query
            vacas_queryS = "SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino "
                    + "WHERE baja.idBovino IS NULL AND b.esToro = 0 "
                    + "AND b.idFinca.id =" + Integer.toString(finca.getId());
            vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_queryS);
            vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();

            //Draw tables
            drawIEPDestetesTable();
            drawPartosPalpacionTable();
            drawPATable();
            drawSCTable(); 
        }
    }

    
    private void drawIEPDestetesTable(){
        //create list
        iepList.clear();
        destetesList.clear();
        for (int i = 0; i < vacasList.size(); i++){
            Bovino vaca_temp = (Bovino) vacasList.get(i);
            Object[] partosArray = vaca_temp.getPartoCollection().toArray();          
            int iep = 0;
            if (partosArray.length > 1){
                int k;
                for (k = 1; k < partosArray.length; k++){
                    //Fechas
                    Parto p1 = (Parto) partosArray[partosArray.length -(k+1)];
                    Parto p2 = (Parto) partosArray[partosArray.length -k];
                    Calendar p1fecha = Calendar.getInstance();
                    Calendar p2fecha = Calendar.getInstance();
                    p1fecha.setTime(p1.getFecha());
                    p2fecha.setTime(p2.getFecha());
                    iep += getIntervaloFechas( p1fecha, p2fecha);
                }
                iep = iep/(k-1);
                if (iep >= alertaIep) {                        
                    iepList.add(vaca_temp);
                }
            }
            if (partosArray.length >= 1) {
                // lista destetes
                Parto ultimoParto = (Parto) partosArray[partosArray.length-1];
                Calendar partoFecha = Calendar.getInstance();
                Calendar actualFecha = Calendar.getInstance();
                partoFecha.setTime(ultimoParto.getFecha());
                int diasPartoActual = getIntervaloFechas( partoFecha, actualFecha);
                if (!vaca_temp.getDesteteCollection().isEmpty()){
                   Object[] destetesArray = vaca_temp.getDesteteCollection().toArray();   
                   Destete ultimoDestete = (Destete) destetesArray[destetesArray.length-1];
                   Calendar desteteFecha = Calendar.getInstance();
                   desteteFecha.setTime(ultimoDestete.getFecha());
                   if (diasPartoActual >= alertaDestetes && getIntervaloFechas( desteteFecha, partoFecha) > 0){  
                       if(diasPartoActual <= maxAlertaDestetes){
                           destetesList.add(vaca_temp);
                       }
                   } 
                } else if (diasPartoActual >= alertaDestetes && diasPartoActual <= maxAlertaDestetes) {
                    destetesList.add(vaca_temp);
                }
            }      
        }
           
        // Table bind
        
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, iepList, iepTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(iepTable);
        if (iepTable.getColumnModel().getColumnCount() > 0) {
            iepTable.getColumnModel().getColumn(0).setResizable(false);
            iepTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            iepTable.getColumnModel().getColumn(1).setResizable(false);
            iepTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        }
        
        
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, destetesList, destetesTable);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(destetesTable);
        if (destetesTable.getColumnModel().getColumnCount() > 0) {
            destetesTable.getColumnModel().getColumn(0).setResizable(false);
            destetesTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            destetesTable.getColumnModel().getColumn(1).setResizable(false);
            destetesTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        }
    }
     
    int getIntervaloFechas(Calendar fecha1, Calendar fecha2){
        int intervalo;
        Calendar f1 = fecha1;
        Calendar f2 = fecha2;
        for (intervalo = 0; f1.before(f2); intervalo++) {
            f1.add(Calendar.DAY_OF_MONTH, 1);
        } 
        return intervalo;
    }
    
    
    private void drawSCTable(){
        SCList.clear();
        DBManager dbm = new DBManager();
        java.util.List<ucr.ac.cr.ecci.Models.Servicio> serviciosList;
        javax.persistence.Query serviciosQuery;
        double promedio=0;
        int positivos=0;
        int total=0;
        for (int i = 0; i < vacasList.size(); i++){
            Bovino vaca_temp = (Bovino) vacasList.get(i);
            int vaca_id= vaca_temp.getId();
            
            serviciosQuery = TCUGanaderosPUEntityManager.createQuery("SELECT s FROM Servicio s WHERE (s.idVaca.idBovino)= "+ vaca_id + " ORDER BY s.fechaServicio DESC");
            serviciosList = serviciosQuery.getResultList();
            boolean comenzar=false;
                if (!serviciosList.isEmpty()){
  
                    for(int j = 0; j<serviciosList.size(); j++){
                        Servicio temp= serviciosList.get(j);
                        if(temp.getEstaPreñada() && !comenzar){
                            comenzar=true;
                        }
                        
                        if(comenzar){
                            if(temp.getEstaPreñada()){
                                positivos++;
                                total++;
                            }else{
                                total++;
                            }
                        }
                    }
                    if(positivos!=0){
                        promedio = total/positivos;
                    }
                    
                    if(promedio>=SC){
                        SCList.add(vaca_temp);
                    }
                }
        promedio=0;
        positivos=0;
        total=0;
        }
        
        // Table bind        
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, SCList, SCTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane6.setViewportView(SCTable);
        if (SCTable.getColumnModel().getColumnCount() > 0) {
            SCTable.getColumnModel().getColumn(0).setResizable(false);
            SCTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            SCTable.getColumnModel().getColumn(1).setResizable(false);
            SCTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        }
    }
    /*ORIGINAL
    private void drawPATable(){
    PAList.clear();
    int intervaloFecha = 0;
    DBManager dbm = new DBManager();
    java.util.List<ucr.ac.cr.ecci.Models.Servicio> serviciosList;
    javax.persistence.Query serviciosQuery;
    for (int i = 0; i < vacasList.size(); i++){
            Bovino vaca_temp = (Bovino) vacasList.get(i);
            int vaca_id= vaca_temp.getId();
            Object[] partosArray = vaca_temp.getPartoCollection().toArray(); 

            if(partosArray.length > 0){
                Parto ultimoParto = (Parto) partosArray[partosArray.length-1];
                Calendar partoFecha = Calendar.getInstance();
                partoFecha.setTime(ultimoParto.getFecha());
                
                serviciosQuery = TCUGanaderosPUEntityManager.createQuery("SELECT s FROM Servicio s WHERE (s.idVaca.idBovino)= "+ vaca_id + " ORDER BY s.fechaServicio DESC");
                serviciosList = serviciosQuery.getResultList();
                
                if (!serviciosList.isEmpty()){
                    Servicio ultimoServicio= (Servicio) serviciosList.get(0);
                    Calendar servicioFecha = Calendar.getInstance();
                    servicioFecha.setTime(ultimoServicio.getFechaServicio());
                
                    if(!ultimoServicio.getEstaPreñada()){
                        intervaloFecha=getIntervaloFechas(partoFecha, servicioFecha);
                        if(intervaloFecha>=PA){
                            PAList.add(vaca_temp);
                        }
                    }
                }else{
                     Calendar actualFecha = Calendar.getInstance();
                     intervaloFecha=getIntervaloFechas(partoFecha, actualFecha);
                     System.out.println(intervaloFecha);
                     if(intervaloFecha>=PA){
                        PAList.add(vaca_temp);
                     }
                }
            }
        }
    
            // Table bind        
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, PAList, PATable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane5.setViewportView(PATable);
        if (PATable.getColumnModel().getColumnCount() > 0) {
            PATable.getColumnModel().getColumn(0).setResizable(false);
            PATable.getColumnModel().getColumn(0).setPreferredWidth(40);
            PATable.getColumnModel().getColumn(1).setResizable(false);
            PATable.getColumnModel().getColumn(1).setPreferredWidth(140);
        }
    }
    */
    
    private void drawPATable(){
    PAList.clear();
    int intervaloFecha = 0;
    DBManager dbm = new DBManager();
    java.util.List<ucr.ac.cr.ecci.Models.Servicio> serviciosList;
    javax.persistence.Query serviciosQuery;
        for (int i = 0; i < vacasList.size(); i++){
            Bovino vaca_temp = (Bovino) vacasList.get(i);
            int vaca_id= vaca_temp.getId();
            Object[] partosArray = vaca_temp.getPartoCollection().toArray(); 

            if(partosArray.length > 0){
                Parto ultimoParto = (Parto) partosArray[partosArray.length-1];
                Calendar partoFecha = Calendar.getInstance();
                partoFecha.setTime(ultimoParto.getFecha());
                
                serviciosQuery = TCUGanaderosPUEntityManager.createQuery("SELECT s FROM Servicio s WHERE (s.idVaca.idBovino)= "+ vaca_id + " ORDER BY s.fechaServicio DESC");
                serviciosList = serviciosQuery.getResultList();
                Servicio ultimoServicio=null;
                if (!serviciosList.isEmpty()){
                    boolean seguir=true;
                    for(int j=0; j<serviciosList.size() && seguir; j++){
                        ultimoServicio= (Servicio) serviciosList.get(0);
                        if(ultimoServicio.getEstaPreñada()){
                           seguir=false; 
                        }
                    }
                    if(!seguir){//Verifica si se encontró un servicio positivo.
                        Calendar servicioFecha = Calendar.getInstance();
                        servicioFecha.setTime(ultimoServicio.getFechaServicio());
                        if(partoFecha.compareTo(servicioFecha)<0){//Si el parto ocurrió antes que el servicio positivo.
                            intervaloFecha=getIntervaloFechas(partoFecha, servicioFecha);
                            if(intervaloFecha>=PA){//Verifica que el intervalo sea mayor a la configuación establecida.
                                PAList.add(vaca_temp);
                            }
                        }
                    }
                }
            }
        }
        
    
            // Table bind        
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, PAList, PATable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane5.setViewportView(PATable);
        if (PATable.getColumnModel().getColumnCount() > 0) {
            PATable.getColumnModel().getColumn(0).setResizable(false);
            PATable.getColumnModel().getColumn(0).setPreferredWidth(40);
            PATable.getColumnModel().getColumn(1).setResizable(false);
            PATable.getColumnModel().getColumn(1).setPreferredWidth(140);
        }
    }
    
        private void drawPartosPalpacionTable(){
        //create list
        partosList.clear();
        palpacionList.clear();
        DBManager dbm = new DBManager();
        java.util.List<ucr.ac.cr.ecci.Models.Servicio> serviciosList;
        javax.persistence.Query serviciosQuery;
        for (int i = 0; i < vacasList.size(); i++){
            
            Bovino vaca_temp = (Bovino) vacasList.get(i);
            int vaca_id= vaca_temp.getId();
            
            
            serviciosQuery = TCUGanaderosPUEntityManager.createQuery("SELECT s FROM Servicio s WHERE (s.idVaca.idBovino)= "+ vaca_id + " ORDER BY s.fechaServicio DESC");
            serviciosList = serviciosQuery.getResultList();
                
            if (!serviciosList.isEmpty()){
                
                Servicio ultimoServicio= (Servicio) serviciosList.get(0);
                
                Calendar servicioFecha = Calendar.getInstance();
                
                Calendar actualFecha = Calendar.getInstance();
                
                servicioFecha.setTime(ultimoServicio.getFechaServicio());
                
                int diasServiciosActual = getIntervaloFechas( servicioFecha, actualFecha);
                
                if ( (diasServiciosActual + alertaPartos >= tiempoGestacion) && (ultimoServicio.getEstaPreñada())){
                    partosList.add(vaca_temp);
                }
    

                if (diasServiciosActual >= alertaPalpaciones && diasServiciosActual <= maxAlertaPalpaciones){//CAMBIADA LA CONDICIÓN.
                    palpacionList.add(vaca_temp);
                } 
            }
    }
        // Table bind        
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, partosList, partosTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane3.setViewportView(partosTable);
        if (partosTable.getColumnModel().getColumnCount() > 0) {
            partosTable.getColumnModel().getColumn(0).setResizable(false);
            partosTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            partosTable.getColumnModel().getColumn(1).setResizable(false);
            partosTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        }
        
        
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, palpacionList, palpacionesTable);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombre}"));
        columnBinding.setColumnName("Nombre");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane4.setViewportView(palpacionesTable);
        if (palpacionesTable.getColumnModel().getColumnCount() > 0) {
            palpacionesTable.getColumnModel().getColumn(0).setResizable(false);
            palpacionesTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            palpacionesTable.getColumnModel().getColumn(1).setResizable(false);
            palpacionesTable.getColumnModel().getColumn(1).setPreferredWidth(140);
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

        TCUGanaderosPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("TCUGanaderosPU").createEntityManager();
        vacaQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL AND b.esToro = 0");
        vacaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacaQuery.getResultList();
        configuracionQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT c FROM Configuracion c");
        configuracionList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : configuracionQuery.getResultList();
        fincaQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT f FROM Finca f");
        fincaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : fincaQuery.getResultList();
        partoQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT p FROM Parto p");
        partoList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : partoQuery.getResultList();
        jScrollPane1 = new javax.swing.JScrollPane();
        destetesTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        iepTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        partosTable = new javax.swing.JTable();
        palpacionesLabel = new javax.swing.JLabel();
        iepLabel = new javax.swing.JLabel();
        partosLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        partosTextField = new javax.swing.JTextField();
        iepTextField = new javax.swing.JTextField();
        palpacionesTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        palpacionesTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        palpacionesLabel1 = new javax.swing.JLabel();
        destetesTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        fincaComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        vacasButton = new javax.swing.JButton();
        actualizarButton = new javax.swing.JButton();
        volverButton = new javax.swing.JButton();
        destetesTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        palpacionesTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        PATable = new javax.swing.JTable();
        palpacionesLabel2 = new javax.swing.JLabel();
        palpacionesLabel3 = new javax.swing.JLabel();
        PATextField = new javax.swing.JTextField();
        palpacionesLabel4 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        SCTable = new javax.swing.JTable();
        SCTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        palpacionesLabel5 = new javax.swing.JLabel();
        palpacionesLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1, 1));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        destetesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(destetesTable);

        iepTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        iepTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iepTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(iepTable);

        jScrollPane3.setViewportView(partosTable);
        if (partosTable.getColumnModel().getColumnCount() > 0) {
            partosTable.getColumnModel().getColumn(0).setResizable(false);
            partosTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            partosTable.getColumnModel().getColumn(0).setHeaderValue("Id");
            partosTable.getColumnModel().getColumn(1).setResizable(false);
            partosTable.getColumnModel().getColumn(1).setPreferredWidth(140);
            partosTable.getColumnModel().getColumn(1).setHeaderValue("Nombre");
        }

        palpacionesLabel.setText("Vacas por palpar, con salto");

        iepLabel.setText("Vacas con IEP mayora a");

        partosLabel.setText("Vacas con posibles partos ");

        jLabel1.setText("días.");

        jLabel2.setText("días.");

        partosTextField.setEditable(false);
        partosTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partosTextFieldActionPerformed(evt);
            }
        });

        iepTextField.setEditable(false);
        iepTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iepTextFieldActionPerformed(evt);
            }
        });

        palpacionesTextField.setEditable(false);
        palpacionesTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                palpacionesTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("días.");

        palpacionesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(palpacionesTable);

        jLabel4.setText(" en los  prox");

        palpacionesLabel1.setText("Vacas por destetar, con parto");

        destetesTextField.setEditable(false);
        destetesTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destetesTextFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("y");

        jLabel8.setText("Para cambiar el valor de alerta vaya a Configuración.");

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

        jLabel10.setText("Finca:");

        vacasButton.setText("Ir a Tabla de Vacas");
        vacasButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vacasButtonActionPerformed(evt);
            }
        });

        actualizarButton.setText("Actualizar");
        actualizarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarButtonActionPerformed(evt);
            }
        });

        volverButton.setText("Volver");
        volverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverButtonActionPerformed(evt);
            }
        });

        destetesTextField1.setEditable(false);
        destetesTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destetesTextField1ActionPerformed(evt);
            }
        });

        jLabel11.setText("dias.");

        palpacionesTextField1.setEditable(false);
        palpacionesTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                palpacionesTextField1ActionPerformed(evt);
            }
        });

        jLabel5.setText("y");

        jLabel12.setText("entre");

        jLabel13.setText("entre");

        PATable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(PATable);

        palpacionesLabel2.setText("Vacas con periodo abierto ");

        palpacionesLabel3.setText("mayor a");

        PATextField.setEditable(false);
        PATextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PATextFieldActionPerformed(evt);
            }
        });

        palpacionesLabel4.setText("días.");

        SCTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(SCTable);

        SCTextField.setEditable(false);
        SCTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SCTextFieldActionPerformed(evt);
            }
        });

        palpacionesLabel5.setText("Vacas con SC ");

        palpacionesLabel6.setText("mayor a");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(volverButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fincaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(actualizarButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(vacasButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(iepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(partosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2))
                                            .addComponent(partosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(23, 23, 23)
                                        .addComponent(palpacionesLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(iepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(90, 90, 90))
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(189, 189, 189)
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(palpacionesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(palpacionesTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(1, 1, 1)
                                                        .addComponent(jLabel13)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(destetesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(destetesTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel11)
                                                        .addGap(25, 25, 25)
                                                        .addComponent(palpacionesLabel3)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(PATextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(palpacionesLabel4))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(palpacionesLabel1)
                                                        .addGap(33, 33, 33)
                                                        .addComponent(palpacionesLabel2)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(palpacionesLabel5)
                                                        .addGap(2, 2, 2)
                                                        .addComponent(palpacionesLabel6)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel6))
                                                    .addComponent(SCTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(45, 45, 45))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fincaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(actualizarButton)
                    .addComponent(vacasButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(iepLabel)
                            .addComponent(partosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(palpacionesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(iepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(partosTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(palpacionesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(palpacionesTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(destetesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(destetesTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(palpacionesLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PATextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(palpacionesLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(palpacionesLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(palpacionesLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(palpacionesLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(palpacionesLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(SCTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(volverButton))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void partosTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partosTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partosTextFieldActionPerformed

    private void iepTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iepTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_iepTextFieldActionPerformed

    private void palpacionesTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_palpacionesTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_palpacionesTextFieldActionPerformed

    private void destetesTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destetesTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_destetesTextFieldActionPerformed

    private void fincaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fincaComboBoxActionPerformed
        TCUGanaderosPUEntityManager.getEntityManagerFactory().getCache().evictAll();
        this.finca = fincaList.get(fincaComboBox.getSelectedIndex());
        config = (Configuracion) configuracionList.get(fincaComboBox.getSelectedIndex());
        iepTextField.setText(Integer.toString(config.getMaxIep()));
        palpacionesTextField.setText(Integer.toString(config.getAlertaPalpacion()));
        partosTextField.setText(Integer.toString(config.getAlertaParto()));
        destetesTextField.setText(Integer.toString(config.getAlertaDestete()));
        PATextField.setText(Integer.toString(config.getLimitePa()));
        SCTextField.setText(Double.toString(config.getLimiteSc()));
        
        // Create query
        vacas_queryS = "SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino "
                + "WHERE baja.idBovino IS NULL AND b.esToro = 0 "
                + "AND b.idFinca.id =" + Integer.toString(finca.getId());
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_queryS);
        vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();

        // Redraw tables
        drawIEPDestetesTable();
        drawPartosPalpacionTable();
        drawPATable();
        drawSCTable();
    }//GEN-LAST:event_fincaComboBoxActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        TCUGanaderosPUEntityManager.getEntityManagerFactory().getCache().evictAll();
        this.finca = fincaList.get(fincaComboBox.getSelectedIndex());
        config = (Configuracion) configuracionList.get(fincaComboBox.getSelectedIndex());
        iepTextField.setText(Integer.toString(config.getMaxIep()));
        palpacionesTextField.setText(Integer.toString(config.getAlertaPalpacion()));
        partosTextField.setText(Integer.toString(config.getAlertaParto()));
        destetesTextField.setText(Integer.toString(config.getAlertaDestete()));
        PATextField.setText(Integer.toString(config.getLimitePa()));
        SCTextField.setText(Double.toString(config.getLimiteSc()));
        
        // Create query
        vacas_queryS = "SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino "
                + "WHERE baja.idBovino IS NULL AND b.esToro = 0 "
                + "AND b.idFinca.id =" + Integer.toString(finca.getId());
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_queryS);
        vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();
        
        drawIEPDestetesTable();
        drawPartosPalpacionTable();
        drawPATable();
        drawSCTable();
    }//GEN-LAST:event_formWindowGainedFocus

    private void iepTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iepTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_iepTableMouseClicked

    private void vacasButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vacasButtonActionPerformed
        new VacaTableUI().setVisible(true);
    }//GEN-LAST:event_vacasButtonActionPerformed

    private void actualizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarButtonActionPerformed
        this.dispose();
        AlertasUI alerta = new AlertasUI();
        alerta.setLocationRelativeTo(null);
        alerta.setVisible(true);
    }//GEN-LAST:event_actualizarButtonActionPerformed

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButtonActionPerformed
        dispose();
    }//GEN-LAST:event_volverButtonActionPerformed

    private void destetesTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destetesTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_destetesTextField1ActionPerformed

    private void palpacionesTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_palpacionesTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_palpacionesTextField1ActionPerformed

    private void PATextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PATextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PATextFieldActionPerformed

    private void SCTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SCTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SCTextFieldActionPerformed

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
            java.util.logging.Logger.getLogger(AlertasUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlertasUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlertasUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlertasUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlertasUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable PATable;
    private javax.swing.JTextField PATextField;
    private javax.swing.JTable SCTable;
    private javax.swing.JTextField SCTextField;
    private javax.persistence.EntityManager TCUGanaderosPUEntityManager;
    private javax.swing.JButton actualizarButton;
    private java.util.List configuracionList;
    private javax.persistence.Query configuracionQuery;
    private javax.swing.JTable destetesTable;
    private javax.swing.JTextField destetesTextField;
    private javax.swing.JTextField destetesTextField1;
    private javax.swing.JComboBox<String> fincaComboBox;
    private java.util.List<ucr.ac.cr.ecci.Models.Finca> fincaList;
    private javax.persistence.Query fincaQuery;
    private javax.swing.JLabel iepLabel;
    private javax.swing.JTable iepTable;
    private javax.swing.JTextField iepTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel palpacionesLabel;
    private javax.swing.JLabel palpacionesLabel1;
    private javax.swing.JLabel palpacionesLabel2;
    private javax.swing.JLabel palpacionesLabel3;
    private javax.swing.JLabel palpacionesLabel4;
    private javax.swing.JLabel palpacionesLabel5;
    private javax.swing.JLabel palpacionesLabel6;
    private javax.swing.JTable palpacionesTable;
    private javax.swing.JTextField palpacionesTextField;
    private javax.swing.JTextField palpacionesTextField1;
    private java.util.List partoList;
    private javax.persistence.Query partoQuery;
    private javax.swing.JLabel partosLabel;
    private javax.swing.JTable partosTable;
    private javax.swing.JTextField partosTextField;
    private java.util.List<ucr.ac.cr.ecci.Models.Bovino> vacaList;
    private javax.persistence.Query vacaQuery;
    private javax.swing.JButton vacasButton;
    private javax.swing.JButton volverButton;
    // End of variables declaration//GEN-END:variables
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
}
