package Laboratorio;

public class Resultado {
    private float valorResultado;
    private String descripcionResultado;

    public Resultado(float valorResultado, String descripcionResultado) {
        this.valorResultado = valorResultado;
        this.descripcionResultado = descripcionResultado;
    }

    public float getValorResultado() {
        return valorResultado;
    }

    public String getDescripcionResultado() {
        return descripcionResultado;
    }
}


