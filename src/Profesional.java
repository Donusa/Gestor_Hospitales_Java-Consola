import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Profesional extends Usuario implements CrearPlan, Menu{
	
	private List<Paciente> pacientes = new ArrayList<>();

	public Profesional() {
	}

	public Profesional(String userName , String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		
	}

	@Override
	public void menu() {
		int choice = 0;
		Scanner scan =  new Scanner(System.in);

		do{
			try{
				System.out.println("1. Asignacion o Modificacion de Planes de Control.\n"
								 + "2. Control de los Registros de los Pacientes.\n"
								 + "3. Finalizacion de Planes de Control.\n"
								 + "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice) {
					case 1 -> {
						System.out.println("Ingrese el DNI del paciente a asignar el Plan.\n");
						gestionPlanesDeControl(scan.nextLine());
					}
					case 2 -> controlRegistrosDePacientes();
					case 3 -> {
						System.out.println("Ingrese el DNI del paciente a asignar el Plan.\n");
						finalizacionPlanesDeControl(scan.nextLine());
					}
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);

		scan.close();
	}

	public void gestionPlanesDeControl(String dniPaciente) {
		Paciente p =  buscarPaciente(dniPaciente);
		Scanner scan =  new Scanner(System.in);
		if (p!=null){
			List<Tratamiento> tratamientosPaciente = listarTratamientosPaciente(p);
			mostrarTratamientosPaciente(tratamientosPaciente);
			System.out.println("Seleccione el numero del tratamiento a asignar/modificar.\n");
			Tratamiento auxT = tratamientosPaciente.get(scan.nextInt() - 1);
			if (auxT.estado.equals(EstadoDelTratamiento.SIN_ASIGNAR)){
				asignacionTratamientos(auxT);
			}
			else{
				modificacionTratamientos(auxT);
			}
		}
		else{
			System.out.println("No se encuentra al paciente DNI: " + dniPaciente + " en la lista de pacientes asignados.\n");
		}
		scan.close();
	}

	public void asignacionTratamientos(Tratamiento t) {
		Scanner scan = new Scanner(System.in);
		int choice = 0;

		do {
			try {
				System.out.println("Ingrese una opcion.\n"
						+ "1. Asignar Plan preestablecido para la enfermedad \"" + t.plan.getEnfermedad() + "\".\n"
						+ "2. Crear nuevo Plan.\n");
				choice = scan.nextInt();
				switch (choice) {
					case 1:
						//buscar la lista de planes y mostrarle el preestablecido de la enfermedad e
						break;
					case 2:
						t.setPlan(crearNuevoPlan(t.plan.getEnfermedad()));
						break;
					default:
						System.out.println("Ingrese una opcion valida.\n");
				}
				t.setInicio(LocalDate.now());
				t.setFin();
				t.setEstado(EstadoDelTratamiento.EN_CURSO);
			} catch (InputMismatchException e) {
				System.out.println("Ingrese una opcion valida.\n");
			}
		}while (choice!=1 && choice!=2);

		scan.close();
	}

	public void modificacionTratamientos(Tratamiento t){
		t.setPlan(modificarPlan(t.getPlan()));
		t.setPlan(modificarPlan(t.plan));
		t.setFin();
	}

	public void controlRegistrosDePacientes() {
		// falta ver que va aca
	}
	
	public void finalizacionPlanesDeControl(String dniPaciente) {
		Paciente p = buscarPaciente(dniPaciente);
		Scanner scan = new Scanner(System.in);
		if (p!=null){
			List<Tratamiento> tratamientosPaciente = listarTratamientosPaciente(p);
			mostrarTratamientosPaciente(tratamientosPaciente);
			System.out.println("Seleccione el numero del tratamiento a finalizar.\n");
			tratamientosPaciente.get(scan.nextInt()).setEstado(EstadoDelTratamiento.FINALIZADO);
		}
		else{
			System.out.println("No se encuentra al paciente DNI: " + dniPaciente + " en la lista de pacientes asignados.\n");
		}
		scan.close();
	}

	public Paciente buscarPaciente(String dniPaciente){
		if(!pacientes.isEmpty()) {
			for (int i = 0; i < pacientes.size(); i++) {
				if (dniPaciente.equals(pacientes.get(i).userDni)) {
					Paciente p = pacientes.get(i);
					return p;
				}
			}
		}
		return null;
	}

	public List<Tratamiento> listarTratamientosPaciente(Paciente p){
		List<Tratamiento> tratamientosPaciente = new ArrayList<>();
		if(p!=null){
			if(!p.getTratamientos().isEmpty()){
				for(int i=0; i<p.getTratamientos().size(); i++){
					if(p.getTratamientos().get(i).profesionalEncargado.equals(this)){
						if(!(p.getTratamientos().get(i).estado.equals(EstadoDelTratamiento.FINALIZADO))) {
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

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	@Override
	public Plan crearNuevoPlan(Enfermedad e) {
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
	public Plan modificarPlan(Plan p){
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
						p.tasks.remove(scan.nextInt()-1);
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

}
