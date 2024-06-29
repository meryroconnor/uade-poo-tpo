package Laboratorio;

public class Practica {
    private int codigoPractica;
    private IndiceReservado indiceReservado;
    private IndiceCritico indiceCritico;

    public Practica(int codigoPractica, String valorCritico, Float lLimitCritico,Float hLCritico, String valorReservado, Float lLimitReservado, Float hLReservado) {
        this.codigoPractica = codigoPractica;
        this.indiceCritico = new IndiceCritico(valorCritico, lLimitCritico, hLCritico);
        this.indiceReservado = new IndiceReservado(valorReservado, lLimitReservado, hLReservado);
    }

    public int getCodigoPractica() {
        return codigoPractica;
    }

    public IndiceReservado getIndiceReservado() {
        return indiceReservado;
    }

    public IndiceCritico getIndiceCritico() {
        return indiceCritico;
    }

    @Override
    public String toString() {
        return "Practica{" +
                "codigoPractica=" + codigoPractica +
                ", indiceReservado=" + indiceReservado +
                ", indiceCritico=" + indiceCritico +
                '}';
    }
}

