/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author afg
 */
@Entity
@Table(name = "BOVINO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bovino.findAll", query = "SELECT b FROM Bovino b")
    , @NamedQuery(name = "Bovino.findById", query = "SELECT b FROM Bovino b WHERE b.id = :id")
    , @NamedQuery(name = "Bovino.findByNombre", query = "SELECT b FROM Bovino b WHERE b.nombre = :nombre")
    , @NamedQuery(name = "Bovino.findByFechaNacimiento", query = "SELECT b FROM Bovino b WHERE b.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Bovino.findByCaracteristicas", query = "SELECT b FROM Bovino b WHERE b.caracteristicas = :caracteristicas")
    , @NamedQuery(name = "Bovino.findByPeso", query = "SELECT b FROM Bovino b WHERE b.peso = :peso")
    , @NamedQuery(name = "Bovino.findByEsToro", query = "SELECT b FROM Bovino b WHERE b.esToro = :esToro")})
public class Bovino implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_NACIMIENTO", nullable  = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(name = "CARACTERISTICAS")
    private String caracteristicas;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PESO")
    private Double peso;
    @Column(name = "ES_TORO")
    private Short esToro;
    @OneToMany(mappedBy = "idMadre")
    private Collection<Bovino> bovinoCollection;
    @JoinColumn(name = "ID_MADRE", referencedColumnName = "ID")
    @ManyToOne
    private Bovino idMadre;
    @OneToMany(mappedBy = "idPadre")
    private Collection<Bovino> bovinoCollection1;
    @JoinColumn(name = "ID_PADRE", referencedColumnName = "ID")
    @ManyToOne
    private Bovino idPadre;
    @JoinColumn(name = "ID_FINCA", referencedColumnName = "ID")
    @ManyToOne
    private Finca idFinca;
    @JoinColumn(name = "ID_RAZA", referencedColumnName = "ID")
    @ManyToOne
    private Raza idRaza;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bovino")
    private Vaca vaca;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "bovino")
    private Baja baja;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBovino")
    private Collection<Destete> desteteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBovino")
    private Collection<Palpacion> palpacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBovino")
    private Collection<Celo> celoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMadre")
    private Collection<Parto> partoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPadre")
    private Collection<Parto> partoCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBovino")
    private Collection<ExamenAndrologico> examenAndrologicoCollection;

    public Bovino() {
    }

    public Bovino(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        String oldNombre = this.nombre;
        this.nombre = nombre;
        changeSupport.firePropertyChange("nombre", oldNombre, nombre);
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        Date oldFechaNacimiento = this.fechaNacimiento;
        this.fechaNacimiento = fechaNacimiento;
        changeSupport.firePropertyChange("fechaNacimiento", oldFechaNacimiento, fechaNacimiento);
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        String oldCaracteristicas = this.caracteristicas;
        this.caracteristicas = caracteristicas;
        changeSupport.firePropertyChange("caracteristicas", oldCaracteristicas, caracteristicas);
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        Double oldPeso = this.peso;
        this.peso = peso;
        changeSupport.firePropertyChange("peso", oldPeso, peso);
    }

    public Short getEsToro() {
        return esToro;
    }

    public void setEsToro(Short esToro) {
        Short oldEsToro = this.esToro;
        this.esToro = esToro;
        changeSupport.firePropertyChange("esToro", oldEsToro, esToro);
    }

    @XmlTransient
    public Collection<Bovino> getBovinoCollection() {
        return bovinoCollection;
    }

    public void setBovinoCollection(Collection<Bovino> bovinoCollection) {
        this.bovinoCollection = bovinoCollection;
    }

    public Bovino getIdMadre() {
        return idMadre;
    }

    public void setIdMadre(Bovino idMadre) {
        Bovino oldIdMadre = this.idMadre;
        this.idMadre = idMadre;
        changeSupport.firePropertyChange("idMadre", oldIdMadre, idMadre);
    }

    @XmlTransient
    public Collection<Bovino> getBovinoCollection1() {
        return bovinoCollection1;
    }

    public void setBovinoCollection1(Collection<Bovino> bovinoCollection1) {
        this.bovinoCollection1 = bovinoCollection1;
    }

    public Bovino getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Bovino idPadre) {
        Bovino oldIdPadre = this.idPadre;
        this.idPadre = idPadre;
        changeSupport.firePropertyChange("idPadre", oldIdPadre, idPadre);
    }

    public Finca getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(Finca idFinca) {
        Finca oldIdFinca = this.idFinca;
        this.idFinca = idFinca;
        changeSupport.firePropertyChange("idFinca", oldIdFinca, idFinca);
    }

    public Raza getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Raza idRaza) {
        Raza oldIdRaza = this.idRaza;
        this.idRaza = idRaza;
        changeSupport.firePropertyChange("idRaza", oldIdRaza, idRaza);
    }

    public Vaca getVaca() {
        return vaca;
    }

    public void setVaca(Vaca vaca) {
        this.vaca = vaca;
    }

    public Baja getBaja() {
        return baja;
    }

    public void setBaja(Baja baja) {
        this.baja = baja;
    }

    @XmlTransient
    public Collection<Destete> getDesteteCollection() {
        return desteteCollection;
    }

    public void setDesteteCollection(Collection<Destete> desteteCollection) {
        this.desteteCollection = desteteCollection;
    }

    @XmlTransient
    public Collection<Palpacion> getPalpacionCollection() {
        return palpacionCollection;
    }

    public void setPalpacionCollection(Collection<Palpacion> palpacionCollection) {
        this.palpacionCollection = palpacionCollection;
    }

    @XmlTransient
    public Collection<Celo> getCeloCollection() {
        return celoCollection;
    }

    public void setCeloCollection(Collection<Celo> celoCollection) {
        this.celoCollection = celoCollection;
    }

    @XmlTransient
    public Collection<Parto> getPartoCollection() {
        return partoCollection;
    }

    public void setPartoCollection(Collection<Parto> partoCollection) {
        this.partoCollection = partoCollection;
    }

    @XmlTransient
    public Collection<Parto> getPartoCollection1() {
        return partoCollection1;
    }

    public void setPartoCollection1(Collection<Parto> partoCollection1) {
        this.partoCollection1 = partoCollection1;
    }

    @XmlTransient
    public Collection<ExamenAndrologico> getExamenAndrologicoCollection() {
        return examenAndrologicoCollection;
    }

    public void setExamenAndrologicoCollection(Collection<ExamenAndrologico> examenAndrologicoCollection) {
        this.examenAndrologicoCollection = examenAndrologicoCollection;
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
        if (!(object instanceof Bovino)) {
            return false;
        }
        Bovino other = (Bovino) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.Bovino[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
