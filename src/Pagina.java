public class Pagina {

    private boolean tieneReferencia;   
    private short contadorEnvejecimiento=0b00000000;

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
