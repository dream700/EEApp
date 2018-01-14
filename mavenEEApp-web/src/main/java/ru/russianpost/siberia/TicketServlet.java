/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import ru.russianpost.siberia.dataaccess.Viewhistory;

/**
 *
 * @author Andrey.Isakov
 */
public class TicketServlet extends HttpServlet {

    @EJB
    private GetTicketSession getTicketSession;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"ru\">");
            out.println("<head>");
            out.println("<title>Servlet TicketServlet</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css\" integrity=\"sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ\" crossorigin=\"anonymous\">");
            out.println("<script src=\"https://code.jquery.com/jquery-3.1.1.slim.min.js\" integrity=\"sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n\" crossorigin=\"anonymous\"></script>");
            out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js\" integrity=\"sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb\" crossorigin=\"anonymous\"></script>");
            out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js\" integrity=\"sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn\" crossorigin=\"anonymous\"></script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table class=\"table table-bordered\">");
            out.println("<caption>" + request.getParameter("barcode") + "</caption>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Индекс</th>");
            out.println("<th>Дата</th>");
            out.println("<th>Тип операции</th>");
            out.println("<th>Операции</th>");
            out.println("<th>Время в мин.</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            try {
                List<Viewhistory> qu = getTicketSession.getTicket(request.getParameter("barcode"));
                for (Viewhistory v : qu) {
                    out.println("<tr class=\"success\">");
                    out.println("<td>" + v.getOperationaddressIndex() + "</td>");
                    out.println("<td>" + v.getOperdate() + "</td>");
                    out.println("<td>" + v.getNametype() + "</td>");
                    out.println("<td>" + v.getNameattr() + "</td>");
                    out.println("<td>" + v.getOperatondelta() + "</td>");
                    out.println("</tr>");
                }
            } catch (SystemException | NotSupportedException ex) {
                out.println("Ошибка на сервер!");
                Logger.getLogger(TicketServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
