<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>VisteBien</title>
        <link rel="icon" type="image/png" href="img/favicon-32x32.png">
        <link rel="stylesheet" href="css/index.css">
    </head>
    <body>

        <header>
            <div class="inicio-encabezado">
                <!-- Logo -->
                <img src="img/logo.jpg" alt="Logo VisteBien">

                <!-- Botones controlados por rol -->
                <div class="botones-inicio">
                    <!-- Catálogo -->
                    <select id="catalogoSelector">
                        <option value="">Catálogo</option>
                    </select>

                    <!-- Menú dinámico según rol -->
                    <c:choose>
                        <c:when test="${rol == 'admin'}">
                            <a href="administrador">Administrador</a>
                            <a href="producto">Productos</a>
                            <a href="usuario">Usuarios</a>
                            <a href="carrito">Carrito</a>
                            <a href="carrito_producto">Carrito Producto</a>
                        </c:when>
                        <c:when test="${rol == 'cliente'}">
                            <a href="carrito">Carrito</a>
                            <a href="carrito_producto">Carrito Producto</a>
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>

                    <!-- Selector de rol (para pruebas) -->
                    <form action="setRol" method="post" style="display:inline;">
                        <select id="rolSelector" name="rol" onchange="this.form.submit()">
                            <option value="invitado" ${rol == 'invitado' ? 'selected' : ''}>Invitado</option>
                            <option value="cliente" ${rol == 'cliente' ? 'selected' : ''}>Usuario</option>
                            <option value="admin" ${rol == 'admin' ? 'selected' : ''}>Administrador</option>
                        </select>
                    </form>
                </div>
            </div>

            <!-- Banner -->
            <div class="contenido-inicio">
                <div class="zoom-image"></div>
                <div class="banner-texto">
                    <p>"VisteBien: transforma tu estilo, destaca tu esencia."</p>
                </div>
            </div>

            <!-- Presentación -->
            <div class="presentacion-bloque">
                <div class="presentacion-imagen">
                    <img src="img/contenido 2.png" alt="Exhibición de ropa en tienda">
                </div>
                <div class="presentacion-texto">
                    <h1>Tu Estilo, Nuestra Pasión</h1>
                    <p>Más que una tienda, somos tu curador personal de estilo.</p>
                    <a href="#" class="boton-catalogo">Catálogo</a>
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
                    </div>
                </div>
            </div>

        </header>

        <!-- FOOTER -->
        <footer>
            <div class="footer-contenido">
                <img src="img/logo.jpg" alt="Logo VisteBien">
                <p>© 2026 VisteBien — Moda con propósito. Todos los derechos reservados.</p>
                <p>Contacto: <a href="mailto:contacto@vistebien.com">contacto@vistebien.com</a></p>
            </div>
        </footer>

    </body>
</html>