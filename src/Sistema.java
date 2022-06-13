
import java.util.ArrayList;
import java.util.List;

public class Sistema extends Thread{
	static List<Usuario> users = new ArrayList<>();
	//Map<Date, Paciente>; -- Definir archivos o implementacion en mapas
	
	
	
	@Override
	public void run() {
		Usuario currentUser = verificacionIdentidad();
		if(currentUser instanceof Administrador)
		{
			((Administrador) currentUser).menu();
		}
		else if(currentUser instanceof Paciente)
		{
			((Paciente) currentUser).menu();
		}
		else if(currentUser instanceof Profesional)
		{
			((Profesional) currentUser).menu();
		}
		TimeControl.setLoop(false);
		separacionGuardadoListas();
	}
	
	

	private void separacionGuardadoListas()
	{
		List<Paciente> savesPacientes = new ArrayList<>();
		List<Administrador> savesAdmins = new ArrayList<>();
		List<Profesional> savesProfesionales = new ArrayList<>();
		for(Usuario u: users)
		{
			if(u instanceof Paciente)
			{
				savesPacientes.add((Paciente) u);
			}
			else if(u instanceof Administrador)
			{
				savesAdmins.add((Administrador) u);
			}
			else if(u instanceof Profesional)
			{
				savesProfesionales.add((Profesional) u);
			}
		}
		SerializacionGuardado.serializacion(nombreArchivos.PACIENTES.getName(), savesPacientes);
		SerializacionGuardado.serializacion(nombreArchivos.ADMINISTRADORES.getName(), savesAdmins);
		SerializacionGuardado.serializacion(nombreArchivos.PROFESIONALES.getName(), savesProfesionales);
	}
	
	private Usuario verificacionIdentidad()
	{
			Boolean flag = false;
			String userName = "";
			String userPass = "";
			Usuario currentLog = new Usuario();
			users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.ADMINISTRADORES.getName(), new Administrador()));
			users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.PACIENTES.getName(), new Paciente()));
			users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.PROFESIONALES.getName(), new Profesional()));
			
			do
			{
				System.out.println("Ingrese nombre de usuario");
				userName = ScannerSingleton.getInstance().nextLine();
				for(Usuario u : users)
				{
					if(userName.equalsIgnoreCase(u.getUserName()))
					{	
						System.out.println("Ingrese Contraseña");
						userPass = ScannerSingleton.getInstance().nextLine();
						if(u.getPassword().equals(userPass))
						{
							flag = true;
							currentLog = u;
						}
					}
				}
				if(!flag)
				{
					System.out.println("Datos no validos");
				}
			}while(!flag);
			
		return currentLog;
	}

	public static List<Plan> listarPlanes(){
		List<Plan> listaPlanes = new ArrayList<>();
		listaPlanes = SerializacionGuardado.deserializacion(nombreArchivos.PLANES.getName(), new Plan());
		return listaPlanes;
	}
	
	
	public static List<Tarea> verListaTareas()
	{
		List<Tarea> l = new ArrayList<>();
		l.addAll(SerializacionGuardado.deserializacion(nombreArchivos.TAREASNUMERICAS.getName(), new TareaNumerica()));
		l.addAll(SerializacionGuardado.deserializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), new TareaAlfanumerica()));
		l.addAll(SerializacionGuardado.deserializacion(nombreArchivos.TAREASBASICAS.getName(), new Tarea()));
		return l;
	}

	public static List<Enfermedad> verListaEnfermedades(){
		List<Enfermedad> listaEnfermedades = new ArrayList<>();
		listaEnfermedades.addAll(SerializacionGuardado.deserializacion(nombreArchivos.ENFERMEDADES.getName(), new Enfermedad()));
		return listaEnfermedades;
	}

}
