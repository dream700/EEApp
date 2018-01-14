/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.dataaccess;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andrey.Isakov
 */
@Entity
@Table(name = "viewhistory",schema = "app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Viewhistory.findAll", query = "SELECT v FROM Viewhistory v")
    , @NamedQuery(name = "Viewhistory.findByBarcode", query = "SELECT v FROM Viewhistory v WHERE v.barcode = :barcode")
    , @NamedQuery(name = "Viewhistory.findByOperdate", query = "SELECT v FROM Viewhistory v WHERE v.operdate = :operdate")
    , @NamedQuery(name = "Viewhistory.findByLastoperdate", query = "SELECT v FROM Viewhistory v WHERE v.lastoperdate = :lastoperdate")
    , @NamedQuery(name = "Viewhistory.findByNameattr", query = "SELECT v FROM Viewhistory v WHERE v.nameattr = :nameattr")
    , @NamedQuery(name = "Viewhistory.findByNametype", query = "SELECT v FROM Viewhistory v WHERE v.nametype = :nametype")
    , @NamedQuery(name = "Viewhistory.findByOperatondelta", query = "SELECT v FROM Viewhistory v WHERE v.operatondelta = :operatondelta")
    , @NamedQuery(name = "Viewhistory.findByOperationaddressIndex", query = "SELECT v FROM Viewhistory v WHERE v.operationaddressIndex = :operationaddressIndex")
    , @NamedQuery(name = "Viewhistory.findByOpertypeid", query = "SELECT v FROM Viewhistory v WHERE v.opertypeid = :opertypeid")
    , @NamedQuery(name = "Viewhistory.findByOperattrid", query = "SELECT v FROM Viewhistory v WHERE v.operattrid = :operattrid")})
public class Viewhistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "barcode")
    @Id private String barcode;
    @Column(name = "operdate")
    @Temporal(TemporalType.TIMESTAMP)
    @Id private Date operdate;
    @Column(name = "lastoperdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastoperdate;
    @Column(name = "nameattr")
    private String nameattr;
    @Column(name = "nametype")
    private String nametype;
    @Column(name = "operatondelta")
    private Integer operatondelta;
    @Column(name = "operationaddress_index")
    @Id private String operationaddressIndex;
    @Column(name = "opertypeid")
    @Id private Integer opertypeid;
    @Column(name = "operattrid")
    @Id private Integer operattrid;

    public Viewhistory() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getOperdate() {
        return operdate;
    }

    public void setOperdate(Date operdate) {
        this.operdate = operdate;
    }

    public Date getLastoperdate() {
        return lastoperdate;
    }

    public void setLastoperdate(Date lastoperdate) {
        this.lastoperdate = lastoperdate;
    }

    public String getNameattr() {
        return nameattr;
    }

    public void setNameattr(String nameattr) {
        this.nameattr = nameattr;
    }

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    public Integer getOperatondelta() {
        return operatondelta;
    }

    public void setOperatondelta(Integer operatondelta) {
        this.operatondelta = operatondelta;
    }

    public String getOperationaddressIndex() {
        return operationaddressIndex;
    }

    public void setOperationaddressIndex(String operationaddressIndex) {
        this.operationaddressIndex = operationaddressIndex;
    }

    public Integer getOpertypeid() {
        return opertypeid;
    }

    public void setOpertypeid(Integer opertypeid) {
        this.opertypeid = opertypeid;
    }

    public Integer getOperattrid() {
        return operattrid;
    }

    public void setOperattrid(Integer operattrid) {
        this.operattrid = operattrid;
    }
    
}
