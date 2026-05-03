<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Usuarios - VisteBien</title>
        <link rel="icon" type="image/png" href="img/favicon-32x32.png">
        <link rel="stylesheet" href="css/general.css">
        <style>
            .field-container {
                position: relative;
                margin-bottom: 1.5em;
            }
            .warning {
                color: red;
                display: none;
                font-size: 0.85em;
                position: absolute;
                top: 100%;
                left: 0;
            }
        </style>
    </head>
    <body>

        <header>
            <div class="inicio-encabezado">
                <img src="img/logo.jpg" alt="Logo VisteBien">
                <div class="botones-inicio">
                    <a href="/index">Inicio</a>
                    <a href="#">Catálogo</a>
                    <a href="/administrador">Administrador</a>
                    <a href="/producto">Productos</a>
                    <a href="/usuario">Usuarios</a>
                </div>
            </div>
            <div class="banner-texto">
                <h1>Gestión de Usuarios</h1>
                <p>VisteBien — Panel de Control</p>
            </div>
        </header>

        <div class="crud-section">

            <!-- Mensajes de error -->
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <!-- Insertar -->
            <h2>Insertar Usuario</h2>
            <form action="/usuario/insertar" method="post">
                <input type="text" name="nombre" placeholder="Nombre" required>
                <input type="email" name="correo" placeholder="Correo" required>
                <input type="password" name="contrasena" placeholder="Contraseña" required>
                <input type="text" name="telefono" placeholder="Teléfono" required>
                <input type="text" name="direccion" placeholder="Dirección" required>
                <button type="submit">Guardar</button>
            </form>

            <!-- Actualizar -->
            <h2>Actualizar Usuario</h2>
            <form id="formActualizarUsuario" action="/usuario/actualizar" method="post">
                <div class="field-container">
                    <input type="text" name="id" id="idUsuario" placeholder="ID del Usuario" required>
                    <div id="warnUpdateUsuario" class="warning">⚠ Solo se permiten números</div>
                </div>

                <label for="campo">Seleccione el campo:</label>
                <select name="campo" id="campo" class="campo-select" required>
                    <option value="Nombre">Nombre</option>
                    <option value="Correo">Correo</option>
                    <option value="Contrasena">Contraseña</option>
                    <option value="Telefono">Teléfono</option>
                    <option value="Direccion">Dirección</option>
                </select>

                <label for="valor">Ingrese el nuevo dato:</label>
                <input type="text" name="valor" id="valor" placeholder="Nuevo valor" required>
                <button type="submit">Actualizar</button>
            </form>

            <!-- Eliminar -->
            <h2>Eliminar Usuario</h2>
            <form id="formEliminarUsuario" action="/usuario/eliminar" method="post">
                <div class="field-container">
                    <input type="text" name="id" id="idEliminarUsuario" placeholder="ID del Usuario" required>
                    <div id="warnDeleteUsuario" class="warning">⚠ Solo se permiten números</div>
                </div>
                <button type="submit" class="btn-danger">Eliminar</button>
            </form>

            <!-- Buscar -->
            <h2>Buscar Usuario</h2>
            <form action="/usuario" method="get" style="display:inline;">
                <input type="text" name="filtro" placeholder="Ingrese ID, Nombre, Correo o Teléfono">
                <button type="submit">Buscar</button>
            </form>
            <form action="/usuario" method="get" style="display:inline;">
                <button type="submit" class="btn-secondary">Consultar Todos</button>
            </form>

            <!-- Lista -->
            <h2>Lista de Usuarios</h2>
            <table>
                <tr>
                    <th>ID</th><th>Nombre</th><th>Correo</th><th>Teléfono</th><th>Dirección</th>
                </tr>
                <c:forEach var="u" items="${usuarios}">
                    <tr>
                        <td>${u.idUsuario}</td>
                        <td>${u.nombre}</td>
                        <td>${u.correo}</td>
                        <td>${u.telefono}</td>
                        <td>${u.direccion}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <footer>
            <img src="img/logo.jpg" alt="Logo VisteBien">
            <p>© 2026 VisteBien — Moda con propósito</p>
        </footer>

        <script>
            function validarNumerico(inputId, warnId, formId) {
                const input = document.getElementById(inputId);
                const warn = document.getElementById(warnId);
                const form = document.getElementById(formId);

                input.addEventListener("input", function () {
                    if (/[a-zA-Z]/.test(this.value)) {
                        warn.style.display = "block";
                    } else {
                        warn.style.display = "none";
                    }
                });

                form.addEventListener("submit", function (e) {
                    if (/[a-zA-Z]/.test(input.value)) {
                        e.preventDefault();
                        alert("El valor ingresado es incorrecto. Solo se permiten números.");
                    }
                });
            }

            validarNumerico("idUsuario", "warnUpdateUsuario", "formActualizarUsuario");
            validarNumerico("idEliminarUsuario", "warnDeleteUsuario", "formEliminarUsuario");
        </script>

    </body>
</html>
