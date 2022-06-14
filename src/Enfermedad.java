import java.util.ArrayList;
import java.util.List;

public class Enfermedad {
	//---Atributos------------------------------------------------------------------------------------------------------
	private List<String> sintomas = new ArrayList<>();
	private String name;
	//------------------------------------------------------------------------------------------------------------------


	//---Constructores--------------------------------------------------------------------------------------------------
	public Enfermedad() {
	}

	public Enfermedad(List<String> sintomas, String name) {
		this.sintomas = sintomas;
		this.name = name;
	}
	//------------------------------------------------------------------------------------------------------------------


	//---Getters y Setters----------------------------------------------------------------------------------------------
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
	//------------------------------------------------------------------------------------------------------------------


	//---toString-------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Enfermedad: " + name +
				  "\nSintomas: ");
		sb.append(sintomas.get(0));
		for(int i = 1; i< sintomas.size(); i++){
			sb.append(", " + sintomas.get(i));
		}
		sb.append("\n");
		return sb.toString();
	}
	//------------------------------------------------------------------------------------------------------------------
}
