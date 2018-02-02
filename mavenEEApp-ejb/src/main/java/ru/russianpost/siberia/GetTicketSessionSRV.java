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
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import ru.russianpost.siberia.dataaccess.Viewhistory;

/**
 *
 * @author Andrey.Isakov
 */
@WebService(serviceName = "GetTicketSessionSRV")
@Stateless()
public class GetTicketSessionSRV {

    @EJB
    private GetTicketSessionLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "getTicket")
    public List<Viewhistory> getTicket(@WebParam(name = "barcode") String barcode) throws SystemException, NotSupportedException {
        return ejbRef.getTicket(barcode);
    }

    @WebMethod(operationName = "Test")
    public String Test(@WebParam(name = "t") String t) {
        return ejbRef.Test(t);
    }
    
}
