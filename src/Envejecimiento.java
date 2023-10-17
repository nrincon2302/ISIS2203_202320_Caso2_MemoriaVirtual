public class Envejecimiento extends Thread{

    private Modo2 modo;

    public Envejecimiento(Modo2 modo) {
        this.modo = modo;
    }

    public void run()
    {
        try {
            sleep(1);
            synchronized (modo) {
            while(!modo.getTerminado1())
            {
                
                while(!modo.getSeActualizo())
                {
                    modo.wait();
                }
                modo.algortimoEnvejecimiento();
            
                    modo.setSeActualizo(false);
                    modo.notifyAll();
                
            }
        }
            System.out.println("Terminado T2");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    
}
