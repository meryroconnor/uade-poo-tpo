package controlador;

import Laboratorio.ControladorPaciente;
import Laboratorio.ObraSocial;
import Laboratorio.Paciente;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ControladorVista {
    private static ControladorVista instance;
    private ControladorPaciente controladorPaciente;

    private ControladorVista() {
        controladorPaciente = ControladorPaciente.getInstance();
    }

    public static ControladorVista getInstance() {
        if (instance == null) {
            instance = new ControladorVista();
        }
        return instance;
    }

    public void agregarPaciente(String nombre, String dni, String sexo, Date fechaNacimiento, String email, String obraSocial, int numeroAfiliado) {
        Paciente paciente = controladorPaciente.createPaciente(nombre,sexo, dni, email);
        ObraSocial os = new ObraSocial(obraSocial, numeroAfiliado);
        paciente.setObraSocial(os);
    }

    public String buscarPaciente(String pacienteID, String dni) {
        StringBuilder resultado = new StringBuilder();
        List<Paciente> pacientes = controladorPaciente.buscarPaciente(pacienteID, dni);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Paciente p : pacientes) {
            resultado.append("ID: ").append(p.getPacienteID()).append(", Nombre: ").append(p.getNombreApellido())
                    .append(", DNI: ").append(p.getDNI()).append(", Sexo: ").append(p.getSexo())
                    .append(", Fecha de Nacimiento: ").append(sdf.format(p.getFechaNacimiento()))
                    .append(", Email: ").append(p.getEmail()).append(", Obra Social: ").append(p.getObraSocial().getObraSocial())
                    .append(", Número de Afiliado: ").append(p.getObraSocial().getNumeroAfiliado()).append("\n");
        }

        return resultado.toString();
    }

    public void eliminarPaciente(String pacienteID) {
        controladorPaciente.deletePaciente(Integer.parseInt(pacienteID));
    }
}
