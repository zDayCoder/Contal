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
            <div class="row">
                <div class="col-3"></div>
                <div class="col">
                    <div class="card card-profile">
                        <div class="card-header-p">
                            <h1>Meus Dados</h1>
                            <button class="btn btn-success buttonProfile alt">Alterar</button>
                            <!--<button class="btn btn-success buttonProfile">Salvar alterações</button>-->
                        </div>
                        <hr>
                        <div class="card-body card-body-p">
                            <label class="label-profile">Nome</label>
                            <p class="p-profile">Pedro Pedrinho</p>
                            <input type="text" class="form-control input-profile"/>
                            <label class="label-profile">E-mail</label>
                            <p class="p-profile">pedrinho@email.com</p>
                            <input type="email" class="form-control input-profile"/>
                            <label class="label-profile">Foto de Perfil</label>
                            <br>
                            <img class="image-profile" src="./assets/usuario2.jpg"/>
                            <input type="file" class="form-control input-profile">
                        </div>
                    </div> 
                </div>
                <div class="col-3"></div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
    </body>
</html>
