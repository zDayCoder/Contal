<%@page import="java.util.ArrayList"%>
<%@page import="model.User"%>
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
                if (request.getSession().getAttribute("user") != null) {
                    User u = (User) request.getSession().getAttribute("user");
                    String filterType = "";
                    List<Contact> list = new ArrayList<>();

                    if (request.getAttribute("findContact") != null) {
                        if (request.getAttribute("findType") != null) {
                            if (request.getAttribute("findType").equals("phone")) {
                                list = (List) request.getAttribute("findContact");
                            } else {
                                System.out.println("puxa por tudo");
                                Contact c = (Contact) request.getAttribute("findContact");
                                list.add(c);
                            }
                        }
                    } else {
                        list = Contact.getAllContacts(u.getEmail());
                    }

                    int increment = 0;
                    if (list != null && !list.isEmpty()) {
            %>

            <div class="row workspace">
                <%for (Contact contact : list) {
                            if (contact != null) {
                                increment++;
                %>
                <div class="col-md-3 contatos" onclick="document.getElementById('contactform<%=increment%>').submit()">

                    <form id="contactform<%=increment%>" method="post">
                        <input type="hidden" value="<%=contact.getTelephone()%>" name="view_contact"/>
                        <div class="row">
                            <div class="contato">
                                <p><%=contact.getName()%></p>
                                <div class="contato-icons">
                                    <img src="./assets/telefone.png" alt="Telefone">
                                    <%if (contact.getEmail() != null) {
                                            if (!contact.getEmail().isEmpty()) {%>
                                    <img src="./assets/email.png" alt="E-mail">
                                </div>
                                <%}
                                    }%>
                            </div>
                        </div>
                    </form>

                </div>
                <%
                }
            }%>
                <div class="col-md-1"></div>
                <%

                    if (request.getAttribute("selected_contact") != null) {
                        Contact c = (Contact) request.getAttribute("selected_contact");
                        String name = "", description = "", telephone = "", email = "", address = "";
                        name = c.getName();
                        telephone = c.getTelephone();
                %>
                <div class="col-md-8 none-workspace">

                    <div class="card card-workspace">
                        <div class="card-header bg-transparent header-workspace">
                            <span id="span-header-workspace"><%=name%></span>
                            <div class="icons-contact">
                                <form id="editcontact" method="post">
                                    <input type="hidden" value="<%=c.getTelephone()%>" name="edit_contact"/>
                                    <img class="icon-contact" src="./assets/edit.png" alt="Alterar" onclick="document.getElementById('editcontact').submit()"/>
                                </form>

                                <form id="deletecontact" method="post">
                                    <input type="hidden" value="<%=c.getTelephone()%>" name="delete_contact"/>
                                    <img class="icon-contact" src="./assets/trash.png" alt="Deletar" onclick="document.getElementById('deletecontact').submit()"/>
                                </form>
                            </div>
                        </div>
                        <div class="card-body">

                            <%if (c.getDescription() != null) {
                                    if (!c.getDescription().isEmpty()) {
                                        description = c.getDescription();%>
                            <p class="p-workspace">Descrição</p> 
                            <span class="span-workspace"><%=description%></span>
                            <%}
                                }%>

                            <p class="p-workspace">Telefone</p> 
                            <span class="span-workspace"><%=telephone%></span>

                            <%if (c.getEmail() != null) {
                                    if (!c.getEmail().isEmpty()) {
                                        email = c.getEmail();%>
                            <p class="p-workspace">E-mail</p>
                            <span class="span-workspace"><%=email%></span>
                            <%}
                                }%>

                            <%if (c.getAddress() != null) {
                                    if (!c.getAddress().isEmpty()) {
                                        address = c.getAddress();%>
                            <p class="p-workspace">Endereço</p> 
                            <span class="span-workspace"><%=address%></span>
                            <%}
                                }%>

                        </div>
                    </div>
                </div>
                <%}%>
            </div>

            <%
            } else {%>
            <h1 id="h1-workspace">Selecione um contato para visualizar mais informações.</h1>
            <%}
                }%>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    </body>
</html>
