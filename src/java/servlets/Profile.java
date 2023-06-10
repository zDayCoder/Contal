package servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Contact;
import model.User;
import services.Databases;

@WebServlet(name = "Profile", urlPatterns = {"/profile"})
public class Profile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") != null) {
            User u = (User) request.getSession().getAttribute("user");
            if (u != null) {
                try {
                    if (User.checkEmailExists(u.getEmail())) {

                        request.getSession().setAttribute("user", u);
                        request.getRequestDispatcher("profile.jsp").forward(request, response);
                    }
                } catch (Exception e) {
                    request.setAttribute("error", e.getLocalizedMessage());
                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                }
            }
        } else {
            response.sendRedirect("./login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = "", password = "";

        try {
            name = request.getParameter("name");
            if (request.getParameter("password") != null) {
                if (!request.getParameter("password").isEmpty() && request.getParameter("password").length() > 6) {
                    password = Databases.getMd5Hash(request.getParameter("password"));
                } else {
                    if (request.getSession().getAttribute("user") != null) {
                        User u = (User) request.getSession().getAttribute("user");
                        password = u.getPasswordHash();
                    }

                }
            }
            HashMap<String, String> hash = new HashMap<>();

            if (name.isEmpty()) {
                hash.put("e_name", "Campo obrigatório!");
            }

            if (request.getParameter("password") != null) {
                if (!request.getParameter("password").isEmpty() && request.getParameter("password").length() > 0 && request.getParameter("password").length() < 6) {
                    hash.put("e_password", "Campo obrigatório e valor maior que 6!");
                }
            }

            request.setAttribute("errors", hash);

            if (name.isEmpty() || (request.getParameter("password") != null && request.getParameter("password").length() > 0 && request.getParameter("password").length() < 6)) {
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            } else {

                try {
                    Connection c = Databases.getConnection();
                    Statement s = c.createStatement();
                    s.execute(Contact.getCreateStatement());

                    if (request.getSession().getAttribute("user") != null) {
                        User u = (User) request.getSession().getAttribute("user");
                        User.updateUser(name, password, u.getEmail());
                        request.getSession().setAttribute("user", User.getUser(u.getEmail(), name));

                        response.sendRedirect("./workspace");

                    }

                    s.close();
                    c.close();
                } catch (Exception e) {
                    request.setAttribute("error", e.getLocalizedMessage());
                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                }
            }

        } catch (IOException | ServletException | NoSuchAlgorithmException e) {
            request.setAttribute("error", e.getLocalizedMessage());
            request.getRequestDispatcher("error_page.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
