
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file = "WEB-INF/jspf/header.jspf" %>

<!DOCTYPE html>
<html>
    <head>

        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Entre</title>
    </head>
    <body class="body-gradient">

        <p class="cadastro">
            <a href="./cadastro" >
                Não tem conta? <br>Cadastre-se agora!</a>
        </p>
        <div class="content-form">

            <main class="form">
                <header>
                    <p>Faça seu login</p>
                </header>
                <form method="post">
                    <%
                        HashMap<String, String> errors = new HashMap<>();
                        if (request.getAttribute("errors") != null) {
                            errors = (HashMap<String, String>) request.getAttribute("errors");
                        }
                    %>

                    <label for="input-email">E-mail </label>
                    <input required type="email" id="input-email" name="email" >
                    <%
                        if (errors.get("e_name") != null) {%>
                        <p><%=errors.get("e_name")%></p> <%
                        }
                    %>
                    <label for="input-password">Senha </label>
                    <input required minlength="6" type="password" id="input-passwrod" name="password">
                    <%
                        if (errors.get("e_password") != null) {%>
                        <p><%=errors.get("e_password")%></p> <%
                        }
                    %>
                    <p class="recovery"><a href="./forgot_pass">Esqueci minha senha</a></p>
                    <%
                        if (errors.get("e_nouser") != null) {%>
                    <p><%=errors.get("e_nouser")%></p> <%
                        }
                    %>
                    <div class="actions-login" >
                        <input type="submit" value="Entrar">

                    </div>

                </form>

            </main>

        </div>
    </body>
</html>