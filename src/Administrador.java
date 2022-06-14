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
	private void ingresoPacientes()
	{
		Paciente p = new Paciente();
		Usuario.crearUser(p);	//Envia paciente vacio a funcion, solo carga datos comunes de usuario(padre)
		Sistema.users.add(p);	//agrega a la lista general, esta se guarda al finalizar el programa y persiste los cambios
		
	}
	
	private void gestionPacientes()	//funcion encargada de agregar profesionales a los pacientes y/o agregar pacientes
	{
		int choice = -1;
		do {
			System.out.println("1. Ingresar nuevo paciente\n" + "2. Asignar profesional a paciente\n" + "0. Salir\n");
			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			} catch (InputMismatchException e) {
				System.out.println(e);
			}
			switch (choice) {
			case 1:
				ingresoPacientes();
				break;
			case 2:
				asignarProfesional();
				break;
			case 0:
				break;
			default:
				System.out.println("Ingrese una opcion valida");
				break;
			}
		} while (choice!=0);
		
	}
	
	private void asignarProfesional()  //asigna profesional al paciente
	{
		Profesional profesional = null;
		Paciente paciente = null;
		System.out.println("Ingrese DNI de profesional a asignar");
		String dni = ScannerSingleton.getInstance().nextLine();//metodo de busca en lista
		
		for(Usuario u: Sistema.users)
		{
			if(u instanceof Profesional && u.getUserDni().equals(dni)) {//si encuentra al usuario y este es un profesional
				profesional = (Profesional)u;
				System.out.println("Ingrese DNI del paciente a asignar");//luego se cambia la busqueda al paciente
				dni = ScannerSingleton.getInstance().nextLine();
			}
		}
		if(profesional!=null)//si el profesional fue encontrado
		{
			for(Usuario u: Sistema.users)
			{
				if(u instanceof Paciente && u.getUserDni().equals(dni))
				{
					paciente = (Paciente)u;//se guarda el paciente que coincida con la informacion
				}
			}
		}
		
		if(profesional!=null && paciente!=null)//si ambos datos fueron corroborados y existen
		{
			Enfermedad e = Sistema.seleccionarEnfermedad();//se selecciona una enfermedad de la lista
			paciente.getTratamientos().add(new Tratamiento(profesional.getUserName(), e));
			//y se le asigna junto a un nuevo tratamiento al paciente previamente encontrado
			
		}
	}
	
	private void ingresoProfesionales()	//Igual que con la funcion ingresoPaciente, este ingresa un profesional al sistema
	{
		Profesional p = new Profesional();
		Usuario.crearUser(p);//Se crea un nuevo profesional asignandole los valores comunes a todos los usuarios
		Sistema.users.add(p);//se asigna a la lista de usuarios que luego es persistida al finalizar el programa
	}
	
	public void administracionEnfermedades() //funcion que tiene el alta/baja/modificacion de las enfermedades
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
					case 1:	nuevaEnfermedad();//crea y persiste nueva enfermedad
						break;
					case 2: eliminarEnfermedad();//elimina del archivo una enfermedad especifica
						break;
					case 3: System.out.println(Sistema.verListaEnfermedades());//print a la lista de enfermedades en archivo
						break;
				}
			
		} while (choice!=0);
		
	}

	public void eliminarEnfermedad()
	{
		List<Enfermedad> listaEnfermedades = Sistema.verListaEnfermedades();//levanta la lista del json
		int choice = -2;
		for(int i = 0 ; i < listaEnfermedades.size() ; i++)
		{
			System.out.println((i+1)+"."+listaEnfermedades.get(i).getName());
			System.out.println("0. Salir");
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
		}while(choice !=-1);//En este caso se contempla -1 ya que para la comodidad del usuario se evita usar el valor 0
		//en la lista mostrada
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
		//guarda la nueva enfermedaden sistema
		

	}
	
	public void administracionTareasDeControl() //Alta/baja de tareas
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
		int choice = -2;
		for(int i = 0 ; i < l.size() ; i++)
		{
			System.out.println((i+1)+"."+l.get(i).getTaskName());
			System.out.println("0. Salir");
		}
		do
		{
			try
			{
				System.out.println("Ingrese el numero de la tarea a eliminar: ");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine())-1;
				
			}
			catch (InputMismatchException e) {
				System.out.println("Solo valores numericos");
			}	
		}while(choice!=-1);//Se usa -1 por la forma en la cual se muetra para los usuarios la lista, al captar 0(salida)
		//choice lo interpreta como -1
		aux = l.get(choice-1);
		l.remove(choice-1);
		
		for(Tarea t: l)//Busca la instancia correspondiente para agregarla a una lista valida a la hora de guardar
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
		//dependiendo la instancia valida, guarda en el archivo correspondiente
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
		
		//guarda la lista valida correspondiente a la instancia de la tarea creada
		
		if(nuevaTarea instanceof TareaNumerica)
		{
			saves.addAll(SerializacionGuardado.deserializacion(nombreArchivos.TAREASNUMERICAS.getName(), new TareaNumerica()));
			SerializacionGuardado.serializacion(nombreArchivos.TAREASNUMERICAS.getName(), saves);
		}
		else if(nuevaTarea instanceof TareaAlfanumerica)
		{
			saves.addAll(SerializacionGuardado.deserializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), new TareaAlfanumerica()));
			SerializacionGuardado.serializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), saves);
		}
		else
		{
			saves.addAll(SerializacionGuardado.deserializacion(nombreArchivos.TAREASBASICAS.getName(), new Tarea()));
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
			System.out.println("1. Gestion de pacientes.\n"
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
				case 1:	gestionPacientes();
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
