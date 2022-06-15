import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Paciente extends Usuario implements Menu{
	//---Atributos------------------------------------------------------------------------------------------------------
	private List<Tratamiento> tratamientos = new ArrayList<>();
	//------------------------------------------------------------------------------------------------------------------


	//---Constructores--------------------------------------------------------------------------------------------------
	public Paciente() {
	}

	public Paciente(Enfermedad e, String profesionalEncargado, String userName, String email, String password, String userDni, String userCel) {
		super(userName,email,password,userDni,userCel);
		tratamientos.add(new Tratamiento(profesionalEncargado, e));
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Getters y Setters----------------------------------------------------------------------------------------------
	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Metodos--------------------------------------------------------------------------------------------------------
	@Override
	public void menu() {
		int choice;
		System.out.println("******************************\n"
							+ "\t\tNOTIFICACIONES\n"
							+ "******************************");
		notificacionesTareasSinRealizar();
		do{
			System.out.println("\n-- MENU PACIENTE --\n"
								+ "1. Marcar o Modificar informacion las Tareas del dia.\n"
								+ "2. Desmarcar una o varias Tareas del dia.\n"
								+ "3. Ver tratamientos en curso.\n"
								+ "4. Listar todos los tratamientos.\n"
								+ "0. Salir.\n"
								+ "----");
			try{
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());

				switch(choice)
				{
					case 1:
						marcarModificarTareas();	//falta realizar la persistencia
						break;
					case 2:
						desmarcarTareas();			// falta realizar la persistencia
						break;
					case 3:
						mostrarTratamientosEnCurso();
						break;
					case 4:
						mostrarTratamientos();
						break;
					case 0: break;
					default: System.out.println("Error. Ingrese una opcion valida.");
						break;
				}
			}
			catch(InputMismatchException | NumberFormatException e){
				System.out.println("Error. Ingrese una opcion valida.");
				choice = -1;
			}
		} while (choice!=0);
	}

	public void marcarModificarTareas(){
		List<Tratamiento> tratEnCurso = new ArrayList<>();
		int choice;
		boolean repeat;

		System.out.println("-- Marcar o Modificar Tareas --");
		for(Tratamiento t : tratamientos){
			if(t.getEstado().equals(EstadoDelTratamiento.EN_CURSO)){
				tratEnCurso.add(t);
			}
		}
		if(tratEnCurso.size()>1){
			System.out.println("Seleccione un tratamiento:");
			mostrarTratamientosEnCurso();
			do {
				repeat = false;
				try {
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
					System.out.println("-- Marcar o Modificar Tareas --");
					System.out.println("Tratamiento: " + tratEnCurso.get(choice).getPlan().getEnfermedad().getName());
					marcarTareasDeUnPlan(tratEnCurso.get(choice).getPlan());
				} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
					System.out.println("Error. Ingrese un numero de tratamiento valido.");
					repeat = true;
				}
			} while(repeat);
		}
		else{
			System.out.println("Tratamiento: " + tratEnCurso.get(0).getPlan().getEnfermedad().getName());
			marcarTareasDeUnPlan(tratEnCurso.get(0).getPlan());
		}
	}

	public void marcarTareasDeUnPlan(Plan p){;
		int numTarea;
		int choice = 1;
		do{
			if(choice==1){
				System.out.println("Seleccione una tarea para marcar o modificar.");
				p.mostrarTareas();
				do {
					try {
						numTarea = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
						p.getTasks().get(numTarea).setTaskDone(true);
						if (p.getTasks().get(numTarea) instanceof TareaNumerica) {    // si elijo una tarea numerica
							System.out.println("Ingrese el numero requerido:");
							((TareaNumerica) p.getTasks().get(numTarea)).setNumero(Integer.parseInt(ScannerSingleton.getInstance().nextLine()));
						} else if (p.getTasks().get(numTarea) instanceof TareaAlfanumerica) {    // si elijo una tarea alfanumerica
							System.out.println("Ingrese la informacion requerida:");
							((TareaAlfanumerica) p.getTasks().get(numTarea)).setInfo(ScannerSingleton.getInstance().nextLine());
						}
						choice=0;
					} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
						System.out.println("Error. Ingrese una opcion valida.");
					}
				} while(choice==1);
				System.out.println("Desea marcar o modificar otra tarea?\n"
									+ "1. Si\n"
									+ "2. No");
			}
			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				if(choice!=1 && choice!=2){
					System.out.println("Ingrese una opcion valida.");
				}
			}
			catch(InputMismatchException | NumberFormatException e){
				System.out.println("Ingrese una opcion valida.");
				choice=0;
			}
		} while(choice!=2);
	}

	public void desmarcarTareas(){
		List<Tratamiento> tratEnCurso = new ArrayList<>();
		int choice;
		boolean repeat;

		System.out.println("\n-- Desmarcar Tareas --");
		for(Tratamiento t : tratamientos){
			if(t.getEstado().equals(EstadoDelTratamiento.EN_CURSO)){
				tratEnCurso.add(t);
			}
		}
		if(tratEnCurso.size()>1){
			System.out.println("Seleccione un tratamiento:");
			mostrarTratamientosEnCurso();
			do {
				repeat = false;
				try {
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
					System.out.println("\n-- Desmarcar Tareas --");
					System.out.println("Tratamiento: " + tratEnCurso.get(choice).getPlan().getEnfermedad().getName());
					desmarcarTareasDeUnPlan(tratEnCurso.get(choice).getPlan());
				} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
					System.out.println("Error. Ingrese un numero de tratamiento valido.");
					repeat = true;
				}
			} while(repeat);
		}
		else{
			System.out.println("Tratamiento: " + tratEnCurso.get(0).getPlan().getEnfermedad().getName());
			desmarcarTareasDeUnPlan(tratEnCurso.get(0).getPlan());
		}
	}

	public void desmarcarTareasDeUnPlan(Plan p){
		int numTarea;
		int choice = 1;
		do{
			if(choice==1){
				System.out.println("Seleccione una tarea para desmarcarla.");
				p.mostrarTareas();
				do {
					try {
						numTarea = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
						p.getTasks().get(numTarea).setTaskDone(false);
						if (p.getTasks().get(numTarea) instanceof TareaNumerica) {    // si elijo una tarea numerica
							((TareaNumerica) p.getTasks().get(numTarea)).setNumero(0);
						} else if (p.getTasks().get(numTarea) instanceof TareaAlfanumerica) {    // si elijo una tarea alfanumerica
							((TareaAlfanumerica) p.getTasks().get(numTarea)).setInfo("");
						}
						choice=0;
					} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
						System.out.println("Error. Ingrese una opcion valida.");
					}
				} while(choice==1);
				System.out.println("Desea desmarcar otra tarea?\n"
						+ "1. Si\n"
						+ "2. No");
			}
			try {
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
				if(choice!=1 && choice!=2){
					System.out.println("Ingrese una opcion valida.");
				}
			}
			catch(InputMismatchException | NumberFormatException e){
				System.out.println("Ingrese una opcion valida.");
				choice=0;
			}
		} while(choice!=2);
	}

	private void mostrarTratamientosEnCurso(){
		System.out.println("\nLISTADO DE TRATAMIENTOS EN CURSO\n"
						   + "--------------------------------");
		for(int i=0; i < tratamientos.size(); i++){
			if(tratamientos.get(i).getEstado().equals(EstadoDelTratamiento.EN_CURSO)){
				System.out.println((i+1) + ". " + tratamientos.get(i));
			}
		}
	}

	private void mostrarTratamientos(){
		System.out.println("\nLISTADO DE TRATAMIENTOS\n"
						 + "-----------------------");
		for(int i=0; i < tratamientos.size(); i++){
			System.out.println((i+1) + ". " + tratamientos.get(i));
		}
	}

	private void notificacionesTareasSinRealizar() {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
		sb.append("Tareas sin realizar: \n");
		for(Tratamiento t :this.getTratamientos())
		{
			for(int i = 0 ; i < t.getPlan().getTasks().size() ; i++) {
				if(!t.getPlan().getTasks().get(i).isTaskDone())
				{
					flag = true;
					sb.append("\t" + t.getPlan().getTasks().get(i).getTaskName() + ".\n");
				}
			}
		}
		if(flag){
			System.out.println(sb);
		}
		else {
			System.out.println("No tiene tareas sin realizar.");
		}
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PACIENTE | " + super.toString() +
				"\nTratamientos del Paciente:\n");
		sb.append("---------------------------------------------------------------------------------------\n");
		for (int i = 0; i < tratamientos.size(); i++) {
			sb.append((i+1) + ") " + tratamientos.get(i));
		}
		return sb.toString();
	}
	//------------------------------------------------------------------------------------------------------------------
}
