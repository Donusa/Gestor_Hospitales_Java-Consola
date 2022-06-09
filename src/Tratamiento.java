import java.time.LocalDate;

public class Tratamiento {
	private LocalDate inicio;
	private LocalDate fin;
	private Profesional profesionalEncargado;
	private Plan plan;
	private EstadoDelTratamiento estado = EstadoDelTratamiento.SIN_ASIGNAR;

	public Tratamiento() {
	}

	public Tratamiento(Profesional profesionalEncargado, Enfermedad e) {
		this.profesionalEncargado = profesionalEncargado;
		this.plan = new Plan(e);
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFin() {
		return fin;
	}

	public void setFin() {
		this.fin = this.inicio.plusDays(plan.getDuracion());
	}

	public Profesional getProfesionalEncargado() {
		return profesionalEncargado;
	}

	public void setProfesionalEncargado(Profesional profesionalEncargado) {
		this.profesionalEncargado = profesionalEncargado;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public EstadoDelTratamiento getEstado() {
		return estado;
	}

	public void setEstado(EstadoDelTratamiento estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Tratamiento de Enfermedad: \"" + plan.getEnfermedad() + "\"" +
				"\nProfesional encargado: " + profesionalEncargado.getUserName() +
				"\nInicio: " + inicio +
				" | Fin: " + fin +
				"\n" + plan +
				"\nEstado del Tratamiento: " + estado.getName();
	}
}
