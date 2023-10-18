import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Reloj extends Thread {
    private RAM ram;
    private CyclicBarrier barrier;

    public Reloj (RAM ram, CyclicBarrier barrier){
        this.ram = ram;
        this.barrier = barrier;
    }

    @Override
    public void run(){
        try {
            while(ram.continuar()){
                ram.envejecer();
                Thread.sleep(1);   
            }
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
    
}
