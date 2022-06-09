import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Plan {
	private Enfermedad enfermedad;
	private List<Tarea> tasks = new ArrayList<>();
	private int duracion=0;

	public Plan() {
	}

	public Plan(Enfermedad enfermedad) {
		this.enfermedad = enfermedad;
	}

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

	public void mostrarTareas(){
		for(int i=0; i<tasks.size(); i++){
			System.out.println("Tarea N°"+(i+1)+":\n"+
					"   Nombre: "+ tasks.get(i).taskName);
		}
	}

	public void agregarTarea() {
		Scanner scan = new Scanner(System.in);
		TipoDeTarea tipo;
		String nombre;
		int flag=1;
		do {

			System.out.println("Escribe el tipo de tarea que desea agregar:\n" +
					"-SINO\n" +
					"-NUMERICA\n" +
					"-ALFANUMERICA\n" +
					"-SOLOMARCAR");
			tipo = Enum.valueOf(TipoDeTarea.class, scan.nextLine().toUpperCase());

			switch (tipo) {
				case SINO:
					System.out.println("Ingrese el nombre de la tarea:");
					nombre = scan.nextLine();
					TareaSiNo tareaSiNo = new TareaSiNo(nombre);
					this.tasks.add(tareaSiNo);
					break;
				case NUMERICA:
					System.out.println("Ingrese el nombre de la tarea:");
					nombre = scan.nextLine();
					TareaNumerica tareaNumerica = new TareaNumerica(nombre);
					this.tasks.add(tareaNumerica);
					break;
				case ALFANUMERICA:
					System.out.println("Ingrese el nombre de la tarea:");
					nombre = scan.nextLine();
					TareaAlfanumerica tareaAlfanumerica = new TareaAlfanumerica(nombre);
					this.tasks.add(tareaAlfanumerica);
					break;
				case SOLOMARCAR:
					System.out.println("Ingrese el nombre de la tarea:");
					nombre = scan.nextLine();
					Tarea tareaSoloMarcar = new Tarea(nombre);
					this.tasks.add(tareaSoloMarcar);
					break;
				default:
					System.out.println("Tipo de tarea no valido, vuelva a ingresar");
					flag = 2;
					break;
			}
		}while (flag == 2 );
		scan.close();
	}

	@Override
	public String toString() {
		return "Plan de Control:\n"
				+ enfermedad +
				"Tareas a realizar:\n" + tasks +
				"Duracion: " + duracion + " dias.\n";
	}
}
