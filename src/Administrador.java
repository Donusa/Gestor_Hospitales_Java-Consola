import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Administrador extends Usuario implements CrearPlan, Menu{

	public Administrador() {
	}

	public Administrador(String userName , String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		
	}

	
	public Paciente ingresoPacientes()
	{
		Paciente p = new Paciente();
		Usuario.crearUser(p);
		return p;
	}
	
	public Profesional ingresoProfesionales()
	{
		Profesional p = new Profesional();
		Usuario.crearUser(p);
		return p;
	}

	public void administracionEnfermedades()
	{
		int choice = 0;
		Scanner scan =  new Scanner(System.in);

		do{
			try{
				System.out.println("1. Agregar enfermedad.\n"
						+ "2. Eliminar enfermedad.\n"
						+ "3. Ver lista de enfermedades.\n"
						+ "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice){
					case 1:	nuevaEnfermedad();
						break;
					case 2: eliminarEnfermedad();
						break;
					case 3: System.out.println(Sistema.verListaEnfermedades());
						break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);
		scan.close();
	}

	public void eliminarEnfermedad()
	{
		List<Enfermedad> listaEnfermedades = Sistema.verListaEnfermedades();
		Scanner scan = new Scanner(System.in);
		int choice = -1;
		for(int i = 0 ; i < listaEnfermedades.size() ; i++)
		{
			System.out.println((i+1)+"."+listaEnfermedades.get(i).getName());
		}
		do
		{
			try
			{
				System.out.println("Ingrese el numero de la enfermedad a eliminar: ");
				choice = scan.nextInt();

			}
			catch (InputMismatchException e) {
				System.out.println("Solo valores numericos");
			}
		}while(choice < 0 || choice >= listaEnfermedades.size());
		listaEnfermedades.remove(choice-1);
		SerializacionGuardado.serializacion(nombreArchivos.ENFERMEDADES.getName(), listaEnfermedades);
		scan.close();
	}

	public void nuevaEnfermedad()
	{
		int choice = 0;
		List<Enfermedad> listaEnfermedades = Sistema.verListaEnfermedades();
		List<String> sintomas = new ArrayList<>();
		Enfermedad nuevaEnfermedad = new Enfermedad();
		Scanner scan = new Scanner(System.in);
		do{
			try{
				System.out.println("Ingrese el nombre de la enfermedad: ");
				nuevaEnfermedad.setName(scan.nextLine());
				System.out.println("Ingrese los sintomas de la enfermedad: ");
				do {
					try {
						sintomas.add(scan.nextLine());
						System.out.println("1. Agregar otro sintoma.\n+"
								+ "0. Salir.\n");
						choice = scan.nextInt();
					}
					catch(InputMismatchException e){
						System.out.println("Ingrese un valor numerico.\n");
					}
					} while (choice != 0);
				nuevaEnfermedad.setSintomas(sintomas);
				}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (nuevaEnfermedad.getSintomas().isEmpty());
		listaEnfermedades.add(nuevaEnfermedad);
		SerializacionGuardado.serializacion(nombreArchivos.ENFERMEDADES.getName(), listaEnfermedades);
		scan.close();

	}
	
	public void administracionTareasDeControl()
	{
		int choice = 0;
		Scanner scan =  new Scanner(System.in);

		do{
			try{
				System.out.println("1. Agregar tarea.\n"
								 + "2. Eliminar tarea.\n"
								 + "3. Ver lista de tareas.\n"
								 + "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice){
					case 1:	nuevaTarea();
							break;
					case 2: eliminarTarea();
							break;
					case 3: System.out.println(Sistema.verListaTareas());
							break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);
		scan.close();
	}
	
	public void eliminarTarea()
	{
		List<Tarea> l = Sistema.verListaTareas();
		List<Tarea> save = new ArrayList<>();
		Tarea aux = new Tarea();
		Scanner scan = new Scanner(System.in);
		int choice = -1;
		for(int i = 0 ; i < l.size() ; i++)
		{
			System.out.println((i+1)+"."+l.get(i).getTaskName());
		}
		do
		{
			try
			{
				System.out.println("Ingrese el numero de la tarea a eliminar: ");
				choice = scan.nextInt();
				
			}
			catch (InputMismatchException e) {
				System.out.println("Solo valores numericos");
			}	
		}while(choice < 0 || choice >= l.size());
		aux = l.get(choice-1);
		l.remove(choice-1);
		
		for(Tarea t: l)
		{
			if(aux instanceof TareaNumerica && t instanceof TareaNumerica)
			{
				save.add(t);
			}
			else if(aux instanceof TareaAlfanumerica && t instanceof TareaAlfanumerica)
			{
				save.add(t);
			}
			else
			{
				if(!(t instanceof TareaNumerica || t instanceof TareaAlfanumerica))
				{
					save.add(t);
				}
			}
		}
		if(aux instanceof TareaNumerica)
		{
			SerializacionGuardado.serializacion(nombreArchivos.TAREASNUMERICAS.getName(), save);
		}
		else if(aux instanceof TareaAlfanumerica)
		{
			SerializacionGuardado.serializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), save);
		}
		else
		{
			SerializacionGuardado.serializacion(nombreArchivos.TAREASBASICAS.getName(), save);
		}

		scan.close();
	}
		
	public void nuevaTarea()
	{
		int choice = 0;
		String taskName = "";
		Tarea nuevaTarea = null;
		Scanner scan = new Scanner(System.in);
		do{
			try{
				System.out.println("1. Tarea simple de solo Check\n"
								 + "2. Tarea con respuesta Alfanumerica.\n"
								 + "3. Tarea con respuesta Numerica\n");
				choice = scan.nextInt();
				switch (choice){
					case 1:
						nuevaTarea = new Tarea(taskName);
						break;
					case 2:	
						nuevaTarea = new TareaAlfanumerica(taskName);
						break;
					case 3:
						nuevaTarea = new TareaNumerica(taskName);
						break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (nuevaTarea == null);
		scan.close();
		//agregar tarea al archivo
	}


	@Override
	public Plan crearNuevoPlan(Enfermedad e) {          /// Falta que agregue el nuevo plan al archivo de planes
		Scanner scan= new Scanner(System.in);
		Plan p= new Plan(e);
		System.out.println("Ingrese la duracion del Plan:");
		p.setDuracion(scan.nextInt());
		int rta =2;
		do {
			try {
				p.agregarTarea();
				System.out.println("Desea agregar otra tarea?\n"+
						"1. Si\n"+
						"2. No");
				rta=scan.nextInt();

			}catch (InputMismatchException exception){
				System.out.println("Ingrese una opcion valida.\n");
			}
		}while (rta == 1);
		scan.close();
		return p;
	}

	@Override
	public Plan modificarPlan(Plan p){             /// Falta que modifique el plan en el archivo de planes
		int choice = 0;
		Scanner scan =  new Scanner(System.in);

		do{
			try{
				System.out.println("1. Modificar duracion.\n"
								+ "2. Agregar tarea.\n"
								+ "3. Borrar tarea.\n"
								+ "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice) {
					case 1 :
						System.out.println("Ingrese la nueva duracion:");
						p.setDuracion(scan.nextInt());
						break;
					case 2 :
						p.agregarTarea();
						break;
					case 3 :
						p.mostrarTareas();
						System.out.println("Ingrese el numero de tarea que quieras eliminar");
						p.getTasks().remove(scan.nextInt()-1);
						break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);

		scan.close();
		return p;
	}

	@Override
	public void menu() {
		int choice = 0;
		Scanner scan =  new Scanner(System.in);

		do{
			try{
				System.out.println("1. Ingreso de Pacientes.\n"
								 + "2. Ingreso de Profesionales.\n"
								 + "3. Administracion de Enfermedades.\n"
								 + "4. Administracion de Tareas de Control.\n"
								 + "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice){
					case 1:	ingresoPacientes();
							break;
					case 2:	ingresoProfesionales();
							break;
					case 3:	administracionEnfermedades();
							break;
					case 4: administracionTareasDeControl();
							break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);
		scan.close();
	}

	@Override
	public String toString() {
		return "ADMINISTRADOR | " + super.toString();
	}
}
