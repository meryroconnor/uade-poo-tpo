package DTOs;

import Laboratorio.IndiceCritico;
import Laboratorio.IndiceReservado;

public class PracticaDTO {
    private int codigoPractica;
    private IndiceReservado indiceReservado;
    private IndiceCritico indiceCritico;

    public PracticaDTO(int codigoPractica, IndiceReservado indiceReservado, IndiceCritico indiceCritico){
        this.codigoPractica = codigoPractica;
        this.indiceReservado = indiceReservado;
        this.indiceCritico = indiceCritico;
    }

}
