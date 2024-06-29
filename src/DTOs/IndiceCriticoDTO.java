package DTOs;

public class IndiceCriticoDTO {
    private String value;
    private Float lowLimit;
    private Float highLimit;

    public IndiceCriticoDTO(String value, Float lowLimit, Float highLimit){
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
}
