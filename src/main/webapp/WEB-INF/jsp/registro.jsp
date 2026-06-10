<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>

    <meta charset="UTF-8">

    <title>Registro - VisteBien</title>

    <link rel="icon"
          type="image/png"
          href="${pageContext.request.contextPath}/img/favicon-32x32.png">

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

        <h1>Únete a VisteBien</h1>

        <p>
            Regístrate y descubre miles de productos
            diseñados para ti.
        </p>

    </div>

    <div class="auth-right">

        <h2>Crear Cuenta</h2>

        <!-- MENSAJE ERROR -->
        <c:if test="${not empty error}">
            <div class="mensaje-error">
                ${error}
            </div>
        </c:if>

        <!-- MENSAJE EXITO -->
        <c:if test="${not empty success}">
            <div class="mensaje-success">
                ${success}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/registro"
              method="post">

            <div class="form-group">
                <label>Nombre Completo</label>

                <input type="text"
                       name="nombre"
                       required
                       minlength="3"
                       maxlength="100">
            </div>

            <div class="form-group">
                <label>Correo Electrónico</label>

                <input type="email"
                       name="correo"
                       required>
            </div>

            <div class="form-group">
                <label>Contraseña</label>

                <input type="password"
                       name="contrasena"
                       required
                       minlength="6">
            </div>

            <div class="form-group">
                <label>Confirmar Contraseña</label>

                <input type="password"
                       name="confirmarContrasena"
                       required
                       minlength="6">
            </div>

            <div class="form-group">
                <label>Teléfono</label>

                <input type="text"
                       name="telefono"
                       maxlength="20">
            </div>

            <div class="form-group">
                <label>Dirección</label>

                <input type="text"
                       name="direccion"
                       maxlength="150">
            </div>

            <button type="submit"
                    class="btn-auth">

                Registrarme

            </button>

        </form>

        <div class="auth-links">

            <a href="${pageContext.request.contextPath}/login">

                Ya tengo cuenta

            </a>

        </div>

    </div>

</div>

</body>
</html>