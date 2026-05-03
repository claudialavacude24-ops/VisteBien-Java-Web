<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Administradores - VisteBien</title>
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
                </div>
            </div>
            <div class="banner-texto">
                <h1>Gestión de Administradores</h1>
                <p>VisteBien — Panel de Control</p>
            </div>
        </header>

        <div class="crud-section">

            <!-- Insertar -->
            <h2>Insertar Administrador</h2>
            <form action="/administrador/insertar" method="post">
                <input type="text" name="nombre" placeholder="Nombre" required>
                <input type="email" name="correo" placeholder="Correo" required>
                <input type="password" name="contrasena" placeholder="Contraseña" required>
                <input type="text" name="telefono" placeholder="Teléfono" required>
                <button type="submit">Guardar</button>
            </form>

            <!-- Actualizar -->
            <h2>Actualizar Administrador</h2>
            <form id="formActualizar" action="/administrador/actualizar" method="post">
                <div class="field-container">
                    <label for="idAdmin">ID del Administrador:</label>
                    <input type="text" name="id" id="idAdmin" placeholder="ID del Administrador" required>
                    <div id="warnUpdate" class="warning">⚠ Solo se permiten números</div>
                </div>

                <label for="campo">Seleccione el campo a actualizar:</label>
                <select name="campo" id="campo" class="campo-select" required>
                    <option value="Nombre">Nombre</option>
                    <option value="Correo">Correo</option>
                    <option value="Contrasena">Contraseña</option>
                    <option value="Telefono">Teléfono</option>
                </select>
                <label for="valor">Ingrese el nuevo dato:</label>
                <input type="text" name="valor" id="valor" placeholder="Nuevo valor" required>
                <button type="submit">Actualizar</button>
            </form>

            <!-- Eliminar -->
            <h2>Eliminar Administrador</h2>
            <form id="formEliminar" action="/administrador/eliminar" method="post">
                <div class="field-container">
                    <label for="idEliminar">ID del Administrador:</label>
                    <input type="text" name="id" id="idEliminar" placeholder="ID" required>
                    <div id="warnDelete" class="warning">⚠ Solo se permiten números</div>
                </div>
                <button type="submit" class="btn-danger">Eliminar</button>
            </form>

            <!-- Buscar -->
            <h2>Buscar Registros</h2>
            <form action="/administrador" method="get" style="display:inline;">
                <input type="text" name="filtro" placeholder="Ingrese ID, Nombre, Correo o Teléfono">
                <button type="submit">Buscar</button>
            </form>
            <form action="/administrador" method="get" style="display:inline;">
                <button type="submit">Consultar Todos</button>
            </form>

            <!-- Lista -->
            <h2>Lista de Administradores</h2>
            <table>
                <tr>
                    <th>ID</th><th>Nombre</th><th>Correo</th><th>Teléfono</th>
                </tr>
                <c:forEach var="a" items="${administradores}">
                    <tr>
                        <td>${a.idAdministrador}</td>
                        <td>${a.nombre}</td>
                        <td>${a.correo}</td>
                        <td>${a.telefono}</td>
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

                // Validación en tiempo real
                input.addEventListener("input", function () {
                    if (/[a-zA-Z]/.test(this.value)) {
                        warn.style.display = "block";
                    } else {
                        warn.style.display = "none";
                    }
                });

                // Bloquear envío si hay letras
                form.addEventListener("submit", function (e) {
                    if (/[a-zA-Z]/.test(input.value)) {
                        e.preventDefault();
                        alert("El valor ingresado es incorrecto. Solo se permiten números.");
                    }
                });
            }

            // Validación para actualizar y eliminar
            validarNumerico("idAdmin", "warnUpdate", "formActualizar");
            validarNumerico("idEliminar", "warnDelete", "formEliminar");
        </script>

    </body>
</html>