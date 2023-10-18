public class Pagina {

    private boolean tieneReferencia;   
    private short contadorEnvejecimiento=0b00000000;
    private int desplazamiento;
    private int id;

    public Pagina (int pagina)
    {
        this.id = pagina;   
    } 

    public void setId(int id){
        this.id = id;
    }   

    public int getId(){
        return id;
    }
    
    public void setDesplazamiento(int desplazamiento){
        this.desplazamiento = desplazamiento;
    }
    
    public int getDesplazamiento(){
        return desplazamiento;  
    }
    public void setTieneReferencia(boolean tieneReferencia)
    {
        this.tieneReferencia = tieneReferencia;
    }   

    public boolean tieneReferencia()
    {
        return tieneReferencia;
    }  
    
    public void setContadorEnvejecimiento(short contadorEnvejecimiento)
    {
        this.contadorEnvejecimiento = contadorEnvejecimiento;
    }

    public short getContadorEnvejecimiento()
    {
        return this.contadorEnvejecimiento;
    }

}
