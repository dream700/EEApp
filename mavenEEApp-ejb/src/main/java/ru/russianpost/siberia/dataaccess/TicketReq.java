/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.dataaccess;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Таблица запросов пакетной обработки. Тут хранятся не запрошенные тикеты.
 * После успешного запроса нужно удалять тикет из таблицы
 *
 * @author andy
 */
@Entity
@Table(name = "ticketreq", schema = "app")
@NamedQueries({
    @NamedQuery(name = "TicketReq.findAll", query = "SELECT t FROM TicketReq t")})
public class TicketReq implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "ticketrequest", length = 50, nullable = false)
    private String ticketrequest;
    @Column(name = "DateReq")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReq;

    public TicketReq() {
        dateReq = new Date();
    }

    public Date getDateReq() {
        return dateReq;
    }

    public void setDateReq(Date dateReq) {
        this.dateReq = dateReq;
    }

    public TicketReq(String ticketrequest) {
        this.ticketrequest = ticketrequest;
        dateReq = new Date();
    }

    public String getTicketrequest() {
        return ticketrequest;
    }

    public void setTicketrequest(String ticketrequest) {
        this.ticketrequest = ticketrequest;
    }

}
