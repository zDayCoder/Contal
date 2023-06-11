
<%@page import="model.Contact"%>
<%@page import="model.User"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>

        <link rel="stylesheet" type="text/css" href="style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualizar contato</title>
    </head>
    <body class="body-gradient">

        <div class="content-form" style="margin-top: 30px;margin-bottom: 20px">

            <main class="form">
                <header>
                    <p>Atualize seu contato</p>
                </header>


                <form method="post">
                    <% HashMap<String, String> errors = new HashMap<>();
                        if (request.getAttribute("errors") != null) {
                            errors = (HashMap<String, String>) request.getAttribute("errors");
                        }
                        if (request.getSession().getAttribute("user") != null) {
                            User u = (User) request.getSession().getAttribute("user");

                            if (request.getSession().getAttribute("updatectt") != null) {
                                Contact contact = (Contact) request.getSession().getAttribute("updatectt");
                                String name = "", description = "", telephone = "", email = "", address = "";
                                if (contact != null) {
                                    if (!contact.getName().isEmpty()) {
                                        name = contact.getName();
                                    }
                                    if (contact.getDescription() != null) {
                                        if (!contact.getDescription().isEmpty()) {
                                            description = contact.getDescription();
                                        }
                                    }
                                    if (contact.getTelephone() != null) {
                                        if (!contact.getTelephone().isEmpty()) {
                                            telephone = contact.getTelephone();
                                        }
                                    }
                                    if (contact.getEmail() != null) {
                                        if (!contact.getEmail().isEmpty()) {
                                            email = contact.getEmail();
                                        }
                                    }
                                    if (contact.getAddress() != null) {
                                        if (!contact.getAddress().isEmpty()) {
                                            address = contact.getAddress();
                                        }
                                    }
                                }
                    %>
                    <label for="input-name">Nome*</label>
                    <input required type="text" value="<%=name%>" id="input-name" name="name">
                    <% if (errors.get("e_name") != null) {%>
                    <p><%= errors.get("e_name")%></p>
                    <% }%>

                    <label for="input-description">Descrição</label>
                    <textarea id="input-description" name="description"><%=description%></textarea>
                    <% if (errors.get("e_description") != null) {%>
                    <p><%= errors.get("e_description")%></p>
                    <% }%>

                    <label for="input-phone">Telefone*</label>
                    <input required type="text" value="<%=telephone%>" name="telephone" pattern="[0-9]{2}[0-9]{4,5}[0-9]{4}" title="Formato de telefone inválido. Digite no formato XX-XXXXX-XXXX ou XX-XXXX-XXXX">
                    <% if (errors.get("e_telephone") != null) {%>
                    <p><%= errors.get("e_telephone")%></p>
                    <% }%>

                    <label for="input-email">E-mail</label>
                    <input type="email" name="email" value="<%=email%>" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" title="Formato de email inválido. Digite um email válido no formato exemplo@dominio.com">
                    <% if (errors.get("e_email") != null) {%>
                    <p><%= errors.get("e_email")%></p>
                    <% }%>

                    <label for="input-address">Endereço</label>
                    <input type="text" id="input-address" value="<%=address%>" name="address">
                    <% if (errors.get("e_address") != null) {%>
                    <p><%= errors.get("e_address")%></p>
                    <% }%>

                    <div class="actions-login-cadastro">
                        <input type="submit" value="Atualizar Contato">
                    </div>
                    <%}
                        } else {
                            response.sendRedirect("./login");
                        }
                    %>
                </form>


            </main>
        </div>
    </body>
</html>