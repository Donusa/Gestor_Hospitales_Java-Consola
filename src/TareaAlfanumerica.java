
public class TareaAlfanumerica extends Tarea{
	
	private String info = "";

	public TareaAlfanumerica() {
	}

	public TareaAlfanumerica(String taskName) {
		super(taskName);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return super.toString()+" TareaAlfanumerica [info=" + info + "]\n";
	}

	
}
