
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
    	Paciente p = new Paciente();
    	List<Paciente> l = new ArrayList<>();
    	l.add(p);
    	SerializacionGuardado.serializacion(nombreArchivos.PACIENTES.getName(), l);
    }
}
