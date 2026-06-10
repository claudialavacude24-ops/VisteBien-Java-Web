<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>VisteBien</title>

        <link rel="icon" type="image/png"
              href="${pageContext.request.contextPath}/img/favicon-32x32.png">

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/index.css">
    </head>

    <body>

        <header>

            <div class="inicio-encabezado">

                <img src="${pageContext.request.contextPath}/img/logo.jpg"
                     alt="Logo VisteBien">

                <div class="botones-inicio">

                    <div class="dropdown">

                        <button id="catalogoSelector" type="button"  class="boton-encabezado">
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

                    <c:choose>

                        <%-- ADMINISTRADOR --%>
                        <c:when test="${rol eq 'admin'}">

                            <a href="${pageContext.request.contextPath}/administrador">
                                Administrador
                            </a>

                            <a href="${pageContext.request.contextPath}/producto">
                                Productos
                            </a>

                            <a href="${pageContext.request.contextPath}/usuario">
                                Usuarios
                            </a>

                            <a href="${pageContext.request.contextPath}/carrito">
                                Gestión Carrito
                            </a>

                            <a href="${pageContext.request.contextPath}/carrito_producto">
                                Carrito Producto
                            </a>

                            <c:if test="${not empty usuarioLogueado}">
                                <span class="usuario-logueado">
                                     ${usuarioLogueado.nombre}
                                </span>
                            </c:if>

                            <a href="${pageContext.request.contextPath}/logout">
                                Cerrar Sesión
                            </a>

                        </c:when>

                        <%-- CLIENTE --%>
                        <c:when test="${rol eq 'cliente'}">

                            <a href="${pageContext.request.contextPath}/carrito">
                                🛒 Carrito
                            </a>

                            <c:if test="${not empty usuarioLogueado}">
                                <span class="usuario-logueado">
                                     ${usuarioLogueado.nombre}
                                </span>
                            </c:if>

                            <a href="${pageContext.request.contextPath}/logout">
                                Cerrar Sesión
                            </a>

                        </c:when>

                        <%-- INVITADO --%>
                        <c:otherwise>

                            <a href="${pageContext.request.contextPath}/login" class="boton-encabezado">
                                Iniciar Sesión
                            </a>

                            <a href="${pageContext.request.contextPath}/registro" class="boton-encabezado">
                                Registrarse
                            </a>

                        </c:otherwise>

                    </c:choose>

                </div>

            </div>

            <div class="contenido-inicio">

                <div class="zoom-image"></div>

                <div class="banner-texto">
                    <p>
                        "VisteBien: transforma tu estilo, destaca tu esencia."
                    </p>
                </div>

            </div>

            <div class="presentacion-bloque">

                <div class="presentacion-imagen">
                    <img src="${pageContext.request.contextPath}/img/contenido 2.png"
                         alt="Exhibición de ropa">
                </div>

                <div class="presentacion-texto">

                    <h1>Tu Estilo, Nuestra Pasión</h1>

                    <p>
                        Más que una tienda, somos tu curador personal de estilo.
                    </p>

                    <a href="${pageContext.request.contextPath}/catalogo"
                       class="boton-catalogo">
                        Catálogo
                    </a>

                </div>

            </div>

            <!-- Catálogo con imágenes -->
            <div class="seccion-catalogo">
                <h2>Nuestro catálogo</h2>
                <div class="catalogo-grid">
                    <div class="categoria-item">
                        <img src="img/contenido 2.png" alt="Vestidos">
                        <h3>Vestidos</h3>
                        <p>Variedad en vestidos para todo tipo de ocasión</p>
                    </div>
                    <div class="categoria-item">
                        <img src="img/imagen1.jpg" alt="Chaquetas">
                        <h3>Chaquetas</h3>
                        <p>Nuestras chaquetas no solo te protegen, sino que definen tu presencia.</p>
                    </div>
                    <div class="categoria-item">
                        <img src="img/imagen2.png" alt="Pantalones">
                        <h3>Pantalones</h3>
                        <p>Desde la oficina hasta la aventura, tenemos el par que se adapta a tu día.</p>
                    </div>

                    <div class="catalogo-footer">
                        <div class="categoria-item">
                            <img src="img/imagen3.png" alt="Deportivo">
                            <h3>Deportivo</h3>
                            <p>Redefine tu descanso con nuestra colección de sudaderas.</p>
                        </div>
                        <div class="categoria-item">
                            <img src="img/imagen4.png" alt="Camisas y Camisetas">
                            <h3>Camisas y camisetas</h3>
                            <p>Redefine tu descanso con nuestra colección de camisas y camisetas.</p>
                        </div>
                        <!-- Nuevo: Maquillaje -->
                        <div class="categoria-item">
                            <img src="img/maquillaje.jpg" alt="Maquillaje">
                            <h3>Maquillaje</h3>
                            <p>Descubre nuestra línea de maquillaje para resaltar tu estilo único.</p>
                        </div>
                        <!-- Nuevo: Accesorios -->
                        <div class="categoria-item">
                            <img src="img/accesorios.avif" alt="Accesorios">
                            <h3>Accesorios</h3>
                            <p>Complementa tu look con accesorios modernos y elegantes.</p>
                        </div>
                    </div>
                </div>
            </div>


        </header>

        <footer>

            <div class="footer-contenido">

                <img src="${pageContext.request.contextPath}/img/logo.jpg"
                     alt="Logo VisteBien">

                <p>
                    © 2026 VisteBien — Moda con propósito.
                    Todos los derechos reservados.
                </p>

                <p>
                    Contacto:
                    <a href="mailto:contacto@vistebien.com">
                        contacto@vistebien.com
                    </a>
                </p>

            </div>

        </footer>

        <script>
            (function () {

                const btn = document.getElementById('catalogoSelector');

                const menu = btn
                        ? btn.closest('.dropdown').querySelector('.dropdown-content')
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