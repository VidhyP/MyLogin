package ca.sait.mylogin.servlets;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 *
 * @author Vidhy Patel
 */
public class HomeServlet extends HttpServlet {

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
        //Look if user has already logged in and has an active session
        String username = (String) request.getSession().getAttribute("username");

        //Without an active session, users redirected to login page
        if (username == null) {
            response.sendRedirect("login");
            return;
        }

        request.setAttribute("user", username);
        getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
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
        getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }

}
