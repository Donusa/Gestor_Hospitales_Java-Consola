import java.time.LocalDate;

public class Tratamiento {
	LocalDate inicio;
	LocalDate fin;
	Profesional profesionalEncargado;
	Plan plan;
	EstadoDelTratamiento estado = EstadoDelTratamiento.SIN_ASIGNAR;

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

	public void setFin(LocalDate fin) {
		this.fin = fin;
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
}
