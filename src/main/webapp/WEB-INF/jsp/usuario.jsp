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
                    <!-- Catálogo -->
                    <!-- Botón Catálogo con desplegable -->
                    <div class="dropdown">
                        <button id="catalogoSelector">Catálogo</button>
                        <div class="dropdown-content">
                            <a href="${pageContext.request.contextPath}/catalogo">General</a>
                            <a href="${pageContext.request.contextPath}/catalogo/Vestidos">Vestidos</a>
                            <a href="${pageContext.request.contextPath}/catalogo/Pantalones">Pantalones</a>
                            <a href="${pageContext.request.contextPath}/catalogo/Camisas y camisetas">Camisas y camisetas</a>
                            <a href="${pageContext.request.contextPath}/catalogo/Ropa deportiva">Ropa deportiva</a>
                            <a href="${pageContext.request.contextPath}/catalogo/Accesorios">Accesorios</a>
                            <a href="${pageContext.request.contextPath}/catalogo/Maquillaje">Maquillaje</a>
                        </div>
                    </div>
                    <a href="/administrador">Administrador</a>
                    <a href="/producto">Productos</a>
                    <a href="/usuario">Usuarios</a>
                    <a href="/carrito">Gestion Carrito</a>
                    <a href="/carrito_producto">Carrito Producto</a>
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
            
               (function () {
                const btn = document.getElementById('catalogoSelector');
                const menu = btn ? btn.closest('.dropdown').querySelector('.dropdown-content') : null;
                if (!btn || !menu)
                    return;

                let closeTimer = null;   // ✅ Timer para el delay de cierre

                // Abrir al hacer clic en el botón
                btn.addEventListener('click', function (e) {
                    e.stopPropagation();
                    const abierto = menu.classList.contains('visible');
                    if (abierto) {
                        cerrarMenu();
                    } else {
                        abrirMenu();
                    }
                });

                // ✅ Mantener abierto mientras el ratón esté sobre botón o menú
                btn.addEventListener('mouseenter', function () {
                    clearTimeout(closeTimer);
                    abrirMenu();
                });

                btn.addEventListener('mouseleave', function () {
                    // ✅ Espera 400ms antes de cerrar — tiempo suficiente para mover el cursor al menú
                    closeTimer = setTimeout(cerrarMenu, 400);
                });

                menu.addEventListener('mouseenter', function () {
                    clearTimeout(closeTimer);   // ✅ Cancela el cierre si el cursor llega al menú
                });

                menu.addEventListener('mouseleave', function () {
                    closeTimer = setTimeout(cerrarMenu, 300);
                });

                // Cerrar al hacer clic fuera
                document.addEventListener('click', function (e) {
                    if (!btn.closest('.dropdown').contains(e.target)) {
                        cerrarMenu();
                    }
                });

                function abrirMenu() {
                    menu.style.display = 'block';
                    // Pequeño delay para que la transición CSS funcione
                    requestAnimationFrame(function () {
                        menu.classList.add('visible');
                        btn.classList.add('activo');
                    });
                }

                function cerrarMenu() {
                    menu.classList.remove('visible');
                    btn.classList.remove('activo');
                    // ✅ Espera a que termine la animación antes de ocultar
                    setTimeout(function () {
                        if (!menu.classList.contains('visible')) {
                            menu.style.display = 'none';
                        }
                    }, 200);
                }
            })();
        </script>

    </body>
</html>
