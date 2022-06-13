
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TimeControl extends Thread{

	private LocalDate localDate = LocalDate.now();
	private static Boolean loop = true;


	@Override
	public void run() {
		
		List<String> saves = new ArrayList<>();
		saves = SerializacionGuardado.deserializacion(nombreArchivos.BACKUPFECHA.getName(), new String());
		localDate = LocalDate.parse(saves.get(0));
		do
		{
			if(!(localDate.equals(LocalDate.now())))
			{
				localDate = LocalDate.now();
				saves.remove(0);
				saves.add(localDate.toString());
				SerializacionGuardado.serializacion(nombreArchivos.BACKUPFECHA.getName(), saves);
				setTasksPacientes();
			}
		}while(loop == true);
	}

	public static void setLoop(Boolean loop) {
		TimeControl.loop = loop;
	}
	
	private void setTasksPacientes()
	{
		for(Usuario u: Sistema.users)
		{
			if(u instanceof Paciente)
			{
				for(Tratamiento t: ((Paciente) u).getTratamientos())
				{
					for(Tarea tareas: t.getPlan().getTasks())
					{
						tareas.setTaskDone(false);
					}
				}
			}
		}
	}
	
	
}
