
public class Tarea {
	private String taskName;
	private boolean taskDone = false;

	public Tarea() {
	}

	public Tarea(String taskName) {
		this.taskName = taskName;
	}

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
	
}
