import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SobreescritorMemoriaReal extends Thread {
    private static Pagina paginaActual;
    private static HashMap<Integer, Pagina> tablaPaginas;

    public SobreescritorMemoriaReal(Pagina actual, HashMap<Integer, Pagina> pTablaPaginas) {
        paginaActual = actual;
        tablaPaginas = pTablaPaginas;
    }

    public void sobreescribirMemoria(Pagina paginaNueva) {
        int minimo = tablaPaginas.get(0).darContador();
        int minKey = 0;
        for (Pagina pag : tablaPaginas.values()) {
            int contador = pag.darContador();
            if (contador < minimo) {
                minimo = contador;
                int llave = 0;
                for (Map.Entry<Integer, Pagina> entrada : tablaPaginas.entrySet()) {
                    if (entrada.getValue() == pag) {
                        llave = entrada.getKey();
                    }
                }
                minKey = llave;
            }
        }
        // Reemplazar la página en la memoria
        tablaPaginas.replace(minKey, paginaNueva);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sobreescribirMemoria(paginaActual);
    }
}
