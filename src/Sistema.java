
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema extends Thread{
	static List<Usuario> users = new ArrayList<>();
	//Map<Date, Paciente>; -- Definir archivos o implementacion en mapas
	
	
	
	public List<Usuario> filtradoLista(String tipoDeUsuario)
	{
		return new ArrayList<Usuario>();
		
	}
	
	@Override
	public void run() {
		
	}

	public void verificacionIdentidad(Usuario logUser)
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
