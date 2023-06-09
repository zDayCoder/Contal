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

                    if (request.getParameter("filterType") != null) {
                        filterType = request.getParameter("filterType");

                        if (filterType.equals("t_email")) {
                            if (request.getAttribute("findContact") != null) {
                                Contact c = (Contact) request.getAttribute("findContact");
                                list.add(c);
                            }
                        } else if (filterType.equals("t_phone")) {
                            list = (List) request.getAttribute("findContact");
                        } else {
                            list = Contact.getAllContacts(u.getEmail());
                        }
                    } else {
                        list = Contact.getAllContacts(u.getEmail());
                    }

                    int increment = 0;
                    if (list != null && !list.isEmpty()) {
            %>

            <div class="row workspace">
                <div class="col-md-4 contatos">
                    <%for (Contact contact : list) {
                            if (contact != null) {
                                increment++;
                    %>
                    <form id="contactform<%=increment%>" method="post">
                        <input type="hidden" value="<%=contact.getTelephone()%>" name="view_contact"/>
                        <div class="row" style="cursor: pointer; background: #ccc;padding:8px" onclick="document.getElementById('contactform<%=increment%>').submit()">
                            <div class="contato">
                                <p>Nome: <%=contact.getName()%></p>
                                <img src="./assets/telefone.png" alt="Telefone">
                                <%if (!contact.getEmail().isEmpty()) {%>
                                <img src="./assets/email.png" alt="E-mail">
                                <%}%>
                            </div>
                        </div>
                    </form>
                    <%
                            }
                        }%>
                </div>
                <%

                    if (request.getAttribute("selected_contact") != null) {
                        Contact c = (Contact) request.getAttribute("selected_contact");
                        String name = "", description = "", telephone = "", email = "", address = "";
                        name = c.getName();
                        telephone = c.getTelephone();
                %>
                <div class="col-md-8 none-workspace">

                    <div class="card card-workspace">
                        <div class="card-header">
                            <%=name%>

                            <form id="editcontact" method="post">
                                <input type="hidden" value="<%=c.getTelephone()%>" name="edit_contact"/>
                                <img src="./assets/edit.png" alt="Alterar" style="cursor: pointer; padding:8px" onclick="document.getElementById('editcontact').submit()"/>
                            </form>

                            <form id="deletecontact" method="post">
                                <input type="hidden" value="<%=c.getTelephone()%>" name="delete_contact"/>
                                <img src="./assets/trash.png" alt="Deletar" style="cursor: pointer; padding:8px" onclick="document.getElementById('deletecontact').submit()"/>
                            </form>
                        </div>
                        <div class="card-body">

                            <%if (!c.getDescription().isEmpty() && c.getDescription() != null) {
                                    description = c.getDescription();%>
                            <p>Descrição</p> 
                            <span><%=description%></span>
                            <%}%>

                            <p>Telefone</p> 
                            <span><%=telephone%></span>

                            <%if (!c.getEmail().isEmpty() && c.getEmail() != null) {
                                    email = c.getEmail();%>
                            <p>E-mail</p>
                            <span><%=email%></span>
                            <%}%>

                            <%if (!c.getAddress().isEmpty() && c.getAddress() != null) {
                                    address = c.getAddress();%>
                            <p>Endereço</p> 
                            <span><%=address%></span>
                            <%}%>

                        </div>
                    </div>
                </div>
                <%}%>
            </div>

            <%
            } else {%>
            <h1>Selecione um contato para visualizar mais informações.</h1>
            <%}
                }%>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    </body>
</html>
