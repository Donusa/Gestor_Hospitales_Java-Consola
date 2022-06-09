
public class TareaSiNo extends Tarea{
	
	private String decision;

	public TareaSiNo() {
	}

	public TareaSiNo(String taskName) {
		super(taskName);
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(int decision) {
		if(decision == 1){
			this.decision = "Si";
		}
		if(decision == 2)
		{
			this.decision = "No";
		}
	}

	@Override
	public String toString() {
		return super.toString() +
				"Respuesta: " + decision;
	}

}
