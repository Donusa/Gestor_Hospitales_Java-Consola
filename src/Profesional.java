import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;


public class Profesional extends Usuario implements CrearPlan, Menu{
	
	private List<String> pacientes = new ArrayList<>();

	public Profesional() {
	}

	public Profesional(String userName , String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
	}

	@Override
	public void menu() {
		int choice = 0;

		do{
			try{
				System.out.println("1. Asignacion o Modificacion de Planes de Control.\n"
								 + "2. Control de los Registros de los Pacientes.\n"
								 + "3. Finalizacion de Planes de Control.\n"
								 + "0. Salir.\n");
				choice = ScannerSingleton.getInstance().nextInt();
				switch (choice) {
					case 1 -> {
						System.out.println("Ingrese el DNI del paciente a asignar el Plan.\n");
						gestionPlanesDeControl(ScannerSingleton.getInstance().nextLine());
					}
					case 2 -> controlRegistrosDePacientes();
					case 3 -> {
						System.out.println("Ingrese el DNI del paciente a asignar el Plan.\n");
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
			Tratamiento auxT = tratamientosPaciente.get(ScannerSingleton.getInstance().nextInt() - 1);
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
				choice = ScannerSingleton.getInstance().nextInt();
				switch (choice) {
					case 1:
						List<Plan> listaPlanesDefault = Sistema.listarPlanes();
						Plan p = null;
						for(int i=0; i<listaPlanesDefault.size(); i++){
							if(listaPlanesDefault.get(i).getEnfermedad().equals(t.getPlan().getEnfermedad())){
								p = listaPlanesDefault.get(i);
								break;
							}
						}
						if(p!=null) {
							System.out.println(p);
							System.out.println("Desea asignar este plan preestablecido?" +
									"\n1. Si" +
									"\n2. No");
							if (ScannerSingleton.getInstance().nextInt() == 1) {
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
		// falta ver que va aca
	}
	
	public void finalizacionPlanesDeControl(String dniPaciente) {
		Paciente p = buscarPaciente(dniPaciente);
		if (p!=null){
			List<Tratamiento> tratamientosPaciente = listarTratamientosPaciente(p);
			if(!tratamientosPaciente.isEmpty()) {
				mostrarTratamientosPaciente(tratamientosPaciente);
				System.out.println("Seleccione el numero del tratamiento a finalizar.\n");
				tratamientosPaciente.get(ScannerSingleton.getInstance().nextInt()).setEstado(EstadoDelTratamiento.FINALIZADO);
			}
			else{
				System.out.println("No hay tratamientos con este paciente.\n");
			}
		}
		else{
			System.out.println("No se encuentra al paciente DNI: " + dniPaciente + " en la lista de pacientes asignados.\n");
		}
		
	}

	public Paciente buscarPaciente(String dniPaciente){
		Paciente p = null;
		int i = 0, j = 0;
		
		while(i < pacientes.size() && !(dniPaciente.equals(pacientes.get(i))))
		{
			i++;
		}
		
		if(dniPaciente.equals(pacientes.get(i))) {
			while(j < Sistema.users.size() && !(Sistema.users.get(j).getUserDni().equals(pacientes.get(i))))
			{
				j++;
			}
			
			if(Sistema.users.get(j).getUserDni().equals(pacientes.get(i)))
			{
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
				System.out.println(tratamientosPaciente.get(i));
			}
		}
		else{
			System.out.println("No tiene tratamientos en curso con este paciente.\n");
		}
	}

	public List<String> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<String> pacientes) {
		this.pacientes = pacientes;
	}

	@Override
	public Plan crearNuevoPlan(Enfermedad e) {
		Plan p= new Plan(e);
		System.out.println("Ingrese la duracion del Plan:");
		p.setDuracion(ScannerSingleton.getInstance().nextInt());
		int rta =2;
		do {
			try {
				p.agregarTarea();
				System.out.println("Desea agregar otra tarea?\n"+
								"1. Si\n"+
								"2. No");
				rta=ScannerSingleton.getInstance().nextInt();

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
				choice = ScannerSingleton.getInstance().nextInt();
				switch (choice) {
					case 1 :
						System.out.println("Ingrese la nueva duracion:");
						p.setDuracion(ScannerSingleton.getInstance().nextInt());
						break;
					case 2 :
						p.agregarTarea();
						break;
					case 3 :
						p.mostrarTareas();
						System.out.println("Ingrese el numero de tarea que quieras eliminar");
						p.getTasks().remove(ScannerSingleton.getInstance().nextInt()-1);
						break;
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);

		
		return p;
	}

	@Override
	public String toString() {
		return "PROFESIONAL | " + super.toString() +
				"\nLista de Pacientes asignados:\n" + pacientes;
	}
}
