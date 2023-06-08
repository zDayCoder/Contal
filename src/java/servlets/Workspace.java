package servlets;

import java.io.IOException;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Contact;
import model.User;
import services.Databases;

@WebServlet(name = "Workspace", urlPatterns = {"/workspace"})
public class Workspace extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") != null) {
            User u = (User) request.getSession().getAttribute("user");
            if (u != null) {
                try {
                    if (User.checkEmailExists(u.getEmail())) {

                        request.getSession().setAttribute("user", u);

                        request.getRequestDispatcher("workspace.jsp").forward(request, response);
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
        if (request.getSession().getAttribute("user") != null) {
            User u = (User) request.getSession().getAttribute("user");
            String email = "", password = "", filter = "", filterType = "", selected = "", view_contact = "", menu = "", deleteupdate;
            if (request.getParameter("menu") != null) {
                switch (request.getParameter("menu")) {
                    case "profile" -> {
                        response.sendRedirect("./profile");
                    }
                    case "settings" -> {
                        response.sendRedirect("./settings");
                    }
                    case "logout" -> {
                        request.getSession().removeAttribute("user");
                        response.sendRedirect("./login");
                    }
                }

            }

            if (request.getParameter("view_contact") != null) {
                view_contact = request.getParameter("view_contact");
                try {

                    Connection c = Databases.getConnection();
                    Statement s = c.createStatement();
                    s.execute(Contact.getCreateStatement());

                    Contact cont = Contact.getContactByTelephone(view_contact, u.getEmail());
                    if (cont == null) {
                        request.getRequestDispatcher("workspace.jsp").forward(request, response);
                    }
                    request.setAttribute("selected_contact", cont);
                    s.close();
                    c.close();
                    request.getRequestDispatcher("workspace.jsp").forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("error", e.getLocalizedMessage());
                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                }
            }
            
            if (request.getParameter("delete_contact") != null) {
                deleteupdate = request.getParameter("delete_contact");
                Contact
            }
            if (request.getParameter("delete_contact") != null) {
                deleteupdate = request.getParameter("delete_contact");
            }
            if (request.getParameter("filter") != null) {
                try {
                    filter = request.getParameter("filter");
                    filterType = request.getParameter("filterType");
                    switch (filterType) {
                        case "t_email" -> {
                            Contact c = Contact.findContactByEmail(filter);
                            request.setAttribute("findType", filterType);
                            request.setAttribute("findContact", c);
                            request.getRequestDispatcher("workspace.jsp").forward(request, response);
                        }
                        case "t_phone" -> {
                            List<Contact> c = Contact.searchContactsByPhone(filter, u.getEmail());
                            request.setAttribute("findType", filterType);
                            request.setAttribute("findContact", c);
                            request.getRequestDispatcher("workspace.jsp").forward(request, response);
                        }
                        default -> {
                            request.setAttribute("error", "Pesquisa inválida");
                            request.getRequestDispatcher("error_page.jsp").forward(request, response);
                        }
                    }
                } catch (Exception e) {
                    request.setAttribute("error", e.getLocalizedMessage());
                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                }
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
