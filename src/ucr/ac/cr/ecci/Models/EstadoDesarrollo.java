/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Models;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author afg
 */
@Entity
@Table(name = "ESTADO_DESARROLLO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoDesarrollo.findAll", query = "SELECT e FROM EstadoDesarrollo e")
    , @NamedQuery(name = "EstadoDesarrollo.findById", query = "SELECT e FROM EstadoDesarrollo e WHERE e.id = :id")
    , @NamedQuery(name = "EstadoDesarrollo.findByEstado", query = "SELECT e FROM EstadoDesarrollo e WHERE e.estado = :estado")})
public class EstadoDesarrollo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "idEstadoDesarrollo")
    private Collection<Vaca> vacaCollection;

    public EstadoDesarrollo() {
    }

    public EstadoDesarrollo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Vaca> getVacaCollection() {
        return vacaCollection;
    }

    public void setVacaCollection(Collection<Vaca> vacaCollection) {
        this.vacaCollection = vacaCollection;
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
        if (!(object instanceof EstadoDesarrollo)) {
            return false;
        }
        EstadoDesarrollo other = (EstadoDesarrollo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.EstadoDesarrollo[ id=" + id + " ]";
    }
    
}
