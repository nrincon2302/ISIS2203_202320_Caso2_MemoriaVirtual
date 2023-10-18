import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Proceso extends Thread {
    private BufferedReader archivo;
    CyclicBarrier barrier;
    private RAM ram;
    private ArrayList<Integer> secuenciaPaginas = new ArrayList<>();
    

    public Proceso( BufferedReader pArchivo, RAM ram, CyclicBarrier barrier) {
        this.barrier = barrier;
        this.archivo = pArchivo;
        this.ram = ram;
        HashMap<Integer,Integer> tablaPaginas = new HashMap<Integer,Integer>();
        
        try{
            String line = archivo.readLine();
            for(int i = 0; i<6; i++){
                line = archivo.readLine();
            }
            while(line != null){
                int numPagina = Integer.parseInt(line.split(", ")[1]); 
                tablaPaginas.put(numPagina,-1);
                secuenciaPaginas.add(numPagina);
                line = archivo.readLine();
            }

            ram.setTablaPaginas(tablaPaginas);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            for(Integer paginaVirtual : secuenciaPaginas){
                    ram.getPagina(paginaVirtual);
                    Thread.sleep(2);
            }
            ram.notContinuar();
            barrier.await();
        }
        catch(InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }


}
