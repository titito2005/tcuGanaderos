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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author afg
 */
@Entity
@Table(name = "BAJA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Baja.findAll", query = "SELECT b FROM Baja b")
    , @NamedQuery(name = "Baja.findByIdBovino", query = "SELECT b FROM Baja b WHERE b.idBovino = :idBovino")
    , @NamedQuery(name = "Baja.findByFecha", query = "SELECT b FROM Baja b WHERE b.fecha = :fecha")
    , @NamedQuery(name = "Baja.findByCausa", query = "SELECT b FROM Baja b WHERE b.causa = :causa")})
public class Baja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_BOVINO")
    private Integer idBovino;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "CAUSA")
    private String causa;
    @JoinColumn(name = "ID_BOVINO", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Bovino bovino;

    public Baja() {
    }

    public Baja(Integer idBovino) {
        this.idBovino = idBovino;
    }

    public Integer getIdBovino() {
        return idBovino;
    }

    public void setIdBovino(Integer idBovino) {
        this.idBovino = idBovino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public Bovino getBovino() {
        return bovino;
    }

    public void setBovino(Bovino bovino) {
        this.bovino = bovino;
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
        if (!(object instanceof Baja)) {
            return false;
        }
        Baja other = (Baja) object;
        if ((this.idBovino == null && other.idBovino != null) || (this.idBovino != null && !this.idBovino.equals(other.idBovino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.Baja[ idBovino=" + idBovino + " ]";
    }
    
}
