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
@Table(name = "DESTETE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Destete.findAll", query = "SELECT d FROM Destete d")
    , @NamedQuery(name = "Destete.findById", query = "SELECT d FROM Destete d WHERE d.id = :id")
    , @NamedQuery(name = "Destete.findByFecha", query = "SELECT d FROM Destete d WHERE d.fecha = :fecha")})
public class Destete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "ID_BOVINO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Bovino idBovino;

    public Destete() {
    }

    public Destete(Integer id) {
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
        if (!(object instanceof Destete)) {
            return false;
        }
        Destete other = (Destete) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.Destete[ id=" + id + " ]";
    }
    
}
