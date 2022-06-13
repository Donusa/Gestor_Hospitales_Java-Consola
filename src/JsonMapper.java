import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class JsonMapper {
	
	String fecha;
	List<Paciente> lista = new ArrayList<>();

	public static void mapSave()
	{
		List<JsonMapper> mapaAux = new ArrayList<>();
		JsonMapper aux = new JsonMapper();
		Sistema.userDate.forEach((k,v)-> 
		{
			aux.setFecha(k.toString());
			aux.setLista(v);
			mapaAux.add(aux);
		});
		SerializacionGuardado.serializacion(nombreArchivos.MAPAUSUARIO.getName(), mapaAux);
	}
	
	public static void mapLoad()
	{
		List<JsonMapper> listaDeserializacion = SerializacionGuardado
												.deserializacion(nombreArchivos.MAPAUSUARIO.getName(), new JsonMapper());
		for(JsonMapper m : listaDeserializacion)
		{
			Sistema.userDate.put(LocalDate.parse(m.getFecha()), m.getLista());
		}
	}
	
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List<Paciente> getLista() {
		return lista;
	}

	public void setLista(List<Paciente> v) {
		this.lista = v;
	}


}
