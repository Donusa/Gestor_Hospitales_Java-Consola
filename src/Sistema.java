import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public class Sistema extends Thread{
	//---Atributos------------------------------------------------------------------------------------------------------
	static List<Usuario> users = new ArrayList<>();
	static Map<LocalDate/*Fecha*/, List<Paciente>> userDate = new HashMap<>();
	//------------------------------------------------------------------------------------------------------------------


	//---Metodos--------------------------------------------------------------------------------------------------------
	@Override
	public void run() {
		levantarListaUsers();
		Usuario currentUser = verificacionIdentidad();
		JsonMapper.mapLoad();
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
		JsonMapper.mapSave();
		TimeControl.setLoop(false);
		separacionGuardadoListas();
	}

	private void levantarListaUsers(){
		users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.ADMINISTRADORES.getName(), new Administrador()));
		users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.PACIENTES.getName(), new Paciente()));
		users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.PROFESIONALES.getName(), new Profesional()));
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
		String userName;
		String userPass;
		Usuario currentLog = new Usuario();
			
		do
		{
			System.out.println("Ingrese nombre de usuario");
			userName = ScannerSingleton.getInstance().nextLine();
			for(Usuario u : users)
			{
				if(userName.equalsIgnoreCase(u.getUserName()))
				{
					System.out.println("Ingrese Contraseņa");
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
		return SerializacionGuardado.deserializacion(nombreArchivos.PLANES.getName(), new Plan());
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
	
	public static Enfermedad seleccionarEnfermedad() {
		List<Enfermedad> listaEnfermedades = verListaEnfermedades();
		Enfermedad retorno = null;
		int choice = -1;
		System.out.println("Seleccione una enfermedad");
		for(int i = 0 ; i < listaEnfermedades.size() ; i++)
		{
			System.out.println((i+1)+"."+listaEnfermedades.get(i).getName());
		}
		System.out.println("0. Salir");
		
			do {
				try {
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
					retorno = listaEnfermedades.get(choice);
				} catch (InputMismatchException e) {
					System.out.println("Ingrese un valor numerico valido");
				} 
			} while (retorno == null || choice == -1);
		return retorno;
	}
	
	public static Plan seleccionarPlan()
	{
		List<Plan> listaPlanes = listarPlanes();
		int choice = -1;
		Plan retorno = null;
		for(int i = 0 ; i < listaPlanes.size() ; i++)
		{
			System.out.println("Plan "+(i+1)+"\n"+listaPlanes.get(i));
		}
		
		do {
			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
				if(choice>=0)retorno = listaPlanes.get(choice);
			} catch (InputMismatchException e) {
				System.out.println("Ingrese un valor numerico valido");
			} 
		} while (retorno == null);
		return retorno;
	}
	//------------------------------------------------------------------------------------------------------------------
}
