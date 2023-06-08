package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import services.Databases;

@WebServlet(name = "RegisterUser", urlPatterns = {"/cadastro"})
public class RegisterUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("cadastro_acesso.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = "", email = "", password = "";

        try {
            name = request.getParameter("name");
            email = request.getParameter("email");
            password = request.getParameter("password");
            HashMap<String, String> hash = new HashMap<>();

            if (name.isEmpty()) {
                hash.put("e_name", "Campo obrigatório!");
            }
            if (email.isEmpty()) {
                hash.put("e_email", "Campo obrigatório!");
            }
            if (password.isEmpty()) {
                hash.put("e_password", "Campo obrigatório!");
            }

            request.setAttribute("errors", hash);

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                request.getRequestDispatcher("cadastro_acesso.jsp").forward(request, response);
            }

            try {
                Connection c = Databases.getConnection();
                Statement s = c.createStatement();
                s.execute(User.getCreateStatement());
                if (!User.checkEmailExists(email)) {
                    User.insertUser(name, email, password);
                    response.sendRedirect("./login");
                } else {
                    hash.put("e_email", "E-mail já está em uso!");
                    s.close();
                    c.close();
                    request.getRequestDispatcher("cadastro_acesso.jsp").forward(request, response);
                }
                s.close();
                c.close();
            } catch (Exception e) {
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
