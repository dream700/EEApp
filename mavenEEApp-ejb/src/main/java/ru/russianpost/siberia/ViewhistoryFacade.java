/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import ru.russianpost.siberia.dataaccess.Viewhistory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.russianpost.siberia.dataaccess.Ticket;

/**
 *
 * @author Andrey.Isakov
 */
@Stateless
public class ViewhistoryFacade extends AbstractFacade<Viewhistory> implements ViewhistoryFacadeLocal {

    @PersistenceContext(unitName = "PERSISTENT-EJB-TEST")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Viewhistory> findBarcode(String barcode) {
        TypedQuery<Viewhistory> query = em.createNamedQuery("Viewhistory.findByBarcode", Viewhistory.class);
        query.setParameter("barcode", barcode);
        List<Viewhistory> res = query.getResultList();
        return res;   
    }
    
    @Override
    public Ticket findTicket(String barcode) {
       return em.find(Ticket.class, barcode);
    }
    
    public ViewhistoryFacade() {
        super(Viewhistory.class);
    }
    
}
