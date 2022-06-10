
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		Scanner scan = new Scanner(System.in);
		Boolean flag = false, nameChecker = false, passChecker = false;
		String userName = "";
		String userPass = "";
		Usuario currentLog = new Usuario();
		users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.ADMINISTRADORES.getName(), new Administrador()));
		users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.PACIENTES.getName(), new Paciente()));
		users.addAll(SerializacionGuardado.deserializacion(nombreArchivos.PROFESIONALES.getName(), new Profesional()));
		
		do
		{
			System.out.println("Ingrese nombre de usuario");
			userName = scan.nextLine();
			for(Usuario u : users)
			{
				if(userName.equalsIgnoreCase(u.getUserName()))
				{	
					nameChecker = true;
					currentLog = u;
				}
			}
			if(nameChecker)
			{
				System.out.println("Ingrese Contraseña");
				userPass = scan.nextLine();
				if(userPass.equals(currentLog.getPassword()));
				{
					flag = true;
					passChecker = true;
				}
			}
			if(!passChecker || !nameChecker)
			{
				System.out.println("Datos no validos");
				nameChecker = false;
				passChecker = false;
			}
		}while(flag == false);
		
		scan.close();
		return currentLog;
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

	public static List<Enfermedad> verListaEnfermedades(){
		List<Enfermedad> listaEnfermedades = new ArrayList<>();
		Sistema.mergeListas(SerializacionGuardado.deserializacion(nombreArchivos.ENFERMEDADES.getName(), new Enfermedad()), listaEnfermedades);
		return listaEnfermedades;
	}

}
