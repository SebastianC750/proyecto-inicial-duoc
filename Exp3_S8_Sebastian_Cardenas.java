import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Exp3_S8_Sebastian_Cardenas {

    void mostrarVentas(Scanner sc) {
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

    static final String NOMBRE_TEATRO = "Teatro Moro";
    static final int MAX_VENTAS = 100;
    static final double PRECIO_VIP = 30000.0;
    static final double PRECIO_PLATEA_BAJA = 15000.0;
    static final double PRECIO_PLATEA_ALTA = 18000.0;
    static final double PRECIO_PALCO = 20000.0;

    String[] ventasIds = new String[MAX_VENTAS];
    Cliente[] clientes = new Cliente[MAX_VENTAS];
    int ventasCount = 0;

    List<Promocion> promociones = new ArrayList<>();
    double totalIngresos = 0;
    int totalEntradasVendidas = 0;
    List<String> usedClientIds = new ArrayList<>();

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

    Exp3_S8_Sebastian_Cardenas() {
        promociones.add(new Promocion("Estudiante", 0.10));
        promociones.add(new Promocion("TerceraEdad", 0.15));
    }

    void iniciar() {
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
            System.out.println("\n--- " + NOMBRE_TEATRO + " - Men\u00fa Principal ---");
            for (int i = 0; i < menu.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, menu.get(i));
            }
            System.out.print("Seleccione opci\u00f3n: ");
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
                    System.out.println("Gracias por su visita. \u00a1Hasta luego!");
                }
                default -> System.out.println("Opci\u00f3n inv\u00e1lida.");
            }
        }
        sc.close();
    }

    void venderEntrada(Scanner sc) {
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
            System.out.print("Ingrese tarifa (VIP/Platea Baja/Platea Alta/Palco): ");
            tarifaZona = sc.nextLine().toLowerCase();
            tarifaValida = tarifaZona.equals("vip") || tarifaZona.equals("platea baja") ||
                    tarifaZona.equals("platea alta") || tarifaZona.equals("palco");
            if (!tarifaValida) System.out.println("Tarifa inv\u00e1lida.");
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
            case "vip" -> { base = PRECIO_VIP; vipDisponibles -= cantidad; }
            case "platea baja" -> { base = PRECIO_PLATEA_BAJA; plateaBajaDisponibles -= cantidad; }
            case "platea alta" -> { base = PRECIO_PLATEA_ALTA; plateaAltaDisponibles -= cantidad; }
            case "palco" -> { base = PRECIO_PALCO; palcoDisponibles -= cantidad; }
        }

        System.out.println("Nota: Hay descuentos disponibles para Estudiante (10%) y TerceraEdad (15%)");
        System.out.print("\u00bfDeseas continuar con la compra o volver al men\u00fa principal? (finalizar/menu): ");
        String decision = sc.nextLine().toLowerCase();
        if (decision.equals("menu")) {
            System.out.println("Volviendo al men\u00fa principal...");
            return;
        }

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

        System.out.println(" --- Resumen de la Venta ---");
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
        System.out.println("\u00a1Gracias por tu compra! Esperamos que disfrutes tu experiencia en " + NOMBRE_TEATRO + ".");
        ventasCount++;
    }

    void eliminarVenta(Scanner sc) { /* sin cambios */ }
    void actualizarCliente(Scanner sc) { /* sin cambios */ }

    public static void main(String[] args) {
        Exp3_S8_Sebastian_Cardenas app = new Exp3_S8_Sebastian_Cardenas();
        app.iniciar();
    }
}
