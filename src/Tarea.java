import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Tarea {
	//---Atributos------------------------------------------------------------------------------------------------------
	protected String taskName;
	protected boolean taskDone = false;
	//------------------------------------------------------------------------------------------------------------------


	//---Constructores--------------------------------------------------------------------------------------------------
	public Tarea() {
	}

	public Tarea(String taskName) {
		this.taskName = taskName;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Getters y Setters----------------------------------------------------------------------------------------------
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean isTaskDone() {
		return taskDone;
	}

	public void setTaskDone(boolean taskDone) {
		this.taskDone = taskDone;
	}
	//------------------------------------------------------------------------------------------------------------------

	public static Tarea nuevaTarea() {
		int choice = 0;
		String taskName = "";
		Tarea nuevaTarea = null;
		List<Tarea> saves = new ArrayList<>();
		do{

			System.out.println("Agregar Tarea:\n"
					+ "1. Tarea simple de solo Check\n"
					+ "2. Tarea con respuesta Alfanumerica.\n"
					+ "3. Tarea con respuesta Numerica\n");
			try{
				choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine());
			}
			catch(InputMismatchException e){
				System.out.println("Ingrese una opcion valida.\n");
			}

			switch (choice){
				case 1:
					nuevaTarea = new Tarea(taskName);
					break;
				case 2:
					nuevaTarea = new TareaAlfanumerica(taskName);
					break;
				case 3:
					nuevaTarea = new TareaNumerica(taskName);
					break;
			}

		} while (nuevaTarea == null);
		saves.add(nuevaTarea);
		System.out.println("Ingrese nombre de la tarea a agregar");
		nuevaTarea.setTaskName(ScannerSingleton.getInstance().nextLine());
		//guarda la lista valida correspondiente a la instancia de la tarea creada

		if(nuevaTarea instanceof TareaNumerica)
		{
			saves.addAll(SerializacionGuardado.deserializacion(nombreArchivos.TAREASNUMERICAS.getName(), new TareaNumerica()));
			SerializacionGuardado.serializacion(nombreArchivos.TAREASNUMERICAS.getName(), saves);
		}
		else if(nuevaTarea instanceof TareaAlfanumerica)
		{
			saves.addAll(SerializacionGuardado.deserializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), new TareaAlfanumerica()));
			SerializacionGuardado.serializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), saves);
		}
		else
		{
			saves.addAll(SerializacionGuardado.deserializacion(nombreArchivos.TAREASBASICAS.getName(), new Tarea()));
			SerializacionGuardado.serializacion(nombreArchivos.TAREASBASICAS.getName(), saves);
		}
		System.out.println("Tarea agregada.");
		return nuevaTarea;
	}

	public static void eliminarTarea() {
		List<Tarea> l = Sistema.verListaTareas();
		List<Tarea> save = new ArrayList<>();
		Tarea aux = new Tarea();
		int choice = -1;
		boolean repeat;
		if(!l.isEmpty()) {
			mostrarListaTareas();
			System.out.println("\nIngrese el numero de la tarea a eliminar: ");
			do {
				repeat=false;
				try {
					choice = Integer.parseInt(ScannerSingleton.getInstance().nextLine()) - 1;
					aux = l.get(choice);
					l.remove(choice);
					if (aux instanceof TareaNumerica) {
						for (Tarea t : l) {
							if (t instanceof TareaNumerica) {
								save.add(t);
							}
						}
						SerializacionGuardado.serializacion(nombreArchivos.TAREASNUMERICAS.getName(), save);
					} else if (aux instanceof TareaAlfanumerica) {
						for (Tarea t : l) {
							if (t instanceof TareaAlfanumerica) {
								save.add(t);
							}
						}
						SerializacionGuardado.serializacion(nombreArchivos.TAREASALFANUMERICAS.getName(), save);
					} else {
						for (Tarea t : l) {
							if (!(t instanceof TareaAlfanumerica || t instanceof TareaNumerica)) {
								save.add(t);
							}
						}
						SerializacionGuardado.serializacion(nombreArchivos.TAREASBASICAS.getName(), save);
					}
				} catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
					System.out.println("Error. Ingrese una opcion valida:");
					repeat=true;
				}
			} while(repeat);
			System.out.println("Tarea eliminada.");
		}
		else{
			System.out.println("No hay tareas cargadas en el Sistema.");
		}
	}

	public static void mostrarListaTareas(){
		List<Tarea> listaTareas = Sistema.verListaTareas();
		System.out.println("LISTA DE TAREAS\n"
				+ "---------------");
		for(int i = 0; i < listaTareas.size(); i++){
			System.out.println((i+1) + ". " + listaTareas.get(i).getTaskName());
		}
	}
	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(taskName + " | Realizada: ");
		if (taskDone) {
			sb.append("Si");
		} else {
			sb.append("No");
		}
		return sb.toString();
	}
}
	//------------------------------------------------------------------------------------------------------------------

