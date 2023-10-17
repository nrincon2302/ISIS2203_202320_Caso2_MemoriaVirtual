import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CargadorMemoriaReal extends Thread {
    private int marcosPagina;
    private String archivo;
    private ArrayList<String> referencias;
    private ArrayList<Integer[]> posicionesDesplazamientos;
    private static HashMap<Integer, Integer> tablaPaginas;
    private ArrayList<Integer> marcosLibres;

    public CargadorMemoriaReal(int pMarcosPagina, String pArchivo, HashMap<Integer, Integer> tabla) {
        this.marcosPagina = pMarcosPagina;
        this.archivo = pArchivo;
        this.referencias = new ArrayList<>();
        this.posicionesDesplazamientos = new ArrayList<>();
        this.marcosLibres = new ArrayList<>();
        for (int i=0; i<marcosPagina; i++) {
            marcosLibres.add((Integer) i);
        }
        tablaPaginas = tabla;
        try {
            leerArchivo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void leerArchivo() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo)) ) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                // Procesar el archivo desde donde empiezan las referencias
                if (lineNumber > 6) {
                    // Llenar los arreglos con su dato respectivo
                    String[] parts = line.split(", ");
                    if (parts.length == 3) {
                        String detalle = parts[0];
                        Integer[] posDesp = new Integer[2];
                        posDesp[0] = Integer.parseInt(parts[1]);
                        posDesp[1] = Integer.parseInt(parts[2]);

                        referencias.add(detalle);
                        posicionesDesplazamientos.add(posDesp);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Algo ha fallado en la lectura del archivo. Intente de nuevo por favor");
        }
    }

    public boolean cargarPagina(int paginaVirtual) {
        Integer marcoReal = -1;

        if (tablaPaginas.containsValue(paginaVirtual)) {
            for (Map.Entry<Integer, Integer> entrada : tablaPaginas.entrySet()) {
                if (entrada.getValue() == paginaVirtual) {
                    marcoReal = entrada.getKey();
                }
            }
            System.out.println("\nLa Página Virtual " + paginaVirtual + " ya se encuentra guardada en" +
                    " el Marco de Página Real " + marcoReal);
            System.out.println("Marco de Página == Página Virtual -> " + tablaPaginas.entrySet());
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public void run() {
        for (Integer[] posDesp : posicionesDesplazamientos) {
            if (!cargarPagina(posDesp[0])) {
                if (!marcosLibres.isEmpty()) {
                    System.out.println("\nFALLO DE PÁGINA");
                    int marcoReal = marcosLibres.remove(0);
                    tablaPaginas.put(marcoReal, posDesp[0]);
                    System.out.println("La Página Virtual " + posDesp[0] + " ha sido cargada en" +
                            " el Marco de Página Real " + marcoReal);
                }
                else {
                    System.out.println("\nAcá viene el Algoritmo de Envejecimiento");
                    System.out.println("No se ha cargado la Página Virtual " + posDesp[0]);
                }
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
