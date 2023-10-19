public class RAM{

    int[] tablaPaginas;
    int[] tablaPaginasInv;
    int[] contadorMarcosPaginas;
    int[] marcosPaginaReferenciados;
    int fallosPagina = 0;
    boolean continuar = true;

    public RAM(int numMarcosPaginas){
        tablaPaginasInv = new int[numMarcosPaginas];
        contadorMarcosPaginas = new int[numMarcosPaginas];
        marcosPaginaReferenciados = new int[numMarcosPaginas];
        
        for(int i=0; i<numMarcosPaginas; i++){
            tablaPaginasInv[i]=-1;
            contadorMarcosPaginas[i]=0;
            marcosPaginaReferenciados[i]=0;
        }
    }

    public synchronized void setTablaPaginas(int numPaginasVirtuales) {
        tablaPaginas = new int[numPaginasVirtuales];
        for(int i=0; i<numPaginasVirtuales; i++){
            tablaPaginas[i] = -1;
        }
    }

    public synchronized void getPagina(int numPaginaVirtual) {
        int direccionMarcoPagina = tablaPaginas[numPaginaVirtual];

        //El marcoDePagina no está en la RAM
        if(direccionMarcoPagina == -1){
            fallosPagina ++;
            int marcoMenosUsado = getMarcoMenosUsado();
            tablaPaginas[numPaginaVirtual] = marcoMenosUsado;
            
            int paginaVirtualBorrada = tablaPaginasInv[marcoMenosUsado];
            if(paginaVirtualBorrada != -1){
                tablaPaginas[paginaVirtualBorrada] = -1;
            }
            tablaPaginasInv[marcoMenosUsado] = numPaginaVirtual;  
            
            contadorMarcosPaginas[marcoMenosUsado] = 0b01000000000000000000000000000000; 
        }
        //El marcoDePagina está en la RAM2
        else{
            marcosPaginaReferenciados[direccionMarcoPagina] = 1;
        }      
    }

    private int getMarcoMenosUsado() {
        int indexMenosUsado = 0;
        int valMenosUsado = contadorMarcosPaginas[0];
        for (int i=1; i<contadorMarcosPaginas.length; i++){
            if(contadorMarcosPaginas[i]<valMenosUsado){
                valMenosUsado = contadorMarcosPaginas[i];
                indexMenosUsado=i;
            }
        }
        return indexMenosUsado;
    }

    public synchronized int getNumFallosPagina() {
        return fallosPagina;
    }

    public synchronized boolean continuar() {
        return continuar;
    }
    public synchronized void terminar(){
        continuar = false;
    }

    public synchronized void envejecer() {
        for (int i=0; i<marcosPaginaReferenciados.length; i++){
            contadorMarcosPaginas[i] >>=1;
            if(marcosPaginaReferenciados[i] == 1){
                contadorMarcosPaginas[i] = contadorMarcosPaginas[i] | 0b01000000000000000000000000000000;
            }
            marcosPaginaReferenciados[i] = 0;   
        }
    }
}