import java.util.Scanner;

public class Exp1_S3_Sebastian_Cardenas {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
//INICIO_DEFINICION DE VARIABLES____________________________________________________________________________________________________________________
        
        int tipoEntrada, tipoTarifa, cantidadEntradas, metodoPago;
        double precioGeneral = 0, precioEstudiante = 0, precioFinal, precioTotal;
        int asientosVIP = 100, asientosPlateaBaja = 150, asientosPlateaAlta = 120, asientosPalcos = 80;
        String respuestaMenu, respuestaCompra = null,nombreEntrada, nombreTarifa;
//PRESENTACION______________________________________________________________________________________________________________________________________
        do {
            System.out.println("Bienvenido a Teatro Moro");
            precioTotal = 0;
            do {
              do {
                System.out.println("Elige un tipo de entrada:");
                System.out.println("1. Entrada VIP - $30.000 (Estudiante: $20.000) - Asientos disponibles: " + asientosVIP);
                System.out.println("2. Entrada Platea Baja - $15.000 (Estudiante: $10.000) - Asientos disponibles: " + asientosPlateaBaja);
                System.out.println("3. Entrada Platea Alta - $18.000 (Estudiante: $9.000) - Asientos disponibles: " + asientosPlateaAlta);
                System.out.println("4. Entrada Palcos - $13.000 (Estudiante: $6.500) - Asientos disponibles: " + asientosPalcos);

                tipoEntrada = sc.nextInt();
//PROCESO_________________________________________________________________________________________________________________________________________________
                if (tipoEntrada < 1 || tipoEntrada > 4) {
                    System.out.println("Opcion invalida, intente nuevamente.");
                }
            } while (tipoEntrada < 1 || tipoEntrada > 4);

            switch (tipoEntrada) {
                case 1 -> { precioGeneral = 30000; precioEstudiante = 20000; }
                case 2 -> { precioGeneral = 15000; precioEstudiante = precioGeneral * 0.50;}
                case 3 -> { precioGeneral = 18000; precioEstudiante = 9000; }
                case 4 -> { precioGeneral = 13000; precioEstudiante = precioGeneral * 0.50;}
            }
//CONSULTAR TARIFAS________________________________________________________________________________________________________________________________
            do {
                System.out.println("Seleccione el tipo de tarifa:");
                System.out.println("1. Publico General");
                System.out.println("2. Estudiante");
                tipoTarifa = sc.nextInt();

                if (tipoTarifa < 1 || tipoTarifa > 2) {
                    System.out.println("Opcion invalida, intente nuevamente.");
                }
            } while (tipoTarifa < 1 || tipoTarifa > 2);

            precioFinal = (tipoTarifa == 1) ? precioGeneral : precioEstudiante;

//CONSULTAR CANTIDAD DE ENTRADAS___________________________________________________________________________________________________________________            
            
            System.out.println("El precio de su entrada es: $" + (int)precioFinal);
            System.out.println("Ingrese la cantidad de entradas que desea comprar:");
            cantidadEntradas = sc.nextInt();

            boolean disponibilidad = switch (tipoEntrada) {
                case 1 -> cantidadEntradas <= asientosVIP;
                case 2 -> cantidadEntradas <= asientosPlateaBaja;
                case 3 -> cantidadEntradas <= asientosPlateaAlta;
                case 4 -> cantidadEntradas <= asientosPalcos;
                default -> false;
            };

//VOLVER A INTENTAR SI NO HAY STOCK________________________________________________________________________________________________________________            
            
            if (!disponibilidad) {
                System.out.println("No hay suficientes asientos disponibles.");
                continue;
            }

            switch (tipoEntrada) {
                case 1 -> asientosVIP -= cantidadEntradas;
                case 2 -> asientosPlateaBaja -= cantidadEntradas;
                case 3 -> asientosPlateaAlta -= cantidadEntradas;
                case 4 -> asientosPalcos -= cantidadEntradas;
            }

            precioTotal = precioFinal * cantidadEntradas;
            
//RESUMEN DE COMPRA______________________________________________________________________________________________________________________

            System.out.println("Desea realizar otra compra? (si/no)");
            respuestaCompra = sc.next().toLowerCase();
            
        } while (respuestaCompra.equals("si"));


            System.out.println("------------------------------");
            System.out.println("Resumen de su compra:");
            System.out.println("Cantidad de entradas: " + cantidadEntradas);         
            System.out.println("Precio unitario: $" + (int)precioFinal);
            System.out.println("Total a pagar: $" + (int)precioTotal);
            System.out.println("------------------------------");

//METODO DE PAGO______________________________________________________________________________________________________________________________            
            
            System.out.println("Indique metodo de pago: (1) Debito o (2) Efectivo");
            metodoPago = sc.nextInt();

            if (metodoPago == 1) {
                System.out.println("Coloque su tarjeta en el lector");
                System.out.println("Procesando pago...");
                System.out.println("Pago realizado con exito.");
            } else if (metodoPago == 2) {
                System.out.println("Ingrese el efectivo...");
                System.out.println("Esta maquina no genera vuelto");
            } else {
                System.out.println("Metodo invalido.");
            }

            System.out.println("Gracias por su compra, disfrute la funcion.");

            System.out.println("Desea volver al menu principal? (si/no)");
            respuestaMenu = sc.next().toLowerCase();

        } while (respuestaMenu.equals("si"));

        sc.close();
    }
}

