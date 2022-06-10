import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // de prueba para generar archivo tareas.json
        Tarea t = new Tarea("Tomar agua");
        List<Tarea> l = new ArrayList<>();
        l.add(t);
        SerializacionGuardado.serializacion(nombreArchivos.TAREASBASICAS.getName(), l);
    }
}


