package Laboratorio;

public class IndiceCritico {
    private String value;
    private Float lowLimit;
    private Float highLimit;

    public IndiceCritico(String value, Float lowLimit, Float highLimit) {
        this.value = value;
        this.lowLimit = lowLimit;
        this.highLimit = highLimit;
    }

    public String getValue() {
        return value;
    }

    public float getLowLimit() {
        return lowLimit;
    }

    public float getHighLimit() {
        return highLimit;
    }

    @Override
    public String toString() {
        return "IndiceCritico{" +
                "value='" + value + '\'' +
                ", lowLimit=" + lowLimit +
                ", highLimit=" + highLimit +
                '}';
    }
}
