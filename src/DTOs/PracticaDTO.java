package DTOs;

public class PracticaDTO {
    private int codigoPractica;
    private IndiceReservadoDTO indiceReservadoDTO;
    private IndiceCriticoDTO indiceCriticoDTO;

    public PracticaDTO(int codigoPractica, IndiceReservadoDTO indiceReservadoDTO, IndiceCriticoDTO indiceCriticoDTO){
        this.codigoPractica = codigoPractica;
        this.indiceReservadoDTO = indiceReservadoDTO;
        this.indiceCriticoDTO = indiceCriticoDTO;
    }

}
