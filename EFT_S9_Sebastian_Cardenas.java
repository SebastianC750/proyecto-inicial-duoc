package eft_s9_sebastian_cardenas;

//IMPORTACION DE LIBRERIAS A UTILIZAR======================================================================================
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//CLASE PRINCIPAL DEL PROGRAMA======================================================================================
public class EFT_S9_Sebastian_Cardenas {

    // VARIABLES ESTÁTICAS==============================================================================
    static final String NOMBRE_TEATRO = "Teatro Moro";
    static final double PRECIO_VIP = 29900;
    static final double PRECIO_PALCO = 19900;
    static final double PRECIO_PLATEA_BAJA = 14990;
    static final double PRECIO_PLATEA_ALTA = 17980;
    static final double PRECIO_GALERIA = 9990;

    // Estructuras para almacenar datos de clientes y ventas ARRAY LIST==================================
    static List<String> rutClientes = new ArrayList<>();
    static List<String> nombresClientes = new ArrayList<>();
    static List<String> contactosClientes = new ArrayList<>();
    static List<Integer> edadesClientes = new ArrayList<>();
    static List<String> generosClientes = new ArrayList<>();
    static List<String> ventasZonas = new ArrayList<>();
    static List<List<String>> ventasAsientos = new ArrayList<>();
    static List<Double> listaVentasTotales = new ArrayList<>();
    static List<String> ventaIds = new ArrayList<>();

    static String[][] asientos = new String[10][10];

    //metodo main======================================================================================
    
    public static void main(String[] args) {
        inicializarAsientos();
        iniciar();
    }
    //inicio de asientos ======================================================================================
    static void inicializarAsientos() {
        for (int i = 0; i < 10; i++) {
            char fila = (char) ('A' + i);
            for (int j = 0; j < 10; j++) {
                asientos[i][j] = fila + String.valueOf(j + 1);
            }
        }
    }
    //menu principal======================================================================================
    static void iniciar() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- " + NOMBRE_TEATRO + " --- Menu Principal ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Mostrar plano de asientos");
            System.out.println("3. Mostrar listado de ventas");
            System.out.println("4. Modificar venta");
            System.out.println("5. Salir");
            int opcion = leerEntero("Seleccione opcion: ", sc);

            switch (opcion) {
                case 1: venderEntrada(sc); break;
                case 2: mostrarPlanoAsientos(); break;
                case 3: mostrarVentas(); break;
                case 4:
                    if (ventaIds.isEmpty()) {
                        System.out.println("No existen ventas");
                    } else {
                        modificarVenta(sc);
                    }
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema. Hasta pronto.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
//mostrar plano de asientos
    static void mostrarPlanoAsientos() {
        String[] zonas = {"VIP", "PALCO", "PLATEA BAJA", "PLATEA ALTA", "GALERÍA"};
        int[][] rangos = {{0, 1}, {2, 3}, {4, 5}, {6, 7}, {8, 9}};

        for (int z = 0; z < zonas.length; z++) {
            System.out.println("\n==================== SECTOR " + zonas[z] + " ====================");
            for (int i = rangos[z][0]; i <= rangos[z][1]; i++) {
                System.out.printf("%-8s", "Fila " + (char)('A' + i));
                for (String seat : asientos[i]) System.out.printf("%4s", seat);
                System.out.println();
            }
        }
    }
//leer numeros de entrada======================================================================================
    static int leerEntero(String mensaje, Scanner sc) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine();
            boolean esNumero = true;
            for (int i = 0; i < linea.length(); i++) {
                char c = linea.charAt(i);
                if (c < '0' || c > '9') {
                    esNumero = false;
                    break;
                }
            }
            if (esNumero && linea.length() > 0) {
                int numero = 0;
                for (int i = 0; i < linea.length(); i++) {
                    numero = numero * 10 + (linea.charAt(i) - '0');
                }
                return numero;
            } else {
                System.out.println("Por favor ingresa un número válido.");
            }
        }
    }
//proceso de ventas===================================================

    static void venderEntrada(Scanner sc) {
        System.out.println("\n-- Venta de Entrada --");
        System.out.print("Ingrese su RUT: ");
        String rut = sc.nextLine();

        if (rutClientes.contains(rut)) {
            System.out.println("Este RUT ya está registrado.");
            return;
        }

        System.out.print("Ingrese su nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese su numero de contacto: ");
        String contacto = sc.nextLine();

        int edad = leerEntero("Ingrese su edad: ", sc);
//validacion ===================================================
        boolean estudiante = false;
        while (true) {
            System.out.print("Es estudiante? (si/no): ");
            String r = sc.nextLine().toLowerCase();
            if (r.equals("si")) { estudiante = true; break; }
            if (r.equals("no")) break;
            System.out.println("Respuesta inválida.");
        }
//validacion===================================================
        String genero = "";
        while (true) {
            System.out.print("Ingrese su genero (m/f): ");
            genero = sc.nextLine().toLowerCase();
            if (genero.equals("m") || genero.equals("f")) break;
            System.out.println("Género inválido.");
        }

        mostrarPlanoAsientos();
        System.out.println("Zonas disponibles: vip, palco, platea baja, platea alta, galeria");
        System.out.print("Seleccione zona: ");
        String zona = sc.nextLine().toLowerCase();

        int cantidad = leerEntero("Cantidad de entradas: ", sc);

        List<String> asientosAsignados = new ArrayList<>();
        int inicio = 0, fin = 0;
        if (zona.equals("vip")) { inicio = 0; fin = 1; }
        else if (zona.equals("palco")) { inicio = 2; fin = 3; }
        else if (zona.equals("platea baja")) { inicio = 4; fin = 5; }
        else if (zona.equals("platea alta")) { inicio = 6; fin = 7; }
        else if (zona.equals("galeria")) { inicio = 8; fin = 9; }

        for (int i = 0; i < cantidad; i++) {
            while (true) {
                System.out.print("Ingrese asiento (ej. A1, B2, C3, D4, Etc...): ");
                String asiento = sc.nextLine().toUpperCase();
                boolean asignado = false;
                for (int f = inicio; f <= fin; f++) {
                    for (int c = 0; c < 10; c++) {
                        if (asientos[f][c].equals(asiento)) {
                            asientos[f][c] = "XX";
                            asientosAsignados.add(asiento);
                            asignado = true;
                            break;
                        }
                    }
                    if (asignado) break;
                }
                if (asignado) break;
                System.out.println("Asiento inválido o ya ocupado.");
            }
        }

        
        //calculo de precio===================================================
        double precio = 0;
        if (zona.equals("vip")) precio = PRECIO_VIP;
        else if (zona.equals("palco")) precio = PRECIO_PALCO;
        else if (zona.equals("platea baja")) precio = PRECIO_PLATEA_BAJA;
        else if (zona.equals("platea alta")) precio = PRECIO_PLATEA_ALTA;
        else if (zona.equals("galeria")) precio = PRECIO_GALERIA;

        double descuento = calcularDescuento(edad, genero, estudiante);
        double totalPagar = precio * cantidad * (1 - descuento);

        //registro de venta===================================================
        rutClientes.add(rut);
        nombresClientes.add(nombre);
        contactosClientes.add(contacto);
        edadesClientes.add(edad);
        generosClientes.add(genero);
        ventasZonas.add(zona);
        ventasAsientos.add(asientosAsignados);
        listaVentasTotales.add(totalPagar);
        String id = "VENTA" + (ventaIds.size() + 1);
        ventaIds.add(id);

        // resumen de venta===================================================
        System.out.println("\nResumen de venta:");
        System.out.println("ID Venta: " + id);
        System.out.println("Cliente: " + nombre);
        System.out.println("Zona: " + zona);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Asientos: " + asientosAsignados);
        System.out.println("Descuento aplicado: " + (int)(descuento * 100) + "%");
        System.out.printf("Total a pagar: $%.0f ", totalPagar);
    }
 
    
    //calculo de descuento===================================================
    static double calcularDescuento(int edad, String genero, boolean estudiante) {
        if (edad < 12) return 0.10;
        if (edad >= 60) return 0.25;
        if (estudiante) return 0.15;
        if (genero.equals("f")) return 0.20;
        return 0;
    }
// mostrar ventas opcion ===================================================
    static void mostrarVentas() {
        System.out.println("\nListado de Ventas:");
        for (int i = 0; i < ventaIds.size(); i++) {
            System.out.printf("ID: %s | Cliente: %s | Zona: %s | Total: $%.0f\n",
                    ventaIds.get(i), nombresClientes.get(i), ventasZonas.get(i), listaVentasTotales.get(i));
        }
    }
//modificar ventas opcion===================================================
    static void modificarVenta(Scanner sc) {
        System.out.print("Ingrese ID de venta a modificar: ");
        String id = sc.nextLine();
        int idx = ventaIds.indexOf(id);
        if (idx == -1) {
            System.out.println("Venta no encontrada.");
            return;
        }

        System.out.println("Modificando datos de " + nombresClientes.get(idx));

        System.out.print("Nuevo nombre (enter para mantener): ");
        String nuevoNombre = sc.nextLine();
        if (!nuevoNombre.isEmpty()) nombresClientes.set(idx, nuevoNombre);

        System.out.print("Nuevo contacto (enter para mantener): ");
        String nuevoContacto = sc.nextLine();
        if (!nuevoContacto.isEmpty()) contactosClientes.set(idx, nuevoContacto);

        System.out.println("Datos actualizados correctamente.");
    }
}
