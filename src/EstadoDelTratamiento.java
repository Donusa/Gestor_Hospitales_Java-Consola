public enum EstadoDelTratamiento {
    SIN_ASIGNAR ("Sin asignar"),
    EN_CURSO ("En curso"),
    ESPERANDO_ALTA("Esperando alta"),
    FINALIZADO ("Finalizado");

    private String name;

    EstadoDelTratamiento(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
