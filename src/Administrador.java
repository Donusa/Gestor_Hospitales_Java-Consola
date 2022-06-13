import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Administrador extends Usuario implements CrearPlan, Menu{

	//---Constructores--------------------------------------------------------------------------------------------------
	public Administrador() {
	}

	public Administrador(String userName , String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Metodos--------------------------------------------------------------------------------------------------------
	public void ingresoPacientes()
	{
		Paciente p = new Paciente();
		Usuario.crearUser(p);
		Sistema.users.add(p);
		
	}
	
	public void ingresoProfesionales()
	{
		Profesional p = new Profesional();
		Usuario.crearUser(p);
		Sistema.users.add(p);
	}
	
	public void administracionEnfermedades()
	{
		int choice = 0;

		do{
			
				System.out.println("1. Agregar enfermedad.\n"
						+ "2. Eliminar enfermedad.\n"
						+ "3. Ver lista de enfermedades.\n"
						+ "0. Salir.\n");
				try{
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				}
				catch(InputMismatchException e){
					System.out.println("Ingrese una opcion valida.\n");
				}
				switch (choice){
					case 1:	nuevaEnfermedad();
						break;
					case 2: eliminarEnfermedad();
						break;
					case 3: System.out.println(Sistema.verListaEnfermedades());
						break;
				}
			
		} while (choice!=0);
		
	}

	public void eliminarEnfermedad()
	{
		List<Enfermedad> listaEnfermedades = Sistema.verListaEnfermedades();
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
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine())-1;

			}
			catch (InputMismatchException e) {
				System.out.println("Solo valores numericos");
			}
		}while(choice < 0 || choice >= listaEnfermedades.size());
		listaEnfermedades.remove(choice-1);
		SerializacionGuardado.serializacion(nombreArchivos.ENFERMEDADES.getName(), listaEnfermedades);
		
	}

	public void nuevaEnfermedad()
	{
		int choice = 0;
		List<Enfermedad> listaEnfermedades = Sistema.verListaEnfermedades();
		List<String> sintomas = new ArrayList<>();
		Enfermedad nuevaEnfermedad = new Enfermedad();
		do{
			try{
				System.out.println("Ingrese el nombre de la enfermedad: ");
				nuevaEnfermedad.setName(ScannerSingleton.getInstance().nextLine());
				System.out.println("Ingrese los sintomas de la enfermedad: ");
				do {
					try {
						sintomas.add(ScannerSingleton.getInstance().nextLine());
						System.out.println("1. Agregar otro sintoma.\n+"
								+ "0. Salir.\n");
						choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
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
		

	}
	
	public void administracionTareasDeControl()
	{
		int choice = 0;

		do{
			
				System.out.println("1. Agregar tarea.\n"
								 + "2. Eliminar tarea.\n"
								 + "3. Ver lista de tareas.\n"
								 + "0. Salir.\n");
				try{
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				}
				catch(InputMismatchException e){
					System.out.println("Ingrese una opcion valida.\n");
				}
				switch (choice){
					case 1:	nuevaTarea();
							break;
					case 2: eliminarTarea();
							break;
					case 3: System.out.println(Sistema.verListaTareas());
							break;
				}
			
		} while (choice!=0);
		
	}

	public void eliminarTarea()
	{
		List<Tarea> l = Sistema.verListaTareas();
		List<Tarea> save = new ArrayList<>();
		Tarea aux = new Tarea();
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
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				
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

		
	}
		
	public void nuevaTarea()
	{
		int choice = 0;
		String taskName = "";
		Tarea nuevaTarea = null;
		List<Tarea> saves = new ArrayList<>();
		do{
			
				System.out.println("1. Tarea simple de solo Check\n"
								 + "2. Tarea con respuesta Alfanumerica.\n"
								 + "3. Tarea con respuesta Numerica\n");
				try{
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				}
				catch(InputMismatchException e){
					System.out.println("Ingrese una opcion valida.\n");
				}
				
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
			
		} while (nuevaTarea == null);
		
		saves.add(nuevaTarea);
		
		if(nuevaTarea instanceof TareaNumerica)
		{
			SerializacionGuardado.serializacion(nombreArchivos.TAREASNUMERICAS.getName(), saves);
		}
		else if(nuevaTarea instanceof TareaAlfanumerica)
		{
			SerializacionGuardado.serializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), saves);
		}
		else
		{
			SerializacionGuardado.serializacion(nombreArchivos.TAREASBASICAS.getName(), saves);
		}
	}


	@Override
	public Plan crearNuevoPlan(Enfermedad e) {     
		
		Plan p= new Plan(e);
		System.out.println("Ingrese la duracion del Plan:");
		p.setDuracion(Integer.parseInt(ScannerSingleton.getInstance().nextLine()));
		int rta =2;
		do {
			try {
				p.agregarTarea();
				System.out.println("Desea agregar otra tarea?\n"+
						"1. Si\n"+
						"2. No");
				rta=Integer.parseInt(ScannerSingleton.getInstance().nextLine());

			}catch (InputMismatchException exception){
				System.out.println("Ingrese una opcion valida.\n");
			}
		}while (rta == 1);
		
		return p;
	}

	@Override
	public Plan modificarPlan(Plan p){           
		int choice = 0;

		do{
			
				System.out.println("1. Modificar duracion.\n"
								+ "2. Agregar tarea.\n"
								+ "3. Borrar tarea.\n"
								+ "0. Salir.\n");
				try{
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				}
				catch(InputMismatchException e){
					System.out.println("Ingrese una opcion valida.\n");
				}
				
				switch (choice) {
					case 1 :
						System.out.println("Ingrese la nueva duracion:");
						p.setDuracion(Integer.parseInt(ScannerSingleton.getInstance().nextLine()));
						break;
					case 2 :
						p.agregarTarea();
						break;
					case 3 :
						p.mostrarTareas();
						System.out.println("Ingrese el numero de tarea que quieras eliminar");
						p.getTasks().remove(Integer.parseInt(ScannerSingleton.getInstance().nextLine())-1);
						break;
				}
			
		} while (choice!=0);

		
		return p;
	}

	private void administracionPlanes()
	{
		int choice = 0;
		List<Plan> save = Sistema.listarPlanes();
		Plan plan = new Plan();
		do{
			
			System.out.println("1. Crear nuevo plan.\n"
							 + "2. Modificar plan existente"
							 + "0. Salir");
				
			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			} catch (InputMismatchException e) {
				System.out.println("Ingrese un dato numerico valido");
			}

			switch (choice){
				case 1: Enfermedad enfermedad = Sistema.seleccionarEnfermedad(); 
						crearNuevoPlan(enfermedad);
						break;
				case 2: plan = Sistema.seleccionarPlan();
						modificarPlan(plan);
						break;
				default: System.out.println("Ingrese un dato numerico valido");
						break;
			}
		} while (choice!=0);
		
		SerializacionGuardado.serializacion(nombreArchivos.PLANES.getName(), save);
	}
	
	@Override
	public void menu() {
		int choice = -1;
		do{
			System.out.println("1. Ingreso de Pacientes.\n"
							 + "2. Ingreso de Profesionales.\n"
							 + "3. Administracion de Enfermedades.\n"
							 + "4. Administracion de Tareas de Control.\n"
							 + "5. Administracion de Planes\n"
							 + "0. Salir.\n");

			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			} catch (InputMismatchException e) {
				System.out.println(e);
			}
			switch (choice){
				case 1:	ingresoPacientes();
						break;
				case 2:	ingresoProfesionales();
						break;
				case 3:	administracionEnfermedades();
						break;
				case 4: administracionTareasDeControl();
						break;
				case 5: administracionPlanes();
						break;
				case 0: break;
				default: System.out.println("Ingrese un dato numerico valido");
						break;
			}
		} while (choice!=0);
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "ADMINISTRADOR | " + super.toString();
	}
	//------------------------------------------------------------------------------------------------------------------
}
