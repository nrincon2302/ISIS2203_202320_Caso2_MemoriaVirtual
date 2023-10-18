import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static String input(String mensaje) {
        /// Método input que puede usarse análogo al de Python
        try {
            System.out.print(mensaje + ": ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        }
        catch (IOException e) {
            System.out.println("Error leyendo de la consola");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Bienvenid@ al sistema para simular paginación en memoria virtual!\n");
        boolean continuar = true;

        while (continuar) {
            System.out.println("Opciones Disponibles:");
            System.out.println("1. Generar las referencias");
            System.out.println("2. Calcular el número de fallos de página");
            System.out.println("3. Salir de la aplicación");

            try {
                int opcion = Integer.parseInt(input("Ingrese la opción que desee ejecutar"));

                if (opcion == 1) {
                    System.out.println("\nHa seleccionado el Modo 1: Generar el archivo de referencias");
                    int tamPag = Integer.parseInt(input("Ingrese el tamaño de página de memoria virtual"));
                    int filasA = Integer.parseInt(input("Ingrese el número de filas de A"));
                    int colFilAB = Integer.parseInt(input("Ingrese el número de columnas de A (y filas de B)"));
                    int colB = Integer.parseInt(input("Ingrese el número de columnas de B"));
                    System.out.println("\nGenerando archivo...\n");
                    Generador g = new Generador(tamPag, filasA, colFilAB, colB);
                    g.escribirArchivo();
                    System.out.println("Se ha generado el archivo 'docs/referenciasModo1.txt'\n");
                } else if (opcion == 2) {
                    int marcosPag = Integer.parseInt(input("Ingrese el número de marcos de página"));
                    String nombreArchivo = input("Ingrese el nombre del archivo de referencias");
                    Pagina pag = new Pagina(0);
                    CargadorMemoriaReal cargador = new CargadorMemoriaReal(marcosPag, nombreArchivo, pag);
                    SobreescritorMemoriaReal sobreescritor = new SobreescritorMemoriaReal(pag, cargador.darTP());
                    cargador.start();
                    sobreescritor.start();
                } else if (opcion == 3) {
                    continuar = false;
                } else {
                    System.out.println("\nNo ha ingresado una opción válida. Intente de nuevo por favor.");
                }
            } catch (Exception e) {
                System.out.println("\nNo ha ingresado una opción válida. Intente de nuevo por favor.");
            }
        }
    }
}