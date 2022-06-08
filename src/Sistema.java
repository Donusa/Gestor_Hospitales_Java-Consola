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



	public List<Plan> listarPlanes(){
		List<Plan> listaPlanes = new ArrayList<>();
		listaPlanes = SerializacionGuardado.deserializacion(nombreArchivos.PLANES.getName(), new Plan());
		return listaPlanes;
	}
}
