<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Carritos - VisteBien</title>
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
                    <a href="/carrito">Carrito</a>
                </div>
            </div>
            <div class="banner-texto">
                <h1>Gestión de Carritos</h1>
                <p>VisteBien — Panel de Control</p>
            </div>
        </header>

        <div class="crud-section">

            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <!-- Insertar -->
            <h2>Insertar Carrito</h2>
            <form action="/carrito/insertar" method="post">
                <div class="field-container">
                    <input type="text" name="idUsuario" id="idUsuarioCarrito" placeholder="ID Usuario" required>
                    <div id="warnInsertCarrito" class="warning">⚠ Solo se permiten números</div>
                </div>
                <button type="submit">Guardar</button>
            </form>

            <!-- Eliminar -->
            <h2>Eliminar Carrito</h2>
            <form id="formEliminarCarrito" action="/carrito/eliminar" method="post">
                <div class="field-container">
                    <input type="text" name="id" id="idEliminarCarrito" placeholder="ID Carrito" required>
                    <div id="warnDeleteCarrito" class="warning">⚠ Solo se permiten números</div>
                </div>
                <button type="submit" class="btn-danger">Eliminar</button>
            </form>

            <!-- Buscar -->
            <h2>Buscar Carrito</h2>
            <form action="/carrito" method="get" style="display:inline;">
                <input type="text" name="filtro" placeholder="Ingrese ID Carrito o Usuario">
                <button type="submit">Buscar</button>
            </form>
            <form action="/carrito" method="get" style="display:inline;">
                <button type="submit" class="btn-secondary">Consultar Todos</button>
            </form>

            <!-- Lista -->
            <h2>Lista de Carritos</h2>
            <table>
                <tr>
                    <th>ID Carrito</th><th>ID Usuario</th>
                </tr>
                <c:forEach var="c" items="${carritos}">
                    <tr>
                        <td>${c.idCarrito}</td>
                        <td>${c.idUsuario}</td>
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
                const form = formId ? document.getElementById(formId) : null;

                input.addEventListener("input", function () {
                    if (/[a-zA-Z]/.test(this.value)) {
                        warn.style.display = "block";
                    } else {
                        warn.style.display = "none";
                    }
                });

                if (form) {
                    form.addEventListener("submit", function (e) {
                        if (/[a-zA-Z]/.test(input.value)) {
                            e.preventDefault();
                            alert("El valor ingresado es incorrecto. Solo se permiten números.");
                        }
                    });
                }
            }

            validarNumerico("idUsuarioCarrito", "warnInsertCarrito", null