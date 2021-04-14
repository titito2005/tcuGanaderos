/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author afg
 */
@Entity
@Table(name = "EXAMEN_ANDROLOGICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamenAndrologico.findAll", query = "SELECT e FROM ExamenAndrologico e")
    , @NamedQuery(name = "ExamenAndrologico.findById", query = "SELECT e FROM ExamenAndrologico e WHERE e.id = :id")
    , @NamedQuery(name = "ExamenAndrologico.findByFecha", query = "SELECT e FROM ExamenAndrologico e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "ExamenAndrologico.findByCondicionCorporal", query = "SELECT e FROM ExamenAndrologico e WHERE e.condicionCorporal = :condicionCorporal")
    , @NamedQuery(name = "ExamenAndrologico.findByLibido", query = "SELECT e FROM ExamenAndrologico e WHERE e.libido = :libido")
    , @NamedQuery(name = "ExamenAndrologico.findByCapacidadDeMonta", query = "SELECT e FROM ExamenAndrologico e WHERE e.capacidadDeMonta = :capacidadDeMonta")
    , @NamedQuery(name = "ExamenAndrologico.findByPrepucio", query = "SELECT e FROM ExamenAndrologico e WHERE e.prepucio = :prepucio")
    , @NamedQuery(name = "ExamenAndrologico.findByPene", query = "SELECT e FROM ExamenAndrologico e WHERE e.pene = :pene")
    , @NamedQuery(name = "ExamenAndrologico.findByTesticulos", query = "SELECT e FROM ExamenAndrologico e WHERE e.testiculos = :testiculos")
    , @NamedQuery(name = "ExamenAndrologico.findByEpididimos", query = "SELECT e FROM ExamenAndrologico e WHERE e.epididimos = :epididimos")
    , @NamedQuery(name = "ExamenAndrologico.findByGlandulasBulbouretrales", query = "SELECT e FROM ExamenAndrologico e WHERE e.glandulasBulbouretrales = :glandulasBulbouretrales")
    , @NamedQuery(name = "ExamenAndrologico.findByProstata", query = "SELECT e FROM ExamenAndrologico e WHERE e.prostata = :prostata")
    , @NamedQuery(name = "ExamenAndrologico.findByVesiculasSeminales", query = "SELECT e FROM ExamenAndrologico e WHERE e.vesiculasSeminales = :vesiculasSeminales")
    , @NamedQuery(name = "ExamenAndrologico.findByAmpulas", query = "SELECT e FROM ExamenAndrologico e WHERE e.ampulas = :ampulas")
    , @NamedQuery(name = "ExamenAndrologico.findByDescanso", query = "SELECT e FROM ExamenAndrologico e WHERE e.descanso = :descanso")
    , @NamedQuery(name = "ExamenAndrologico.findByCircunferenciaEscrotal", query = "SELECT e FROM ExamenAndrologico e WHERE e.circunferenciaEscrotal = :circunferenciaEscrotal")
    , @NamedQuery(name = "ExamenAndrologico.findByVolumenEyaculado", query = "SELECT e FROM ExamenAndrologico e WHERE e.volumenEyaculado = :volumenEyaculado")
    , @NamedQuery(name = "ExamenAndrologico.findByConcentracion", query = "SELECT e FROM ExamenAndrologico e WHERE e.concentracion = :concentracion")
    , @NamedQuery(name = "ExamenAndrologico.findByMotilidadMasal", query = "SELECT e FROM ExamenAndrologico e WHERE e.motilidadMasal = :motilidadMasal")
    , @NamedQuery(name = "ExamenAndrologico.findByMotilidadProgresiva", query = "SELECT e FROM ExamenAndrologico e WHERE e.motilidadProgresiva = :motilidadProgresiva")
    , @NamedQuery(name = "ExamenAndrologico.findByMorfologiaNormal", query = "SELECT e FROM ExamenAndrologico e WHERE e.morfologiaNormal = :morfologiaNormal")
    , @NamedQuery(name = "ExamenAndrologico.findByDanosAcrosomales", query = "SELECT e FROM ExamenAndrologico e WHERE e.danosAcrosomales = :danosAcrosomales")
    , @NamedQuery(name = "ExamenAndrologico.findByAnormalidadesPrimarias", query = "SELECT e FROM ExamenAndrologico e WHERE e.anormalidadesPrimarias = :anormalidadesPrimarias")
    , @NamedQuery(name = "ExamenAndrologico.findByAnormalidadesSecundarias", query = "SELECT e FROM ExamenAndrologico e WHERE e.anormalidadesSecundarias = :anormalidadesSecundarias")
    , @NamedQuery(name = "ExamenAndrologico.findByAnormalidadFrecuente", query = "SELECT e FROM ExamenAndrologico e WHERE e.anormalidadFrecuente = :anormalidadFrecuente")
    , @NamedQuery(name = "ExamenAndrologico.findByTotalAnormalidades", query = "SELECT e FROM ExamenAndrologico e WHERE e.totalAnormalidades = :totalAnormalidades")
    , @NamedQuery(name = "ExamenAndrologico.findByLeucocitos", query = "SELECT e FROM ExamenAndrologico e WHERE e.leucocitos = :leucocitos")
    , @NamedQuery(name = "ExamenAndrologico.findByCelulasEpiteliales", query = "SELECT e FROM ExamenAndrologico e WHERE e.celulasEpiteliales = :celulasEpiteliales")
    , @NamedQuery(name = "ExamenAndrologico.findByPalpacion", query = "SELECT e FROM ExamenAndrologico e WHERE e.palpacion = :palpacion")
    , @NamedQuery(name = "ExamenAndrologico.findByEcografia", query = "SELECT e FROM ExamenAndrologico e WHERE e.ecografia = :ecografia")
    , @NamedQuery(name = "ExamenAndrologico.findByVaginaArtificial", query = "SELECT e FROM ExamenAndrologico e WHERE e.vaginaArtificial = :vaginaArtificial")
    , @NamedQuery(name = "ExamenAndrologico.findByElectroeyaculador", query = "SELECT e FROM ExamenAndrologico e WHERE e.electroeyaculador = :electroeyaculador")
    , @NamedQuery(name = "ExamenAndrologico.findByProtusion", query = "SELECT e FROM ExamenAndrologico e WHERE e.protusion = :protusion")
    , @NamedQuery(name = "ExamenAndrologico.findBySatisfactorio", query = "SELECT e FROM ExamenAndrologico e WHERE e.satisfactorio = :satisfactorio")
    , @NamedQuery(name = "ExamenAndrologico.findByVeterinario", query = "SELECT e FROM ExamenAndrologico e WHERE e.veterinario = :veterinario")
    , @NamedQuery(name = "ExamenAndrologico.findByObservaciones", query = "SELECT e FROM ExamenAndrologico e WHERE e.observaciones = :observaciones")})
public class ExamenAndrologico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "CONDICION_CORPORAL")
    private String condicionCorporal;
    @Column(name = "LIBIDO")
    private Short libido;
    @Column(name = "CAPACIDAD_DE_MONTA")
    private Short capacidadDeMonta;
    @Column(name = "PREPUCIO")
    private Short prepucio;
    @Column(name = "PENE")
    private Short pene;
    @Column(name = "TESTICULOS")
    private Short testiculos;
    @Column(name = "EPIDIDIMOS")
    private Short epididimos;
    @Column(name = "GLANDULAS_BULBOURETRALES")
    private Short glandulasBulbouretrales;
    @Column(name = "PROSTATA")
    private Short prostata;
    @Column(name = "VESICULAS_SEMINALES")
    private Short vesiculasSeminales;
    @Column(name = "AMPULAS")
    private Short ampulas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DESCANSO")
    private Double descanso;
    @Column(name = "CIRCUNFERENCIA_ESCROTAL")
    private Double circunferenciaEscrotal;
    @Column(name = "VOLUMEN_EYACULADO")
    private Double volumenEyaculado;
    @Column(name = "CONCENTRACION")
    private Double concentracion;
    @Column(name = "MOTILIDAD_MASAL")
    private Double motilidadMasal;
    @Column(name = "MOTILIDAD_PROGRESIVA")
    private Double motilidadProgresiva;
    @Column(name = "MORFOLOGIA_NORMAL")
    private Double morfologiaNormal;
    @Column(name = "DANOS_ACROSOMALES")
    private String danosAcrosomales;
    @Column(name = "ANORMALIDADES_PRIMARIAS")
    private String anormalidadesPrimarias;
    @Column(name = "ANORMALIDADES_SECUNDARIAS")
    private String anormalidadesSecundarias;
    @Column(name = "ANORMALIDAD_FRECUENTE")
    private String anormalidadFrecuente;
    @Column(name = "TOTAL_ANORMALIDADES")
    private Double totalAnormalidades;
    @Column(name = "LEUCOCITOS")
    private Double leucocitos;
    @Column(name = "CELULAS_EPITELIALES")
    private Double celulasEpiteliales;
    @Column(name = "PALPACION")
    private Short palpacion;
    @Column(name = "ECOGRAFIA")
    private Short ecografia;
    @Column(name = "VAGINA_ARTIFICIAL")
    private Short vaginaArtificial;
    @Column(name = "ELECTROEYACULADOR")
    private Short electroeyaculador;
    @Column(name = "PROTUSION")
    private Short protusion;
    @Column(name = "SATISFACTORIO")
    private Short satisfactorio;
    @Column(name = "VETERINARIO")
    private String veterinario;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @JoinColumn(name = "ID_BOVINO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Bovino idBovino;

    public ExamenAndrologico() {
    }

    public ExamenAndrologico(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCondicionCorporal() {
        return condicionCorporal;
    }

    public void setCondicionCorporal(String condicionCorporal) {
        this.condicionCorporal = condicionCorporal;
    }

    public Short getLibido() {
        return libido;
    }

    public void setLibido(Short libido) {
        this.libido = libido;
    }

    public Short getCapacidadDeMonta() {
        return capacidadDeMonta;
    }

    public void setCapacidadDeMonta(Short capacidadDeMonta) {
        this.capacidadDeMonta = capacidadDeMonta;
    }

    public Short getPrepucio() {
        return prepucio;
    }

    public void setPrepucio(Short prepucio) {
        this.prepucio = prepucio;
    }

    public Short getPene() {
        return pene;
    }

    public void setPene(Short pene) {
        this.pene = pene;
    }

    public Short getTesticulos() {
        return testiculos;
    }

    public void setTesticulos(Short testiculos) {
        this.testiculos = testiculos;
    }

    public Short getEpididimos() {
        return epididimos;
    }

    public void setEpididimos(Short epididimos) {
        this.epididimos = epididimos;
    }

    public Short getGlandulasBulbouretrales() {
        return glandulasBulbouretrales;
    }

    public void setGlandulasBulbouretrales(Short glandulasBulbouretrales) {
        this.glandulasBulbouretrales = glandulasBulbouretrales;
    }

    public Short getProstata() {
        return prostata;
    }

    public void setProstata(Short prostata) {
        this.prostata = prostata;
    }

    public Short getVesiculasSeminales() {
        return vesiculasSeminales;
    }

    public void setVesiculasSeminales(Short vesiculasSeminales) {
        this.vesiculasSeminales = vesiculasSeminales;
    }

    public Short getAmpulas() {
        return ampulas;
    }

    public void setAmpulas(Short ampulas) {
        this.ampulas = ampulas;
    }

    public Double getDescanso() {
        return descanso;
    }

    public void setDescanso(Double descanso) {
        this.descanso = descanso;
    }

    public Double getCircunferenciaEscrotal() {
        return circunferenciaEscrotal;
    }

    public void setCircunferenciaEscrotal(Double circunferenciaEscrotal) {
        this.circunferenciaEscrotal = circunferenciaEscrotal;
    }

    public Double getVolumenEyaculado() {
        return volumenEyaculado;
    }

    public void setVolumenEyaculado(Double volumenEyaculado) {
        this.volumenEyaculado = volumenEyaculado;
    }

    public Double getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(Double concentracion) {
        this.concentracion = concentracion;
    }

    public Double getMotilidadMasal() {
        return motilidadMasal;
    }

    public void setMotilidadMasal(Double motilidadMasal) {
        this.motilidadMasal = motilidadMasal;
    }

    public Double getMotilidadProgresiva() {
        return motilidadProgresiva;
    }

    public void setMotilidadProgresiva(Double motilidadProgresiva) {
        this.motilidadProgresiva = motilidadProgresiva;
    }

    public Double getMorfologiaNormal() {
        return morfologiaNormal;
    }

    public void setMorfologiaNormal(Double morfologiaNormal) {
        this.morfologiaNormal = morfologiaNormal;
    }

    public String getDanosAcrosomales() {
        return danosAcrosomales;
    }

    public void setDanosAcrosomales(String danosAcrosomales) {
        this.danosAcrosomales = danosAcrosomales;
    }

    public String getAnormalidadesPrimarias() {
        return anormalidadesPrimarias;
    }

    public void setAnormalidadesPrimarias(String anormalidadesPrimarias) {
        this.anormalidadesPrimarias = anormalidadesPrimarias;
    }

    public String getAnormalidadesSecundarias() {
        return anormalidadesSecundarias;
    }

    public void setAnormalidadesSecundarias(String anormalidadesSecundarias) {
        this.anormalidadesSecundarias = anormalidadesSecundarias;
    }

    public String getAnormalidadFrecuente() {
        return anormalidadFrecuente;
    }

    public void setAnormalidadFrecuente(String anormalidadFrecuente) {
        this.anormalidadFrecuente = anormalidadFrecuente;
    }

    public Double getTotalAnormalidades() {
        return totalAnormalidades;
    }

    public void setTotalAnormalidades(Double totalAnormalidades) {
        this.totalAnormalidades = totalAnormalidades;
    }

    public Double getLeucocitos() {
        return leucocitos;
    }

    public void setLeucocitos(Double leucocitos) {
        this.leucocitos = leucocitos;
    }

    public Double getCelulasEpiteliales() {
        return celulasEpiteliales;
    }

    public void setCelulasEpiteliales(Double celulasEpiteliales) {
        this.celulasEpiteliales = celulasEpiteliales;
    }

    public Short getPalpacion() {
        return palpacion;
    }

    public void setPalpacion(Short palpacion) {
        this.palpacion = palpacion;
    }

    public Short getEcografia() {
        return ecografia;
    }

    public void setEcografia(Short ecografia) {
        this.ecografia = ecografia;
    }

    public Short getVaginaArtificial() {
        return vaginaArtificial;
    }

    public void setVaginaArtificial(Short vaginaArtificial) {
        this.vaginaArtificial = vaginaArtificial;
    }

    public Short getElectroeyaculador() {
        return electroeyaculador;
    }

    public void setElectroeyaculador(Short electroeyaculador) {
        this.electroeyaculador = electroeyaculador;
    }

    public Short getProtusion() {
        return protusion;
    }

    public void setProtusion(Short protusion) {
        this.protusion = protusion;
    }

    public Short getSatisfactorio() {
        return satisfactorio;
    }

    public void setSatisfactorio(Short satisfactorio) {
        this.satisfactorio = satisfactorio;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Bovino getIdBovino() {
        return idBovino;
    }

    public void setIdBovino(Bovino idBovino) {
        this.idBovino = idBovino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamenAndrologico)) {
            return false;
        }
        ExamenAndrologico other = (ExamenAndrologico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.ExamenAndrologico[ id=" + id + " ]";
    }
    
}
