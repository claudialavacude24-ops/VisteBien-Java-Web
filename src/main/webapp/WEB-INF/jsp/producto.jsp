<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Productos - VisteBien</title>
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
            .field-row {
                display: flex;
                align-items: center;
                gap: 15px;
                flex-wrap: nowrap;
            }

        </style>
    </head>
    <body>

        <header>
            <div class="inicio-encabezado">
                <img src="img/logo.jpg" alt="Logo VisteBien">
                <div class="botones-inicio">
                    <a href="/index"">Inicio</a>
                    <a href="#">Catálogo</a>
                    <a href="/administrador">Administrador</a>
                    <a href="/producto">Productos</a>
                </div>
            </div>
            <div class="banner-texto">
                <h1>Gestión de Productos</h1>
                <p>VisteBien — Panel de Control</p>
            </div>
        </header>

        <div class="crud-section">

            <!-- Insertar producto -->
            <h2>Insertar Producto</h2>
            <form action="${pageContext.request.contextPath}/producto/insertar" method="post" enctype="multipart/form-data">
                <input type="text" name="nombre" placeholder="Nombre" required>
                <input type="text" name="descripcion" placeholder="Descripción" required>
                <input type="number" step="0.01" name="precio" placeholder="Precio" required>
                <input type="number" name="stock" placeholder="Stock" required>

                <label for="imagenFile">Imagen del producto:</label>
                <input type="file" name="imagenFile" id="imagenFile" accept="image/*" required>

                <label for="categoria">Categoría:</label>
                <select name="categoria" id="categoria" required class="campo-select">
                    <option value="Vestidos">Vestidos</option>
                    <option value="Camisas y camisetas">Camisas y camisetas</option>
                    <option value="Pantalones">Pantalones</option>
                    <option value="Ropa deportiva">Ropa deportiva</option>
                    <option value="Accesorios">Accesorios</option>
                    <option value="Maquillaje">Maquillaje</option>
                </select>

                <input type="number" name="idUsuario" placeholder="ID Administrador" required>
                <button type="submit">Guardar</button>
            </form>

            <!-- Actualizar producto -->
            <h2>Actualizar Producto</h2>
            <form id="formActualizar" action="${pageContext.request.contextPath}/producto/actualizar" method="post" enctype="multipart/form-data">

                <div class="field-row">
                    <label for="idProd">ID del Producto:</label>
                    <input type="number" name="id" id="idProd" placeholder="ID del Producto" required>
                    <div id="warnUpdateProd" class="warning">⚠ Solo se permiten números</div>

                    <label for="campo">Seleccione el campo:</label>
                    <select name="campo" id="campo" required onchange="toggleCampo(this.value)" class="campo-select">
                        <option value="Nombre">Nombre</option>
                        <option value="Descripcion">Descripción</option>
                        <option value="Precio">Precio</option>
                        <option value="Stock">Stock</option>
                        <option value="Imagen">Imagen</option>
                        <option value="Categoria">Categoría</option>
                        <option value="IdUsuario">Administrador</option>
                    </select>

                    <!-- Campo dinámico genérico -->
                    <div id="valorContainer">
                        <input type="text" name="valor" id="valorInput" placeholder="Nuevo valor">
                    </div>

                    <!-- Campo de imagen -->
                    <div id="imagenContainer" style="display:none;">
                        <input type="file" name="imagenFile" accept="image/*">
                    </div>

                    <!-- Campo de categoría -->
                    <div id="categoriaContainer" style="display:none;">
                        <select name="valorCategoria" id="valorCategoria" class="campo-select">
                            <option value="Vestidos">Vestidos</option>
                            <option value="Camisas y camisetas">Camisas y camisetas</option>
                            <option value="Pantalones">Pantalones</option>
                            <option value="Ropa deportiva">Ropa deportiva</option>
                            <option value="Accesorios">Accesorios</option>
                            <option value="Maquillaje">Maquillaje</option>
                        </select>
                    </div>
                </div>

                <button type="submit">Actualizar</button>
            </form>



            <!-- Eliminar producto -->
            <h2>Eliminar Producto</h2>
            <form action="${pageContext.request.contextPath}/producto/eliminar" method="post">
                <input type="number" name="id" placeholder="ID del Producto" required>
                <button type="submit" class="btn-danger">Eliminar</button>
            </form>


            <!-- Buscar producto -->
            <h2>Buscar Producto</h2>
            <form action="${pageContext.request.contextPath}/producto" method="get">
                <input type="hidden" name="action" value="buscar">
                <input type="text" name="filtro" placeholder="Ingrese ID, Nombre, Descripción o Categoría">
                <button type="submit">Buscar</button>

                <!-- Consultar productos -->
                <button type="submit">Consultar</button>
            </form>

            <!-- Lista de productos -->
            <h2>Lista de Productos</h2>
            <table>
                <tr>
                    <th>ID</th><th>Nombre</th><th>Descripción</th><th>Precio</th><th>Stock</th><th>Imagen</th><th>Categoría</th><th>Administrador</th>
                </tr>
                <c:forEach var="p" items="${productos}">
                    <tr>
                        <td>${p.idProducto}</td>
                        <td>${p.nombre}</td>
                        <td>${p.descripcion}</td>
                        <td>
                            <fmt:formatNumber value="${p.precio}" type="currency" currencySymbol="$" groupingUsed="true"/>
                        </td>
                        <td>${p.stock}</td>
                        <td>
                            <img src="uploads/${p.imagen}" alt="Imagen Producto" style="height:80px; border-radius:6px;">
                        </td>
                        <td>${p.categoria}</td>
                        <td>${p.idUsuario}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <footer>
            <img src="img/logo.jpg" alt="Logo VisteBien">
            <p>© 2026 VisteBien — Moda con propósito</p>
        </footer>

        <script>
            function toggleCampo(campo) {
                const valorContainer = document.getElementById("valorContainer");
                const imagenContainer = document.getElementById("imagenContainer");
                const categoriaContainer = document.getElementById("categoriaContainer");

                if (campo === "Imagen") {
                    valorContainer.style.display = "none";
                    categoriaContainer.style.display = "none";
                    imagenContainer.style.display = "block";
                } else if (campo === "Categoria") {
                    valorContainer.style.display = "none";
                    imagenContainer.style.display = "none";
                    categoriaContainer.style.display = "block";
                } else {
                    valorContainer.style.display = "block";
                    imagenContainer.style.display = "none";
                    categoriaContainer.style.display = "none";
                }
            }

        </script>

    </body>
</html>
