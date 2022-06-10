import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sistema {
	static List<Usuario> users = new ArrayList<>();
	LocalDate date;
	//Map<Date, Paciente>; -- Definir archivos o implementacion en mapas
	
	public List<Usuario> filtradoLista(String tipoDeUsuario)
	{
		return new ArrayList<Usuario>();
	}
	
	public void verificacionIdentidad()
	{
		
	}
	
	public void dayChangeReset()
	{

	}

	public static <T> List<T> mergeListas(List<T> lista, List<T> listaAgregada)
	{
		lista.addAll(listaAgregada);
		return lista;
	}

	public static List<Plan> listarPlanes(){
		List<Plan> listaPlanes = new ArrayList<>();
		listaPlanes = SerializacionGuardado.deserializacion(nombreArchivos.PLANES.getName(), new Plan());
		return listaPlanes;
	}
	
	
	public static List<Tarea> verListaTareas()
	{
		List<Tarea> l = new ArrayList<>();
		Sistema.mergeListas(SerializacionGuardado.deserializacion(nombreArchivos.TAREASBASICAS.getName(), new Tarea()), l);
		Sistema.mergeListas(SerializacionGuardado.deserializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), new Tarea()), l);
		Sistema.mergeListas(SerializacionGuardado.deserializacion(nombreArchivos.TAREASNUMERICAS.getName(), new Tarea()), l);
		Sistema.mergeListas(SerializacionGuardado.deserializacion(nombreArchivos.TAREASSINO.getName(), new Tarea()), l);
		return l;
	}

}
