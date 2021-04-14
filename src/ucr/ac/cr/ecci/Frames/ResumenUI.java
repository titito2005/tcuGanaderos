/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Frames;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import org.apache.poi.util.IOUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import static ucr.ac.cr.ecci.Frames.VacaTableUI.centreWindow;
import ucr.ac.cr.ecci.Models.Bovino;
import ucr.ac.cr.ecci.Models.Finca;
import ucr.ac.cr.ecci.Models.Palpacion;
import ucr.ac.cr.ecci.Models.Parto;
import ucr.ac.cr.ecci.Models.Vaca;
import org.apache.poi.xssf.usermodel.*;
import ucr.ac.cr.ecci.Models.Celo;
import ucr.ac.cr.ecci.Models.Destete;
import org.apache.commons.lang3.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import ucr.ac.cr.ecci.DBManager;
import ucr.ac.cr.ecci.Models.Servicio;
import java.math.*; 


/**
 *
 * @author afg
 */
public class ResumenUI extends javax.swing.JFrame {

    /**
     * Creates new form ResumenUI
     */
    
    Calendar cal;
    Date desde;
    Date hasta;
    Vaca vaca;
    String cond_finca;
    Finca finca;
    
    String vacas_activas_query;
    String vacas_con_partos_query;
    String vacas_activas_fechas_query;
    String vacas_con_partos_fechas_query;
    
    String servicios_positivos_query;
    String servicios_totales_query;
    
    double porcentajeConcepcion;
    
    String iep_promedio_query;
    int iep_prom;
    int ultimo_iep_prom;
    int cantidad_partos_activas;
    double SC;
    
    String cond_fecha_desde;
    String cond_fecha_hasta;
    
    java.util.List vacasActivasList;
    javax.persistence.Query vacasActivasQuery;
    java.util.List vacasActivasPartosList;
    javax.persistence.Query vacasActivasPartosQuery;
    java.util.List vacasActivasPartosFechasList;
    javax.persistence.Query vacasActivasPartosFechasQuery;
    java.util.List vacasActivasFechasList;
    javax.persistence.Query vacasActivasFechasQuery;
    
    java.util.List<Long> serviciosPositivosList;
    javax.persistence.Query serviciosPositivosQuery;
    java.util.List<Long> serviciosTotalesList;
    javax.persistence.Query serviciosTotalesQuery;
    
    
    java.util.List vacasList;
    String vacas_queryS;
    javax.persistence.Query vacasQuery;
    
    ArrayList SCList = new ArrayList();
    
    
    public ResumenUI() {
        centreWindow(this);
        initComponents();
        
        TCUGanaderosPUEntityManager.getEntityManagerFactory().getCache().evictAll();
        cond_finca = "";
        cond_fecha_desde = "";
        cond_fecha_hasta = ""; 
        
        DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel();
        comboModel.addElement("Todas");
        for (int i = 0; i < fincaList.size(); i++) {
            comboModel.addElement(fincaList.get(i).getNombre());
        }
        fincaComboBox.setModel(comboModel);
        
        setFormGeneral();   
        porcentajeConcepcion=0;
        //Pone fechas
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);    
        desde = cal.getTime();
        desdeDatePicker.setDate(desde);
        hasta = Calendar.getInstance().getTime();
        hastaDatePicker.setDate(hasta);
             
    }
    
    double promedioAbortos(){//poner que este es general
        double retorno=0;
        
        java.util.List<Long> partosTotalesList;
        javax.persistence.Query partosTotalesQuery;
        partosTotalesQuery = TCUGanaderosPUEntityManager.createQuery("SELECT COUNT (p) FROM Parto p, Finca finca WHERE p.idMadre.idFinca.id = finca.id"+  cond_finca);
        partosTotalesList = partosTotalesQuery.getResultList();
        double totalPartos= partosTotalesList.get(0);
        
        java.util.List<Long> abortosList;
        javax.persistence.Query abortosQuery;
        abortosQuery = TCUGanaderosPUEntityManager.createQuery("SELECT COUNT (p) FROM Parto p, Finca finca WHERE p.muerteprematura = true AND p.idMadre.idFinca.id = finca.id" +  cond_finca);
        abortosList = abortosQuery.getResultList();
        double totalAbortos= abortosList.get(0);
        if(totalPartos!=0){
            retorno= (totalAbortos/totalPartos)*100;
        }
        return retorno;
    }
        
    //abortos.
    
    double periodoSeco(){
        double retorno=0;
        double contadorOperaciones=0;
        double promedioDias=0;
        
        TCUGanaderosPUEntityManager.clear();
        DBManager dbm = new DBManager();
        
        vacas_queryS = "SELECT " +
        " bovino "+
        " FROM " +
        " Bovino bovino," +
        " Finca finca " +
        " WHERE " +
        " bovino.idFinca.id = finca.id AND" +
        " bovino.esToro = 0 AND" +
        " bovino IN (SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL ) "
                + cond_finca;

        
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_queryS);
        vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();
        
        for (int i = 0; i < vacasList.size(); i++){
            Bovino vaca_temp = (Bovino) vacasList.get(i);
            int vaca_id= vaca_temp.getId();
            
            java.util.List<ucr.ac.cr.ecci.Models.Parto> partosList;
            javax.persistence.Query partosQuery;

            java.util.List<ucr.ac.cr.ecci.Models.Destete> destetesList;
            javax.persistence.Query destetesQuery;
            
            partosQuery = TCUGanaderosPUEntityManager.createQuery("SELECT p FROM Parto p WHERE p.idMadre.id = " + vaca_id + " ORDER BY p.fecha ASC");
            partosList = partosQuery.getResultList();
            
            destetesQuery = TCUGanaderosPUEntityManager.createQuery("SELECT d FROM Destete d WHERE d.idBovino.id = " + vaca_id +" ORDER BY d.fecha ASC");
            destetesList =destetesQuery.getResultList();
            
            int tamDestetes= destetesList.size();
            int tamPartos = partosList.size();
            
            if(tamDestetes !=0 && tamPartos!=0){
                boolean terminar=false;
                int contDestetes=0;
                int contPartos=0;
         
                while(!terminar){
                    
                    Destete desteteAux= destetesList.get(contDestetes);
                    Parto partoAux= partosList.get(contPartos);
                    
                    Calendar desteteFecha = Calendar.getInstance();
                    desteteFecha.setTime(desteteAux.getFecha());
                    
                    Calendar partoFecha = Calendar.getInstance();
                    partoFecha.setTime(partoAux.getFecha());
                    
                    if(desteteFecha.compareTo(partoFecha)<0){//Si el destete ocurrió antes que el parto.
                        
                        int dias= getIntervaloFechas(desteteFecha, partoFecha);
                        if(dias>0){
                            promedioDias+=dias;
                        }
                        contDestetes++;
                        contadorOperaciones++;
                        
                        if(contDestetes>=tamDestetes){
                            terminar=true;
                        }
                    }else{
                        contPartos++;
                        if(contPartos>=tamPartos){
                            terminar=true;
                        }
                    }
                }
            }
            
        }
        if(contadorOperaciones!=0){
            retorno= promedioDias/contadorOperaciones;
        }
        return retorno;
    }
    
    double promedioDiasPrimerCelo(){
        double retorno=0;
        double contadorOperaciones=0;
        double promedioDias=0;
        
        TCUGanaderosPUEntityManager.clear();
        DBManager dbm = new DBManager();
        
        vacas_queryS = "SELECT " +
        " bovino "+
        " FROM " +
        " Bovino bovino," +
        " Finca finca " +
        " WHERE " +
        " bovino.idFinca.id = finca.id AND" +
        " bovino.esToro = 0 AND" +
        " bovino IN (SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL ) "
                + cond_finca;

        
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_queryS);
        vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();
        
        for (int i = 0; i < vacasList.size(); i++){
            Bovino vaca_temp = (Bovino) vacasList.get(i);
            int vaca_id= vaca_temp.getId();
            
            java.util.List<ucr.ac.cr.ecci.Models.Parto> partosList;
            javax.persistence.Query partosQuery;

            java.util.List<ucr.ac.cr.ecci.Models.Celo> celosList;
            javax.persistence.Query celosQuery;
            
            partosQuery = TCUGanaderosPUEntityManager.createQuery("SELECT p FROM Parto p WHERE p.idMadre.id = " + vaca_id + " ORDER BY p.fecha ASC");
            partosList = partosQuery.getResultList();
            
            celosQuery= TCUGanaderosPUEntityManager.createQuery("SELECT c FROM Celo c WHERE c.idBovino.id = " + vaca_id + " ORDER BY c.fecha ASC");
            celosList = celosQuery.getResultList();
            
            int tamCelos= celosList.size();
            int tamPartos = partosList.size();
            
            if(tamCelos !=0 && tamPartos!=0){
                boolean terminar=false;
                int contCelos=0;
                int contPartos=0;
         
                while(!terminar){
                    
                    Celo celoAux= celosList.get(contCelos);
                    Parto partoAux= partosList.get(contPartos);
                    
                    Calendar celoFecha = Calendar.getInstance();
                    celoFecha.setTime(celoAux.getFecha());
                    
                    Calendar partoFecha = Calendar.getInstance();
                    partoFecha.setTime(partoAux.getFecha());
                    
                    if(partoFecha.compareTo(celoFecha)<0){//Si el parto ocurrió antes que el celo.
                        int dias= getIntervaloFechas(partoFecha, celoFecha);
                        if(dias>0){
                            promedioDias+=dias;
                        }
                        contPartos++;
                        contadorOperaciones++;
                        
                        if(contPartos>=tamPartos){
                            terminar=true;
                        }
                    }else{
                        contCelos++;
                        if(contCelos>=tamCelos){
                            terminar=true;
                        }
                    }
                }
            }
        }
        if(contadorOperaciones!=0){
            retorno= promedioDias/contadorOperaciones;
        }
        return retorno;
    }
    
    double setQueriesServicios(boolean cond){
        TCUGanaderosPUEntityManager.clear();
        DBManager dbm = new DBManager();
        
        vacas_queryS = "SELECT " +
        " bovino "+
        " FROM " +
        " Bovino bovino," +
        " Finca finca " +
        " WHERE " +
        " bovino.idFinca.id = finca.id AND" +
        " bovino.esToro = 0 AND" +
        " bovino IN (SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL ) "
                + cond_finca;

        
        vacasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_queryS);
        vacasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasQuery.getResultList();
        
        java.util.List<ucr.ac.cr.ecci.Models.Servicio> serviciosList;
        javax.persistence.Query serviciosQuery;
        
        double promedioFinal=0;
        int cantVacas=0;
        double promedio=0;        
        double positivos=0;
        double total=0;
        double totalPromedioConcepcion=0;
        
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
                            cantVacas++;
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
                    
                    if(total!=0){
                        totalPromedioConcepcion=  positivos/total;
                    }
                    promedioFinal+=promedio;
                    porcentajeConcepcion+=totalPromedioConcepcion;
                    
                }
            promedio=0;
            total=0;
            positivos=0;
            totalPromedioConcepcion=0;
        }
        if(promedioFinal!=0){
            promedioFinal=promedioFinal/cantVacas;
            porcentajeConcepcion= (porcentajeConcepcion/cantVacas)*100;//PORCENTAJE DE CONCEPCION TOTAL.
        }else{
            promedioFinal=0;
        }
        
    return promedioFinal; 
    }
    
    void setQueriesGeneral() {
        TCUGanaderosPUEntityManager.clear();
        vacas_activas_query = "SELECT " +
        " bovino "+
        " FROM " +
        " Bovino bovino," +
        " Finca finca " +
        " WHERE " +
        " bovino.idFinca.id = finca.id AND" +
        " bovino.esToro = 0 AND" +
        " bovino IN (SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL ) "
                + cond_finca;
        vacasActivasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_activas_query);
        vacasActivasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasActivasQuery.getResultList();
        vacas_con_partos_query = "SELECT " +
        " bovino "+
        " FROM " +
        " Bovino bovino," +
        " Finca finca " +
        " WHERE " +
        " bovino.idFinca.id = finca.id AND" +
        " bovino.esToro = 0 AND" +
        " bovino IN (SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE baja.idBovino IS NULL ) AND" +
        " bovino.partoCollection IS NOT EMPTY"
                + cond_finca;
        vacasActivasPartosQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_con_partos_query);
        vacasActivasPartosList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasActivasPartosQuery.getResultList();
    }
    
    void setQueriesFechas() {
        TCUGanaderosPUEntityManager.clear();
        vacasActivasFechasQuery = null;
        vacas_activas_fechas_query = "SELECT " +
        " bovino "+
        " FROM " +
        " Bovino bovino," +
        " Finca finca " +
        " WHERE " +
        " bovino.idFinca.id = finca.id AND" +
        " bovino.esToro = 0 AND" +
        " bovino IN (SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE (baja.fecha > '" + cond_fecha_desde + "') OR (baja.idBovino IS NULL)) "
                + cond_finca + " AND " +
        " bovino.fechaNacimiento < '" + cond_fecha_hasta + "'";
        vacasActivasFechasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_activas_fechas_query);
        vacasActivasFechasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasActivasFechasQuery.getResultList();
        vacas_con_partos_fechas_query = "SELECT " +
        " bovino "+
        " FROM " +
        " Bovino bovino," +
        " Finca finca " +
        " WHERE " +
        " bovino.idFinca.id = finca.id AND" +
        " bovino.esToro = 0 AND" +
        " bovino IN (SELECT b FROM Bovino b LEFT JOIN Baja baja ON b.id=baja.idBovino WHERE (baja.fecha > '" + cond_fecha_desde + "') OR (baja.idBovino IS NULL)) AND" +
        " bovino.partoCollection IS NOT EMPTY"
                + cond_finca + " AND " +
        " bovino.fechaNacimiento < '" + cond_fecha_hasta + "'";
        vacasActivasPartosFechasQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery(vacas_con_partos_fechas_query);
        vacasActivasPartosFechasList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : vacasActivasPartosFechasQuery.getResultList();
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
    
    void setFormGeneral(){
        //this.setSize(this.getWidth(), 350);
        desdeLabel.setVisible(false);
        desdeDatePicker.setVisible(false);
        hastaLabel.setVisible(false);
        hastaDatePicker.setVisible(false);
        ultimoLabel.setVisible(false);
        ultimoPorcentajeParicionTextField.setVisible(false);
        
        
        
        //Set fields
        setQueriesGeneral();
        
        //IEP promedio historico
        iep_prom = 0;
        ultimo_iep_prom = 0;
        cantidad_partos_activas = 0;
         for (int i = 0; i < vacasActivasList.size(); i++){
            Bovino vaca_temp = (Bovino) vacasActivasList.get(i);
            Object[] partosArray = vaca_temp.getPartoCollection().toArray();
            cantidad_partos_activas += partosArray.length;
            int iep = 0;
            int ultimo_iep = 0;
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
                    if (k == 1) {
                        ultimo_iep = iep;
                    }
                }
                iep = iep/(k-1);
            }
            iep_prom += iep;
            ultimo_iep_prom += ultimo_iep;
         }
         if (!vacasActivasList.isEmpty()) {
            iep_prom = iep_prom/vacasActivasList.size();
            ultimo_iep_prom = ultimo_iep_prom/vacasActivasList.size();
         }

        
        
        hembrasConsideradasTextField.setText(Integer.toString(vacasActivasList.size()));
        
        hembrasParidoTextField.setText(Integer.toString(vacasActivasPartosList.size()));
        
        iepPromedioTextField.setText(Integer.toString(iep_prom));
        
        serviciosConcepcionTextField.setText("0");

        
        double vacas_activas = (double) vacasActivasList.size();
        double vacas_partos_activas = (double) vacasActivasPartosList.size();
        double porcentaje_paricion = (vacas_partos_activas/vacas_activas)*100;
        String porcentaje_paricion_s = Double.toString(porcentaje_paricion);
        porcentaje_paricion_s = porcentaje_paricion_s.substring(0, Math.min(porcentaje_paricion_s.length(), 5));
        if (porcentaje_paricion_s.equals("NaN")){
            porcentaje_paricion_s = "0";
        }
        porcentajeParicionTextField.setText(porcentaje_paricion_s);
        
        ultimoIEPTextField.setText(Integer.toString(ultimo_iep_prom));
        
        String promedio_partos_hato_s = Double.toString((double)cantidad_partos_activas/vacas_activas);
        promedio_partos_hato_s = promedio_partos_hato_s.substring(0, Math.min(promedio_partos_hato_s.length(), 5));
        if (promedio_partos_hato_s.equals("NaN"))
            promedio_partos_hato_s = "0";
        promedioPartosHatoTextField.setText(promedio_partos_hato_s);
        
        SC=setQueriesServicios(false);
        serviciosConcepcionTextField.setText(Double.toString(SC));
        promedioDiasPrimerCelo();
        periodoSeco();
        promedioAbortos();
    }
    
    void setFormPorFechas(){//POR FECHAS
        //this.setSize(this.getWidth(), this.getHeight());
        desdeLabel.setVisible(true);
        desdeDatePicker.setVisible(true);
        hastaLabel.setVisible(true);
        hastaDatePicker.setVisible(true);
        ultimoLabel.setVisible(true);
        ultimoPorcentajeParicionTextField.setVisible(true);
        
        Date fechaDesde = desdeDatePicker.getDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cond_fecha_desde = df.format(fechaDesde);
        Date fechaHasta = hastaDatePicker.getDate();
        cond_fecha_hasta = df.format(fechaHasta);
        
        setQueriesFechas();
        
        hembrasConsideradasTextField.setText(Integer.toString(vacasActivasFechasList.size()));
        
        int vacas_partos_fechas = 0;
        for (int i = 0; i < vacasActivasPartosFechasList.size(); i++) {                  
            Bovino vaca_temp = (Bovino) vacasActivasPartosFechasList.get(i);
//            vaca_temp.getPartoCollection().removeIf((Parto e) -> e.getFecha().after(fechaHasta) || e.getFecha().before(fechaDesde));
//            for (Iterator<Parto> iterator = vaca_temp.getPartoCollection().iterator(); iterator.hasNext();) {
//                if (iterator.next().getFecha().after(fechaHasta) || iterator.next().getFecha().before(fechaDesde)) {
//                    vaca_temp.getPartoCollection().remove(iterator.next());
//                }
//            } 
            Object [] partos_array =  vaca_temp.getPartoCollection().toArray();
            for (Object o : partos_array) {
                Parto parto = (Parto) o;
                if ((parto.getFecha().before(fechaDesde) || parto.getFecha().after(fechaHasta))) {
                    vaca_temp.getPartoCollection().remove(parto);
                }
            }
            if (!vaca_temp.getPartoCollection().isEmpty()){
                vacas_partos_fechas++;
            }
        }    
        hembrasParidoTextField.setText(Integer.toString(vacas_partos_fechas));        
        
        //IEP promedio historico
        iep_prom = 0;
        ultimo_iep_prom = 0;
        cantidad_partos_activas = 0;
         for (int i = 0; i < vacasActivasPartosFechasList.size(); i++){
            Bovino vaca_temp = (Bovino) vacasActivasPartosFechasList.get(i);
            Object[] partosArray = vaca_temp.getPartoCollection().toArray();
            cantidad_partos_activas += partosArray.length;
            int iep = 0;
            int ultimo_iep = 0;
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
                    if (k == 1) {
                        ultimo_iep = iep;
                    }
                }
                iep = iep/(k-1);
            }
            iep_prom += iep;
            ultimo_iep_prom += ultimo_iep;
         }
         
        if (!vacasActivasPartosList.isEmpty()) {
            iep_prom = iep_prom/vacasActivasPartosFechasList.size();
            ultimo_iep_prom = ultimo_iep_prom/vacasActivasPartosFechasList.size();
         }

         
        iepPromedioTextField.setText(Integer.toString(iep_prom));
         
        double vacas_activas = (double) vacasActivasFechasList.size();
        double vacas_partos_activas = (double) vacas_partos_fechas;
        double porcentaje_paricion = (vacas_partos_activas/vacas_activas)*100;
        String porcentaje_paricion_s = Double.toString(porcentaje_paricion);
        porcentaje_paricion_s = porcentaje_paricion_s.substring(0, Math.min(porcentaje_paricion_s.length(), 5));
        if (porcentaje_paricion_s.equals("NaN")){
            porcentaje_paricion_s = "0";
        }
        porcentajeParicionTextField.setText(porcentaje_paricion_s);
        
        ultimoIEPTextField.setText(Integer.toString(ultimo_iep_prom));
        
        String promedio_partos_hato_s = Double.toString((double)cantidad_partos_activas/vacas_activas);
        promedio_partos_hato_s = promedio_partos_hato_s.substring(0, Math.min(promedio_partos_hato_s.length(), 5));
        promedioPartosHatoTextField.setText(promedio_partos_hato_s);
        if (promedio_partos_hato_s.equals("NaN"))
            promedio_partos_hato_s = "0";
        
        double cant_vacas_palpaciones_confirmadas_fechas = 0;
        for (Object b : vacasActivasPartosFechasList) {
            boolean palpacion_positiva = false;
            Bovino bovino = (Bovino) b;
            for (Object p : bovino.getPalpacionCollection()){
                Palpacion palpacion = (Palpacion) p;
                
                if (palpacion.getConfirmacion().equals("Positiva") && (palpacion.getFecha().after(fechaDesde) || palpacion.getFecha().before(fechaHasta))){
                    palpacion_positiva = true;
                }
            }
            if (palpacion_positiva){
                cant_vacas_palpaciones_confirmadas_fechas++;
            }
        }
        if (cant_vacas_palpaciones_confirmadas_fechas != 0){
            ultimoPorcentajeParicionTextField.setText(Double.toString((vacas_partos_activas/cant_vacas_palpaciones_confirmadas_fechas)*100));
        } else ultimoPorcentajeParicionTextField.setText("0");   
        
        SC=setQueriesServicios(true);
        serviciosConcepcionTextField.setText(Double.toString(SC));
    }

       
    void generarReporteGeneral() throws FileNotFoundException{
        
        Calendar hoyCalendar = Calendar.getInstance();
        Date hoyDate = hoyCalendar.getTime();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_hoy_s = df.format(hoyDate);

        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); 

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(" Resumen ");

        //Create row object
        XSSFRow row;

        //Create styles
        XSSFCellStyle resumen_style = workbook.createCellStyle();
        resumen_style.setBorderBottom(BorderStyle.THIN);
        resumen_style.setBorderLeft(BorderStyle.THIN);
        resumen_style.setBorderRight(BorderStyle.THIN);
        resumen_style.setBorderTop(BorderStyle.THIN);
        
        XSSFCellStyle resumen_title_style = workbook.createCellStyle();
        resumen_title_style.setBorderBottom(BorderStyle.THIN);
        resumen_title_style.setBorderLeft(BorderStyle.THIN);
        resumen_title_style.setBorderRight(BorderStyle.THIN);
        resumen_title_style.setBorderTop(BorderStyle.THIN);
        //String rgbS = "D0D0D0";
        String rgbS = "4C9900";
        byte[] rgbB = null;
        try {
            rgbB = Hex.decodeHex(rgbS); // get byte array from hex string
        } catch (DecoderException ex) {
            Logger.getLogger(ResumenUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        XSSFColor color = new XSSFColor(rgbB, null); //IndexedColorMap has no usage until now. So it can be set null.
        resumen_title_style.setFillForegroundColor(color);
        resumen_title_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        resumen_title_style.setVerticalAlignment(VerticalAlignment.CENTER);
        resumen_title_style.setAlignment(HorizontalAlignment.CENTER);
        resumen_title_style.setWrapText(true);
        
        //This data needs to be written (Object[])
        Map < Integer, Object[] > empinfo = new TreeMap < Integer, Object[] >();
        empinfo.put( 1, new Object[] {"","","","   REPORTE GENERAL DE LA FINCA GANADERA"});
        empinfo.put( 2, new Object[] {"Fecha de reporte: ", fecha_hoy_s});
        empinfo.put( 3, new Object[] {"Hembras consideradas: ", hembrasConsideradasTextField.getText()});
        empinfo.put( 4, new Object[] {"Hembras que han parido: ", hembrasParidoTextField.getText()});
        empinfo.put( 5, new Object[] {"IEP promedio histórico (días): ", iepPromedioTextField.getText()});
        empinfo.put( 6, new Object[] {"Porcentaje parición histórico: ", porcentajeParicionTextField.getText()});
        empinfo.put( 7, new Object[] {"Promedio último IEP de cada vaca (días): ", ultimoIEPTextField.getText()});
        empinfo.put( 8, new Object[] {"Promedio de partos hato: ", promedioPartosHatoTextField.getText()});
        empinfo.put( 9, new Object[] {"Promedio de servicios por concepción: ", serviciosConcepcionTextField.getText()});
        empinfo.put( 10, new Object[] {"Promedio de dias al primer celo (General): ", Double.toString(promedioDiasPrimerCelo())});
        empinfo.put( 11, new Object[] {"Promedio periodo seco (General): ", Double.toString (periodoSeco())});
        empinfo.put( 12, new Object[] {"Promedio de abortos (General): ", Double.toString(promedioAbortos())});
        empinfo.put( 13, new Object[] {""});
        empinfo.put( 14, new Object[] {"Orden","Nombre", "Número trazable", "Edad a primer parto (meses)", "Partos", "Edad de la última cría (meses)",
        "Fecha destete a 7 meses, última cría", "IEP Promedio (días)", "Último IEP (días)", "Último celo", "Gestación (días)", "Parto"});
        
        int[][] data_type = {{1,1,1,1},{1,1,1},{1,1,2},{1,1,2},{1,1,2},{1,1,2},{1,1,2},{1,1,2},{1,1,2},{1,1,2},{1,1,2},{1,1,2},
            {1},{1,1,1,1,1,1,1,1,1,1,1,1}};
        
        //Llenar tabla
        for (int i = 0; i < vacasActivasList.size(); i++){
            int fila = i+15;
            Bovino vaca = (Bovino) vacasActivasList.get(i);
            String orden = Integer.toString(i+1);
            String nombre = vaca.getNombre();
            String id = Double.toString((double) vaca.getId());
            String edad_primer_parto = "";
            String edad_ultima_cria = "";
            if (!vaca.getPartoCollection().isEmpty()){
                Object[] parto_array = vaca.getPartoCollection().toArray();
                Parto primer_parto = (Parto) parto_array[0];
                Parto ultimo_parto = (Parto) parto_array[parto_array.length-1];
                Calendar vaca_fecha = Calendar.getInstance();
                Calendar primer_parto_fecha = Calendar.getInstance();
                Calendar ultimo_parto_fecha = Calendar.getInstance();
                if (vaca.getFechaNacimiento()== null){
                        Calendar calndr1 = Calendar.getInstance();
                        calndr1.set(Calendar.MONTH, 1); 
                        calndr1.set(Calendar.YEAR, 2000); 
                        calndr1.set(Calendar.DAY_OF_WEEK, 1); 
                        Date dt = calndr1.getTime(); 
                        vaca_fecha.setTime(dt);
                }else{
                    vaca_fecha.setTime(vaca.getFechaNacimiento());
                }  
                primer_parto_fecha.setTime(primer_parto.getFecha());
                ultimo_parto_fecha.setTime(ultimo_parto.getFecha());
                double edad_a_primer_parto = (double) getIntervaloFechas( vaca_fecha, primer_parto_fecha)/30;
                double edad_de_ultimo_parto = (double) getIntervaloFechas( ultimo_parto_fecha, hoyCalendar)/30;
                edad_primer_parto = Double.toString(edad_a_primer_parto);
                edad_primer_parto = edad_primer_parto.substring(0, Math.min(edad_primer_parto.length(), 5));
                edad_ultima_cria = Double.toString(edad_de_ultimo_parto);
                edad_ultima_cria = edad_ultima_cria.substring(0, Math.min(edad_ultima_cria.length(), 5));
            }
            String partos = Integer.toString(vaca.getPartoCollection().size());
            String ultimo_destete = "";
            if (!vaca.getDesteteCollection().isEmpty()){
                Object [] destetes_array = vaca.getDesteteCollection().toArray();
                Destete destete = (Destete) destetes_array[destetes_array.length-1];
                ultimo_destete = df.format(destete.getFecha());
            }
            String iep_s = "";
            String ultimo_iep_s = "";
            if (vaca.getPartoCollection().size() >= 2){
                int iep = 0;
                int ultimo_iep = 0;
                Object[] parto_array = vaca.getPartoCollection().toArray();
                for (int j = 0; j < parto_array.length-1; j++){
                    Parto p1 = (Parto) parto_array[j];
                    Parto p2 = (Parto) parto_array[j+1];
                    Calendar p1fecha = Calendar.getInstance();
                    Calendar p2fecha = Calendar.getInstance();
                    p1fecha.setTime(p1.getFecha());
                    p2fecha.setTime(p2.getFecha());
                    iep += getIntervaloFechas( p1fecha, p2fecha);
                    if (j+1 == parto_array.length-1){
                        p1fecha.setTime(p1.getFecha());
                        p2fecha.setTime(p2.getFecha());
                        ultimo_iep = getIntervaloFechas( p1fecha, p2fecha);
                    }
                }
                iep = iep/(parto_array.length-1);
                iep_s = Integer.toString(iep);
                ultimo_iep_s = Integer.toString(ultimo_iep);
            }
            String ultimo_celo = "";
            if (!vaca.getCeloCollection().isEmpty()){
                Object[] celos_array = vaca.getCeloCollection().toArray();
                Celo celo = (Celo) celos_array[celos_array.length-1];
                ultimo_celo = df.format(celo.getFecha());
            }
            String gestacion = "";
            boolean embarazada = false;
            if (!ultimo_celo.equals("")){
                int gestacion_dias = 0;
                if (!vaca.getPartoCollection().isEmpty()){
                    Object[] partos_array = vaca.getPartoCollection().toArray();
                    Object[] celos_array = vaca.getCeloCollection().toArray();
                    Parto parto = (Parto) partos_array[partos_array.length-1];
                    Celo celo = (Celo) celos_array[celos_array.length-1];
                    Calendar parto_fecha = Calendar.getInstance();
                    Calendar celo_fecha = Calendar.getInstance();
                    parto_fecha.setTime(parto.getFecha());
                    celo_fecha.setTime(celo.getFecha());
                    if (celo_fecha.before(parto_fecha)){
                        gestacion_dias = getIntervaloFechas( celo_fecha, parto_fecha);
                    } else {
                        gestacion_dias = getIntervaloFechas( celo_fecha, hoyCalendar);
                        embarazada = true;
                    }
                    gestacion = Integer.toString(gestacion_dias);
                }
            }
            String fecha_parto = "";
            if (!embarazada && !vaca.getPartoCollection().isEmpty()) {
                Object[] partos_array = vaca.getPartoCollection().toArray();
                Parto parto = (Parto) partos_array[partos_array.length-1];
                Calendar parto_fecha = Calendar.getInstance();
                parto_fecha.setTime((Date)parto.getFecha());
                fecha_parto = df.format(parto.getFecha());
                //fecha_parto = df.format(parto_fecha);
            }
            
            empinfo.put( fila, new Object[] {orden, nombre, id, edad_primer_parto, partos, edad_ultima_cria, ultimo_destete, iep_s, ultimo_iep_s, ultimo_celo, gestacion, fecha_parto });
            
            int data_types_vaca[] = new int[12];
            data_types_vaca[0] = 2;
            data_types_vaca[1] = 1;
            data_types_vaca[2] = 2;
            if (edad_primer_parto.equals(""))
                data_types_vaca[3] = 1;
            else data_types_vaca[3] = 2;
            data_types_vaca[4] = 2;
            if (edad_ultima_cria.equals(""))
                data_types_vaca[5] = 1;
            else data_types_vaca[5] = 2;
            data_types_vaca[6] = 1;
            if (iep_s.equals(""))
                data_types_vaca[7] = 1;
            else data_types_vaca[7] = 2;
            if (ultimo_iep_s.equals(""))
                data_types_vaca[8] = 1;
            else data_types_vaca[8] = 2;
            data_types_vaca[9] = 1;
            if (gestacion.equals(""))
                data_types_vaca[10] = 1;
            else data_types_vaca[10] = 2;
            data_types_vaca[11] = 1;
            
            data_type = ArrayUtils.add(data_type, data_types_vaca);
        }
        

        //Iterate over data and write to sheet
        Set < Integer > keyid = empinfo.keySet();
        int rowid = 0;

        for (Integer key : keyid) {
           row = spreadsheet.createRow(rowid++);
           Object [] objectArr = empinfo.get(key);
           int cellid = 0;

           for (Object obj : objectArr) {
              XSSFCell cell = row.createCell(cellid++);

              if (data_type[rowid-1][cellid-1] == 2) {
                  cell.setCellValue(Double.parseDouble((String)obj));
              } else cell.setCellValue((String)obj);

              
              // Estilo area resumen
              if (rowid > 1 && rowid <= 12){
                  if (cellid == 1){
                      cell.setCellStyle(resumen_title_style);
                  } else if (cellid == 2){
                      cell.setCellStyle(resumen_style);
                  } 
                  if (rowid == 5 || rowid == 7){
                      row.setHeightInPoints((2 * spreadsheet.getDefaultRowHeightInPoints()));
                  } else {
                      row.setHeightInPoints(spreadsheet.getDefaultRowHeightInPoints());
                  }
              } else if (rowid == 14){
                  row.setHeightInPoints((2 * spreadsheet.getDefaultRowHeightInPoints()));
                  cell.setCellStyle(resumen_title_style);
              }
              
           }

        }

        //Resize
        for (int i = 0; i <= 14; i++) {
            spreadsheet.setColumnWidth(i, 5000);
        }
        spreadsheet.autoSizeColumn(0);
        spreadsheet.autoSizeColumn(1);
        spreadsheet.autoSizeColumn(2);
        spreadsheet.autoSizeColumn(4);
        spreadsheet.autoSizeColumn(7);
        spreadsheet.autoSizeColumn(8);
        spreadsheet.autoSizeColumn(9);
        spreadsheet.autoSizeColumn(10);
        spreadsheet.autoSizeColumn(11);
        spreadsheet.autoSizeColumn(12);
        spreadsheet.autoSizeColumn(13);
        spreadsheet.autoSizeColumn(14);
        
        
        

        String documentsPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Reportes";
        DateFormat dfh = new SimpleDateFormat("yyyy-MM-dd_HHmm");
        String nombre_archivo = dfh.format(hoyDate) + "_Reporte_General.xlsx";
        File file = new File(documentsPath);
        file.mkdir();

        
          //write operation workbook using file out object
          try ( //Create file system using specific name
              FileOutputStream out = new FileOutputStream(new File(documentsPath + "\\"+ nombre_archivo))) {
              //write operation workbook using file out object
              workbook.write(out);
              JOptionPane.showMessageDialog(null, "Reporte guardado en " + file.getAbsolutePath());
          } catch (IOException ex) {
              Logger.getLogger(ResumenUI.class.getName()).log(Level.SEVERE, null, ex);
          }    
    }
    
    void generarReporteFechas() throws FileNotFoundException{
        
        Calendar hoyCalendar = Calendar.getInstance();
        Date hoyDate = hoyCalendar.getTime();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_hoy_s = df.format(hoyDate);
        
        Date fecha_desde = desdeDatePicker.getDate();
        Date fecha_hasta = hastaDatePicker.getDate();
        
        String fecha_desde_s = df.format(fecha_desde);
        String fecha_hasta_s = df.format(fecha_hasta);
        

        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); 

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(" Resumen ");

        //Create row object
        XSSFRow row;

        //Create styles
        XSSFCellStyle resumen_style = workbook.createCellStyle();
        resumen_style.setBorderBottom(BorderStyle.THIN);
        resumen_style.setBorderLeft(BorderStyle.THIN);
        resumen_style.setBorderRight(BorderStyle.THIN);
        resumen_style.setBorderTop(BorderStyle.THIN);
        
        XSSFCellStyle resumen_title_style = workbook.createCellStyle();
        resumen_title_style.setBorderBottom(BorderStyle.THIN);
        resumen_title_style.setBorderLeft(BorderStyle.THIN);
        resumen_title_style.setBorderRight(BorderStyle.THIN);
        resumen_title_style.setBorderTop(BorderStyle.THIN);
        String rgbS = "4C9900";
        byte[] rgbB = null;
        try {
            rgbB = Hex.decodeHex(rgbS); // get byte array from hex string
        } catch (DecoderException ex) {
            Logger.getLogger(ResumenUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        XSSFColor color = new XSSFColor(rgbB, null); //IndexedColorMap has no usage until now. So it can be set null.
        resumen_title_style.setFillForegroundColor(color);
        resumen_title_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        resumen_title_style.setVerticalAlignment(VerticalAlignment.CENTER);
        resumen_title_style.setAlignment(HorizontalAlignment.CENTER);
        resumen_title_style.setWrapText(true);

        
 

        //This data needs to be written (Object[])
        Map < Integer, Object[] > empinfo = new TreeMap < Integer, Object[] >();
        empinfo.put( 1, new Object[] {"","","","REPORTE GENERAL POR FECHAS DE LA FINCA GANADERA"});
        empinfo.put( 2, new Object[] {"Periodo: ", "Desde " + fecha_desde_s + " hasta " + fecha_hasta_s});
        empinfo.put( 3, new Object[] {"Hembras consideradas: ", hembrasConsideradasTextField.getText()});
        empinfo.put( 4, new Object[] {"Hembras que han parido: ", hembrasParidoTextField.getText()});
        empinfo.put( 5, new Object[] {"IEP promedio histórico (días): ", iepPromedioTextField.getText()});
        empinfo.put( 6, new Object[] {"Porcentaje de parición histórico: ", porcentajeParicionTextField.getText()});
        empinfo.put( 7, new Object[] {"Promedio último IEP de cada vaca (días): ", ultimoIEPTextField.getText()});
        empinfo.put( 8, new Object[] {"Promedio de partos hato: ", promedioPartosHatoTextField.getText()});
        empinfo.put( 9, new Object[] {"Ultimo % parición: ", ultimoPorcentajeParicionTextField.getText()});
        empinfo.put( 10, new Object[] {""});
        empinfo.put( 11, new Object[] {"Orden","Nombre", "Número trazable", "Edad a primer parto (meses)", "Partos", "Edad de la última cría (meses)",
        "Fecha destete a 7 meses, última cría", "IEP Promedio (días)", "Último IEP (días)", "Último celo", "Gestación (días)", "Parto"});

        int[][] data_type = {{1,1,1,1},{1,1,1},{1,1,2},{1,1,2},{1,1,2},{1,1,2},{1,1,2},{1,1,2},{1,1,2},
            {1},{1,1,1,1,1,1,1,1,1,1,1,1}};
        
        //Llenar tabla
        for (int i = 0; i < vacasActivasFechasList.size(); i++){
            int fila = i+12;
            Bovino vaca = (Bovino) vacasActivasFechasList.get(i);
            String orden = Integer.toString(i+1);
            String nombre = vaca.getNombre();
            String id = Double.toString((double) vaca.getId());
            String edad_primer_parto = "";
            String edad_ultima_cria = "";
            if (!vaca.getPartoCollection().isEmpty()){
                Object[] parto_array = vaca.getPartoCollection().toArray();
                Parto primer_parto = (Parto) parto_array[0];
                Parto ultimo_parto = (Parto) parto_array[parto_array.length-1];
                Calendar vaca_fecha = Calendar.getInstance();
                Calendar primer_parto_fecha = Calendar.getInstance();
                Calendar ultimo_parto_fecha = Calendar.getInstance();
                vaca_fecha.setTime(vaca.getFechaNacimiento());
                primer_parto_fecha.setTime(primer_parto.getFecha());
                ultimo_parto_fecha.setTime(ultimo_parto.getFecha());
                double edad_a_primer_parto = (double) getIntervaloFechas( vaca_fecha, primer_parto_fecha)/30;
                double edad_de_ultimo_parto = (double) getIntervaloFechas( ultimo_parto_fecha, hoyCalendar)/30;
                edad_primer_parto = Double.toString(edad_a_primer_parto);
                edad_primer_parto = edad_primer_parto.substring(0, Math.min(edad_primer_parto.length(), 5));
                edad_ultima_cria = Double.toString(edad_de_ultimo_parto);
                edad_ultima_cria = edad_ultima_cria.substring(0, Math.min(edad_ultima_cria.length(), 5));
            }
            String partos = Integer.toString(vaca.getPartoCollection().size());
            String ultimo_destete = "";
            if (!vaca.getDesteteCollection().isEmpty()){
                Object [] destetes_array = vaca.getDesteteCollection().toArray();    
                for(Object d : destetes_array) {
                    Destete destete = (Destete)d;
                    if (destete.getFecha().before(fecha_hasta) && destete.getFecha().after(fecha_desde)){
                        ultimo_destete = df.format(destete.getFecha());
                    }
                }                
            }
            String iep_s = "";
            String ultimo_iep_s = "";
            if (vaca.getPartoCollection().size() >= 2){
                int iep = 0;
                int ultimo_iep = 0;
                Object[] parto_array = vaca.getPartoCollection().toArray();
                for (int j = 0; j < parto_array.length-1; j++){
                    Parto p1 = (Parto) parto_array[j];
                    Parto p2 = (Parto) parto_array[j+1];
                    Calendar p1fecha = Calendar.getInstance();
                    Calendar p2fecha = Calendar.getInstance();
                    p1fecha.setTime(p1.getFecha());
                    p2fecha.setTime(p2.getFecha());
                    iep += getIntervaloFechas( p1fecha, p2fecha);
                    if (j+1 == parto_array.length-1){
                        p1fecha.setTime(p1.getFecha());
                        p2fecha.setTime(p2.getFecha());
                        ultimo_iep = getIntervaloFechas( p1fecha, p2fecha);
                    }
                }
                iep = iep/(parto_array.length-1);
                iep_s = Integer.toString(iep);
                ultimo_iep_s = Integer.toString(ultimo_iep);
            }
            String ultimo_celo = "";
            if (!vaca.getCeloCollection().isEmpty()){
                Object[] celos_array = vaca.getCeloCollection().toArray();
                for(Object c : celos_array) {
                    Celo celo = (Celo)c;
                    if (celo.getFecha().before(fecha_hasta) && celo.getFecha().after(fecha_desde)){
                        ultimo_celo = df.format(celo.getFecha());
                    }
                }        
            }
            String gestacion = "";
            boolean embarazada = false;
            if (!ultimo_celo.equals("")){
                int gestacion_dias = 0;
                if (!vaca.getPartoCollection().isEmpty()){
                    Object[] partos_array = vaca.getPartoCollection().toArray();
                    Object[] celos_array = vaca.getCeloCollection().toArray();
                    Celo ultimoCelo = new Celo();
                    Parto ultimoParto = new Parto();
                    for(Object c : celos_array) {
                        Celo celo = (Celo)c;
                        if (celo.getFecha().before(fecha_hasta) && celo.getFecha().after(fecha_desde)){
                            ultimoCelo = celo;
                        }
                    }
                    for(Object p : partos_array) {
                        Parto parto = (Parto)p;
                        if (parto.getFecha().before(fecha_hasta) && parto.getFecha().after(fecha_desde)){
                            ultimoParto = parto;
                        }
                    }
                    Calendar parto_fecha = Calendar.getInstance();
                    Calendar celo_fecha = Calendar.getInstance();
                    parto_fecha.setTime(ultimoParto.getFecha());
                    celo_fecha.setTime(ultimoCelo.getFecha());
                    if (celo_fecha.before(parto_fecha)){
                        gestacion_dias = getIntervaloFechas( celo_fecha, parto_fecha);
                    } else {
                        gestacion_dias = getIntervaloFechas( celo_fecha, hoyCalendar);
                        embarazada = true;
                    }
                    gestacion = Integer.toString(gestacion_dias);
                }
            }
            String fecha_parto = "";
            if (!embarazada && !vaca.getPartoCollection().isEmpty()) {
                Object[] partos_array = vaca.getPartoCollection().toArray();
                for(Object p : partos_array) {
                        Parto parto = (Parto)p;
                        if (parto.getFecha().before(fecha_hasta) && parto.getFecha().after(fecha_desde)){
                            Calendar parto_fecha = Calendar.getInstance();
                            parto_fecha.setTime(parto.getFecha());
                            Date date = parto_fecha.getTime();
                            fecha_parto = df.format(date);
                        }
                    }

            }
            
            empinfo.put( fila, new Object[] {orden, nombre, id, edad_primer_parto, partos, edad_ultima_cria, ultimo_destete, iep_s, ultimo_iep_s, ultimo_celo, gestacion, fecha_parto });
            
            int data_types_vaca[] = new int[12];
            data_types_vaca[0] = 2;
            data_types_vaca[1] = 1;
            data_types_vaca[2] = 2;
            if (edad_primer_parto.equals(""))
                data_types_vaca[3] = 1;
            else data_types_vaca[3] = 2;
            data_types_vaca[4] = 2;
            if (edad_ultima_cria.equals(""))
                data_types_vaca[5] = 1;
            else data_types_vaca[5] = 2;
            data_types_vaca[6] = 1;
            if (iep_s.equals(""))
                data_types_vaca[7] = 1;
            else data_types_vaca[7] = 2;
            if (ultimo_iep_s.equals(""))
                data_types_vaca[8] = 1;
            else data_types_vaca[8] = 2;
            data_types_vaca[9] = 1;
            if (gestacion.equals(""))
                data_types_vaca[10] = 1;
            else data_types_vaca[10] = 2;
            data_types_vaca[11] = 1;
            
            data_type = ArrayUtils.add(data_type, data_types_vaca);
        }
        

        //Iterate over data and write to sheet
        Set < Integer > keyid = empinfo.keySet();
        int rowid = 0;

        for (Integer key : keyid) {
           row = spreadsheet.createRow(rowid++);
           Object [] objectArr = empinfo.get(key);
           int cellid = 0;

           for (Object obj : objectArr) {
              XSSFCell cell = row.createCell(cellid++);

              if (data_type[rowid-1][cellid-1] == 2) {
                  cell.setCellValue(Double.parseDouble((String)obj));
              } else cell.setCellValue((String)obj);

              
              // Estilo area resumen
              if (rowid > 1 && rowid <= 9){
                  if (cellid == 1){
                      cell.setCellStyle(resumen_title_style);
                  } else if (cellid == 2){
                      cell.setCellStyle(resumen_style);
                  }
                  if (rowid == 5 || rowid == 7){
                      row.setHeightInPoints((2 * spreadsheet.getDefaultRowHeightInPoints()));
                  } else {
                      row.setHeightInPoints(spreadsheet.getDefaultRowHeightInPoints());
                  }
              } else if (rowid == 11){
                  row.setHeightInPoints((2 * spreadsheet.getDefaultRowHeightInPoints()));
                  cell.setCellStyle(resumen_title_style);
              }
              
           }

        }

        //Resize
        for (int i = 0; i <= 12; i++) {
            spreadsheet.setColumnWidth(i, 5000);
        }
        spreadsheet.autoSizeColumn(0);
        spreadsheet.autoSizeColumn(4);
        spreadsheet.autoSizeColumn(7);
        spreadsheet.autoSizeColumn(8);
        spreadsheet.autoSizeColumn(9);
        spreadsheet.autoSizeColumn(10);
        spreadsheet.autoSizeColumn(11);
        
        

        String documentsPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/Reportes";
        DateFormat dfh = new SimpleDateFormat("yyyy-MM-dd_HHmm");
        String nombre_archivo = dfh.format(hoyDate) + "_Reporte_Fechas.xlsx";
        File file = new File(documentsPath);
        file.mkdir();
        
          //write operation workbook using file out object
          try ( //Create file system using specific name
              FileOutputStream out = new FileOutputStream(new File(documentsPath + "\\"+ nombre_archivo))) {
              //write operation workbook using file out object
              workbook.write(out);
              JOptionPane.showMessageDialog(null, "Reporte guardado en " + file.getAbsolutePath());
          } catch (IOException ex) {
              Logger.getLogger(ResumenUI.class.getName()).log(Level.SEVERE, null, ex);
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
        fincaQuery = java.beans.Beans.isDesignTime() ? null : TCUGanaderosPUEntityManager.createQuery("SELECT f FROM Finca f");
        fincaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : fincaQuery.getResultList();
        jLabel1 = new javax.swing.JLabel();
        resumenComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ultimoLabel = new javax.swing.JLabel();
        desdeLabel = new javax.swing.JLabel();
        hastaLabel = new javax.swing.JLabel();
        desdeDatePicker = new org.jdesktop.swingx.JXDatePicker();
        hastaDatePicker = new org.jdesktop.swingx.JXDatePicker();
        hembrasConsideradasTextField = new javax.swing.JTextField();
        hembrasParidoTextField = new javax.swing.JTextField();
        iepPromedioTextField = new javax.swing.JTextField();
        porcentajeParicionTextField = new javax.swing.JTextField();
        ultimoIEPTextField = new javax.swing.JTextField();
        promedioPartosHatoTextField = new javax.swing.JTextField();
        ultimoPorcentajeParicionTextField = new javax.swing.JTextField();
        reporteButton = new javax.swing.JButton();
        volverButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        fincaComboBox = new javax.swing.JComboBox<>();
        ultimoLabel2 = new javax.swing.JLabel();
        serviciosConcepcionTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Resumen:");

        resumenComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "General", "Por fechas" }));
        resumenComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumenComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Hembras consideradas:");

        jLabel3.setText("Hembras que han parido:");

        jLabel4.setText("IEP promedio histórico (días):");

        jLabel5.setText("Porcentaje parición histórico:");

        jLabel6.setText("Promedio último IEP de cada vaca:");

        jLabel7.setText("Promedio de partos hato:");

        ultimoLabel.setText("Último porcentaje de parición:");

        desdeLabel.setText("Desde:");

        hastaLabel.setText("Hasta:");

        desdeDatePicker.setFormats(new String[]{"dd/MM/yyyy"});
        desdeDatePicker.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                desdeDatePickerFocusLost(evt);
            }
        });
        desdeDatePicker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                desdeDatePickerMouseExited(evt);
            }
        });
        desdeDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desdeDatePickerActionPerformed(evt);
            }
        });

        hastaDatePicker.setFormats(new String[]{"dd/MM/yyyy"});
        hastaDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hastaDatePickerActionPerformed(evt);
            }
        });

        hembrasConsideradasTextField.setEditable(false);
        hembrasConsideradasTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hembrasConsideradasTextFieldActionPerformed(evt);
            }
        });

        hembrasParidoTextField.setEditable(false);
        hembrasParidoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hembrasParidoTextFieldActionPerformed(evt);
            }
        });

        iepPromedioTextField.setEditable(false);
        iepPromedioTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iepPromedioTextFieldActionPerformed(evt);
            }
        });

        porcentajeParicionTextField.setEditable(false);
        porcentajeParicionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porcentajeParicionTextFieldActionPerformed(evt);
            }
        });

        ultimoIEPTextField.setEditable(false);
        ultimoIEPTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ultimoIEPTextFieldActionPerformed(evt);
            }
        });

        promedioPartosHatoTextField.setEditable(false);
        promedioPartosHatoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                promedioPartosHatoTextFieldActionPerformed(evt);
            }
        });

        ultimoPorcentajeParicionTextField.setEditable(false);
        ultimoPorcentajeParicionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ultimoPorcentajeParicionTextFieldActionPerformed(evt);
            }
        });

        reporteButton.setText("Generar Reporte");
        reporteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reporteButtonActionPerformed(evt);
            }
        });

        volverButton.setText("Volver");
        volverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("Finca:");

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

        ultimoLabel2.setText("Servicios por concepción (SC)");

        serviciosConcepcionTextField.setEditable(false);
        serviciosConcepcionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviciosConcepcionTextFieldActionPerformed(evt);
            }
        });

        jLabel9.setText("Para más información, genere un reporte.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(resumenComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fincaComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(desdeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(desdeDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hastaLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(hastaDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ultimoLabel2)
                                .addGap(76, 76, 76)
                                .addComponent(serviciosConcepcionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(ultimoLabel))
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(iepPromedioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(porcentajeParicionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ultimoIEPTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(promedioPartosHatoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ultimoPorcentajeParicionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(hembrasConsideradasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(hembrasParidoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(volverButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(reporteButton)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(resumenComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(fincaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hastaLabel)
                    .addComponent(hastaDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(desdeLabel)
                    .addComponent(desdeDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(hembrasConsideradasTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hembrasParidoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(iepPromedioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(porcentajeParicionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ultimoIEPTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(promedioPartosHatoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ultimoLabel)
                    .addComponent(ultimoPorcentajeParicionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ultimoLabel2)
                    .addComponent(serviciosConcepcionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reporteButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(volverButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hembrasConsideradasTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hembrasConsideradasTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hembrasConsideradasTextFieldActionPerformed

    private void hembrasParidoTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hembrasParidoTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hembrasParidoTextFieldActionPerformed

    private void iepPromedioTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iepPromedioTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_iepPromedioTextFieldActionPerformed

    private void porcentajeParicionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porcentajeParicionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_porcentajeParicionTextFieldActionPerformed

    private void ultimoIEPTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ultimoIEPTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ultimoIEPTextFieldActionPerformed

    private void promedioPartosHatoTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_promedioPartosHatoTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_promedioPartosHatoTextFieldActionPerformed

    private void ultimoPorcentajeParicionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ultimoPorcentajeParicionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ultimoPorcentajeParicionTextFieldActionPerformed

    private void resumenComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumenComboBoxActionPerformed
        if (resumenComboBox.getSelectedIndex() == 0){
            setFormGeneral();
        } else {
            setFormPorFechas();
        }
    }//GEN-LAST:event_resumenComboBoxActionPerformed

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButtonActionPerformed
        dispose();
    }//GEN-LAST:event_volverButtonActionPerformed

    private void fincaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fincaComboBoxActionPerformed
        if (!fincaComboBox.getSelectedItem().toString().equals("Todas")) {
            this.finca = fincaList.get(fincaComboBox.getSelectedIndex()-1);
            cond_finca = " AND finca.id = " + Integer.toString(finca.getId()) ;
        } else {
            cond_finca = "";
            this.finca = null;
        }
        
        //Redraw form
        if (resumenComboBox.getSelectedIndex() == 0){
            setFormGeneral();
        } else {
            setFormPorFechas();
        }
    }//GEN-LAST:event_fincaComboBoxActionPerformed

    private void desdeDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desdeDatePickerActionPerformed
        setFormPorFechas();
    }//GEN-LAST:event_desdeDatePickerActionPerformed

    private void hastaDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hastaDatePickerActionPerformed
        setFormPorFechas();
    }//GEN-LAST:event_hastaDatePickerActionPerformed

    private void desdeDatePickerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_desdeDatePickerFocusLost
        setFormPorFechas();
    }//GEN-LAST:event_desdeDatePickerFocusLost

    private void desdeDatePickerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_desdeDatePickerMouseExited
        setFormPorFechas();
    }//GEN-LAST:event_desdeDatePickerMouseExited

    private void reporteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reporteButtonActionPerformed
        if (resumenComboBox.getSelectedIndex() == 0){
            try {
                generarReporteGeneral();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ResumenUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                generarReporteFechas();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ResumenUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_reporteButtonActionPerformed

    private void serviciosConcepcionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviciosConcepcionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviciosConcepcionTextFieldActionPerformed

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
            java.util.logging.Logger.getLogger(ResumenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResumenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResumenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResumenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResumenUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager TCUGanaderosPUEntityManager;
    private org.jdesktop.swingx.JXDatePicker desdeDatePicker;
    private javax.swing.JLabel desdeLabel;
    private javax.swing.JComboBox<String> fincaComboBox;
    private java.util.List<ucr.ac.cr.ecci.Models.Finca> fincaList;
    private javax.persistence.Query fincaQuery;
    private org.jdesktop.swingx.JXDatePicker hastaDatePicker;
    private javax.swing.JLabel hastaLabel;
    private javax.swing.JTextField hembrasConsideradasTextField;
    private javax.swing.JTextField hembrasParidoTextField;
    private javax.swing.JTextField iepPromedioTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField porcentajeParicionTextField;
    private javax.swing.JTextField promedioPartosHatoTextField;
    private javax.swing.JButton reporteButton;
    private javax.swing.JComboBox<String> resumenComboBox;
    private javax.swing.JTextField serviciosConcepcionTextField;
    private javax.swing.JTextField ultimoIEPTextField;
    private javax.swing.JLabel ultimoLabel;
    private javax.swing.JLabel ultimoLabel2;
    private javax.swing.JTextField ultimoPorcentajeParicionTextField;
    private javax.swing.JButton volverButton;
    // End of variables declaration//GEN-END:variables
}
