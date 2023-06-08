package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String email = "", password = "", isFilter = "";

        if (request.getParameter("filter") != null) {
            isFilter = request.getParameter("filter");
            
        } else {

            try {
                if (request.getSession().getAttribute("user") != null) {
                    User u = (User) request.getSession().getAttribute("user");
                }
                User u = (User) request.getSession().getAttribute("user");

                Connection c = Databases.getConnection();
                Statement s = c.createStatement();
                s.execute(User.getCreateStatement());

                if (User.checkEmailExists(email)) {
                    User.changePassword(email, password);
                    response.sendRedirect("./login");
                } else {
                    s.close();
                    c.close();
                    request.getRequestDispatcher("forgot_pass.jsp").forward(request, response);
                }
                s.close();
                c.close();

            } catch (IOException | ServletException e) {
                request.setAttribute("error", e.getLocalizedMessage());
                request.getRequestDispatcher("error_page.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("error", e.getLocalizedMessage());
                request.getRequestDispatcher("error_page.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", e.getLocalizedMessage());
                request.getRequestDispatcher("error_page.jsp").forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
