package DTOs;

public class PracticaDTO {
    private int codigoPractica;
    private String nombrePractica;
    private IndiceReservadoDTO indiceReservadoDTO;
    private IndiceCriticoDTO indiceCriticoDTO;
    private int cantidadHorasDemora;

    public PracticaDTO(int codigoPractica, String nombrePractica, IndiceReservadoDTO indiceReservadoDTO, IndiceCriticoDTO indiceCriticoDTO, int cantidadHorasDemora){
        this.codigoPractica = codigoPractica;
        this.nombrePractica = nombrePractica;
        this.indiceReservadoDTO = indiceReservadoDTO;
        this.indiceCriticoDTO = indiceCriticoDTO;
        this.cantidadHorasDemora = cantidadHorasDemora;
    }
    public int getCodigoPractica() { return codigoPractica; }
    public String getNombrePractica() { return nombrePractica; }

    public IndiceReservadoDTO getIndiceReservadoDTO() {
        return indiceReservadoDTO;
    }

    public IndiceCriticoDTO getIndiceCriticoDTO() {
        return indiceCriticoDTO;
    }

    public int getCantidadHorasDemora() {
        return cantidadHorasDemora;
    }
}
