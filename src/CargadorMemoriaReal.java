import javax.sound.midi.SysexMessage;
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
    private static HashMap<Integer, Pagina> tablaPaginas;
    private static Pagina paginaActual;
    private ArrayList<Integer> marcosLibres;
    private int fallosPagina;

    public CargadorMemoriaReal(int pMarcosPagina, String pArchivo, Pagina pagina) {
        this.marcosPagina = pMarcosPagina;
        this.archivo = pArchivo;
        this.referencias = new ArrayList<>();
        this.posicionesDesplazamientos = new ArrayList<>();
        this.marcosLibres = new ArrayList<>();
        this.fallosPagina = 0;
        for (int i=0; i<marcosPagina; i++) {
            marcosLibres.add((Integer) i);
        }
        tablaPaginas = new HashMap<>();
        paginaActual = pagina;
        try {
            leerArchivo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<Integer, Pagina> darTP() {
        return tablaPaginas;
    }

    public int darFallosPagina() {
        return fallosPagina;
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

    public boolean cargarPagina(Pagina paginaVirtual) {
        Integer marcoReal = -1;

        if (tablaPaginas.containsValue(paginaVirtual)) {
            for (Map.Entry<Integer, Pagina> entrada : tablaPaginas.entrySet()) {
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

    public void esperarSobreescritura() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Integer[] posDesp : posicionesDesplazamientos) {
            if (!cargarPagina(new Pagina(posDesp[0]))) {
                paginaActual = new Pagina(posDesp[0]);
                if (!marcosLibres.isEmpty()) {
                    fallosPagina ++;
                    System.out.println("\nFALLO DE PÁGINA");
                    int marcoReal = marcosLibres.remove(0);
                    tablaPaginas.put(marcoReal, new Pagina(posDesp[0]));
                    System.out.println("La Página Virtual " + posDesp[0] + " ha sido cargada en" +
                            " el Marco de Página Real " + marcoReal);
                }
                else {
                    fallosPagina ++;
                    System.out.println("\nFALLO DE PÁGINA: Memoria Llena. Se ha sobreescrito.");
                    synchronized (this) {
                        this.notify();
                    }
                    esperarSobreescritura();
                }
            }
        }

        System.out.println("Se han generado " + darFallosPagina() + " fallos de página.");
    }


}
