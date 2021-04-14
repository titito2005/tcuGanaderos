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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author afg
 */
@Entity
@Table(name = "FINCA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Finca.findAll", query = "SELECT f FROM Finca f")
    , @NamedQuery(name = "Finca.findById", query = "SELECT f FROM Finca f WHERE f.id = :id")
    , @NamedQuery(name = "Finca.findByNombre", query = "SELECT f FROM Finca f WHERE f.nombre = :nombre")
    , @NamedQuery(name = "Finca.findByDireccion", query = "SELECT f FROM Finca f WHERE f.direccion = :direccion")
    , @NamedQuery(name = "Finca.findByArea", query = "SELECT f FROM Finca f WHERE f.area = :area")
    , @NamedQuery(name = "Finca.findByContacto", query = "SELECT f FROM Finca f WHERE f.contacto = :contacto")})
public class Finca implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AREA")
    private Double area;
    @Column(name = "CONTACTO")
    private String contacto;
    @OneToMany(mappedBy = "idFinca")
    private Collection<Bovino> bovinoCollection;
    @OneToMany(mappedBy = "idFinca")
    private Collection<Configuracion> configuracionCollection;

    public Finca() {
    }

    public Finca(Integer id) {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        String oldDireccion = this.direccion;
        this.direccion = direccion;
        changeSupport.firePropertyChange("direccion", oldDireccion, direccion);
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        Double oldArea = this.area;
        this.area = area;
        changeSupport.firePropertyChange("area", oldArea, area);
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        String oldContacto = this.contacto;
        this.contacto = contacto;
        changeSupport.firePropertyChange("contacto", oldContacto, contacto);
    }

    @XmlTransient
    public Collection<Bovino> getBovinoCollection() {
        return bovinoCollection;
    }

    public void setBovinoCollection(Collection<Bovino> bovinoCollection) {
        this.bovinoCollection = bovinoCollection;
    }

    @XmlTransient
    public Collection<Configuracion> getConfiguracionCollection() {
        return configuracionCollection;
    }

    public void setConfiguracionCollection(Collection<Configuracion> configuracionCollection) {
        this.configuracionCollection = configuracionCollection;
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
        if (!(object instanceof Finca)) {
            return false;
        }
        Finca other = (Finca) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.Finca[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
