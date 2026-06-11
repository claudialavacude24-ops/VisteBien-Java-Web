<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Carrito de Compras - VisteBien</title>

        <link rel="icon"
              href="${pageContext.request.contextPath}/img/favicon-32x32.png">

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/css/carrito.css">
    </head>

    <body>

        <header>
            <div class="banner-texto">
                <h1 class="titulo-carrito">
                    Carrito de Compras
                </h1>
            </div>
        </header>
        <main>
            <div class="cart-wrapper">

                <div class="cart-panel">

                    <div class="cart-panel-header">
                        Productos seleccionados

                        <c:if test="${sessionScope.cantidadCarrito gt 0}">
                            <span class="badge">
                                ${sessionScope.cantidadCarrito}
                            </span>
                        </c:if>
                    </div>

                    <c:choose>

                        <c:when test="${compraFinalizada}">
                            <div class="cart-success">
                                <span class="icon">✅</span>
                                <h2>Compra realizada con éxito</h2>
                                <p>Tu pedido fue registrado correctamente.</p>
                            </div>
                        </c:when>

                        <c:when test="${empty items}">
                            <div class="cart-empty">
                                <span class="icon">🛒</span>
                                <h2>Tu carrito está vacío</h2>
                                <p>Agrega productos desde el catálogo.</p>
                            </div>
                        </c:when>

                        <c:otherwise>

                            <c:forEach items="${items}" var="item">

                                <div class="cart-item">

                                    <div class="item-img">

                                        <c:choose>

                                            <c:when test="${not empty item.imagen}">
                                                <img
                                                    src="${pageContext.request.contextPath}/uploads/${item.imagen}"
                                                    alt="${item.nombreProducto}">
                                            </c:when>

                                            <c:otherwise>
                                                <span class="no-img">👕</span>
                                            </c:otherwise>

                                        </c:choose>

                                    </div>

                                    <div class="item-info">

                                        <div class="item-name">
                                            ${item.nombreProducto}
                                        </div>

                                        <div class="item-price-unit">
                                            Precio unitario:
                                            $<fmt:formatNumber value="${item.precio}" pattern="#,##0"/>
                                        </div>

                                        <form class="qty-form"
                                              method="post"
                                              action="${pageContext.request.contextPath}/carritoCompra/editarCantidad">

                                            <input type="hidden"
                                                   name="idProducto"
                                                   value="${item.idProducto}">

                                            <input type="hidden"
                                                   name="idCarrito"
                                                   value="${idCarrito}">

                                            <button type="button"
                                                    class="qty-btn"
                                                    onclick="cambiarCantidad(this, -1)">
                                                -
                                            </button>

                                            <input type="number"
                                                   name="cantidad"
                                                   class="qty-input"
                                                   min="1"
                                                   value="${item.cantidad}">

                                            <button type="button"
                                                    class="qty-btn"
                                                    onclick="cambiarCantidad(this, 1)">
                                                +
                                            </button>

                                        </form>

                                    </div>

                                    <div class="item-right">

                                        <span class="item-subtotal">
                                            $<fmt:formatNumber value="${item.subtotal}" pattern="#,##0"/>
                                        </span>

                                        <form method="post"
                                              action="${pageContext.request.contextPath}/carritoCompra/eliminar">

                                            <input type="hidden"
                                                   name="idCarrito"
                                                   value="${idCarrito}">

                                            <input type="hidden"
                                                   name="idProducto"
                                                   value="${item.idProducto}">

                                            <button class="btn-eliminar"
                                                    type="submit"
                                                    onclick="return confirm('¿Eliminar este producto del carrito?')">
                                                Eliminar
                                            </button>

                                        </form>

                                    </div>

                                </div>

                            </c:forEach>

                        </c:otherwise>

                    </c:choose>

                </div>

                <div class="summary-panel">

                    <div class="summary-header">
                        Resumen del pedido
                    </div>

                    <div class="summary-body">

                        <div class="s-row">
                            <span>Productos (${cantItems})</span>
                            <span>
                                $<fmt:formatNumber value="${total}" pattern="#,##0"/>
                            </span>
                        </div>

                        <div class="s-row">
                            <span>Envío</span>
                            <span>Gratis</span>
                        </div>

                        <hr class="s-divider">

                        <div class="s-total">
                            <span>Total</span>
                            <span>
                                $<fmt:formatNumber value="${total}" pattern="#,##0"/>
                            </span>
                        </div>

                        <c:if test="${not empty items}">

                            <form method="get"
                                  action="${pageContext.request.contextPath}/pago">

                                <button type="submit"
                                        class="btn-finalizar">
                                    Finalizar Compra
                                </button>

                            </form>

                        </c:if>

                        <a class="btn-seguir"
                           href="${pageContext.request.contextPath}/catalogo">
                            Seguir comprando
                        </a>

                    </div>

                </div>

            </div>
        </main>
        <footer>
            <div class="footer-contenido">

                <img src="${pageContext.request.contextPath}/img/logo.jpg"
                     alt="Logo VisteBien">

                <p>
                    © 2026 VisteBien 
                </p>
            </div>
        </footer>

        <script>
            function cambiarCantidad(btn, delta) {

                const form = btn.closest("form");
                const input = form.querySelector(".qty-input");

                let valor = parseInt(input.value || 1);

                valor += delta;

                if (valor < 1) {
                    valor = 1;
                }

                input.value = valor;

                form.submit();
            }
        </script>

    </body>
</html>
