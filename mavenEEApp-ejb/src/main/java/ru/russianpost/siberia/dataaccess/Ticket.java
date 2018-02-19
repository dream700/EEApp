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
@XmlAccessorType(XmlAccessType.FIELD)
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "barcode", referencedColumnName = "barcode")
    @XmlElement(name = "historyrecord")
    private Collection<Historyrecord> historyrecordCollection;
    @Column(name = "isFinal")
    private boolean isFinal;
    @Column(name = "po_version", length = 20)
    private String po_version;
    @Column(name = "recp_name", length = 120)
    private String recp_name;
    @Column(name = "recp_index", length = 6)
    private String recp_index;
    @Column(name = "recp_region", length = 120)
    private String recp_region;
    @Column(name = "recp_area", length = 120)
    private String recp_area;
    @Column(name = "recp_place", length = 120)
    private String recp_place;
    @Column(name = "recp_street", length = 100)
    private String recp_street;
    @Column(name = "sender_name", length = 120)
    private String sender_name;
    @Column(name = "sender_index", length = 6)
    private String sender_index;
    @Column(name = "sender_region", length = 120)
    private String sender_region;
    @Column(name = "sender_area", length = 120)
    private String sender_area;
    @Column(name = "sender_place", length = 120)
    private String sender_place;
    @Column(name = "sender_street", length = 100)
    private String sender_street;
    @Column(name = "sender_value", length = 50)
    private String sender_value;

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

    public String getPo_version() {
        return po_version;
    }

    public void setPo_version(String po_version) {
        this.po_version = po_version;
    }

    public String getRecp_name() {
        return recp_name;
    }

    public void setRecp_name(String recp_name) {
        this.recp_name = recp_name;
    }

    public String getRecp_index() {
        return recp_index;
    }

    public void setRecp_index(String recp_index) {
        this.recp_index = recp_index;
    }

    public String getRecp_region() {
        return recp_region;
    }

    public void setRecp_region(String recp_region) {
        this.recp_region = recp_region;
    }

    public String getRecp_area() {
        return recp_area;
    }

    public void setRecp_area(String recp_area) {
        this.recp_area = recp_area;
    }

    public String getRecp_place() {
        return recp_place;
    }

    public void setRecp_place(String recp_place) {
        this.recp_place = recp_place;
    }

    public String getRecp_street() {
        return recp_street;
    }

    public void setRecp_street(String recp_street) {
        this.recp_street = recp_street;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_index() {
        return sender_index;
    }

    public void setSender_index(String sender_index) {
        this.sender_index = sender_index;
    }

    public String getSender_region() {
        return sender_region;
    }

    public void setSender_region(String sender_region) {
        this.sender_region = sender_region;
    }

    public String getSender_area() {
        return sender_area;
    }

    public void setSender_area(String sender_area) {
        this.sender_area = sender_area;
    }

    public String getSender_place() {
        return sender_place;
    }

    public void setSender_place(String sender_place) {
        this.sender_place = sender_place;
    }

    public String getSender_street() {
        return sender_street;
    }

    public void setSender_street(String sender_street) {
        this.sender_street = sender_street;
    }

    public String getSender_value() {
        return sender_value;
    }

    public void setSender_value(String sender_value) {
        this.sender_value = sender_value;
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
