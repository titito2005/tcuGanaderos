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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ricardo Alfaro
 */
@Entity
@Table(name = "VACA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vaca.findAll", query = "SELECT v FROM Vaca v")
    , @NamedQuery(name = "Vaca.findByIdBovino", query = "SELECT v FROM Vaca v WHERE v.idBovino = :idBovino")})
public class Vaca implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVaca")
    private Collection<Servicio> servicioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_BOVINO")
    private Integer idBovino;
    @JoinColumn(name = "ID_BOVINO", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Bovino bovino;
    @JoinColumn(name = "ID_ESTADO_DESARROLLO", referencedColumnName = "ID")
    @ManyToOne
    private EstadoDesarrollo idEstadoDesarrollo;
    @JoinColumn(name = "ID_MODO_PRE\u00d1EZ", referencedColumnName = "ID")
    @ManyToOne
    private ModoPreñez idModoPreñez;

    public Vaca() {
    }

    public Vaca(Integer idBovino) {
        this.idBovino = idBovino;
    }

    public Integer getIdBovino() {
        return idBovino;
    }

    public void setIdBovino(Integer idBovino) {
        Integer oldIdBovino = this.idBovino;
        this.idBovino = idBovino;
        changeSupport.firePropertyChange("idBovino", oldIdBovino, idBovino);
    }

    public Bovino getBovino() {
        return bovino;
    }

    public void setBovino(Bovino bovino) {
        this.bovino = bovino;
    }

    public EstadoDesarrollo getIdEstadoDesarrollo() {
        return idEstadoDesarrollo;
    }

    public void setIdEstadoDesarrollo(EstadoDesarrollo idEstadoDesarrollo) {
        EstadoDesarrollo oldIdEstadoDesarrollo = this.idEstadoDesarrollo;
        this.idEstadoDesarrollo = idEstadoDesarrollo;
        changeSupport.firePropertyChange("idEstadoDesarrollo", oldIdEstadoDesarrollo, idEstadoDesarrollo);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBovino != null ? idBovino.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vaca)) {
            return false;
        }
        Vaca other = (Vaca) object;
        if ((this.idBovino == null && other.idBovino != null) || (this.idBovino != null && !this.idBovino.equals(other.idBovino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.Vaca[ idBovino=" + idBovino + " ]";
    }

    @XmlTransient
    public Collection<Servicio> getServicioCollection() {
        return servicioCollection;
    }

    public void setServicioCollection(Collection<Servicio> servicioCollection) {
        this.servicioCollection = servicioCollection;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
