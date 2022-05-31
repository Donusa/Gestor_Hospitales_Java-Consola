import java.util.ArrayList;
import java.util.List;

public class Plan {
	Enfermedad enfermedad;
	List<Tarea> tasks = new ArrayList<>();
	int duracion;
	
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
	
	
}
