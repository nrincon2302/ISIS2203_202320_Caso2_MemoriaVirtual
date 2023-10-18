import java.util.HashMap;
import java.util.Stack;

public class RAM {
    
    private HashMap<Integer,Integer> tablaPaginas;
    private int[] tablaPaginasInv;
    private Stack<Integer> registrosVacios = new Stack<>();
    private int[] registrosRam;
    private int[] registrosRecientementeLeidos;

    int numFallosPagina = 0;
    boolean continuar = true;

    public RAM(int numMarcosPagina){
        registrosRam = new int[numMarcosPagina];
        registrosRecientementeLeidos = new int[numMarcosPagina];
        tablaPaginasInv = new int[numMarcosPagina];

        for (int i = 0; i<numMarcosPagina; i++){
            registrosRam[i] = 0b00000000000000000000000000000000;
            registrosRecientementeLeidos[i] = 0;
            registrosVacios.push(i);
            tablaPaginasInv[i]=-1;
        }
    }

    public synchronized void setTablaPaginas(HashMap<Integer,Integer> tablaPaginas){
        this.tablaPaginas = tablaPaginas;
    }

    public synchronized void getPagina(int paginaVirtual){
        int paginaVirtual2PaginaReal = tablaPaginas.get(paginaVirtual);
        
        if (paginaVirtual2PaginaReal == -1){
            numFallosPagina++;  

            int paginaReemplazoReal;
            if(!registrosVacios.isEmpty()){
                paginaReemplazoReal = registrosVacios.pop();
            }
            else{
                paginaReemplazoReal = getMasViejo();
            }

            if (tablaPaginasInv[paginaReemplazoReal] != -1){
                tablaPaginas.put(tablaPaginasInv[paginaReemplazoReal],-1);
            }
            tablaPaginas.put(paginaVirtual,paginaReemplazoReal);
            tablaPaginasInv[paginaReemplazoReal] = paginaVirtual;

            registrosRecientementeLeidos[paginaReemplazoReal] = 1;
        }
        else{
            registrosRecientementeLeidos[paginaVirtual2PaginaReal] = 1;
        }
    }

    private int getMasViejo(){
        int minVal = registrosRam[0];
        int indexMinVal = 0;
        for(int i = 0; i<registrosRam.length; i++){
            if(registrosRam[i]<=minVal){
                minVal=registrosRam[i];
                indexMinVal = i;
            }
        }
        return indexMinVal;
    }

    public synchronized boolean continuar(){
        return continuar;
    }

    public synchronized void notContinuar(){
        continuar = false;
    }

    public synchronized void envejecer(){
        for(int i = 0; i<registrosRecientementeLeidos.length; i++){
            registrosRam[i] = registrosRam[i] >> 1; 
            if(registrosRecientementeLeidos[i] == 1){
                registrosRam[i] = registrosRam[i] | 0b01000000000000000000000000000000;
            }
            registrosRam[i] = 0;
        }
    }

    public synchronized int getNumFallosPagina(){
        return numFallosPagina;
    }

}
