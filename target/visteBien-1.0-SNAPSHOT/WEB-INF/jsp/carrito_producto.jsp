<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Carrito Producto - VisteBien</title>
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
                    <a href="/carrito">Carrito</a>
                    <a href="/carrito_producto">Carrito Producto</a>
                </div>
            </div>
            <div class="banner-texto">
                <h1>Gestión de Carrito Producto</h1>
                <p>VisteBien — Panel de Control</p>
            </div>
        </header>

        <div class="crud-section">

            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>

            <!-- Insertar -->
            <h2>Insertar Registro</h2>
            <form action="/carrito_producto/insertar" method="post">
                <div class="field-container">
                    <input type="text" name="idProducto" id="idProductoInsert" placeholder="ID Producto" required>
                    <div id="warnProductoInsert" class="warning">⚠ Solo se permiten números</div>
                </div>
                <div class="field-container">
                    <input type="text" name="idUsuario" id="idUsuarioInsert" placeholder="ID Usuario" required>
                    <div id="warnUsuarioInsert" class="warning">⚠ Solo se permiten números</div>
                </div>
                <input type="number" name="cantidad" placeholder="Cantidad" required>
                <button type="submit">Guardar</button>
            </form>

            <!-- Actualizar -->
            <h2>Actualizar Cantidad</h2>
            <form id="formActualizarCP" action="/carrito_producto/actualizar" method="post">
                <div class="field-container">
                    <input type="text" name="idCarrito" id="idCarritoUpdate" placeholder="ID Carrito" required>
                    <div id="warnCarritoUpdate" class="warning">⚠ Solo se permiten números</div>
                </div>
                <div class="field-container">
                    <input type="text" name="idProducto" id="idProductoUpdate" placeholder="ID Producto" required>
                    <div id="warnProductoUpdate" class="warning">⚠ Solo se permiten números</div>
                </div>
                <input type="number" name="cantidad" placeholder="Nueva Cantidad" required>
                <button type="submit">Actualizar</button>
            </form>

            <!-- Eliminar -->
            <h2>Eliminar Registro</h2>
            <form id="formEliminarCP" action="/carrito_producto/eliminar" method="post">
                <div class="field-container">
                    <input type="text" name="idCarrito" id="idCarritoDelete" placeholder="ID Carrito" required>
                    <div id="warnCarritoDelete" class="warning">⚠ Solo se permiten números</div>
                </div>
                <div class="field-container">
                    <input type="text" name="idProducto" id="idProductoDelete" placeholder="ID Producto" required>
                    <div id="warnProductoDelete" class="warning">⚠ Solo se permiten números</div>
                </div>
                <button type="submit" class="btn-danger">Eliminar</button>
            </form>

            <!-- Buscar -->
            <h2>Buscar Registros</h2>
            <form action="/carrito_producto" method="get" style="display:inline;">
                <input type="text" name="filtro" placeholder="Ingrese ID Producto o Usuario">
                <button type="submit">Buscar</button>
            </form>
            <form action="/carrito_producto" method="get" style="display:inline;">
                <button type="submit" class="btn-secondary">Consultar Todos</button>
            </form>

            <!-- Lista -->
            <h2>Lista de Registros</h2>
            <table>
                <tr>
                    <th>ID Carrito</th><th>ID Producto</th><th>ID Usuario</th><th>Cantidad</th>
                </tr>
                <c:forEach var="cp" items="${carritoProductos}">
                    <tr>
                        <td>${cp.idCarrito}</td>
                        <td>${cp.idProducto}</td>
                        <td>${cp.idUsuario}</td>
                        <td>${cp.cantidad}</td>
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

            validarNumerico("idProductoInsert", "warnProductoInsert", null);
            validarNumerico("idUsuarioInsert", "warnUsuarioInsert", null);
            validarNumerico("idCarritoUpdate", "warnCarritoUpdate", "formActualizarCP");
            validarNumerico("idProductoUpdate", "warnProductoUpdate", "formActualizarCP");
            validarNumerico("idCarritoDelete", "warnCarritoDelete", "formEliminarCP");
            validarNumerico("idProductoDelete", "warnProductoDelete", "formEliminarCP");
        </script>

    </body>
</html>
