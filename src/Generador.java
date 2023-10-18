import java.io.FileWriter;
import java.io.IOException;


public class Generador {
    // Esta clase genera el archivo con los cálculos de cada posición en memoria virtual
    private int tamanioPagina;
    private int filasA;
    private int columnasFilasAB;
    private int columnasB;

    public Generador (int pTamPag, int pFilasA, int pColFilAB, int pColB) {
        this.tamanioPagina = pTamPag;
        this.filasA = pFilasA;
        this.columnasFilasAB = pColFilAB;
        this.columnasB = pColB;
    }

    public String generarEncabezado() {
        String encabezado = "";
        encabezado += "TP=" + Integer.toString(tamanioPagina);
        encabezado += "\nNF=" + Integer.toString(filasA);
        encabezado += "\nNC1=" + Integer.toString(columnasFilasAB);
        encabezado += "\nNC2=" + Integer.toString(columnasB);

        // Almacenar los operandos y el resultado (1+2*cAB) y repetir para cada posición resultante (tamC)
        int numeroRegistros = (1 + 2*columnasFilasAB) * (filasA * columnasB);
        encabezado += "\nNR=" + Integer.toString(numeroRegistros);

        // Numero de Páginas = (# total de elementos * tamaño de 1 entero) / tamaño de 1 página
        int numeroPaginas = 4*(filasA*columnasFilasAB + columnasFilasAB*columnasB + filasA*columnasB) / tamanioPagina;
        if (4*(filasA*columnasFilasAB + columnasFilasAB*columnasB + filasA*columnasB) % tamanioPagina != 0){
            numeroPaginas++;
        }
        encabezado += "\nNP=" + Integer.toString(numeroPaginas);

        return encabezado;
    }

    public String generarReferencias() {
        // Crear las matrices
        Matriz A = new Matriz(filasA, columnasFilasAB, tamanioPagina);
        Matriz B = new Matriz(columnasFilasAB, columnasB, tamanioPagina);
        Matriz C = new Matriz(filasA, columnasB, tamanioPagina);

        // Crear la cadena de respuesta
        String referencias = "";

        for (int i=0; i<filasA; i++) {
            for (int j=0; j<columnasB; j++) {
                for (int k=0; k<columnasFilasAB; k++) {
                    referencias += "\n[A-" + Integer.toString(i) + "-" +
                            Integer.toString(k) + "], " + A.darPaginaYDesplazamiento(i, k);
                    referencias += "\n[B-" + Integer.toString(k) + "-" +
                            Integer.toString(j) + "], " + B.darPaginaYDesplazamiento(k, j);
                }
                referencias += "\n[C-" + Integer.toString(i) + "-" +
                        Integer.toString(j) + "], " + C.darPaginaYDesplazamiento(i, j);
            }
        }

        return referencias;
    }

    public void escribirArchivo() throws IOException {
        FileWriter writer = new FileWriter("docs/referenciasModo1.txt");

        // Write data to the file.
        writer.write(generarEncabezado() + generarReferencias());

        // Close the FileWriter object.
        writer.close();
    }

}
