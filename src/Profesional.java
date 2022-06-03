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
				System.out.println("1. Asignacion de Planes de Control.\n"
								 + "2. Control de los Registros de los Pacientes.\n"
								 + "3. Finalizacion de Planes de Control.\n"
								 + "0. Salir.\n");
				choice = scan.nextInt();
				switch (choice) {
					case 1 -> {
						System.out.println("Ingrese el DNI del paciente a asignar el Plan.\n");
						asignacionPlanesDeControl(scan.nextLine());
					}
					case 2 -> controlRegistrosDePacientes();
					case 3 -> finalizacionPlanesDeControl();
				}
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}
		} while (choice!=0);

		scan.close();
	}

	public void asignacionPlanesDeControl(String dniPaciente) {
		Paciente p =  buscarPaciente(dniPaciente);
		if (p!=null){
			List<Tratamiento> tratamientosPaciente = listarTratamientosPaciente(p);
			mostrarTratamientosPaciente(tratamientosPaciente);
			System.out.println("Seleccione el numero del tratamiento a asignar/modificar.\n");

		}
		else{
			//exception??
		}
	}
	
	public void controlRegistrosDePacientes()
	{
		
	}
	
	public void finalizacionPlanesDeControl()
	{
		
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
						tratamientosPaciente.add(p.getTratamientos().get(i));
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
	public void crearNuevoPlan(Enfermedad e) {
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
