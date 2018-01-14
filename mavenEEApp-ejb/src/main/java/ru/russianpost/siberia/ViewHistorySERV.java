/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import ru.russianpost.siberia.dataaccess.Ticket;
import ru.russianpost.siberia.dataaccess.Viewhistory;

/**
 *
 * @author andy
 */
@WebService(serviceName = "ViewHistorySERV")
@Stateless()
public class ViewHistorySERV {

    @EJB
    private ViewhistoryFacadeLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "find")
    public Viewhistory find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Viewhistory> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findBarcode")
    public List<Viewhistory> findBarcode(@WebParam(name = "barcode") String barcode) {
        return ejbRef.findBarcode(barcode);
    }

    @WebMethod(operationName = "findRange")
    public List<Viewhistory> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "findTicket")
    public Ticket findTicket(@WebParam(name = "barcode") String barcode) {
        return ejbRef.findTicket(barcode);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }
    
}
