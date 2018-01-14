/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.dataaccess;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andrey.Isakov
 */
@Entity
@Table(name = "historyrecord", schema = "app")
@XmlRootElement(name = "historyrecord")
@XmlAccessorType (XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Historyrecord.findAll", query = "SELECT h FROM Historyrecord h")
    , @NamedQuery(name = "Historyrecord.findById", query = "SELECT h FROM Historyrecord h WHERE h.id = :id")
    , @NamedQuery(name = "Historyrecord.findByDestinationaddressDescription", query = "SELECT h FROM Historyrecord h WHERE h.destinationaddressDescription = :destinationaddressDescription")
    , @NamedQuery(name = "Historyrecord.findByDestinationAddressIndex", query = "SELECT h FROM Historyrecord h WHERE h.destinationAddressIndex = :destinationAddressIndex")
    , @NamedQuery(name = "Historyrecord.findByOperationAddressIndex", query = "SELECT h FROM Historyrecord h WHERE h.operationAddressIndex = :operationAddressIndex")
    , @NamedQuery(name = "Historyrecord.findByOperationAddressDescription", query = "SELECT h FROM Historyrecord h WHERE h.operationAddressDescription = :operationAddressDescription")
    , @NamedQuery(name = "Historyrecord.findByMailDirectID", query = "SELECT h FROM Historyrecord h WHERE h.mailDirectID = :mailDirectID")
    , @NamedQuery(name = "Historyrecord.findByMailDirectNameRU", query = "SELECT h FROM Historyrecord h WHERE h.mailDirectNameRU = :mailDirectNameRU")
    , @NamedQuery(name = "Historyrecord.findByCountryOperID", query = "SELECT h FROM Historyrecord h WHERE h.countryOperID = :countryOperID")
    , @NamedQuery(name = "Historyrecord.findByComplexItemName", query = "SELECT h FROM Historyrecord h WHERE h.complexItemName = :complexItemName")
    , @NamedQuery(name = "Historyrecord.findByMass", query = "SELECT h FROM Historyrecord h WHERE h.mass = :mass")
    , @NamedQuery(name = "Historyrecord.findByOperTypeID", query = "SELECT h FROM Historyrecord h WHERE h.operTypeID = :operTypeID")
    , @NamedQuery(name = "Historyrecord.findByOperTypeName", query = "SELECT h FROM Historyrecord h WHERE h.operTypeName = :operTypeName")
    , @NamedQuery(name = "Historyrecord.findByOperAttrID", query = "SELECT h FROM Historyrecord h WHERE h.operAttrID = :operAttrID")
    , @NamedQuery(name = "Historyrecord.findByOperAttrName", query = "SELECT h FROM Historyrecord h WHERE h.operAttrName = :operAttrName")
    , @NamedQuery(name = "Historyrecord.findByOperDate", query = "SELECT h FROM Historyrecord h WHERE h.operDate = :operDate")
    , @NamedQuery(name = "Historyrecord.findByLastOperDate", query = "SELECT h FROM Historyrecord h WHERE h.lastOperDate = :lastOperDate")
    , @NamedQuery(name = "Historyrecord.findBySndr", query = "SELECT h FROM Historyrecord h WHERE h.sndr = :sndr")
    , @NamedQuery(name = "Historyrecord.findByRcpn", query = "SELECT h FROM Historyrecord h WHERE h.rcpn = :rcpn")
    , @NamedQuery(name = "Historyrecord.findByOperatonDelta", query = "SELECT h FROM Historyrecord h WHERE h.operatonDelta = :operatonDelta")
    , @NamedQuery(name = "Historyrecord.findBybarcode", query = "SELECT h FROM Historyrecord h WHERE h.barcode = :barcode")})
public class Historyrecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "pk_sequence", sequenceName = "historyrec_id_seq", schema = "app")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    private Integer id;
    @Column(name = "destinationaddress_description", length = 255)
    private String destinationaddressDescription;
    @Column(name = "DestinationAddress_Index", length = 6)
    private String destinationAddressIndex;
    @Column(name = "OperationAddress_Index", length = 6)
    private String operationAddressIndex;
    @Column(name = "OperationAddress_Description", length = 255)
    private String operationAddressDescription;
    @Column(name = "MailDirectID")
    private Integer mailDirectID;
    @Column(name = "MailDirectNameRU", length = 255)
    private String mailDirectNameRU;
    @Column(name = "CountryOperID")
    private Integer countryOperID;
    @Column(name = "ComplexItemName", length = 255)
    private String complexItemName;
    @Column(name = "Mass", length = 10)
    private String mass;
    @Column(name = "OperTypeID")
    private Integer operTypeID;
    @Column(name = "OperTypeName", length = 255)
    private String operTypeName;
    @Column(name = "OperAttrID")
    private Integer operAttrID;
    @Column(name = "OperAttrName", length = 255)
    private String operAttrName;
    @Column(name = "OperDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date operDate;
    @Column(name = "lastOperDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastOperDate;
    @Column(name = "Sndr", length = 255)
    private String sndr;
    @Column(name = "Rcpn", length = 255)
    private String rcpn;
    @Column(name = "OperatonDelta")
    private Integer operatonDelta;
    @ManyToOne
    @JoinColumn(name = "barcode")
    @XmlTransient
    private Ticket barcode;

    public Ticket getBarcode() {
        return barcode;
    }

    public void setBarcode(Ticket barcode) {
        this.barcode = barcode;
    }

    public Historyrecord() {
        this.id = 1;
        this.destinationAddressIndex = "";
        this.destinationaddressDescription = "";
        this.operationAddressIndex = "";
        this.operationAddressDescription = "";
        this.mailDirectID = -1;
        this.mailDirectNameRU = "";
        this.countryOperID = -1;
        this.complexItemName = "";
        this.mass = "";
        this.operTypeID = -1;
        this.operTypeName = "";
        this.operAttrID = -1;
        this.operAttrName = "";
        this.operDate = new Date();
        this.sndr = "";
        this.rcpn = "";
        this.operatonDelta = 0;
        this.lastOperDate = null;
    }

    public Historyrecord(Date lastOperDate) {
        this.id = 1;
        this.destinationAddressIndex = "";
        this.destinationaddressDescription = "";
        this.operationAddressIndex = "";
        this.operationAddressDescription = "";
        this.mailDirectID = -1;
        this.mailDirectNameRU = "";
        this.countryOperID = -1;
        this.complexItemName = "";
        this.mass = "";
        this.operTypeID = -1;
        this.operTypeName = "";
        this.operAttrID = -1;
        this.operAttrName = "";
        this.operDate = new Date();
        this.sndr = "";
        this.rcpn = "";
        this.operatonDelta = 0;
        this.lastOperDate = lastOperDate;
    }

    public Historyrecord(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestinationaddressDescription() {
        return destinationaddressDescription;
    }

    public void setDestinationaddressDescription(String destinationaddressDescription) {
        this.destinationaddressDescription = destinationaddressDescription;
    }

    public void setOperDate(String operdate, boolean isBatch) {
        DateFormat sf;
        if (isBatch) {
            sf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        } else {
            sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");            
        }
        Date parseDate;
        try {
            parseDate = sf.parse(operdate);
        } catch (ParseException ex) {
            parseDate = new Date();
        }
        this.operDate = parseDate;
        if (this.lastOperDate instanceof Date) {
            long mill = (this.operDate.getTime() - this.lastOperDate.getTime()) / (60 * 1000);
            this.operatonDelta = (int) mill;
        } else {
            this.operatonDelta = 0;
        }
    }

    public String getDestinationAddressIndex() {
        return destinationAddressIndex;
    }

    public void setDestinationAddressIndex(String destinationAddressIndex) {
        this.destinationAddressIndex = destinationAddressIndex;
    }

    public String getOperationAddressIndex() {
        return operationAddressIndex;
    }

    public void setOperationAddressIndex(String operationAddressIndex) {
        this.operationAddressIndex = operationAddressIndex;
    }

    public String getOperationAddressDescription() {
        return operationAddressDescription;
    }

    public void setOperationAddressDescription(String operationAddressDescription) {
        this.operationAddressDescription = operationAddressDescription;
    }

    public Integer getMailDirectID() {
        return mailDirectID;
    }

    public void setMailDirectID(Integer mailDirectID) {
        this.mailDirectID = mailDirectID;
    }

    public void setMailDirectID(String mailDirectID) {
        this.mailDirectID = Integer.valueOf(mailDirectID);
    }

    public String getMailDirectNameRU() {
        return mailDirectNameRU;
    }

    public void setMailDirectNameRU(String mailDirectNameRU) {
        this.mailDirectNameRU = mailDirectNameRU;
    }

    public Integer getCountryOperID() {
        return countryOperID;
    }

    public void setCountryOperID(Integer countryOperID) {
        this.countryOperID = countryOperID;
    }

    public void setCountryOperID(String countryOperID) {
        this.countryOperID = Integer.valueOf(countryOperID);
    }

    public String getComplexItemName() {
        return complexItemName;
    }

    public void setComplexItemName(String complexItemName) {
        this.complexItemName = complexItemName;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public Integer getOperTypeID() {
        return operTypeID;
    }

    public void setOperTypeID(Integer operTypeID) {
        this.operTypeID = operTypeID;
    }

    public void setOperTypeID(String operTypeID) {
        this.operTypeID = Integer.valueOf(operTypeID);
    }

    public String getOperTypeName() {
        return operTypeName;
    }

    public void setOperTypeName(String operTypeName) {
        this.operTypeName = operTypeName;
    }

    public Integer getOperAttrID() {
        return operAttrID;
    }

    public void setOperAttrID(Integer operAttrID) {
        this.operAttrID = operAttrID;
    }

    public void setOperAttrID(String operAttrID) {
        this.operAttrID = Integer.valueOf(operAttrID);
    }

    public String getOperAttrName() {
        return operAttrName;
    }

    public void setOperAttrName(String operAttrName) {
        this.operAttrName = operAttrName;
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }

    public Date getLastOperDate() {
        return lastOperDate;
    }

    public void setLastOperDate(Date lastOperDate) {
        this.lastOperDate = lastOperDate;
    }

    public String getSndr() {
        return sndr;
    }

    public void setSndr(String sndr) {
        this.sndr = sndr;
    }

    public String getRcpn() {
        return rcpn;
    }

    public void setRcpn(String rcpn) {
        this.rcpn = rcpn;
    }

    public Integer getOperatonDelta() {
        return operatonDelta/60;
    }

    public void setOperatonDelta(Integer operatonDelta) {
        this.operatonDelta = operatonDelta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Historyrecord)) {
            return false;
        }
        Historyrecord other = (Historyrecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nDataAccess.Historyrecord[ OperationAddress_Index=" + operationAddressIndex + ", OperTypeName=" + operTypeName + ", OperAttrName=" + operAttrName + "Delta: " + String.valueOf(this.operatonDelta) + ", OperDate=" + operDate + ']';
    }
}
