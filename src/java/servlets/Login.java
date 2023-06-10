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

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (request.getSession().getAttribute("user") != null) {
            User u = (User) request.getSession().getAttribute("user");
            if (u != null) {
                try {
                    if (User.checkEmailExists(u.getEmail())) {

                        request.getSession().setAttribute("user", u);

                        response.sendRedirect("./workspace");
                    }
                } catch (Exception e) {
                    request.setAttribute("error", e.getLocalizedMessage());
                    request.getRequestDispatcher("error_page.jsp").forward(request, response);
                }
            }
        } else {
            request.getRequestDispatcher("login_acesso.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = "", password = "";

        try {
            email = request.getParameter("email");
            password = request.getParameter("password");
            HashMap<String, String> hash = new HashMap<>();

            if (email.isEmpty()) {
                hash.put("e_email", "Campo obrigatório!");
            }
            if (password.isEmpty()) {
                hash.put("e_password", "Campo obrigatório!");
            }

            request.setAttribute("errors", hash);

            if (email.isEmpty() || password.isEmpty()) {
                request.getRequestDispatcher("login_acesso.jsp").forward(request, response);
            }

            try {
                Connection c = Databases.getConnection();
                Statement s = c.createStatement();
                s.execute(User.getCreateStatement());

                if (User.verifyUser(email, password)) {
                    System.out.println("Usuário logado: " + User.findUserByEmail(email).getName());

                    User u = User.getUser(email, password);
                    request.getSession().setAttribute("user", u);

                    response.sendRedirect("./workspace");
                } else {
                    hash.put("e_nouser", "Nenhum usuário encontrado com essas credenciais!");
                    s.close();
                    c.close();
                    request.getRequestDispatcher("login_acesso.jsp").forward(request, response);
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
