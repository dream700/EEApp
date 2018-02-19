/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import ru.russianpost.siberia.dataaccess.Viewhistory;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.russianpost.siberia.dataaccess.Historyrecord;
import ru.russianpost.siberia.dataaccess.Ticket;

/**
 *
 * @author martin
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class GetTicketSession implements GetTicketSessionLocal {

    @PersistenceContext(unitName = "PERSISTENT-EJB")
    private EntityManager em;
    @Resource
    private SessionContext sessionContext;

    private final String login = "hfaoUUkggxfrPJ";
    private final String password = "8O4OofKi4Nsz";
    Historyrecord his;
    private Ticket ticket;

    UserTransaction utx;

    /*
    Дополнительные функции
     */
    private static String getValue(Element element) {
        String ret = "";
        if (element.hasChildNodes()) {
            ret = element.getChildNodes().item(0).getNodeValue();
        }
        return ret;
    }

    private Object getLastElement(final Collection c) {
        final Iterator itr = c.iterator();
        Object lastElement = itr.next();
        while (itr.hasNext()) {
            lastElement = itr.next();
        }
        return lastElement;
    }

    private Element getData(NodeList nList) {
        Element retNodeList = null;
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                if ("historyRecord".equals(eElement.getLocalName())) {
                    if (his instanceof Historyrecord) {
//                        his.setBarcode(ticket);
                        ticket.getHistoryrecordCollection().add(his);
                        em.persist(his);
                    }
                    if (ticket.getHistoryrecordCollection().size() > 0) {
                        his = new Historyrecord(((Historyrecord) getLastElement(ticket.getHistoryrecordCollection())).getOperDate());
                    } else {
                        his = new Historyrecord();
                    }
                }
                if ("Index".equals(eElement.getLocalName()) & "DestinationAddress".equals(eElement.getParentNode().getLocalName())) {
                    his.setDestinationAddressIndex(getValue(eElement));
                }
                if ("Description".equals(eElement.getLocalName()) & "DestinationAddress".equals(eElement.getParentNode().getLocalName())) {
                    his.setDestinationaddressDescription(getValue(eElement));
                }
                if ("Index".equals(eElement.getLocalName()) & "OperationAddress".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperationAddressIndex(getValue(eElement));
                }
                if ("Description".equals(eElement.getLocalName()) & "OperationAddress".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperationAddressDescription(getValue(eElement));
                }
                if ("ComplexItemName".equals(eElement.getLocalName())) {
                    his.setComplexItemName(getValue(eElement));
                }
                if ("Mass".equals(eElement.getLocalName())) {
                    his.setMass(getValue(eElement));
                }
                if ("Id".equals(eElement.getLocalName()) & "OperType".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperTypeID(getValue(eElement));
                }
                if ("Name".equals(eElement.getLocalName()) & "OperType".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperTypeName(getValue(eElement));
                }
                if ("Id".equals(eElement.getLocalName()) & "OperAttr".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperAttrID(getValue(eElement));
                }
                if ("Name".equals(eElement.getLocalName()) & "OperAttr".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperAttrName(getValue(eElement));
                }
                if ("OperDate".equals(eElement.getLocalName())) {
                    his.setOperDate(getValue(eElement), false);
                }
                if ("Sndr".equals(eElement.getLocalName())) {
                    his.setSndr(getValue(eElement));
                }
                if ("Rcpn".equals(eElement.getLocalName())) {
                    his.setRcpn(getValue(eElement));
                }
                if (eElement.hasChildNodes()) {
                    getData(eElement.getChildNodes());
                }
            }
        }
        return retNodeList;
    }

    @Override
    public List<Viewhistory> getTicket(String barcode) throws SystemException, NotSupportedException {
        utx = sessionContext.getUserTransaction();
        utx.begin();
        try {
            ticket = em.find(Ticket.class, barcode);
            if (ticket == null) {
                ticket = new Ticket(barcode);

                em.persist(ticket);
            }
            if (!ticket.isIsFinal()) {
//                JSONTicketDetail.getTicketDetailData(ticket);
                SOAPRequest instance = new SOAPRequest(login, password);
                SOAPMessage soapmessage;
                soapmessage = instance.GetTicket(ticket.getBarcode());
                if (soapmessage.getSOAPBody().hasFault()) {
                    new Throwable("Ошибка сервера");
                }
//                em.getTransaction().begin();
                try {
                    Query query = em.createQuery("delete Historyrecord where barcode = :barcode");
                    query.setParameter("barcode", ticket);
                    query.executeUpdate();
                    ticket.getHistoryrecordCollection().clear();

                    Document doc = soapmessage.getSOAPBody().extractContentAsDocument();
                    doc.getDocumentElement().normalize();
                    System.out.println(doc.getDocumentElement().getNodeName());
                    NodeList nList = doc.getElementsByTagName("ns3:OperationHistoryData");

                    his = null;
                    for (int i = 0; i < nList.getLength(); i++) {
                        getData(nList);
                    }
                    if (his instanceof Historyrecord) {
                        ticket.getHistoryrecordCollection().add(his);
                        if ((his.getOperTypeID() == 2) | ((his.getOperAttrID() == 1) | (his.getOperAttrID() == 2)) & (his.getOperTypeID() == 5)) {
                            ticket.setIsFinal(true);
                        }
                    }
                    ticket.setDateFetch(new Date());
                    //                em.getTransaction().commit();
                } catch (SOAPException ex) {
                    LOG.severe(ex.getMessage());
                }
            }
            utx.commit();
        } catch (Exception ex) {
            LOG.severe(ex.getMessage());
        }
        TypedQuery<Viewhistory> query = em.createNamedQuery("Viewhistory.findByBarcode", Viewhistory.class);
        query.setParameter("barcode", ticket.getBarcode());
        List<Viewhistory> res = query.getResultList();
        return res;
    }

    private static final Logger LOG = Logger.getLogger(GetTicketSession.class.getName());

    @Override
    public String Test(String t) {
        return t;
    }

}
