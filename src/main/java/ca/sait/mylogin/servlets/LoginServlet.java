package ca.sait.mylogin.servlets;

import ca.sait.mylogin.services.AccountService;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vidhy Patel
 */
public class LoginServlet extends HttpServlet {

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
       
        //Check if user has already logged in and has an active session
        if (request.getSession().getAttribute("username") != null) {

            //Check if user had requested to logout from session
            String query = request.getQueryString();
            if (query != null && query.contains("logout")) {
                request.getSession().invalidate();
                request.setAttribute("message", "Successfully logged out.");
            } else {
                response.sendRedirect("home");
                return;
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //Ensure credentials are entered and valid before authenticating a session
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("message", "Missing username or password.");
        } else {
            AccountService account = new AccountService();
            if (account.login(username, password) != null) {
                request.getSession().setAttribute("username", username);
                response.sendRedirect("home");
                return;
            } else {
                request.setAttribute("usernameEntered", username);
                request.setAttribute("message", "Invalid username or password!");
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
