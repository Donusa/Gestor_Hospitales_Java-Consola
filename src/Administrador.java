import java.time.LocalDate;
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
	@Override
	public void menu() {
		int a = -1;
		do{
			System.out.println("\n-- MENU ADMINISTRADOR --\n"
								+ "1. Ingreso de Pacientes.\n"
								+ "2. Ingreso de Profesionales.\n"
								+ "3. Asignar profesional a paciente.\n"
								+ "4. Administracion de Enfermedades.\n"
								+ "5. Administracion de Tareas de Control.\n"
								+ "6. Administracion de Planes.\n"
								+ "0. Salir.\n"
								+ "----");
			try {
				a = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			}
			catch (InputMismatchException | NumberFormatException e) {
				a = -1;
			}

			switch (a){
				case 1: System.out.println("\n-- Ingreso de Pacientes --");
						ingresoPacientes();
						break;
				case 2: System.out.println("\n-- Ingreso de Profesionales --");
						ingresoProfesionales();
						break;
				case 3: System.out.println("-- Asignar Profesional a un Paciente --");
						asignarProfesional();
						break;
				case 4:	administracionEnfermedades();
						break;
				case 5: administracionTareasDeControl();
						break;
				case 6: administracionPlanes();
						break;
				case 0: break;
				default: System.out.println("Ingrese un dato numerico valido.");
					break;
			}
		} while (a!=0);
	}

	private void ingresoPacientes() {
		Paciente p = new Paciente();
		if(Usuario.crearUser(p)) {		//Envia paciente vacio a funcion, solo carga datos comunes de usuario(padre)
			Sistema.users.add(p);    	//agrega a la lista general, esta se guarda al finalizar el programa y persiste los cambios
			System.out.println("Paciente registrado correctamente.");
			List<Paciente> aux = Sistema.userDate.get(LocalDate.now());
			aux.add(p);
			Sistema.userDate.put(LocalDate.now(), aux);
		}
		
	}

	private void ingresoProfesionales(){	//Igual que con la funcion ingresoPaciente, este ingresa un profesional al sistema
		Profesional p = new Profesional();
		if(Usuario.crearUser(p)) {			//Se crea un nuevo profesional asignandole los valores comunes a todos los usuarios
			Sistema.users.add(p);			//se asigna a la lista de usuarios que luego es persistida al finalizar el programa
			System.out.println("Profesional registrado correctamente.");
		}
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
			for(Usuario u : Sistema.users)
			{
				if(u instanceof Profesional && u.equals(profesional))
				{
					((Profesional)u).getPacientes().add(paciente.getUserDni());
				}
			}
			//y se le asigna junto a un nuevo tratamiento al paciente previamente encontrado
			System.out.println("Paciente DNI " + paciente.getUserDni() + " asignado al Profesional DNI " + profesional.userDni);
		}
	}
	
	private void administracionEnfermedades() //funcion que tiene el alta/baja/modificacion de las enfermedades
	{
		int choice = 0;

		do{
			
				System.out.println("\n-- ADMINISTRACION DE ENFERMEDADES -- \n"
								+ "1. Agregar enfermedad.\n"
								+ "2. Eliminar enfermedad.\n"
								+ "3. Ver lista de enfermedades.\n"
								+ "0. Salir.\n"
								+ "----");
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
					case 3: mostrarListaEnfermedades();//muestra la lista de enfermedades levantada del archivo
						break;
				}
			
		} while (choice!=0);
		
	}

	private void nuevaEnfermedad() {
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

	private void eliminarEnfermedad() {
		List<Enfermedad> listaEnfermedades = Sistema.verListaEnfermedades();	//levanta la lista del json
		int choice=-1;
		boolean repeat;

		if(!listaEnfermedades.isEmpty()) {
			for (int i = 0; i < listaEnfermedades.size(); i++) {
				System.out.println((i + 1) + "." + listaEnfermedades.get(i).getName());
			}
			System.out.println("Ingrese el numero de la enfermedad a eliminar: ");
			do {
				repeat = false;
				try {
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
					listaEnfermedades.remove(choice);
					System.out.println("Enfermedad eliminada correctamente.\n");
					SerializacionGuardado.serializacion(nombreArchivos.ENFERMEDADES.getName(), listaEnfermedades);
				} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
					System.out.println("Error. Ingrese un numero valido:");
					repeat = true;
				}
			} while(repeat);
		}
		else{
			System.out.println("No hay ninguna enfermedad cargada en el Sistema.");
		}
	}

	private void mostrarListaEnfermedades(){
		List<Enfermedad> listaEnfermedades = Sistema.verListaEnfermedades();
		System.out.println("LISTA DE ENFERMEDADES");
		System.out.println("---------------------");
		for(int i = 0; i < listaEnfermedades.size(); i++){
			System.out.println((i+1) + ". " + listaEnfermedades.get(i));
		}
	}

	private void administracionTareasDeControl() //Alta/baja de tareas
	{
		int choice = 0;

		do{
			
				System.out.println("\n-- ADMINISTRACION TAREAS DE CONTROL -- \n"
								 + "1. Agregar tarea.\n"
								 + "2. Eliminar tarea.\n"
								 + "3. Ver lista de tareas.\n"
								 + "0. Salir.\n"
								 + "----");
				try{
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				}
				catch(InputMismatchException e){
					System.out.println("Ingrese una opcion valida.\n");
				}
				switch (choice){
					case 1:	Tarea.nuevaTarea();
							break;
					case 2: Tarea.eliminarTarea();
							break;
					case 3: Tarea.mostrarListaTareas();
							break;
				}
			
		} while (choice!=0);
		
	}

	private void administracionPlanes() {
		int choice;
		List<Plan> save = Sistema.listarPlanes();
		do{
			System.out.println("\n-- ADMINISTRACION DE PLANES DE CONTROL --\n"
								+ "1. Crear nuevo plan.\n"
								+ "2. Modificar plan existente\n"
								+ "3. Ver todos los planes precargados.\n"
								+ "0. Salir\n"
								+ "----");

			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			} catch (InputMismatchException | NumberFormatException e) {
				choice=-1;
			}
			switch (choice){
				case 1: Enfermedad enfermedad = Sistema.seleccionarEnfermedad();
					save.add(crearNuevoPlan(enfermedad));
					break;
				case 2: Plan plan = Sistema.seleccionarPlan();
						
					for(int i = 0 ; i < save.size(); i ++){
						if (save.get(i).getEnfermedad().getName().equals(plan.getEnfermedad().getName())
								&& save.get(i).getTasks().toString().equals(plan.getTasks().toString())) {
							System.out.println("Ingrese numero de plan: \n");
							plan = modificarPlan(plan);
							save.set(i, plan);
						}
						}
					break;
				case 3:	mostrarListaPlanes();
					break;
				case 0: break;
				default:
					System.out.println("Ingrese un numero valido.");
					break;
			}
		} while (choice!=0);

		SerializacionGuardado.serializacion(nombreArchivos.PLANES.getName(), save);
	}

	@Override
	public Plan crearNuevoPlan(Enfermedad e) {
		int choice = 1;
		Plan p= new Plan(e);
		System.out.println("\nIngrese la duracion del Plan:");
		p.setDuracion(Integer.parseInt(ScannerSingleton.getInstance().nextLine()));

		do{
			try {
				if(choice==1) {
					p.agregarTareaAlPlan();
					System.out.println("\nDesea agregar otra tarea?\n"
							+ "1. Si\n"
							+ "2. No");
				}
				else if (choice!=2){
					System.out.println("Ingrese una opcion valida.");
				}
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			}
			catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException exception){
				choice = 0;
			}
		} while(choice!=2);
		System.out.println("El Plan ha sido creado.");
		return p;
	}

	@Override
	public Plan modificarPlan(Plan p){
		int choice = 0;

		do{
			
				System.out.println("\n-- MODIFICACION DE PLANES --\n"
								+ "Plan seleccionado: " + p.getEnfermedad().getName() + "\n\n"
								+ "1. Modificar duracion.\n"
								+ "2. Agregar tarea.\n"
								+ "3. Borrar tarea.\n"
								+ "0. Salir.\n"
								+ "----");
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
						System.out.println("Duracion modificada.");
						break;
					case 2 :
						p.agregarTareaAlPlan();
						break;
					case 3 :
						p.mostrarTareasDelPlan();
						System.out.println("\nIngrese el numero de la tarea a eliminar.");
						try {
							p.getTasks().remove(Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1);
						}
						catch(NumberFormatException | InputMismatchException | IndexOutOfBoundsException e){
							System.out.println("Opcion invalida.\n");
						}
						break;
				}
			
		} while (choice!=0);
		return p;
	}

	private void mostrarListaPlanes(){
		List<Plan> listaPlanes = Sistema.listarPlanes();
		System.out.println("LISTA DE PLANES:\n"
						 + "----------------");
		for(int i = 0; i < listaPlanes.size(); i++){
			System.out.println((i+1) + ". " + listaPlanes.get(i));
		}
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "ADMINISTRADOR | " + super.toString();
	}
	//------------------------------------------------------------------------------------------------------------------
}
