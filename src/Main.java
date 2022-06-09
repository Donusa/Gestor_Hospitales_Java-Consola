import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        List<String> sintomas = new ArrayList<>();
        sintomas.add("Fiebre");
        sintomas.add("Tos");
        sintomas.add("Dolor de garganta");
        sintomas.add("Congestion nasal");
        sintomas.add("Dolores corporales");
        Enfermedad e = new Enfermedad(sintomas, "Gripe");

        List<Enfermedad> le = new ArrayList<>();
        le.add(e);
        SerializacionGuardado.serializacion(nombreArchivos.ENFERMEDADES.getName(), le);
    }
}
