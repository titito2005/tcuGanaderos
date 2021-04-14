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
@Table(name = "PALPACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Palpacion.findAll", query = "SELECT p FROM Palpacion p")
    , @NamedQuery(name = "Palpacion.findById", query = "SELECT p FROM Palpacion p WHERE p.id = :id")
    , @NamedQuery(name = "Palpacion.findByFecha", query = "SELECT p FROM Palpacion p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Palpacion.findByCondicionCorporal", query = "SELECT p FROM Palpacion p WHERE p.condicionCorporal = :condicionCorporal")
    , @NamedQuery(name = "Palpacion.findByConfirmacion", query = "SELECT p FROM Palpacion p WHERE p.confirmacion = :confirmacion")
    , @NamedQuery(name = "Palpacion.findByResultado", query = "SELECT p FROM Palpacion p WHERE p.resultado = :resultado")})
public class Palpacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CONDICION_CORPORAL")
    private Double condicionCorporal;
    @Column(name = "CONFIRMACION")
    private String confirmacion;
    @Column(name = "RESULTADO")
    private String resultado;
    @JoinColumn(name = "ID_BOVINO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Bovino idBovino;

    public Palpacion() {
    }

    public Palpacion(Integer id) {
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

    public Double getCondicionCorporal() {
        return condicionCorporal;
    }

    public void setCondicionCorporal(Double condicionCorporal) {
        this.condicionCorporal = condicionCorporal;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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
        if (!(object instanceof Palpacion)) {
            return false;
        }
        Palpacion other = (Palpacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.Palpacion[ id=" + id + " ]";
    }
    
}
