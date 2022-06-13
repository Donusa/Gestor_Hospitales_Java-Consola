
public class Tarea {
	//---Atributos------------------------------------------------------------------------------------------------------
	private String taskName;
	private boolean taskDone = false;
	//------------------------------------------------------------------------------------------------------------------


	//---Constructores--------------------------------------------------------------------------------------------------
	public Tarea() {
	}

	public Tarea(String taskName) {
		this.taskName = taskName;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Getters y Setters----------------------------------------------------------------------------------------------
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean isTaskDone() {
		return taskDone;
	}

	public void setTaskDone(boolean taskDone) {
		this.taskDone = taskDone;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tarea: " + taskName +
				" | Realizada: ");
		if(taskDone){
			sb.append("Si");
		}
		else{
			sb.append("No");
		}
		return sb.toString();
	}
	//------------------------------------------------------------------------------------------------------------------
}
