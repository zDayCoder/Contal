<%@include file ="WEB-INF/jspf/header-3.jspf"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;1,700&display=swap"
              rel="stylesheet">
        <script src="https://kit.fontawesome.com/5b3bb07e17.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Criar novo contato</title>
    </head>
    <body class="body-gradient">

        <div class="content-form-cadastro">

            <main class="form">
                <header>
                    <p>Cadastre um novo registro</p>
                </header>

                <form method="post">
                    <% HashMap<String, String> errors = new HashMap<>();
                        if (request.getAttribute("errors") != null) {
                            errors = (HashMap<String, String>) request.getAttribute("errors");
                        }
                    %>

                    <label for="input-name">Nome*</label>
                    <input required type="text" id="input-name" name="name">
                    <% if (errors.get("e_name") != null) {%>
                    <p><%= errors.get("e_name")%></p>
                    <% } %>

                    <label for="input-description">Descrição</label>
                    <textarea id="input-description" name="description"></textarea>
                    <% if (errors.get("e_description") != null) {%>
                    <p><%= errors.get("e_description")%></p>
                    <% } %>

                    <label for="input-phone">Telefone*</label>
                    <input required type="text" name="telephone" pattern="[0-9]{2}[0-9]{4,5}[0-9]{4}" title="Formato de telefone inválido. Digite no formato XX-XXXXX-XXXX ou XX-XXXX-XXXX">
                    <% if (errors.get("e_telephone") != null) {%>
                    <p><%= errors.get("e_telephone")%></p>
                    <% } %>

                    <label for="input-email">E-mail</label>
                    <input type="email" name="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" title="Formato de email inválido. Digite um email válido no formato exemplo@dominio.com">
                    <% if (errors.get("e_email") != null) {%>
                    <p><%= errors.get("e_email")%></p>
                    <% } %>

                    <label for="input-address">Endereço</label>
                    <input type="text" id="input-address" name="address" class="endereco">
                    <% if (errors.get("e_address") != null) {%>
                    <p><%= errors.get("e_address")%></p>
                    <% }%>

                    <div class="actions-login">
                        <input type="submit" value="Adicionar Contato">
                    </div>
                </form>

            </main>
        </div>
    </body>
</html>