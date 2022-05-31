import java.time.LocalDate;

public class Tratamiento {
	LocalDate inicio;
	LocalDate fin;
	Profesional profesionalEncargado;
	Plan plan;
	public Tratamiento(Profesional profesionalEncargado, Enfermedad e) {
		this.profesionalEncargado = profesionalEncargado;
		this.plan = new Plan(e);
	}
	
	
}
