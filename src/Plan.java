import java.util.ArrayList;
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
	public void mostrarTareas(){
		for(int i=0; i<tasks.size(); i++){
			System.out.println("Tarea N°"+(i+1)+":\n"+
					"   Nombre: "+ tasks.get(i).getTaskName());
		}
	}

	public void agregarTarea() {
		TipoDeTarea tipo;
		String nombre;
		int flag=1;
		do {

			System.out.println("Escribe el tipo de tarea que desea agregar:\n" +
								"-NUMERICA\n" +
								"-ALFANUMERICA\n" +
								"-SOLOMARCAR");
			tipo = Enum.valueOf(TipoDeTarea.class, ScannerSingleton.getInstance().nextLine().toUpperCase());

			switch (tipo) {
				case NUMERICA:
					System.out.println("Ingrese el nombre de la tarea:");
					nombre = ScannerSingleton.getInstance().nextLine();
					TareaNumerica tareaNumerica = new TareaNumerica(nombre);
					this.tasks.add(tareaNumerica);
					break;
				case ALFANUMERICA:
					System.out.println("Ingrese el nombre de la tarea:");
					nombre = ScannerSingleton.getInstance().nextLine();
					TareaAlfanumerica tareaAlfanumerica = new TareaAlfanumerica(nombre);
					this.tasks.add(tareaAlfanumerica);
					break;
				case SOLOMARCAR:
					System.out.println("Ingrese el nombre de la tarea:");
					nombre = ScannerSingleton.getInstance().nextLine();
					Tarea tareaSoloMarcar = new Tarea(nombre);
					this.tasks.add(tareaSoloMarcar);
					break;
				default:
					System.out.println("Tipo de tarea no valido, vuelva a ingresar");
					flag = 2;
					break;
			}
		}while (flag == 2 );
		
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Plan de Control:\n" + enfermedad +
				"Duracion: " + duracion + " dias.\n" +
				"Tareas a realizar:\n");
		for (int i=0; i<tasks.size(); i++) {
			sb.append("\t" + i+1 + ". " + tasks.get(i) + "\n");
		}
		return sb.toString();
	}
	//------------------------------------------------------------------------------------------------------------------
}
