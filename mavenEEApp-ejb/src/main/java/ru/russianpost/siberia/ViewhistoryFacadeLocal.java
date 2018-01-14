/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import ru.russianpost.siberia.dataaccess.Viewhistory;
import java.util.List;
import javax.ejb.Local;
import ru.russianpost.siberia.dataaccess.Ticket;

/**
 *
 * @author Andrey.Isakov
 */
@Local
public interface ViewhistoryFacadeLocal {

    Viewhistory find(Object id);

    List<Viewhistory> findAll();
    
    List<Viewhistory> findBarcode(String barcode);

    List<Viewhistory> findRange(int[] range);
    
    Ticket findTicket(String barcode);

    int count();
    
}
