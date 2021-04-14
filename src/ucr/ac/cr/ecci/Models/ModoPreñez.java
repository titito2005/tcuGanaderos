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
@Table(name = "MODO_PRE\u00d1EZ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModoPre\u00f1ez.findAll", query = "SELECT m FROM ModoPre\u00f1ez m")
    , @NamedQuery(name = "ModoPre\u00f1ez.findById", query = "SELECT m FROM ModoPre\u00f1ez m WHERE m.id = :id")
    , @NamedQuery(name = "ModoPre\u00f1ez.findByModo", query = "SELECT m FROM ModoPre\u00f1ez m WHERE m.modo = :modo")})
public class ModoPreñez implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "MODO")
    private String modo;
    @OneToMany(mappedBy = "idModoPre\u00f1ez")
    private Collection<Vaca> vacaCollection;

    public ModoPreñez() {
    }

    public ModoPreñez(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
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
        if (!(object instanceof ModoPreñez)) {
            return false;
        }
        ModoPreñez other = (ModoPreñez) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.ModoPre\u00f1ez[ id=" + id + " ]";
    }
    
}
