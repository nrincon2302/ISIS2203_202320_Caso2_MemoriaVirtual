import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Proceso extends Thread{
    int[] listaPaginas; 

    RAM ram; 
    
    CyclicBarrier barrier;


    public Proceso(BufferedReader archivo, RAM ram, CyclicBarrier barrier) throws IOException{
        this.ram = ram;
        this.barrier = barrier;

        String line = archivo.readLine();
        line = archivo.readLine();
        line = archivo.readLine();
        line = archivo.readLine();

        line = archivo.readLine();
        int numReferencias = Integer.parseInt(line.split("=")[1]);
        listaPaginas = new int[numReferencias];

        line = archivo.readLine();
        int numPaginasVirtuales = Integer.parseInt(line.split("=")[1]);
        ram.setTablaPaginas(numPaginasVirtuales);

        for(int i = 0; i<numReferencias; i++){
            line = archivo.readLine();
            listaPaginas[i] = Integer.parseInt(line.split(", ")[1]);
        }
    }
    
    @Override
    public void run(){
        try {
            for(int i=0; i<listaPaginas.length; i++){
                ram.getPagina(listaPaginas[i]);        
                Thread.sleep(2);
            }
            ram.terminar(); 
            barrier.await();
        }
        catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
    }
}