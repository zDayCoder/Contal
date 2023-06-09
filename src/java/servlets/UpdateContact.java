package servlets;

import java.io.IOException;
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

@WebServlet(name = "Update a Contact", urlPatterns = {"/update_contact"})
public class UpdateContact extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") != null) {
            User u = (User) request.getSession().getAttribute("user");
            if (u != null) {
                try {
                    if (User.checkEmailExists(u.getEmail())) {

                        request.getSession().setAttribute("user", u);
                        request.getRequestDispatcher("update_contact.jsp").forward(request, response);
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
        String name = "", description = "", telephone = "", email = "", address = "";

        try {
            name = request.getParameter("name");
            description = request.getParameter("description");
            telephone = request.getParameter("telephone");
            email = request.getParameter("email");
            address = request.getParameter("address");
            HashMap<String, String> hash = new HashMap<>();

            if (name.isEmpty()) {
                hash.put("e_name", "Campo obrigatório!");
            }
            if (telephone.isEmpty()) {
                hash.put("e_telephone", "Campo obrigatório!");
            }

            request.setAttribute("errors", hash);

            if (name.isEmpty() || telephone.isEmpty()) {
                request.getRequestDispatcher("update_contact.jsp").forward(request, response);
            }

            try {
                Connection c = Databases.getConnection();
                Statement s = c.createStatement();
                s.execute(Contact.getCreateStatement());

                if (request.getSession().getAttribute("user") != null) {
                    User u = (User) request.getSession().getAttribute("user");
                    Contact contact = (Contact) request.getSession().getAttribute("updatectt");
                    String oldTelephone = contact.getTelephone();

                    if (!email.equals(contact.getEmail())) {
                        if (Contact.contactMailExists(email, u.getEmail())) {
                            System.out.println("invisivel");
                            hash.put("e_email", "Já existe um contato com o mesmo email.");
                        }
                   }

                    if (!telephone.equals(oldTelephone)) {
                        if (Contact.contactTelephoneExists(telephone, u.getEmail())) {
                            hash.put("e_telephone", "Já existe um contato com o mesmo telefone.");
                        }
                    }

                    
                    if ((!email.equals(contact.getEmail()) && !Contact.contactMailExists(email, u.getEmail())
                            || email.equals(contact.getEmail()) && Contact.contactMailExists(email, u.getEmail()))
                            && (!telephone.equals(oldTelephone) && !Contact.contactTelephoneExists(telephone, u.getEmail()))
                            || telephone.equals(oldTelephone) && Contact.contactTelephoneExists(telephone, u.getEmail())) {
                        if (email.isEmpty()) {
                            Contact.updateContact(telephone, oldTelephone, name, description, null, address, u.getEmail());
                        } else {
                            Contact.updateContact(telephone, oldTelephone, name, description, email, address, u.getEmail());
                        }
                        response.sendRedirect("./workspace");
                    } else {
                        request.setAttribute("errors", hash);
                        request.getRequestDispatcher("update_contact.jsp").forward(request, response);
                    }
                }

                s.close();
                c.close();
            } catch (Exception e) {
                request.setAttribute("error", e.getLocalizedMessage());
                request.getRequestDispatcher("error_page.jsp").forward(request, response);
            }

        } catch (IOException | ServletException e) {
            request.setAttribute("error", e.getLocalizedMessage());
            request.getRequestDispatcher("error_page.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
