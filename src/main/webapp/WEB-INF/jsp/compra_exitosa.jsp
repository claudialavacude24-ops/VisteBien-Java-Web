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

    <title>
        Compra Exitosa
    </title>

    <style>

        body{
            font-family:Arial,sans-serif;
            background:#f4f4f4;
            margin:0;
            padding:0;
        }

        .contenedor{

            width:900px;
            margin:40px auto;
            background:white;
            border-radius:10px;
            padding:30px;
            box-shadow:0 0 10px rgba(0,0,0,.1);
        }

        h1{

            color:green;
            text-align:center;
        }

        table{

            width:100%;
            border-collapse:collapse;
            margin-top:20px;
        }

        th,
        td{

            padding:12px;
            border:1px solid #ddd;
            text-align:center;
        }

        th{

            background:#0b1736;
            color:white;
        }

        .total{

            margin-top:20px;
            text-align:right;
            font-size:24px;
            font-weight:bold;
        }

        .btn{

            display:inline-block;
            margin-top:30px;
            padding:15px 25px;
            background:#0b1736;
            color:white;
            text-decoration:none;
            border-radius:6px;
        }

    </style>

</head>

<body>

<div class="contenedor">

    <h1>
        ✅ Compra realizada correctamente
    </h1>

    <h2>
        Productos adquiridos
    </h2>

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

                    $<fmt:formatNumber
                        value="${item.precio}"
                        pattern="#,##0"/>

                </td>

                <td>

                    $<fmt:formatNumber
                        value="${item.subtotal}"
                        pattern="#,##0"/>

                </td>

            </tr>

        </c:forEach>

        </tbody>

    </table>

    <div class="total">

        Total pagado:

        $<fmt:formatNumber
            value="${total}"
            pattern="#,##0"/>

    </div>

    <center>

        <a href="${pageContext.request.contextPath}/catalogo"
           class="btn">

            Seguir comprando

        </a>

    </center>

</div>

</body>
</html>