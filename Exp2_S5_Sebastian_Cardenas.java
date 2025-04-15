import java.util.Scanner;

public class Exp2_S5_Sebastian_Cardenas {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
// DEFINIR VARIABLES DE INSTANCIA---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        int tipoEntrada, tipoTarifa, cantidadEntradas;
        double precioGeneral = 0, precioEstudiante = 0, precioFinal, precioTotal;
        int asientosVIP = 100, asientosPlateaBaja = 150, asientosPlateaAlta = 120, asientosPalcos = 80;
        String respuestaMenu = "si", respuestaCompra = "si";

        String[][] teatro = {
                {"A1", "A2", "A3", "A4", "A5"},
                {"B1", "B2", "B3", "B4", "B5"},
                {"C1", "C2", "C3", "C4", "C5"}
        };
// PRESENTACION----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        System.out.println("Bienvenido a Teatro Moro");

        do {
            System.out.println("\n Menú inicial");
            System.out.println("Elige una opción:");
            System.out.println("1. Menú Principal");
            System.out.println("2. Salir");
            int opcionInicial = sc.nextInt();

            if (opcionInicial == 2) {
                System.out.println("Gracias por visitarnos. ¡Hasta luego!");
                break;
            } else if (opcionInicial != 1) {
                System.out.println("Opción inválida, intente nuevamente.");
                continue;
            }
// MOSTRAR PROMOCIONES---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            System.out.println("\n¡Tenemos promociones para ti! ");
            System.out.println("1. Compra 2 entradas y obtén un 10% de descuento");
            System.out.println("2. Compra 3 entradas y obtén un 20% de descuento");
            System.out.println("3. Compra 4 o más entradas y obtén un 30% de descuento");

            System.out.print("\n¿Cuántas entradas deseas comprar? ");
            cantidadEntradas = sc.nextInt();
//DEFINIR VARIABLES ESTATICAS----------------------------------------------------------------------------------------------------------
            double descuentoCantidad = 0;
            if (cantidadEntradas == 2) {
                descuentoCantidad = 0.10;
            } else if (cantidadEntradas == 3) {
                descuentoCantidad = 0.20;
            } else if (cantidadEntradas >= 4) {
                descuentoCantidad = 0.30;
            }

            if (descuentoCantidad > 0) {
                System.out.println("¡Tienes un descuento del " + (descuentoCantidad * 100) + "%!");
            }
// CONSULTAR EDAD Y APLICAR DESCUENTO SEGUN----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            System.out.print("Por favor, introduce tu edad: ");
            int edad = sc.nextInt();
            double descuento = 0;

            if (edad >= 60) {
                descuento = 0.15;
                System.out.println("Se aplica un descuento del 15% para personas de la tercera edad.");
            } else {
                System.out.print("¿Eres estudiante? (sí/no): ");
                sc.nextLine();
                String esEstudiante = sc.nextLine();
                if (esEstudiante.equalsIgnoreCase("si")) {
                    descuento = 0.10;
                    System.out.println("Se aplica un descuento del 10% para estudiantes.");
                }
            }

            if (descuento == 0) {
                System.out.println("No se aplica ningún descuento.");
            } else {
                System.out.println("¡Tu descuento se ha aplicado exitosamente!");
            }

            int totalEntradas = 0;
            double montoTotalPagado = 0;
            String asientoElegido;

            do {
                double precioBaseTotal;


// ENSEÑAR TIPOS DE ENTRADA A VENDER----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                do {
                    System.out.println("Elige un tipo de entrada:");
                    System.out.println("1. Entrada VIP - $30.000 (Estudiante: $20.000) - Asientos disponibles: " + asientosVIP);
                    System.out.println("2. Entrada Platea Baja - $15.000 (Estudiante: $10.000) - Asientos disponibles: " + asientosPlateaBaja);
                    System.out.println("3. Entrada Platea Alta - $18.000 (Estudiante: $9.000) - Asientos disponibles: " + asientosPlateaAlta);
                    System.out.println("4. Entrada Palcos - $13.000 (Estudiante: $6.500) - Asientos disponibles: " + asientosPalcos);
                    tipoEntrada = sc.nextInt();

                    if (tipoEntrada < 1 || tipoEntrada > 4)
                        System.out.println("Opción inválida, intente nuevamente.");
                } while (tipoEntrada < 1 || tipoEntrada > 4);

                switch (tipoEntrada) {
                    case 1 -> { precioGeneral = 30000; precioEstudiante = 20000; }
                    case 2 -> { precioGeneral = 15000; precioEstudiante = 7500; }
                    case 3 -> { precioGeneral = 18000; precioEstudiante = 9000; }
                    case 4 -> { precioGeneral = 13000; precioEstudiante = 6500; }
                }

                do {
                    System.out.println("Seleccione el tipo de tarifa:");
                    System.out.println("1. Público General");
                    System.out.println("2. Estudiante");
                    tipoTarifa = sc.nextInt();

                    if (tipoTarifa < 1 || tipoTarifa > 2)
                        System.out.println("Opción inválida, intente nuevamente.");
                } while (tipoTarifa < 1 || tipoTarifa > 2);

                precioFinal = (tipoTarifa == 1) ? precioGeneral : precioEstudiante;

                if (descuento > 0) {
                    precioFinal -= (precioFinal * descuento);
                }
// PREGUNTAR CANTIDAD DE ENTRADAS----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                do {
                    System.out.println("Ingrese la cantidad de entradas que desea comprar:");
                    cantidadEntradas = sc.nextInt();

                    if (cantidadEntradas <= 0)
                        System.out.println("Cantidad inválida, debe ser mayor que cero. Intente nuevamente.");

                } while (cantidadEntradas <= 0);
// PLANO DE ASIENTOS----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                System.out.println("\nPlano del teatro (Asientos disponibles):");
                for (String[] zona : teatro) {
                    for (String asiento : zona) {
                        System.out.print("[" + asiento + "] ");
                    }
                    System.out.println();
                }

                System.out.println("Seleccione su asiento específico (Ejemplo: A1, B3, C5):");
                asientoElegido = sc.next().toUpperCase();

                boolean asientoValido = false;
                for (String[] zona : teatro) {
                    for (int i = 0; i < zona.length; i++) {
                        if (zona[i].equals(asientoElegido)) {
                            zona[i] = "XX";
                            asientoValido = true;
                        }
                    }
                }

                if (!asientoValido) {
                    System.out.println("Asiento inválido o ya ocupado, por favor intente nuevamente.");
                    continue;
                }

                precioBaseTotal = ((tipoTarifa == 1) ? precioGeneral : precioEstudiante) * cantidadEntradas;
                precioTotal = precioFinal * cantidadEntradas;
                double descuentoAplicado = precioBaseTotal - precioTotal;

                totalEntradas += cantidadEntradas;
                montoTotalPagado += precioTotal;

// MOSTAR RESUMEN DE COMPRA----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                System.out.println("\nResumen de la compra:");
                System.out.println("------------------------------");
                System.out.println("Cantidad de entradas: " + cantidadEntradas);
                System.out.println("Asiento seleccionado: " + asientoElegido);
                System.out.println("Precio base total (sin descuento): $" + (int)precioBaseTotal);
                System.out.println("Descuento aplicado: $" + (int)descuentoAplicado);
                System.out.println("Total a pagar: $" + (int)precioTotal);
                System.out.println("------------------------------\n");

// PREGUNTAR SI DESEA MAS ENTRADAS---------------------------------------------------------------------------------------------------------------------------------------------------------
                System.out.println("¿Deseas eliminar alguna entrada? (si/no)");
                String eliminarEntradas = sc.next().toLowerCase();

                if (eliminarEntradas.equals("si")) {
                    System.out.println("¿Cuántas entradas deseas eliminar?");
                    int entradasAEliminar = sc.nextInt();

                    if (entradasAEliminar > totalEntradas) {
                        System.out.println("No puedes eliminar más entradas de las que has comprado.");
                    } else {
                        totalEntradas -= entradasAEliminar;
                        montoTotalPagado -= (precioTotal * entradasAEliminar / cantidadEntradas);
                        System.out.println("Entradas eliminadas exitosamente.");
                    }
                }




                System.out.println("Desea realizar otra compra? (si/no)");
                respuestaCompra = sc.next().toLowerCase();

            } while (respuestaCompra.equals("si"));
// RESUMEN DEFINITIVO----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
            System.out.println("------------------------------");
            System.out.println("Resumen total de su compra:");
            System.out.println("Ubicacion del asiento: "+asientoElegido);
            System.out.println("Cantidad total de entradas: " + totalEntradas);
            System.out.println("Total pagado: $" + (int)montoTotalPagado);
            System.out.println("------------------------------");

            System.out.println("\n¿Desea volver al menú inicial? (si/no)");
            respuestaMenu = sc.next().toLowerCase();

        } while (respuestaMenu.equals("si"));

        sc.close();
    }
}