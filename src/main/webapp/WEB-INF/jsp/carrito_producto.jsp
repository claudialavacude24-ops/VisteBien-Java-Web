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
            /* Botón eliminar */
            .btn-danger {
                background-color: #c0392b;
                color: var(--Blanco);
                border: none;
                padding: 10px 16px;
                border-radius: 6px;
                font-weight: 600;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            .btn-danger:hover {
                background-color: #e74c3c;
            }

            /* Footer fijo abajo */
            footer {
                background-color: var(--Azul-petróleo);
                color: var(--Blanco);
                text-align: center;
                padding: 15px;
                position: fixed;
                bottom: 0;
                width: 100%;
            }
            footer img {
                height: 45px;
                margin-bottom: 5px;
            }
            footer p {
                margin: 0;
            }

            /* Tabla azul */
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
            }
            th {
                background-color: var(--Azul-petróleo);
                color: var(--Blanco);
                padding: 10px;
                text-align: center;
            }
            td {
                background-color: var(--Azul-oscuro);
                color: var(--Blanco);
                padding: 10px;
                text-align: center;
            }

            /* Advertencias */
            .warning {
                color: red;
                display: none;
                font-size: 0.85em;
            }

            /* Formularios horizontales */
            .form-horizontal {
                display: flex;
                align-items: center;
                gap: 15px;
                flex-wrap: wrap;
                margin-bottom: 20px;
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
            <form class="form-horizontal" action="/carrito_producto/insertar" method="post">
                <input type="text" name="idProducto" id="idProductoInsert" placeholder="ID Producto" required>
                <div id="warnProductoInsert" class="warning">⚠ Solo números</div>
                <input type="text" name="idUsuario" id="idUsuarioInsert" placeholder="ID Usuario" required>
                <div id="warnUsuarioInsert" class="warning">⚠ Solo números</div>
                <input type="number" name="cantidad" placeholder="Cantidad" required>
                <button type="submit">Guardar</button>
            </form>

            <!-- Actualizar -->
            <h2>Actualizar Cantidad</h2>
            <form class="form-horizontal" id="formActualizarCP" action="/carrito_producto/actualizar" method="post">
                <input type="text" name="idCarrito" id="idCarritoUpdate" placeholder="ID Carrito" required>
                <div id="warnCarritoUpdate" class="warning">⚠ Solo números</div>
                <input type="text" name="idProducto" id="idProductoUpdate" placeholder="ID Producto" required>
                <div id="warnProductoUpdate" class="warning">⚠ Solo números</div>
                <input type="number" name="cantidad" placeholder="Nueva Cantidad" required>
                <button type="submit">Actualizar</button>
            </form>

            <!-- Eliminar -->
            <h2>Eliminar Registro</h2>
            <form class="form-horizontal" id="formEliminarCP" action="/carrito_producto/eliminar" method="post">
                <input type="text" name="idCarrito" id="idCarritoDelete" placeholder="ID Carrito" required>
                <div id="warnCarritoDelete" class="warning">⚠ Solo números</div>
                <input type="text" name="idProducto" id="idProductoDelete" placeholder="ID Producto" required>
                <div id="warnProductoDelete" class="warning">⚠ Solo números</div>
                <button type="submit" class="btn-danger">Eliminar</button>
            </form>

            <!-- Buscar -->
            <h2>Buscar Registros</h2>
            <form class="form-horizontal" action="/carrito_producto" method="get">
                <input type="text" name="filtro" placeholder="Ingrese ID Producto o Usuario">
                <button type="submit">Buscar</button>
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
