/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.dataaccess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.soap.SOAPElement;

/**
 *
 * @author Andrey.Isakov
 */
@Entity
@Table(name = "ticket", schema = "app")
@XmlRootElement(name = "ticket")
@XmlAccessorType (XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t")
    , @NamedQuery(name = "Ticket.findByBarcode", query = "SELECT t FROM Ticket t WHERE t.barcode = :barcode")
    , @NamedQuery(name = "Ticket.findByDateFetch", query = "SELECT t FROM Ticket t WHERE t.dateFetch = :dateFetch")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "Ticket.findDateFetchisNull", query = "SELECT * FROM app.ticket WHERE dateFetch is null limit 2800", resultClass = Ticket.class)})
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "barcode", nullable = false, length = 16)
    private String barcode;
    @Column(name = "DateFetch")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFetch;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "barcode", referencedColumnName = "barcode")
    @XmlElement(name = "historyrecord")
    private Collection<Historyrecord> historyrecordCollection;
    @Column(name = "isFinal")
    private boolean isFinal;

    public boolean isIsFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }
    @Transient
    @XmlTransient
    public SOAPElement item;

    public Ticket() {
    }

    public Ticket(String barcode) {
        this.dateFetch = null;
        this.barcode = barcode;
        historyrecordCollection = new ArrayList<>();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getDateFetch() {
        return dateFetch;
    }

    public void setDateFetch(Date dateFetch) {
        this.dateFetch = dateFetch;
    }

    @XmlTransient
    public Collection<Historyrecord> getHistoryrecordCollection() {
        return historyrecordCollection;
    }

    public void setHistoryrecordCollection(Collection<Historyrecord> historyrecordCollection) {
        this.historyrecordCollection = historyrecordCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (barcode != null ? barcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.barcode == null && other.barcode != null) || (this.barcode != null && !this.barcode.equals(other.barcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.Ticket[ barcode=" + barcode + " ]\n HistoryRecords [" + historyrecordCollection + "]";
    }

}
