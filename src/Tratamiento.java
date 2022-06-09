

public class Tratamiento {
	private String inicio;
	private String fin;
	private String profesionalEncargado;
	private Plan plan;
	private EstadoDelTratamiento estado = EstadoDelTratamiento.SIN_ASIGNAR;

	public Tratamiento() {
	}

	public Tratamiento(String profesionalEncargado, Enfermedad e) {
		this.profesionalEncargado = profesionalEncargado;
		this.plan = new Plan(e);
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin() {
		this.fin = this.inicio.plusDays(plan.getDuracion());
	}

	public String getProfesionalEncargado() {
		return profesionalEncargado;
	}

	public void setProfesionalEncargado(String profesionalEncargado) {
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
