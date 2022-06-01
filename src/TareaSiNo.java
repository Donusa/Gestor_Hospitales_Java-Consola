
public class TareaSiNo extends Tarea{
	
	private String decision;
	
	public TareaSiNo(String taskName) {
		super(taskName);
		// TODO Auto-generated constructor stub
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
	
}
