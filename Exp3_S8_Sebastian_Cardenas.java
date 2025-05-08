import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Exp3_S8_Sebastian_Cardenas {
    public static void main(String[] args){
        new Exp3_S8_Sebastian_Cardenas().iniciar();
    }


//se realiza breakpoint para evaluar metodo main no encontrado

    
    //metodo para mostrar ventas ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    void mostrarVentas (Scanner sc){
        System.out.println(" -- Listado de Ventas --");
        if (ventasCount == 0) {
            System.out.println("No existen ventas registradas.");
            return;
        }
        for (int i = 0; i < ventasCount; i++) {
            String id = ventasIds[i];
            Cliente cliente = clientes[i];
            System.out.println("- ID Venta: " + id + " | Cliente: " + cliente.nombre + " | Contacto: " + cliente.contacto + " | RUT: " + cliente.id);
        }
    }

    // Variables estáticas ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    static final String NOMBRE_TEATRO = "Teatro Moro";
    static final int MAX_VENTAS = 100;
    static final double PRECIO_VIP = 29900;
    static final double PRECIO_PLATEA_BAJA = 14990;
    static final double PRECIO_PLATEA_ALTA = 17980;
    static final double PRECIO_PALCO = 19900;

    // Variables de instancia |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    String[] ventasIds = new String[MAX_VENTAS];
    Cliente[] clientes = new Cliente[MAX_VENTAS];
    int ventasCount = 0;
    List<Promocion> promociones = new ArrayList<>();
    double totalIngresos = 0;
    int totalEntradasVendidas = 0;
    List<String> usedClientIds = new ArrayList<>();

    // Disponibilidad de asientos|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    int vipDisponibles = 10;
    int plateaBajaDisponibles = 15;
    int plateaAltaDisponibles = 20;
    int palcoDisponibles = 10;

    static class Cliente {
        String id;
        String nombre;
        String contacto;

        Cliente(String id, String nombre, String contacto) {
            this.id = id;
            this.nombre = nombre;
            this.contacto = contacto;
        }
    }

    static class Promocion {
        String nombre;
        double descuento;

        Promocion(String nombre, double descuento) {
            this.nombre = nombre;
            this.descuento = descuento;
        }
    }

    // Constructor: inicializa promociones |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    Exp3_S8_Sebastian_Cardenas() {
        promociones.add(new Promocion("Estudiante", 0.10));
        promociones.add(new Promocion("TerceraEdad", 0.15));
    }

    // Implementación de eliminarVenta|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    private void eliminarVenta (Scanner sc){
        System.out.print("Ingrese ID de venta a eliminar: ");
        String id = sc.nextLine();
        int index = -1;
        for (int i = 0; i < ventasCount; i++) {
            if (ventasIds[i].equals(id)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("ID de venta no encontrado.");
            return;
        }
        // Desplazar los elementos posteriores|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
        for (int i = index; i < ventasCount - 1; i++) {
            ventasIds[i] = ventasIds[i + 1];
            clientes[i] = clientes[i + 1];
        }
        ventasCount--;
        System.out.println("Venta con ID " + id + " eliminada.");
    }

    // Implementación de actualizarCliente|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    private void actualizarCliente (Scanner sc){
        System.out.print("Ingrese ID de venta a modificar: ");
        String id = sc.nextLine();
        int index = -1;
        for (int i = 0; i < ventasCount; i++) {
            if (ventasIds[i].equals(id)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("ID de venta no encontrado.");
            return;
        }
        System.out.print("Nuevo nombre del cliente: ");
        String nuevoNombre = sc.nextLine();
        System.out.print("Nuevo contacto: ");
        String nuevoContacto = sc.nextLine();
        clientes[index].nombre = nuevoNombre;
        clientes[index].contacto = nuevoContacto;
        System.out.println("Datos de cliente actualizados para la venta ID " + id + ".");
    }

    // Mostrar menú principal|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    void iniciar () {
        Scanner sc = new Scanner(System.in);
        List<String> menu = Arrays.asList(
                "Comprar entrada",
                "Eliminar venta del carro",
                "Modificar venta del carro",
                "Mostrar listado de ventas",
                "Salir"
        );
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- " + NOMBRE_TEATRO + " --- Menu Principal ---");
            for (int i = 0; i < menu.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, menu.get(i));
            }
            System.out.print("Seleccione opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1 -> venderEntrada(sc);
                case 2 -> {
                    if (ventasCount == 0) {
                        System.out.println("No hay ventas registradas para eliminar.");
                    } else {
                        eliminarVenta(sc);
                    }
                }
                case 3 -> {
                    if (ventasCount == 0) {
                        System.out.println("No hay ventas registradas para modificar.");
                    } else {
                        actualizarCliente(sc);
                    }
                }
                case 4 -> {
                    if (ventasCount == 0) {
                        System.out.println("No hay ventas registradas para mostrar.");
                    } else {
                        mostrarVentas(sc);
                    }
                }
                case 5 -> {
                    salir = true;
                    System.out.println("Gracias por su visita. !Hasta luego!");
                }
                default -> System.out.println("Opción inválida.");
            }
        }
        sc.close();
    }

    // Opcíon 1: venta de entradas|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    void venderEntrada (Scanner sc){
        System.out.println("\n-- Venta de Entrada --");
        System.out.print("Ingrese su RUT: ");
        String rut = sc.nextLine();
        if (usedClientIds.contains(rut)) {
            System.out.println("Este RUT ya ha sido registrado. Venta cancelada.");
            return;
        }
        usedClientIds.add(rut);

        System.out.print("Nombre del cliente: ");
        String nombre = sc.nextLine();
        System.out.print("Contacto: ");
        String contacto = sc.nextLine();

        clientes[ventasCount] = new Cliente(rut, nombre, contacto);

        System.out.println("\nTarifas disponibles:");
        System.out.println("- VIP: $" + PRECIO_VIP + " (" + vipDisponibles + " disponibles)");
        System.out.println("- Platea Baja: $" + PRECIO_PLATEA_BAJA + " (" + plateaBajaDisponibles + " disponibles)");
        System.out.println("- Platea Alta: $" + PRECIO_PLATEA_ALTA + " (" + plateaAltaDisponibles + " disponibles)");
        System.out.println("- Palco: $" + PRECIO_PALCO + " (" + palcoDisponibles + " disponibles)");

        String tarifaZona = "";
        double base = 0;
        boolean tarifaValida = false;
        while (!tarifaValida) {
            System.out.print("\nIngrese tarifa (VIP/Platea Baja/Platea Alta/Palco): ");
            tarifaZona = sc.nextLine().toLowerCase();
            tarifaValida = tarifaZona.equals("vip") || tarifaZona.equals("platea baja") || tarifaZona.equals("platea alta") || tarifaZona.equals("palco");
            if (!tarifaValida) System.out.println("Tarifa inválida.");
        }

        System.out.print("Cantidad de entradas a comprar: ");
        int cantidad = Integer.parseInt(sc.nextLine());
        int disponibles = switch (tarifaZona) {
            case "vip" -> vipDisponibles;
            case "platea baja" -> plateaBajaDisponibles;
            case "platea alta" -> plateaAltaDisponibles;
            case "palco" -> palcoDisponibles;
            default -> 0;
        };
        if (cantidad > disponibles) {
            System.out.println("Cantidad solicitada excede la disponibilidad. Venta cancelada.");
            return;
        }

        switch (tarifaZona) {
            case "vip" -> {
                base = PRECIO_VIP;
                vipDisponibles -= cantidad;
            }
            case "platea baja" -> {
                base = PRECIO_PLATEA_BAJA;
                plateaBajaDisponibles -= cantidad;
            }
            case "platea alta" -> {
                base = PRECIO_PLATEA_ALTA;
                plateaAltaDisponibles -= cantidad;
            }
            case "palco" -> {
                base = PRECIO_PALCO;
                palcoDisponibles -= cantidad;
            }
        }

        System.out.println("Nota: Hay descuentos disponibles para Estudiante (10%) y Tercera Edad (15%)");
        System.out.print("Tipo Cliente (estudiante/terceraedad/general): ");
        String tipo = sc.nextLine().toLowerCase();

        double desc = 0.0;
        for (Promocion p : promociones) {
            if (p.nombre.equalsIgnoreCase(tipo)) {
                desc = p.descuento;
                break;
            }
        }

        double total = (base * cantidad) * (1 - desc);

        System.out.print("¿Deseas finalizar tu compra o volver al menú principal? (finalizar/menu): ");
        String decision = sc.nextLine().toLowerCase();
        if (decision.equals("menu")) {
            System.out.println("Volviendo al menú principal...");
            return;
        }

        // Resumen de compra|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
        System.out.println("\n --- Resumen de la Venta ---");
        System.out.println("Cliente: " + nombre);
        System.out.println("RUT: " + rut);
        System.out.println("Contacto: " + contacto);
        System.out.println("Tarifa: " + tarifaZona.toUpperCase());
        System.out.println("Precio por entrada: $" + base);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Tipo de cliente: " + tipo);
        System.out.println("Descuento aplicado: " + (desc * 100) + "%");
        System.out.printf("Total a pagar: $%.2f%n", total);
        String ventaId = String.valueOf(++totalEntradasVendidas);
        ventasIds[ventasCount] = ventaId;
        totalIngresos += total;
        System.out.printf("ID de venta generado: %s%n", ventaId);
        System.out.println("------------------------------------");
        System.out.println("\n!Gracias por tu compra! Esperamos que disfrutes tu experiencia en " + NOMBRE_TEATRO + ".");
        ventasCount++;

    }
}
