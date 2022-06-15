import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;


public class Profesional extends Usuario implements CrearPlan, Menu{
	//---Atributos------------------------------------------------------------------------------------------------------
	private List<String> pacientes = new ArrayList<>();
	//------------------------------------------------------------------------------------------------------------------


	//---Constructores--------------------------------------------------------------------------------------------------
	public Profesional() {
	}

	public Profesional(String userName , String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Getters y Setters----------------------------------------------------------------------------------------------
	public List<String> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<String> pacientes) {
		this.pacientes = pacientes;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Metodos--------------------------------------------------------------------------------------------------------
	@Override
	public void menu() {
		int choice = -1;
		System.out.println("******************************\n"
							+ "\t  NOTIFICACIONES\n"
							+ "******************************");
		chequearTratamientosSinAsignar();
		tareasIncompletas();
		pacientesEsperandoAlta();
		do{
			try{
				System.out.println("\n-- MENU PROFESIONAL --\n"
								 + "1. Asignacion o Modificacion de Planes de Control.\n"
								 + "2. Control de los Registros de los Pacientes.\n"
								 + "3. Finalizacion de Planes de Control.\n"
								 + "0. Salir.\n"
								 + "----");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				switch (choice) {
					case 1 -> {
							System.out.println("\n-- ASIGNACION O MODIFICACION DE PLANES --\n");
							mostrarListaPacientes();
							System.out.println("\nIngrese el DNI del paciente a asignar el Plan.");
							gestionPlanesDeControl(ScannerSingleton.getInstance().nextLine());
					}
					case 2 -> controlRegistrosDePacientes();
					case 3 -> {
							System.out.println("Ingrese el DNI del paciente a finalizar el Plan.");
							finalizacionPlanesDeControl(ScannerSingleton.getInstance().nextLine());
					}
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.");
			}
		} while (choice!=0);
	}

	public void gestionPlanesDeControl(String dniPaciente) {
		Paciente p =  buscarPaciente(dniPaciente);
		if (p!=null){
			List<Tratamiento> tratamientosPaciente = listarTratamientosPaciente(p);
			mostrarTratamientosPaciente(tratamientosPaciente);
			System.out.println("\nSeleccione el numero del tratamiento a asignar/modificar.");
			Tratamiento auxT = tratamientosPaciente.get(Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1);
			if (auxT.getEstado().equals(EstadoDelTratamiento.SIN_ASIGNAR)){
				asignacionTratamientos(auxT);
				auxT.setInicio(LocalDate.now().toString());
			}
			else{
				modificarPlan(auxT.getPlan());
			}
			auxT.setFin();
		}
		else{
			System.out.println("No se encontro al paciente DNI: " + dniPaciente + " en la lista de pacientes asignados.");
		}
		
	}

	public void asignacionTratamientos(Tratamiento t) {
		int choice = 0;

		do {
			try {
				System.out.println("\n-- ASIGNACION DE PLANES DE CONTROL --\n"
						+ "Tratamiento: " + t.getPlan().getEnfermedad().getName() + "\n"
						+ "1. Asignar Plan preestablecido para la enfermedad\n"
						+ "2. Crear nuevo Plan.\n"
						+ "----");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				switch (choice) {
					case 1:
						List<Plan> listaPlanesDefault = Sistema.listarPlanes();
						Plan p = null;
						for(int i=0; i<listaPlanesDefault.size(); i++){
							if(listaPlanesDefault.get(i).getEnfermedad().getName().equals(t.getPlan().getEnfermedad().getName())){
								p = listaPlanesDefault.get(i);	//obtiene el plan cargado desde archivo
								break;
							}
						}
						if(p!=null) {
							System.out.println(p);
							System.out.println("Desea asignar este plan preestablecido?\n"
									+ "1. Si\n"
									+ "2. No");
							if (Integer.parseInt(ScannerSingleton.getInstance().nextLine()) == 1) {
								t.setPlan(p);
								System.out.println("Plan asignado.");
							} else {
								t.setPlan(crearNuevoPlan(t.getPlan().getEnfermedad()));
							}
						}
						else{
							System.out.println("No hay planes preestablecidos para esta enfermedad.");
							t.setPlan(crearNuevoPlan(t.getPlan().getEnfermedad()));
						}
						break;
					case 2:
						t.setPlan(crearNuevoPlan(t.getPlan().getEnfermedad()));
						break;
					default:
						System.out.println("Ingrese una opcion valida.");
				}
				t.setEstado(EstadoDelTratamiento.EN_CURSO);
			} catch (InputMismatchException e) {
				System.out.println("Ingrese una opcion valida.");
			}
		}while (choice!=1 && choice!=2);
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
			try{
				System.out.println("\n-- MODIFICACION DE PLANES --\n"
						+ "Tratamiento: " + p.getEnfermedad().getName() + "\n"
						+ "1. Modificar duracion.\n"
						+ "2. Agregar tarea.\n"
						+ "3. Borrar tarea.\n"
						+ "0. Salir.\n"
						+ "----");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				switch (choice) {
					case 1 :
						System.out.println("Ingrese la nueva duracion:");
						p.setDuracion(Integer.parseInt(ScannerSingleton.getInstance().nextLine()));
						break;
					case 2 :
						p.agregarTareaAlPlan();
						break;
					case 3 :
						p.mostrarTareasDelPlan();
						System.out.println("Ingrese el numero de tarea que quieras eliminar");
						p.getTasks().remove(Integer.parseInt(ScannerSingleton.getInstance().nextLine())-1);
						break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.");
			}
		} while (choice!=0);

		return p;
	}

	public void controlRegistrosDePacientes() {
		int choice = -1;	
		
		do {
			System.out.println("\n-- CONTROL REGISTROS DE PACIENTES --\n"
								+ "1. Ver lista de pacientes\n"
								+ "2. Ver historial de un paciente\n"
								+ "3. Ver tareas incompletas del dia anterior.\n"
								+ "4. Ver pacientes con planes Sin Asignar.\n"
								+ "0. Salir\n"
								+ "----");
			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			} catch (InputMismatchException e) {
				System.out.println(e);
			} 
			
			switch(choice)
			{
			case 1:
				mostrarListaPacientes();
				break;
			case 2:
				System.out.println("Ingrese DNI del paciente a buscar: ");
				Paciente p = buscarPaciente(ScannerSingleton.getInstance().nextLine());
				if(p!=null && pacientes.contains(p.getUserDni()))
				{
					historialClinico(p);
				}
				else {
					System.out.println("No hay pacientes asignados con ese DNI.");
				}
				break;
			case 3:
				tareasIncompletas();
				break;
			case 4:
				chequearTratamientosSinAsignar();
				break;
			case 0: break;
			default: System.out.println("Ingrese un dato valido");
				break; 
			}
		} while (choice != 0);
	}

	private void mostrarListaPacientes(){
		Paciente paciente;
		if(!pacientes.isEmpty()) {
			System.out.println("LISTA DE PACIENTES\n"
							 + "------------------");
			for (int i=0; i<pacientes.size(); i++) {
				paciente = buscarPaciente(pacientes.get(i));
				System.out.println((i+1) + ". Paciente: " + paciente.getUserName() + " | DNI: " + paciente.getUserDni());
			}
		}
		else{
			System.out.println("No hay pacientes asignados.");
		}
	}
	
	private void pacientesEsperandoAlta() {
		for(Usuario u : Sistema.users)
		{
			if(u instanceof Paciente
					&& pacientes.contains(u.getUserDni()))
			{
				for(Tratamiento t : ((Paciente)u).getTratamientos())
				{
					if(t.getEstado().equals(EstadoDelTratamiento.ESPERANDO_ALTA))
					{
						System.out.println("Paciente "+u.getUserName()+ " de DNI : "+u.getUserDni()+" esperando alta");
					}
				}
			}
		}
	}

	private void tareasIncompletas() {
		Sistema.userDate.forEach((k,v) -> { //por cada par clave valor hace ->
			boolean flag = false;
			StringBuilder sb = new StringBuilder();
			sb.append("Tareas incompletas del dia anterior:\n");
			if(k.isEqual(LocalDate.now().minusDays(1))){
				for(Paciente p : v) {
					if (pacientes.contains(p.getUserDni())) {
						for (Tratamiento t : p.getTratamientos()) { //ciclos para llegar a las listas contenidas en listas.
							if (t.getProfesionalEncargado().equals(this.userName)
									&& !t.getEstado().equals(EstadoDelTratamiento.ESPERANDO_ALTA))
							 	{						//Obtiene el profesional encargado de dicha tarea y compara
								List<Tarea> listaT = new ArrayList<>();					// con el profesional logueado
								for (Tarea tarea : t.getPlan().getTasks()) {
									if (!tarea.isTaskDone()) {
										listaT.add(tarea);
										flag = true;
									}
								}
								if (!listaT.isEmpty()) {
									sb.append("Paciente: " + p.getUserName() + " | DNI: " + p.getUserDni()
											+ " | Tratamiento: " + t.getPlan().getEnfermedad().getName()
											+ "\nTareas incompletas:");
									for (Tarea tarea : listaT) {
										sb.append("\t" + tarea);
									}
								}
							}
						}
					}
				}
			}
			if(flag){
				System.out.println(sb);
			}
			else{
				System.out.println("No hubo pacientes con tareas sin realizar el dia anterior.");
			}
		});
	}
	
	private void historialClinico(Paciente paciente)
	{
		int choice = -1;
		
		System.out.println("1. Ver historial completo del paciente"
				+ "2. Ver historial de un tratamiento especifico"
				+ "0. Salir");
		
		choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
		
		switch(choice) {
		case 1 :
			Sistema.userDate.forEach((k, v) ->  //por cada clave valor
			{
				System.out.println(k.toString());
				for(Paciente p : v) {
					if(paciente.getUserDni().equals(p.getUserDni())) {
						System.out.println(p.getTratamientos()+"\n");
					}
				}
				
			});
			break;
		case 2:
			System.out.println("Ingrese fecha del tratamiento : (Formato YYYY-MM-DD)");
			try {
				String choiceB = ScannerSingleton.getInstance().nextLine();
				System.out.println(Sistema.userDate.get(LocalDate.parse(choiceB)));
			} catch (Exception e) {
				System.out.println("Datos no validos");
			}
			break;
			
		}
	}

	
	private void chequearTratamientosSinAsignar(){
		if(!pacientes.isEmpty()) {
			boolean flag = false;
			for (String dni : pacientes) {
				Paciente auxP = buscarPaciente(dni);
				List<Tratamiento> tratamientosPaciente = listarTratamientosPaciente(auxP);
				if(!tratamientosPaciente.isEmpty()){
					int i = 0;
					while(i < tratamientosPaciente.size() && !(tratamientosPaciente.get(i).getEstado().equals(EstadoDelTratamiento.SIN_ASIGNAR))){
						i++;
					}
					if(i < tratamientosPaciente.size()){
						System.out.println("El paciente DNI:" + auxP.getUserDni() + " tiene tratamientos Sin Asignar.");
						flag = true;
					}
				}
			}
			if(!flag){
				System.out.println("Todos los pacientes tienen asignados sus planes.");
			}
		}
		else{
			System.out.println("No hay pacientes asignados.");
		}
	}

	public void finalizacionPlanesDeControl(String dniPaciente) {
		Paciente p = buscarPaciente(dniPaciente);
		if (p!=null){
			List<Tratamiento> tratamientosPaciente = listarTratamientosPaciente(p);
			if(!tratamientosPaciente.isEmpty()) {
				mostrarTratamientosPaciente(tratamientosPaciente);
				System.out.println("Seleccione el numero del tratamiento a finalizar.");
				tratamientosPaciente.get(Integer.parseInt(ScannerSingleton.getInstance().nextLine())-1).setEstado(EstadoDelTratamiento.FINALIZADO);
				System.out.println("Tratamiento finalizado.");
				chequearTratamientosPendientes(tratamientosPaciente, dniPaciente);
			}
			else{
				System.out.println("No hay tratamientos con este paciente.");
			}
		}
		else{
			System.out.println("No se encuentra al paciente DNI: " + dniPaciente + " en la lista de pacientes asignados.");
		}
	}

	private void chequearTratamientosPendientes(List<Tratamiento> tratamientosPaciente, String dniPaciente){
		int cont=0;
		for(Tratamiento t : tratamientosPaciente){
			if(t.getEstado().equals(EstadoDelTratamiento.FINALIZADO)){
				cont++;
			}
		}
		if(cont==tratamientosPaciente.size()){
			pacientes.remove(dniPaciente);
		}
	}

	public Paciente buscarPaciente(String dniPaciente){
		Paciente p = null;
		int i = 0, j = 0;
		
		while(i < pacientes.size() && !(dniPaciente.equals(pacientes.get(i))))
		{
			i++;
		}
		if(i< pacientes.size()){
			while(j < Sistema.users.size() && !(Sistema.users.get(j).getUserDni().equals(pacientes.get(i))))
			{
				j++;
			}
			if(j < Sistema.users.size()){
				p = (Paciente)Sistema.users.get(j);
			}
		}

		return p;
	}

	public List<Tratamiento> listarTratamientosPaciente(Paciente p){
		List<Tratamiento> tratamientosPaciente = new ArrayList<>();
		if(p!=null){
			if(!p.getTratamientos().isEmpty()){
				for(int i=0; i<p.getTratamientos().size(); i++){
					if(p.getTratamientos().get(i).getProfesionalEncargado().equals(this.userName)){
						if(!(p.getTratamientos().get(i).getEstado().equals(EstadoDelTratamiento.FINALIZADO))) {
							tratamientosPaciente.add(p.getTratamientos().get(i));
						}
					}
				}
			}
		}
		return tratamientosPaciente;
	}

	public void mostrarTratamientosPaciente(List<Tratamiento> tratamientosPaciente){
		if(!tratamientosPaciente.isEmpty()){
			System.out.println("\nLista de Tratamientos con este paciente\n"
							 + "---------------------------------------");
			for(int i=0; i<tratamientosPaciente.size(); i++){
				System.out.println((i+1) + ". " + tratamientosPaciente.get(i));
			}
		}
		else{
			System.out.println("No tiene tratamientos en curso con este paciente.");
		}
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		return "PROFESIONAL | " + super.toString() +
				"\nLista de Pacientes asignados:\n" + pacientes;
	}
	//------------------------------------------------------------------------------------------------------------------
}
