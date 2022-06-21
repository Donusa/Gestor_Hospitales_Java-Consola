import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;


public class TimeControl extends Thread{
	//---Atributos------------------------------------------------------------------------------------------------------
	private LocalDate localDate = LocalDate.now();
	private static Boolean loop = true;
	//------------------------------------------------------------------------------------------------------------------


	//---Metodos--------------------------------------------------------------------------------------------------------
	@Override
	public void run() {
		
		List<String> saves = new ArrayList<>();
		saves = SerializacionGuardado.deserializacion(nombreArchivos.BACKUPFECHA.getName(), new String());
		localDate = LocalDate.parse(saves.get(0));
		JsonMapper.mapLoad();
		do
		{
			if(!(localDate.equals(LocalDate.now())))
			{
				localDate = LocalDate.now();
				setPacientesMapFinDelDia();
				saves.set(0, localDate.toString());
				setTasksPacientes();
				SerializacionGuardado.serializacion(nombreArchivos.BACKUPFECHA.getName(), saves);
			}
		}while(loop == true);
	}

	public static void setLoop(Boolean loop) {
		TimeControl.loop = loop;
	}
	
	private void setPacientesMapFinDelDia()
	{
		List<Paciente> listaPacientes = new ArrayList<>();
		JsonMapper.mapSave();
		for(Usuario u : Sistema.users)
		{
			if(u instanceof Paciente)
			{
				listaPacientes.add((Paciente) u);
			}
		}
		for(int i = 0 ; i < listaPacientes.size() ; i++)
		{
			for(int j = 0 ; j < listaPacientes.get(i).getTratamientos().size(); j++)
			{
				if(listaPacientes.get(i).getTratamientos().get(j).getEstado().equals(EstadoDelTratamiento.FINALIZADO))
				{
					listaPacientes.get(i).getTratamientos().remove(j);
				}
			}
		}
		Sistema.userDate.put(localDate, listaPacientes);
	}
	
	  
	private void setTasksPacientes()
	{
		Boolean flag = false;
		do {
			try {
				for (Usuario u : Sistema.users) {
					if (u instanceof Paciente) {
						for (Tratamiento t : ((Paciente) u).getTratamientos()) {
							if (t.getFin() != null && t.getFin().equals(LocalDate.now().toString())) {
								t.setEstado(EstadoDelTratamiento.ESPERANDO_ALTA);
							}
							for (Tarea tareas : t.getPlan().getTasks()) {
								tareas.setTaskDone(false);
								if (tareas instanceof TareaAlfanumerica) {
									((TareaAlfanumerica) tareas).setInfo("");
								} else if (tareas instanceof TareaNumerica) {
									((TareaNumerica) tareas).setNumero(0);
								}
							}
						}
					}
				}
				flag = true;
			} catch (ConcurrentModificationException e) {
				System.out.println("Esperando carga de datos");
			} 
		} while (!flag);
	}
	//------------------------------------------------------------------------------------------------------------------
}
