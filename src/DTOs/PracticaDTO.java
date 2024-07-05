package DTOs;

public class PracticaDTO {
    private int codigoPractica;
    private String nombrePractica;
    private IndiceReservadoDTO indiceReservadoDTO;
    private IndiceCriticoDTO indiceCriticoDTO;

    public PracticaDTO(int codigoPractica, String nombrePractica, IndiceReservadoDTO indiceReservadoDTO, IndiceCriticoDTO indiceCriticoDTO){
        this.codigoPractica = codigoPractica;
        this.nombrePractica = nombrePractica;
        this.indiceReservadoDTO = indiceReservadoDTO;
        this.indiceCriticoDTO = indiceCriticoDTO;
    }
    public int getCodigoPractica() { return codigoPractica; }
    public String getNombrePractica() { return nombrePractica; }

    public IndiceReservadoDTO getIndiceReservadoDTO() {
        return indiceReservadoDTO;
    }

    public IndiceCriticoDTO getIndiceCriticoDTO() {
        return indiceCriticoDTO;
    }
}
