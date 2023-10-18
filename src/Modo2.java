public class Modo2 {

    private boolean seActualizo;
    private boolean terminado1;
    private Pagina[] memoriaReal;
    private int paginaCambio;

    public Modo2(){
        this.seActualizo = false;
        this.terminado1 = false;
        this.memoriaReal = new Pagina[0];
        this.paginaCambio = 0;
    }

    public void setTermino1(boolean termino)
    {
        this.terminado1 = termino;
    }   

    public boolean getTerminado1()
    {
        return this.terminado1;
    }
    
    public void setSeActualizo(boolean seActualizo)
    {
        this.seActualizo = seActualizo;
    }

    public boolean getSeActualizo()
    {
        return this.seActualizo;
    }

    public void algortimoEnvejecimiento() throws InterruptedException
    {
        
            while(memoriaReal[0]==null)
            {
                System.out.println("Esperando");
            }

            for(int i=0; i<memoriaReal.length; i++)
            {
                if(memoriaReal[i] != null)
                {
                    sumarAlEnvejecimiento(memoriaReal[i], memoriaReal[i].tieneReferencia());
                    memoriaReal[i].setTieneReferencia(false);
                }
            }

                //Espera a que el otro Thread haya llenado toda la memoriaReal
                int posicion = buscarElMasViejo();
                paginaCambio = posicion;   
    }

    public int buscarElMasViejo()
    {
    
        int posicion=0;
        short menor = memoriaReal[0].getContadorEnvejecimiento();

 
        for (int i = 1; i < memoriaReal.length; i++) {
            if (memoriaReal[i] != null && memoriaReal[i].getContadorEnvejecimiento() < menor) {
                menor = memoriaReal[i].getContadorEnvejecimiento();
                posicion = i;
            }
        }
        
        return posicion;
        
    }

    public void sumarAlEnvejecimiento(Pagina pagina, boolean esReferenciada)
    {
        short contadorEnvejecimiento = pagina.getContadorEnvejecimiento();
        contadorEnvejecimiento = (short) (contadorEnvejecimiento >> 1);
        if(esReferenciada)
        {
            contadorEnvejecimiento = (short) ((contadorEnvejecimiento | (1<<7)));
            
        }
        pagina.setContadorEnvejecimiento(contadorEnvejecimiento);
    }

    
}
