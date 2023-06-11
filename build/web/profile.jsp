<%@include file ="WEB-INF/jspf/header-2.jspf"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <title>Perfil</title>
    </head>
    <body class="body-gradient">
        <div class="container">
            <%if (request.getSession().getAttribute("user") != null) {
                    User u = (User) request.getSession().getAttribute("user");
                    if (u != null) {
                        String name = "", password = "";
                        if (u.getName() != null) {
                            if (!u.getName().isEmpty()) {
                                name = u.getName();
                            }
                        }

                        if (u.getPasswordHash() != null) {
                            if (!u.getPasswordHash().isEmpty()) {
                                password = u.getPasswordHash();
                            }
                        }
            %>
            <div class="row">
                <div class="col-3"></div>
                <div class="col">
                    <div class="card card-profile">

                        <div class="card-body card-body-p">
                            <form method="post">
                                <div class="card-header-p">
                                    <h1>Meus Dados</h1>
                                    <button type="submit" class="btn btn-success buttonProfile alt">Alterar</button>
                                    <!--<button class="btn btn-success buttonProfile">Salvar alterações</button>-->
                                </div>
                                <hr>

                                <label class="label-profile">Nome</label>
                                <input required name="name" value="<%=name%>" type="text" class="form-control input-profile"/>
                                <label class="label-profile">Atualizar senha</label>
                                <input minlength="6" name="password" placeholder="Deixe em branco para não alterar" type="password" class="form-control input-profile"/>
                                <label class="label-profile">Foto de Perfil</label>
                                <br>
                                <img id="profileImage" class="image-profile" src="./assets/usuario2.jpg" />

                            </form>

                            <button onclick="showPhotoOptions()" style="border: 1px solid gray; margin: 0px">Mostrar Fotos</button>
                            <div id="photoOptions" class="mt-3" style="display: none;">
                                <center>
                                <img class="input-profile" style="border-radius:10%;width: 125px" src="./assets/usuario1.png" name="photoOption" value="./assets/usuario1.png" onclick="showImage(this)"/>
                                <img class="input-profile" style="border-radius:10%;width: 125px" src="./assets/usuario2.jpg" name="photoOption" value="./assets/usuario2.jpg" onclick="showImage(this)"/>
                                <img class="input-profile" style="border-radius:10%;width: 125px" src="./assets/usuario3.png" name="photoOption" value="./assets/usuario3.png" onclick="showImage(this)"/>
                                <img class="input-profile" style="border-radius:10%;width: 125px" src="./assets/usuario4.png" name="photoOption" value="./assets/usuario4.png" onclick="showImage(this)"/>
                                </center>
                            </div>
D
                            <script>
                                function showPhotoOptions() {
                                    const photoOptions = document.getElementById("photoOptions");
                                    photoOptions.style.display = "block";
                                }

                                function showImage(element) {
                                    const profileImage = document.getElementById("profileImage");
                                    const imageSrc = element.getAttribute("value");
                                    profileImage.setAttribute("src", imageSrc);
                                }
                            </script>
                        </div>
                    </div>
                </div>
                <div class="col-3"></div>
            </div>
            <%}
                }%>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    </body>
</html>
