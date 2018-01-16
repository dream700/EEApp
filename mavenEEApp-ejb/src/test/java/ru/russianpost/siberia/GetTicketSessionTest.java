/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.russianpost.siberia.dataaccess.*;

/**
 *
 * @author Andrey.Isakov
 */
public class GetTicketSessionTest {

    public GetTicketSessionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTicket method, of class GetTicketSession.
     */
    @Test
    public void testGetTicket() throws Exception {
        System.out.println("getTicket");
        String barcode = "63010217232797";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        GetTicketSession instance = (GetTicketSession) container.getContext().lookup("java:global/classes/GetTicketSession");
        List<Viewhistory> expResult = null;
        List<Viewhistory> result = instance.getTicket(barcode);
        assertEquals(expResult, result);
        container.close();
    }

    /**
     * Test of Test method, of class GetTicketSession.
     */
    @Test
    public void testTest() throws Exception {
        System.out.println("Test");
        String t = "ThisTEST";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        GetTicketSession instance = (GetTicketSession) container.getContext().lookup("java:global/classes/GetTicketSession");
        String expResult = "ThisTEST";
        String result = instance.Test(t);
        assertEquals(expResult, result);
        container.close();
    }

}
