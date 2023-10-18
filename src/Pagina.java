public class Pagina {
    private int numero;
    private int contador = 0b00000000; //8bits

    public Pagina(int pNumero) {
        this.numero = pNumero;
    }

    public int darNumero() {
        return numero;
    }

    public void setNumero(int nuevoNumero) {
        numero = nuevoNumero;
    }

    public int darContador() {
        return contador;
    }

    public void setContador(int nuevoContador) {
        contador = nuevoContador;
    }

    public void agregarConteo(int referenciado) {
        if (referenciado == 0) {
            contador = (contador >>> 1) & 0x7F;
        }
        else {
            contador = (contador >>> 1) | 0x80;
        }
    }

}
