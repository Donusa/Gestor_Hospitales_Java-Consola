
public enum nombreArchivos {
	
	ADMINISTRADORES("admins"),
	PACIENTES("pacientes"),
	PROFESIONALES("profesionales"),
	TRATAMIENTOS("tratamientos"),
	PLANES("planes"),
	ENFERMEDADES("enfermedades"),
	TAREASBASICAS("tareasBasicas"),
	TAREASALFANUMERICAS("tareasAlfanumericas"),
	TAREASSINO("tareasSiNo"),
	TAREASNUMERICAS("tareasNumericas"),
	BACKUPFECHA("backupFecha");
	
	private String name;
	
	nombreArchivos(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
