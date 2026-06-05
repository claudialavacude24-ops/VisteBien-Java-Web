<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catálogo General - VisteBien</title>
    <link rel="icon" type="image/png" href="img/favicon-32x32.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogo.css">
</head>
<body>

    <!-- ===== ENCABEZADO ===== -->
    <header>
        <div class="inicio-encabezado">

            <!-- Logo -->
            <img src="${pageContext.request.contextPath}/img/logo.jpg" alt="Logo VisteBien">

            <!-- Navegación -->
            <div class="botones-inicio">

                <a href="${pageContext.request.contextPath}/index">Inicio</a>

                <!-- Catálogo desplegable -->
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

                <!-- Carrito -->
                <a href="${pageContext.request.contextPath}/carrito" class="btn-carrito">
                    🛒 Carrito
                </a>

                <!--<a href="${pageContext.request.contextPath}/administrado/a>r">Administrador</a>-->
                <a href="${pageContext.request.contextPath}/producto">Productos</a>

            </div>
        </div>

        <!-- Banner título -->
        <div class="banner-texto">
            <h1>Catálogo General</h1>
            <p>VisteBien — Moda con propósito</p>
        </div>
    </header>

    <!-- ===== GRID DE PRODUCTOS ===== -->
    <div class="contenedor-catalogo">
        <div class="grid-productos">
            <c:forEach var="p" items="${productos}">
                <div class="producto-card">
                    <img src="${pageContext.request.contextPath}/uploads/${p.imagen}"
                         alt="${p.nombre}">
                    <h3>${p.nombre}</h3>
                    <p>${p.descripcion}</p>
                    <p class="precio">
                        <fmt:formatNumber value="${p.precio}" type="currency" currencySymbol="$"/>
                    </p>
                    <button class="boton-agregar">Agregar al carrito</button>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- ===== FOOTER ===== -->
    <footer>
        <img src="${pageContext.request.contextPath}/img/logo.jpg" alt="Logo VisteBien">
        <p>© 2026 VisteBien — Moda con propósito</p>
    </footer>
        <script>
    (function () {
        const btn    = document.getElementById('catalogoSelector');
        const menu   = btn ? btn.closest('.dropdown').querySelector('.dropdown-content') : null;
        if (!btn || !menu) return;

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