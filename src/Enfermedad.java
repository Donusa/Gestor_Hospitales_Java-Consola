import java.util.ArrayList;
import java.util.List;

public class Enfermedad {
	private List<String> sintomas = new ArrayList<>();
	private String name;

	public Enfermedad() {
	}

	public Enfermedad(List<String> sintomas, String name) {
		this.sintomas = sintomas;
		this.name = name;
	}

	public List<String> getSintomas() {
		return sintomas;
	}

	public void setSintomas(List<String> sintomas) {
		this.sintomas = sintomas;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
