/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci;

import ucr.ac.cr.ecci.Models.Vaca;
import ucr.ac.cr.ecci.Models.Finca;
import ucr.ac.cr.ecci.Models.Bovino;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ucr.ac.cr.ecci.Models.Celo;
import ucr.ac.cr.ecci.Models.Configuracion;
import ucr.ac.cr.ecci.Models.Destete;
import ucr.ac.cr.ecci.Models.ExamenAndrologico;
import ucr.ac.cr.ecci.Models.ModoPreñez;
import ucr.ac.cr.ecci.Models.Palpacion;
import ucr.ac.cr.ecci.Models.Parto;
import ucr.ac.cr.ecci.Models.Raza;
import ucr.ac.cr.ecci.Models.Servicio;
//import ucr.ac.cr.ecci.Models.ServicioPK;

/**
 *
 * @author afg
 */
public class DBManager {
    EntityManagerFactory emf;
    EntityManager em;
    Object obj;
    
    public DBManager() {
        emf = Persistence.createEntityManagerFactory("TCUGanaderosPU");
        em = emf.createEntityManager();
    }
    
    public DBManager(Object obj) {
        emf = Persistence.createEntityManagerFactory("TCUGanaderosPU");
        em = emf.createEntityManager();
        this.obj = obj;
    }
    
    public void close() {
        em.close();
        emf.close();
    }
    
    public void insert(Object obj) {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
    }
    
    public Finca findFincaById(int id) {
        Finca f = em.find(Finca.class, id);
        return f;
    }
    
    public Vaca findVacaById(int id){
        Vaca v= em.find(Vaca.class,id);
        return v;
    } 
    
    public Bovino findBovinoById(int id) {
        Bovino b = em.find(Bovino.class, id);
        return b;
    }
    
    public Bovino findToroById(int id){
        Bovino aux = null; 
        
        Bovino b = em.find(Bovino.class, id);
        if(b!=null){
          if(b.getEsToro()==1){
             aux=b;
            }
        }
        return aux;
    }
    
    public Parto findPartoById(int id) {
        Parto p = em.find(Parto.class, id);
        return p;
    }
    
    public Palpacion findPalpacionById(int id) {
        Palpacion p = em.find(Palpacion.class, id);
        return p;
    }
    
    public Celo findCeloById(int id) {
        Celo c = em.find(Celo.class, id);
        return c;
    }
    
    public Configuracion findConfiguracionById(int id) {
        Configuracion c = em.find(Configuracion.class, id);
        return c;
    }
    
    public Destete findDesteteById(int id) {
        Destete d = em.find(Destete.class, id);
        return d;
    }
    
    public ExamenAndrologico findExamenById(int id) {
        ExamenAndrologico e = em.find(ExamenAndrologico.class, id);
        return e;
    }
    
    public Raza findRazaById(int id) {
        Raza r = em.find(Raza.class, id);
        return r;
    }
    
   public Servicio findServicioById(int id){//BUSCA SERVICIO
       Servicio s= em.find(Servicio.class, id);
       return s;
   }
   
    public void updateServicio (Servicio servicio){
        System.out.println(servicio.getTipoServicio());
        Servicio s = em.find(Servicio.class, servicio.getIdServicio());
        em.getTransaction().begin();
        s.setIdServicio(servicio.getIdServicio());//CUIDADO
        s.setIdVaca(servicio.getIdVaca());
        s.setIdToro(servicio.getIdToro());
        s.setFechaServicio(servicio.getFechaServicio());
        s.setTipoServicio(servicio.getTipoServicio());
        s.setEstaPreñada(servicio.getEstaPreñada());
        s.setModoPalpacion(servicio.getModoPalpacion());
        s.setContadorServicios(servicio.getContadorServicios());
        em.getTransaction().commit();
    }
   
    public ModoPreñez findModoPreñezById(int id) {
        ModoPreñez mp = em.find(ModoPreñez.class, id);
        return mp;
    }
     
    public void delete(Object obj) {
        em.getTransaction().begin();
        em.remove(obj);
        em.getTransaction().commit();
    }
    
    public void updateFinca (Finca finca){
        Finca f = em.find(Finca.class, finca.getId());
        em.getTransaction().begin();
        f.setNombre(finca.getNombre());
        f.setDireccion(finca.getDireccion());
        em.getTransaction().commit();
    }
    
    public void deleteFinca (Finca finca){
        Finca f = em.find(Finca.class, finca.getId());
        em.getTransaction().begin();
        em.getTransaction().commit();
    }
    
    public void updateBovino (Bovino bovino){
        Bovino b = em.find(Bovino.class, bovino.getId());
        em.getTransaction().begin();
        b.setNombre(bovino.getNombre());
        b.setIdFinca(bovino.getIdFinca());
        b.setFechaNacimiento(bovino.getFechaNacimiento());
        b.setPeso(bovino.getPeso());
        b.setIdRaza(bovino.getIdRaza());
        b.setIdMadre(bovino.getIdMadre());
        b.setIdPadre(bovino.getIdPadre());
        b.setCaracteristicas(bovino.getCaracteristicas());
        b.setEsToro(bovino.getEsToro());
        em.getTransaction().commit();
    }
    
    public void updateVaca (Vaca vaca){
        Vaca v = em.find(Vaca.class, vaca.getIdBovino());
        em.getTransaction().begin();
        v.setIdEstadoDesarrollo(vaca.getIdEstadoDesarrollo());
        em.getTransaction().commit();
    }
    
    public void updateExamen (ExamenAndrologico examen){
        ExamenAndrologico e = em.find(ExamenAndrologico.class, examen.getId());
        em.getTransaction().begin();
        e.setFecha(examen.getFecha());
        e.setCondicionCorporal(examen.getCondicionCorporal());
        e.setLibido(examen.getLibido());
        e.setCapacidadDeMonta(examen.getCapacidadDeMonta());
        e.setPrepucio(examen.getPrepucio());
        e.setPene(examen.getPene());
        e.setTesticulos(examen.getTesticulos());
        e.setEpididimos(examen.getEpididimos());
        e.setGlandulasBulbouretrales(examen.getGlandulasBulbouretrales());
        e.setProstata(examen.getProstata());
        e.setVesiculasSeminales(examen.getVesiculasSeminales());
        e.setAmpulas(examen.getAmpulas());
        e.setDescanso(examen.getDescanso());
        e.setCircunferenciaEscrotal(examen.getCircunferenciaEscrotal());
        e.setVolumenEyaculado(examen.getVolumenEyaculado());
        e.setConcentracion(examen.getConcentracion());
        e.setMotilidadMasal(examen.getMotilidadMasal());
        e.setMotilidadProgresiva(examen.getMotilidadProgresiva());
        e.setMorfologiaNormal(examen.getMorfologiaNormal());
        e.setDanosAcrosomales(examen.getDanosAcrosomales());
        e.setAnormalidadesPrimarias(examen.getAnormalidadesPrimarias());
        e.setAnormalidadesSecundarias(examen.getAnormalidadesSecundarias());
        e.setTotalAnormalidades(examen.getTotalAnormalidades());
        e.setAnormalidadFrecuente(examen.getAnormalidadFrecuente());
        e.setLeucocitos(examen.getLeucocitos());
        e.setCelulasEpiteliales(examen.getCelulasEpiteliales());
        e.setPalpacion(examen.getPalpacion());
        e.setEcografia(examen.getEcografia());
        e.setVaginaArtificial(examen.getVaginaArtificial());
        e.setElectroeyaculador(examen.getElectroeyaculador());
        e.setProtusion(examen.getProtusion());
        e.setSatisfactorio(examen.getSatisfactorio());
        e.setVeterinario(examen.getVeterinario());
        e.setObservaciones(examen.getObservaciones());     
        em.getTransaction().commit();
    }
    
    public void updateConfiguracion (Configuracion config){
        Configuracion c = em.find(Configuracion.class, config.getId());
        em.getTransaction().begin();
        c.setMaxIep(config.getMaxIep());
        c.setAlertaPalpacion(config.getAlertaPalpacion());
        c.setMaxAlertaPalpacion(config.getMaxAlertaPalpacion());
        c.setAlertaParto(config.getAlertaParto());
        c.setAlertaDestete(config.getAlertaDestete()); 
        c.setMaxAlertaDestete(config.getMaxAlertaDestete());
        c.setLimitePa(config.getLimitePa());
        c.setLimiteSc(config.getLimiteSc());
        em.getTransaction().commit();
    }
    
}