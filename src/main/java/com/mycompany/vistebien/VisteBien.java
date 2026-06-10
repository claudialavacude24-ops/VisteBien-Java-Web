package com.mycompany.vistebien;

import com.mycompany.vistebien.dao.AdministradorDAO;
import com.mycompany.vistebien.dao.AgregarProductoDAO;
import com.mycompany.vistebien.dao.CarritoDAO;
import com.mycompany.vistebien.dao.CarritoProductoDAO;
import com.mycompany.vistebien.dao.MetodoPagoDAO;
import com.mycompany.vistebien.dao.ProductoDAO;
import com.mycompany.vistebien.dao.UsuarioDAO;
import com.mycompany.vistebien.dao.ConexionBD;
import com.mycompany.vistebien.model.Administrador;
import com.mycompany.vistebien.model.AgregarProducto;
import com.mycompany.vistebien.model.Carrito;
import com.mycompany.vistebien.model.CarritoProducto;
import com.mycompany.vistebien.model.MetodoPago;
import com.mycompany.vistebien.model.Producto;
import com.mycompany.vistebien.model.Usuario;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class VisteBien {

    public static void main(String[] args) {

        Connection conn = ConexionBD.getConnection();
        Scanner sc = new Scanner(System.in);

        AdministradorDAO adminDAO = new AdministradorDAO();
        AgregarProductoDAO apDAO = new AgregarProductoDAO();
        CarritoDAO carritoDAO = new CarritoDAO();
        CarritoProductoDAO cpDAO = new CarritoProductoDAO();
        MetodoPagoDAO metodoPagoDAO = new MetodoPagoDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        int opcionTabla;

        do {
            System.out.println("\n=================================");
            System.out.println("        SISTEMA VISTEBIEN");
            System.out.println("=================================");
            System.out.println("Seleccione la tabla a administrar:");
            System.out.println("1. Administrador");
            System.out.println("2. Agregar Producto");
            System.out.println("3. Carrito");
            System.out.println("4. CarritoProducto");
            System.out.println("5. MetodoPago");
            System.out.println("6. Producto");
            System.out.println("7. Usuario");
            System.out.println("8. Salir");
            System.out.println("---------------------------------");
            System.out.print("Opcion: ");
            opcionTabla = sc.nextInt();
            sc.nextLine();

            switch (opcionTabla) {
                case 1: // Submenú Administrador
                    int opcionAdmin;
                    do {
                        System.out.println("\n--- Tabla Administrador ---");
                        System.out.println("1. Insertar administrador");
                        System.out.println("2. Listar administradores");
                        System.out.println("3. Actualizar administrador");
                        System.out.println("4. Eliminar administrador");
                        System.out.println("5. Volver al menu principal");
                        System.out.print("Opcion: ");
                        opcionAdmin = sc.nextInt();
                        sc.nextLine();

                        switch (opcionAdmin) {
                            case 1:
                                System.out.print("Nombre: ");
                                String nombre = sc.nextLine();
                                System.out.print("Correo: ");
                                String correo = sc.nextLine();
                                System.out.print("Contrasena: ");
                                String pass = sc.nextLine();
                                System.out.print("Telefono: ");
                                String tel = sc.nextLine();

                                Administrador admin = new Administrador(nombre, correo, pass, tel);
                                adminDAO.insertarAdministrador(admin);
                                break;

                            case 2:
                                List<Administrador> lista = adminDAO.listarAdministradores();
                                System.out.println("\nID | Nombre | Correo | Telefono | Contrasena");
                                for (Administrador a : lista) {
                                    System.out.println(
                                            a.getIdAdministrador() + " | "
                                            + a.getNombre() + " | "
                                            + a.getCorreo() + " | "
                                            + a.getTelefono() + " | "
                                            + "******"
                                    );
                                }
                                break;

                            case 3:
                                System.out.print("ID del administrador a actualizar: ");
                                int id = sc.nextInt();
                                sc.nextLine();

                                int campo;
                                do {
                                    System.out.println("¿Qué desea actualizar?");
                                    System.out.println("1 Nombre");
                                    System.out.println("2 Correo");
                                    System.out.println("3 Contrasena");
                                    System.out.println("4 Telefono");
                                    System.out.println("5 Volver");
                                    campo = sc.nextInt();
                                    sc.nextLine();

                                    if (campo >= 1 && campo <= 4) {
                                        String nombreCampo = "";
                                        switch (campo) {
                                            case 1:
                                                nombreCampo = "Nombre";
                                                break;
                                            case 2:
                                                nombreCampo = "Correo";
                                                break;
                                            case 3:
                                                nombreCampo = "Contrasena";
                                                break;
                                            case 4:
                                                nombreCampo = "Telefono";
                                                break;
                                        }
                                        System.out.print("Nuevo valor: ");
                                        String valor = sc.nextLine();
                                        adminDAO.actualizarCampo(id, nombreCampo, valor);
                                    }
                                } while (campo != 5);
                                break;

                            case 4:
                                System.out.print("ID del administrador a eliminar: ");
                                int idEliminar = sc.nextInt();
                                sc.nextLine();
                                adminDAO.eliminarAdministrador(idEliminar);
                                break;

                            case 5:
                                System.out.println("Volviendo al menú principal...");
                                break;

                            default:
                                System.out.println("Opción invalida");
                        }
                    } while (opcionAdmin != 5);
                    break;

                case 2: // Submenú AgregarProducto
                    int opcionAP;
                    do {
                        System.out.println("\n--- Tabla AgregarProducto ---");
                        System.out.println("1. Listar registros");
                        System.out.println("2. Eliminar registro");
                        System.out.println("3. Volver al menú principal");
                        System.out.print("Opcion: ");
                        opcionAP = sc.nextInt();
                        sc.nextLine();

                        switch (opcionAP) {
                            case 1:

                                List<AgregarProducto> listaAP = apDAO.listarAgregarProductos();
                                System.out.println("\n=== Registros en AgregarProducto ===");
                                System.out.printf("%-15s %-15s %-15s%n", "IdAgregarProducto", "IdProducto", "IdUsuario");
                                System.out.println("----------------------------------------------------------");
                                for (AgregarProducto ap : listaAP) {
                                    System.out.printf("%-15d %-15d %-15d%n",
                                            ap.getIdAgregarProducto(),
                                            ap.getIdProducto(),
                                            ap.getIdUsuario());
                                }
                                break;

                            case 2:
                                // Eliminar registro por IdAgregarProducto
                                System.out.print("ID del registro a eliminar: ");
                                int idAP = sc.nextInt();
                                sc.nextLine();
                                apDAO.eliminarAgregarProducto(idAP);
                                break;

                            case 3:
                                System.out.println("Volviendo al menu principal...");
                                break;

                            default:
                                System.out.println("Opcion invalida");
                        }
                    } while (opcionAP != 3);
                    break;

                case 3: // Submenú Carrito
                    int opcionCarrito;
                    do {
                        System.out.println("\n--- Tabla Carrito ---");
                        System.out.println("1. Insertar carrito");
                        System.out.println("2. Listar carritos");
                        System.out.println("3. Eliminar carrito");
                        System.out.println("4. Volver al menú principal");
                        System.out.print("Opcion: ");
                        opcionCarrito = sc.nextInt();
                        sc.nextLine();

                        switch (opcionCarrito) {
                            case 1:
                                System.out.print("IdUsuario: ");
                                int idUsuario = sc.nextInt();
                                sc.nextLine();
                                Carrito carrito = new Carrito(idUsuario);
                                carritoDAO.insertarCarrito(carrito);
                                break;

                            case 2:
                                List<Carrito> listaCarrito = carritoDAO.listarCarritos();
                                System.out.println("\n=== Registros en Carrito ===");
                                System.out.printf("%-10s %-10s%n", "IdCarrito", "IdUsuario");
                                System.out.println("-------------------------");
                                for (Carrito c : listaCarrito) {
                                    System.out.printf("%-10d %-10d%n", c.getIdCarrito(), c.getIdUsuario());
                                }
                                break;

                            case 3:
                                System.out.print("ID del carrito a eliminar: ");
                                int idCarrito = sc.nextInt();
                                sc.nextLine();
                                carritoDAO.eliminarCarrito(idCarrito);
                                break;

                            case 4:
                                System.out.println("Volviendo al menú principal...");
                                break;

                            default:
                                System.out.println("Opcion invalida");
                        }
                    } while (opcionCarrito != 4);
                    break;

                case 4: // Submenú CarritoProducto
                    int opcionCP;
                    do {
                        System.out.println("\n--- Tabla CarritoProducto ---");
                        System.out.println("1. Insertar registro");
                        System.out.println("2. Listar registros");
                        System.out.println("3. Eliminar registro");
                        System.out.println("4. Volver al menú principal");
                        System.out.print("Opcion: ");
                        opcionCP = sc.nextInt();
                        sc.nextLine();

                        switch (opcionCP) {
                            case 1:
                                System.out.print("IdProducto: ");
                                int idProducto = sc.nextInt();
                                sc.nextLine();
                                System.out.print("IdUsuario: ");
                                int idUsuario = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Cantidad: ");
                                int cantidad = sc.nextInt();
                                sc.nextLine();

                                // Como IdCarrito es autoincrementable, no se pide aquí
                                CarritoProducto cp = new CarritoProducto(idProducto, idUsuario, cantidad);
                                cpDAO.insertarCarritoProducto(cp);
                                break;

                            case 2:
                                List<CarritoProducto> listaCP = cpDAO.listarCarritoProductos();
                                System.out.println("\n=== Registros en CarritoProducto ===");
                                System.out.printf("%-10s %-10s %-10s%n", "IdProducto", "IdUsuario", "Cantidad");
                                System.out.println("-----------------------------------");
                                for (CarritoProducto c : listaCP) {
                                    System.out.printf("%-10d %-10d %-10d%n",
                                            c.getIdProducto(),
                                            c.getIdUsuario(),
                                            c.getCantidad());
                                }
                                break;

                            case 3:
                                System.out.print("IdCarrito del registro: ");
                                int idC = sc.nextInt();
                                sc.nextLine();
                                System.out.print("IdProducto del registro: ");
                                int idP = sc.nextInt();
                                sc.nextLine();

                                cpDAO.eliminarCarritoProducto(idC, idP);
                                break;

                            case 4:
                                System.out.println("Volviendo al menú principal...");
                                break;

                            default:
                                System.out.println("Opción inválida");
                        }
                    } while (opcionCP != 4);
                    break;

                case 5: // Submenú MetodoPago
                    int opcionMP;
                    do {
                        System.out.println("\n--- Tabla MetodoPago ---");
                        System.out.println("1. Insertar método de pago");
                        System.out.println("2. Listar métodos de pago");
                        System.out.println("3. Actualizar nombre");
                        System.out.println("4. Eliminar método de pago");
                        System.out.println("5. Volver al menú principal");
                        System.out.print("Opcion: ");
                        opcionMP = sc.nextInt();
                        sc.nextLine();

                        switch (opcionMP) {
                            case 1:
                                System.out.print("Nombre del metodo: ");
                                String nombreMP = sc.nextLine();
                                MetodoPago mp = new MetodoPago(nombreMP);
                                metodoPagoDAO.insertarMetodoPago(mp);
                                break;

                            case 2:
                                List<MetodoPago> listaMP = metodoPagoDAO.listarMetodosPago();
                                System.out.println("\nIdMetodo | Nombre");
                                for (MetodoPago m : listaMP) {
                                    System.out.println(m.getIdMetodo() + " | " + m.getNombre());
                                }
                                break;

                            case 3:
                                System.out.print("ID del metodo a actualizar: ");
                                int idMP = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Nuevo nombre: ");
                                String nuevoNombre = sc.nextLine();
                                metodoPagoDAO.actualizarNombre(idMP, nuevoNombre);
                                break;

                            case 4:
                                System.out.print("ID del metodo a eliminar: ");
                                int idEliminarMP = sc.nextInt();
                                sc.nextLine();
                                metodoPagoDAO.eliminarMetodoPago(idEliminarMP);
                                break;

                            case 5:
                                System.out.println("Volviendo al menu principal...");
                                break;

                            default:
                                System.out.println("Opcion invalida");
                        }
                    } while (opcionMP != 5);
                    break;

                case 6: // Submenú Producto
                    int opcionProd;
                    do {
                        System.out.println("\n--- Tabla Producto ---");
                        System.out.println("1. Insertar producto");
                        System.out.println("2. Listar productos");
                        System.out.println("3. Actualizar campo");
                        System.out.println("4. Eliminar producto");
                        System.out.println("5. Volver al menu principal");
                        System.out.print("Opción: ");
                        opcionProd = sc.nextInt();
                        sc.nextLine();

                        switch (opcionProd) {
                            case 1:
                                System.out.print("Nombre: ");
                                String nombre = sc.nextLine();
                                System.out.print("Descripción: ");
                                String descripcion = sc.nextLine();
                                System.out.print("Precio: ");
                                double precio = sc.nextDouble();
                                sc.nextLine();
                                System.out.print("Stock: ");
                                int stock = sc.nextInt();
                                sc.nextLine();
                                System.out.print("Imagen (URL): ");
                                String imagen = sc.nextLine();
                                System.out.print("Categoría: ");
                                String categoria = sc.nextLine();   // ✅ nuevo campo
                                System.out.print("IdAdministrador (usuario creador): ");
                                int idAdmin = sc.nextInt();
                                sc.nextLine();

                                // Validar que el administrador existe
                                List<Administrador> listaAdmins = adminDAO.listarAdministradores();
                                boolean existeAdmin = listaAdmins.stream()
                                        .anyMatch(a -> a.getIdAdministrador() == idAdmin);

                                if (existeAdmin) {
                                    Producto p = new Producto(nombre, descripcion, precio, stock, imagen, categoria, idAdmin);
                                    productoDAO.insertarProducto(p);
                                    System.out.println("✅ Producto insertado correctamente.");
                                } else {
                                    System.out.println("❌ Error: El IdAdministrador no corresponde a ningún administrador registrado.");
                                }
                                break;

                            case 2:
                                List<Producto> productos = productoDAO.listarProductosConAdministrador();
                                System.out.println("\nIdProducto | Nombre | Descripción | Precio | Stock | Imagen | Categoria | IdAdministrador");
                                for (Producto p : productos) {
                                    System.out.println(
                                            p.getIdProducto() + " | "
                                            + p.getNombre() + " | "
                                            + p.getDescripcion() + " | "
                                            + p.getPrecio() + " | "
                                            + p.getStock() + " | "
                                            + p.getImagen() + " | "
                                            + p.getCategoria() + " | "
                                            + p.getIdAdministrador()
                                    );
                                }
                                break;

                            case 3:
                                System.out.print("ID del producto a actualizar: ");
                                int idProd = sc.nextInt();
                                sc.nextLine();
                                System.out.println("¿Qué campo desea actualizar?");
                                System.out.println("1 Nombre");
                                System.out.println("2 Descripcion");
                                System.out.println("3 Precio");
                                System.out.println("4 Stock");
                                System.out.println("5 Imagen");
                                System.out.println("6 Categoria");   // ✅ nuevo campo
                                System.out.println("7 Volver");
                                int campo = sc.nextInt();
                                sc.nextLine();

                                if (campo >= 1 && campo <= 6) {
                                    String nombreCampo = "";
                                    Object valor = null;
                                    switch (campo) {
                                        case 1:
                                            nombreCampo = "Nombre";
                                            System.out.print("Nuevo nombre: ");
                                            valor = sc.nextLine();
                                            break;
                                        case 2:
                                            nombreCampo = "Descripcion";
                                            System.out.print("Nueva descripcion: ");
                                            valor = sc.nextLine();
                                            break;
                                        case 3:
                                            nombreCampo = "Precio";
                                            System.out.print("Nuevo precio: ");
                                            valor = sc.nextDouble();
                                            sc.nextLine();
                                            break;
                                        case 4:
                                            nombreCampo = "Stock";
                                            System.out.print("Nuevo stock: ");
                                            valor = sc.nextInt();
                                            sc.nextLine();
                                            break;
                                        case 5:
                                            nombreCampo = "Imagen";
                                            System.out.print("Nueva imagen (URL): ");
                                            valor = sc.nextLine();
                                            break;
                                        case 6:
                                            nombreCampo = "Categoria";
                                            System.out.print("Nueva categoría: ");
                                            valor = sc.nextLine();
                                            break;
                                    }
                                    productoDAO.actualizarCampo(idProd, nombreCampo, valor);
                                    System.out.println("✅ Campo actualizado correctamente.");
                                }
                                break;

                            case 4:
                                System.out.print("ID del producto a eliminar: ");
                                int idEliminarProd = sc.nextInt();
                                sc.nextLine();
                                productoDAO.eliminarProducto(idEliminarProd);
                                System.out.println("✅ Producto eliminado correctamente.");
                                break;

                            case 5:
                                System.out.println("Volviendo al menu principal...");
                                break;

                            default:
                                System.out.println("❌ Opción inválida");
                        }
                    } while (opcionProd != 5);
                    break;

                case 7: // Submenú Usuario
                    int opcionUsuario;
                    do {
                        System.out.println("\n--- Tabla Usuario ---");
                        System.out.println("1. Insertar usuario");
                        System.out.println("2. Listar usuarios");
                        System.out.println("3. Actualizar campo");
                        System.out.println("4. Eliminar usuario");
                        System.out.println("5. Volver al menú principal");
                        System.out.print("Opcion: ");
                        opcionUsuario = sc.nextInt();
                        sc.nextLine();

                        switch (opcionUsuario) {
                            case 1:
                                System.out.print("Nombre: ");
                                String nombreU = sc.nextLine();
                                System.out.print("Correo: ");
                                String correoU = sc.nextLine();
                                System.out.print("Contraseña: ");
                                String contrasenaU = sc.nextLine();
                                System.out.print("Teléfono: ");
                                String telefonoU = sc.nextLine();
                                System.out.print("Direccion: ");
                                String direccionU = sc.nextLine();

                                Usuario u = new Usuario(nombreU, correoU, contrasenaU, telefonoU, direccionU);
                                usuarioDAO.insertarUsuario(u);
                                break;

                            case 2:
                                List<Usuario> listaUsuarios = usuarioDAO.listarUsuarios();
                                System.out.println("\nIdUsuario | Nombre | Correo | Teléfono | Direccion");
                                for (Usuario us : listaUsuarios) {
                                    System.out.println(us.getIdUsuario() + " | "
                                            + us.getNombre() + " | "
                                            + us.getCorreo() + " | "
                                            + us.getTelefono() + " | "
                                            + us.getDireccion());
                                }
                                break;

                            case 3:
                                System.out.print("ID del usuario a actualizar: ");
                                int idUsuario = sc.nextInt();
                                sc.nextLine();
                                System.out.println("¿Qué campo desea actualizar?");
                                System.out.println("1 Nombre");
                                System.out.println("2 Correo");
                                System.out.println("3 Contraseña");
                                System.out.println("4 Teléfono");
                                System.out.println("5 Dirección");
                                System.out.println("6 Volver");
                                int campoU = sc.nextInt();
                                sc.nextLine();

                                if (campoU >= 1 && campoU <= 5) {
                                    String nombreCampo = "";
                                    Object valor = null;
                                    switch (campoU) {
                                        case 1:
                                            nombreCampo = "Nombre";
                                            System.out.print("Nuevo nombre: ");
                                            valor = sc.nextLine();
                                            break;
                                        case 2:
                                            nombreCampo = "Correo";
                                            System.out.print("Nuevo correo: ");
                                            valor = sc.nextLine();
                                            break;
                                        case 3:
                                            nombreCampo = "Contrasena";
                                            System.out.print("Nueva contraseña: ");
                                            valor = sc.nextLine();
                                            break;
                                        case 4:
                                            nombreCampo = "Telefono";
                                            System.out.print("Nuevo teléfono: ");
                                            valor = sc.nextLine();
                                            break;
                                        case 5:
                                            nombreCampo = "Direccion";
                                            System.out.print("Nueva direccion: ");
                                            valor = sc.nextLine();
                                            break;
                                    }
                                    usuarioDAO.actualizarCampo(idUsuario, nombreCampo, valor);
                                }
                                break;

                            case 4:
                                System.out.print("ID del usuario a eliminar: ");
                                int idEliminarUsuario = sc.nextInt();
                                sc.nextLine();
                                usuarioDAO.eliminarUsuario(idEliminarUsuario);
                                break;

                            case 5:
                                System.out.println("Volviendo al menu principal...");
                                break;

                            default:
                                System.out.println("Opcion invalida");
                        }
                    } while (opcionUsuario != 5);
                    break;

                case 8:
                    System.out.println("Sistema finalizado");
                    break;

                default:
                    System.out.println("Opcion inválida");
            }

        } while (opcionTabla != 8);

        sc.close();
    }
}
