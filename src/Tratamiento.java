

public class Tratamiento {
	protected String inicio;
	protected String fin;
	protected String profesionalEncargado;
	protected Plan plan;
	EstadoDelTratamiento estado = EstadoDelTratamiento.SIN_ASIGNAR;

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
}
