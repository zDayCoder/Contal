<%-- 
    Document   : error_page
    Created on : 6 de jun. de 2023, 20:54:50
    Author     : ryana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
            if (request.getParameter("error") != null) {
            String error = request.getParameter("error");
            if (error != null || !error.isEmpty()) {
        %>
        <h1>Ocorreu um erro: <%=error%></h1>
        <%}}
        %>
    </body>
</html>
