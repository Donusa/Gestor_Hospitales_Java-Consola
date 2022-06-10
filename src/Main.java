import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        /* Carga de Profesionales
        Profesional p = new Profesional("Diego Acosta", "diegoacosta@gmail.com", "1234", "25300401", "2234203488");
        Profesional p1 = new Profesional("Cecilia Abrego", "ceciliaabrego@gmail.com", "1234", "29430287", "2234563701");
        Profesional p2 = new Profesional("Matias Alvarez", "matiasalvarez@gmail.com", "1234", "31000403", "2235331675");
        Profesional p3 = new Profesional("Silvina Garcia", "silvinagarcia@gmail.com", "1234", "26547332", "2235554312");
        Profesional p4 = new Profesional("Mariana Lopez", "marianalopez@gmail.com", "1234", "32876554", "2234386598");
        Profesional p5 = new Profesional("Pablo Ledesma", "pabloledesma@gmail.com", "1234", "28443605", "2235463765");
        Profesional p6 = new Profesional("Gustavo Perez", "gustavoperez@gmail.com", "1234", "20448796", "2235677892");

        List<Profesional> profesionales = new ArrayList<>();
        profesionales.add(p);
        profesionales.add(p1);
        profesionales.add(p2);
        profesionales.add(p3);
        profesionales.add(p4);
        profesionales.add(p5);
        profesionales.add(p6);

        SerializacionGuardado.serializacion(nombreArchivos.PROFESIONALES.getName(), profesionales);
        */
        /*
        //Carga de Pacientes
        List<String> sintomas = new ArrayList<>();
        sintomas.add("Fiebre");
        sintomas.add("Tos");
        sintomas.add("Dolor de garganta");
        sintomas.add("Congestion nasal");
        sintomas.add("Dolores corporales");

        Enfermedad e = new Enfermedad(sintomas, "Gripe");

        Paciente paciente = new Paciente(e, "Diego Acosta", "Ana Paz", "anapaz@gmail.com", "1234", "38487299", "2234756570");

        Tarea t = new Tarea("Tomar ibuprofeno cada 8hs");
        Tarea t1 = new Tarea("Utilizar solucion salina nasal para aliviar la congestion nasal");
        Tarea t2 = new Tarea("Tomarse la fiebre. Ingresar el resultado en grados centigrados");
        Tarea t3 = new Tarea("Tomar pastillas para el dolor de garganta");

        List<Tarea> tareas = new ArrayList<>();
        tareas.add(t);
        tareas.add(t1);
        tareas.add(t2);
        tareas.add(t3);

        Plan plan = new Plan(e);
        plan.setDuracion(7);
        plan.setTasks(tareas);

        Tratamiento trat = new Tratamiento("Diego Acosta", e);
        trat.setPlan(plan);
        trat.setInicio(LocalDate.now().toString());
        trat.setFin();

        List<Tratamiento> tratamientos = new ArrayList<>();
        tratamientos.add(trat);

        paciente.setTratamientos(tratamientos);

        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(paciente);

        SerializacionGuardado.serializacion(nombreArchivos.PACIENTES.getName(), pacientes);

        //---------------------

        Profesional p = new Profesional("Diego Acosta", "diegoacosta@gmail.com", "1234", "25300401", "2234203488");
        Profesional p1 = new Profesional("Cecilia Abrego", "ceciliaabrego@gmail.com", "1234", "29430287", "2234563701");
        Profesional p2 = new Profesional("Matias Alvarez", "matiasalvarez@gmail.com", "1234", "31000403", "2235331675");
        Profesional p3 = new Profesional("Silvina Garcia", "silvinagarcia@gmail.com", "1234", "26547332", "2235554312");
        Profesional p4 = new Profesional("Mariana Lopez", "marianalopez@gmail.com", "1234", "32876554", "2234386598");
        Profesional p5 = new Profesional("Pablo Ledesma", "pabloledesma@gmail.com", "1234", "28443605", "2235463765");
        Profesional p6 = new Profesional("Gustavo Perez", "gustavoperez@gmail.com", "1234", "20448796", "2235677892");

        p.setPacientes(pacientes);

        List<Profesional> profesionales = new ArrayList<>();
        profesionales.add(p);
        profesionales.add(p1);
        profesionales.add(p2);
        profesionales.add(p3);
        profesionales.add(p4);
        profesionales.add(p5);
        profesionales.add(p6);

        SerializacionGuardado.serializacion(nombreArchivos.PROFESIONALES.getName(), profesionales);
        */
    }
}
