/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Frames;

import java.awt.Color;
import javax.swing.UIManager;
import ucr.ac.cr.ecci.DBManager;
import static ucr.ac.cr.ecci.Frames.VacaTableUI.centreWindow;
import ucr.ac.cr.ecci.Models.Bovino;
import ucr.ac.cr.ecci.Models.ExamenAndrologico;
import ucr.ac.cr.ecci.Models.Parto;

/**
 *
 * @author afg
 */
public class ExamenesUI extends javax.swing.JFrame {

    /**
     * Creates new form ExamenesUI
     */
    
    ExamenAndrologico examen;
    Bovino bovino;
    boolean detalles = false;

    
    public ExamenesUI() {
        initComponents();
    }
    
    public ExamenesUI(Bovino bovino) {
        initComponents();
        centreWindow(this);
        this.bovino = bovino;
        examen = new ExamenAndrologico();
        examen.setIdBovino(bovino);

        
        toroIdTextField.setText(Integer.toString(bovino.getId()));
        toroNombreTextField.setText(bovino.getNombre()); 
        toroIdTextField.setEditable(false);
        toroNombreTextField.setEditable(false);
        
        editarCheckBox.setVisible(false);
        eliminarButton.setVisible(false);
        
        satisfactorioButtonGroup.add(satisfactorioRadio);
        satisfactorioButtonGroup.add(noSatisfactorioRadio);
    }
    
    public ExamenesUI(Bovino bovino, ExamenAndrologico examen) {
        initComponents();
        centreWindow(this);
        this.examen = examen;
        this.bovino = bovino;
        guardarButton.setVisible(false);
        eliminarButton.setVisible(false);
        editarCheckBox.setVisible(true);
        detalles = true;
        toroIdTextField.setText(Integer.toString(bovino.getId()));
        toroNombreTextField.setText(bovino.getNombre()); 
        toroIdTextField.setEditable(false);
        toroNombreTextField.setEditable(false);
        
        satisfactorioButtonGroup.add(satisfactorioRadio);
        satisfactorioButtonGroup.add(noSatisfactorioRadio);
        
        fechaDatePicker.setDate(examen.getFecha());
        condicionCorporalTextField.setText(examen.getCondicionCorporal());
        if (examen.getLibido() == 1) libidoBox.setSelected(true);
        if (examen.getCapacidadDeMonta() == 1) capacidadMontaBox.setSelected(true);
        if (examen.getPrepucio() == 1) prepucioBox.setSelected(true);
        if (examen.getPene() == 1) peneBox.setSelected(true);
        if (examen.getTesticulos() == 1) testiculosBox.setSelected(true);
        if (examen.getEpididimos() == 1) epididimosBox.setSelected(true);
        if (examen.getGlandulasBulbouretrales() == 1) glandulasBox.setSelected(true);
        if (examen.getProstata() == 1) prostataBox.setSelected(true);
        if (examen.getVesiculasSeminales() == 1) vesiculasBox.setSelected(true);
        if (examen.getAmpulas() == 1) ampulasBox.setSelected(true);
        descansoSpinner.setValue(examen.getDescanso());
        circunferenciaSpinner.setValue(examen.getCircunferenciaEscrotal());
        volumenEyaculadoSpinner.setValue(examen.getVolumenEyaculado());
        concentracionSpinner.setValue(examen.getConcentracion());
        motilidadMasalSpinner.setValue(examen.getMotilidadMasal());
        motilidadProgresivaSpinner.setValue(examen.getMotilidadProgresiva());
        morfologiaNormalSpinner.setValue(examen.getMorfologiaNormal());
        dañosAcrosomalesTextField.setText(examen.getDanosAcrosomales());
        anormalidadesPrimariasTextField.setText(examen.getAnormalidadesPrimarias());
        anormalidadesSecundariasTextField.setText(examen.getAnormalidadesSecundarias());
        totalAnormalidadesSpinner.setValue(examen.getTotalAnormalidades());
        anormalidadFrecuenteTextField.setText(examen.getAnormalidadFrecuente());
        leucocitosSpinner.setValue(examen.getLeucocitos());
        celulasEpitelialesSpinner.setValue(examen.getCelulasEpiteliales());
        if (examen.getPalpacion() == 1) palpacionBox.setSelected(true);
        if (examen.getEcografia() == 1) ecografiaBox.setSelected(true);
        if (examen.getVaginaArtificial() == 1) vaginaBox.setSelected(true);
        if (examen.getElectroeyaculador() == 1) electroeyaculadorBox.setSelected(true);
        if (examen.getProtusion() == 1) protusionBox.setSelected(true);
        if (examen.getSatisfactorio() == 1) satisfactorioRadio.setSelected(true);
        else noSatisfactorioRadio.setSelected(true);
        veterinarioTextField.setText(examen.getVeterinario());
        observacionesTextArea.setText(examen.getObservaciones());
        
        setFieldsEnabled(false);
        
        detalles = true;

    }
    
    private void setFieldsEnabled(boolean bool) {
        

        ampulasBox.setEnabled(bool);
        anormalidadFrecuenteTextField.setEditable(bool);
        anormalidadesPrimariasTextField.setEditable(bool);
        anormalidadesSecundariasTextField.setEditable(bool);
        capacidadMontaBox.setEnabled(bool);
        celulasEpitelialesSpinner.setEnabled(bool);
        circunferenciaSpinner.setEnabled(bool);
        concentracionSpinner.setEnabled(bool);
        descansoSpinner.setEnabled(bool);
        leucocitosSpinner.setEnabled(bool);
        morfologiaNormalSpinner.setEnabled(bool);
        motilidadMasalSpinner.setEnabled(bool);
        motilidadProgresivaSpinner.setEnabled(bool);
        totalAnormalidadesSpinner.setEnabled(bool);
        volumenEyaculadoSpinner.setEnabled(bool);
        condicionCorporalTextField.setEditable(bool);
        dañosAcrosomalesTextField.setEditable(bool);
        observacionesTextArea.setEditable(bool);
        veterinarioTextField.setEditable(bool);
        ecografiaBox.setEnabled(bool);
        electroeyaculadorBox.setEnabled(bool);
        epididimosBox.setEnabled(bool);
        glandulasBox.setEnabled(bool);
        libidoBox.setEnabled(bool);
        palpacionBox.setEnabled(bool);
        peneBox.setEnabled(bool);
        prepucioBox.setEnabled(bool);
        prostataBox.setEnabled(bool);
        protusionBox.setEnabled(bool);
        testiculosBox.setEnabled(bool);
        vaginaBox.setEnabled(bool);
        vesiculasBox.setEnabled(bool);
        fechaDatePicker.setEditable(bool);
        satisfactorioRadio.setEnabled(bool);
        noSatisfactorioRadio.setEnabled(bool);
        
        ampulasBox.setForeground(Color.black);
        capacidadMontaBox.setForeground(Color.black);
        celulasEpitelialesSpinner.setForeground(Color.black);       
        circunferenciaSpinner.setForeground(Color.black);
        concentracionSpinner.setForeground(Color.black);
        descansoSpinner.setForeground(Color.black);
        leucocitosSpinner.setForeground(Color.black);
        morfologiaNormalSpinner.setForeground(Color.black);
        motilidadMasalSpinner.setForeground(Color.black);
        motilidadProgresivaSpinner.setForeground(Color.black);
        totalAnormalidadesSpinner.setForeground(Color.black);
        volumenEyaculadoSpinner.setForeground(Color.black);
        ecografiaBox.setForeground(Color.black);
        electroeyaculadorBox.setForeground(Color.black);
        epididimosBox.setForeground(Color.black);
        glandulasBox.setForeground(Color.black);
        libidoBox.setForeground(Color.black);
        palpacionBox.setForeground(Color.black);
        peneBox.setForeground(Color.black);
        prepucioBox.setForeground(Color.black);
        prostataBox.setForeground(Color.black);
        protusionBox.setForeground(Color.black);
        testiculosBox.setForeground(Color.black);
        vaginaBox.setForeground(Color.black);
        vesiculasBox.setForeground(Color.black);    
        satisfactorioRadio.setForeground(Color.black);    
        noSatisfactorioRadio.setForeground(Color.black);    
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        satisfactorioButtonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        toroIdTextField = new javax.swing.JTextField();
        toroNombreTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fechaDatePicker = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        condicionCorporalTextField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        libidoBox = new javax.swing.JCheckBox();
        capacidadMontaBox = new javax.swing.JCheckBox();
        prepucioBox = new javax.swing.JCheckBox();
        peneBox = new javax.swing.JCheckBox();
        testiculosBox = new javax.swing.JCheckBox();
        epididimosBox = new javax.swing.JCheckBox();
        ampulasBox = new javax.swing.JCheckBox();
        vesiculasBox = new javax.swing.JCheckBox();
        prostataBox = new javax.swing.JCheckBox();
        glandulasBox = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        descansoSpinner = new javax.swing.JSpinner();
        circunferenciaSpinner = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        volumenEyaculadoSpinner = new javax.swing.JSpinner();
        motilidadMasalSpinner = new javax.swing.JSpinner();
        concentracionSpinner = new javax.swing.JSpinner();
        motilidadProgresivaSpinner = new javax.swing.JSpinner();
        morfologiaNormalSpinner = new javax.swing.JSpinner();
        dañosAcrosomalesTextField = new javax.swing.JTextField();
        anormalidadesPrimariasTextField = new javax.swing.JTextField();
        anormalidadesSecundariasTextField = new javax.swing.JTextField();
        anormalidadFrecuenteTextField = new javax.swing.JTextField();
        leucocitosSpinner = new javax.swing.JSpinner();
        totalAnormalidadesSpinner = new javax.swing.JSpinner();
        celulasEpitelialesSpinner = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        palpacionBox = new javax.swing.JCheckBox();
        ecografiaBox = new javax.swing.JCheckBox();
        vaginaBox = new javax.swing.JCheckBox();
        electroeyaculadorBox = new javax.swing.JCheckBox();
        protusionBox = new javax.swing.JCheckBox();
        editarCheckBox = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        veterinarioTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        observacionesTextArea = new javax.swing.JTextArea();
        satisfactorioRadio = new javax.swing.JRadioButton();
        noSatisfactorioRadio = new javax.swing.JRadioButton();
        volverButton = new javax.swing.JButton();
        guardarButton = new javax.swing.JButton();
        eliminarButton = new javax.swing.JButton();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Físicos"));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setText("Toro:");

        jLabel2.setText("Fecha:");

        fechaDatePicker.setFormats(new String[]{"dd/MM/yyyy"});

        jLabel3.setText("Cond. Corporal:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Físicos"));

        libidoBox.setText("Libido");

        capacidadMontaBox.setText("Capacidad de monta");

        prepucioBox.setText("Prepucio");

        peneBox.setText("Pene");

        testiculosBox.setText("Testículos");

        epididimosBox.setText("Epidídimos");

        ampulasBox.setText("Ámpulas");

        vesiculasBox.setText("Vesículas seminales");

        prostataBox.setText("Próstata");

        glandulasBox.setText("Glándulas bulbouretrales");

        jLabel4.setText("Descanso sexual (días)");

        jLabel5.setText("Circunf. escrotal (cm)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(libidoBox)
            .addComponent(capacidadMontaBox)
            .addComponent(prepucioBox)
            .addComponent(peneBox)
            .addComponent(testiculosBox)
            .addComponent(epididimosBox)
            .addComponent(glandulasBox)
            .addComponent(prostataBox)
            .addComponent(vesiculasBox)
            .addComponent(ampulasBox)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(circunferenciaSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                    .addComponent(descansoSpinner)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(libidoBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(capacidadMontaBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(prepucioBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(peneBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(testiculosBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(epididimosBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(glandulasBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(prostataBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vesiculasBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ampulasBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(descansoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(circunferenciaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Semen"));

        jLabel6.setText("Volumen eyaculado (ml)");

        jLabel7.setText("Concentración");

        jLabel8.setText("Motilidad masal %");

        jLabel9.setText("Daños acrosomales");

        jLabel10.setText("Morfología normal %");

        jLabel11.setText("Motilidad progresiva rápida %");

        jLabel12.setText("Total anormalidades %");

        jLabel13.setText("Anormalidades secundarias");

        jLabel14.setText("Anormalidades primarias");

        jLabel15.setText("Celulas epiteliales");

        jLabel16.setText("Leucocitos");

        jLabel17.setText("Anormalidad más frecuente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(anormalidadFrecuenteTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel17)
                            .addComponent(jLabel12))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalAnormalidadesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dañosAcrosomalesTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(anormalidadesPrimariasTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(anormalidadesSecundariasTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(volumenEyaculadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(motilidadProgresivaSpinner, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(motilidadMasalSpinner, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(concentracionSpinner, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addComponent(morfologiaNormalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15))
                        .addGap(77, 77, 77)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(celulasEpitelialesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(leucocitosSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(volumenEyaculadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(concentracionSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(motilidadMasalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(motilidadProgresivaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(morfologiaNormalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(dañosAcrosomalesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(anormalidadesPrimariasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(anormalidadesSecundariasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(totalAnormalidadesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(anormalidadFrecuenteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(leucocitosSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(celulasEpitelialesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Métodos utilizados"));

        palpacionBox.setText("Palpación");

        ecografiaBox.setText("Ecografía");

        vaginaBox.setText("Vagina artifical");

        electroeyaculadorBox.setText("Electroeyaculador");

        protusionBox.setText("Protusión");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(palpacionBox)
                    .addComponent(ecografiaBox)
                    .addComponent(vaginaBox)
                    .addComponent(electroeyaculadorBox)
                    .addComponent(protusionBox))
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(palpacionBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ecografiaBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vaginaBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(electroeyaculadorBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(protusionBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jLabel18.setText("Veterinario");

        jLabel19.setText("Observaciones");

        observacionesTextArea.setColumns(20);
        observacionesTextArea.setRows(5);
        jScrollPane1.setViewportView(observacionesTextArea);

        satisfactorioRadio.setText("Satisfactorio");

        noSatisfactorioRadio.setText("No satisfactorio");

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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(veterinarioTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel18))
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(satisfactorioRadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(noSatisfactorioRadio)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(toroIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toroNombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fechaDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(condicionCorporalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(editarCheckBox)
                                .addGap(0, 4, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(volverButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(eliminarButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(guardarButton)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(toroIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toroNombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(fechaDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(condicionCorporalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editarCheckBox))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(noSatisfactorioRadio)
                            .addComponent(satisfactorioRadio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addGap(4, 4, 4)
                        .addComponent(veterinarioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(volverButton)
                    .addComponent(guardarButton)
                    .addComponent(eliminarButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editarCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editarCheckBoxActionPerformed

    private void editarCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_editarCheckBoxStateChanged
        if (editarCheckBox.isSelected()){
            setFieldsEnabled(true);
            guardarButton.setVisible(true);
            eliminarButton.setVisible(true);
        } else if (!editarCheckBox.isSelected()){
            setFieldsEnabled(false);
            guardarButton.setVisible(false);
            eliminarButton.setVisible(false);
        }
    }//GEN-LAST:event_editarCheckBoxStateChanged

    private void guardarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarButtonActionPerformed
        DBManager dbm = new DBManager();
        if (detalles) {
            
            examen.setFecha(fechaDatePicker.getDate());
            examen.setCondicionCorporal(condicionCorporalTextField.getText());

            if (libidoBox.isSelected()) examen.setLibido((short)1);
            else examen.setLibido((short)0);
            if (capacidadMontaBox.isSelected()) examen.setCapacidadDeMonta((short)1);
            else examen.setCapacidadDeMonta((short)0);
            if (prepucioBox.isSelected()) examen.setPrepucio((short)1);
            else examen.setPrepucio((short)0);
            if (peneBox.isSelected()) examen.setPene((short)1);
            else examen.setPene((short)0);
            if (testiculosBox.isSelected()) examen.setTesticulos((short)1);
            else examen.setTesticulos((short)0);
            if (epididimosBox.isSelected()) examen.setEpididimos((short)1);
            else examen.setEpididimos((short)0);
            if (glandulasBox.isSelected()) examen.setGlandulasBulbouretrales((short)1);
            else examen.setGlandulasBulbouretrales((short)0);
            if (prostataBox.isSelected()) examen.setProstata((short)1);
            else examen.setProstata((short)0);
            if (vesiculasBox.isSelected()) examen.setVesiculasSeminales((short)1);
            else examen.setVesiculasSeminales((short)0);
            if (ampulasBox.isSelected()) examen.setAmpulas((short)1);
            else examen.setAmpulas((short)0);
            
            examen.setDescanso(Double.parseDouble(descansoSpinner.getValue().toString()));
            examen.setCircunferenciaEscrotal(Double.parseDouble(circunferenciaSpinner.getValue().toString()));
            
            examen.setVolumenEyaculado(Double.parseDouble(volumenEyaculadoSpinner.getValue().toString()));
            examen.setConcentracion(Double.parseDouble(concentracionSpinner.getValue().toString()));
            examen.setMotilidadMasal(Double.parseDouble(motilidadMasalSpinner.getValue().toString()));
            examen.setMotilidadProgresiva(Double.parseDouble(motilidadProgresivaSpinner.getValue().toString()));
            examen.setMorfologiaNormal(Double.parseDouble(morfologiaNormalSpinner.getValue().toString()));
            examen.setDescanso(Double.parseDouble(descansoSpinner.getValue().toString()));
            
            examen.setDanosAcrosomales(dañosAcrosomalesTextField.getText());
            examen.setAnormalidadesPrimarias(anormalidadesPrimariasTextField.getText());
            examen.setAnormalidadesSecundarias(anormalidadesSecundariasTextField.getText());
            
            examen.setTotalAnormalidades(Double.parseDouble(totalAnormalidadesSpinner.getValue().toString()));
            
            examen.setAnormalidadFrecuente(anormalidadFrecuenteTextField.getText());
            
            examen.setLeucocitos(Double.parseDouble(leucocitosSpinner.getValue().toString()));
            examen.setCelulasEpiteliales(Double.parseDouble(celulasEpitelialesSpinner.getValue().toString()));
            
            if (palpacionBox.isSelected()) examen.setPalpacion((short)1);
            else examen.setPalpacion((short)0);
            if (ecografiaBox.isSelected()) examen.setEcografia((short)1);
            else examen.setEcografia((short)0);
            if (vaginaBox.isSelected()) examen.setVaginaArtificial((short)1);
            else examen.setVaginaArtificial((short)0);
            if (electroeyaculadorBox.isSelected()) examen.setElectroeyaculador((short)1);
            else examen.setElectroeyaculador((short)0);
            if (protusionBox.isSelected()) examen.setProtusion((short)1);
            else examen.setProtusion((short)0);
            
            if (satisfactorioRadio.isSelected()) examen.setSatisfactorio((short)1);
            else examen.setSatisfactorio((short)0);

            examen.setVeterinario(veterinarioTextField.getText());
            examen.setObservaciones(observacionesTextArea.getText());          
                      
            dbm.updateExamen(examen);
            dbm.close();  
        } else {
             
            examen.setFecha(fechaDatePicker.getDate());
            examen.setCondicionCorporal(condicionCorporalTextField.getText());

            if (libidoBox.isSelected()) examen.setLibido((short)1);
            else examen.setLibido((short)0);
            if (capacidadMontaBox.isSelected()) examen.setCapacidadDeMonta((short)1);
            else examen.setCapacidadDeMonta((short)0);
            if (prepucioBox.isSelected()) examen.setPrepucio((short)1);
            else examen.setPrepucio((short)0);
            if (peneBox.isSelected()) examen.setPene((short)1);
            else examen.setPene((short)0);
            if (testiculosBox.isSelected()) examen.setTesticulos((short)1);
            else examen.setTesticulos((short)0);
            if (epididimosBox.isSelected()) examen.setEpididimos((short)1);
            else examen.setEpididimos((short)0);
            if (glandulasBox.isSelected()) examen.setGlandulasBulbouretrales((short)1);
            else examen.setGlandulasBulbouretrales((short)0);
            if (prostataBox.isSelected()) examen.setProstata((short)1);
            else examen.setProstata((short)0);
            if (vesiculasBox.isSelected()) examen.setVesiculasSeminales((short)1);
            else examen.setVesiculasSeminales((short)0);
            if (ampulasBox.isSelected()) examen.setAmpulas((short)1);
            else examen.setAmpulas((short)0);
            
            examen.setDescanso(Double.parseDouble(descansoSpinner.getValue().toString()));
            examen.setCircunferenciaEscrotal(Double.parseDouble(circunferenciaSpinner.getValue().toString()));
            
            examen.setVolumenEyaculado(Double.parseDouble(volumenEyaculadoSpinner.getValue().toString()));
            examen.setConcentracion(Double.parseDouble(concentracionSpinner.getValue().toString()));
            examen.setMotilidadMasal(Double.parseDouble(motilidadMasalSpinner.getValue().toString()));
            examen.setMotilidadProgresiva(Double.parseDouble(motilidadProgresivaSpinner.getValue().toString()));
            examen.setMorfologiaNormal(Double.parseDouble(morfologiaNormalSpinner.getValue().toString()));
            examen.setDescanso(Double.parseDouble(descansoSpinner.getValue().toString()));
            
            examen.setDanosAcrosomales(dañosAcrosomalesTextField.getText());
            examen.setAnormalidadesPrimarias(anormalidadesPrimariasTextField.getText());
            examen.setAnormalidadesSecundarias(anormalidadesSecundariasTextField.getText());
            
            examen.setTotalAnormalidades(Double.parseDouble(totalAnormalidadesSpinner.getValue().toString()));
            
            examen.setAnormalidadFrecuente(anormalidadFrecuenteTextField.getText());
            
            examen.setLeucocitos(Double.parseDouble(leucocitosSpinner.getValue().toString()));
            examen.setCelulasEpiteliales(Double.parseDouble(celulasEpitelialesSpinner.getValue().toString()));
            
            if (palpacionBox.isSelected()) examen.setPalpacion((short)1);
            else examen.setPalpacion((short)0);
            if (ecografiaBox.isSelected()) examen.setEcografia((short)1);
            else examen.setEcografia((short)0);
            if (vaginaBox.isSelected()) examen.setVaginaArtificial((short)1);
            else examen.setVaginaArtificial((short)0);
            if (electroeyaculadorBox.isSelected()) examen.setElectroeyaculador((short)1);
            else examen.setElectroeyaculador((short)0);
            if (protusionBox.isSelected()) examen.setProtusion((short)1);
            else examen.setProtusion((short)0);
            
            if (satisfactorioRadio.isSelected()) examen.setSatisfactorio((short)1);
            else examen.setSatisfactorio((short)0);

            examen.setVeterinario(veterinarioTextField.getText());
            examen.setObservaciones(observacionesTextArea.getText());          
            
            dbm.insert(examen);
            dbm.close();  
        }
        setVisible(false);
        dispose();        
    }//GEN-LAST:event_guardarButtonActionPerformed

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButtonActionPerformed
            setVisible(false);
            dispose();
    }//GEN-LAST:event_volverButtonActionPerformed

    private void eliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarButtonActionPerformed
        DBManager dbm = new DBManager();
        ExamenAndrologico examenDB = dbm.findExamenById(examen.getId());
        dbm.delete(examenDB);
        dbm.close();
        setVisible(false);
        dispose();  
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
            java.util.logging.Logger.getLogger(ExamenesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExamenesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExamenesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExamenesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExamenesUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ampulasBox;
    private javax.swing.JTextField anormalidadFrecuenteTextField;
    private javax.swing.JTextField anormalidadesPrimariasTextField;
    private javax.swing.JTextField anormalidadesSecundariasTextField;
    private javax.swing.JCheckBox capacidadMontaBox;
    private javax.swing.JSpinner celulasEpitelialesSpinner;
    private javax.swing.JSpinner circunferenciaSpinner;
    private javax.swing.JSpinner concentracionSpinner;
    private javax.swing.JTextField condicionCorporalTextField;
    private javax.swing.JTextField dañosAcrosomalesTextField;
    private javax.swing.JSpinner descansoSpinner;
    private javax.swing.JCheckBox ecografiaBox;
    private javax.swing.JCheckBox editarCheckBox;
    private javax.swing.JCheckBox electroeyaculadorBox;
    private javax.swing.JButton eliminarButton;
    private javax.swing.JCheckBox epididimosBox;
    private org.jdesktop.swingx.JXDatePicker fechaDatePicker;
    private javax.swing.JCheckBox glandulasBox;
    private javax.swing.JButton guardarButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner leucocitosSpinner;
    private javax.swing.JCheckBox libidoBox;
    private javax.swing.JSpinner morfologiaNormalSpinner;
    private javax.swing.JSpinner motilidadMasalSpinner;
    private javax.swing.JSpinner motilidadProgresivaSpinner;
    private javax.swing.JRadioButton noSatisfactorioRadio;
    private javax.swing.JTextArea observacionesTextArea;
    private javax.swing.JCheckBox palpacionBox;
    private javax.swing.JCheckBox peneBox;
    private javax.swing.JCheckBox prepucioBox;
    private javax.swing.JCheckBox prostataBox;
    private javax.swing.JCheckBox protusionBox;
    private javax.swing.ButtonGroup satisfactorioButtonGroup;
    private javax.swing.JRadioButton satisfactorioRadio;
    private javax.swing.JCheckBox testiculosBox;
    private javax.swing.JTextField toroIdTextField;
    private javax.swing.JTextField toroNombreTextField;
    private javax.swing.JSpinner totalAnormalidadesSpinner;
    private javax.swing.JCheckBox vaginaBox;
    private javax.swing.JCheckBox vesiculasBox;
    private javax.swing.JTextField veterinarioTextField;
    private javax.swing.JSpinner volumenEyaculadoSpinner;
    private javax.swing.JButton volverButton;
    // End of variables declaration//GEN-END:variables
}
