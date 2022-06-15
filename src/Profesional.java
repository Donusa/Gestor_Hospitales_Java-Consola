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
		System.out.println("***************************************************************************************************");
		System.out.println("-NOTIFICACIONES-\n");
		chequearTratamientosSinAsignar();
		tareasIncompletas();
		pacientesEsperandoAlta();
		System.out.println("***************************************************************************************************\n\n");
		do{
			try{
				System.out.println("-- M E N U --\n"
								 + "1. Asignacion o Modificacion de Planes de Control.\n"
								 + "2. Control de los Registros de los Pacientes.\n"
								 + "3. Finalizacion de Planes de Control.\n"
								 + "0. Salir.\n"
								 + "----\n");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				switch (choice) {
					case 1 -> {
						System.out.println("Ingrese el DNI del paciente a asignar el Plan.\n");
						gestionPlanesDeControl(ScannerSingleton.getInstance().nextLine());
					}
					case 2 -> controlRegistrosDePacientes();
					case 3 -> {
						System.out.println("Ingrese el DNI del paciente a finalizar el Plan.\n");
						finalizacionPlanesDeControl(ScannerSingleton.getInstance().nextLine());
					}
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);

		
	}

	public void gestionPlanesDeControl(String dniPaciente) {
		Paciente p =  buscarPaciente(dniPaciente);
		if (p!=null){
			List<Tratamiento> tratamientosPaciente = listarTratamientosPaciente(p);
			mostrarTratamientosPaciente(tratamientosPaciente);
			System.out.println("Seleccione el numero del tratamiento a asignar/modificar.\n");
			Tratamiento auxT = tratamientosPaciente.get(Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1);
			if (auxT.getEstado().equals(EstadoDelTratamiento.SIN_ASIGNAR)){
				asignacionTratamientos(auxT);
			}
			else{
				modificacionTratamientos(auxT);
			}
		}
		else{
			System.out.println("No se encuentra al paciente DNI: " + dniPaciente + " en la lista de pacientes asignados.\n");
		}
		
	}

	public void asignacionTratamientos(Tratamiento t) {
		int choice = 0;

		do {
			try {
				System.out.println("Ingrese una opcion.\n"
						+ "1. Asignar Plan preestablecido para la enfermedad \"" + t.getPlan().getEnfermedad() + "\".\n"
						+ "2. Crear nuevo Plan.\n");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				switch (choice) {
					case 1:
						List<Plan> listaPlanesDefault = Sistema.listarPlanes();
						Plan p = null;
						for(int i=0; i<listaPlanesDefault.size(); i++){
							if(listaPlanesDefault.get(i).getEnfermedad().equals(t.getPlan().getEnfermedad())){
								p = listaPlanesDefault.get(i);	//obtiene el plan cargado desde archivo
								break;
							}
						}
						if(p!=null) {
							System.out.println(p);
							System.out.println("Desea asignar este plan preestablecido?" +
									"\n1. Si" +
									"\n2. No");
							if (Integer.parseInt(ScannerSingleton.getInstance().nextLine()) == 1) {
								t.setPlan(p);
							} else {
								t.setPlan(crearNuevoPlan(t.getPlan().getEnfermedad()));
							}
						}
						else{
							System.out.println("No hay planes preestablecidos para esta enfermedad.\n");
							t.setPlan(crearNuevoPlan(t.getPlan().getEnfermedad()));
						}
						break;
					case 2:
						t.setPlan(crearNuevoPlan(t.getPlan().getEnfermedad()));
						break;
					default:
						System.out.println("Ingrese una opcion valida.\n");
				}
				t.setInicio(LocalDate.now().toString());
				t.setFin();
				t.setEstado(EstadoDelTratamiento.EN_CURSO);
			} catch (InputMismatchException e) {
				System.out.println("Ingrese una opcion valida.\n");
			}
		}while (choice!=1 && choice!=2);

		
	}

	public void modificacionTratamientos(Tratamiento t){
		t.setPlan(modificarPlan(t.getPlan()));
		t.setPlan(modificarPlan(t.getPlan()));
		t.setFin();
	}

	public void controlRegistrosDePacientes() {
		int choice = -1;	
		
		do {
			System.out.println(
					"1. Ver lista de pacientes\n" 
				  + "2. Ver historial de un paciente\n" 
				  + "3. Ver tareas incompletas del dia anterior.\n"
				  + "4. Ver pacientes con planes Sin Asignar.\n"
				  + "0. Salir\n");
			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			} catch (InputMismatchException e) {
				System.out.println(e);
			} 
			
			switch(choice)
			{
			case 1:
				if(!pacientes.isEmpty()) {
					for (String dni : pacientes) {
						System.out.println(buscarPaciente(dni));
					}
				}
				else{
					System.out.println("No hay pacientes asignados.\n");
				}
				break;
			case 2:
				System.out.println("Ingrese DNI del paciente a buscar: ");
				Paciente p = buscarPaciente(ScannerSingleton.getInstance().nextLine());
				if(p!=null && pacientes.contains(p.getUserDni()))
				{
					historialClinico(p);
				}
				else {
					System.out.println("No hay pacientes asignados con ese DNI.\n");
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
	
	private void pacientesEsperandoAlta()
	{
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
	
	private void tareasIncompletas()
	{
		Sistema.userDate.forEach((k,v) -> { //por cada par clave valor hace -> 
			boolean flag = false;
			System.out.println("Tareas incompletas del dia anterior:");
			System.out.println("-------------------------------------------------------------------------");
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
									System.out.println("Paciente: " + p.getUserName() + " | DNI: " + p.getUserDni() +
											" | Tratamiento: " + t.getPlan().getEnfermedad().getName() +
											"\nTareas incompletas:");
									for (Tarea tarea : listaT) {
										System.out.println("\t" + tarea);
									}
									System.out.println("-------------------------------------------------------------------------");
								}
							}
						}
					}
				}
			}
			if(!flag){
				System.out.println("No hubo pacientes con tareas sin realizar.");
				System.out.println("-------------------------------------------------------------------------");
			}
		});
	}
	
	private void historialClinico(Paciente paciente)
	{
		Sistema.userDate.forEach((k, v) ->  //por cada clave valor
		{
			System.out.println(k.toString());
			for(Paciente p : v)
			{
				if(p.getUserDni().equals(paciente.getUserDni())) { //si el paciente pasado coincide devuelve el mismo por pantalla
					System.out.println(p);
				}
				
			}
		});
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
						System.out.println("El paciente DNI:" + auxP.getUserDni() + " tiene tratamientos Sin Asignar.\n");
						flag = true;
					}
				}
			}
			if(!flag){
				System.out.println("Todos los pacientes tienen asignados sus planes.\n");
			}
		}
		else{
			System.out.println("No hay pacientes asignados.\n");
		}
	}

	public void finalizacionPlanesDeControl(String dniPaciente) {
		Paciente p = buscarPaciente(dniPaciente);
		if (p!=null){
			List<Tratamiento> tratamientosPaciente = listarTratamientosPaciente(p);
			if(!tratamientosPaciente.isEmpty()) {
				mostrarTratamientosPaciente(tratamientosPaciente);
				System.out.println("Seleccione el numero del tratamiento a finalizar.\n");
				tratamientosPaciente.get(Integer.parseInt(ScannerSingleton.getInstance().nextLine())-1).setEstado(EstadoDelTratamiento.FINALIZADO);
				System.out.println("Tratamiento finalizado.\n");
				chequearTratamientosPendientes(tratamientosPaciente, dniPaciente);
			}
			else{
				System.out.println("No hay tratamientos con este paciente.\n");
			}
		}
		else{
			System.out.println("No se encuentra al paciente DNI: " + dniPaciente + " en la lista de pacientes asignados.\n");
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
			System.out.println("Lista de Tratamientos con este paciente:\n");
			for(int i=0; i<tratamientosPaciente.size(); i++){
				System.out.println((i+1) + ". " + tratamientosPaciente.get(i));
			}
		}
		else{
			System.out.println("No tiene tratamientos en curso con este paciente.\n");
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
			try{
				System.out.println("1. Modificar duracion.\n"
								+ "2. Agregar tarea.\n"
								+ "3. Borrar tarea.\n"
								+ "0. Salir.\n");
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
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
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);

		
		return p;
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
