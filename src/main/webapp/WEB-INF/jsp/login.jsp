<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Iniciar Sesión - VisteBien</title>
        <link rel="icon" type="image/png" href="img/favicon-32x32.png">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/login.css">
    </head>
    <body>

        <div class="auth-container">

            <div class="auth-left">

                <a href="${pageContext.request.contextPath}/">
                    <img src="${pageContext.request.contextPath}/img/logo.jpg"
                         alt="Logo VisteBien">
                </a>

                <h1>VisteBien</h1>

                <p>
                    Encuentra las mejores prendas,
                    accesorios y maquillaje para
                    expresar tu estilo.
                </p>

            </div>

            <div class="auth-right">

                <h2>Iniciar Sesión</h2>

                <form action="login" method="post">

                    <div class="form-group">
                        <label>Correo</label>
                        <input type="email" name="correo" required>
                    </div>

                    <div class="form-group">
                        <label>Contraseña</label>
                        <input type="password" name="contrasena" required>
                    </div>

                    <button class="btn-auth">
                        Ingresar
                    </button>

                </form>

                <div class="auth-links">
                    <a href="registro">
                        Crear cuenta
                    </a>
                </div>

            </div>

        </div>

    </body>
</html>

