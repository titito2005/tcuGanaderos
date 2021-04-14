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
 * @author XPC
 */
@Entity
@Table(name = "PARTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parto.findAll", query = "SELECT p FROM Parto p")
    , @NamedQuery(name = "Parto.findById", query = "SELECT p FROM Parto p WHERE p.id = :id")
    , @NamedQuery(name = "Parto.findByFecha", query = "SELECT p FROM Parto p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Parto.findBySexo", query = "SELECT p FROM Parto p WHERE p.sexo = :sexo")
    , @NamedQuery(name = "Parto.findByMuerteprematura", query = "SELECT p FROM Parto p WHERE p.muerteprematura = :muerteprematura")})
public class Parto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "SEXO")
    private String sexo;
    @Column(name = "MUERTEPREMATURA")
    private Boolean muerteprematura;
    @JoinColumn(name = "ID_MADRE", referencedColumnName = "ID")
    @ManyToOne
    private Bovino idMadre;
    @JoinColumn(name = "ID_PADRE", referencedColumnName = "ID")
    @ManyToOne
    private Bovino idPadre;

    public Parto() {
    }

    public Parto(Integer id) {
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean getMuerteprematura() {
        return muerteprematura;
    }

    public void setMuerteprematura(Boolean muerteprematura) {
        this.muerteprematura = muerteprematura;
    }

    public Bovino getIdMadre() {
        return idMadre;
    }

    public void setIdMadre(Bovino idMadre) {
        this.idMadre = idMadre;
    }

    public Bovino getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Bovino idPadre) {
        this.idPadre = idPadre;
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
        if (!(object instanceof Parto)) {
            return false;
        }
        Parto other = (Parto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.Parto[ id=" + id + " ]";
    }
    
}
