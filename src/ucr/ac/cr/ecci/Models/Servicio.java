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
@Table(name = "SERVICIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s")
    , @NamedQuery(name = "Servicio.findByIdServicio", query = "SELECT s FROM Servicio s WHERE s.idServicio = :idServicio")
    , @NamedQuery(name = "Servicio.findByIdToro", query = "SELECT s FROM Servicio s WHERE s.idToro = :idToro")
    , @NamedQuery(name = "Servicio.findByFechaServicio", query = "SELECT s FROM Servicio s WHERE s.fechaServicio = :fechaServicio")
    , @NamedQuery(name = "Servicio.findByTipoServicio", query = "SELECT s FROM Servicio s WHERE s.tipoServicio = :tipoServicio")
    , @NamedQuery(name = "Servicio.findByModoPalpacion", query = "SELECT s FROM Servicio s WHERE s.modoPalpacion = :modoPalpacion")
    , @NamedQuery(name = "Servicio.findByEstaPre\u00f1ada", query = "SELECT s FROM Servicio s WHERE s.estaPre\u00f1ada = :estaPre\u00f1ada")
    , @NamedQuery(name = "Servicio.findByContadorServicios", query = "SELECT s FROM Servicio s WHERE s.contadorServicios = :contadorServicios")})
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_SERVICIO")
    private Integer idServicio;
    @Basic(optional = false)
    @Column(name = "ID_TORO")
    private int idToro;
    @Basic(optional = false)
    @Column(name = "FECHA_SERVICIO")
    @Temporal(TemporalType.DATE)
    private Date fechaServicio;
    @Column(name = "TIPO_SERVICIO")
    private String tipoServicio;
    @Column(name = "MODO_PALPACION")
    private String modoPalpacion;
    @Column(name = "ESTA_PRE\u00d1ADA")
    private Boolean estaPreñada;
    @Basic(optional = false)
    @Column(name = "CONTADOR_SERVICIOS")
    private int contadorServicios;
    @JoinColumn(name = "ID_VACA", referencedColumnName = "ID_BOVINO")
    @ManyToOne(optional = false)
    private Vaca idVaca;

    public Servicio() {
    }

    public Servicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Servicio(Integer idServicio, int idToro, Date fechaServicio, int contadorServicios) {
        this.idServicio = idServicio;
        this.idToro = idToro;
        this.fechaServicio = fechaServicio;
        this.contadorServicios = contadorServicios;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdToro() {
        return idToro;
    }

    public void setIdToro(int idToro) {
        this.idToro = idToro;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getModoPalpacion() {
        return modoPalpacion;
    }

    public void setModoPalpacion(String modoPalpacion) {
        this.modoPalpacion = modoPalpacion;
    }

    public Boolean getEstaPreñada() {
        return estaPreñada;
    }

    public void setEstaPreñada(Boolean estaPreñada) {
        this.estaPreñada = estaPreñada;
    }

    public int getContadorServicios() {
        return contadorServicios;
    }

    public void setContadorServicios(int contadorServicios) {
        this.contadorServicios = contadorServicios;
    }

    public Vaca getIdVaca() {
        return idVaca;
    }

    public void setIdVaca(Vaca idVaca) {
        this.idVaca = idVaca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ucr.ac.cr.ecci.Models.Servicio[ idServicio=" + idServicio + " ]";
    }
    
}
