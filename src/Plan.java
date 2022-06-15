import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Plan {
	//---Atributos------------------------------------------------------------------------------------------------------
	private Enfermedad enfermedad;
	private List<Tarea> tasks = new ArrayList<>();
	private int duracion=0;
	//------------------------------------------------------------------------------------------------------------------


	//---Constructores--------------------------------------------------------------------------------------------------
	public Plan() {
	}

	public Plan(Enfermedad enfermedad) {
		this.enfermedad = enfermedad;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Getters y Setters----------------------------------------------------------------------------------------------
	public Enfermedad getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(Enfermedad enfermedad) {
		this.enfermedad = enfermedad;
	}

	public List<Tarea> getTasks() {
		return tasks;
	}

	public void setTasks(List<Tarea> tasks) {
		this.tasks = tasks;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Metodos--------------------------------------------------------------------------------------------------------
	public void mostrarTareasDelPlan(){
		System.out.println("LISTA DE TAREAS\n"
						 + "---------------");
		for(int i = 0; i < this.getTasks().size(); i++){
			System.out.println((i+1) + ". " + this.getTasks().get(i));
		}
	}

	public void agregarTareaAlPlan(){
		int numTarea=-2;
		boolean repeat;

		System.out.println("\nSeleccione un numero de tarea para agregarla al nuevo Plan:\n");
		List<Tarea> listaTareas = Sistema.verListaTareas();
		Tarea.mostrarListaTareas();
		System.out.println("0. Crear nueva tarea.");
		do {
			repeat = false;
			try {
				numTarea = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
				if (numTarea == -1) {
					this.getTasks().add(Tarea.nuevaTarea());
				} else {
					this.getTasks().add(listaTareas.get(numTarea));
				}
			} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
				System.out.println("Error. Ingrese una opcion valida:");
				repeat = true;
			}
		} while(repeat);
		if(numTarea!=-1) {
			System.out.println("Tarea agregada.");
		}
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Plan de Control:\n" + enfermedad +
				"Duracion: " + duracion + " dias.\n" +
				"Tareas:\n");
		for (int i=0; i<tasks.size(); i++) {
			sb.append("\t" + (i+1) + ". " + tasks.get(i) + "\n");
		}
		return sb.toString();
	}
	//------------------------------------------------------------------------------------------------------------------
}
