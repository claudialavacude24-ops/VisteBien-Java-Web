<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Catálogo General - VisteBien</title>

        <link rel="icon"
              type="image/png"
              href="${pageContext.request.contextPath}/img/favicon-32x32.png">

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/catalogo.css">
    </head>

    <body>

        <!-- =========================================
             ENCABEZADO
        ========================================== -->
        <header>

            <div class="inicio-encabezado">

                <!-- LOGO -->
                <img src="${pageContext.request.contextPath}/img/logo.jpg"
                     alt="Logo VisteBien">

                <!-- MENÚ -->
                <div class="botones-inicio">

                    <!-- INICIO -->
                    <a href="${pageContext.request.contextPath}/index">
                        Inicio
                    </a>

                    <!-- CATÁLOGO -->
                    <div class="dropdown">

                        <button id="catalogoSelector">
                            Catálogo
                        </button>

                        <div class="dropdown-content">

                            <a href="${pageContext.request.contextPath}/catalogo">
                                General
                            </a>

                            <a href="${pageContext.request.contextPath}/catalogo/Vestidos">
                                Vestidos
                            </a>

                            <a href="${pageContext.request.contextPath}/catalogo/Pantalones">
                                Pantalones
                            </a>

                            <a href="${pageContext.request.contextPath}/catalogo/Camisas y camisetas">
                                Camisas y camisetas
                            </a>

                            <a href="${pageContext.request.contextPath}/catalogo/Ropa deportiva">
                                Ropa deportiva
                            </a>

                            <a href="${pageContext.request.contextPath}/catalogo/Accesorios">
                                Accesorios
                            </a>

                            <a href="${pageContext.request.contextPath}/catalogo/Maquillaje">
                                Maquillaje
                            </a>

                        </div>

                    </div>

                    <!-- =====================================
                         CLIENTE Y ADMIN
                    ====================================== -->
                    <c:if test="${not empty sessionScope.rol}">

                        <a href="${pageContext.request.contextPath}/carrito">
                            🛒 Carrito
                        </a>

                    </c:if>

                    <!-- =====================================
                         SOLO ADMIN
                    ====================================== -->
                    <c:if test="${sessionScope.rol eq 'admin'}">

                        <a href="${pageContext.request.contextPath}/producto">
                            Productos
                        </a>

                        <a href="${pageContext.request.contextPath}/usuario">
                            Usuarios
                        </a>

                        <a href="${pageContext.request.contextPath}/administrador">
                            Administradores
                        </a>

                    </c:if>

                    <!-- =====================================
                         USUARIO LOGUEADO
                    ====================================== -->
                    <c:if test="${not empty sessionScope.nombreUsuario}">

                        <span class="usuario-logueado">
                             ${sessionScope.nombreUsuario}
                        </span>

                        <a href="${pageContext.request.contextPath}/logout">
                            Cerrar Sesión
                        </a>

                    </c:if>

                    <!-- =====================================
                         INVITADO
                    ====================================== -->
                    <c:if test="${empty sessionScope.nombreUsuario}">

                        <a href="${pageContext.request.contextPath}/login">
                            Iniciar Sesión
                        </a>

                        <a href="${pageContext.request.contextPath}/registro">
                            Registrarse
                        </a>

                    </c:if>

                </div>

            </div>

            <!-- =========================================
                 BANNER
            ========================================== -->
            <div class="banner-texto">

                <h1>Catálogo General</h1>

                <p>
                    VisteBien — Moda con propósito
                </p>

            </div>

        </header>

        <!-- =========================================
             PRODUCTOS
        ========================================== -->
        <div class="contenedor-catalogo">

            <div class="grid-productos">

                <c:forEach var="p" items="${productos}">

                    <div class="producto-card">

                        <img src="${pageContext.request.contextPath}/uploads/${p.imagen}"
                             alt="${p.nombre}">

                        <h3>${p.nombre}</h3>

                        <p>${p.descripcion}</p>

                        <p class="precio">
                            <fmt:formatNumber
                                value="${p.precio}"
                                type="currency"
                                currencySymbol="$"/>
                        </p>

                        <!-- SOLO CLIENTE Y ADMIN -->
                        <c:if test="${not empty sessionScope.rol}">
                            <button class="boton-agregar">
                                Agregar al carrito
                            </button>
                        </c:if>

                        <!-- INVITADO -->
                        <c:if test="${empty sessionScope.rol}">
                            <a href="${pageContext.request.contextPath}/login"
                               class="boton-agregar">
                                Inicia sesión para comprar
                            </a>
                        </c:if>

                    </div>

                </c:forEach>

            </div>

        </div>

        <!-- =========================================
             FOOTER
        ========================================== -->
        <footer>

            <img src="${pageContext.request.contextPath}/img/logo.jpg"
                 alt="Logo VisteBien">

            <p>
                © 2026 VisteBien — Moda con propósito
            </p>

        </footer>

        <!-- =========================================
             SCRIPT DROPDOWN
        ========================================== -->
        <script>

            (function () {

                const btn = document.getElementById('catalogoSelector');

                const menu = btn
                        ? btn.closest('.dropdown')
                                .querySelector('.dropdown-content')
                        : null;

                if (!btn || !menu)
                    return;

                let closeTimer = null;

                btn.addEventListener('click', function (e) {

                    e.stopPropagation();

                    if (menu.classList.contains('visible')) {
                        cerrarMenu();
                    } else {
                        abrirMenu();
                    }
                });

                btn.addEventListener('mouseenter', function () {
                    clearTimeout(closeTimer);
                    abrirMenu();
                });

                btn.addEventListener('mouseleave', function () {
                    closeTimer = setTimeout(cerrarMenu, 400);
                });

                menu.addEventListener('mouseenter', function () {
                    clearTimeout(closeTimer);
                });

                menu.addEventListener('mouseleave', function () {
                    closeTimer = setTimeout(cerrarMenu, 300);
                });

                document.addEventListener('click', function (e) {

                    if (!btn.closest('.dropdown').contains(e.target)) {
                        cerrarMenu();
                    }

                });

                function abrirMenu() {

                    menu.style.display = 'block';

                    requestAnimationFrame(function () {

                        menu.classList.add('visible');
                        btn.classList.add('activo');

                    });

                }

                function cerrarMenu() {

                    menu.classList.remove('visible');
                    btn.classList.remove('activo');

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