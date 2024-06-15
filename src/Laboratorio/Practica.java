package Laboratorio;

public class Practica {
    private int codigoPractica;
    private float valorReservado;
    private float valorCritico;
    private String descripcionReservado;
    private String descripcionCritico;

    public Practica(int codigoPractica) {
        this.codigoPractica = codigoPractica;
    }

    public int getCodigoPractica() {
        return codigoPractica;
    }
}
