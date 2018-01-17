/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.russianpost.siberia.dataaccess.Historyrecord;
import ru.russianpost.siberia.dataaccess.Ticket;
import ru.russianpost.siberia.dataaccess.TicketReq;

/**
 *
 * @author andy
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class GetBatchTicketSession implements GetBatchTicketSessionLocal {

    @PersistenceContext(unitName = "PERSISTENT-EJB")
    private EntityManager em;
    @Resource
    private SessionContext sessionContext;

    private final String login = "hfaoUUkggxfrPJ";
    private final String password = "8O4OofKi4Nsz";
    Historyrecord his;
    private Ticket ticket;

    UserTransaction utx;

    private static String getTagValue(String sTag, Element eElement) {
        Attr attr = eElement.getAttributeNode(sTag);
        return attr.getNodeValue();
    }

    private Object getLastElement(final Collection c) {
        final Iterator itr = c.iterator();
        Object lastElement = itr.next();
        while (itr.hasNext()) {
            lastElement = itr.next();
        }
        return lastElement;
    }

    Date last;

    private Element getData(NodeList nList) {
        Element retNodeList = null;
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                if ("ns3:Item".equals(eElement.getNodeName())) {
                    String barcode = getTagValue("Barcode", eElement);
                    ticket = em.find(Ticket.class, barcode);
                    if (ticket == null) {
                        ticket = new Ticket(barcode);
                        em.persist(ticket);
                    } else {
                        Query query = em.createQuery("delete Historyrecord where barcode = :barcode");
                        query.setParameter("barcode", ticket);
                        query.executeUpdate();
                    }
                    ticket.setDateFetch(new Date());
                    last = null;
                }
                if ("Operation".equals(eElement.getLocalName())) {
                    his = new Historyrecord(last);
                    his.setOperTypeID(getTagValue("OperTypeID", eElement));
                    his.setOperTypeName(getTagValue("OperName", eElement));
                    his.setOperAttrID(getTagValue("OperCtgID", eElement));
                    his.setOperDate(getTagValue("DateOper", eElement), true);
                    his.setOperationAddressIndex(getTagValue("IndexOper", eElement));
                    his.setBarcode(ticket);
                    if ((his.getOperTypeID() == 2) | ((his.getOperAttrID() == 1) | (his.getOperAttrID() == 2)) & (his.getOperTypeID() == 5)) {
                        ticket.setIsFinal(true);
                    }
                    last = his.getOperDate();
                    em.persist(his);
                }
                if (eElement.hasChildNodes()) {
                    getData(eElement.getChildNodes());
                }
            }
        }
        return retNodeList;
    }

    public boolean getSOAPTicketAnswer() throws SystemException, NotSupportedException {
        utx = sessionContext.getUserTransaction();
        utx.begin();
        try {
            TypedQuery<TicketReq> query = em.createNamedQuery("TicketReq.findAll", TicketReq.class);
            List<TicketReq> req = query.getResultList();
            for (TicketReq ticketReq : req) {
                SOAPBatchRequest instance = new SOAPBatchRequest(login, password);
                try {
                    SOAPMessage result = instance.GetResponseByTicket(ticketReq.getTicketrequest());
                    SOAPBody soapBody = result.getSOAPBody();
                    if (soapBody.hasFault()) {
                        Logger.getLogger(GetBatchTicketSession.class.getName()).log(Level.SEVERE, null, "Fault with code: " + soapBody.getFault().getFaultCode());
                        return false;
                    }
                    Document doc = result.getSOAPBody().extractContentAsDocument();
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("ns2:answerByTicketResponse");
                    for (int i = 0; i < nList.getLength(); i++) {
                        getData(nList);
                    }
                    em.remove(ticketReq);
                } catch (SOAPException ex) {
                    Logger.getLogger(GetBatchTicketSession.class.getName()).log(Level.SEVERE, null, ex);
                    utx.rollback();
                    return false;
                }
            }
            utx.commit();
        } catch (Exception ex) {
            Logger.getLogger(GetBatchTicketSession.class.getName()).log(Level.SEVERE, null, "Fault with code: " + ex.getMessage());
        }
        return true;
    }

    /*Формирование и запрос пакета SOAP
     */
    public boolean getSOAPTicketRequest() throws SystemException, NotSupportedException {
        utx = sessionContext.getUserTransaction();
        utx.begin();
        try {
            TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findDateFetchisNull", Ticket.class);
            List<Ticket> tks;
            while (!(tks = query.getResultList()).isEmpty()) {
                SOAPBatchRequest instance = new SOAPBatchRequest(login, password);
                SOAPMessage result;
                try {
                    result = instance.GetTicket(tks);
                    if (result instanceof SOAPMessage) {
                        SOAPBody soapBody = result.getSOAPBody();
                        if (soapBody.hasFault()) {
                            throw new SOAPException("Fault with code: " + soapBody.getFault().getFaultCode());
                        }
                        Document doc = result.getSOAPBody().extractContentAsDocument();
                        doc.getDocumentElement().normalize();
                        String br = doc.getElementsByTagName("value").item(0).getFirstChild().getNodeValue();
                        TicketReq tr = new TicketReq(br);
                        for (Ticket tk : tks) {
                            tk.setDateFetch(new Date());
                        }
                        em.persist(tr);
                    }
                } catch (SOAPException | TransformerException ex) {
                    Logger.getLogger(GetBatchTicketSession.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            utx.commit();
        } catch (Exception ex) {
            Logger.getLogger(GetBatchTicketSession.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean GetBatchTickets(List<String> tickets) throws NotSupportedException, SystemException {
        utx = sessionContext.getUserTransaction();
        utx.begin();
        try {
            for (String line : tickets) {
                Ticket tk = em.find(Ticket.class, line);
                if (tk == null) {
                    tk = new Ticket(line);
                    em.persist(tk);
                } else if (!tk.isIsFinal()) {
                    tk.setDateFetch(null);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            Logger.getLogger(GetBatchTicketSession.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        getSOAPTicketRequest();
        return true;
    }

    @Override
    public String Test(String t) {
        return t;
    }

}