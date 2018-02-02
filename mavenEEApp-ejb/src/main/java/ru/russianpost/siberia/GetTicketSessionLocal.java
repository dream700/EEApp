/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import java.util.List;
import javax.ejb.Local;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import ru.russianpost.siberia.dataaccess.Viewhistory;

/**
 *
 * @author Andrey.Isakov
 */
@Local
public interface GetTicketSessionLocal {
    
    List<Viewhistory> getTicket(String barcode) throws SystemException, NotSupportedException;
    String Test(String t);

}