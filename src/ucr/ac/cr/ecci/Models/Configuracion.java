/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr.ac.cr.ecci.Models;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author XPC
 */
@Entity
@Table(name = "CONFIGURACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c")
    , @NamedQuery(name = "Configuracion.findById", query = "SELECT c FROM Configuracion c WHERE c.id = :id")
    , @NamedQuery(name = "Configuracion.findByAlertaDestete", query = "SELECT c FROM Configuracion c WHERE c.alertaDestete = :alertaDestete")
    , @NamedQuery(name = "Configuracion.findByAlertaPalpacion", query = "SELECT c FROM Configuracion c WHERE c.alertaPalpacion = :alertaPalpacion")
    , @NamedQuery(name = "Configuracion.findByAlertaParto", query = "SELECT c FROM Configuracion c WHERE c.alertaParto = :alertaParto")
    , @NamedQuery(name = "Configuracion.findByMaxAlertaDestete", query = "SELECT c FROM Configuracion c WHERE c.maxAlertaDestete = :maxAlertaDestete")
    , @NamedQuery(name = "Configuracion.findByMaxAlertaPalpacion", query = "SELECT c FROM Configuracion c WHERE c.maxAlertaPalpacion = :maxAlertaPalpacion")
    , @NamedQuery(name = "Configuracion.findByMaxIep", query = "SELECT c FROM Configuracion c WHERE c.maxIep = :maxIep")
    , @NamedQuery(name = "Configuracion.findByLimitePa", query = "SELECT c FROM Configuracion c WHERE c.limitePa = :limitePa")
    , @NamedQuery(name = "Configuracion.findByLimiteSc", query = "SELECT c FROM Configuracion c WHERE c.limiteSc = :limiteSc")})
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ALERTA_DESTETE")
    private Integer alertaDestete;
    @Column(name = "ALERTA_PALPACION")
    private Integer alertaPalpacion;
    @Column(name = "ALERTA_PARTO")
    private Integer alertaParto;
    @Column(name = "MAX_ALERTA_DESTETE")
    private Integer maxAlertaDestete;
    @Column(name = "MAX_ALERTA_PALPACION")
    private Integer maxAlertaPalpacion;
    @Column(name = "MAX_IEP")
    private Integer maxIep;
    @Column(name = "LIMITE_PA")
    private Integer limitePa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LIMITE_SC")
    private Double limiteSc;
    @JoinColumn(name = "ID_FINCA", referencedColumnName = "ID")
    @ManyToOne
    private Finca idFinca;

    public Configuracion() {
    }

    public Configuracion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlertaDestete() {
        return alertaDestete;
    }

    public void setAlertaDestete(Integer alertaDestete) {
        this.alertaDestete = alertaDestete;
    }

    public Integer getAlertaPalpacion() {
        return alertaPalpacion;
    }

    public void setAlertaPalpacion(Integer alertaPalpacion) {
        this.alertaPalpacion = alertaPalpacion;
    }

    public Integer getAlertaParto() {
        return alertaParto;
    }

    public void setAlertaParto(Integer alertaParto) {
        this.alertaParto = alertaParto;
    }

    public Integer getMaxAlertaDestete() {
        return maxAlertaDestete;
    }

    public void setMaxAlertaDestete(Integer maxAlertaDestete) {
        this.maxAlertaDestete = maxAlertaDestete;
    }

    public Integer getMaxAlertaPalpacion() {
        return maxAlertaPalpacion;
    }

    public void setMaxAlertaPalpacion(Integer maxAlertaPalpacion) {
        this.maxAlertaPalpacion = maxAlertaPalpacion;
    }

    public Integer getMaxIep() {
        return maxIep;
    }

    public void setMaxIep(Integer maxIep) {
        this.maxIep = maxIep;
    }

    public Integer getLimitePa() {
        return limitePa;
    }

    public void setLimitePa(Integer limitePa) {
        this.limitePa = limitePa;
    }

    public Double getLimiteSc() {
        return limiteSc;
    }

    public void setLimiteSc(Double limiteSc) {
        this.limiteSc = limiteSc;
    }

    public Finca getIdFinca() {
        return idFinca;
    }

    public void setIdFinca(Finca idFinca) {
        this.idFinca = idFinca;
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
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.Configuracion[ id=" + id + " ]";
    }
    
}
