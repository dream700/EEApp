/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import java.io.File;
import java.util.List;
import javax.ejb.Local;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

/**
 *
 * @author andy
 */
@Local
public interface GetBatchTicketSessionLocal {

    List<String> GetBatchTickets(List<String> tickets) throws SystemException, NotSupportedException;

    boolean GetReadyAnswer(String req) throws SystemException, NotSupportedException;
    
    String Test(String t);
}
