<%@page import="java.util.List"%>
<%@page import="model.Contact"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file ="WEB-INF/jspf/header-2.jspf"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <title>Agenda</title>
    </head>

    <body class="body-gradient">

        <div class="container">
            <%
                List<Contact> list = Contact.getAllContacts();
                String name = "", description = "", telephone = "", email = "", address = "";
                if (list != null && !list.isEmpty()) {
            %>

            <div class="row">
                <div class="col-md-4 contatos">
                    <div class="row">
                        <button class="button">Recentes</button>
                        <button class="button">Favoritos</button> 
                    </div>
                    <%for (Contact contact : list) {%>
                    <div class="row" style="cursor: grab">
                        <div class="contato">
                            <input class="form-check-input" type="checkbox"/>
                            <p>Nome: <%=contact.getName()%></p>
                            <p>Telefone: <%=contact.getTelephone()%>
                                <img src="./assets/telefone.png" alt="Telefone">
                            </p>
                            <p>E-mail: <%=contact.getEmail()%>
                                <img src="./assets/email.png" alt="E-mail">
                            </p>
                             <span>Trabalho</span>
                        </div>
                    </div>
                    <%}%>
                </div>
                <div class="col-md-8 none-workspace">
                    <!--<h1 class="none-workspace">Selecione um contato para visualizar mais informações.</h1>-->
                    <div class="card">
                        <div class="card-header">
                            Alice Silva <button class="button">Trabalho</button> <img src="./assets/opcoes.png" alt="Mais Opções" >
                        </div>
                        <div class="card-body">

                            <p>Descrição</p> 
                            <span><%=description%></span>

                            <p>Telefone <span>(13) 3434-3434</span></p>
                            <p>Celular <span>(13) 3434-3434</span></p>
                            <p>E-mail <span>alicesilva@email.com</span></p>
                            <p>Endereço</p>
                            <span>Praça 19 de Janeiro, 144 - Boqueirão, Praia Grande - SP 11700-100</span>
                        </div>
                    </div>
                </div>
            </div>

            <%} else {%>
            <h1>Sem nenhum contato por enquanto</h1>
            <%}%>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    </body>
</html>
