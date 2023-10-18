public class Matriz {
    private int filas;
    private int columnas;
    private String[][] M;
    private static int tamanioPagina;
    private static int desplazamiento = 0;
    private static int numPaginaActual = 0;

    public Matriz(int pFilas, int pColumnas, int tamPagina) {
        this.filas = pFilas;
        this.columnas = pColumnas;
        tamanioPagina = tamPagina;
        M = new String[filas][columnas];
        llenarMatriz();
    }

    public void llenarMatriz() {
        for (int i=0; i<filas; i++) {
            for (int j=0; j<columnas; j++) {
                M[i][j] = Integer.toString(numPaginaActual) + ", " + Integer.toString(desplazamiento);
                desplazamiento += 4;
                if (desplazamiento >= tamanioPagina) {
                    numPaginaActual ++;
                    desplazamiento = 0;
                }
            }
        }
    }

    public String darPaginaYDesplazamiento(int i, int j) {
        return M[i][j];
    }
}
