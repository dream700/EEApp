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
    
    /*
    Поиск ШПИ - если нет, запрос идет к АСУ РПО серверу и ШПИ ложится в БД
    */
    List<Viewhistory> findBarcode(String barcode);

    List<Viewhistory> findRange(int[] range);
    /*
    Поиск ШПИ по БД, если нет, то возвращает null
    */
    Ticket findTicket(String barcode);

    int count();
    
}
