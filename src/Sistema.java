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
		int choice;
		levantarListaUsers();
		do {
			JsonMapper.mapLoad();
			System.out.println("\n************************************************\n"
								+ "\tSistema de Control de Enfermedades\n"
								+ "************************************************\n"
								+ "1. Iniciar Sesion.\n"
								+ "0. Salir del programa.");
			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			} catch (Exception e) {
				choice = -1;
			}
			if (choice == 1) {
				Usuario currentUser = verificacionIdentidad();
				JsonMapper.mapLoad();
				if (currentUser instanceof Administrador) {
					((Administrador) currentUser).menu();
				} else if (currentUser instanceof Paciente) {
					((Paciente) currentUser).menu();
				} else if (currentUser instanceof Profesional) {
					((Profesional) currentUser).menu();
				} 
			} 
			else if(choice!=0 ){ System.out.println("Opcion no valida");}
			JsonMapper.mapSave();
		} while (choice!=0);
		TimeControl.setLoop(false);
		separacionGuardadoListas();
	}

	private void levantarListaUsers(){
		users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.PACIENTES.getName(), new Paciente()));
		users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.ADMINISTRADORES.getName(), new Administrador()));
		users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.PROFESIONALES.getName(), new Profesional()));
		typeFixer();
	}

	public static void typeFixer()
    {
        List<Tarea> lista = verListaTareas();
        int j = 0;
        for (Usuario u : users) {
			if (u instanceof Paciente) {
				for (Tratamiento t : ((Paciente) u).getTratamientos()) {
					for (int i = 0; i < t.getPlan().getTasks().size(); i++) {
						while (j < lista.size()
								&& !t.getPlan().getTasks().get(i).getTaskName().equals(lista.get(j).getTaskName())) {
							j++;
						}
						if (j < lista.size()) {
							t.getPlan().getTasks().set(i, lista.get(j));
						}
						j = 0;
					}
				} 
			} 
		}
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
		boolean flag = false;
		String userName;
		String userPass;
		Usuario currentLog = new Usuario();

		do
		{
			System.out.println("\n-- INICIAR SESION --");
			System.out.println("Ingrese nombre de usuario");
			userName = ScannerSingleton.getInstance().nextLine();
			for(Usuario u : users)
			{
				if(userName.equalsIgnoreCase(u.getUserName()))
				{
					System.out.println("Ingrese Contraseña");
					userPass = ScannerSingleton.getInstance().nextLine();
					System.out.println("----");
					if(u.getPassword().equals(userPass))
					{
						flag = true;
						currentLog = u;
					}
				}
			}
			if(!flag)
			{
				System.out.println("Datos no validos.");
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
		int choice;
		System.out.println("Seleccione una enfermedad");
		for(int i = 0 ; i < listaEnfermedades.size() ; i++)
		{
			System.out.println((i+1)+"."+listaEnfermedades.get(i).getName());
		}
		
			do {
				try {
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
					retorno = listaEnfermedades.get(choice);
				} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
					System.out.println("Ingrese un valor numerico valido");
				} 
			} while (retorno == null);
		return retorno;
	}
	
	public static Plan seleccionarPlan()
	{
		List<Plan> listaPlanes = listarPlanes();
		int choice;
		Plan retorno = null;
		for(int i = 0 ; i < listaPlanes.size() ; i++)
		{
			System.out.println((i+1)+". " + listaPlanes.get(i));
		}
		
		do {
			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
				retorno = listaPlanes.get(choice);
			} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
				System.out.println("Ingrese un valor numerico valido");
			} 
		} while (retorno == null);
		return retorno;
	}
	//------------------------------------------------------------------------------------------------------------------
}
