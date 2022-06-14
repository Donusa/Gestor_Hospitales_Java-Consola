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
		if(Usuario.crearUser(p)) {		//Envia paciente vacio a funcion, solo carga datos comunes de usuario(padre)
			Sistema.users.add(p);    	//agrega a la lista general, esta se guarda al finalizar el programa y persiste los cambios
		}
		
	}

	private void ingresoProfesionales()	//Igual que con la funcion ingresoPaciente, este ingresa un profesional al sistema
	{
		Profesional p = new Profesional();
		if(Usuario.crearUser(p)) {	//Se crea un nuevo profesional asignandole los valores comunes a todos los usuarios
			Sistema.users.add(p);	//se asigna a la lista de usuarios que luego es persistida al finalizar el programa
		}
	}
	
	private void gestionPacientes()	//funcion encargada de agregar profesionales a los pacientes y/o agregar pacientes
	{
		int choice = 0;
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
			Tratamiento tratamiento = new Tratamiento(profesional.getUserName(), e);
			paciente.getTratamientos().add(tratamiento);
			//y se le asigna junto a un nuevo tratamiento al paciente previamente encontrado
			
		}
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
					System.out.println("Ingrese una opcion valida prueba.\n");
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
		}
			try
			{
				System.out.println("Ingrese el numero de la enfermedad a eliminar: ");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine())-1;

			}
			catch (InputMismatchException e) {
				System.out.println("Solo valores numericos");
			}
		listaEnfermedades.remove(choice-1);
		SerializacionGuardado.serializacion(nombreArchivos.ENFERMEDADES.getName(), listaEnfermedades);
		
	}

	public void nuevaEnfermedad() {
		boolean yaExiste = false;
		List<Enfermedad> listaEnfermedades = Sistema.verListaEnfermedades();
		Enfermedad nuevaEnfermedad = new Enfermedad();

		System.out.println("Ingrese el nombre de la enfermedad: ");
		nuevaEnfermedad.setName(ScannerSingleton.getInstance().nextLine());
		for(Enfermedad e : listaEnfermedades){
			if(nuevaEnfermedad.getName().equals(e.getName())){
				yaExiste = true;
			}
		}
		if(yaExiste){
			System.out.println("La enfermedad ya esta cargada en el Sistema.\n");
		}
		else{
			int choice = -1;
			System.out.println("Ingrese el nombre de un sintoma.");
			nuevaEnfermedad.getSintomas().add(ScannerSingleton.getInstance().nextLine());
			do {
				System.out.println("1. Agregar otro sintoma.\n"
									+ "0. Salir.");
				try {
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
					if(choice==1){
						System.out.println("Ingrese el nombre de un sintoma.");
						nuevaEnfermedad.getSintomas().add(ScannerSingleton.getInstance().nextLine());
					}
				}
				catch(InputMismatchException | NumberFormatException e){
					System.out.println("Ingrese una opcion valida:");
					choice = -1;
				}
			} while (choice != 0);
			listaEnfermedades.add(nuevaEnfermedad);

			System.out.println("La enfermedad " + nuevaEnfermedad.getName() + " fue agregada exitosamente.");
			SerializacionGuardado.serializacion(nombreArchivos.ENFERMEDADES.getName(), listaEnfermedades);
			//guarda la nueva enfermedaden sistema
		}
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
					case 3: List<Tarea> l = Sistema.verListaTareas();
							for(Tarea t: l)
							{
								System.out.println(t.getTaskName());
							}
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
		}
		try
			{
				System.out.println("Ingrese el numero de la tarea a eliminar: ");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine())-1;
				
			}
		catch (InputMismatchException e) {
				System.out.println("Solo valores numericos");
		}
		aux = l.get(choice);
		l.remove(choice);
		
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
				save.add(t);

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
		System.out.println("Ingrese nombre de la tarea a agregar");
		nuevaTarea.setTaskName(ScannerSingleton.getInstance().nextLine());
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
						try {
							p.getTasks().remove(Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1);
						}
						catch(NumberFormatException e){
							System.out.println("Opcion invalida.\n");
						}
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
							 + "2. Modificar plan existente\n"
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
		int a = -1;
		do{
				System.out.println("1. Ingreso de Pacientes.\n"
								 + "2. Ingreso de Profesionales.\n"
								 + "3. Administracion de Enfermedades.\n"
								 + "4. Administracion de Tareas de Control.\n"
								 + "5. Administracion de Planes.\n"
								 + "6. Asignar profesional a paciente.\n"
								 + "0. Salir.\n");
				try {
					a = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				}
				catch (InputMismatchException | NumberFormatException e) {
					a = -1;
				}

			switch (a){
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
					case 6: asignarProfesional();
							break;
					case 0: break;
					default: System.out.println("Ingrese un dato numerico valido");
							break;
				}
		} while (a!=0);
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "ADMINISTRADOR | " + super.toString();
	}
	//------------------------------------------------------------------------------------------------------------------
}
