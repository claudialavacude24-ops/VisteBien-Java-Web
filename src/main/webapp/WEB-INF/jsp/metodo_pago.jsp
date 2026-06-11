<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="es">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Método de Pago - VisteBien</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/metodo_pago.css">

</head>

<body>

<header>

    <h1>
        Confirmar Compra
    </h1>

</header>

<div class="contenedor">

    <!-- DATOS DEL CLIENTE -->
    <div class="card">

        <h2>Información del Cliente</h2>

        <div class="dato">
            <strong>Nombre:</strong>
            ${usuario.nombre}
        </div>

        <div class="dato">
            <strong>Correo:</strong>
            ${usuario.correo}
        </div>

        <div class="dato">
            <strong>Teléfono:</strong>
            ${usuario.telefono}
        </div>

        <div class="dato">
            <strong>Dirección:</strong>
            ${usuario.direccion}
        </div>

    </div>

    <!-- PRODUCTOS -->
    <div class="card">

        <h2>Productos a Comprar</h2>

        <table>

            <thead>

                <tr>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Subtotal</th>
                </tr>

            </thead>

            <tbody>

                <c:forEach items="${items}" var="item">

                    <tr>

                        <td>
                            ${item.nombreProducto}
                        </td>

                        <td>
                            ${item.cantidad}
                        </td>

                        <td>

                            $
                            <fmt:formatNumber
                                value="${item.precio}"
                                pattern="#,##0"/>

                        </td>

                        <td>

                            $
                            <fmt:formatNumber
                                value="${item.subtotal}"
                                pattern="#,##0"/>

                        </td>

                    </tr>

                </c:forEach>

            </tbody>

        </table>

    </div>

    <!-- MÉTODOS DE PAGO -->
    <div class="card">

        <h2>
            Seleccione Método de Pago
        </h2>

        <form method="post"
              action="${pageContext.request.contextPath}/pago/procesar">

            <c:forEach items="${metodosPago}" var="metodo">

                <div class="metodo-pago">

                    <label>

                        <input type="radio"
                               name="idMetodo"
                               value="${metodo.idMetodo}"
                               required>

                        ${metodo.nombre}

                    </label>

                </div>

            </c:forEach>

            <div class="total">

                <h3>

                    Total a Pagar:

                    $

                    <fmt:formatNumber
                        value="${total}"
                        pattern="#,##0"/>

                </h3>

            </div>

            <button type="submit"
                    class="btn-pagar">

                Proceder al Pago

            </button>

        </form>

    </div>

</div>

</body>

</html>