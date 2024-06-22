package Laboratorio;

public class IndiceReservado {
    private String value;
    private Float lowLimit;
    private Float highLimit;

    protected IndiceReservado(String value, Float lowLimit, Float highLimit) {
        this.value = value;
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }

    public String getValue() {
        return value;
    }

    public Float getLowLimit() {
        return lowLimit;
    }

    public Float getHighLimit() {
        return highLimit;
    }

    @Override
    public String toString() {
        return "IndiceReservado{" +
                "value='" + value + '\'' +
                ", lowLimit=" + lowLimit +
                ", highLimit=" + highLimit +
                '}';
    }
}
