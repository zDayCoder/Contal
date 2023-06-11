
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file = "WEB-INF/jspf/header.jspf" %>

<!DOCTYPE html>
<html>
    <head>

        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastre-se</title>
    </head>
    <body class="body-gradient">

       
        <div class="content-form">

            <main class="form">
                <header>
                    <p>Realize seu cadastro</p>
                </header>
                <form method="post">
                    <%

                        HashMap<String, String> errors = new HashMap<>();
                        if (request.getAttribute("errors") != null) {
                            errors = (HashMap<String, String>) request.getAttribute("errors");
                        }
                    %>

                    <label for="input-name">Nome</label>
                    <input required type="text" id="input-name" name="name">
                    <%
                        if (errors.get("e_name") != null) {%>
                        <p><%=errors.get("e_name")%></p> <%
                        }
                    %>

                    <label for="input-email">E-mail</label>
                    <input required type="email" id="input-email" name="email">
                    <%
                        if (errors.get("e_email") != null) {%>
                        <p><%=errors.get("e_email")%></p> <%
                        }
                    %>

                    <label for="input-password">Senha</label>
                    <input required minlength="6" type="password" id="input-passwrod" name="password">
                    <%
                        if (errors.get("e_password") != null) {%>
                        <p><%=errors.get("e_password")%></p> <%
                        }
                    %>

                    <div class="actions-login-cadastro" >
                        <input type="submit" value="Cadastrar">
                    </div>

                </form>
                    <p class="ou">Ou</p>
                <p class="cadastro">
                    <a href="./login" > Faça login agora!</a>
                </p>
            </main>
        </div>
    </body>
</html>