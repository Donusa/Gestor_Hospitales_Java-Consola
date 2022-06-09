
public class Tarea {
	protected String taskName;
	protected boolean taskDone = false;

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
		return "Tarea [taskName=" + taskName + ", taskDone=" + taskDone + "]\n";
	}
	
	
}
