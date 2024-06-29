package DAOs;
import Laboratorio.Paciente;
import utils.GenericDAO;
public class PacienteDAO extends GenericDAO{


    public PacienteDAO(Class clase, String file) throws Exception {
        super(Paciente.class, "./src/txtDataFiles/usuarios.json");
    }
}
